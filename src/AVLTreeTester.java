// Owen O'Neil
// Chat.gpt entirely wrote this AVL tree tester/visualizer
public class AVLTreeTester {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // Insert a larger set of nodes to see balancing in action
        String[] values = {
                "M", "C", "T", "A", "E", "R", "Z",
                "B", "D", "F", "P", "S", "U", "Y",
                "N", "G", "Q", "V", "X", "L"
        };

        for (String val : values) {
            tree.insert(val);
        }

        System.out.println("\nInitial AVL Tree:\n");
        printTree(tree.getRoot(), 0);

        // Remove some nodes to check balancing after deletion
        tree.remove("C");
        tree.remove("T");
        tree.remove("M");

        System.out.println("\nAVL Tree after removing 'C', 'T', and 'M':\n");
        printTree(tree.getRoot(), 0);

        // Demonstrate searches
        System.out.println("\nSearch results:");
        System.out.println("Contains 'E'? " + tree.search("E"));
        System.out.println("Contains 'Q'? " + tree.search("Q"));
        System.out.println("Contains 'Z'? " + tree.search("Z"));
        System.out.println("Contains 'K'? " + tree.search("K"));
    }

    /**
     * Prints the AVL tree sideways with clear indentation.
     * Right subtree is at the top, root in the middle, left subtree at the bottom.
     */
    public static void printTree(BinNode node, int depth) {
        if (node == null) return;

        int spacing = 6; // spaces per depth level

        // Print right subtree first
        printTree(node.getRight(), depth + 1);

        // Print current node with indentation and branch symbol
        for (int i = 0; i < depth * spacing; i++) System.out.print(" ");
        if (depth > 0) System.out.print("|-- ");
        System.out.println(node.getData());

        // Print left subtree
        printTree(node.getLeft(), depth + 1);
    }
}