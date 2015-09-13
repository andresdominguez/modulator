package com.google.modulator.params;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.lang.javascript.psi.JSFunctionExpression;
import com.intellij.lang.javascript.psi.JSParameterList;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;

public class ParamsContributor extends CompletionContributor {

  public ParamsContributor() {
    PsiElementPattern.Capture<PsiElement> pattern =
        PlatformPatterns.psiElement()
//            .withParent(PlatformPatterns.psiElement(JSParameterList.class))
            .withLanguage(JavascriptLanguage.INSTANCE);

    ParamsCompletionProvider completionProvider = new ParamsCompletionProvider();

    extend(CompletionType.BASIC, pattern, completionProvider);
  }
}