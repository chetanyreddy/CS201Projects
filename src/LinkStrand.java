import java.util.ArrayList;
import java.util.Stack;

public class LinkStrand implements IDnaStrand {
	public class Node { 
	     String info; 
	     Node next; 

	    Node(String s){ 
	      info = s; 
	      next = null; 
	 } 
	} 
	private Node myFirst, myLast; //first and last nodes of list 
	private long mySize; //# nucleotides in DNA 
	private long myAppends;

	/**
	 * Create a strand representing an empty DNA strand, length of zero.
	 */
	public LinkStrand() {
		// TODO: Implement this method
		this("");
		}

	/**
	 * Create a strand representing s. No error checking is done to see if s
	 * represents valid genomic/DNA data.
	 * 
	 * @param s
	 *            is the source of cgat data for this strand
	 */
	public LinkStrand(String s) {
		// TODO: Implement this method
		Node a = new Node(s);
		myFirst = a;
		myLast = a;
		mySize = s.length();
		myAppends = 0;
		
	}

	/**
	 * Cut this strand at every occurrence of enzyme, essentially replacing
	 * every occurrence of enzyme with splicee.
	 * 
	 * @param enzyme
	 *            is the pattern/strand searched for and replaced
	 * @param splicee
	 *            is the pattern/strand replacing each occurrence of enzyme
	 * @return the new strand leaving the original strand unchanged.
	 */
	@Override
	public IDnaStrand cutAndSplice(String enzyme, String splicee) {
		// TODO: Implement this method
		if(myFirst.next != null) {
			throw new RuntimeException("link strand has more than one node");
			}
		else{
			int pos = 0;
			int start = 0;
			String search = myFirst.info;
			boolean first = true;
			LinkStrand ret = null;
			while ((pos = search.indexOf(enzyme, pos)) >= 0) {
				if (first) {
					ret = new LinkStrand(search.substring(start, pos));
					first = false;
				} else {
					ret.append(search.substring(start, pos));

				}
				start = pos + enzyme.length();
				ret.append(splicee);
				pos++;
			}

			if (start < search.length()) {
				// NOTE: This is an important special case! If the enzyme
				// is never found, return an empty String.
				if (ret == null) {
					ret = new LinkStrand("");
				} else {
					ret.append(search.substring(start));
				}
			}
			return ret;
		}
		
	}

	/**
	 * Initialize this strand so that it represents the value of source. No
	 * error checking is performed.
	 * 
	 * @param source
	 *            is the source of this enzyme
	 */
	@Override
	public void initializeFrom(String source) {
		// TODO: Implement this method
		Node a = new Node(source);
		myFirst = a;
		myLast = a;
		mySize += source.length();
		
	}

	/**
	 * Returns the number of nucleotides/base-pairs in this strand.
	 */
	@Override
	public long size() {
		// TODO: Implement this method
		return mySize;
	}

	/**
	 * Returns the sequence of DNA this object represents as a String
	 * 
	 * @return the sequence of DNA this represents
	 */
	@Override
	public String toString() {
		// TODO: Implement this method
		Node a = myFirst;
		String temp = "";
		while(a!=null){
			temp+=a.info;
			a = a.next;
		}
		return temp;
	}

	/**
	 * Return some string identifying this class.
	 * 
	 * @return a string representing this strand and its characteristics
	 */
	@Override
	public String strandInfo() {
		// TODO: Implement this method
		return this.getClass().getName();
	}

	/**
	 * Append a strand of DNA to this strand. If the strand being appended is
	 * represented by a LinkStrand object then an efficient append is performed.
	 * 
	 * @param dna
	 *            is the strand being appended
	 */
	@Override
	public IDnaStrand append(IDnaStrand dna) {
		// TODO: Implement this method
		if(dna instanceof LinkStrand){
			LinkStrand castlink = (LinkStrand) dna;
			myLast.next = castlink.myFirst;
			myLast = castlink.myLast;
			myAppends++;
			return this;
		}
		else{
			String temp = dna.toString();
			myAppends++;
			return append(temp);
		}

	}

	/**
	 * Simply append a strand of dna data to this strand.
	 * 
	 * @param dna
	 *            is the String appended to this strand
	 */
	@Override
	public IDnaStrand append(String dna) {
		// TODO: Implement this method
		mySize += dna.length();
		Node c = new Node(dna);
		myLast.next = c;
		myLast = c;
		myAppends++;
		return this;
	}

	/**
	 * Returns an IDnaStrand that is the reverse of this strand, e.g., for
	 * "CGAT" returns "TAGC"
	 * 
	 * @return reverse strand
	 */
	@Override
	public IDnaStrand reverse() {
		// TODO: Implement this method
		Stack<String> myStack = new Stack<String>();
		LinkStrand mydna = new LinkStrand();
		Node a = myFirst;
		while (a != null){
			String temp = a.info;
			String strReversed = new StringBuilder(temp).reverse().toString();
			myStack.push(strReversed);
			a = a.next;
		}
		Node c = new Node(myStack.pop());
		mydna.myFirst = c;
		mydna.myLast = c;

		while (!myStack.isEmpty()){
			Node tempnode = new Node(myStack.pop());
			mydna.myLast.next = tempnode;
			mydna.myLast = tempnode;
		}
		//System.out.println(mydna);
		return mydna;
	}

	/**
	 * Returns a string that can be printed to reveal information about what
	 * this object has encountered as it is manipulated by append and
	 * cutAndSplice.
	 * 
	 * @return
	 */
	@Override
	public String getStats() {
		// TODO: Implement this method
		return String.format("# append calls = %d", myAppends);
	}

	/**
	 * Returns an ArrayList of Strings corresponding to the nodes in the linked
	 * list this class contains. That is, the first value of the list should be
	 * the info within the head of the linked list, the second value should be
	 * the info within the node the head points to, etc. The ArrayList returned
	 * should be generated at the time the method is called. That is, the
	 * ArrayList should have a scope of only this method (i.e. not global)
	 * 
	 * @return list of Strings corresponding to nodes
	 */
	public ArrayList<String> nodeList() {
		// TODO: Implement this method
		ArrayList<String> answer = new ArrayList<String>();
		Node a = myFirst;
		while(a != null){
			answer.add(a.info);
			a = a.next;
		}
		return answer;
	}
}
