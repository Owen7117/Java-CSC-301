// Owen O'Neil
// 10/16/25
//Program 2: AVL and Red Black Trees

public class RedBlackTree extends BinaryTree {

    private static final boolean red = true;
    private static final boolean black = false;

    public RedBlackTree() {
        super();
    }

    private boolean isRed(BinNode currentNode) {
        if (currentNode == null) {
            return false;
        }
        return currentNode.getColor() == red;
    }

    @Override
    public void insert(String data) {
        // Recursively insert the node and update the root
        super.setRoot(insertNode(super.getRoot(), data));
        //Set root node as black
        if (super.getRoot() != null) {
            super.getRoot().setColor(black);
        }
    }

    private BinNode insertNode(BinNode currentNode, String data) {
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
        currentNode = fixTree(currentNode);
        return currentNode;
    }

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
        return currentNode;
    }

    /*
    Although this is very much like the AVL rotation, I got really tripped up for some
    reason with the rotations for these, so I referenced chat.gpt
    */
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

    private void changeColor(BinNode currentNode) {
        if (currentNode.getColor() == red) {
            currentNode.setColor(black);
        } else if (currentNode.getColor() == black) {
            currentNode.setColor(red);
        }
    }

    private void flipColors(BinNode currentNode) {
        changeColor(currentNode);
        changeColor(currentNode.getLeft());
        changeColor(currentNode.getRight());
    }

    @Override
    public void remove(String data) {
        super.setRoot(removeNode(super.getRoot(), data));
        if (super.getRoot() != null) {
            super.getRoot().setColor(black);
        }
    }

    private BinNode removeNode(BinNode currentNode, String data) {
        // The node is null
        // Base case
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
                if (!isRed(currentNode)) {
                    currentNode = fixDoubleBlack(currentNode);
                }
                return null;
            }
            // Second case - One child
            // Replace the node with its child
            else if (currentNode.getLeft() == null) {
                BinNode childRight = currentNode.getRight();
                childRight.setColor(currentNode.getColor());
                return childRight;
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
        return currentNode;
    }

    // chat.gpt helped me understand and write parts of this
    private BinNode fixDoubleBlack(BinNode node) {
        BinNode parent = findParent(super.getRoot(), node);
        if (parent == null) return node; // root case

        // Determine if the node is the left child of its parent
        boolean nodeIsLeftChild;
        if (parent.getLeft() == node) {
            nodeIsLeftChild = true;
        } else {
            nodeIsLeftChild = false;
        }

        // Identify the sibling of the node
        BinNode siblingNode;
        if (nodeIsLeftChild) {
            siblingNode = parent.getRight();
        } else {
            siblingNode = parent.getLeft();
        }

        // Case 1: Sibling is red
        if (isRed(siblingNode)) {
            parent.setColor(red);
            siblingNode.setColor(black);
            if (nodeIsLeftChild) {
                parent = rotateLeft(parent);
            } else {
                parent = rotateRight(parent);
            }
            // Update sibling after rotation
            if (nodeIsLeftChild) {
                siblingNode = parent.getRight();
            } else {
                siblingNode = parent.getLeft();
            }
        }

        // Case 2: Sibling and its children are black
        if (!isRed(siblingNode.getLeft()) && !isRed(siblingNode.getRight())) {
            siblingNode.setColor(red);
            if (!isRed(parent)) {
                parent = fixDoubleBlack(parent);
            } else {
                parent.setColor(black);
            }
        } else {
            // Case 3: Sibling has at least one red child
            if (nodeIsLeftChild) {
                if (isRed(siblingNode.getRight())) {
                    siblingNode.setColor(parent.getColor());
                    parent.setColor(black);
                    siblingNode.getRight().setColor(black);
                    parent = rotateLeft(parent);
                } else {
                    siblingNode.getLeft().setColor(black);
                    siblingNode.setColor(red);
                    parent.setRight(rotateRight(siblingNode));
                    parent = rotateLeft(parent);
                }
            } else {
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

        return parent;
    }

    // Helper to find parent of a given node
    private BinNode findParent(BinNode root, BinNode child) {
        if (root == null || root == child) return null;
        if (root.getLeft() == child || root.getRight() == child) return root;

        if (child.getData().compareTo(root.getData()) < 0)
            return findParent(root.getLeft(), child);
        else
            return findParent(root.getRight(), child);
    }
}
