package sample;

/**
 * An interface for screens
 */
public interface ScreenSpec {
  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
