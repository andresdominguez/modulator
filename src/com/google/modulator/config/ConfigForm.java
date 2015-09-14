package com.google.modulator.config;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigForm {
  private JRadioButton searchFilesRadioButton;
  private JRadioButton useFileRadioButton;
  private JRadioButton bothRadioButton;
  private JPanel filePanel;
  private JTextField pathText;
  private JTextArea completeServicesTextArea;
  private JPanel mainPanel;
  private JTextArea completeJsDocTextarea;

  private CompletionStrategy completionStrategy;

  public ConfigForm() {
    setCompletionStrategy(CompletionStrategy.FILE);
  }

  public JComponent getMainPanel() {
    mapRadioButton(searchFilesRadioButton, CompletionStrategy.SEARCH);
    mapRadioButton(useFileRadioButton, CompletionStrategy.FILE);
    mapRadioButton(bothRadioButton, CompletionStrategy.BOTH);

    return mainPanel;
  }

  private void mapRadioButton(JRadioButton radioButton, final CompletionStrategy completionStrategy) {
    radioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        setCompletionStrategy(completionStrategy);
      }
    });
  }

  public void setCompletionStrategy(CompletionStrategy strategy) {
    this.completionStrategy = strategy;
    switch (strategy) {
      case SEARCH:
        searchFilesRadioButton.setSelected(true);
        break;
      case FILE:
        useFileRadioButton.setSelected(true);
        break;
      case BOTH:
        bothRadioButton.setSelected(true);
        break;
    }

    // Switch visibility
    filePanel.setVisible(this.completionStrategy != CompletionStrategy.SEARCH);
  }

  public CompletionStrategy getCompletionStrategy() {
    return completionStrategy;
  }

  public String getFilePath() {
    return pathText.getText();
  }

  public void setFilePath(String path) {
    pathText.setText(path);
  }

  public String getCompleteServiceTokens() {
    return completeServicesTextArea.getText();
  }

  public void setCompleteServiceTokens(String tokens) {
    this.completeServicesTextArea.setText(tokens);
  }

  public String getCompleteJsDoc() {
    return this.completeJsDocTextarea.getText();
  }

  public void setCompleteJsDoc(String text) {
    completeJsDocTextarea.setText(text);
  }
}
