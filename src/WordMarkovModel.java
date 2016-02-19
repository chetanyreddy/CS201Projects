import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WordMarkovModel extends MarkovModel{
	int newk = 0;
	HashMap<WordNgram, ArrayList<WordNgram>> finalmap = new HashMap<WordNgram, ArrayList<WordNgram>>();
	public void wordmap(String[] data, int k) {
		WordNgram tempgram = new WordNgram(data,0,k);
		for(int i = 0; i < data.length - (k); i++){
			WordNgram mykey = new WordNgram(data, i, k);
			tempgram = new WordNgram(data, i+1, k);
			if(!finalmap.containsKey(mykey)){
				ArrayList<WordNgram> vals = new ArrayList<WordNgram>();
				vals.add(tempgram);
				finalmap.put(mykey,vals);
			}
			else {
				finalmap.get(mykey).add(tempgram);
			}
		}
		if(!finalmap.containsKey(tempgram)){
		finalmap.put(tempgram, null);}
		//System.out.println(finalmap);
	}
	protected String makeNGram (int k, int maxLetters){
		if (k!=newk){
			newk = k;
			wordmap(myString.split("\\s+"), k);
		}
		StringBuilder build = new StringBuilder();
		String [] words = myString.split("\\s+");
		int start = myRandom.nextInt(words.length - k + 1);
		WordNgram seed1 = new WordNgram(words, start, k);
		for (int i = 0; i < maxLetters; i++) {
			ArrayList<WordNgram> temps1 = finalmap.get(seed1);
			if(temps1 == null) {
				return build.toString().trim();
			}
			int int3 = myRandom.nextInt(temps1.size());
			WordNgram picked = temps1.get(int3);
			build.append(picked.wordat(k-1) + " ");
			seed1 = picked;
		}
		return build.toString().trim();	
	}
}