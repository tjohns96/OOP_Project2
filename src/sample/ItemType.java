package sample;

/**
 * An enum for the types of products there can be. Used in the product database
 */
public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIOMOBILE("AM"),
  VISUALMOBILE("VM");
  private String code;

  /**
   * Used to get enum name
   * @return Returns the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Constructor
   * @param newCode The code you want
   */
  ItemType(String newCode) {
    code = newCode;
  }
}
