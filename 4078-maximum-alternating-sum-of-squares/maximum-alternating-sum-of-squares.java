
import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        long[] arr = new long[nums.length];

        for (int i = 0; i < nums.length; i++) {
            arr[i] = (long) nums[i] * nums[i];
        }

        Arrays.sort(arr);

        long score = 0;
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            if (i < (n + 1) / 2) {
                score += arr[n - 1 - i];
            } else {
                score -= arr[n - 1 - i];
            }
        }

        return score;
    }
}
