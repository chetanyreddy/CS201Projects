import java.util.Arrays;

/*
 * This class encapsulates N words/strings so that the
 * group of N words can be treated as a key in a map or an
 * element in a set, or an item to be searched for in an array.
 * <P>
 * @author Chetan Reddy
 */

public class WordNgram implements Comparable<WordNgram> {

	private String[] myWords;

	/*
	 * Store the n words that begin at index start of array list as the N words
	 * of this N-gram.
	 * 
	 * @param list contains at least n words beginning at index start
	 * 
	 * @start is the first of the N worsd to be stored in this N-gram
	 * 
	 * @n is the number of words to be stored (the n in this N-gram)
	 */
	public WordNgram(String[] list, int start, int n) {
		myWords = new String[n];
		System.arraycopy(list, start, myWords, 0, n);
	}

	/**
	 * Return value that meets criteria of compareTo conventions.
	 * 
	 * @param wg
	 *            is the WordNgram to which this is compared
	 * @return appropriate value less than zero, zero, or greater than zero
	 */
	public int compareTo(WordNgram wg) {
		// TODO implement compareTo
		
		for(int i = 0; i<wg.myWords.length;i++){
			if(myWords[i].compareTo(wg.myWords[i]) == 0){
				continue;
			}
			else{
				return myWords[i].compareTo(wg.myWords[i]);
			}
		}
		return 0;
	}
	public WordNgram(String[] list) {
		myWords = new String[list.length];
		System.arraycopy(list, 0, myWords, 0, list.length);
		}

	/**
	 * Return true if this N-gram is the same as the parameter: all words the
	 * same.
	 * 
	 * @param o
	 *            is the WordNgram to which this one is compared
	 * @return true if o is equal to this N-gram
	 */
	public boolean equals(Object o) {
		WordNgram wg = (WordNgram) o;
		// TODO return correct value
		if(compareTo(wg) != 0){
			return false;
		}
		return true;
	}

	/**
	 * Returns a good value for this N-gram to be used in hashing.
	 * 
	 * @return value constructed from all N words in this N-gram
	 */
	public int hashCode() {
		int sum = 0;
		for(int i = 0; i<myWords.length;i++){
			sum += 101*sum + myWords[i].hashCode();
		}

		return sum;
		
	}
	
	public String[] wordtostring(){
		return myWords;
		
	}
	public String wordat(int k){
		return myWords[k];
		
	}
	
	public String toString(){
		return Arrays.toString(myWords);
	}
	
	public int lensize(){
		return myWords.length;
		
	}
}
