class Solution {
    int n, k;
    Node[] t;

    class Node {
        int p;
        int[] c;

        Node() {
            c = new int[k];
        }
    }

    Node merge(Node a, Node b) {
        Node z = new Node();
        z.p = a.p * b.p % k;

        for (int i = 0; i < k; i++) {
            z.c[i] += a.c[i];
            z.c[a.p * i % k] += b.c[i];
        }
        return z;
    }

    void build(int p, int l, int r, int[] a) {
        if (l == r) {
            t[p] = new Node();
            t[p].p = a[l] % k;
            t[p].c[t[p].p] = 1;
            return;
        }

        int m = (l + r) / 2;
        build(p * 2, l, m, a);
        build(p * 2 + 1, m + 1, r, a);
        t[p] = merge(t[p * 2], t[p * 2 + 1]);
    }

    void update(int p, int l, int r, int i, int v) {
        if (l == r) {
            t[p] = new Node();
            t[p].p = v % k;
            t[p].c[t[p].p] = 1;
            return;
        }

        int m = (l + r) / 2;
        if (i <= m)
            update(p * 2, l, m, i, v);
        else
            update(p * 2 + 1, m + 1, r, i, v);

        t[p] = merge(t[p * 2], t[p * 2 + 1]);
    }

    Node query(int p, int l, int r, int q) {
        if (l >= q) return t[p];

        int m = (l + r) / 2;

        if (q > m)
            return query(p * 2 + 1, m + 1, r, q);

        return merge(
            query(p * 2, l, m, q),
            query(p * 2 + 1, m + 1, r, q)
        );
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;
        n = nums.length;
        t = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int idx = queries[i][0];
            int val = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            update(1, 0, n - 1, idx, val);

            ans[i] = query(1, 0, n - 1, start).c[x];
        }

        return ans;
    }
}