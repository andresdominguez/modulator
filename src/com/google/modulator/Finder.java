package com.google.modulator;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Finder<T> {
  private final ProjectFileIndex fileIndex;
  private final FileVisitor<T> visitor;

  public Finder(Project project, FileVisitor<T> visitor) {
    fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
    this.visitor = visitor;
  }

  public Iterable<T> find() {
    final List<T> list = new ArrayList<T>();

    fileIndex.iterateContent(new ContentIterator() {
      @Override
      public boolean processFile(VirtualFile file) {

        if (!visitor.isValidFile(file)) {
          return true;
        }

        Collection<T> findings = visitor.visitFile(file);
        if (findings != null) {
          list.addAll(findings);
        }

        return true;
      }
    });

    return list;
  }
}
