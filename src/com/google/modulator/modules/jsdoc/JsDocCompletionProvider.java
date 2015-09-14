package com.google.modulator.modules.jsdoc;

import com.google.modulator.CompletionHelper;
import com.google.modulator.config.ModulatorSettings;
import com.google.modulator.modules.ContributorHelper;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.lang.javascript.psi.jsdoc.JSDocTagValue;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class JsDocCompletionProvider extends CompletionProvider<CompletionParameters> {

  private final ModulatorSettings settings = new ModulatorSettings();

  @Override
  protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                ProcessingContext processingContext,
                                @NotNull CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();
    if (originalPosition == null || !isJsDocTagValue(originalPosition)) {
      return;
    }

    // Get all the modules
    ContributorHelper.findModulesAndCompletions(completionResultSet, originalPosition.getProject());

    // Add extra tokens
    CompletionHelper.addCompletionsFromStringList(completionResultSet, readExtraTokens());
  }

  private boolean isJsDocTagValue(PsiElement originalPosition) {
    return originalPosition.getParent() instanceof JSDocTagValue;
  }

  @NotNull
  private List<String> readExtraTokens() {
    return Arrays.asList(settings.getCompleteJsDocTokens().split(CompletionHelper.LINE_SEPARATOR));
  }
}
