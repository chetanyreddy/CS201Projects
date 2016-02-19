public class QuickUWPC implements IUnionFind {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of sites in subtree rooted at i
    private int count;      // number of components

    /**
     * Initializes an empty union-find data structure with <tt>N</tt> sites
     * <tt>0</tt> through <tt>N-1</tt>. Each site is initially in its own 
     * component.
     *
     * @param  N the number of sites
     * @throws IllegalArgumentException if <tt>N &lt; 0</tt>
     */
    public QuickUWPC(int N) {
       initialize(N);
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between <tt>1</tt> and <tt>N</tt>)
     */
    public int count() {
        return count;
    }
  
    /**
     * Returns the component identifier for the component containing site <tt>p</tt>.
     *
     * @param  p the integer representing one object
     * @return the component identifier for the component containing site <tt>p</tt>
     * @throws IndexOutOfBoundsException unless <tt>0 &le; p &lt; N</tt>
     */
    public int find(int p) {
//        validate(p);
//        while (p != parent[p])
//            p = parent[p];
//        return p;
//    }
//
//    // validate that p is a valid index
//    private void validate(int p) {
//        int N = parent.length;
//        if (p < 0 || p >= N) {
//            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));  
//        }
//    }
    	int root = p;
    	while(root != parent[root]){
    		root = parent[root];
    	}
    	while(p!=root){
    		int temp = parent[p];
    		parent[p] = root;
    		p = temp;
    	}
    	return root;
    	}

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt> are in the same component;
     *         <tt>false</tt> otherwise
     * @throws IndexOutOfBoundsException unless
     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing site <tt>p</tt> with the 
     * the component containing site <tt>q</tt>.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IndexOutOfBoundsException unless
     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

	@Override
	public void initialize(int n) {
		// TODO Auto-generated method stub
		count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
		
	}

	@Override
	public int components() {
		// TODO Auto-generated method stub
		return count;
	}


    

}
