package com.google.modulator;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModuleCompletionProvider extends CompletionProvider<CompletionParameters> {
  @Override
  protected void addCompletions(CompletionParameters completionParameters, ProcessingContext processingContext, CompletionResultSet completionResultSet) {

    PsiElement originalPosition = completionParameters.getOriginalPosition();
    if (originalPosition == null) {
      return;
    }

    String text = originalPosition.getParent().getParent().getParent().getText();
    if (text == null || !text.startsWith("goog.require")) {
      return;
    }

    completionResultSet.addAllElements(getCompletions(originalPosition.getProject()));
  }

  private Iterable<? extends LookupElement> getCompletions(Project project) {
    List<LookupElement> lookups = new ArrayList<LookupElement>();

    ModuleFinder moduleFinder = new ModuleFinder("src", project);
    for (String module : moduleFinder.findModules()) {
      lookups.add(getLookupElement(module));
    }

    return lookups;
  }

  @NotNull
  private LookupElement getLookupElement(String completionString) {
    // TODO: find a better icon.
    return LookupElementBuilder
        .create(completionString)
        .withBoldness(true)
        .withIcon(AllIcons.FileTypes.JavaScript)
        .withPresentableText(completionString)
        .withCaseSensitivity(true)
        .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE);
  }
}
