package com.google.modulator.params;

import com.intellij.openapi.vfs.VirtualFile;

class ServiceResult {
  public final String name;
  public final VirtualFile file;

  public ServiceResult(String name, VirtualFile file) {
    this.name = name;
    this.file = file;
  }
}
