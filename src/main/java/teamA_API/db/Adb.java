package teamA_API.db;

import java.sql.*;

public class Adb {

  public static String pathToDBA = "";

  public static void initialConnection() {

    // Connection to database driver
    System.out.println("----- Apache Derby Connection Testing -----");
    pathToDBA = "src/main/resources/edu/wpi/cs3733/c22/teamA/db/API-DBA";

    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      System.out.println("Apache Derby embedded driver registered!\n");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Not Found");
      e.printStackTrace();
      return;
    }

    try {

      // Check if database exist. If not then create one.
      try {
        Connection connection =
            DriverManager.getConnection(
                String.format("jdbc:derby:%s;user=Admin;password=admin", pathToDBA));
        System.out.println("DB already exist");

      } catch (SQLException e) {
        Connection c =
            DriverManager.getConnection(String.format("jdbc:derby:%s;create=true", pathToDBA));
        turnOnBuiltInUsers(c);
        c.close();
        System.out.println("DB initialized");
        System.out.println("Closed connection");
      }

    } catch (SQLException e) {
      System.out.println("Connection failed");
      e.printStackTrace();
      return;
    }

    // Check if table exists
    // Check Employee table.
    try {

      Connection connection =
          DriverManager.getConnection(
              String.format("jdbc:derby:%s;user=Admin;password=admin", pathToDBA));
      Statement addTable = connection.createStatement();

      addTable.execute(
          "CREATE TABLE Employee(employeeID varchar(25), "
              + "employeeType varchar(25), "
              + "firstName varchar(25), "
              + "lastName varchar(25), "
              + "email varchar(25), "
              + "phoneNum varchar(25), "
              + "address varchar(25), "
              + "startDate date, "
              + "PRIMARY KEY (employeeID))");

    } catch (SQLException e) {
      System.out.println("Table Employee already exist");
    }
  }

  public static void turnOnBuiltInUsers(Connection conn) throws SQLException {

    String setProperty = "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(";
    String getProperty = "VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY(";
    String requireAuth = "'derby.connection.requireAuthentication'";
    String defaultConnMode = "'derby.database.defaultConnectionMode'";
    String fullAccessUsers = "'derby.database.fullAccessUsers'";
    String readOnlyAccessUsers = "'derby.database.readOnlyAccessUsers'";
    String provider = "'derby.authentication.provider'";
    String propertiesOnly = "'derby.database.propertiesOnly'";

    System.out.println("Turning on authentication.");
    Statement s = conn.createStatement();

    // Set and confirm requireAuthentication
    s.executeUpdate(setProperty + requireAuth + ", 'true')");
    ResultSet rs = s.executeQuery(getProperty + requireAuth + ")");
    rs.next();
    System.out.println("Value of requireAuthentication is " + rs.getString(1));

    // Set authentication scheme to Derby builtin
    s.executeUpdate(setProperty + provider + ", 'BUILTIN')");

    // Create some sample users
    s.executeUpdate(setProperty + "'derby.user.Admin', 'admin')");
    s.executeUpdate(setProperty + "'derby.user.Guest', 'guest')");

    // Define noAccess as default connection mode
    s.executeUpdate(setProperty + defaultConnMode + ", 'noAccess')");

    // Confirm default connection mode
    rs = s.executeQuery(getProperty + defaultConnMode + ")");
    rs.next();
    System.out.println("Value of defaultConnectionMode is " + rs.getString(1));

    // Define read-write user
    s.executeUpdate(setProperty + fullAccessUsers + ", 'Admin')");

    // Define read-only user
    s.executeUpdate(setProperty + readOnlyAccessUsers + ", 'Guest')");

    // Confirm full-access users
    rs = s.executeQuery(getProperty + fullAccessUsers + ")");
    rs.next();
    System.out.println("Value of fullAccessUsers is " + rs.getString(1));

    // Confirm read-only users
    rs = s.executeQuery(getProperty + readOnlyAccessUsers + ")");
    rs.next();
    System.out.println("Value of readOnlyAccessUsers is " + rs.getString(1));

    // We would set the following property to TRUE only when we were
    // ready to deploy. Setting it to FALSE means that we can always
    // override using system properties if we accidentally paint
    // ourselves into a corner.
    s.executeUpdate(setProperty + propertiesOnly + ", 'false')");
    s.close();
  }
}
