package teamA_API.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class Employee {

  private String employeeID;
  private String employeeType;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNum;
  private String address;
  public Date startDate;

  public Employee() {}

  public Employee(
      String employeeID,
      String employeeType,
      String firstName,
      String lastName,
      String email,
      String phoneNum,
      String address,
      Date startDate) {
    this.employeeID = employeeID;
    this.employeeType = employeeType;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNum = phoneNum;
    this.address = address;
    this.startDate = startDate;
  }

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  // timestamp get/set
  public String getStartDate() {
    if (this.startDate == null) {
      return "";
    }
    return this.startDate.toString();
  }

  public void setStartDate(String s) throws ParseException {
    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = originalFormat.parse(s);
    this.startDate = date;
  }

  public String getEmployeeID() {
    return employeeID;
  }

  public String getEmployeeType() {
    return employeeType;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public String getAddress() {
    return address;
  }
}
