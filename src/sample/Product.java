package sample;

abstract class Product implements Item {
  private int id;
  private ItemType type;
  private String manufacturer;
  private String name;

  public Product(String newName) {
    name = newName;
  }

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
