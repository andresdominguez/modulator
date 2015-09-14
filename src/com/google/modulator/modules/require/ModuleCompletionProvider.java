package com.google.modulator.modules.require;

import com.google.modulator.CompletionHelper;
import com.google.modulator.config.ModulatorSettings;
import com.google.modulator.modules.ContributorHelper;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

class ModuleCompletionProvider extends CompletionProvider<CompletionParameters> {

  private final ModulatorSettings settings;

  ModuleCompletionProvider() {
    settings = new ModulatorSettings();
  }

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

  @NotNull
  private List<String> readExtraTokens() {
    return Arrays.asList(settings.getCompleteServiceTokens().split(CompletionHelper.LINE_SEPARATOR));
  }
}
