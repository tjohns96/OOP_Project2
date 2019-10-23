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
    AudioPlayer iPodShuffle = new AudioPlayer("iPod Shuffle", "Apple", "mp3");
    iPodShuffle.setType("Audio");
    System.out.println(iPodShuffle.toString());
    iPodShuffle.play();
    iPodShuffle.stop();
    launch(args);
  }
}
