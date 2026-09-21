class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] result = new long[k];
        long[] dp = new long[k];

        for (int num :nums){
            int value = num % k;
            long[] next = new long[k];

            next[value]++;

            for(int r=0;r<k;r++){
                next[(r*value)%k]+=dp[r];
            }
            for(int r=0; r<k;r++){
                result[r] += next[r];
            }
            dp = next;
        }
        return result;
    }
}