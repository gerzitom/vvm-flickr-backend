package cz.cvut.fit.vmm.backend;

import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;

public interface ScoreComputing {
  public Double compute(PhotoReadDto photo);
}
