package sample;

/**
 * Abstract class for all products
 */
abstract class Product implements Item {
  private int id;
  private ItemType type;
  private String manufacturer;
  private String name;

  /**
   * A constructor to set the name
   * @param newName Sets the name of the product
   */
  public Product(String newName) {
    name = newName;
  }

  /**
   * default no argument constructor does nothing
   */
  public Product() {
    id = 0;
    type = null;
    manufacturer = null;
    name = null;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setName(String newName) {
    name = newName;
  }

  /**
   * Gives an enum values based on the String argument
   * @param type A string with what type you want
   */
  public void setType(String type) {
    switch (type) {
      case "Visual":
        this.type = ItemType.VISUAL;
        break;
      case "Audio":
        this.type = ItemType.AUDIO;
        break;
      case "VisualMobile":
        this.type = ItemType.VISUALMOBILE;
        break;
      case "AudioMobile":
        this.type = ItemType.AUDIOMOBILE;
        break;
      default:
        System.out.println("You have entered the type incorrectly.");
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setManufacturer(String newManufacturer) {
    manufacturer = newManufacturer;
  }

  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  @Override
  public String toString() {
    return ("Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type.getCode());
  }
}
