package com.google.modulator;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

public class CompletionHelper {

  public static void addCompletionsFromStringList(
      @NotNull CompletionResultSet completionResultSet,
      Iterable<String> completions) {

    completionResultSet.addAllElements(ContainerUtil.map(completions, new Function<String, LookupElement>() {
      @Override
      public LookupElement fun(String completion) {
        return getLookupElement(completion);
      }
    }));
  }

  @NotNull
  public static LookupElement getLookupElement(String completionString) {
    return getLookupElement(completionString, completionString);
  }

  @NotNull
  public static LookupElement getLookupElement(String completionString, String presentableText) {
    // TODO: find a better icon.
    return LookupElementBuilder
        .create(completionString)
        .withBoldness(true)
        .withIcon(AllIcons.FileTypes.JavaScript)
        .withPresentableText(presentableText)
        .withCaseSensitivity(true)
        .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE);
  }

  public static boolean isInvalidFile(VirtualFile file) {
    String name = file.getName();
    return name.startsWith(".") || file.isDirectory() || !name.endsWith(".js");
  }

  public static boolean isValidFile(VirtualFile file) {
    return !isInvalidFile(file);
  }
}
