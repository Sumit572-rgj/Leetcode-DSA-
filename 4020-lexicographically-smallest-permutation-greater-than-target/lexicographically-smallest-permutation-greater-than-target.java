class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        char[] ans = target.toCharArray();
        for (int i = 0; i < n; i++) {
            int x = target.charAt(i) - 'a';

            if (freq[x] > 0) {
                freq[x]--;
                continue;
            }

            for (int c = x + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    ans[i] = (char) ('a' + c);
                    freq[c]--;

                    int pos = i + 1;

                    for (int k = 0; k < 26; k++) {
                        while (freq[k] > 0) {
                            ans[pos++] = (char) ('a' + k);
                            freq[k]--;
                        }
                    }

                    return new String(ans);
                }
            }

    
            for (int j = i - 1; j >= 0; j--) {
                freq[target.charAt(j) - 'a']++;

                int old = target.charAt(j) - 'a';

                for (int c = old + 1; c < 26; c++) {
                    if (freq[c] > 0) {
                        ans[j] = (char) ('a' + c);
                        freq[c]--;

                        int pos = j + 1;

                        for (int k = 0; k < 26; k++) {
                            while (freq[k] > 0) {
                                ans[pos++] = (char) ('a' + k);
                                freq[k]--;
                            }
                        }

                        return new String(ans);
                    }
                }
            }

            return "";
        }
        for (int i = n - 1; i >= 0; i--) {
            freq[target.charAt(i) - 'a']++;

            int old = target.charAt(i) - 'a';

            for (int c = old + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    ans[i] = (char) ('a' + c);
                    freq[c]--;

                    int pos = i + 1;

                    for (int k = 0; k < 26; k++) {
                        while (freq[k] > 0) {
                            ans[pos++] = (char) ('a' + k);
                            freq[k]--;
                        }
                    }

                    return new String(ans);
                }
            }
        }

        return "";
    }
}