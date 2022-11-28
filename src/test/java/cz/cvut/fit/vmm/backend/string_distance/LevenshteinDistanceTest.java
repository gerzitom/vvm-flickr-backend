package cz.cvut.fit.vmm.backend.string_distance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class LevenshteinDistanceTest {

  protected LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

  @Test
  public void testDistance(){
//    testWords("Mustang", "Form Mustang",0.4166666666666667 );
//    testWords("Mustang", "Mustang",0D );
//    testWords("Mustang", "adasdadadsasdasddfasgwerjowquhilkbsnvm,xzcnlaHSSADFASDFWRGESDFGBVMNBN,NKJRTWERQTWEFGDJYUOUYJDFGSAFSDIFKSAD,NFSADFPASJDFNJKSANDFKALSDNF",0.9776119402985075 );
//    testWords("Mustang", "Yellow",1.0 );
  }

  private void testWords(String str1, String str2, Double targetNorm){
    Double normalizedDistance = this.levenshteinDistance.computeNormalized(str1, str2);
    assertEquals(normalizedDistance, targetNorm);
    printStats(normalizedDistance);
  }

  private void printStats(Double norm){
    System.out.println("Norm: " + norm);
    System.out.println("------------------------------------");
  }

}
