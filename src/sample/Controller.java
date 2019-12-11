package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.xml.soap.Text;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
  private Connection con = null;
  private DatabaseManager db = new DatabaseManager();
  private EmployeeInfo eInfo = new EmployeeInfo();
  private Production production = new Production();

  @FXML private ComboBox typeComboBox;
  @FXML private ComboBox productComboBox;
  @FXML private TextField prodAmount;
  @FXML private TextField textFieldUserName;
  @FXML private TextField textFieldPassword;
  @FXML private TextArea loginResult;
  @FXML private TextField textFieldFirstName;
  @FXML private TextField textFieldLastName;
  @FXML private TextField textFieldAddPassword;
  @FXML private CheckBox checkBoxManager;
  @FXML private TextArea textAreaUserName;
  @FXML private TextArea textAreaProductionResults;
  @FXML private TextField textFieldManufacturer;
  @FXML private TextField textFieldProductName;
  @FXML private TextArea textAreaAddProduct;
  @FXML private TextField textFieldSearch;
  @FXML private TableView<ProductionData> tbvProductionInfo;
  @FXML private ComboBox productComboBox1;
  @FXML private TextField textFieldDepartment;
  @FXML private TextArea textAreaTestResults;

  private ObservableList<ProductionData> productionList;
  private int currentUser;
  private boolean manager = false;
  private boolean loggedIn = false;
  private String currentUserName;

  public Controller() throws SQLException {
    con = DriverManager.getConnection("jdbc:h2:./res/ProductDB");
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    typeComboBox.getItems().addAll("Audio", "Visual", "AudioMobile", "VisualMobile");
    setTypeComboBox();
  }

  public void recordButtonPress() throws SQLException {
    String productName = productComboBox.getValue().toString();
    String amount = prodAmount.getText();
    if (loggedIn == true) {
      production.insertProduction(
          amount,
          Integer.toString(db.selectTotalMade(productName, Integer.parseInt(amount))),
          Integer.toString(db.selectProductID(productName)));
      textAreaProductionResults.setText("Results Recorded!");
      prodAmount.clear();
      productComboBox.getSelectionModel().clearSelection();
      productComboBox.setValue(null);
      productionList = FXCollections.observableArrayList(db.getProductionInfo());
      tbvProductionInfo.setItems(productionList);
    } else {
      textAreaProductionResults.setText("You must be logged in to record production.");
    }
  }

  public void setTypeComboBox() {
    ResultSet rs = null;
    try {
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT DISTINCT name FROM PRODUCT;");

      while (rs.next()) {
        productComboBox.getItems().add(rs.getString(1));
        productComboBox1.getItems().add(rs.getString(1));
      }

    } catch (SQLException e) {
      db.sqlExceptionHandler(e);
    }
  }

  @FXML
  public void handleLogin() throws SQLException {
    String userName = textFieldUserName.getText();
    String password = textFieldPassword.getText();
    if (db.checkCredentials(userName, password)) {
      loggedIn = true;
      loginResult.setText("Logged in!");
      currentUser = db.setCurrentUser(userName);
      manager = db.checkManager(currentUser);
      textFieldUserName.clear();
      textFieldPassword.clear();
      productionList = FXCollections.observableArrayList(db.getProductionInfo());
      tbvProductionInfo.setItems(productionList);
      currentUserName = userName;
    } else {
      loginResult.setText("Username or password incorrect");
    }
  }

  @FXML
  public void handleAddProduct() throws SQLException {
    if (manager == true) {
      String productType = typeComboBox.getValue().toString();
      productType = productType.toUpperCase();
      productType = ItemType.valueOf(productType).getCode();
      String manufacturer = textFieldManufacturer.getText();
      String name = textFieldProductName.getText();
      String[] insertValues = {productType, manufacturer, name};
      String query = "INSERT INTO PRODUCT(TYPE, MANUFACTURER, NAME) VALUES(?,?,?)";
      db.insertProduct(query, insertValues);
      textFieldManufacturer.clear();
      textFieldProductName.clear();
      typeComboBox.getSelectionModel().clearSelection();
      textAreaAddProduct.setText("Product Added!");
      productComboBox.getItems().add(name);
      productComboBox1.getItems().add(name);
    } else {
      textAreaAddProduct.setText("Must be logged in as a manager to add products.");
    }
  }
  @FXML
  public void handleTest() throws SQLException {
    if(loggedIn == true){
    String productName = productComboBox1.getValue().toString();
    db.insertTest(productName, currentUserName);
    textAreaTestResults.setText("Test Recorded!");
    productComboBox1.getSelectionModel().clearSelection();
    }
    else{
      textAreaTestResults.setText("You need to be logged in to record testing.");
    }
  }

  @FXML
  public void handleAddEmployee() throws SQLException {
    if (manager == true) {
      String fName = textFieldFirstName.getText().trim();
      String lName = textFieldLastName.getText().trim();
      String password = textFieldAddPassword.getText();
      String department = textFieldDepartment.getText();
      String userName = eInfo.createUserName(fName.toLowerCase(), lName.toLowerCase());
      int isManager;
      if (checkBoxManager.isSelected()) {
        isManager = 1;
      } else {
        isManager = 0;
      }
      String sql =
          "INSERT INTO EMPLOYEE(FIRSTNAME,LASTNAME,MANAGER,USERNAME,PASSWORD,DEPTID) VALUES (?,?,?,?,?,?)";
      String[] insert = {fName, lName, Integer.toString(isManager), userName, password,department};
      db.insertEmployee(sql, insert);
      textFieldAddPassword.clear();
      textFieldFirstName.clear();
      textFieldLastName.clear();
      textAreaUserName.setText("Your new username is : " + userName);
      checkBoxManager.setSelected(false);
    } else {
      textFieldAddPassword.clear();
      textFieldFirstName.clear();
      textFieldLastName.clear();
      textAreaUserName.setText("You have to be logged in as a manager to add employees.");
      checkBoxManager.setSelected(false);
    }
  }
  public void handleSearch(){
    if(loggedIn == true){
    String search = textFieldSearch.getText();
    if(search.equals("")){
      productionList = FXCollections.observableArrayList(db.getProductionInfo());
      tbvProductionInfo.setItems(productionList);
    } else {
      productionList = FXCollections.observableArrayList(db.getSearchResults(search));
      tbvProductionInfo.setItems(productionList);
    }
    textFieldSearch.clear();
    }
    else{
      textFieldSearch.setText("You need to be logged in to view production records.");
    }
  }

  public void handleLogout() {
    manager = false;
    loggedIn = false;
    currentUserName = null;
    loginResult.setText("Logged Out!");
  }
}
