
// Owen O'Neil
/*
 - AVL tree that takes in strings as an input
 - Sorts the strings using the Binary tree and balances the tree if the leftHeight - rightHeight
   isn't between -1 and 1
 - Inherits the search function directly from the Binary tree
 - Insert and remove override the binary search and remove function
 */


public class AVLTree extends BinaryTree {

    // Calls the BinaryTree's constructor to initialise the tree
    public AVLTree() {
        super();
    }

    // Overrides the Binary tree insert
    @Override
    public void insert(String data) {
        // Recursively  insert the node and update the root
        super.setRoot(insertNode(super.getRoot(), data));
    }

    // Recursive insertion with balancing
    private BinNode insertNode(BinNode currentNode, String data) {
        // The base case, if the node is null insert a new node
        if (currentNode == null) {
            BinNode newNode = new BinNode(data);
            // leaf node height is 1
            newNode.setHeight(1);
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
        // Call the update height subroutine to update the height after insertion
        updateHeight(currentNode);
        // Balance the node
        return balance(currentNode);
    }

    // Returns the height of the node
    private int height(BinNode currentNode) {
        // If the node is null then the height is 0
        if(currentNode == null){
            return 0;
        }
        // Get the height of the node
        else{
            return currentNode.getHeight();
        }
    }
    // Updates the height of the node based on its Children
    private void updateHeight(BinNode currentNode){
        currentNode.setHeight(Math.max(height(currentNode.getLeft()), height(currentNode.getRight())) + 1);
    }

    // Calculates the tree height so it knows when to balance it
    private int balanceDetector(BinNode currentNode){
        if (currentNode == null)
            return 0;
        // Returns the calculated balance of the tree
        return height(currentNode.getLeft()) - height(currentNode.getRight());
    }

    // Balanced the tree if necessary
    private BinNode balance(BinNode currentNode){
        int bd = balanceDetector(currentNode);
        // If the balance is greater than 1 the tree is left heavy
        if(bd > 1){
            // The left child is right heavy
            if(balanceDetector(currentNode.getLeft()) < 0){
                // Rotate left so it is no longer a double rotation
                currentNode.setLeft(rotateLeft(currentNode.getLeft()));
            }
            // Rotate right(single rotation) to fix the tree
            return rotateRight(currentNode);
        }
        // If the balance is less than -1 the tree is right heavy
        if(bd < -1){
            // The right child is left heavy
            if(balanceDetector(currentNode.getRight()) > 0){
                // Rotate right so it is no longer a double rotation
                currentNode.setRight(rotateRight(currentNode.getRight()));
            }
            // Rotate left(single rotation) to fix the tree
            return rotateLeft(currentNode);
        }
        // Return the currentNode if the balance is between 1 and -1
        return currentNode;
    }


    private BinNode rotateRight(BinNode currentNode){
        if (currentNode == null || currentNode.getLeft() == null)
            return currentNode;
        BinNode left = currentNode.getLeft();
        currentNode.setLeft(left.getRight());
        left.setRight(currentNode);
        updateHeight(currentNode);
        updateHeight(left);
        return left;
    }

    private BinNode rotateLeft(BinNode currentNode){
        if (currentNode == null || currentNode.getRight() == null)
            return currentNode;
        BinNode right = currentNode.getRight();
        currentNode.setRight(right.getLeft());
        right.setLeft(currentNode);
        updateHeight(currentNode);
        updateHeight(right);
        return right;
    }

    @Override
    public void remove(String data) {
        super.setRoot(remove(super.getRoot(), data));
    }

    private BinNode remove(BinNode node, String data) {
        if (node == null) {
            return null;
        }
        int cmp = data.compareTo(node.getData());
        // Traverse left or right
        if (cmp < 0) {
            node.setLeft(remove(node.getLeft(), data));
        }
        else if (cmp > 0) {
            node.setRight(remove(node.getRight(), data));
        }
        else {
            // Found the node to remove
            // Case 1: No children
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
                // Use inorder predecessor (max in left subtree)
                BinNode replacement = node.getLeft();
                while (replacement.getRight() != null) {
                    replacement = replacement.getRight();
                }
                node.setData(replacement.getData());
                node.setLeft(remove(node.getLeft(), replacement.getData()));
            }
        }
        // Update height and balance on the way back up
        updateHeight(node);
        return balance(node);
    }
}

