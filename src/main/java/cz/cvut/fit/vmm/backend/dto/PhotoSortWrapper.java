package cz.cvut.fit.vmm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PhotoSortWrapper {
  private PhotoReadDto photo;
  private PhotoScore score;
}
