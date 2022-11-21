package cz.cvut.fit.vmm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoComputeScore {
  private Double score;
  private PhotoScore scoreStats;
}
