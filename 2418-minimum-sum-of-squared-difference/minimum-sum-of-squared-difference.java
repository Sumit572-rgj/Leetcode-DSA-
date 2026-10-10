
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long k = (long) k1 + k2;
        int[] count = new int[100001];
        long sum = 0;

        for (int i = 0; i < n; i++) {
            int d = Math.abs(nums1[i] - nums2[i]);
            count[d]++;
            sum += d;
        }

        if (k >= sum) {
            return 0;
        }

        for (int d = 100000; d > 0 && k > 0; d--) {
            int moves = (int) Math.min(k, count[d]);
            count[d] -= moves;
            count[d - 1] += moves;
            k -= moves;
        }

        long ans = 0;

        for (int d = 1; d <= 100000; d++) {
            ans += (long) count[d] * d * d;
        }

        return ans;
    }
}
