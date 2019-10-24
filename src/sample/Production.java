package sample;

import java.sql.SQLException;
import java.util.Date;

public class Production {
  private Date manufacturedOn = new Date();
  private DatabaseManager db = new DatabaseManager();

    public Production() throws SQLException{
    }

    public void insertProduction(String nextID, String amountMade, String totalMade) throws SQLException {
      if(nextID.equals("-99")){
        nextID = "1";
      }
      else{
        int newID = Integer.parseInt(nextID)+1;
        nextID = Integer.toString(newID);
      }
      String[] insertInfo = {nextID, amountMade, totalMade, "1"};
    db.insertProduction("INSERT INTO production VALUES(?,?,?,?,?)", insertInfo);
  }
}
