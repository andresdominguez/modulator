package com.google.modulator.modules;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.lang.javascript.JavascriptLanguage;
import com.intellij.lang.javascript.psi.JSLiteralExpression;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;

public class ModuleContributor extends CompletionContributor {
  public ModuleContributor() {
    PsiElementPattern.Capture<PsiElement> pattern =
        PlatformPatterns.psiElement()
            .withParent(PlatformPatterns.psiElement(JSLiteralExpression.class))
            .withLanguage(JavascriptLanguage.INSTANCE);

    ModuleCompletionProvider completionProvider = new ModuleCompletionProvider();

    extend(CompletionType.BASIC, pattern, completionProvider);
  }
}