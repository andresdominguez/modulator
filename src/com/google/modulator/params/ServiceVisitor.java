package com.google.modulator.params;

import com.google.modulator.CompletionHelper;
import com.google.modulator.FileVisitor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ServiceVisitor implements FileVisitor<String> {

  private static final String SERVICE_PATTERN = ".service(";
  private final FileDocumentManager documentManager = FileDocumentManager.getInstance();

  @Override
  public boolean isValidFile(VirtualFile file) {
    return CompletionHelper.isValidFile(file);
  }

  @Override
  public Collection<String> visitFile(VirtualFile file) {
    List<String> services = new ArrayList<String>();

    try {
      Document document = documentManager.getDocument(file);
      if (document == null) {
        return services;
      }

      String text = document.getText();
      boolean contains = text.contains(SERVICE_PATTERN);
      if (!contains) {
        return services;
      }

      services.addAll(getServiceNames(text));
    } catch (Exception e) {
      System.err.println("Error finding services in file " + file.getCanonicalPath() + " " + e.getMessage());
      e.printStackTrace(System.err);
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
