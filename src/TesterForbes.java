import java.util.*;
public class TesterForbes {
	
	public static void main(String[] args){
		WordMarkovModel mark = new WordMarkovModel();
	    mark.initialize(new Scanner("a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a"));
	    mark.process("1");
	    String output = "";
	    while (output.length() < 8){
	            output = mark.makeNGram(1, 30);
	    }
	    System.out.println(output);
	}
	
	
}
