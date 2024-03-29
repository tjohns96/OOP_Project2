package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 *
 */
public class Main extends Application {

  /**
   * This is the method that starts the GUI up
   * @param stage
   * @throws Exception
   */
  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
    Scene scene = new Scene(root, 600, 400); // Setting the size of the GUI
    stage.setScene(scene);
    stage.show(); // Making the GUI appear
  }

  /**
   * The program that prints output to show that certain classes are working
   * @throws SQLException because I call getProductionInfo in order to print its contents
   */
  public static void main(String[] args) throws SQLException {
    DatabaseManager db = new DatabaseManager();
    Screen screen = new Screen("1080p", 144, 3);
    System.out.println(screen.toString());
    MoviePlayer moviePlayer = new MoviePlayer("1440p", 60, 2, MonitorType.LED);
    System.out.println(moviePlayer.toString());
    ObservableList<ProductionData> productionData =
        FXCollections.observableArrayList(db.getProductionInfo());
    for (ProductionData s : productionData) {
      System.out.println(s);
    }
    launch(args);
    db.closeCon();
  }
}
