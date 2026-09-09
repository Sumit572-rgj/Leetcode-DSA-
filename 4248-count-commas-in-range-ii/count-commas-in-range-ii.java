class Solution {
    public long countCommas(long n) {
        long answer = 0;

        for (long i = 1000; i <= n; i = i * 1000) {
            answer = answer + n - i + 1;

            if (i > n / 1000) {
                break;
            }
        }

        return answer;
    }
}