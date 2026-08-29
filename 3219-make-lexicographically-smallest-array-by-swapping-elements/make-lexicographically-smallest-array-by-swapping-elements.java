class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }
 
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));
        
    
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        
        for (int i = 1; i < n; i++) {
            if (arr[i][0] - arr[i-1][0] <= limit) {
                union(parent, arr[i][1], arr[i-1][1]);
            }
        }
        
     
        Map<Integer, List<Integer>> groups = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            groups.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }
int[] result = new int[n];
        for (List<Integer> group : groups.values()) {
            List<Integer> values = new ArrayList<>();
            for (int idx : group) values.add(nums[idx]);
            Collections.sort(values);
            Collections.sort(group);
            for (int i = 0; i < group.size(); i++) {
                result[group.get(i)] = values.get(i);
            }
        }
        
        return result;
    }
    
    private int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);
        return parent[x];
    }
    
    private void union(int[] parent, int x, int y) {
        parent[find(parent, x)] = find(parent, y);
    }
}
