package huffmanproject;

public class Node {
    
    private Node leftChild,rightChild;
    private int freq;
    private String letters;


    public Node(Node leftChild, Node rightChild, int freq, String letters) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.freq = freq;
        this.letters = letters;
    }//end of constructor
    
    public Node(int freq,String letters){
        this.freq = freq;
        this.letters = letters;
    }//end of constructor
    
    public String toString(){
        return "The String is: " +  letters + " The Freqency is: " + freq; 
    }//end of toString

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public int getFreq() {
        return freq;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getLetters() {
        return letters;
    }
}//end of Node
