package edu.wpi.cs3733.c22.teamA;

import edu.wpi.cs3733.c22.teamA.controllers.SanitationCtrl;
import edu.wpi.cs3733.c22.teamA.db.Adb;
import edu.wpi.cs3733.c22.teamA.exceptions.ServiceException;

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
      String destLocationID,
      String originLocationID)
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
}
