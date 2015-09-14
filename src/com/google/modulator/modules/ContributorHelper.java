package com.google.modulator.modules;

import com.google.modulator.CompletionHelper;
import com.google.modulator.Finder;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class ContributorHelper {
  public static void addModulesToCompleteList(@NotNull CompletionResultSet completionResultSet, Project project) {
    Finder<ModuleResult> moduleFinder = new Finder<ModuleResult>(project, new ModuleVisitor());
    for (ModuleResult moduleResult : moduleFinder.find()) {
      LookupElement lookupElement = CompletionHelper.newBuilder(moduleResult.name)
          .withPresentableText(String.format("%s (%s)", moduleResult.name, moduleResult.file.getName()))
          .withIcon(AllIcons.FileTypes.JavaScript)
          .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE);

      completionResultSet.addElement(lookupElement);
    }
  }
}
