package cz.cvut.fit.vmm.backend.score_computing_strategy;

import cz.cvut.fit.vmm.backend.dto.PhotoComputeScore;
import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;

public interface ScoreComputingStrategy {
  public PhotoComputeScore computePhotoScore(PhotoReadDto photo, PhotoSearchDto search);
}
