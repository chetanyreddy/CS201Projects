import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class HuffProcessor implements Processor {
	int[] myArray = new int[ALPH_SIZE];
	String[] myArray2 = new String[ALPH_SIZE+1];

	@Override
	public void compress(BitInputStream in, BitOutputStream out) {
		// TODO Auto-generated method stub
		
		// count characters in file
		int temp = in.readBits(BITS_PER_WORD);
		while(temp != -1){
			myArray[temp]++;
			temp = in.readBits(BITS_PER_WORD);
		}
		in.reset();
		
		//create huffman tree
		PriorityQueue<HuffNode> myPQ = new PriorityQueue<HuffNode>();
		for(int i = 0; i<ALPH_SIZE;i++){
			if (myArray[i]!=0){
				myPQ.add(new HuffNode(i, myArray[i]));
			}		
		}
		myPQ.add(new HuffNode(PSEUDO_EOF, 0));
	
		while(myPQ.size() > 1){
			HuffNode temp1 = myPQ.poll();
			HuffNode temp2 = myPQ.poll();
			int combWeight = temp1.weight() + temp2.weight();
			myPQ.add(new HuffNode(-1, combWeight, temp1, temp2));
		}
		HuffNode root = myPQ.poll();
		extractCodes(root, "");
		out.writeBits(BITS_PER_INT, HUFF_NUMBER);
		writeHeader(root, out);		
	
		//compress and write the body
		int temp1 = in.readBits(BITS_PER_WORD);
		while(temp1 != -1){
			String code = myArray2[temp1];
			out.writeBits(code.length(), Integer.parseInt(code, 2));
			temp1 = in.readBits(BITS_PER_WORD);
		}
		//write the pseudo EOF	
		
			String eofCode = myArray2[PSEUDO_EOF];
			out.writeBits(eofCode.length(), Integer.parseInt(eofCode, 2));
	}
	//}
	//traverse tree and extract code
	private void extractCodes(HuffNode current, String path){
		if(current.right() == null && current.left() == null){
			myArray2[current.value()] = path;
			return;
		}
		extractCodes(current.left(), path + 0);
		extractCodes(current.right(), path + 1);

	}
	//write the header
	private void writeHeader(HuffNode current, BitOutputStream out){
		if(current.right() == null && current.left() == null){
			out.writeBits(1,1);
			out.writeBits(9, current.value()); 
			return;
		}
		out.writeBits(1, 0);
		writeHeader(current.left(), out);
		writeHeader(current.right(), out);
	}

	@Override
	public void decompress(BitInputStream in, BitOutputStream out) {
		// TODO Auto-generated method stub
		//check for huff number
		if(in.readBits(BITS_PER_INT) != HUFF_NUMBER){
			throw new HuffException("Huff Number is not found");
		}
		//recreate tree from header
		HuffNode root = readHeader(in);
		HuffNode current  = root;
		int temp3 = in.readBits(1);
		while(temp3 != -1){
			if (temp3 == 1){
				current = current.right();
			}
			else{
				current = current.left();
			}
			
			if(current.right() == null && current.left() == null){
				if (current.value() == PSEUDO_EOF){
					return;
				}
				else{
					out.writeBits(8, current.value());
					current = root;
				}
			}
			temp3 = in.readBits(1);
		}
		
		throw new HuffException("Problem with the PSEUDO_EOF");
		
	}
	
	private HuffNode readHeader(BitInputStream in){
		if(in.readBits(1) == 0){
				HuffNode left = readHeader(in);
				HuffNode right = readHeader(in);
				int weight1 = left.weight() + right.weight();
				return new HuffNode(-1, weight1, left, right); //figure out first number
	}
		else{
			return new HuffNode(in.readBits(9), 100);
		}

}
}
