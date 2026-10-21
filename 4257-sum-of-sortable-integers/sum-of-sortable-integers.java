class Solution {
    static final long MOD1 = 1_000_000_007L;
    static final long MOD2 = 1_000_000_009L;
    static final long BASE = 911382323L;

    long[] pow1, pow2;
    long[] hNum1, hNum2;
    long[] hSort1, hSort2;

    public int sortableIntegers(int[] nums) {
        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        buildHashes(nums, sorted);

        List<Integer> divisors = new ArrayList<>();
        for (int d = 1; d * d <= n; d++) {
            if (n % d == 0) {
                divisors.add(d);
                if (d * d != n) divisors.add(n / d);
            }
        }

        long answer = 0;

        for (int k : divisors) {
            if (isSortable(nums, sorted, k)) {
                answer += k;
            }
        }

        return (int) answer;
    }

    private void buildHashes(int[] nums, int[] sorted) {
        int n = nums.length;

        pow1 = new long[n + 1];
        pow2 = new long[n + 1];

        pow1[0] = 1;
        pow2[0] = 1;

        for (int i = 1; i <= n; i++) {
            pow1[i] = (pow1[i - 1] * BASE) % MOD1;
            pow2[i] = (pow2[i - 1] * BASE) % MOD2;
        }

        hNum1 = new long[n + 1];
        hNum2 = new long[n + 1];
        hSort1 = new long[n + 1];
        hSort2 = new long[n + 1];

        for (int i = 0; i < n; i++) {
            long v1 = nums[i] + 1L;
            hNum1[i + 1] = (hNum1[i] * BASE + v1) % MOD1;
            hNum2[i + 1] = (hNum2[i] * BASE + v1) % MOD2;

            long v2 = sorted[i] + 1L;
            hSort1[i + 1] = (hSort1[i] * BASE + v2) % MOD1;
            hSort2[i + 1] = (hSort2[i] * BASE + v2) % MOD2;
        }
    }

    private boolean isSortable(int[] nums, int[] sorted, int k) {
        int n = nums.length;

        for (int p = 0; p < n; p += k) {

            long t1 = getHash(hSort1, pow1, MOD1, p, p + k - 1);
            long t2 = getHash(hSort2, pow2, MOD2, p, p + k - 1);

            boolean ok = false;

            for (int r = 0; r < k; r++) {
                long a1 = rotationHash(
                        hNum1, pow1, MOD1,
                        p, k, r
                );

                long a2 = rotationHash(
                        hNum2, pow2, MOD2,
                        p, k, r
                );

                if (a1 == t1 && a2 == t2) {
                    ok = true;
                    break;
                }
            }

            if (!ok) return false;
        }

        return true;
    }

    private long rotationHash(
            long[] h, long[] pow, long mod,
            int start, int k, int r) {

        if (r == 0) {
            return getHash(h, pow, mod, start, start + k - 1);
        }

        long first = getHash(h, pow, mod,
                start + r, start + k - 1);

        long second = getHash(h, pow, mod,
                start, start + r - 1);

        return (first * pow[r] + second) % mod;
    }

    private long getHash(
            long[] h, long[] pow, long mod,
            int l, int r) {

        long res = (h[r + 1]
                - h[l] * pow[r - l + 1]) % mod;

        if (res < 0) res += mod;
        return res;
    }
}