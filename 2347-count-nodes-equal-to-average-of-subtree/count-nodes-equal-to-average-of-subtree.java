class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    int count = 0;

    public int averageOfSubtree(TreeNode root) {
        dfs(root);
        return count;
    }

    int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }

        int[] left = dfs(root.left);
        int[] right = dfs(root.right);

        int sum = root.val + left[0] + right[0];
        int nodes = 1 + left[1] + right[1];

        if (root.val == sum / nodes) {
            count++;
        }

        return new int[]{sum, nodes};
    }
}