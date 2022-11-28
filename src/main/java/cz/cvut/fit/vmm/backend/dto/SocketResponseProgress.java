package cz.cvut.fit.vmm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SocketResponseProgress {
  private int totalPhotos;
  private double progress;
  private List<PhotoSortWrapper> payload;
}
