package huffmanproject;

public class huffmanMain {
    public static void main(String[] args) {
        HuffmanConstructor huffmanTree = new HuffmanConstructor();
        huffmanTree.createTree(huffmanTree.readFile());
        huffmanTree.traverseHuffman();
        System.out.println("The decoded message is: " + huffmanTree.decodeMessage());
    }//end of main
}//end of class
