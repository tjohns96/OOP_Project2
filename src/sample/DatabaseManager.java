package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Integer.parseInt;

public class DatabaseManager {
  private String sqlStatement;
  private Connection con = null;
  private ResultSet rs = null;

  public DatabaseManager() throws SQLException {
    con = DriverManager.getConnection("jdbc:h2:./res/ProductDB");
  }

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

  public void insertProduct(String iQuery, String[] insertValues) throws SQLException {
    PreparedStatement stmt = con.prepareStatement(iQuery);
    stmt.setString(1, insertValues[0]);
    stmt.setString(2, insertValues[1]);
    stmt.setString(3, insertValues[2]);
    stmt.executeUpdate();
  }

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

  public boolean checkUserName(String newUserName) throws SQLException {
    sqlStatement = "SELECT USERNAME FROM employee WHERE USERNAME = ?";
    PreparedStatement statement = con.prepareStatement(sqlStatement);
    statement.setString(1, newUserName);
    rs = statement.executeQuery();
    return rs.next();
  }

  public boolean checkManager(int currentUser) throws SQLException {
    sqlStatement = "SELECT MANAGER FROM employee WHERE id = ?";
    PreparedStatement statement = con.prepareStatement(sqlStatement);
    statement.setInt(1, currentUser);
    rs = statement.executeQuery();
    rs.next();
    return rs.getBoolean(1);
  }

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

  public boolean checkCredentials(String userName, String password) throws SQLException {
    String query = "SELECT * FROM EMPLOYEE WHERE USERNAME = ? AND PASSWORD = ?";
    PreparedStatement stmt = con.prepareStatement(query);
    stmt.setString(1, userName);
    stmt.setString(2, password);
    rs = stmt.executeQuery();
    return rs.next();
  }

  public int setCurrentUser(String userName) throws SQLException {
    String query = "SELECT ID FROM EMPLOYEE WHERE USERNAME = ?";
    PreparedStatement stmt = con.prepareStatement(query);
    stmt.setString(1, userName);
    rs = stmt.executeQuery();
    rs.next();
    return rs.getInt(1);
  }

  public void closeCon() {
    try {
      con.close();
    } catch (SQLException e) {
      sqlExceptionHandler(e);
    }
  }

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

  public void sqlExceptionHandler(SQLException error) {
    // add logging, could make into a wrapper function
    System.out.println("Standard Failure: " + error.getMessage());
  }
}
