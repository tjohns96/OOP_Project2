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
    con =
        DriverManager.getConnection(
            "jdbc:h2:./res/ProductDB");
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

  public void insertProduction(String iQuery, String[] insertValues) throws SQLException, ParseException {
    PreparedStatement stmt = con.prepareStatement(iQuery);
    java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    stmt.setInt(1, parseInt(insertValues[0]));
    stmt.setTimestamp(2, currentTimestamp);
    stmt.setInt(3, parseInt(insertValues[1]));
    stmt.setInt(4, parseInt(insertValues[2]));
    stmt.executeUpdate();
  }


  public void selectAll() {
    ResultSet rs = null;

    try {
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery("SELECT * FROM employee;");

      while (rs.next()) {
        System.out.printf("uID = %d%n", rs.getInt("uid"));
        System.out.printf("Name = %s%n", rs.getString("name"));
      }

    } catch (SQLException e) {
      sqlExceptionHandler(e);
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
