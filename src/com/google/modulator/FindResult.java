package com.google.modulator;

import com.intellij.openapi.vfs.VirtualFile;

public class FindResult {
  public final String token;
  public final VirtualFile virtualFile;

  public FindResult(String token, VirtualFile virtualFile) {
    this.token = token;
    this.virtualFile = virtualFile;
  }
}
