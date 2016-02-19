import java.util.Arrays;

/**
 * Simulate a system to see its Percolation Threshold, but use a UnionFind
 * implementation to determine whether simulation occurs. The main idea is that
 * initially all cells of a simulated grid are each part of their own set so
 * that there will be n^2 sets in an nxn simulated grid. Finding an open cell
 * will connect the cell being marked to its neighbors --- this means that the
 * set in which the open cell is 'found' will be unioned with the sets of each
 * neighboring cell. The union/find implementation supports the 'find' and
 * 'union' typical of UF algorithms.
 * <P>
 * 
 * @author Owen Astrachan
 * @author Jeff Forbes
 *
 */

public class PercolationUF implements IPercolate {
	private final int OUT_BOUNDS = -1;
	public int[][] myGrid;
	IUnionFind myUniter;
	int sink;
	int source;



	/**
	 * Constructs a Percolation object for a nxn grid that uses unionThing to
	 * store sets representing the cells and the top/source and bottom/sink
	 * virtual cells
	 */
	public PercolationUF(int n, IUnionFind unionThing) {
		// TODO complete PercolationUF constructor
		myGrid = new int[n][n];
		myUniter = unionThing;
		myUniter.initialize(n*n+2);
		source = n*n;
		sink = n*n+1;

	}

	/**
	 * Return an index that uniquely identifies (row,col), typically an index
	 * based on row-major ordering of cells in a two-dimensional grid. However,
	 * if (row,col) is out-of-bounds, return OUT_BOUNDS.
	 */
	public int getIndex(int row, int col) {
		// TODO complete getIndex
		if (row < 0 || row >= myGrid[0].length || col < 0 || col >= myGrid[0].length){
			return OUT_BOUNDS;
		}		
		else{
			int temp = row*myGrid[0].length+col;
			return temp;
		}
	}

	public void open(int i, int j) {
		// TODO complete open
		//connect everything around the cell (unions)
		myGrid[i][j] = OPEN;
		if(i== myGrid.length - 1){
			myUniter.union(getIndex(i,j), sink);
		}
		if(i== 0){
			myUniter.union(getIndex(i,j), source);
		}

		connect(i,j);
	}

	public boolean isOpen(int i, int j) {
		// TODO complete isOpen
		if(isFull(i,j) == true){
			return true;
		}
		else{
			return (myGrid[i][j] == OPEN);}

	}

	public boolean isFull(int i, int j) {
		// TODO complete isFull
		return myUniter.connected(getIndex(i,j), source);
	}

	public boolean percolates() {
		return myUniter.connected(source, sink);
	}

	/**
	 * Connect new site (row, col) to all adjacent open sites
	 */
	private void connect(int row, int col) {
		// TODO complete connect
		// union w/ 4 adjacents using checks for out of bounds and if cell is open
		//return if out of bounds
		if (row < 0 || row >= myGrid[0].length || col < 0 || col >= myGrid[0].length){
			return;
		}
		if(!isOpen(row,col)){
			return;
		}

		int memsave = getIndex(row, col);
		if(getIndex(row-1,col) != OUT_BOUNDS && isOpen(row-1,col) == true ){
			myUniter.union(memsave, getIndex(row -1 , col));
		}
		if(getIndex(row+1,col) != OUT_BOUNDS && isOpen(row+1,col)== true){
			myUniter.union(memsave, getIndex(row+1 , col));
		}
		if(getIndex(row,col-1) != OUT_BOUNDS && isOpen(row,col-1)== true){
			myUniter.union(memsave, getIndex(row,col-1));
		}
		if(getIndex(row,col+1) != OUT_BOUNDS && isOpen(row,col+1)== true){
			myUniter.union(memsave, getIndex(row,col+1));
		}


	}

}
