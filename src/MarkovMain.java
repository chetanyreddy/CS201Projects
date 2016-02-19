import java.util.Scanner;

/**
 * Main for Markov Text Generation Program
 * 
 * @author ola
 * @date Jan 12, 2008
 *
 */
public class MarkovMain {
	public static void main(String[] args) {
		IModel model = new WordMarkovModel();
		SimpleViewer view = new SimpleViewer("Compsci 201 Markovian Text" +
				" Generation", "k count>");
		view.setModel(model);
		WordMarkovModel mark = new WordMarkovModel();
        mark.initialize(new Scanner("a a a a a a a a a a a a a a a a a a a a a"
                        + " a a a a a a a a a a a a a a a a a a"));
        mark.process("1");
        String output = "";
        while (output.length() < 8){
                output = mark.makeNGram(1, 30);
        }
        System.out.println(output);
	}
}

