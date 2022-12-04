package cz.cvut.fit.vmm.backend.distance;

import java.util.Arrays;

public class LevenshteinDistance {

  public Double computeNormalized(String str1, String str2) {
    double normingValue = (double) Math.max(str1.length(), str2.length());
    return 1 - (this.compute(str1, str2) / normingValue);
  }
  public Double compute(String str1, String str2) {
    str1 = str1.toLowerCase();
    str2 = str2.toLowerCase();
    int[][] dp = new int[str1.length() + 1][str2.length() + 1];

    for (int i = 0; i <= str1.length(); i++) {
      for (int j = 0; j <= str2.length(); j++) {
        if (i == 0) {
          dp[i][j] = j;
        }
        else if (j == 0) {
          dp[i][j] = i;
        }
        else {
          dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(str1.charAt(i - 1), str2.charAt(j - 1)),
                  dp[i - 1][j] + 1,
                  dp[i][j - 1] + 1);
        }
      }
    }

    return (double) dp[str1.length()][str2.length()];
  }

  public int costOfSubstitution(char a, char b) {
    return a == b ? 0 : 1;
  }

  public int min(int... numbers) {
    return Arrays.stream(numbers)
            .min().orElse(Integer.MAX_VALUE);
  }
}
