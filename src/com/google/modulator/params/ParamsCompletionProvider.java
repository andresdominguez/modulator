package com.google.modulator.params;

import com.google.modulator.CompletionHelper;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.javascript.psi.JSFunctionExpression;
import com.intellij.lang.javascript.psi.JSParameterList;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.google.modulator.CompletionHelper.getCompletions;

public class ParamsCompletionProvider extends CompletionProvider<CompletionParameters> {
  @Override
  protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                ProcessingContext processingContext,
                                @NotNull CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();
    if (!isFunctionParamList(originalPosition)) {
      return;
    }

    ServiceFinder serviceFinder = new ServiceFinder();
    Iterable<String> services = serviceFinder.findServices();

    completionResultSet.addAllElements(getCompletions(services));
  }

  // Check if this is a function(<caret>) element
  private boolean isFunctionParamList(PsiElement psiElement) {
    PsiElement parent = psiElement.getParent();
    PsiElement grandParent = parent.getParent();

    return parent instanceof JSParameterList && grandParent instanceof JSFunctionExpression;
  }
}
