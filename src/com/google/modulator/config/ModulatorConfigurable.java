package com.google.modulator.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ModulatorConfigurable implements Configurable {

  private final ModulatorSettings settings;
  private ConfigForm view;

  public ModulatorConfigurable() {
    settings = new ModulatorSettings();
  }

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
    if (view == null) {
      view = new ConfigForm();
    }
    view.setCompletionStrategy(CompletionStrategy.FILE);

    return view.getMainPanel();
  }

  @Override
  public boolean isModified() {
    return view.getCompletionStrategy() != settings.getStrategy() ||
        !view.getFilePath().equals(settings.getFilePath()) ||
        !view.getCompleteTokens().equals(settings.getCompleteTokens());
  }

  @Override
  public void apply() throws ConfigurationException {
    settings.setStrategy(view.getCompletionStrategy());
    settings.setFilePath(view.getFilePath());
    settings.setCompleteTokens(view.getCompleteTokens());

    settings.save();
  }

  @Override
  public void reset() {
    settings.load();

    view.setCompletionStrategy(settings.getStrategy());
    view.setFilePath(settings.getFilePath());
    view.setCompleteTokens(settings.getCompleteTokens());
  }

  @Override
  public void disposeUIResources() {
    this.view = null;
  }
}
