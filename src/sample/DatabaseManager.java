package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * All interactions with SQL are handled here
 */
public class DatabaseManager {
  private String sqlStatement;
  private Connection con = null;
  private ResultSet rs = null;

  /**
   * Initializes the connection
   * @throws SQLException
   */
  public DatabaseManager() throws SQLException {
    con = DriverManager.getConnection("jdbc:h2:./res/ProductDB");
  }

  /**
   * Gets the information to insert an employee into db
   * @param iQuery the sql statement
   * @param insertValues The values to be inserted
   * @throws SQLException
   */
  public void insertEmployee(String iQuery, String[] insertValues) throws SQLException {
    PreparedStatement stmt = con.prepareStatement(iQuery);
    stmt.setString(1, insertValues[0]);
    stmt.setString(2, insertValues[1]);
    stmt.setInt(3, parseInt(insertValues[2]));
    stmt.setString(4, insertValues[3]);
    stmt.setString(5, insertValues[4]);
    stmt.setString(6, insertValues[5]);
    stmt.executeUpdate();
  }

  /**
   * Inserts a product into database
   * @param iQuery The sql statement
   * @param insertValues Insert values
   * @throws SQLException
   */
  public void insertProduct(String iQuery, String[] insertValues) throws SQLException {
    PreparedStatement stmt = con.prepareStatement(iQuery);
    stmt.setString(1, insertValues[0]);
    stmt.setString(2, insertValues[1]);
    stmt.setString(3, insertValues[2]);
    stmt.executeUpdate();
  }

  /**
   * Inserts a test into the Testing database
   * @param productName The name of the product tested
   * @param userName The userName of the employee who tested the product
   * @throws SQLException
   */
  public void insertTest(String productName, String userName) throws SQLException {
    java.sql.Timestamp currentTimestamp =
        new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    String query = "SELECT ID FROM PRODUCT WHERE NAME = ?";
    PreparedStatement stmt = con.prepareStatement(query);
    stmt.setString(1, productName);
    rs = stmt.executeQuery();
    rs.next();
    int productID = rs.getInt("ID");
    String query2 = "INSERT INTO TESTING(TESTTIME,TESTERUSERNAME,PRODUCTID) VALUES(?,?,?)";
    PreparedStatement stmt2 = con.prepareStatement(query2);
    stmt2.setTimestamp(1, currentTimestamp);
    stmt2.setString(2, userName);
    stmt2.setInt(3, productID);
    stmt2.execute();
  }

  /**
   * Inserts a production from info from the GUI
   * @param iQuery The sql
   * @param insertValues The values to be inserted
   * @throws SQLException
   */
  public void insertProduction(String iQuery, String[] insertValues) throws SQLException {
    PreparedStatement stmt = con.prepareStatement(iQuery);
    java.sql.Timestamp currentTimestamp =
        new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    stmt.setTimestamp(1, currentTimestamp);
    stmt.setInt(2, parseInt(insertValues[0]));
    stmt.setInt(3, parseInt(insertValues[1]));
    stmt.setInt(4, parseInt(insertValues[2]));
    stmt.executeUpdate();
  }

  /**
   * Sets total made based off previous productions
   * @param product The product being made
   * @param madeNow The amount made now
   * @return The previous total made plus the amount made now
   */
  public int selectTotalMade(String product, int madeNow) {

    try {
      PreparedStatement stmt = con.prepareStatement("SELECT id FROM product WHERE NAME = ?");
      stmt.setString(1, product);
      rs = stmt.executeQuery();
      rs.next();
      int productID = rs.getInt(1);
      PreparedStatement stmt2 =
          con.prepareStatement("SELECT MAX(TOTALMADE) FROM PRODUCTION WHERE PRODUCTID = ?");
      stmt2.setInt(1, productID);
      rs = stmt2.executeQuery();
      rs.next();
      int totalMade = rs.getInt(1);
      totalMade += madeNow;
      return totalMade;
    } catch (SQLException e) {
      sqlExceptionHandler(e);
      return 0;
    }
  }

  /**
   * Sees if a username already exists
   * @param newUserName The username being checked
   * @return A boolean on whether the username already exists
   * @throws SQLException
   */
  public boolean checkUserName(String newUserName) throws SQLException {
    sqlStatement = "SELECT USERNAME FROM employee WHERE USERNAME = ?";
    PreparedStatement statement = con.prepareStatement(sqlStatement);
    statement.setString(1, newUserName);
    rs = statement.executeQuery();
    return rs.next();
  }

  /**
   * This method checks if a logged in user is a manager
   * @param currentUser The user's id
   * @return A boolean determining whether they are a manager
   * @throws SQLException
   */
  public boolean checkManager(int currentUser) throws SQLException {
    sqlStatement = "SELECT MANAGER FROM employee WHERE id = ?";
    PreparedStatement statement = con.prepareStatement(sqlStatement);
    statement.setInt(1, currentUser);
    rs = statement.executeQuery();
    rs.next();
    return rs.getBoolean(1);
  }

  /**
   * Gets the product name and returns the id
   * @param product product name
   * @return Returns the productID
   */
  public int selectProductID(String product) {
    try {
      PreparedStatement stmt = con.prepareStatement("SELECT id FROM product WHERE NAME = ?");
      stmt.setString(1,product);
      rs = stmt.executeQuery();
      rs.next();
      return rs.getInt(1);
    } catch (SQLException e) {
      sqlExceptionHandler(e);
      return 0;
    }
  }

  /**
   * Checks if the user's credentials are in the db
   * @param userName The entered username
   * @param password The entered password
   * @return A boolean on whether they are in the db
   * @throws SQLException
   */
  public boolean checkCredentials(String userName, String password) throws SQLException {
    String query = "SELECT * FROM EMPLOYEE WHERE USERNAME = ? AND PASSWORD = ?";
    PreparedStatement stmt = con.prepareStatement(query);
    stmt.setString(1, userName);
    stmt.setString(2, password);
    rs = stmt.executeQuery();
    return rs.next();
  }

  /**
   * Gets the username and returns the employee id
   * @param userName The username from the login
   * @return Retuns the ID for the employee
   * @throws SQLException
   */
  public int setCurrentUser(String userName) throws SQLException {
    String query = "SELECT ID FROM EMPLOYEE WHERE USERNAME = ?";
    PreparedStatement stmt = con.prepareStatement(query);
    stmt.setString(1, userName);
    rs = stmt.executeQuery();
    rs.next();
    return rs.getInt(1);
  }

  /**
   *
   */
  public void closeCon() {
    try {
      con.close();
    } catch (SQLException e) {
      sqlExceptionHandler(e);
    }
  }

  /**
   * Creates a list to load into a tableview, pretty complicated because it's getting data from 2 tables
   * @return Returns a list to load into the tableview
   */
  public List<ProductionData> getProductionInfo() {
    List<ProductionData> prodList = new ArrayList<>();
    try {
      String query = "SELECT * FROM PRODUCTION";
      PreparedStatement stmt = con.prepareStatement(query);
      rs = stmt.executeQuery();
      while (rs.next()) {
        int productionID = rs.getInt("PRODUCTIONID");
        Date madeOn = rs.getDate("MANUFACTUREDON");
        int numMade = rs.getInt("AMOUNTMADE");
        int totalMade = rs.getInt("TOTALMADE");
        int productID = rs.getInt("PRODUCTID");
        String[] productInfo = getProductInfo(productID);
        prodList.add(
            new ProductionData(
                productionID,
                madeOn,
                numMade,
                totalMade,
                productInfo[0],
                productInfo[1],
                productInfo[2]));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return prodList;
  }

  /**
   * Updates the table based on the user search
   * @param searchQuery The search query from the user
   * @return Returns a list to load into the tableview
   */
  public List<ProductionData> getSearchResults(String searchQuery) {
    List<ProductionData> prodList = new ArrayList<>();
    try {
      String query = "SELECT * FROM PRODUCT WHERE NAME = ? OR MANUFACTURER = ? OR TYPE =?";
      PreparedStatement stmt = con.prepareStatement(query);
      stmt.setString(1, searchQuery);
      stmt.setString(2, searchQuery);
      stmt.setString(3, searchQuery);
      rs = stmt.executeQuery();
      while (rs.next()) {
        int productID = rs.getInt("ID");
        String name = rs.getString("NAME");
        String manufacturer = rs.getString("MANUFACTURER");
        String type = rs.getString("TYPE");
        query = "SELECT * FROM PRODUCTION WHERE PRODUCTID = ?";
        ResultSet result;
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, productID);
        result = statement.executeQuery();
        while (result.next()) {
          int productionID = result.getInt("PRODUCTIONID");
          Date madeOn = result.getDate("MANUFACTUREDON");
          int numMade = result.getInt("AMOUNTMADE");
          int totalMade = result.getInt("TOTALMADE");
          prodList.add(
              new ProductionData(
                  productionID, madeOn, numMade, totalMade, name, manufacturer, type));
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return prodList;
  }

  /**
   * Used with getProductionInfo, since they are in separate tables
   * @param productID The product to get info on
   * @return Returns a string array with the info that will be necessary for ProductionData
   * @throws SQLException
   */
  public String[] getProductInfo(int productID) throws SQLException {
    String query = "SELECT * FROM PRODUCT WHERE ID = ?";
    PreparedStatement stmt = con.prepareStatement(query);
    stmt.setInt(1, productID);
    ResultSet results;
    results = stmt.executeQuery();
    String prodName;
    String type;
    String manufacturer;
    results.next();
    prodName = results.getString("NAME");
    manufacturer = results.getString("MANUFACTURER");
    type = results.getString("TYPE");
    return new String[] {prodName, manufacturer, type};
  }

  /**
   * Handles all sql exceptions
   * @param error The error that was caught
   */
  public void sqlExceptionHandler(SQLException error) {
    // add logging, could make into a wrapper function
    System.out.println("Standard Failure: " + error.getMessage());
  }
}
