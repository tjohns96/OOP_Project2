package sample;

import java.sql.SQLException;

public class EmployeeInfo {
    private StringBuilder name;
    private String code;
    private int suffixNum;
    private DatabaseManager db = new DatabaseManager();

    public EmployeeInfo() throws SQLException {
    }

    public String getCode(){
        return code;
    }
    public StringBuilder getName(){
        return name;
    }
    private void setName(){

    }
    public String createUserName(String fName, String lName) throws SQLException {
        suffixNum = 0;
        StringBuilder newUserName = new StringBuilder(lName);
        newUserName.insert(0,fName.substring(0,1));
        int counter = 0;
        while(db.checkUserName(newUserName.toString())){
            if(counter!=0){
                newUserName.delete(newUserName.length()-1,newUserName.length());
            }
            counter++;
            newUserName = newUserName.append(suffixNum);
            suffixNum++;
        }
        return newUserName.toString();
    }
    private String inputName(){
        return code;
    }
    private boolean checkName(StringBuilder name){
        return true;
    }
}
