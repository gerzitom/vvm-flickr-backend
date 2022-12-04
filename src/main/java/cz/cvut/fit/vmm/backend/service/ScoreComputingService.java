package cz.cvut.fit.vmm.backend.service;

import com.flickr4java.flickr.photos.GeoData;
import cz.cvut.fit.vmm.backend.distance.DateDistance;
import cz.cvut.fit.vmm.backend.distance.GreatCircleDistance;
import cz.cvut.fit.vmm.backend.distance.LevenshteinDistance;
import cz.cvut.fit.vmm.backend.dto.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ScoreComputingService {

  private final GreatCircleDistance greatCircleDistance = new GreatCircleDistance();
  private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
  private final DateDistance dateDistance = new DateDistance();
  public PhotoComputeScore computePhotoScore(PhotoReadDto photo, PhotoSearchDto search) {
    Double geoDistanceNorm = this.greatCircleDistance.computeNorm(fromGeoData(photo.getGeo()), search.getGeo());
    Double titleDistanceNorm = this.levenshteinDistance.computeNormalized(photo.getTitle(), search.getQuery());
    Double dateDistanceNorm = this.dateDistance.computeNormalized(photo.getDatePosted(), search.getDate());

    Double geoScore = search.getGeoScale() * geoDistanceNorm;
    Double titleScore = search.getTitleScale() * titleDistanceNorm;
    Double dateScore = search.getDateScale() * dateDistanceNorm;

    Double finalScore = geoScore + titleScore + dateScore;
    Double scoreWithBonuses = applyBonuses(finalScore, search);

    PhotoScore photoScore = new PhotoScore(geoDistanceNorm, titleDistanceNorm, dateDistanceNorm);
    return new PhotoComputeScore(scoreWithBonuses, photoScore);
  }

  private Double applyBonuses(Double score, PhotoSearchDto search){
    Double factor = 1.0;
    if(search.isNameIncludesBonus()) factor *= 1.05;
    return score * factor;
  }

  private SearchGeoData fromGeoData(GeoData geoData){
    return new SearchGeoData(geoData.getLatitude(), geoData.getLongitude());
  }
}
