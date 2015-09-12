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

  public ModuleFinder(String modulesPath, Project project) {
    startingDir = project.getBaseDir().findFileByRelativePath(modulesPath);
  }

  Iterable<String> findModules() {
    List<String> list = new ArrayList<String>();
    final String MODULE = "goog.module";

    VfsUtilCore.visitChildrenRecursively(startingDir, new VirtualFileVisitor() {
      @Override
      public boolean visitFile(@NotNull VirtualFile file) {

        if (file.isDirectory()) {
          return true;
        }

        FileDocumentManager instance = FileDocumentManager.getInstance();
        Document document = instance.getCachedDocument(file);
        if (document != null) {
          String text = document.getText(TextRange.create(0, 500));

          if (text.contains(MODULE)) {
            int start = text.indexOf(MODULE);
            int end = text.indexOf(")", start + MODULE.length());

            String moduleName = text.substring(start, end);
          }
        }

        return true;
      }
    });

    return list;
  }

}
