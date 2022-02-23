package edu.wpi.cs3733.c22.teamAPI;

import edu.wpi.cs3733.c22.teamAPI.controllers.SanitationCtrl;
import edu.wpi.cs3733.c22.teamAPI.db.Adb;
import edu.wpi.cs3733.c22.teamAPI.db.EmployeeDAO;
import edu.wpi.cs3733.c22.teamAPI.db.EmployeeDerbyImpl;
import edu.wpi.cs3733.c22.teamAPI.entities.Employee;
import edu.wpi.cs3733.c22.teamAPI.entities.SanitationSR;
import edu.wpi.cs3733.c22.teamAPI.exceptions.ServiceException;
import java.util.Date;
import java.util.List;

public class Main {
  private static EmployeeDAO dataObject = new EmployeeDerbyImpl();

  // Runs the program when ran separately
  public static void main(String[] args) {
    Adb.initialConnection();
    App.launch(App.class, args);
  }

  // API method to start program
  public static void run(
      int xCoord,
      int yCoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String destLocationID)
      throws ServiceException {
    App.windowPosX = xCoord;
    App.windowPosY = yCoord;
    App.windowWidth = windowWidth;
    App.windowHeight = windowLength;
    App.pathToCss = cssPath;
    SanitationCtrl.setToLocation(destLocationID);
    Adb.initialConnection();
    App.launch(App.class);
  }

  // Get a list of all sanitation requests
  public static List<SanitationSR> getRequestList() {
    return SanitationCtrl.getRequestList();
  }

  // Add an employee to the API database
  public static void addEmployee(
      String employeeID,
      String employeeType,
      String firstName,
      String lastName,
      String email,
      String phoneNum,
      String address,
      Date startDate) {
    dataObject.enterEmployee(
        employeeID, employeeType, firstName, lastName, email, phoneNum, address, startDate);
  }

  // Updates the data of an employee in the API database
  public static void updateEmployee(String employeeID, String field, Object changedValue) {
    dataObject.updateEmployee(employeeID, field, changedValue);
  }

  // Removes an employee from the API database
  public static void deleteEmployee(String employeeID) {
    dataObject.deleteEmployee(employeeID);
  }

  // Gets an employee from the database by ID
  public static Employee getEmployee(String employeeID) {
    return dataObject.getEmployee(employeeID);
  }

  // Gets all employees from the database
  public static List<Employee> getEmployeeList() {
    return dataObject.getEmployeeList();
  }

  public static EmployeeDAO getEmployeeDAO() {
    return dataObject;
  }
}
