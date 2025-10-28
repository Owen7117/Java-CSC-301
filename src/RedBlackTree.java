// Owen O'Neil
// 10/16/25
//Program 2: AVL and Red Black Trees

public class RedBlackTree extends BinaryTree{

    private static final boolean red = true;
    private static final boolean black = false;

    public RedBlackTree() {
        super();
    }

    private boolean isRed(BinNode currentNode){
        if(currentNode == null){return false;}
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
        if(currentNode == null){
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

    private BinNode fixTree(BinNode currentNode){
        // First Case - Right child is red and the left child is black
        if(isRed(currentNode.getRight()) && !isRed(currentNode.getLeft())){
            currentNode = rotateLeft(currentNode);
        }
        // Second Case - Left child and its left child are both red
        if(isRed(currentNode.getLeft()) && isRed(currentNode.getLeft().getLeft())){
            currentNode = rotateRight(currentNode);
        }
        // Third case - Both children are red so flip colors
        if(isRed(currentNode.getLeft()) && isRed(currentNode.getRight())){
            flipColors(currentNode);
        }
        return currentNode;
    }
    /*
    Although this is very much like the AVL rotation, I got really tripped up for some
    reason with the rotations for these, so I referenced chat.gpt
    */
    private BinNode rotateLeft(BinNode currentNode){
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

    private void changeColor(BinNode currentNode){
        if(currentNode.getColor() == red){
            currentNode.setColor(black);
        }
        else if(currentNode.getColor() == black){
            currentNode.setColor(red);
        }
    }
    private void flipColors(BinNode currentNode){
        changeColor(currentNode);
        changeColor(currentNode.getLeft());
        changeColor(currentNode.getRight());
    }
}
