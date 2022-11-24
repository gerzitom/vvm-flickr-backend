package cz.cvut.fit.vmm.backend.score_computing_strategy;

import com.flickr4java.flickr.photos.GeoData;
import cz.cvut.fit.vmm.backend.dto.PhotoComputeScore;
import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoScore;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.dto.SearchGeoData;
import cz.cvut.fit.vmm.backend.geo_distance.GreatCircleDistance;
import cz.cvut.fit.vmm.backend.string_distance.LevenshteinDistance;


public class SimpleComputingStrategy implements ScoreComputingStrategy {

  private final GreatCircleDistance greatCircleDistance = new GreatCircleDistance();
  private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
  @Override
  public PhotoComputeScore computePhotoScore(PhotoReadDto photo, PhotoSearchDto search) {
    Double geoDistanceNorm = this.greatCircleDistance.computeNorm(fromGeoData(photo.getGeo()), search.getGeo());
    Double titleDistanceNorm = this.levenshteinDistance.computeNormalized(photo.getTitle(), search.getQuery());


    Double finalScore = search.getGeoScale() * geoDistanceNorm + search.getTitleScale() * titleDistanceNorm;
    Double scoreWithBonuses = applyBonuses(finalScore, search);

    PhotoScore photoScore = new PhotoScore(geoDistanceNorm, titleDistanceNorm, null);
    return new PhotoComputeScore(scoreWithBonuses, photoScore);
  }

  private Double applyBonuses(Double score, PhotoSearchDto search){
    Double factor = 1.0;
    if(search.isNameIncludesBonus()) factor *= 0.9;
    return score * factor;
  }

  private SearchGeoData fromGeoData(GeoData geoData){
    return new SearchGeoData(geoData.getLatitude(), geoData.getLongitude());
  }
}
