package com.google.modulator.config;

import com.intellij.ide.util.PropertiesComponent;

public class ModulatorSettings {

  private CompletionStrategy strategy;
  private String filePath;
  private String completeServiceTokens;
  private String completeJsDocTokens;

  public enum Property {

    Strategy("com.google.modulator.completionStrategy", CompletionStrategy.SEARCH.name()),
    FilePath("com.google.modulator.filePath", ""),
    CompleteServiceTokens("com.google.modulator.autoCompleteServiceTokens", ""),
    CompleteJsDocTokens("com.google.modulator.autoCompleteJsDocTokens", "");

    private final String property;
    private final String defaultValue;

    Property(String property, String defaultValue) {
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
    completeServiceTokens = getValue(Property.CompleteServiceTokens);
    completeJsDocTokens = getValue(Property.CompleteJsDocTokens);
  }

  void save() {
    setValue(Property.Strategy, strategy.name());
    setValue(Property.FilePath, filePath);
    setValue(Property.CompleteServiceTokens, completeServiceTokens);
    setValue(Property.CompleteJsDocTokens, completeJsDocTokens);
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

  public String getCompleteServiceTokens() {
    return completeServiceTokens;
  }

  public void setCompleteServiceTokens(String completeTokens) {
    this.completeServiceTokens = completeTokens;
  }

  public String getCompleteJsDocTokens() {
    return completeJsDocTokens;
  }

  public void setCompleteJsDocTokens(String completeJsDocTokens) {
    this.completeJsDocTokens = completeJsDocTokens;
  }
}
