package cz.cvut.fit.vmm.backend.score_strategy;

import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.geo_distance.GeoDistance;
import cz.cvut.fit.vmm.backend.string_distance.StringDistance;

abstract public class AbstractComputeStrategy {
  protected PhotoSearchDto search;
  protected GeoDistance geoDistance;
  protected StringDistance stringDistance;

  public AbstractComputeStrategy(PhotoSearchDto search) {
    this.search = search;
  }

  protected Double photoTitleDistance(PhotoReadDto photo) {
    Integer norm = Math.max(search.getQuery().length(), photo.getTitle().length());
    return this.stringDistance.compute(search.getQuery(), photo.getTitle()) / norm;
  }

  protected Double photoAuthorDistance(PhotoReadDto photo) {
    Integer norm = Math.max(search.getQuery().length(), photo.getAuthor().getUsername().length());
    return this.stringDistance.compute(search.getQuery(), photo.getAuthor().getUsername()) / norm;
  }
}
