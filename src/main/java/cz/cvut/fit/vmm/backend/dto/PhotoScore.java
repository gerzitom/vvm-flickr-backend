package cz.cvut.fit.vmm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PhotoScore {
  private Double geoDistance;
  private Double titleDistance;
  private Double authorDistance;
  private Double total;
}
