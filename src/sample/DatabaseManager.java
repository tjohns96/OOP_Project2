package sample;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    stmt.setString(4,insertValues[3]);
    stmt.setString(5,insertValues[4]);
    stmt.executeUpdate();
  }

  public void insertProduct(String iQuery, String[] insertValues) throws SQLException {
    PreparedStatement stmt = con.prepareStatement(iQuery);
    stmt.setInt(1, parseInt(insertValues[0]));
    stmt.setString(2, insertValues[1]);
    stmt.setString(3, insertValues[2]);
    stmt.setString(4, insertValues[3]);
    stmt.executeUpdate();
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
      PreparedStatement stmt2 = con.prepareStatement("SELECT MAX(TOTALMADE) FROM PRODUCTION WHERE PRODUCTID = ?");
      stmt2.setInt(1,productID);
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
     boolean result = rs.next();
     return result;
  }

  public boolean checkManager(int currentUser) throws SQLException {
    sqlStatement = "SELECT MANAGER FROM employee WHERE id = ?";
    PreparedStatement statement = con.prepareStatement(sqlStatement);
    statement.setInt(1, currentUser);
    rs = statement.executeQuery();
    rs.next();
    boolean result = rs.getBoolean(1);
    return result;
  }

  public int selectProductID(String product) {
    try {
      PreparedStatement stmt =
          con.prepareStatement("SELECT id FROM product WHERE NAME = '" + product + "';");
      rs = stmt.executeQuery();
      rs.next();
      int productID = rs.getInt(1);
      return productID;
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
    if (rs.next()) {
      return true;
    } else {
      return false;
    }
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

  public void sqlExceptionHandler(SQLException error) {
    // add logging, could make into a wrapper function
    System.out.println("Standard Failure: " + error.getMessage());
  }
}
