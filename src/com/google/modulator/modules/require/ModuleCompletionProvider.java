package com.google.modulator.modules.require;

import com.google.modulator.modules.ContributorHelper;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

class ModuleCompletionProvider extends CompletionProvider<CompletionParameters> {

  @Override
  protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                ProcessingContext processingContext,
                                @NotNull CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();
    if (originalPosition == null) {
      return;
    }

    String text = originalPosition.getParent().getParent().getParent().getText();
    if (text == null || !text.startsWith("goog.require")) {
      return;
    }

    ContributorHelper.addModulesToCompleteList(completionResultSet, originalPosition.getProject());
  }
}
