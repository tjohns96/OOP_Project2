package sample;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Production {
  private Date manufacturedOn = new Date();
  private DatabaseManager db = new DatabaseManager();

    public Production() throws SQLException{
    }

    public void insertProduction(String nextID, String amountMade) throws SQLException {
      if(nextID.equals("-99")){
        nextID = "1";
      }
      else{
        int newID = Integer.parseInt(nextID)+1;
        nextID = Integer.toString(newID);
      }
      String[] insertInfo = {nextID, amountMade, "2", "2"};
    db.insertProduction("INSERT INTO production VALUES(?,?,?,?,?)", insertInfo);
    db.closeCon();
  }
}
