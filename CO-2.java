import java.util.Arrays;

class SegTreeLazy {
    long[] tree, lazy;

    SegTreeLazy(int n) { tree = new long[4 * n]; lazy = new long[4 * n]; }

    void build(int node, int lo, int hi, long[] a) {
        if (lo == hi) { tree[node] = a[lo]; return; }
        int m = (lo + hi) / 2;
        build(node * 2, lo, m, a);
        build(node * 2 + 1, m + 1, hi, a);
        tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
    }

    void pushDown(int node) {
        if (lazy[node] == 0) return;
        for (int c = node * 2; c <= node * 2 + 1; c++) {
            tree[c] += lazy[node];
            lazy[c] += lazy[node];
        }
        lazy[node] = 0;
    }

    void updateRange(int node, int lo, int hi, int l, int r, long d) {
        if (r < lo || hi < l) return;
        if (l <= lo && hi <= r) { tree[node] += d; lazy[node] += d; return; }
        pushDown(node);
        int m = (lo + hi) / 2;
        updateRange(node * 2, lo, m, l, r, d);
        updateRange(node * 2 + 1, m + 1, hi, l, r, d);
        tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
    }

    long queryMax(int node, int lo, int hi, int l, int r) {
        if (r < lo || hi < l) return Long.MIN_VALUE;
        if (l <= lo && hi <= r) return tree[node];
        pushDown(node);
        int m = (lo + hi) / 2;
        return Math.max(queryMax(node * 2, lo, m, l, r), queryMax(node * 2 + 1, m + 1, hi, l, r));
    }

    public static void main(String[] args) {
        int n = 16;
        long[] arr = new long[n];
        Arrays.fill(arr, 2);
        SegTreeLazy st = new SegTreeLazy(n);
        st.build(1, 0, n - 1, arr);
        st.updateRange(1, 0, n - 1, 2, 8, 4);
        st.updateRange(1, 0, n - 1, 6, 13, 3);
        System.out.println("Max in [0,15] = " + st.queryMax(1, 0, n - 1, 0, 15));
        st.updateRange(1, 0, n - 1, 1, 5, 5);
        System.out.println("Max in [3,10] = " + st.queryMax(1, 0, n - 1, 3, 10));
    }
}
