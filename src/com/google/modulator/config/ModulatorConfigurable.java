package com.google.modulator.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ModulatorConfigurable implements Configurable {
  @Nls
  @Override
  public String getDisplayName() {
    return "Modulator";
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return "Configure modulator settings";
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return null;
  }

  @Override
  public boolean isModified() {
    return false;
  }

  @Override
  public void apply() throws ConfigurationException {

  }

  @Override
  public void reset() {

  }

  @Override
  public void disposeUIResources() {

  }
}
