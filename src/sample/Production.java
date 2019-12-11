package sample;

import java.sql.SQLException;

public class Production {
  private DatabaseManager db = new DatabaseManager();

  public Production() throws SQLException {}

  public void insertProduction(String amountMade, String totalMade, String productID)
      throws SQLException {

    String[] insertInfo = {amountMade, totalMade, productID};
    db.insertProduction(
        "INSERT INTO production(MANUFACTUREDON, AMOUNTMADE, TOTALMADE, PRODUCTID) VALUES(?,?,?,?)",
        insertInfo);
  }
}
