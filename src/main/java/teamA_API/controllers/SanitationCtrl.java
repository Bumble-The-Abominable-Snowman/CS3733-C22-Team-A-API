package teamA_API.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import teamA_API.App;
import teamA_API.Main;
import teamA_API.db.EmployeeDAO;
import teamA_API.entities.Employee;
import teamA_API.entities.SR;

public class SanitationCtrl {

  private @FXML JFXComboBox<String> employeeRequestedChoice;
  private @FXML JFXComboBox<String> employeeAssignedChoice;
  private @FXML Label origLocID;
  private @FXML Label destLocID;
  private @FXML JFXComboBox<String> typeChoice;
  private @FXML JFXTextArea commentsBox;
  private @FXML JFXTextArea typeOtherBox;

  private Popup popup;

  private static List<SR> requests = new ArrayList<>();

  private static String toLocationOption;
  private static String fromLocationOption;

  @FXML
  private void initialize() {
    popup = new Popup();

    // Make text areas wrap
    commentsBox.setWrapText(true);
    typeOtherBox.setWrapText(true);
    origLocID.setWrapText(true);
    destLocID.setWrapText(true);

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
    origLocID.setText(String.format("Orig Location ID: %s", fromLocationOption));
    destLocID.setText(String.format("Dest Location ID: %s", toLocationOption));

    // Put employees from database in employee menu
    EmployeeDAO dataObject = Main.getEmployeeDAO();
    for (Employee e : dataObject.getEmployeeList()) {
      employeeAssignedChoice.getItems().add(e.getFullName());
      employeeRequestedChoice.getItems().add(e.getFullName());
    }
  }

  private void reloadEmployeeBox() {
    EmployeeDAO dataObject = Main.getEmployeeDAO();
    employeeAssignedChoice.getItems().clear();
    employeeRequestedChoice.getItems().clear();
    for (Employee e : dataObject.getEmployeeList()) {
      employeeRequestedChoice.getItems().add(e.getFullName());
      employeeAssignedChoice.getItems().add(e.getFullName());
    }
  }

  @FXML
  private void submitRequest() throws IllegalAccessException {
    // Checks to make sure required fields are selected
    if (typeChoice.getSelectionModel().getSelectedItem() == null
        || employeeRequestedChoice.getSelectionModel().getSelectedItem() == null
        || employeeAssignedChoice.getSelectionModel().getSelectedItem() == null) return;
    // Creates sanitation request object
    SR request = new SR();
    String typeOption = typeChoice.getSelectionModel().getSelectedItem().toString();
    if (typeOption == "Other") typeOption = typeOtherBox.getText();
    if (typeOption.equals("")) return;

    for (Employee e : Main.getEmployeeDAO().getEmployeeList()) {
      if (employeeRequestedChoice.getSelectionModel().getSelectedItem().equals(e.getFullName())) {
        request.setField("employee_requested", e);
      }
    }

    for (Employee e : Main.getEmployeeDAO().getEmployeeList()) {
      if (employeeAssignedChoice.getSelectionModel().getSelectedItem().equals(e.getFullName())) {
        request.setField("employee_assigned", e);
      }
    }

    request.setFieldByString("request_id", String.format("SANITATION_API_ID_%d", requests.size()));
    request.setFieldByString("start_location", toLocationOption);
    request.setFieldByString("end_location", toLocationOption);
    request.setFieldByString(
        "comments", commentsBox.getText().equals("") ? "N/A" : commentsBox.getText());

    request.setFieldByString("sanitation_type", typeOption);

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

  public static void setFromLocation(String location) {
    fromLocationOption = location;
  }

  public static List<SR> getRequestList() {
    return requests;
  }

  @FXML
  private void addPopup() {

    popup.hide();

    // cancel button
    JFXButton cancelUpdateButton = new JFXButton();
    cancelUpdateButton.setText("X");

    cancelUpdateButton.setOnAction(
        event -> {
          popup.hide();
        });

    Label empIDLabel = new Label("Employee ID");
    Label typeLabel = new Label("Employee Type");
    Label firstNameLabel = new Label("First Name");
    Label lastNameLabel = new Label("Last Name");
    Label emailLabel = new Label("Email");
    Label phoneNumLabel = new Label("Phone Number");
    Label addressLabel = new Label("Address");
    Label startDate = new Label("Start Date");

    empIDLabel.setPadding(new Insets(10, 10, 10, 5));
    typeLabel.setPadding(new Insets(10, 10, 10, 5));
    firstNameLabel.setPadding(new Insets(10, 10, 10, 5));
    lastNameLabel.setPadding(new Insets(10, 10, 10, 5));
    emailLabel.setPadding(new Insets(10, 10, 10, 5));
    phoneNumLabel.setPadding(new Insets(10, 10, 10, 5));
    addressLabel.setPadding(new Insets(10, 10, 10, 5));

    cancelUpdateButton.setStyle("-fx-alignment: center ;");
    cancelUpdateButton.setAlignment(Pos.CENTER);

    // value text area
    TextArea employeeIDText = new TextArea();
    employeeIDText.setPromptText("Enter Employee ID:");
    employeeIDText.setMinSize(50, 30);
    employeeIDText.setMaxSize(150, 30);

    ObservableList<String> typeOptions = FXCollections.observableArrayList("Admin", "Staff");
    JFXComboBox type = new JFXComboBox(typeOptions);

    TextArea firstNameText = new TextArea();
    firstNameText.setPromptText("Enter First Name:");
    firstNameText.setMinSize(50, 30);
    firstNameText.setMaxSize(150, 30);

    TextArea lastNameText = new TextArea();
    lastNameText.setPromptText("Enter Last Name:");
    lastNameText.setMinSize(50, 30);
    lastNameText.setMaxSize(150, 30);

    TextArea emailText = new TextArea();
    emailText.setPromptText("Enter Email:");
    emailText.setMinSize(50, 30);
    emailText.setMaxSize(150, 30);

    TextArea phoneNumberText = new TextArea();
    phoneNumberText.setPromptText("Enter Phone Number:");
    phoneNumberText.setMinSize(50, 30);
    phoneNumberText.setMaxSize(150, 30);

    TextArea addressText = new TextArea();
    addressText.setPromptText("Enter Address:");
    addressText.setMinSize(50, 30);
    addressText.setMaxSize(150, 30);

    JFXButton updateButton = new JFXButton();
    updateButton.setText("Update");

    updateButton.setOnAction(
        e -> {
          if (employeeIDText.getText().length() > 2
              && firstNameText.getText().length() > 2
              && lastNameText.getText().length() > 2
              && emailText.getText().length() > 2
              && phoneNumberText.getText().length() > 2
              && addressText.getText().length() > 2) {
            EmployeeDAO dataObject = Main.getEmployeeDAO();
            dataObject.enterEmployee(
                employeeIDText.getText(),
                type.getSelectionModel().getSelectedItem().toString(),
                firstNameText.getText(),
                lastNameText.getText(),
                emailText.getText(),
                phoneNumberText.getText(),
                addressText.getText(),
                new Date());
            updateButton.setTextFill(Color.GREEN);
            reloadEmployeeBox();
          } else {
            updateButton.setTextFill(Color.RED);
          }
        });

    // add it to the scene
    GridPane content = new GridPane();
    content.setRowIndex(empIDLabel, 0);
    content.setColumnIndex(empIDLabel, 0);
    content.setRowIndex(typeLabel, 1);
    content.setColumnIndex(typeLabel, 0);
    content.setRowIndex(firstNameLabel, 2);
    content.setColumnIndex(firstNameLabel, 0);
    content.setRowIndex(lastNameLabel, 3);
    content.setColumnIndex(lastNameLabel, 0);
    content.setRowIndex(emailLabel, 4);
    content.setColumnIndex(emailLabel, 0);
    content.setRowIndex(phoneNumLabel, 5);
    content.setColumnIndex(phoneNumLabel, 0);
    content.setRowIndex(addressLabel, 6);
    content.setColumnIndex(addressLabel, 0);
    content.setRowIndex(cancelUpdateButton, 7);
    content.setColumnIndex(cancelUpdateButton, 0);

    content.setRowIndex(employeeIDText, 0);
    content.setColumnIndex(employeeIDText, 1);
    content.setRowIndex(type, 1);
    content.setColumnIndex(type, 1);
    content.setRowIndex(firstNameText, 2);
    content.setColumnIndex(firstNameText, 1);
    content.setRowIndex(lastNameText, 3);
    content.setColumnIndex(lastNameText, 1);
    content.setRowIndex(emailText, 4);
    content.setColumnIndex(emailText, 1);
    content.setRowIndex(phoneNumberText, 5);
    content.setColumnIndex(phoneNumberText, 1);
    content.setRowIndex(addressText, 6);
    content.setColumnIndex(addressText, 1);
    content.setRowIndex(updateButton, 7);
    content.setColumnIndex(updateButton, 1);

    content
        .getChildren()
        .addAll(
            empIDLabel,
            employeeIDText,
            typeLabel,
            type,
            firstNameLabel,
            firstNameText,
            lastNameLabel,
            lastNameText,
            emailLabel,
            emailText,
            phoneNumLabel,
            phoneNumberText,
            addressLabel,
            addressText,
            updateButton,
            cancelUpdateButton);

    content.setPadding(new Insets(10, 5, 10, 5));
    content.setBackground(
        new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), null)));
    content.setEffect(new DropShadow());

    popup.getContent().add(content);

    popup.show(App.getStage());
  }
}
