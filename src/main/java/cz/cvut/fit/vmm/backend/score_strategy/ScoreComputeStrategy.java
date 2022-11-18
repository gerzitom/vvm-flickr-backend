package cz.cvut.fit.vmm.backend.score_strategy;

import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoScore;

public interface ScoreComputeStrategy {
  public PhotoScore computePhotoScore(PhotoReadDto photo);
}
