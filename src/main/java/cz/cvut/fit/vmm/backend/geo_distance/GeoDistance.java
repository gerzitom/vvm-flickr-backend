package cz.cvut.fit.vmm.backend.geo_distance;

import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;

public interface GeoDistance {
  public Double computeGeoDistance(PhotoReadDto photoReadDto);
}
