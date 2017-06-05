package functions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Function {
	
	/**
	 * This funciton is used to generate a random number following the Gaussian distribution
	 * @return Random Number
	 */
	
	//Kai
	public static double GaussianRNG(double avg,double variance){
		double number = 0;
		Random ran = new Random();
		number = Math.sqrt(variance)*ran.nextGaussian()+avg;
		/*
		 *  Implements the content of the Random Number Generator function here 
		 */
		return number;
	}
	
	
	/**
	 * This function is used to generate m unique random integer among 0 ~ n-1
	 * @return unique random number list
	 */
	
	//Yuzhi
	public static List<Integer> uniqueRandom(int m, int n) {
		HashSet<Integer> set = new HashSet<>();
		int[] uniqueList = new int[m];
		int ulIndex = 0;
		int ulNum = 0;
		while(ulIndex < m) {
			ulNum = (int) (Math.random()*n);
			if(!set.contains(ulNum)){
				set.add(ulNum);
				uniqueList[ulIndex++] = ulNum;
			}
		}
		List<Integer> res = new ArrayList<Integer>();
		for(int i = 0; i < uniqueList.length; i++){
			res.add(uniqueList[i]);
		}
		return res;
	}
	
	/**
	 * This function is used format a double number to 2-digit, i.e. "0.00"
	 * @return String of the input, with the format of 0.00
	 */
	//Yuzhi
	public static String printFormat2(double n) {
		DecimalFormat pf2 = new DecimalFormat("0.00");
		return pf2.format(n);
	}
	
}
