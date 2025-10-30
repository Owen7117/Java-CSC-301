// Owen O'Neil
// 10/16/25
//Program 2: AVL and Red Black Trees

/*
 - Inserts nodes into the tree by following coloring rules
 - New node is default red while a null node is black
 - Search is the same as Binary tree
 - Remove handles the double black case that can occur after the deletion of a node
 - Number of black height in each side of the root needs to be the same
 - Like insertion, remove handles color changing and rotating nodes
 */

public class RedBlackTree extends BinaryTree {

    private static final boolean red = true;
    private static final boolean black = false;

    // Call the BinaryTree's constructor
    public RedBlackTree() {
        super();
    }

    // Subroutine determines the color of the node
    private boolean isRed(BinNode currentNode) {
        // Null nodes are considered black
        if (currentNode == null) {
        return false;
    }
        // Will return true if the node is red and false if not
        return currentNode.getColor() == red;
}
    // Override the binary tree insert method
    @Override
    public void insert(String data) {
        // Recursively insert the node and update the root
        super.setRoot(insertNode(super.getRoot(), data));
        //Set root node as black
        if (super.getRoot() != null) {
            super.getRoot().setColor(black);
        }
    }

    // Subroutine that inserts nodes properly with correct color
    private BinNode insertNode(BinNode currentNode, String data) {
        // Base case - If a null node is found, create a red node and insert the data
        if (currentNode == null) {
            BinNode newNode = new BinNode(data);
            newNode.setColor(red);
            return newNode;
        }
        // Same insert logic as binary tree
        if (data.compareTo(currentNode.getData()) < 0) {
            currentNode.setLeft(insertNode(currentNode.getLeft(), data));
        } else if (data.compareTo(currentNode.getData()) > 0) {
            currentNode.setRight(insertNode(currentNode.getRight(), data));
        } else {
            // Return currentNode if there is a duplicate
            return currentNode;
        }
        // Must fix the subtree as the recursion unwinds
        currentNode = fixTree(currentNode);
        return currentNode;
    }

    // Subroutine that fixes the tree after insertion
    private BinNode fixTree(BinNode currentNode) {
        // First Case - Right child is red and the left child is black
        if (isRed(currentNode.getRight()) && !isRed(currentNode.getLeft())) {
            currentNode = rotateLeft(currentNode);
        }
        // Second Case - Left child and its left child are both red
        if (isRed(currentNode.getLeft()) && isRed(currentNode.getLeft().getLeft())) {
            currentNode = rotateRight(currentNode);
        }
        // Third case - Both children are red so flip colors
        if (isRed(currentNode.getLeft()) && isRed(currentNode.getRight())) {
            flipColors(currentNode);
        }
        // Return rebalanced node
        return currentNode;
    }

    // Subroutine that rotates the subtree left from the current node
    private BinNode rotateLeft(BinNode currentNode) {
        // If the node is null of doesn't have a right child, you cant preform the rotation so return null
        if (currentNode == null || currentNode.getRight() == null)
            return currentNode;
        // Get right child
        BinNode right = currentNode.getRight();
        // Move the right child's left subtree into the current nodes right child
        currentNode.setRight(right.getLeft());
        // Set the current node to the left child of the former right child
        right.setLeft(currentNode);
        // The right child takes the new root and original color of the old root
        right.setColor(currentNode.getColor());
        // The old root becomes red
        currentNode.setColor(red);
        // return the new root after the rotation
        return right;
    }
    // Subroutine that rotates the subroutine right form the current node
    private BinNode rotateRight(BinNode currentNode) {
        // If the node is null of doesn't have a left child, you cant preform the rotation so return null
        if (currentNode == null || currentNode.getLeft() == null)
            return currentNode;
        // Store a reference to the left child (the one that will move up)
        BinNode left = currentNode.getLeft();
        // Move the left child's right subtree into the current node's left child
        currentNode.setLeft(left.getRight());
        // Set the current node to the right child of the former left child
        left.setRight(currentNode);
        // The left child takes the new root and original color of the old root
        left.setColor(currentNode.getColor());
        // The old root becomes red
        currentNode.setColor(red);
        // Return the new root of after the rotation
        return left;
    }
    // Subroutine that changes the color of the current node
    private void changeColor(BinNode currentNode) {
        if (currentNode.getColor() == red) {
            currentNode.setColor(black);
        } else if (currentNode.getColor() == black) {
            currentNode.setColor(red);
        }
    }
    // subroutine that flips the color of the current node and its two children
    private void flipColors(BinNode currentNode) {
        changeColor(currentNode);
        changeColor(currentNode.getLeft());
        changeColor(currentNode.getRight());
    }

    // Overrid the Binary tree remove method
    @Override
    public void remove(String data) {
        // Recursively remove node and rebalance tree
        super.setRoot(removeNode(super.getRoot(), data));
        // Root must always be black
        if (super.getRoot() != null) {
            super.getRoot().setColor(black);
        }
    }

    // Subroutine that removes the given node
    private BinNode removeNode(BinNode currentNode, String data) {
        // Base case - found a null node
        if (currentNode == null) {
            return null;
        }
        // Compare the value to be deleted with the currentNode
        int cmp = data.compareTo(currentNode.getData());
        // If currentNode is less than 0 go left node
        if (cmp < 0) {
            currentNode.setLeft(removeNode(currentNode.getLeft(), data));
        }
        // If currentNode is greater than 0 go to the right node
        else if (cmp > 0) {
            currentNode.setRight(removeNode(currentNode.getRight(), data));
        }
        // Found node to remove
        else {
            // First case - leaf node
            if (currentNode.getLeft() == null && currentNode.getRight() == null) {
                // If it is a black leaf, it could be a double black
                if (!isRed(currentNode)) {
                    currentNode = fixDoubleBlack(currentNode);
                }
                return null;
            }
            // Second case - One child
            // Replace the node with its child
            // Only right child exists
            else if (currentNode.getLeft() == null) {
                BinNode childRight = currentNode.getRight();
                childRight.setColor(currentNode.getColor());
                return childRight;
            // Only left child exists
            } else if (currentNode.getRight() == null) {
                BinNode childLeft = currentNode.getLeft();
                childLeft.setColor(currentNode.getColor());
                return childLeft;
            }
            // Third case - Two children
            // First find the leaf node that is an inorder successor
            else {
                // Traverse one node to the left
                BinNode replacement = currentNode.getLeft();
                // Keep going until you find the max node to the right of that node
                while (replacement.getRight() != null) {
                    replacement = replacement.getRight();
                }
                // Copy that value into the current node
                currentNode.setData(replacement.getData());
                // recursively delete that replacement node because it's a duplicate
                currentNode.setLeft(removeNode(currentNode.getLeft(), replacement.getData()));
            }

            // Rebalance as the recursion builds back up
            if (isRed(currentNode.getRight()) && !isRed(currentNode.getLeft())) {
                currentNode = rotateLeft(currentNode);
            }
            if (isRed(currentNode.getLeft()) && !isRed(currentNode.getRight())) {
                currentNode = rotateRight(currentNode);
            }
            if (isRed(currentNode.getLeft()) && isRed(currentNode.getRight()))
                flipColors(currentNode);
        }
        // Return the adjusted node
        return currentNode;
    }
    // Subroutine that fixes the double black node problem
    // chat.gpt helped me understand and write this
    private BinNode fixDoubleBlack(BinNode node) {
        // Find the parent of the double black
        BinNode parent = findParent(super.getRoot(), node);
        // Base case - If the node is the root of the tree, stop
        if (parent == null) return node;

        // Determine if the node a left or right child
        boolean nodeIsLeftChild;
        if (parent.getLeft() == node) {
            nodeIsLeftChild = true;
        } else {
            nodeIsLeftChild = false;
        }

        // Find the sibling based on what side the node is on
        BinNode siblingNode;
        if (nodeIsLeftChild) {
            siblingNode = parent.getRight();
        } else {
            siblingNode = parent.getLeft();
        }

        // First case -The Sibling is red
        // Rotate and recolor
        if (isRed(siblingNode)) {
            parent.setColor(red);
            siblingNode.setColor(black);
            if (nodeIsLeftChild) {
                parent = rotateLeft(parent);
            } else {
                parent = rotateRight(parent);
            }
            // Update the sibling after rotation
            if (nodeIsLeftChild) {
                siblingNode = parent.getRight();
            } else {
                siblingNode = parent.getLeft();
            }
        }

        // Second case - The Sibling and its children are black
        // Recolor
        if (!isRed(siblingNode.getLeft()) && !isRed(siblingNode.getRight())) {
            siblingNode.setColor(red);
            if (!isRed(parent)) {
                parent = fixDoubleBlack(parent);
            } else {
                parent.setColor(black);
            }
        }
        // Third case - The Sibling has at least one red child
        else {
            if (nodeIsLeftChild) {
                // The right child of the sibling is red
                // Rotate left
                if (isRed(siblingNode.getRight())) {
                    siblingNode.setColor(parent.getColor());
                    parent.setColor(black);
                    siblingNode.getRight().setColor(black);
                    parent = rotateLeft(parent);
                }
                // If the left child of the sibling is red
                // Double Rotation
                else {
                    siblingNode.getLeft().setColor(black);
                    siblingNode.setColor(red);
                    parent.setRight(rotateRight(siblingNode));
                    parent = rotateLeft(parent);
                }
            }
            // Same stuff but for right child
            else {
                if (isRed(siblingNode.getLeft())) {
                    siblingNode.setColor(parent.getColor());
                    parent.setColor(black);
                    siblingNode.getLeft().setColor(black);
                    parent = rotateRight(parent);
                } else {
                    siblingNode.getRight().setColor(black);
                    siblingNode.setColor(red);
                    parent.setLeft(rotateLeft(siblingNode));
                    parent = rotateRight(parent);
                }
            }
        }
        // Return the fixed parent node
        return parent;
    }

    // Subroutine to find the parent node by starting from the root
    private BinNode findParent(BinNode root, BinNode child) {
        // Two base cases
        // If you are at the root there is no parent
        if (root == null || root == child){return null;}
        // Found the parent
        if (root.getLeft() == child || root.getRight() == child){return root;}
        // recursively search left or right
        if (child.getData().compareTo(root.getData()) < 0)
            return findParent(root.getLeft(), child);
        else
            return findParent(root.getRight(), child);
    }
}
