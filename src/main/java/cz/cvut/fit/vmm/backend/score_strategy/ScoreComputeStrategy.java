package cz.cvut.fit.vmm.backend.score_strategy;

import cz.cvut.fit.vmm.backend.dto.PhotoComputeScore;
import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoScore;

public interface ScoreComputeStrategy {
  public PhotoComputeScore computePhotoScore(PhotoReadDto photo);
}
