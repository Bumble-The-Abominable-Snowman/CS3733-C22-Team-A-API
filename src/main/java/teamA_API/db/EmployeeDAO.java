package teamA_API.db;

import java.util.Date;
import java.util.List;
import teamA_API.entities.Employee;

public interface EmployeeDAO {

  public Employee getEmployee(String ID);

  public void updateEmployee(String ID, String field, Object change);

  public void enterEmployee(
      String employeeID,
      String employeeType,
      String firstName,
      String lastName,
      String email,
      String phoneNum,
      String address,
      Date startDate);

  public void deleteEmployee(String ID);

  public List<Employee> getEmployeeList();
}
