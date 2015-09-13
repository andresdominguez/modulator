package com.google.modulator.config;

import com.intellij.ide.util.PropertiesComponent;

public class ModulatorSettings {

  private CompletionStrategy strategy;
  private String filePath;
  private String completeTokens;

  public enum Property {

    Strategy("com.google.modulator.completionStrategy", CompletionStrategy.SEARCH.name()),
    FilePath("com.google.modulator.filePath", ""),
    CompleteTokens("com.google.modulator.autoCompleteTokens", "");

    private final String property;
    private final String defaultValue;

    private Property(String property, String defaultValue) {
      this.property = property;
      this.defaultValue = defaultValue;
    }

    public String getProperty() {
      return property;
    }

    public String getDefaultValue() {
      return defaultValue;
    }
  }

  private final PropertiesComponent properties;

  public ModulatorSettings() {
    this.properties = PropertiesComponent.getInstance();
    load();
  }

  void load() {
    strategy = CompletionStrategy.valueOf(getValue(Property.Strategy));
    filePath = getValue(Property.FilePath);
    completeTokens = getValue(Property.CompleteTokens);
  }

  private void save() {
    setValue(Property.Strategy, strategy.name());
    setValue(Property.FilePath, filePath);
    setValue(Property.CompleteTokens, completeTokens);
  }

  private String getValue(Property property) {
    return properties.getValue(
        property.getProperty(), property.getDefaultValue());
  }

  private void setValue(Property property, String value) {
    properties.setValue(property.getProperty(), value);
  }

  public CompletionStrategy getStrategy() {
    return strategy;
  }

  public void setStrategy(CompletionStrategy strategy) {
    this.strategy = strategy;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getCompleteTokens() {
    return completeTokens;
  }

  public void setCompleteTokens(String completeTokens) {
    this.completeTokens = completeTokens;
  }
}
