package sample;

/**
 * A class to simulate a movieplayer unnecessary besides the assignment
 */
public class MoviePlayer extends Product implements MultimediaControl {
  private Screen screen = new Screen();
  private MonitorType monitorType;

  /**
   * A constructor for a movie player
   * @param resolution Sets res
   * @param refreshRate Sets hz
   * @param responseTime Sets ms
   * @param mType Sets monitor type, from the enum MonitorType
   */
  public MoviePlayer(String resolution, int refreshRate, int responseTime, MonitorType mType) {
    screen.setResolution(resolution);
    screen.setRefreshRate(refreshRate);
    screen.setResponseTime(responseTime);
    monitorType = mType;
  }

  public void setMonitorType(MonitorType mType) {
    monitorType = mType;
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
  public String toString() {
    return (screen.toString() + "\nMonitor type: " + monitorType);
  }
}
