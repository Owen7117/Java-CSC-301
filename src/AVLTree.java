
public class AVLTree extends BinaryTree {

    // Calls the BinaryTree's constructor
    public AVLTree() {
        super();
    }

    @Override
    public void insert(String data) {
        super.setRoot(insertNode(super.getRoot(), data));
    }

    private BinNode insertNode(BinNode currentNode, String data) {
        if (currentNode == null) {
            return new BinNode(data);
        }
        if (data.compareTo(currentNode.getData()) < 0) {
            currentNode.setLeft(insertNode(currentNode.getLeft(), data));
        } else if (data.compareTo(currentNode.getData()) > 0) {
            currentNode.setRight(insertNode(currentNode.getRight(), data));
        } else {
            return currentNode;
        }
        updateHeight(currentNode);
        return balance(currentNode);
    }

    private int height(BinNode currentNode) {
        if(currentNode == null){
            return 0;
        }
        else{
            return currentNode.getHeight();
        }
    }
    private void updateHeight(BinNode currentNode){
        currentNode.setHeight(Math.max(height(currentNode.getLeft()), height(currentNode.getRight())) + 1);
    }

    private int balanceDetector(BinNode currentNode){
        if (currentNode == null) return 0;
        return height(currentNode.getLeft()) - height(currentNode.getRight());
    }

    private BinNode balance(BinNode currentNode){
        int bd = balanceDetector(currentNode);

        if(bd > 1){
            if(balanceDetector(currentNode.getLeft()) < 0){
                currentNode.setLeft(rotateLeft(currentNode.getLeft()));
            }
            return rotateRight(currentNode);
        }
        if(bd > -1){
            if(balanceDetector(currentNode.getRight()) > 0){
                currentNode.setRight(rotateRight(currentNode.getRight()));
            }
            return rotateRight(currentNode);
        }
        return currentNode;
    }
    private BinNode rotateRight(BinNode currentNode){
        BinNode left = currentNode.getLeft();
        currentNode.setLeft(left.getRight());
        left.setRight(currentNode);
        updateHeight(currentNode);
        updateHeight(left);
        return left;
    }

    private BinNode rotateLeft(BinNode currentNode){
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
        int comparison = data.compareTo(node.getData());
        // Traverse left or right
        if (comparison < 0) {
            node.setLeft(remove(node.getLeft(), data));
        }
        else if (comparison > 0) {
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

