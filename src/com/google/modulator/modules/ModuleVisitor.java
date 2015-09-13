package com.google.modulator.modules;

import com.google.modulator.CompletionHelper;
import com.google.modulator.FileVisitor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

class ModuleVisitor implements FileVisitor<ModuleResult> {

  private final static String MODULE = "goog.module(";
  private static final int MAX_CHARS = 700;
  private final FileDocumentManager documentManager = FileDocumentManager.getInstance();

  @Override
  public boolean isValidFile(VirtualFile file) {
    return CompletionHelper.isValidFile(file);
  }

  @Nullable
  @Override
  public Collection<ModuleResult> visitFile(VirtualFile file) {
    List<ModuleResult> modules = ContainerUtil.newArrayList();

    try {
      Document document = documentManager.getDocument(file);
      if (document == null) {
        return null;
      }

      // Get the first few characters of the file.
      int endOffset = Math.min(MAX_CHARS, document.getTextLength());
      String text = document.getText(TextRange.create(0, endOffset));

      // Not module in this file?
      if (!text.contains(MODULE)) {
        return null;
      }

      int start = text.indexOf(MODULE) + MODULE.length();
      int end = text.indexOf(")", start);

      // Remove quotes from module name.
      String moduleName = text.substring(start, end).replaceAll("'", "").trim();
      modules.add(new ModuleResult(moduleName, file));
    } catch (Exception e) {
      System.err.println("Error reading file " + file.getName() + ":" + e.getMessage());
    }

    return modules;
  }
}
