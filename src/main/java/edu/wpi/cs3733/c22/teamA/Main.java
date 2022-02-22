package edu.wpi.cs3733.c22.teamA;

import edu.wpi.cs3733.c22.teamA.controllers.SanitationCtrl;
import edu.wpi.cs3733.c22.teamA.db.Adb;
import edu.wpi.cs3733.c22.teamA.db.EmployeeDAO;
import edu.wpi.cs3733.c22.teamA.db.EmployeeDerbyImpl;
import edu.wpi.cs3733.c22.teamA.entities.Employee;
import edu.wpi.cs3733.c22.teamA.entities.SanitationSR;
import edu.wpi.cs3733.c22.teamA.exceptions.ServiceException;
import java.util.Date;
import java.util.List;

public class Main {

  // Runs the program when ran separately
  public static void main(String[] args) {
    Adb.initialConnection();
    App.launch(App.class, args);
  }

  // API method to start program
  void run(
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
  List<SanitationSR> getRequestList() {
    return SanitationCtrl.getRequestList();
  }

  // Add an employee to the API database
  void addEmployee(
      String employeeID,
      String employeeType,
      String firstName,
      String lastName,
      String email,
      String phoneNum,
      String address,
      Date startDate) {
    EmployeeDAO dataObject = new EmployeeDerbyImpl();
    dataObject.enterEmployee(
        employeeID, employeeType, firstName, lastName, email, phoneNum, address, startDate);
  }

  // Updates the data of an employee in the API database
  void updateEmployee(String employeeID, String field, Object changedValue) {
    EmployeeDAO dataObject = new EmployeeDerbyImpl();
    dataObject.updateEmployee(employeeID, field, changedValue);
  }

  // Removes an employee from the API database
  void deleteEmployee(String employeeID) {
    EmployeeDAO dataObject = new EmployeeDerbyImpl();
    dataObject.deleteEmployee(employeeID);
  }

  // Gets an employee from the database by ID
  Employee getEmployee(String employeeID) {
    EmployeeDAO dataObject = new EmployeeDerbyImpl();
    return dataObject.getEmployee(employeeID);
  }

  // Gets all employees from the database
  List<Employee> getEmployeeList() {
    EmployeeDAO dataObject = new EmployeeDerbyImpl();
    return dataObject.getEmployeeList();
  }
}
