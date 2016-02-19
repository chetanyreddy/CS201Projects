public class Ademola {
	
	public static void main(String args[]){
		int n = 10;
		String[] a = new String[n];
		double[] weights = new double[n];
		
		a[0] = "apes";
		weights[0] = 10;
		
		a[1] = "banana";
		weights[1] = 32;
		
		a[2] = "apes";
		weights[2] = 4;
		
		a[3] = "ape";
		weights[3] = 43;
		
		a[4] = "cats";
		weights[4] = 15;
		
		a[5] = "cat";
		weights[5] = 6;
		
		a[6] = "ape";
		weights[6] = 41;
		
		a[7] = "banana";
		weights[7] = 30;
		
		a[8] = "cat";
		weights[8] = 8;
		
		a[9] = "ban";
		weights[9] = 31;
		
		TrieAutocomplete b = new TrieAutocomplete(a, weights);
		
		System.out.println(b.myRoot.mySubtreeMaxWeight);
		//System.out.println(b.topMatch(""));
		//String[] topK = b.topKMatches("", 6);
//		for (String x : topK){
//			//System.out.println(x);
//		}
		
		b.toPrint("", b.myRoot);
		
	}
	
}
