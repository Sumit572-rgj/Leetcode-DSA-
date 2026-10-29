class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int[] parent = new int[n];
        int[] depth = new int[n];
        int[] size = new int[n];
        int[] heavy = new int[n];
        int[] head = new int[n];
        int[] pos = new int[n];
        int[] order = new int[n];

        Arrays.fill(parent, -1);
        Arrays.fill(heavy, -1);

        int[] stack = new int[n];
        int top = 0, count = 0;
        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];
            order[count++] = u;

            for (int v : adj[u]) {
                if (parent[v] == -1) {
                    parent[v] = u;
                    depth[v] = depth[u] + 1;
                    stack[top++] = v;
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];
            size[u] = 1;
            int maxSize = 0;

            for (int v : adj[u]) {
                if (parent[v] == u) {
                    size[u] += size[v];
                    if (size[v] > maxSize) {
                        maxSize = size[v];
                        heavy[u] = v;
                    }
                }
            }
        }

        int timer = 0;
        int[] nodeStack = new int[n];
        int[] headStack = new int[n];
        top = 0;
        nodeStack[top] = 0;
        headStack[top++] = 0;

        while (top > 0) {
            int u = nodeStack[--top];
            int h = headStack[top];

            while (u != -1) {
                head[u] = h;
                pos[u] = timer++;

                for (int v : adj[u]) {
                    if (parent[v] == u && v != heavy[u]) {
                        nodeStack[top] = v;
                        headStack[top++] = v;
                    }
                }

                u = heavy[u];
            }
        }

        int[] seg = new int[4 * n];
        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int idx = pos[i] + n;
            seg[idx] = 1 << (chars[i] - 'a');
        }

        int[] base = new int[2 * n];
        for (int i = 0; i < n; i++) {
            base[n + pos[i]] = 1 << (chars[i] - 'a');
        }

        for (int i = n - 1; i > 0; i--) {
            base[i] = base[i << 1] ^ base[i << 1 | 1];
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                char c = p[2].charAt(0);
                chars[u] = c;

                int idx = n + pos[u];
                base[idx] = 1 << (c - 'a');

                for (idx >>= 1; idx > 0; idx >>= 1) {
                    base[idx] = base[idx << 1] ^ base[idx << 1 | 1];
                }
            } else {
                int v = Integer.parseInt(p[2]);
                int mask = 0;

                while (head[u] != head[v]) {
                    if (depth[head[u]] < depth[head[v]]) {
                        int t = u;
                        u = v;
                        v = t;
                    }

                    mask ^= rangeQuery(base, n, pos[head[u]], pos[u]);
                    u = parent[head[u]];
                }

                if (depth[u] > depth[v]) {
                    int t = u;
                    u = v;
                    v = t;
                }

                mask ^= rangeQuery(base, n, pos[u], pos[v]);
                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    private int rangeQuery(int[] tree, int n, int l, int r) {
        int res = 0;
        l += n;
        r += n + 1;

        while (l < r) {
            if ((l & 1) == 1) res ^= tree[l++];
            if ((r & 1) == 1) res ^= tree[--r];
            l >>= 1;
            r >>= 1;
        }

        return res;
    }
}