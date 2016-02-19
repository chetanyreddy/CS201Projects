import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import princeton.*;

/**
 * Print statistics on Percolation: prompts the user for N and T, performs T
 * independent experiments on an N-by-N grid, prints out the 95% confidence
 * interval for the percolation threshold, and prints mean and std. deviation
 * of timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 */

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);

	public static void main(String[] args) {
		int N, T;
		if (args.length == 2) { // use command-line arguments for
			// testing/grading
			N = Integer.parseInt(args[0]);
			T = Integer.parseInt(args[1]);
		} else {
			String input = JOptionPane.showInputDialog("Enter N and T", "20 100");
			// TODO: parse N and T from input
			String[] temp = input.split(" ");
			T = Integer.parseInt(temp[1]);
			N = Integer.parseInt(temp[0]);

			//System.out.println(N);
		}


		// TODO: Perform T experiments for N-by-N grid
		long start = System.currentTimeMillis();


		ArrayList<Double> counto = new ArrayList<Double>();
		ArrayList<Double> countTime = new ArrayList<Double>();

		for(int a = 0; a<T; a++ ){
			ArrayList<Point> cellsToOpen = new ArrayList<Point>();
			for(int i = 0; i<N;i++){
				for(int j = 0;j<N;j++){
					cellsToOpen.add(new Point(i,j));
				}
			}
			Collections.shuffle(cellsToOpen, ourRandom);

			//IPercolate perc = new PercolationDFS(N);
			//IPercolate perc = new PercolationUF(N, new QuickFind());
			IPercolate perc = new PercolationUF(N, new QuickUWPC(N));

			while(!perc.percolates()){

				Point current = cellsToOpen.remove(cellsToOpen.size()-1);
				perc.open(current.x, current.y);}
				int countOpen = 0;

			for(int i = 0; i<N; i++){
				for(int j = 0; j<N; j++){
					if(perc.isFull(i, j) || perc.isOpen(i, j)){
						countOpen++;;

					}

				}
			}


			Double count0temp =  (countOpen*1.0) / (N*N);

			counto.add(count0temp);
			//System.out.println(countOpen);

			long end = System.currentTimeMillis();
			long time = end - start;
			double seconds = time/ 1000.00;
			countTime.add(seconds);
		}


		Double mean1 = 0.0;
		for(int k = 0; k<T;k++){
			mean1 += counto.get(k);
		}
		Double meanOpen = mean1 / (T);
		System.out.println("mean percolation threshold = " + meanOpen);

		//STD for Open
		double stdOpen = 0.0;
		for(int k = 0; k<T;k++){
			stdOpen += Math.pow(counto.get(k) - meanOpen, 2);
		}
		stdOpen = stdOpen / (T-1);
		stdOpen = Math.sqrt(stdOpen);


		System.out.println("stddev = " + stdOpen);
		
		//CONFIDENCE INTERVAL
		double lowerBound = meanOpen - ((1.96 * stdOpen)/ Math.sqrt(T));
		double upperBound = meanOpen + ((1.96 * stdOpen) / Math.sqrt(T));
		System.out.println("95% confidence interval = [" + lowerBound + ", "+ upperBound + "]");

		//TOTAL TIME
		double totalTime = 0.0;
		for(int k = 0; k<T;k++){
			totalTime += countTime.get(k);
		}
		double meanTime = totalTime / T;
		
		System.out.println("total time = " + totalTime + "s");
		//MEAN TIME
		System.out.println("mean time per experiment = " + totalTime / T + "s");
		//STD DEV of TIME
		double stdTime = 0.0;
		for(int k = 0; k<T;k++){
			stdTime += Math.pow(countTime.get(k) - meanTime, 2);
		}
		double stdTimeFinal = stdTime / (T-1);
		System.out.println("stddev = " + Math.sqrt(stdTimeFinal));
		




		// TODO: print statistics and confidence interval

	}
}
