class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            boolean ok = true;

            for (int j = 0; j < n; j++) {
                if (nums[(i + j) % n] != j) {
                    ok = false;
                    break;
                }
            }

            if (ok) {
                ans = Math.min(ans, Math.min(i, n - i + 2));
            }

            ok = true;

            for (int j = 0; j < n; j++) {
                if (nums[(i + j) % n] != n - 1 - j) {
                    ok = false;
                    break;
                }
            }

            if (ok) {
                ans = Math.min(ans, Math.min(i + 1, n - i + 1));
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}