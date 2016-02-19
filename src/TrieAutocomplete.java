import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * General trie/priority queue algorithm for implementing Autocompletor
 * 
 * @author Austin Lu
 *
 */
public class TrieAutocomplete implements Autocompletor {

	/**
	 * Root of entire trie
	 */
	protected Node myRoot;

	/**
	 * Constructor method for TrieAutocomplete. Should initialize the trie
	 * rooted at myRoot, as well as add all nodes necessary to represent the
	 * words in terms.
	 * 
	 * @param terms
	 *            - The words we will autocomplete from
	 * @param weights
	 *            - Their weights, such that terms[i] has weight weights[i].
	 * @throws a
	 *             NullPointerException if either argument is null
	 */
	public TrieAutocomplete(String[] terms, double[] weights) {
		if (terms == null || weights == null)
			throw new NullPointerException("One or more arguments null");
		// Represent the root as a dummy/placeholder node
		myRoot = new Node('-', null, 0);

		for (int i = 0; i < terms.length; i++) {
			add(terms[i], weights[i]);
		}
	}

	/**
	 * Add the word with given weight to the trie. If word already exists in the
	 * trie, no new nodes should be created, but the weight of word should be
	 * updated.
	 * 
	 * In adding a word, this method should do the following: Create any
	 * necessary intermediate nodes if they do not exist. Update the
	 * subtreeMaxWeight of all nodes in the path from root to the node
	 * representing word. Set the value of myWord, myWeight, isWord, and
	 * mySubtreeMaxWeight of the node corresponding to the added word to the
	 * correct values
	 * 
	 * @throws a
	 *             NullPointerException if word is null
	 * @throws an
	 *             IllegalArgumentException if weight is negative.
	 * 
	 */
	private void add(String word, double weight) {
		// TODO: Implement add
		if(word == null){
			throw new NullPointerException();
		}
		if(weight < 0){
			throw new IllegalArgumentException();
		}

		Node current = myRoot;
		for(char ch : word.toCharArray()){
			if(current.mySubtreeMaxWeight  < weight){
				current.mySubtreeMaxWeight = weight;
			}
			if(!current.children.containsKey(ch)){
				current.children.put(ch, new Node(ch, current, weight));
			}
			current = current.children.get(ch);
		}

		//current.myWeight = weight;
		current.setWord(word);

		current.setWeight(weight);

		current.isWord = true;

		if(current.mySubtreeMaxWeight < weight){
			current.mySubtreeMaxWeight = weight;
		}

		//duplicate nonsense

		if(current.myWeight > weight){

			while(current!=myRoot){
				double maxWeight = current.myWeight;


				for(char ch : current.parent.children.keySet()){
					if(current.parent.children.get(ch).mySubtreeMaxWeight > maxWeight){
						maxWeight = current.parent.children.get(ch).mySubtreeMaxWeight;
					}
				}

				current.parent.mySubtreeMaxWeight = maxWeight;
				current = current.parent;

			}
		}


	}



	@Override
	/**
	 * Required by the Autocompletor interface. Returns an array containing the
	 * k words in the trie with the largest weight which match the given prefix,
	 * in descending weight order. If less than k words exist matching the given
	 * prefix (including if no words exist), then the array instead contains all
	 * those words. e.g. If terms is {air:3, bat:2, bell:4, boy:1}, then
	 * topKMatches("b", 2) should return {"bell", "bat"}, but topKMatches("a",
	 * 2) should return {"air"}
	 * 
	 * @param prefix
	 *            - A prefix which all returned words must start with
	 * @param k
	 *            - The (maximum) number of words to be returned
	 * @return An array of the k words with the largest weights among all words
	 *         starting with prefix, in descending weight order. If less than k
	 *         such words exist, return an array containing all those words If
	 *         no such words exist, return an empty array
	 * @throws a
	 *             NullPointerException if prefix is null
	 */
	public String[] topKMatches(String prefix, int k) {
		// TODO: Implement topKMatches
		if(prefix.equals(null)){
			throw new NullPointerException();
		}
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Node.ReverseSubtreeMaxWeightComparator());
		ArrayList<Node> al = new ArrayList<Node>();
		Node current = myRoot;
		for(char c : prefix.toCharArray()) {
			if(!current.children.containsKey(c)) {
				return new String[0];
			}
			current = current.getChild(c);
		}
		pq.add(current);
		while(!pq.isEmpty()){
			Collections.sort(al, Collections.reverseOrder());
			if(al.size() > k){
				if(pq.peek().mySubtreeMaxWeight < al.get(k).myWeight){
					break;
				}
			}


			current = pq.remove();
			if(current.isWord){
				al.add(current);
			}
			for(char ch: current.children.keySet()){
				pq.add(current.children.get(ch));
			}
		}

		if(al.size() <= k){
			String [] answer = new String[al.size()];
			for(int i = 0; i< al.size(); i++){
				answer[i] = al.get(i).myWord;
			}
			return answer;
		}
		else{
			String [] answer = new String[k];
			for(int i = 0; i< k; i++){
				answer[i] = al.get(i).myWord;
			}
			return answer;
		}



	}

	@Override
	/**
	 * Given a prefix, returns the largest-weight word in the trie starting with
	 * that prefix.
	 * 
	 * @param prefix
	 *            - the prefix the returned word should start with
	 * @return The word from _terms with the largest weight starting with
	 *         prefix, or an empty string if none exists
	 * @throws a
	 *             NullPointerException if the prefix is null
	 * 
	 */
	public String topMatch(String prefix) {
		// TODO: Implement topMatch
		if(prefix.equals(null)){
			throw new NullPointerException();
		}
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Node.ReverseSubtreeMaxWeightComparator());

		Node current = myRoot;
		for(char c : prefix.toCharArray()) {
			if(!current.children.containsKey(c)) {
				return "";}
			current = current.getChild(c);
		}


		while(current.mySubtreeMaxWeight != current.myWeight){

			for(Node child: current.children.values()){

				pq.add(child);

			}
			current = pq.remove();


		}


		return current.myWord;

	}

	public void toPrint(String k, Node a){
		System.out.println(k + a.myInfo + " " + a.myWeight + " " + a.mySubtreeMaxWeight);
		for (char ch : a.children.keySet()){
			toPrint(k + " ", a.children.get(ch));
		}
	}	
}
