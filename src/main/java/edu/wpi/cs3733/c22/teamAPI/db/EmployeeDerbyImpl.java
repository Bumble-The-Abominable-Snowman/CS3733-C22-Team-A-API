package edu.wpi.cs3733.c22.teamAPI.db;

import edu.wpi.cs3733.c22.teamAPI.entities.Employee;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDerbyImpl implements EmployeeDAO {

  public void EmployeeDerbyImpl() {}

  public Employee getEmployee(String ID) {
    try {
      Connection connection =
          DriverManager.getConnection(
              String.format("jdbc:derby:%s;user=Admin;password=admin", Adb.pathToDBA));
      Statement get = connection.createStatement();
      String str = String.format("SELECT * FROM Employee WHERE employeeID = '%s'", ID);
      ResultSet rset = get.executeQuery(str);
      Employee emp = new Employee();

      if (rset.next()) {
        String employeeID = rset.getString("employeeID");
        String employeeType = rset.getString("employeeType");
        String firstName = rset.getString("firstName");
        String lastName = rset.getString("lastName");
        String email = rset.getString("email");
        String phoneNum = rset.getString("phoneNum");
        String address = rset.getString("address");
        Date startDate = rset.getDate("startDate");

        emp =
            new Employee(
                employeeID, employeeType, firstName, lastName, email, phoneNum, address, startDate);
      }

      return emp;
    } catch (SQLException e) {
      System.out.println("Connection Failed");
      e.printStackTrace();
      return null;
    }
  }

  public void updateEmployee(String ID, String field, Object change) {
    try {
      Connection connection =
          DriverManager.getConnection(
              String.format("jdbc:derby:%s;user=Admin;password=admin", Adb.pathToDBA));
      Statement update = connection.createStatement();

      String str = "";
      if (change instanceof String) {
        str =
            String.format(
                "UPDATE Employee SET " + field + " = '%s' WHERE employeeID = '%s'", change, ID);
      } else {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = originalFormat.format(change);

        str =
            String.format(
                "UPDATE Employee SET "
                    + field
                    + " = '"
                    + startDateStr
                    + "' WHERE employeeID = '%s'",
                ID);
      }

      update.execute(str);
    } catch (SQLException e) {
      System.out.println("Failed");
      e.printStackTrace();
      return;
    }
  }

  public void enterEmployee(
      String employeeID,
      String employeeType,
      String firstName,
      String lastName,
      String email,
      String phoneNum,
      String address,
      Date startDate) {
    try {
      Connection connection =
          DriverManager.getConnection(
              String.format("jdbc:derby:%s;user=Admin;password=admin", Adb.pathToDBA));
      Statement insert = connection.createStatement();

      SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
      String startDateStr = originalFormat.format(startDate);

      String str =
          String.format(
              "INSERT INTO Employee(employeeID, employeeType, firstName, "
                  + "lastName, email, phoneNum, "
                  + "address, startDate) "
                  + " VALUES('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
              employeeID,
              employeeType,
              firstName,
              lastName,
              email,
              phoneNum,
              address,
              startDateStr);
      insert.execute(str);

    } catch (SQLException e) {
      System.out.println("Failed");
      e.printStackTrace();
      return;
    }
  }

  public void deleteEmployee(String ID) {
    try {
      Connection connection =
          DriverManager.getConnection(
              String.format("jdbc:derby:%s;user=Admin;password=admin", Adb.pathToDBA));
      Statement delete = connection.createStatement();
      String str = String.format("DELETE FROM Employee WHERE employeeID = '%s'", ID);
      delete.execute(str);

    } catch (SQLException e) {
      System.out.println("Failed");
      e.printStackTrace();
      return;
    }
  }

  public List<Employee> getEmployeeList() {
    List<Employee> empList = new ArrayList<>();
    try {
      Connection connection =
          DriverManager.getConnection(
              String.format("jdbc:derby:%s;user=Admin;password=admin", Adb.pathToDBA));
      Statement getNodeList = connection.createStatement();
      ResultSet rset = getNodeList.executeQuery("SELECT * FROM Employee");

      while (rset.next()) {
        String employeeID = rset.getString("employeeID");
        String employeeType = rset.getString("employeeType");
        String firstName = rset.getString("firstName");
        String lastName = rset.getString("lastName");
        String email = rset.getString("email");
        String phoneNum = rset.getString("phoneNum");
        String address = rset.getString("address");
        Date startDate = rset.getDate("startDate");

        Employee emp =
            new Employee(
                employeeID, employeeType, firstName, lastName, email, phoneNum, address, startDate);
        empList.add(emp);
      }
    } catch (SQLException e) {
      System.out.println("Failed");
      e.printStackTrace();
    }

    return empList;
  }
}
