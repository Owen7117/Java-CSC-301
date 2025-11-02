// Owen O'Neil
// Chat.gpt entirely wrote this Red Black tree tester/visualizer
public class RedBlackTreeTester {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        // Insert nodes to test
        String[] values = {"M", "C", "T", "A", "E", "R", "Z", "B", "D", "F", "P", "S", "U", "Y", "N", "G", "Q", "V", "X", "L"};

        for (String val : values) {
            tree.insert(val);
        }

        System.out.println("\nInitial Red-Black Tree:\n");
        printTree(tree.getRoot(), 0);

        // Remove a few nodes
        tree.remove("C");
        tree.remove("T");
        tree.remove("M");

        System.out.println("\nRed-Black Tree after removing 'C', 'T', and 'M':\n");
        printTree(tree.getRoot(), 0);
    }

    /*
     * Prints the red-black tree sideways with colors.
     * Right subtree is at the top, root in the middle, left subtree at the bottom.
     * Shows (R) for red nodes and (B) for black nodes.
     */
    public static void printTree(BinNode node, int depth) {
        if (node == null) {
            return;
        }

        int spacing = 6; // Space between levels

        // Print right subtree first
        printTree(node.getRight(), depth + 1);

        // Print current node with indentation
        for (int i = 0; i < depth * spacing; i++) {
            System.out.print(" ");
        }

        if (depth > 0) {
            System.out.print("-- ");
        }

        // Show node value with color
        String color = node.getColor() ? "(R)" : "(B)";
        System.out.println(node.getData() + color);

        // Print left subtree
        printTree(node.getLeft(), depth + 1);
    }
}