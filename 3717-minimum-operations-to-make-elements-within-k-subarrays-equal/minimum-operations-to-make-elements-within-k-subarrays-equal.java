class Solution {
    java.util.TreeMap<Integer, Integer> left = new java.util.TreeMap<>();
    java.util.TreeMap<Integer, Integer> right = new java.util.TreeMap<>();

    long sumL = 0, sumR = 0;
    int sizeL = 0, sizeR = 0;

    void add(java.util.TreeMap<Integer, Integer> map, int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    void remove(java.util.TreeMap<Integer, Integer> map, int val) {
        int c = map.get(val);
        if (c == 1) map.remove(val);
        else map.put(val, c - 1);
    }

    void insert(int val) {
        if (sizeL == 0 || val <= left.lastKey()) {
            add(left, val);
            sumL += val;
            sizeL++;
        } else {
            add(right, val);
            sumR += val;
            sizeR++;
        }
        balance();
    }

    void erase(int val) {
        if (left.containsKey(val)) {
            remove(left, val);
            sumL -= val;
            sizeL--;
        } else {
            remove(right, val);
            sumR -= val;
            sizeR--;
        }
        balance();
    }

    void balance() {
        int target = (sizeL + sizeR + 1) / 2;

        while (sizeL > target) {
            int v = left.lastKey();
            remove(left, v);
            sumL -= v;
            sizeL--;

            add(right, v);
            sumR += v;
            sizeR++;
        }

        while (sizeL < target) {
            int v = right.firstKey();
            remove(right, v);
            sumR -= v;
            sizeR--;

            add(left, v);
            sumL += v;
            sizeL++;
        }
    }

    long getCost() {
        int median = left.lastKey();

        return (long) median * sizeL - sumL
             + sumR - (long) median * sizeR;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        long[] cost = new long[n - x + 1];

        left.clear();
        right.clear();
        sumL = sumR = 0;
        sizeL = sizeR = 0;

        for (int i = 0; i < x; i++) {
            insert(nums[i]);
        }

        cost[0] = getCost();

        for (int i = x; i < n; i++) {
            erase(nums[i - x]);
            insert(nums[i]);
            cost[i - x + 1] = getCost();
        }

        long[][] dp = new long[k + 1][n + 1];

        for (int i = 1; i <= k; i++) {
            java.util.Arrays.fill(dp[i], Long.MAX_VALUE / 4);
        }

        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i][j - 1];

                if (j >= x) {
                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - 1][j - x] + cost[j - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}