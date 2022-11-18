package cz.cvut.fit.vmm.backend.dto;

import com.flickr4java.flickr.photos.GeoData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoSearchDto {
  private String query;
  private Date dateFrom;
  private Date dateTo;
  private String[] tags;
  private SearchGeoData geo;
}
