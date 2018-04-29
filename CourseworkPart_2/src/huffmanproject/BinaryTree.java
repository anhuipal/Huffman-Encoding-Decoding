package huffmanproject;

public class BinaryTree {
    
    private Node root;

    public BinaryTree(Node root) {
        super();
        this.root = root;
    }//end of constructor

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }
    
    public boolean isLeaf(){
        if(root.getLeftChild()== null){
            return true;
        }else if(root.getRightChild()==null){
            return true;
        }
        else{ return false;}
    }//end of isLeaf
    
}//end of BinaryTree
