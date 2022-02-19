package edu.wpi.cs3733.c22.teamA.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;

public class SanitationSR {

  @FXML JFXButton submitButton;
  @FXML JFXComboBox typeChoice;
  @FXML JFXComboBox toLocationChoice;
  @FXML JFXComboBox employeeChoice;
  @FXML JFXTextArea commentsBox;
  @FXML JFXTextArea typeOtherBox;

  @FXML
  private void initialize() {
    // Make text areas wrap
    commentsBox.setWrapText(true);
    typeOtherBox.setWrapText(true);

    // Put sanitation types in type menu
    typeChoice.getItems().addAll("Decontaminate Area", "Floor Spill", "Other");
    typeChoice.getSelectionModel().select("Select Type");
    typeChoice
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldValue, newValue) -> {
              if (newValue.equals("Other")) {
                typeOtherBox.setVisible(true);
              } else {
                typeOtherBox.setVisible(false);
              }
            });

    // Put example locations in location menu
    toLocationChoice.getItems().addAll("Emergency", "Chapel", "Elevator LL2");
    toLocationChoice.getSelectionModel().select("Select Location");

    // Put example employees in employee menu
    employeeChoice.getItems().addAll("Trump", "Obama", "The Rock");
    employeeChoice.getSelectionModel().select("Select Employee");
  }

  @FXML
  private void submitRequest() {
    // Implement request
  }
}
