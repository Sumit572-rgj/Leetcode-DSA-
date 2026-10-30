import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        int[][] dist = new int[n][k + 1];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[2], b[2])
        );

        int count = 1;
        dist[0][1] = 0;
        pq.offer(new int[]{0, 1, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();

            int node = curr[0];
            int same = curr[1];
            int cost = curr[2];

            if (cost != dist[node][same]) {
                continue;
            }

            if (node == n - 1) {
                return cost;
            }

            for (int[] next : graph[node]) {
                int v = next[0];
                int weight = next[1];

                int newSame;

                if (labels.charAt(node) == labels.charAt(v)) {
                    newSame = same + 1;
                } else {
                    newSame = 1;
                }

                if (newSame > k) {
                    continue;
                }

                int newCost = cost + weight;

                if (newCost < dist[v][newSame]) {
                    dist[v][newSame] = newCost;
                    pq.offer(new int[]{v, newSame, newCost});
                }
            }
        }

        return -1;
    }
}