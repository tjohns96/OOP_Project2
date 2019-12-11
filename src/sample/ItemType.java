package sample;

public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIOMOBILE("AM"),
  VISUALMOBILE("VM");
  private String code;

  public String getCode() {
    return code;
  }

  ItemType(String newCode) {
    code = newCode;
  }
}
