package sample;

/**
 * An interface for products
 */
public interface Item {
  int getId();

  void setName(String newName);

  String getName();

  void setManufacturer(String newManufacturer);

  String getManufacturer();
}
