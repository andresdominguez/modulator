package com.google.modulator.modules;

import com.intellij.openapi.vfs.VirtualFile;

class ModuleResult {
  final String name;
  final VirtualFile file;

  public ModuleResult(String name, VirtualFile file) {
    this.name = name;
    this.file = file;
  }
}
