class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();

        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = n; j > 0; j--) {
                if (s.charAt(i) == t.charAt(j - 1)) {
                    dp[j] = dp[j] + dp[j - 1];
                }
            }
        }

        return dp[n];
    }
}