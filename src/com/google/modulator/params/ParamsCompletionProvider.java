package com.google.modulator.params;

import com.google.modulator.CompletionHelper;
import com.google.modulator.Finder;
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
import java.util.Arrays;
import java.util.List;

import static com.google.modulator.CompletionHelper.addCompletionsFromStringList;
import static com.google.modulator.config.CompletionStrategy.FILE;
import static com.google.modulator.config.CompletionStrategy.SEARCH;

class ParamsCompletionProvider extends CompletionProvider<CompletionParameters> {

  private final ModulatorSettings settings = new ModulatorSettings();

  @Override
  protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                ProcessingContext processingContext,
                                @NotNull CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();
    if (originalPosition == null || !isFunctionParamList(originalPosition)) {
      return;
    }

    // Should I complete from a file?
    CompletionStrategy strategy = settings.getStrategy();
    if (strategy != SEARCH) {
      addCompletionsFromStringList(completionResultSet, readAutoCompleteFile());
    }

    // Should I search the files?
    if (strategy != FILE) {
      addCompletionsFromStringList(completionResultSet, searchFilesForModules(originalPosition));
    }

    // Add extra complete tokens
    addCompletionsFromStringList(completionResultSet, readExtraTokens());
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

  private Iterable<String> searchFilesForModules(PsiElement originalPosition) {
    Finder<String> serviceFinder = new Finder<String>(originalPosition.getProject(), new ServiceVisitor());
    return serviceFinder.find();
  }

  @NotNull
  private List<String> readExtraTokens() {
    return Arrays.asList(settings.getCompleteServiceTokens().split(CompletionHelper.LINE_SEPARATOR));
  }
}
