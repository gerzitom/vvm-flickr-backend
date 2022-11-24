package cz.cvut.fit.vmm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class PhotoSortWrapper {
  private PhotoReadDto photo;
  private Double score;
  private PhotoScore scoreStats;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof PhotoSortWrapper that)) return false;
    return ((PhotoSortWrapper) o).getPhoto().getId().equals(this.getPhoto().getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(photo.getId());
  }
}
