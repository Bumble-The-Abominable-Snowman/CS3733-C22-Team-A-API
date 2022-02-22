package edu.wpi.cs3733.c22.teamA.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.c22.teamA.db.EmployeeDAO;
import edu.wpi.cs3733.c22.teamA.db.EmployeeDerbyImpl;
import edu.wpi.cs3733.c22.teamA.entities.SanitationSR;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;

public class SanitationCtrl {

  @FXML JFXComboBox typeChoice;
  @FXML JFXComboBox employeeChoice;
  @FXML JFXTextArea toLocationBox;
  @FXML JFXTextArea commentsBox;
  @FXML JFXTextArea typeOtherBox;

  private static List<SanitationSR> requests = new ArrayList<>();

  private static String toLocationOption = "N/A";

  @FXML
  private void initialize() {
    // Make text areas wrap
    commentsBox.setWrapText(true);
    typeOtherBox.setWrapText(true);
    toLocationBox.setWrapText(true);

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
    if (toLocationOption != "N/A") toLocationBox.setText(toLocationOption);

    // Put employees from database in employee menu
    EmployeeDAO dataObject = new EmployeeDerbyImpl();
    employeeChoice.getItems().addAll(dataObject.getEmployeeList());
  }

  @FXML
  private void submitRequest() {
    // Checks to make sure required fields are selected
    if (typeChoice.getSelectionModel().getSelectedItem() == null
        || toLocationBox.getText().equals("")
        || employeeChoice.getSelectionModel().getSelectedItem() == null) return;
    // Creates sanitation request object
    SanitationSR request = new SanitationSR();
    String typeOption = typeChoice.getSelectionModel().getSelectedItem().toString();
    if (typeOption == "Other") typeOption = typeOtherBox.getText();
    if (typeOption.equals("")) return;
    request.setSanitationType(typeOption);
    request.setToLocation(toLocationBox.getText());
    request.setEmployeeAssigned(employeeChoice.getSelectionModel().getSelectedItem().toString());
    request.setComments(commentsBox.getText().equals("") ? "N/A" : commentsBox.getText());
    // Add request to internal list
    requests.add(request);
  }

  @FXML
  private void exit() {
    System.exit(0);
  }

  public static void setToLocation(String location) {
    toLocationOption = location;
  }

  public static List<SanitationSR> getRequestList() {
    return requests;
  }
}
