package cz.cvut.fit.vmm.backend.score_strategy;

import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;

abstract public class AbstractComputeStrategy {
  protected PhotoSearchDto search;

  public AbstractComputeStrategy(PhotoSearchDto search) {
    this.search = search;
  }
}
