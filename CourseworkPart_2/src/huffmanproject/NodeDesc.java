package huffmanproject;

public class NodeDesc {
    
    private int freq;
    private String letters;


    public NodeDesc(int freq, String letters) {
        super();
        this.freq = freq;
        this.letters = letters;
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
    
    public String toString(){
        return letters + " " + freq;
    }
    
}//end of class
