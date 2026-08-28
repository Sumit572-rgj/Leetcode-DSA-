
class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();

        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int odd = 0;
        int center = -1;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                center = i;
            }
        }

        if (odd > 1) {
            return "";
        }

    
        int halfLen = n / 2;
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        String targetHalf = target.substring(0, halfLen);

       
        if (canForm(targetHalf, halfFreq)) {

            String candidate = buildPalindrome(targetHalf, center, n);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }
        String nextHalf = nextGreater(targetHalf, halfFreq);

        if (nextHalf == null) {
            return "";
        }

        return buildPalindrome(nextHalf, center, n);
    }

    
    private boolean canForm(String targetHalf, int[] freq) {
        int[] used = new int[26];

        for (char c : targetHalf.toCharArray()) {
            int x = c - 'a';

            used[x]++;

            if (used[x] > freq[x]) {
                return false;
            }
        }

        return true;
    }

    private String nextGreater(String targetHalf, int[] freq) {
        int m = targetHalf.length();
        for (int pos = m - 1; pos >= 0; pos--) {

            int[] used = new int[26];
            boolean possiblePrefix = true;

          
            for (int i = 0; i < pos; i++) {
                int x = targetHalf.charAt(i) - 'a';
                used[x]++;

                if (used[x] > freq[x]) {
                    possiblePrefix = false;
                    break;
                }
            }

            if (!possiblePrefix) {
                continue;
            }

            int current = targetHalf.charAt(pos) - 'a';

            for (int c = current + 1; c < 26; c++) {

                if (used[c] < freq[c]) {

                    StringBuilder result = new StringBuilder();

                   
                    for (int i = 0; i < pos; i++) {
                        result.append(targetHalf.charAt(i));
                    }

         
                    result.append((char) ('a' + c));

                    int[] remaining = freq.clone();

                    for (int i = 0; i < pos; i++) {
                        remaining[targetHalf.charAt(i) - 'a']--;
                    }

                    remaining[c]--;

                    for (int x = 0; x < 26; x++) {
                        while (remaining[x] > 0) {
                            result.append((char) ('a' + x));
                            remaining[x]--;
                        }
                    }

                    return result.toString();
                }
            }
        }

        return null;
    }

    private String buildPalindrome(String half, int center, int n) {
        StringBuilder result = new StringBuilder();

        result.append(half);

        if (n % 2 == 1) {
            result.append((char) ('a' + center));
        }

      
        for (int i = half.length() - 1; i >= 0; i--) {
            result.append(half.charAt(i));
        }

        return result.toString();
    }
}
