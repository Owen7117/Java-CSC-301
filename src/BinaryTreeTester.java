// Owen O'Neil
// Chat.gpt entirely wrote this Binary tree tester/visualizer
public class BinaryTreeTester {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        // Insert more nodes to fill the tree
        String[] values = {
                "M", "C", "T", "A", "E", "R", "Z",
                "B", "D", "F", "P", "S", "U", "Y",
                "N", "G", "Q", "V", "X", "L"
        };

        for (String val : values) {
            tree.insert(val);
        }

        System.out.println("\nInitial Binary Tree:\n");
        printTree(tree.getRoot(), 0);

        // Remove a few nodes
        tree.remove("C");
        tree.remove("T");
        tree.remove("M");

        System.out.println("\nBinary Tree after removing 'C', 'T', and 'M':\n");
        printTree(tree.getRoot(), 0);
    }

    /*
     * Prints the binary tree sideways with clean formatting.
     * Right subtree is at the top, root in the middle, left subtree at the bottom.
     */
    public static void printTree(BinNode node, int depth) {
        if (node == null) {
            return;
        }

        // Increase spacing between levels
        int spacing = 6;

        // Print right subtree first
        printTree(node.getRight(), depth + 1);

        // Print current node with indentation
        for (int i = 0; i < depth * spacing; i++) {
            System.out.print(" ");
        }

        // Add connecting branch lines for clarity
        if (depth > 0) {
            System.out.print("-- ");
        }

        System.out.println(node.getData());

        // Print left subtree
        printTree(node.getLeft(), depth + 1);
    }
}