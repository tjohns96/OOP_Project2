package sample;

import java.sql.SQLException;

/**
 * Sets the employee's username to be unique, plus other stuff for the assignment
 */
public class EmployeeInfo {
  private StringBuilder name;
  private String code;
  private String deptID;
  private DatabaseManager db = new DatabaseManager();

  public EmployeeInfo() throws SQLException {}

  public String getCode() {
    return code;
  }

  public String getDeptId() {
    return "Production";
  }

  private void setDeptId(String deptID) {
    this.deptID = deptID;
  }

  private String getId() {
    return deptID;
  }

  private boolean validId(String id) {
    return id.equals("Production");
  }

  public StringBuilder getName() {
    return name;
  }

  /**
   * The only actual method in this class. Makes the username unique by incrementing a suffix until it is unique
   * @param fName The employee first name
   * @param lName The employee last name
   * @return The new user name in the form of the first initial+last name + suffix
   * @throws SQLException
   */
  public String createUserName(String fName, String lName) throws SQLException {
    int suffixNum = 0;
    StringBuilder newUserName = new StringBuilder(lName);
    newUserName.insert(0, fName.substring(0, 1));
    int counter = 0;
    while (db.checkUserName(newUserName.toString())) {
      if (counter != 0) {
        newUserName.delete(newUserName.length() - 1, newUserName.length());
      }
      counter++;
      newUserName = newUserName.append(suffixNum);
      suffixNum++;
    }
    return newUserName.toString();
  }

  private String inputName() {
    return code;
  }

  private boolean checkName(StringBuilder name) {
    return true;
  }
}
