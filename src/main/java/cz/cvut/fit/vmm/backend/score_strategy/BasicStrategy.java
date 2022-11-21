package cz.cvut.fit.vmm.backend.score_strategy;

import cz.cvut.fit.vmm.backend.dto.PhotoComputeScore;
import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoScore;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.geo_distance.EuclideanGeoDistance;
import cz.cvut.fit.vmm.backend.geo_distance.GeoDistance;
import cz.cvut.fit.vmm.backend.string_distance.LevenshteinDistance;
import cz.cvut.fit.vmm.backend.string_distance.StringDistance;

import java.util.Arrays;

public class BasicStrategy extends AbstractComputeStrategy implements ScoreComputeStrategy {


  public BasicStrategy(PhotoSearchDto search) {
    super(search);
    geoDistance = new EuclideanGeoDistance(search);
    stringDistance = new LevenshteinDistance();
  }

  @Override
  public PhotoComputeScore computePhotoScore(PhotoReadDto photo) {
    Double geoDistance = this.geoDistance.computeGeoDistance(photo);
    Double titleStringDistance = photoTitleDistance(photo);
    Double authorNameStringDistance = photoAuthorDistance(photo);
    Double roundedGeoDistance = Math.round(geoDistance * 1000.0) / 1000.0;

    Double geoScore = geoScore(geoDistance);
    Double titleScore = getTitleScore(titleStringDistance);
    Double authorUsernameScore = getAuthorUsernameScore(authorNameStringDistance);
    Double total = geoScore + titleScore + authorUsernameScore + getTitleIncludesQueryBonus(photo);
    PhotoScore photoScore = new PhotoScore(roundedGeoDistance, titleScore, authorUsernameScore);
    return new PhotoComputeScore(total, photoScore);
  }

  private Double geoScore(Double geoDistance){
    Double roundedGeoDistance = Math.round(geoDistance * 1000.0) / 1000.0;
    if(roundedGeoDistance == 0) return 1000D;
    return 1/roundedGeoDistance;
  }

  private Double getTitleScore(Double titleDistance) {
    if(titleDistance == 0) return 50D;
    return (10/titleDistance);
  }

  private Double getAuthorUsernameScore(Double nameDistance) {
    if(nameDistance == 0) return 50D;
    return (5/nameDistance) / 3;
  }

  private Double getTitleIncludesQueryBonus(PhotoReadDto photo){
    return titleIncludesQuery(photo.getTitle()) ? 50D : 0D;
  }
  private boolean titleIncludesQuery(String photoTitle){
    return photoTitle.contains(this.search.getQuery());
  }
}
