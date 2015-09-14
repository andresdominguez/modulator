package com.google.modulator.modules;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.lang.javascript.psi.jsdoc.JSDocTagValue;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class JsDocCompletionProvider extends CompletionProvider<CompletionParameters> {
  @Override
  protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                ProcessingContext processingContext,
                                @NotNull CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();
    if (originalPosition == null || !isJsDocTagValue(originalPosition)) {
      return;
    }

    ContributorHelper.findModulesAndCompletions(completionResultSet, originalPosition.getProject());
  }

  private boolean isJsDocTagValue(PsiElement originalPosition) {
    return originalPosition.getParent() instanceof JSDocTagValue;
  }
}
