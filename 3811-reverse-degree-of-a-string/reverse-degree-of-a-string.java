class Solution {
    public int reverseDegree(String s) {
        int n = s.length();
        int sum = 0;

        for (int i = 0; i < n; i++) {
            int value = 26 - (s.charAt(i) - 'a');
            sum = sum + value * (i + 1);
        }

        return sum;
    }
}