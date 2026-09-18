import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, -1);

        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';

            if (first[c] == -1)
                first[c] = i;

            last[c] = i;
        }

        List<int[]> list = new ArrayList<>();

        for (int c = 0; c < 26; c++) {
            if (first[c] == -1)
                continue;

            int l = first[c];
            int r = last[c];
            boolean ok = true;

            for (int i = l; i <= r; i++) {
                int x = s.charAt(i) - 'a';

                if (first[x] < l) {
                    ok = false;
                    break;
                }

                r = Math.max(r, last[x]);
            }

            if (ok)
                list.add(new int[]{l, r});
        }

        list.sort((a, b) -> a[1] - b[1]);

        List<String> ans = new ArrayList<>();
        int end = -1;

        for (int[] p : list) {
            if (p[0] > end) {
                ans.add(s.substring(p[0], p[1] + 1));
                end = p[1];
            }
        }

        return ans;
    }
}