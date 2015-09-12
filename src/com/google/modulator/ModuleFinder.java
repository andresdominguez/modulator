package com.google.modulator;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.List;

class ModuleFinder {

  private final static String MODULE = "goog.module(";
  private static final int MAX_CHARS = 700;
  private final ProjectFileIndex fileIndex;
  private final FileDocumentManager documentManager;

  public ModuleFinder(Project project) {
    fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
    documentManager = FileDocumentManager.getInstance();
  }

  Iterable<String> findModules() {
    final List<String> list = new ArrayList<String>();

    fileIndex.iterateContent(new ContentIterator() {
      @Override
      public boolean processFile(VirtualFile file) {

        // Skip hidden files, dirs, and non-js files.
        String name = file.getName();
        if (name.startsWith(".") || file.isDirectory() || !name.endsWith(".js")) {
          return true;
        }

        String moduleName = findModuleName(file);
        if (moduleName != null) {
          list.add(moduleName);
        }

        return true;
      }
    });

    return list;
  }

  private String findModuleName(VirtualFile file) {
    try {
      Document document = documentManager.getDocument(file);
      if (document == null) {
        return null;
      }

      // Get the first few characters of the file.
      String text = document
          .getText(TextRange.create(0, Math.min(MAX_CHARS, document.getTextLength())));

      // Not module in this file?
      if (!text.contains(MODULE)) {
        return null;
      }

      int start = text.indexOf(MODULE) + MODULE.length();
      int end = text.indexOf(")", start);

      // Remove quotes from module name.
      return text.substring(start, end).replaceAll("'", "");
    } catch (Exception e) {
      System.err.println("Error reading file " + file.getName() + ":" + e.getMessage());
    }
    return null;
  }
}
