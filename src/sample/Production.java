package sample;

import java.sql.SQLException;

/**
 * A class where productions can be recorded
 */
public class Production {
  private DatabaseManager db = new DatabaseManager();

  /**
   * no argument constructor
   * @throws SQLException
   */
  public Production() throws SQLException {}

  /**
   * The method to insert production
   * @param amountMade The amount to produce
   * @param totalMade The total amount made
   * @param productID The id of the product you want to make
   * @throws SQLException
   */
  public void insertProduction(String amountMade, String totalMade, String productID)
      throws SQLException {

    String[] insertInfo = {amountMade, totalMade, productID};
    db.insertProduction(
        "INSERT INTO production(MANUFACTUREDON, AMOUNTMADE, TOTALMADE, PRODUCTID) VALUES(?,?,?,?)",
        insertInfo);
  }
}
