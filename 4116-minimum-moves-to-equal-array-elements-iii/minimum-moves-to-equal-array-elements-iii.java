class Solution {
    public int minMoves(int[] nums) {
        int move = 0;
        int target = nums[0];
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[i] > target) {
                target = nums[i];
            }
        }

        for (int i = 0; i < n; i++) {
            move = move + target - nums[i];
        }

        return move;
    }
}