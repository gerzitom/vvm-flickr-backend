package cz.cvut.fit.vmm.backend.score_strategy;

import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoScore;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.geo_distance.EuclideanGeoDistance;
import cz.cvut.fit.vmm.backend.geo_distance.GeoDistance;
import cz.cvut.fit.vmm.backend.string_distance.LevenshteinDistance;
import cz.cvut.fit.vmm.backend.string_distance.StringDistance;

import java.util.Arrays;

public class BasicStrategy extends AbstractComputeStrategy implements ScoreComputeStrategy {

  private GeoDistance geoDistance;
  private StringDistance stringDistance;

  public BasicStrategy(PhotoSearchDto search) {
    super(search);
    geoDistance = new EuclideanGeoDistance(search);
    stringDistance = new LevenshteinDistance();
  }

  @Override
  public PhotoScore computePhotoScore(PhotoReadDto photo) {
    Double geoDistance = this.geoDistance.computeGeoDistance(photo);
    Double titleStringDistance = photoTitleDistance(photo);
    Double authorNameStringDistance = photoAuthorDistance(photo);
    Double total = geoDistance + titleStringDistance + authorNameStringDistance;
    PhotoScore photoScore = new PhotoScore(geoDistance, titleStringDistance, authorNameStringDistance, total);
    return photoScore;
  }


  private Double photoTitleDistance(PhotoReadDto photo) {
    Integer norm = Math.max(search.getQuery().length(), photo.getTitle().length());
    return this.stringDistance.compute(search.getQuery(), photo.getTitle()) / norm;
  }

  private Double photoAuthorDistance(PhotoReadDto photo) {
    Integer norm = Math.max(search.getQuery().length(), photo.getAuthor().length());
    return this.stringDistance.compute(search.getQuery(), photo.getAuthor()) / norm;
  }
}
