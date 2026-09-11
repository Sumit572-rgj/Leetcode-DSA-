class Solution {
    public int totalNumbers(int[] digits) {
        int ans = 0;
        for (int n = 100; n <= 998; n = n + 2) {
            int[] f = new int[10];
            for (int d : digits) f[d]++;
            int a = n / 100, b = n / 10 % 10, c = n % 10;
            if (--f[a] >= 0 && --f[b] >= 0 && --f[c] >= 0) ans++;
        }
        return ans;
    }
}