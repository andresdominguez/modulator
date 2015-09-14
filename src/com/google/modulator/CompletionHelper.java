package com.google.modulator;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

public class CompletionHelper {

  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

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
    return newBuilder(completionString)
        .withPresentableText(completionString)
        .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE);
  }

  @NotNull
  public static LookupElementBuilder newBuilder(String completionString) {
    return LookupElementBuilder
        .create(completionString)
        .withBoldness(true)
        .withCaseSensitivity(true);
  }

  public static boolean isInvalidFile(VirtualFile file) {
    String name = file.getName();
    return name.startsWith(".") || file.isDirectory() || !name.endsWith(".js");
  }

  public static boolean isValidFile(VirtualFile file) {
    return !isInvalidFile(file);
  }
}
