package com.google.modulator.params;

import com.google.modulator.config.CompletionStrategy;
import com.google.modulator.config.ModulatorSettings;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.lang.javascript.psi.JSFunctionExpression;
import com.intellij.lang.javascript.psi.JSParameterList;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.modulator.CompletionHelper.addCompletionsFromStringList;

class ParamsCompletionProvider extends CompletionProvider<CompletionParameters> {

  private final ModulatorSettings settings;

  public ParamsCompletionProvider() {
    settings = new ModulatorSettings();
  }

  @Override
  protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                ProcessingContext processingContext,
                                @NotNull CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();
    if (originalPosition == null || !isFunctionParamList(originalPosition)) {
      return;
    }

    // Should I complete from a file?
    if (settings.getStrategy() == CompletionStrategy.FILE) {
      addCompletionsFromStringList(completionResultSet, readAutoCompleteFile());
      return;
    }

    ServiceFinder serviceFinder = new ServiceFinder(originalPosition.getProject());
    Iterable<String> services = serviceFinder.findServices();

    addCompletionsFromStringList(completionResultSet, services);
  }

  // Check if this is a function(<caret>) element
  private boolean isFunctionParamList(PsiElement psiElement) {
    PsiElement parent = psiElement.getParent();
    PsiElement grandParent = parent.getParent();

    return parent instanceof JSParameterList && grandParent instanceof JSFunctionExpression;
  }

  private Iterable<String> readAutoCompleteFile() {
    String filePath = settings.getFilePath();

    try {
      List<String> lines = new ArrayList<String>();

      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }

      return lines;
    } catch (IOException e) {
      throw new RuntimeException("Error reading file " + filePath, e);
    }
  }
}
