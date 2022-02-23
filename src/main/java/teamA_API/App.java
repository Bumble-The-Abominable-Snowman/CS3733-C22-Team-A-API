package teamA_API;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  private static Stage guiStage;

  static int windowPosX = -1;
  static int windowPosY = -1;
  static int windowWidth = -1;
  static int windowHeight = -1;
  static String pathToCss = "";

  public static Stage getStage() {
    return guiStage;
  }

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    URL xmlUrl = App.class.getResource("views/SanitationSR.fxml");
    loader.setLocation(xmlUrl);

    Parent root = loader.load();
    Scene sanitationScene = new Scene(root);
    if (!pathToCss.equals("")) sanitationScene.getStylesheets().add(pathToCss);
    primaryStage.setScene(sanitationScene);
    if (windowPosX != -1) primaryStage.setX(windowPosX);
    if (windowPosY != -1) primaryStage.setY(windowPosY);
    if (windowWidth != -1) primaryStage.setWidth(windowWidth);
    if (windowHeight != -1) primaryStage.setHeight(windowHeight);
    primaryStage.show();
    guiStage = primaryStage;
  }

  public static void createWindow() throws IOException {
    Stage primaryStage = new Stage();
    FXMLLoader loader = new FXMLLoader();
    URL xmlUrl = App.class.getResource("views/SanitationSR.fxml");
    loader.setLocation(xmlUrl);

    Parent root = loader.load();
    Scene sanitationScene = new Scene(root);
    if (!pathToCss.equals("")) sanitationScene.getStylesheets().add(pathToCss);
    primaryStage.setScene(sanitationScene);
    if (windowPosX != -1) primaryStage.setX(windowPosX);
    if (windowPosY != -1) primaryStage.setY(windowPosY);
    if (windowWidth != -1) primaryStage.setWidth(windowWidth);
    if (windowHeight != -1) primaryStage.setHeight(windowHeight);
    primaryStage.show();
    guiStage = primaryStage;
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
