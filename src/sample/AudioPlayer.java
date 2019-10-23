package sample;

public class AudioPlayer extends Product implements MultimediaControl {
  private String audioSpecification;
  private ItemType mediaType;

  public AudioPlayer(String newName, String newManufacturer, String audioSpecification) {
    super(newName);
    super.setManufacturer(newManufacturer);
    this.audioSpecification = audioSpecification;
    mediaType = ItemType.AUDIO;
  }

  @Override
  public void play() {
    System.out.println("Playing...");
  }

  @Override
  public void stop() {
    System.out.println("Stopping...");
  }

  @Override
  public void previous() {
    System.out.println("Playing previous track.");
  }

  @Override
  public void next() {
    System.out.println("Playing next track.");
  }
  @Override
    public String toString(){
      return(super.toString()+ "\nAudio Spec: " + audioSpecification + "\nMedia type: " + mediaType);

  }
}
