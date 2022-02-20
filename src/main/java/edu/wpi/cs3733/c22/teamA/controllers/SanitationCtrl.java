package edu.wpi.cs3733.c22.teamA.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.c22.teamA.entities.SanitationSR;
import javafx.fxml.FXML;

public class SanitationCtrl {

  @FXML JFXComboBox typeChoice;
  @FXML JFXComboBox toLocationChoice;
  @FXML JFXComboBox employeeChoice;
  @FXML JFXTextArea commentsBox;
  @FXML JFXTextArea typeOtherBox;

  private static String toLocationOption = "N/A";

  @FXML
  private void initialize() {
    // Make text areas wrap
    commentsBox.setWrapText(true);
    typeOtherBox.setWrapText(true);

    // Put sanitation types in type menu
    typeChoice.getItems().addAll("Decontaminate Area", "Floor Spill", "Other");
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
    if (toLocationOption != "N/A") {
      toLocationChoice.getItems().add(toLocationOption);
      toLocationChoice.getSelectionModel().select(toLocationOption);
    }

    // Put example employees in employee menu
    employeeChoice.getItems().addAll("Trump", "Obama", "The Rock");
  }

  @FXML
  private void submitRequest() {
    // Checks to make sure required fields are selected
    if (typeChoice.getSelectionModel().getSelectedItem() == null
        || toLocationChoice.getSelectionModel().getSelectedItem() == null
        || employeeChoice.getSelectionModel().getSelectedItem() == null) return;
    // Creates sanitation request object
    SanitationSR request = new SanitationSR();
    String typeOption = typeChoice.getSelectionModel().getSelectedItem().toString();
    if (typeOption == "Other") typeOption = typeOtherBox.getText();
    if (typeOption.equals("")) return;
    request.setSanitationType(typeOption);
    request.setToLocation(toLocationChoice.getSelectionModel().getSelectedItem().toString());
    request.setEmployeeAssigned(employeeChoice.getSelectionModel().getSelectedItem().toString());
    request.setComments(commentsBox.getText().equals("") ? "N/A" : commentsBox.getText());
    // Temporary
    System.out.println("Success!");
  }

  @FXML
  private void exit() {
    System.exit(0);
  }

  public static void setToLocation(String location) {
    toLocationOption = location;
  }
}
