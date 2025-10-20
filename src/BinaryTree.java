// Owen O'Neil
// 10/16/25
//Program 2: AVL and Red Black Trees
/*
 - Binary tree that takes in strings as an input
 - Inserts the data into nodes in the tree
 - Sorts tree based on if the new input is less than or greater than the current node
 - If the node is less than it becomes the left child of the comparison node and if it is greater is goes to the right
 - When something is being searched for it goes through every node
 */

public class BinaryTree {

    private BinNode root;

    /**
     * default constructor
     */
    public BinaryTree() {
        root = null;
    }

    /* Function to check if tree is empty */
    public BinNode getRoot() {
        return this.root;
    }
    public void setRoot(BinNode root) {
        this.root = root;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    /* Functions to insert data */
    public void insert(String data) {
        this.root = insert(this.root, data);
    }

    /* Function to insert data recursively */
    private BinNode insert(BinNode node, String data) {
        if (node == null)
            node = new BinNode(data);
        else {
            if (data.compareTo(node.getData()) < 0) {
                node.setLeft(insert(node.getLeft(), data));
            } else if (data.compareTo(node.getData()) > 0) {
                node.setRight(insert(node.getRight(), data));
            } else {
                node.setRight(insert(node.getRight(), data));
            }
        }
        return node;
    }

    /* Function to count number of nodes */
    public int countNodes() {
        return countNodes(this.root);
    }

    /* Function to count number of nodes recursively */
    private int countNodes(BinNode r) {
        if (r == null) {
            return (0);
        }
        int count = 0;
        if (r.getLeft() == null && r.getRight() == null) {
            count = 1;
        }

        count += countNodes(r.getLeft());
        count += countNodes(r.getRight());

        return count;
    }

    /* Function to search for an element */
    public boolean search(String val) {
        return search(this.root, val);
    }

    /* Function to search for an element recursively */
    private boolean search(BinNode r, String val) {
        if (r == null) {
            return false;
        } else if (val.compareTo(r.getData()) == 0) {
            return true;
        } else if (val.compareTo(r.getData()) < 0) {
            return search(r.getLeft(), val);
        } else {
            return search(r.getRight(), val);
        }
    }

    // Helper method that calls the remove function recursively
    public void remove(String data) {
        this.root = remove(this.root, data);
    }

    // Remove node method
    private BinNode remove(BinNode node, String data) {
        if (node == null) {
            return null;
        }
        int cmp = data.compareTo(node.getData());
        // If the current node is less than its comparison move to the left node and call the remove function
        if (cmp < 0) {
            node.setLeft(remove(node.getLeft(), data));
        }
        // If the current nod is greater go to the node on the right and call the remove function
        else if (cmp > 0) {
            node.setRight(remove(node.getRight(), data));
        }
        // Found the node to delete
        else{
            // First case - the node is a leaf so just remove it
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            // Second case - the node has one child
            // Replace the node with its child and remove
            else if (node.getLeft() == null) {
                return node.getRight();
            }
            else if (node.getRight() == null) {
                return node.getLeft();
            }
            // Third case - the node has 2 children
            else {
                // Traverse one node to the left
                BinNode replacement = node.getLeft();
                // Keep going until you find the max node to the right of that node
                while (replacement.getRight() != null) {
                    replacement = replacement.getRight();
                }
                // Copy that value into the current node
                node.setData(replacement.getData());
                // recursively delete that replacement node because it's a duplicate
                node.setLeft(remove(node.getLeft(), replacement.getData()));
            }
        }
        return node;
    }


    /* Function for inorder traversal */
    public void inorder() {
        inorder(this.root);
    }

    private void inorder(BinNode r) {
        if (r != null) {
            inorder(r.getLeft());
            System.out.print(r.getData() + " ");
            inorder(r.getRight());
        }
    }

    /* Function for preorder traversal */
    public void preorder() {
        preorder(root);
    }

    private void preorder(BinNode r) {
        if (r != null) {
            System.out.print(r.getData() + " ");
            preorder(r.getLeft());
            preorder(r.getRight());
        }
    }

    /* Function for postorder traversal */
    public void postorder() {
        postorder(root);
    }

    private void postorder(BinNode r) {
        if (r != null) {
            postorder(r.getLeft());
            postorder(r.getRight());
            System.out.print(r.getData() + " ");
        }
    }

}
