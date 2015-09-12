package com.google.modulator;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class ModuleCompletionProvider extends CompletionProvider<CompletionParameters> {
  @Override
  protected void addCompletions(CompletionParameters completionParameters, ProcessingContext processingContext, CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();
    if (originalPosition == null) {
      return;
    }

    String text = originalPosition.getParent().getParent().getParent().getText();
    if (text == null) {
      return;
    }

    if (!text.startsWith("goog.require")) {
      return;
    }

    String completionString = "Holaa";
    completionResultSet.addElement(getLookupElement(completionString));
    System.out.println("yeah");


    System.out.println(1);
  }

  @NotNull
  private LookupElement getLookupElement(String completionString) {
    return LookupElementBuilder
        .create(completionString)
        .withBoldness(true)
        .withPresentableText(completionString)
        .withCaseSensitivity(true)
        .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE);
  }
}
