package com.google.modulator.jsdoc;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;

public class JsDocCompletionProvider extends CompletionProvider {
  @Override
  protected void addCompletions(CompletionParameters completionParameters,
                                ProcessingContext processingContext,
                                CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();

    System.out.println(1);
  }
}
