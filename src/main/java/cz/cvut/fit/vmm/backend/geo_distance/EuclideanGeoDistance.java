package cz.cvut.fit.vmm.backend.geo_distance;

import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.score_strategy.AbstractComputeStrategy;

public class EuclideanGeoDistance extends AbstractComputeStrategy implements GeoDistance {

  public EuclideanGeoDistance(PhotoSearchDto search) {
    super(search);
  }

  @Override
  public Double computeGeoDistance(PhotoReadDto photo) {
    double latDistance = (double)(photo.getGeo().getLatitude() - this.search.getGeo().getLat());
    double lonDistance = (double)(photo.getGeo().getLongitude() - this.search.getGeo().getLng());
    return Math.sqrt(Math.pow(latDistance, 2) + Math.pow(lonDistance, 2));
  }
}
