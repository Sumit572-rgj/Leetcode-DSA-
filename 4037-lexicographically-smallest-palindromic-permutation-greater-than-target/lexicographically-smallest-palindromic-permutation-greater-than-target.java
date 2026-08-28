class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();

        
        int[] cnt = new int[26];
        for (char ch : s.toCharArray()) {
            cnt[ch - 'a']++;
        }

       
        int oddCount = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (cnt[i] % 2 == 1) {
                oddCount++;
                middle = (char) ('a' + i);
            }
        }

        if (oddCount > 1) {
            return "";
        }

        int halfLen = n / 2;

        int[] halfCnt = new int[26];
        for (int i = 0; i < 26; i++) {
            halfCnt[i] = cnt[i] / 2;
        }

        String targetHalf = target.substring(0, halfLen);

        int[] temp = halfCnt.clone();
        boolean possible = true;

        for (int i = 0; i < halfLen; i++) {
            int x = targetHalf.charAt(i) - 'a';

            if (temp[x] == 0) {
                possible = false;
                break;
            }

            temp[x]--;
        }

        if (possible) {
            String half = targetHalf;

            StringBuilder palindrome = new StringBuilder();
            palindrome.append(half);

            if (n % 2 == 1) {
                palindrome.append(middle);
            }

            palindrome.append(new StringBuilder(half).reverse());

            String candidate = palindrome.toString();

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        for (int pivot = halfLen - 1; pivot >= 0; pivot--) {

            int[] available = halfCnt.clone();

          
            boolean prefixPossible = true;

            for (int i = 0; i < pivot; i++) {
                int x = targetHalf.charAt(i) - 'a';

                if (available[x] == 0) {
                    prefixPossible = false;
                    break;
                }

                available[x]--;
            }

            if (!prefixPossible) {
                continue;
            }

          
            int targetChar = targetHalf.charAt(pivot) - 'a';

            for (int ch = targetChar + 1; ch < 26; ch++) {

                if (available[ch] > 0) {

                    available[ch]--;

                    StringBuilder half = new StringBuilder();

                    
                    half.append(targetHalf, 0, pivot);

                 
                    half.append((char) ('a' + ch));

                    for (int k = 0; k < 26; k++) {
                        while (available[k] > 0) {
                            half.append((char) ('a' + k));
                            available[k]--;
                        }
                    }

                   
                    String left = half.toString();

                    StringBuilder palindrome = new StringBuilder();
                    palindrome.append(left);

                    if (n % 2 == 1) {
                        palindrome.append(middle);
                    }

                    palindrome.append(new StringBuilder(left).reverse());

                    return palindrome.toString();
                }
            }
        }

        return "";
    }
}