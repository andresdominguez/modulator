package com.google.modulator.modules;

import com.google.modulator.CompletionHelper;
import com.google.modulator.Finder;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.icons.AllIcons;
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

    Finder<ModuleResult> moduleFinder = new Finder<ModuleResult>(originalPosition.getProject(), new ModuleVisitor());
    for (ModuleResult moduleResult : moduleFinder.find()) {
      LookupElement lookupElement = CompletionHelper.newBuilder(moduleResult.name)
          .withPresentableText(String.format("%s (%s)", moduleResult.name, moduleResult.file.getName()))
          .withIcon(AllIcons.FileTypes.JavaScript)
          .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE);

      completionResultSet.addElement(lookupElement);
    }
  }
}
