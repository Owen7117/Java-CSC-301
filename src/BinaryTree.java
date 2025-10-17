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

    public void remove(String data) {
        this.root = remove(this.root, data);
    }

    /* Helper method to recursively remove a node */
    private BinNode remove(BinNode node, String data) {
        if (node == null) {
            return null;
        }

        int cmp = data.compareTo(node.getData());

        if (cmp < 0) {
            node.setLeft(remove(node.getLeft(), data));
        }
        else if (cmp > 0) {
            node.setRight(remove(node.getRight(), data));
        }
        else {
            // Found the node to delete

            // Case 1: No children (leaf node)
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            // Case 2: One child
            else if (node.getLeft() == null) {
                return node.getRight();
            }
            else if (node.getRight() == null) {
                return node.getLeft();
            }
            // Case 3: Two children
            else {
                // Find inorder successor (smallest in right subtree)
                BinNode successor = findMin(node.getRight());
                node.setData(successor.getData());
                node.setRight(remove(node.getRight(), successor.getData()));
            }
        }
        return node;
    }

    private BinNode findMin(BinNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
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
