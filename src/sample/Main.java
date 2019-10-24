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
    product.insertProduction(Integer.toString(db.selectProductionID()), "2",Integer.toString(db.selectTotalMade("DiskMan", 2)));
    product.insertProduction(Integer.toString(db.selectProductionID()), "2",Integer.toString(db.selectTotalMade("DiskMan", 2)));
    db.closeCon();
    launch(args);
  }
}
