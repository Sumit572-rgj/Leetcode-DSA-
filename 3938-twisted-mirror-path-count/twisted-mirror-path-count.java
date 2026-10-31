class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int mod = 1000000007;

        int[][] right = new int[m][n];
        int[][] down = new int[m][n];

        for (int i = 0; i < m; i++) {
            java.util.Arrays.fill(right[i], -1);
            java.util.Arrays.fill(down[i], -1);
        }

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                if (grid[i][j] == 1) {

                  
                    if (i + 1 < m) {
                        if (grid[i + 1][j] == 0) {
                            right[i][j] = (i + 1) * n + j;
                        } else {
                            right[i][j] = down[i + 1][j];
                        }
                    }

                   
                    if (j + 1 < n) {
                        if (grid[i][j + 1] == 0) {
                            down[i][j] = i * n + j + 1;
                        } else {
                            down[i][j] = right[i][j + 1];
                        }
                    }
                }
            }
        }

        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (dp[i][j] == 0) {
                    continue;
                }

                long ways = dp[i][j];

               
                if (j + 1 < n) {
                    int dest;

                    if (grid[i][j + 1] == 0) {
                        dest = i * n + j + 1;
                    } else {
                        dest = right[i][j + 1];
                    }

                    if (dest != -1) {
                        dp[dest / n][dest % n] =
                            (dp[dest / n][dest % n] + ways) % mod;
                    }
                }

              
                if (i + 1 < m) {
                    int dest;

                    if (grid[i + 1][j] == 0) {
                        dest = (i + 1) * n + j;
                    } else {
                        dest = down[i + 1][j];
                    }

                    if (dest != -1) {
                        dp[dest / n][dest % n] =
                            (dp[dest / n][dest % n] + ways) % mod;
                    }
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }
}