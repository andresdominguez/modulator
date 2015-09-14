package com.google.modulator.modules.jsdoc;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.lang.javascript.psi.jsdoc.JSDocTagValue;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;

public class JsDocContributor extends CompletionContributor {

  public JsDocContributor() {
    PsiElementPattern.Capture<PsiElement> pattern =
        PlatformPatterns.psiElement()
            .withParent(PlatformPatterns.psiElement(JSDocTagValue.class))
            .withLanguage(JavascriptLanguage.INSTANCE);

    JsDocCompletionProvider completionProvider = new JsDocCompletionProvider();

    extend(CompletionType.BASIC, pattern, completionProvider);
  }
}
