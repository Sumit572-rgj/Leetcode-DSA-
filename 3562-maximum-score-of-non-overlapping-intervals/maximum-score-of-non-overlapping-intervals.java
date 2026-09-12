class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        Arrays.sort(arr, (a, b) -> a[1] - b[1]);

        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            prev[i] = -1;

            for (int j = i - 1; j >= 0; j--) {
                if (arr[j][1] < arr[i][0]) {
                    prev[i] = j;
                    break;
                }
            }
        }

        long[][] dp = new long[n + 1][5];

        List<Integer>[][] list = new ArrayList[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 4; j++) {
                list[i][j] = new ArrayList<>();
            }
        }

        for (int i = 1; i <= n; i++) {

            int id = arr[i - 1][3];
            int weight = arr[i - 1][2];

            for (int k = 1; k <= 4; k++) {

                dp[i][k] = dp[i - 1][k];
                list[i][k] = new ArrayList<>(list[i - 1][k]);

                int p = prev[i - 1] + 1;

                long value = dp[p][k - 1] + weight;

                if (value > dp[i][k]) {

                    dp[i][k] = value;

                    list[i][k] = new ArrayList<>(list[p][k - 1]);
                    list[i][k].add(id);

                    Collections.sort(list[i][k]);
                }
                else if (value == dp[i][k]) {

                    List<Integer> temp =
                        new ArrayList<>(list[p][k - 1]);

                    temp.add(id);
                    Collections.sort(temp);

                    if (smaller(temp, list[i][k])) {
                        list[i][k] = temp;
                    }
                }
            }
        }

        List<Integer> answer = new ArrayList<>();

        for (int k = 1; k <= 4; k++) {

            if (dp[n][k] > dp[n][answer.size()]) {
                answer = list[n][k];
            }
            else if (dp[n][k] == dp[n][answer.size()]) {

                if (smaller(list[n][k], answer)) {
                    answer = list[n][k];
                }
            }
        }

        int[] result = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }

        return result;
    }

    private boolean smaller(List<Integer> a, List<Integer> b) {

        for (int i = 0; i < Math.min(a.size(), b.size()); i++) {

            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }

        return a.size() < b.size();
    }
}