package sample;

/**
 * The class that represents an AudioPlayer
 */
public class AudioPlayer extends Product implements MultimediaControl {
  private String audioSpecification;
  private ItemType mediaType;

  /**
   * The constructor
   * @param newName To set the AudioPlayer name
   * @param newManufacturer To set the AudioPlayer manufacturer
   * @param audioSpecification To set the AudioPlayer audioSpecification
   */
  public AudioPlayer(String newName, String newManufacturer, String audioSpecification) {
    super(newName);
    super.setManufacturer(newManufacturer);
    this.audioSpecification = audioSpecification;
    mediaType = ItemType.AUDIO;
    super.setType("Audio");
  }

  /**
   * Simulates playing
   */
  @Override
  public void play() {
    System.out.println("Playing...");
  }

  /**
   * Simulates the end of playing
   */
  @Override
  public void stop() {
    System.out.println("Stopping...");
  }

  /**
   * Simulates rewinding
   */
  @Override
  public void previous() {
    System.out.println("Playing previous track.");
  }

  /**
   * Simulates the next track
   */
  @Override
  public void next() {
    System.out.println("Playing next track.");
  }

  /**
   * Allows the object to be printed
   * @return A string that prints the info
   */
  @Override
  public String toString() {
    return (super.toString()
        + "\nAudio Spec: "
        + audioSpecification
        + "\nMedia type: "
        + mediaType);
  }
}
