package com.google.modulator;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface FileVisitor<T> {
  boolean isValidFile(VirtualFile file);
  @Nullable
  Collection<T> visitFile(VirtualFile file);
}
