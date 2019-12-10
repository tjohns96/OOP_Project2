package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
  private Connection con = null;
  private DatabaseManager db = new DatabaseManager();
  @FXML private ComboBox typeComboBox;
  @FXML private ComboBox productComboBox;
  @FXML private TextField prodAmount;
  @FXML private Button recordButton;
  @FXML private Button buttonLogin;
  @FXML private TextField textFieldUserName;
  @FXML private TextField textFieldPassword;

  public Controller() throws SQLException {
    con = DriverManager.getConnection("jdbc:h2:./res/ProductDB");
  }

  public void recordButtonPress() {}

  public void setTypeComboBox() {
    ResultSet rs = null;
    try {
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT DISTINCT name FROM PRODUCT;");

      while (rs.next()) {
          productComboBox.getItems().add(rs.getString(1));
      }

    } catch (SQLException e) {
      db.sqlExceptionHandler(e);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    typeComboBox.getItems().addAll("Audio", "Visual", "AudioMobile", "VisualMobile");
    setTypeComboBox();
  }
  @FXML
  public void handleLogin(){
    String userName = textFieldUserName.getText();
    String password = textFieldPassword.getText();
  }
}
