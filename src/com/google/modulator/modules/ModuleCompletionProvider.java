package com.google.modulator.modules;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.google.modulator.CompletionHelper.addCompletionsFromStringList;

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

    ModuleFinder moduleFinder = new ModuleFinder(originalPosition.getProject());
    addCompletionsFromStringList(completionResultSet, moduleFinder.findModules());
  }
}
