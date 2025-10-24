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
        fixed = fixTree(currentNode);
        return fixed;
    }

    private BinNode fixTree(BinNode currentNode){

    }

    private void changeColor(BinNode currentNode){
        if(currentNode.getColor() == red){
            currentNode.setColor(black);
        }
        else if(currentNode.getColor() == black){
            currentNode.setColor(red);
        }
    }
}
