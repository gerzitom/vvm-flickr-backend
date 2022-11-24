package cz.cvut.fit.vmm.backend.geo_distance;

import cz.cvut.fit.vmm.backend.dto.SearchGeoData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class GreatCircleDistanceTest {

  @Test
  public void testGreatCircleDistance(){
    doGCDNorm(48.87F, -2.33F, 30.27F, 97.74F, 0.40955006405655825 );
    doGCDNorm(59.9F, -30.3F, 37.8F, 122.4F, 0.4429971890111369 );
    doGCDNorm(36.12F, -86.67F, 33.94F, -118.4F, 0.14421345353679765 );

    System.out.println("poles");
    doGCDNorm(90F, 0F, -90F, -0F, 1.0 );
  }

  private void doGCDNorm(float lat1, float lng1, float lat2, float lng2, double targetNorm){
    GreatCircleDistance greatCircleDistance = new GreatCircleDistance();
    SearchGeoData location1 = new SearchGeoData(lat1,lng1);
    SearchGeoData location2 = new SearchGeoData(lat2, lng2);
    Double norm = greatCircleDistance.computeNorm(location1, location2);
    assertTrue(norm >= 0);
    assertTrue(norm <= 1);
    assertEquals(norm, targetNorm);
    printStats(norm);

  }

  private void printStats(Double angle){
    System.out.println("Angle: " + angle);
    System.out.println("Normed: " + (angle / 180));
    System.out.println("Miles: " + angle * 60);
    System.out.println("----------------------------------------------------------------");
  }

}
