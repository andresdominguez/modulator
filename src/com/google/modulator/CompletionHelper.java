package com.google.modulator;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class CompletionHelper extends CompletionProvider<CompletionParameters> {
  @NotNull
  static LookupElement getLookupElement(String completionString) {
    // TODO: find a better icon.
    return LookupElementBuilder
        .create(completionString)
        .withBoldness(true)
        .withIcon(AllIcons.FileTypes.JavaScript)
        .withPresentableText(completionString)
        .withCaseSensitivity(true)
        .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE);
  }

  public static Iterable<? extends LookupElement> getCompletions(Iterable<String> completions) {
    List<LookupElement> lookups = new ArrayList<LookupElement>();

    for (String module : completions) {
      lookups.add(getLookupElement(module));
    }

    return lookups;
  }
}
