import java.util.ArrayList;
import java.util.HashMap;


public class MapMarkovModel extends MarkovModel{
	int newk = 0;
	HashMap<String, ArrayList<String>> updatemap;
	public HashMap<String, ArrayList<String>> newMap(String data, int k) {
		
		HashMap<String, ArrayList<String>> myMap = new HashMap<String, ArrayList<String>>();
		for(int i = 0; i < data.length() - k+1; i++){
			String temp = data.substring(i, i+k);
			ArrayList<String> tempVal = new ArrayList<String>();
			myMap.put(temp, tempVal) ;
		}
		for(int i = 0; i < data.length() - k; i++){
			String tempKey = data.substring(i, i+k);
			if(tempKey.length() < k){
				ArrayList<String> tempString = myMap.get(tempKey);
				tempString.add("#"); //eof character
				myMap.put(tempKey, tempString);
			}
			char tempchar = data.charAt(i+k);
			String nextchar = Character.toString(tempchar);
			ArrayList<String> nextgram = myMap.get(tempKey);
			nextgram.add(nextchar);
			//System.out.println(nextgram);
			myMap.put(tempKey,nextgram);
		}
			
		return myMap;}
	
		protected String makeNGram (int k, int maxLetters){
			if (k!=newk){
			newk = k;
			updatemap = newMap(myString, k);}
			
	        StringBuilder build = new StringBuilder();
	        int start = myRandom.nextInt(myString.length() - k + 1); //given
	        String seed = myString.substring(start, start + k); //initialize seed

	        for (int i = 0; i < maxLetters; i++) {
	           ArrayList<String> temps1 = updatemap.get(seed);
	           if(temps1.size() < 1){
	        	   return build.toString();
	           }
		       int int2 = myRandom.nextInt(temps1.size());
		       if(temps1.get(int2) == "#"){
		    	   return build.toString();
		       }
	           char seed2 =  temps1.get(int2).charAt(0); //newseed
	           seed = seed.substring(1, k) + seed2;
	          build.append(temps1.get(int2)); //nextkvaltobuildstring
	          
	        }
	        return build.toString();}} //finishmymap
	
		
		

	

