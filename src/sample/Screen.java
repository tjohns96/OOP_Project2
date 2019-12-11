package sample;

public class Screen implements ScreenSpec {
  private String resolution;
  private int refreshRate;

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  public void setRefreshRate(int refreshRate) {
    this.refreshRate = refreshRate;
  }

  public void setResponseTime(int responseTime) {
    this.responseTime = responseTime;
  }

  private int responseTime;

  public Screen() {
    resolution = "0p";
    refreshRate = 0;
  }

  public Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  @Override
  public String getResolution() {
    return resolution;
  }

  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  @Override
  public int getResponseTime() {
    return responseTime;
  }

  @Override
  public String toString() {
    return ("Resolution: "
        + resolution
        + "\nRefresh Rate (hz): "
        + refreshRate
        + "\nResponse Time (ms): "
        + responseTime);
  }
}
