class Solution {
    public List<String> braceExpansionII(String expression) {
        return new ArrayList<>(parse(expression, 0).set);
    }

    static class Node {
        TreeSet<String> set;
        int index;

        Node(TreeSet<String> set, int index) {
            this.set = set;
            this.index = index;
        }
    }

    private Node parse(String s, int i) {
        TreeSet<String> result = new TreeSet<>();
        TreeSet<String> current = new TreeSet<>();
        current.add("");

        while (i < s.length() && s.charAt(i) != '}') {
            char c = s.charAt(i);

            if (c == ',') {
                result.addAll(current);
                current = new TreeSet<>();
                current.add("");
                i++;
            } 
            else if (c == '{') {
                Node node = parse(s, i + 1);
                current = combine(current, node.set);
                i = node.index + 1;
            } 
            else {
                TreeSet<String> letter = new TreeSet<>();
                letter.add(String.valueOf(c));
                current = combine(current, letter);
                i++;
            }
        }

        result.addAll(current);
        return new Node(result, i);
    }

    private TreeSet<String> combine(TreeSet<String> a, TreeSet<String> b) {
        TreeSet<String> result = new TreeSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}