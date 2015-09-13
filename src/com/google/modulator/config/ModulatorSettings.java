package com.google.modulator.config;

public class ModulatorSettings {
  public enum Property {

    SearchStrategy("com.google.modulator.completionStrategy", CompletionStrategy.SEARCH.toString()),
    FilePath("com.google.modulator.filePath", ""),
    CompleteTokens("com.google.modulator.autoCompleteTokens", "");

    private final String property;
    private final String defaultValue;

    private Property(String property, String defaultValue) {
      this.property = property;
      this.defaultValue = defaultValue;
    }
  }

}
