package cz.cvut.fit.vmm.backend.geo_distance;

import cz.cvut.fit.vmm.backend.dto.SearchGeoData;

public class GreatCircleDistance {
  private final static double MAX_DEGREES = 180;

  /**
   * Computes great circle distance of locations.
   * Norms it to math search criteria.
   * The greater number the close are locations.
   * @param location1
   * @param location2
   * @return number from interval <0, 1>
   */
  public Double computeNorm (SearchGeoData location1, SearchGeoData location2) {
    Double angle = this.computeAngle(location1, location2);
    return 1 - (angle / MAX_DEGREES);
  }
  public Double computeAngle(SearchGeoData location1, SearchGeoData location2) {
    RadianLocation radianLocation1 = new RadianLocation(location1.getLat(), location1.getLng());
    RadianLocation radianLocation2 = new RadianLocation(location2.getLat(), location2.getLng());

    double angle = Math.acos(
            Math.sin(radianLocation1.lat) * Math.sin(radianLocation2.lat)
            + Math.cos(radianLocation1.lat) * Math.cos(radianLocation2.lat) * Math.cos(radianLocation1.lng - radianLocation2.lng)
    );

    double angleInDegrees = Math.toDegrees(angle);
    return angleInDegrees;
  }

  private class RadianLocation {
    public double lat;
    public double lng;

    public RadianLocation(float lat, float lng) {
      this.lat = Math.toRadians(lat);
      this.lng = Math.toRadians(lng);
    }
  }
}
