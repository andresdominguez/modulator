package com.google.modulator.params;

import com.google.modulator.CompletionHelper;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ServiceFinder {

  private static final String SERVICE_PATTERN = ".service(";
  private final ProjectFileIndex fileIndex;
  private final FileDocumentManager documentManager;


  public ServiceFinder(Project project) {
    fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
    documentManager = FileDocumentManager.getInstance();
  }

  Iterable<String> findServices() {
    final List<String> list = new ArrayList<String>();

    fileIndex.iterateContent(new ContentIterator() {
      @Override
      public boolean processFile(VirtualFile file) {

        // Skip hidden files, dirs, and non-js files.
        if (CompletionHelper.isInvalidFile(file)) {
          return true;
        }

        Collection<String> services = findServicesInFile(file);
        list.addAll(services);

        return true;
      }
    });

    return list;
  }

  @NotNull
  private Collection<String> findServicesInFile(VirtualFile file) {
    List<String> services = new ArrayList<String>();

    try {
      Document document = documentManager.getDocument(file);
      if (document == null) {
        return services;
      }

      System.out.println("File " + file.getName());

      String text = document.getText();
      boolean contains = text.contains(SERVICE_PATTERN);
      if (!contains) {
        return services;
      }

      services.addAll(getServiceNames(text));
    } catch (Exception e) {
      System.err.println("Error finding services in file " + file.getCanonicalPath() + " " + e.getMessage());
    }

    return services;
  }

  private List<String> getServiceNames(String text) {
    List<String> indices = new ArrayList<String>();
    int last = 0;

    while ((last = text.indexOf(SERVICE_PATTERN, last)) != -1) {
      // Find the opening quote.
      int start = text.indexOf("'", last) + 1;
      int end = text.indexOf("'", start);

      String substring = text.substring(start, end);
      indices.add(substring.trim());
      last = end;
    }

    return indices;
  }
}
