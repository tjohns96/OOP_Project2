package sample;

import java.sql.*;

public class DatabaseManager {

  private Connection con = null;

  public DatabaseManager() throws SQLException {
    con =
        DriverManager.getConnection(
            "jdbc:h2:./res/ProductDB");
  }

  public void insertEmployee(String iQuery, String[] insertValues) throws SQLException {
    PreparedStatement stmt = con.prepareStatement(iQuery);
    stmt.setInt(1, Integer.parseInt(insertValues[0]));
    stmt.setString(2, insertValues[1]);
    stmt.setInt(3,Integer.parseInt(insertValues[2]));
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
