import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import princeton.*;

/**
 * Animates the results of opening sites in a percolation system
 * 
 * From Princeton COS 226, Kevin Wayne 
 * Modified by Owen Astrachan, January 2008
 * Modified by Jeff Forbes, October 2008
 */

public class PercolationVisualizer {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);

	/**
	 * Draws a square of color c at (row,col) on a N*N grid
	 */
	public static void draw(int row, int col, int N, Color c) {
		StdDraw.setPenColor(c);
		StdDraw.filledSquare(col + .5, N - row - .5, .45);
	}

	public static void main(String[] args) {
		// Animate 20 times a second if possible
		final int DEFAULT_DELAY = 1000 / 20; // in milliseconds
		String input = "20"; // default
		if (args.length == 1) // use command-line arguments for testing/grading
			input = args[0];
		else
			input = JOptionPane.showInputDialog("Enter N", "20");
		int N = Integer.parseInt(input); // N-by-N lattice

		// set x- and y-scale
		StdDraw.setXscale(0, N);
		StdDraw.setYscale(0, N);
		// draw a black box
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledSquare(N / 2.0, N / 2.0, N / 2.0);

		//IPercolate perc = new PercolationDFS(N);
		 //IPercolate perc = new PercolationUF(N, new QuickFind());
		IPercolate perc = new PercolationUF(N, new QuickUWPC(N));

		// TODO repeatedly declare sites open, draw, & pause until the system
		// percolates
		// draw percolation system
		
		ArrayList<Point> cellsToOpen = new ArrayList<Point>();
		for(int i = 0; i<N;i++){
			for(int j = 0;j<N;j++){
				cellsToOpen.add(new Point(i,j));
			}
		}
		
		Collections.shuffle(cellsToOpen, ourRandom);
		while(!perc.percolates()){
			Point current = cellsToOpen.remove(cellsToOpen.size()-1);
			perc.open(current.x, current.y);
			for(int i = 0; i<N; i++){
				for(int j = 0; j<N; j++){
					if(perc.isFull(i, j)){
						draw(i,j,N,Color.CYAN);
						continue;
					}
					if(perc.isOpen(i, j)){
						draw(i,j,N,Color.WHITE);
					}
					
				}
			}
		}
		
		
		//shuffle cells
		//for each cell in shuffled random order
			//open that cell
			//draw the grid
		//		StdDraw.show(DEFAULT_DELAY);
//if(perc.percolates()) 
		//break
		//
		
		
		//draw grid being full one last time
		//

		// wait DEFAULT_DELAY milliseconds and then display
		StdDraw.show(DEFAULT_DELAY);

	}
}
