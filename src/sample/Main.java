package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
    Scene scene = new Scene(root, 300, 275); // Setting the size of the GUI
    stage.setScene(scene);
    stage.show(); // Making the GUI appear
  }

  public static void main(String[] args) throws SQLException {
    DatabaseManager db = new DatabaseManager();
    Production product = new Production();
    Screen screen = new Screen("1080p", 144, 3);
    System.out.println(screen.toString());
    MoviePlayer moviePlayer = new MoviePlayer("1440p", 60, 2, MonitorType.LED);
    System.out.println(moviePlayer.toString());
    System.out.println( db.checkManager(1));
    EmployeeInfo eInfo = new EmployeeInfo();
    launch(args);
    db.closeCon();
  }
}
