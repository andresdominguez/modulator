package com.google.modulator;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModuleFinder {

  private final VirtualFile startingDir;
  private final static String MODULE = "goog.module(";

  public ModuleFinder(String modulesPath, Project project) {
    startingDir = project.getBaseDir().findFileByRelativePath("");
  }

  Iterable<String> findModules() {
    final List<String> list = new ArrayList<String>();
    final FileDocumentManager documentManager = FileDocumentManager.getInstance();

    VfsUtilCore.visitChildrenRecursively(startingDir, new VirtualFileVisitor() {
      @Override
      public boolean visitFile(@NotNull VirtualFile file) {

        // Do not visit hidden files / dirs.
        if (file.getName().startsWith(".")) {
          return false;
        }

        // Visit directories.
        if (file.isDirectory()) {
          return true;
        }

        Document document = documentManager.getCachedDocument(file);
        if (document == null) {
          return true;
        }

        String moduleName = findModuleName(document);
        if (moduleName != null) {
          list.add(moduleName);
        }

        return true;
      }
    });

    return list;
  }

  private String findModuleName(Document document) {
    // Get the first 500 characters of the file.
    String text = document.getText(TextRange.create(0, Math.min(500, document.getTextLength())));

    // No module in this file?
    if (!text.contains(MODULE)) {
      return null;
    }

    int start = text.indexOf(MODULE) + MODULE.length();
    int end = text.indexOf(")", start);

    // Remove quotes from module name.
    return text.substring(start, end)
        .replaceAll("'", "");
  }
}
