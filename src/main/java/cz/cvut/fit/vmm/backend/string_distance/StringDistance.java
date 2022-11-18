package cz.cvut.fit.vmm.backend.string_distance;

import cz.cvut.fit.vmm.backend.ScoreComputing;

public interface StringDistance {
  public Double compute(String str1, String str2);
}
