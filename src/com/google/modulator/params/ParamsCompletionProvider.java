package com.google.modulator.params;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.lang.javascript.psi.JSFunctionExpression;
import com.intellij.lang.javascript.psi.JSParameterList;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;

public class ParamsCompletionProvider extends CompletionProvider<CompletionParameters> {
  @Override
  protected void addCompletions(CompletionParameters completionParameters, ProcessingContext processingContext, CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();
    if (!isFunctionParamList(originalPosition)) {
      return;
    }

  }

  private boolean isFunctionParamList(PsiElement psiElement) {
    PsiElement parent = psiElement.getParent();
    PsiElement grandParent = parent.getParent();

    return parent instanceof JSParameterList && grandParent instanceof JSFunctionExpression;
  }
}
