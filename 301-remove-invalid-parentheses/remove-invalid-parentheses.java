class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();

        int left = 0, right = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                left++;
            } else if (c == ')') {
                if (left > 0)
                    left--;
                else
                    right++;
            }
        }

        backtrack(s, 0, left, right, 0, new StringBuilder(), ans);

        return ans;
    }

    void backtrack(String s, int index, int leftRemove, int rightRemove,
                   int balance, StringBuilder curr, List<String> ans) {

        if (index == s.length()) {
            if (balance == 0 && leftRemove == 0 && rightRemove == 0) {
                String str = curr.toString();
                if (!ans.contains(str))
                    ans.add(str);
            }
            return;
        }

        char c = s.charAt(index);

        if (c == '(' && leftRemove > 0) {
            backtrack(s, index + 1, leftRemove - 1, rightRemove,
                      balance, curr, ans);
        }

        if (c == ')' && rightRemove > 0) {
            backtrack(s, index + 1, leftRemove, rightRemove - 1,
                      balance, curr, ans);
        }

        curr.append(c);

        if (c != '(' && c != ')') {
            backtrack(s, index + 1, leftRemove, rightRemove,
                      balance, curr, ans);
        } else if (c == '(') {
            backtrack(s, index + 1, leftRemove, rightRemove,
                      balance + 1, curr, ans);
        } else if (balance > 0) {
            backtrack(s, index + 1, leftRemove, rightRemove,
                      balance - 1, curr, ans);
        }

        curr.deleteCharAt(curr.length() - 1);
    }
}