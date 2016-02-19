import java.util.Arrays;

import princeton.*;

/**
 * Simulate percolation thresholds for a grid-base system using depth-first-search,
 * aka 'flood-fill' techniques for determining if the top of a grid is connected
 * to the bottom of a grid.
 * <P>
 * Modified from the COS 226 Princeton code for use at Duke. The modifications
 * consist of supporting the <code>IPercolate</code> interface, renaming methods
 * and fields to be more consistent with Java/Duke standards and rewriting code
 * to reflect the DFS/flood-fill techniques used in discussion at Duke.
 * <P>
 * @author Kevin Wayne, wayne@cs.princeton.edu
 * @author Owen Astrachan, ola@cs.duke.edu
 * @author Jeff Forbes, forbes@cs.duke.edu
 */


public class PercolationDFS implements IPercolate {
	// possible instance variable for storing grid state
	public int[][] myGrid;

	/**
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
	public PercolationDFS(int n) {
		// TODO complete constructor and add necessary instance variables
		myGrid = new int[n][n];
		for(int i = 0; i < n; i++){
			Arrays.fill(myGrid[i], BLOCKED);
		}
		//for each row; fill entire row with Blocked
		//helpful method
	}

	public void open(int i, int j) {
		
		myGrid[i][j] = OPEN;
		for(int a = 0; a<myGrid[0].length;a++){
			for(int b = 0; b<myGrid[0].length;b++){
				if(isFull(a,b)){
					myGrid[a][b] = OPEN;
				}
		}
		}
		for (int c = 0; c< myGrid[0].length; c++){
			dfs(0,c);
		}
	}

	public boolean isOpen(int i, int j) {
		if(myGrid[i][j] == OPEN){
			return true;
			}
		else{
		return false;}
	}

	public boolean isFull(int i, int j) {
		if(myGrid[i][j] == FULL){
			return true;
			}
		else{
		return false;}
	}

	public boolean percolates() {
		// TODO: run DFS to find all full sites
		
		
		for (int j = 0; j< myGrid[0].length; j++){
			if(isFull(myGrid[0].length-1, j)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Private helper method to mark all cells that are open and reachable from
	 * (row,col).
	 * 
	 * @param row
	 *            is the row coordinate of the cell being checked/marked
	 * @param col
	 *            is the col coordinate of the cell being checked/marked
	 */
	private void dfs(int row, int col) {
		if (row < 0 || row >= myGrid[0].length || col < 0 || col >= myGrid[0].length){
			return;
		}
		if (isFull(row, col) || !isOpen(row, col)){
			return;
		}
		
		myGrid[row][col] = FULL;
		dfs(row-1,col);
		dfs(row, col-1);
		dfs(row+1, col);
		dfs(row, col+1);
		
	}

}
