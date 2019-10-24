package sample;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class DatabaseManager {

  private Connection con = null;

  public DatabaseManager() throws SQLException {
    con = DriverManager.getConnection("jdbc:h2:./res/ProductDB");
  }

  public void insertEmployee(String iQuery, String[] insertValues) throws SQLException {
    PreparedStatement stmt = con.prepareStatement(iQuery);
    stmt.setInt(1, parseInt(insertValues[0]));
    stmt.setString(2, insertValues[1]);
    stmt.setString(3, insertValues[2]);
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
    stmt.setInt(1, parseInt(insertValues[0]));
    stmt.setTimestamp(2, currentTimestamp);
    stmt.setInt(3, parseInt(insertValues[1]));
    stmt.setInt(4, parseInt(insertValues[2]));
    stmt.setInt(5, parseInt(insertValues[3]));
    stmt.executeUpdate();
  }



  public int selectProductionID() {
    ResultSet rs = null;
    try {
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT MAX(productionID) FROM production;");
      rs.next();
      int result = rs.getInt(1);
      return result;
    } catch (SQLException e) {
      sqlExceptionHandler(e);
      return -99;
    }
  }

  public int selectTotalMade(String product, int madeNow) {
    ResultSet rs = null;

    try {
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT id FROM product WHERE NAME = '" + product + "';");
      rs.next();
      int productID = rs.getInt(1);
      rs = stmt.executeQuery("SELECT MAX(totalMade) FROM production WHERE PRODUCTID = " + productID + ";");
      rs.next();
      int totalMade = rs.getInt(1);
      totalMade += madeNow;
      return totalMade;
    } catch (SQLException e) {
      sqlExceptionHandler(e);
      return 0;
    }
  }

  public int selectProductID(String product) {
    ResultSet rs = null;

    try {
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT id FROM product WHERE NAME = '" + product + "';");
      rs.next();
      int productID = rs.getInt(1);
      return productID;
    } catch (SQLException e) {
      sqlExceptionHandler(e);
      return 0;
    }
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
