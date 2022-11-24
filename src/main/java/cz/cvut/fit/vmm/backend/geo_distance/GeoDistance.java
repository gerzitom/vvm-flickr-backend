package cz.cvut.fit.vmm.backend.geo_distance;

import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;

public interface GeoDistance {
  public Double computeGeoDistance(PhotoReadDto photoReadDto, PhotoSearchDto search);
}
