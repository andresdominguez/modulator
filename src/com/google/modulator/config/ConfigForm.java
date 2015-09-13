package com.google.modulator.config;

import javax.swing.*;

public class ConfigForm {
  private JRadioButton searchFilesRadioButton;
  private JRadioButton useFileRadioButton;
  private JRadioButton bothRadioButton;
  private JPanel filePanel;
  private JTextField pathText;
  private JTextArea extraCompleteTextArea;
  private JPanel mainPanel;

  public JComponent getMainPanel() {
    return mainPanel;
  }
}
