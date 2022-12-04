package cz.cvut.fit.vmm.backend.dto;

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
  private Date date;
  private String[] tags;
  private SearchGeoData geo;
  private int geoScale;
  private int titleScale;
  private int dateScale;
  private int pagesToFetch;
  private boolean nameIncludesBonus;
}
