package cz.cvut.fit.vmm.backend.geo_distance;

import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;

public class EuclideanGeoDistance implements GeoDistance {

  @Override
  public Double computeGeoDistance(PhotoReadDto photo, PhotoSearchDto search) {
    double latDistance = (double)(photo.getGeo().getLatitude() - search.getGeo().getLat());
    double lonDistance = (double)(photo.getGeo().getLongitude() - search.getGeo().getLng());
    return Math.sqrt(Math.pow(latDistance, 2) + Math.pow(lonDistance, 2));
  }


}
