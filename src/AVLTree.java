// Owen O'Neil
// 10/16/25
//Program 2: AVL and Red Black Trees
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
        // Recursively insert the node and update the root
        super.setRoot(insertNode(super.getRoot(), data));
    }

    // Recursive insertion with balancing
    private BinNode insertNode(BinNode currentNode, String data) {
        // The base case, if the node is null insert a new node
        if(currentNode == null) {
            BinNode newNode = new BinNode(data);
            // leaf node height is 1
            newNode.setHeight(1);
            return newNode;
        }
        // Same insert logic as binary tree
        if(data.compareTo(currentNode.getData()) < 0) {
            currentNode.setLeft(insertNode(currentNode.getLeft(), data));
        } else if(data.compareTo(currentNode.getData()) > 0) {
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
        if(currentNode == null)
            return 0;
        // Returns the calculated balance of the tree
        return height(currentNode.getLeft()) - height(currentNode.getRight());
    }

    // Balanced the tree if necessary
    /*
    Chat.gpt helped me understand that to do a double rotation you need to you need to traverse to the current nodes
    child and call the rotate method setting the current node as its child (this allows for a single rotation after
    competition)
     */
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

    // Right rotation on the subtree with the root as the currentNode
    private BinNode rotateRight(BinNode currentNode){
        // If the node is null of doesn't have a left child, you cant preform the rotation so return null
        if(currentNode == null || currentNode.getLeft() == null)
            return currentNode;
        // Stores the left child so we can make it the new root
        BinNode left = currentNode.getLeft();
        // Move the left child's left subtree to be the currentNodes left subtree
        currentNode.setLeft(left.getRight());
        // Make the currentNode the right child of the left node
        left.setRight(currentNode);
        // update the height of the current
        updateHeight(currentNode);
        // update the height of the left node
        updateHeight(left);
        // return the new root of the subtree
        return left;
    }

    // Left rotation on the subtree with the root as the currentNode
    private BinNode rotateLeft(BinNode currentNode){
        // If the node is null of doesn't have a left child, you cant preform the rotation so return null
        if(currentNode == null || currentNode.getRight() == null)
            return currentNode;
        // Stores the right child so we can make it the new root
        BinNode right = currentNode.getRight();
        // Move the right child's left subtree to be the currentNodes right subtree
        currentNode.setRight(right.getLeft());
        // Make the currentNode the left child of the right node
        right.setLeft(currentNode);
        // Update height of currentNode
        updateHeight(currentNode);
        // Update height of right node
        updateHeight(right);
        // Return the new root of the subtree
        return right;
    }

    // Remove method overrides the binary tree remove method
    @Override
    public void remove(String data) {
        super.setRoot(removeNode(super.getRoot(), data));
    }

    // Chat.gpt helped me understand the three cases for removal and that it works recursively
    private BinNode removeNode(BinNode currentNode, String data) {
        // The node is null
        // Base case
        if(currentNode == null) {
            return null;
        }
        // Compare the value to be deleted with the currentNode
        int cmp = data.compareTo(currentNode.getData());
        // If currentNode is less than 0 go left node
        if(cmp < 0) {
            currentNode.setLeft(removeNode(currentNode.getLeft(), data));
        }
        // If currentNode is greater than 0 go to the right node
        else if(cmp > 0) {
            currentNode.setRight(removeNode(currentNode.getRight(), data));
        }
        // Found node to remove
        else {
            // First case - No children
            // Just remove the leaf node
            if(currentNode.getLeft() == null && currentNode.getRight() == null) {
                return null;
            }
            // Second case - One child
            // Replace the node with its child
            else if(currentNode.getLeft() == null) {
                return currentNode.getRight();
            }
            else if(currentNode.getRight() == null) {
                return currentNode.getLeft();
            }
            // Third case - Two children
            else {
                // Traverse one node to the left
                BinNode replacement = currentNode.getLeft();
                // Keep going until you find the max node to the right of that node
                while(replacement.getRight() != null) {
                    replacement = replacement.getRight();
                }
                // Copy that value into the current node
                currentNode.setData(replacement.getData());
                // recursively delete that replacement node because it's a duplicate
                currentNode.setLeft(removeNode(currentNode.getLeft(), replacement.getData()));
            }
        }
        // Update height and balance on the way back up
        updateHeight(currentNode);
        return balance(currentNode);
    }
}

