package sample;

import java.util.Date;

/**
 * This class is to model the tableview that shows production
 */
public class ProductionData {


  private int productionID;
  private Date manufacturedOn;
  private int numMade;
  private int totalMade;
  private String prodName;
  private String manufacturer;
  private String type;
  public int getProductionID() {
    return productionID;
  }

  public void setProductionID(int productionID) {
    this.productionID = productionID;
  }

  public Date getManufacturedOn() {
    return manufacturedOn;
  }

  public void setManufacturedOn(Date manufacturedOn) {
    this.manufacturedOn = manufacturedOn;
  }

  public int getNumMade() {
    return numMade;
  }

  public void setNumMade(int numMade) {
    this.numMade = numMade;
  }

  public int getTotalMade() {
    return totalMade;
  }

  public void setTotalMade(int totalMade) {
    this.totalMade = totalMade;
  }

  public String getProdName() {
    return prodName;
  }

  public void setProdName(String prodName) {
    this.prodName = prodName;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  /**
   * A constructor with self explanatory parameters
   * @param productionID
   * @param manufacturedOn
   * @param numMade
   * @param totalMade
   * @param prodName
   * @param manufacturer
   * @param type
   */
  public ProductionData(
      int productionID,
      Date manufacturedOn,
      int numMade,
      int totalMade,
      String prodName,
      String manufacturer,
      String type) {
    this.productionID = productionID;
    this.numMade = numMade;
    this.totalMade = totalMade;
    this.manufacturedOn = manufacturedOn;
    this.prodName = prodName;
    this.type = type;
    this.manufacturer = manufacturer;
  }

  /**
   * This allows me to print the results, making sure it works
   * @return A String with all fields in space separated strings
   */
  @Override
  public String toString() {
    return (productionID
        + " "
        + manufacturedOn
        + " "
        + numMade
        + " "
        + totalMade
        + " "
        + prodName
        + " "
        + manufacturer
        + " "
        + type);
  }
}
