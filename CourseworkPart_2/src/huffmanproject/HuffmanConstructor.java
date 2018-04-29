package huffmanproject;

/**
 * This class contains the nesesary methods for decoding the message
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @param freqList a frequency list that is used to find and record the two smallest element
 * @param recordTrees is used to maintain the trees for the huffman codes
 * @param codes is used to keep the codes for the letters
 * @param headNode the root node of the tree
 * @param treeSize the size of the tree
 */
public class HuffmanConstructor {
    
    private List<NodeDesc> freqList = new LinkedList<NodeDesc>();
    private HashMap<String,BinaryTree> recordTrees = new HashMap<String,BinaryTree>();
    private HashMap<String,String> codes = new HashMap<String,String>();
    private String headNode = "";
    private int treeSize = 0;
    
    public HuffmanConstructor() {
        //empty constructor for the tree
    }//end of constructor
    
    /**
     * This method reads the file "freqFile.txt" and returns the number of letter that the file has
     */
    public int readFile(){
        
        try{
        
            FileReader fileReader = new FileReader("freqFile.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line = bufferedReader.readLine();
            String delims = "[,:]";
            String[] str = line.split(delims);
            
            for(int i=0;i < str.length;i+=2){
                freqList.add(new NodeDesc(Integer.parseInt(str[i+1]),str[i]));
                treeSize++;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return treeSize - 1;
        
    }//end of readFile
    
    /**
     *Finds the to smallest elements in the freqList
     * @return an array of NodeDesc that contains information about the nodes
     */
    public NodeDesc[] findSmallest(){

        int first,second;
        int posF,posS;
        NodeDesc [] smallestElements = new NodeDesc[3];
        String firstSeq = "",secondSeq = "";
        
        first = second = Integer.MAX_VALUE;
        posF=posS=0;
        
            for (int i = 0; i < freqList.size(); i ++){
                if (freqList.get(i).getFreq() < first ){
                    
                    second = first;
                    posS=posF;
                    first = freqList.get(i).getFreq();
                    posF=i;
                    secondSeq=firstSeq;
                    firstSeq = freqList.get(i).getLetters();
                    
                }else if ((freqList.get(i).getFreq() < second)){
                    
                    second = freqList.get(i).getFreq();
                    posS=i;
                    secondSeq = freqList.get(i).getLetters();
                    
                }
                
            }//end of loop
            
            //if the first and second smallest element are not adjacent then keep the relative position of all elements
            
            if(posF-posS>1){
                
                smallestElements[0]= new NodeDesc(freqList.get(posS).getFreq(),freqList.get(posS).getLetters());
                smallestElements[1]= new NodeDesc(freqList.get(posF).getFreq(),freqList.get(posF).getLetters());
                freqList.add(posF+1,new NodeDesc(freqList.get(posS).getFreq() + freqList.get(posF).getFreq(),freqList.get(posS).getLetters() + freqList.get(posF).getLetters()));
                smallestElements[2]= new NodeDesc(freqList.get(posF+1).getFreq(),freqList.get(posF+1).getLetters());
                freqList.remove(posF);
                freqList.remove(posS);
                
            }else{
                
                smallestElements[0]= new NodeDesc(freqList.get(posF).getFreq(),freqList.get(posF).getLetters());
                smallestElements[1]= new NodeDesc(freqList.get(posS).getFreq(),freqList.get(posS).getLetters());
                freqList.add(posS,new NodeDesc(freqList.get(posF).getFreq() + freqList.get(posS).getFreq(),freqList.get(posF).getLetters() + freqList.get(posS).getLetters()));
                smallestElements[2]= new NodeDesc(freqList.get(posS).getFreq(),freqList.get(posS).getLetters());
                freqList.remove(posF);
                freqList.remove(posS);
            
            }
            
        return smallestElements;
        
    }//end of findSmallest
    
    /**
     *Creates the trees and puts the in a HashMap for later proccesing 
     * @param treesToCreate the trees that are going to be created 
     */
    public void createTree(int treesToCreate){
        
        NodeDesc[] nodes = new NodeDesc[3];
        BinaryTree newTree;
        
        while(treesToCreate!=0){
            
            nodes=findSmallest();
            newTree = new BinaryTree(new Node(new Node(nodes[0].getFreq(),nodes[0].getLetters()),new Node(nodes[1].getFreq(),nodes[1].getLetters()),nodes[2].getFreq(),nodes[2].getLetters()));
            recordTrees.put(nodes[2].getLetters(),newTree);
            treesToCreate--;
        
        }//outer loop
        
        headNode = nodes[2].getLetters();
        
    }//end of createTree
    
    /**
     * Calls the method getCode for all nodes in order to find the coresponding codes for every letter
     */
    public void traverseHuffman(){
        
        String code="";
        
        for(int i =0;i<treeSize;i++){
            code = this.getCode(headNode,headNode.charAt(i));
            codes.put(String.valueOf(headNode.charAt(i)), code);
        }

    }//end of traverseHuffman
    
    /**
     *
     * @param root the head of the tree
     * @param letter the letter that we are trying to find the code
     * @return the code of the letter by traversing the tree
     */
    public String getCode(String root,char letter){
        String code="";
        String currentHead= root;
        boolean done =true;

        
        while(done){
            
            if(recordTrees.get(currentHead).getRoot().getLeftChild().getLetters().contains(String.valueOf(letter))){
                
                currentHead=recordTrees.get(currentHead).getRoot().getLeftChild().getLetters();
                code= code + "0";

                if(currentHead.equals(String.valueOf(letter))){
                    return code;
                }  
            }
            else if(recordTrees.get(currentHead).getRoot().getRightChild().getLetters().contains(String.valueOf(letter))){
                
                currentHead=recordTrees.get(currentHead).getRoot().getRightChild().getLetters();
                code= code + "1";

                if(currentHead.equals(String.valueOf(letter))){
                    return code;
                }
            }
        }//end of the loop
        
        return code;
   
    }//end of getCode
    
    /**
     * Reads the encoded message from the file "bitString.txt"
     */
    public String readBitString(){
        
        String line = "";
        
        try{
            
        
            FileReader fileReader = new FileReader("bitString.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            line = bufferedReader.readLine();
            String delims = "[,:]";
            String[] str = line.split(delims);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return line;
        
    }//end of readBitString
    
    /**
     *This method decodes the bitString and returns the decoded message
     * @return the decoded message
     */
    public String decodeMessage(){
        
        String result="";
        String message = readBitString();
        
        int maxBits=0;
        int minBits=Integer.MAX_VALUE;
        
        for(String key:codes.keySet()){
        
            if(maxBits<codes.get(key).length()){
                maxBits=codes.get(key).length();
            }
                    
            if(minBits>codes.get(key).length()){
                minBits=codes.get(key).length();
            }

        }//end of loop
        
        while(message.isEmpty()==false){

            for(String key:codes.keySet()){
                
                if(message.isEmpty()==false){
                    
                    if(codes.get(key).length()==minBits){
                        if(message.substring(0, minBits).equals(codes.get(key))){
                            result=result+key;
                            message = message.replaceFirst(codes.get(key),"");
                        }
                    }
                    
                    if(codes.get(key).length()>minBits && codes.get(key).length()<maxBits){
                        if(message.substring(0, codes.get(key).length()).equals(codes.get(key))){
                            result=result+key;
                            message = message.replaceFirst(codes.get(key),"");
                        }
                    }
                
                    if(codes.get(key).length()==maxBits){
                        if(message.substring(0, maxBits).equals(codes.get(key))){
                            result=result+key;
                            message =  message.replaceFirst(codes.get(key),"");
                        }
                    }
                }else{
                    return result;
                }
            }//inner loop
        }//outer loop
        
        return result;
        
    }//end of decodeMessage

}//end of class
