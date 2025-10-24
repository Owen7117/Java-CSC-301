public class BinNode {
    private String data;
    private BinNode left;
    private BinNode right;
    private int height;
    private boolean color;

    public BinNode(){
        data = "";
        left = null;
        right = null;
        height = 0;
    }
    public BinNode(String d){
        data = d;
        left = null;
        right = null;
        height = 1;
    }

    public void setData(String d){
        this.data = d;
    }
    public String getData(){
        return this.data;
    }
    public void setLeft(BinNode l){
        this.left = l;
    }
    public BinNode getLeft(){
        return this.left;
    }
    public void setRight(BinNode r){
        this.right = r;
    }
    public BinNode getRight(){
        return this.right;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public int getHeight(){
        return height;
    }
    public void setColor(boolean color){
        this.color = color;
    }
    public boolean getColor(){
        return this.color;
    }
}