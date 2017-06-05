package main;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import functions.*;
import variable.Species;

//import species;

public class SimulationEngine {
	
	/**
	 * This function is used for the initializing the system at first.
	 * Input: K(total features),		L(chosen features),		n(# of species),		c(parameter)
	 * Output: S(nxn Matrix)
	 * Output: alpha(nxn Matrix)
	 * Output: f(nxn Matrix)
	 * Output: g(nxn Matrix)
	 * Output: N(n length list - each item is a list of used features' indexes)
	 * Output: attr(K length list - whether used) ???
	 */
	//Yuzhi
	public static void initialize(List<List<Double>> m, int K, int L, int n, double c,
			List<List<Double>> S, List<List<Double>> alpha, List<List<Double>> f, List<List<Double>> g, List<Integer> N, List<List<Integer>> nF){
		
		//create ana initialize KxK matrix m (Gaussian Dist.)
		//List<List<Double>> m = new ArrayList<>();
		for(int i=0; i<K; i++) {
			List<Double> lIns = new ArrayList<>();
			for(int j=0; j<K; j++) {
				if(j == i)
					lIns.add(0.0);
				else if(j < i)
					lIns.add(-m.get(j).get(i));
				else
					lIns.add(Function.GaussianRNG(0, 1));
			}
			m.add(lIns);
		}
		/*
		//test m
		for(int i=0; i<K; i++) {
			for(int j=0; j<K; j++) {
				if(m.get(i).get(j) >= 0)
					System.out.print(" " + Function.printFormat2(m.get(i).get(j)) + " ");
				else
					System.out.print(Function.printFormat2(m.get(i).get(j)) + " ");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		*/
		
		//create and initialize n length list nF - each item in N is a list of its used features' indexes.
		//List<List<Integer>> nF = new ArrayList<>();
		for(int i=0; i<n; i++) {
			List<Integer> lIns = Function.uniqueRandom(L, K);
			nF.add(lIns);
		}
		/*
		//test nF
		for(int i=0; i<n; i++) {
			System.out.print("Species " + i + " has the following features: ");
			for(int j=0; j<L; j++) {
				if(nF.get(i).get(j) <10)
					System.out.print(" " + nF.get(i).get(j) + " ");
				else
					System.out.print(nF.get(i).get(j) + " ");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		*/
		
		//initialize nxn matrix S - Score Matrix
		for(int i=0; i<n; i++) {
			List<Double> lIns = new ArrayList<>();
			for(int j=0; j<n; j++) {
				double sij = 0;
				for(int ni=0; ni<L; ni++) {
					for (int nj=0; nj<L; nj++) {
						sij += (m.get(nF.get(i).get(ni)).get(nF.get(j).get(nj)))/L;
//						System.out.print(N.get(i).get(ni) + " " + N.get(j).get(nj) + " " +  
//						Function.printFormat2(m.get(N.get(i).get(ni)).get(N.get(j).get(nj))) + " ");
					}
//					System.out.println(" ");
				}
				
				if(i==0)
					lIns.add(0.0);
				else
					lIns.add(Math.max(0, sij));
			}
			S.add(lIns);
		}
		
		//test S
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				System.out.print(Function.printFormat2(S.get(i).get(j)) + " ");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		
		
		//calculate nxn matrix q
		List<List<Double>> q = new ArrayList<>();
		for(int i=0; i<n; i++) {
			List<Double> lIns = new ArrayList<>();
			for(int j=0; j<n; j++) {
				double count = 0;
				for(int fi=0; fi<L; fi++) {
					for(int fj=0; fj<L; fj++) {
						if(nF.get(i).get(fi) == nF.get(j).get(fj))
							count++;
					}
				}
				lIns.add(count/L);
			}
			q.add(lIns);
		}
		/*
		//test q
		for(int i=0; i<n; i++) {
			for(int j=0;  j<n; j++) {
				System.out.print(Function.printFormat2(q.get(i).get(j)) + " ");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		*/
		
		//initialize nxn matrix alpha - Similarity Matrix
		for(int i=0; i<n; i++) {
			List<Double> lIns = new ArrayList<>();
			for(int j=0; j<n; j++) {
				lIns.add(c+(1-c)*(q.get(i).get(j)));
			}
			alpha.add(lIns);
		}
		/*
		//test alpha
		for(int i=0; i<n; i++) {
			for(int j=0;  j<n; j++) {
				System.out.print(Function.printFormat2(alpha.get(i).get(j)) + " ");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		*/
		
		//initialize nxn matrix f - effort Matrix
		for(int i=0; i<n; i++) {
			List<Double> lIns = new ArrayList<>();
			for(int j=0; j<n; j++) {
				if (S.get(i).get(j) == 0)
				{
					lIns.add(0.0);
				} else {
					lIns.add(1.0/(n-1));
				}
			}
			f.add(lIns);
		}
		/*
		//test f
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				System.out.print(Function.printFormat2(f.get(i).get(j)) + " ");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		
		*/
		//initialize nxn matrix g - simply all 0
		for(int i=0; i<n; i++) {
			List<Double> lIns = new ArrayList<>();
			for(int j=0; j<n; j++) {
				lIns.add(1.0/n);
			}
			g.add(lIns);
		}
		/*
		//test g
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				System.out.print(Function.printFormat2(g.get(i).get(j)) + " ");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		*/
		
		//initialize n length list N - population
		int ratio=1;
		int meanPopulation=5000;
		int varPopulation=5;
		N.add(meanPopulation*ratio);
		//System.out.println(N);
		for(int i=1; i<n; i++) {
			N.add( (int) Function.GaussianRNG(meanPopulation, varPopulation) );
			//System.out.println(N.get(i));
		}
		//System.out.print(N);
		/*
		//test N
		for(int i=0; i<n; i++) {
			System.out.println("Species " + i + "'s population is: " + N.get(i));
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		*/
		
	}
	/*
	 * This function is used for the initializing the system at first.
	 * Input:K,n,L,c
	 * Output:alpha,S,f
	 */

	
	/**
	 * This function is used for updating the population of species for each time step.
	 */
	//Qiang
	public static boolean update(double deltaT,double b,List<List<Double>> f, List<List<Double>> g, List<List<Double>> alpha,List<Integer> N, List<List<Double>> S){
		
		double error=updateParameters(f,b,g,alpha,N,S);
		//error=updateParameters(f,b,g,alpha,N,S);		/**
		while(error>0.1){
			error=updateParameters(f,b,g,alpha,N,S);
		}
		//System.out.format("%f%n",error);
		
		/*
		for(int i =0; i<N.size();i++){
			System.out.print(N.get(i)+" ");
		}
		System.out.println();
		*/
		return updatePopulation(N,deltaT,g);
	}
	/**
	 * Input:f,b,alpha,N,S
	 * Output:f,g
	 * @param f
	 * @param b
	 * @param g
	 * @param alpha
	 * @param N
	 * @param S
	 * @return
	 */
	//Kai
	private static double updateParameters( List<List<Double>> f,double b, List<List<Double>> g, List<List<Double>> alpha,List<Integer> N, List<List<Double>> S){
		double error=0;
		double fmin=0.000001;
		//calculate g
		for(int i=0;i<g.size();i++){
			for(int j=0;j<g.get(i).size();j++){
				double sum=0;
				for(int k=0;k<g.size();k++){
					sum = sum + alpha.get(k).get(i)*S.get(k).get(j)*f.get(k).get(j)*N.get(k);		
				
				//System.out.println("f: "+f.get(k).get(j));
				}
				//System.out.println("Sum: "+sum);
				if(N.get(j)==0){
					g.get(i).set(j, 0.0);
				}
				else{
					g.get(i).set(j,S.get(i).get(j)*f.get(i).get(j)*N.get(j)/(b*N.get(j)+sum));
				}
			}
		}
		/*
		//test g
		for(int i=0; i<g.size(); i++) {
			for(int j=0; j<g.get(i).size(); j++) {
				System.out.print(Function.printFormat2(g.get(i).get(j)) + " ");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		*/
		//calculate f
		for(int i=0;i<g.size();i++){
			for(int j=0;j<g.get(i).size();j++){				
				if (S.get(i).get(j)==0){
					f.get(i).set(j, 0.0);
				}
				else{
					double sum=0;
					double oldValue=f.get(i).get(j);
					for(int k=0;k<g.size();k++){
						sum = sum + g.get(i).get(k);		
					}
					double newValue=g.get(i).get(j)/sum;
					if (newValue<fmin && S.get(i).get(j)>0){
						newValue=fmin;
					}
					f.get(i).set(j,newValue);
					error=Math.max(error,Math.abs((newValue-oldValue)/oldValue));
				}			//System.out.println(error);
			}
		}

		//test f
		/*
		for(int i=0; i<f.size(); i++) {
			for(int j=0; j<f.get(i).size(); j++) {
				System.out.print(Function.printFormat2(f.get(i).get(j)) + " ");
			}
			System.out.println(" ");
		}
		System.out.println("------------------------------------------------------------"
				 + "------------------------------------------------------------");
		//System.out.format("%f%n",error);
		 */
		return error;
		
	}
	/**
	 * Input:N,deltaT,g
	 * Output:N
	 * @param N
	 * @param deltaT
	 * @param g
	 */
	private static boolean updatePopulation(List<Integer> N, double deltaT, List<List<Double>> g){
		double lambda = 0.2;
		List<Integer> tempN =new ArrayList<>();
		for(int i=0;i<N.size();i++){
			tempN.add(N.get(i));
		}
		for(int i=0;i<N.size();i++){
			double sum = 0;
			for(int j=0; j<N.size();j++){
				sum = sum + lambda*tempN.get(i)*g.get(i).get(j)-tempN.get(j)*g.get(j).get(i);
				//System.out.println(g.get(i).get(j));
			}
			N.set(i,Math.max(0,(int) (tempN.get(i)*(1-deltaT)+deltaT*sum)));
		}
		N.set(0, (int)(N.get(0)*1.01457));
		double change=0;
		for(int i=0;i<N.size();i++){
			change += Math.abs(tempN.get(i)-N.get(i));
		}
		return change/N.size()>0.0;
		
	}
	/**
	 * Input: N
	 * Output: S, alpha, f, attr
	 * @param S
	 * @param alpha
	 * @param f
	 * @param N
	 * @param attr
	 */
	//Tong
	//add m to args
	//add L to args
	//add c to args
	public static void generateNewSpecies( List<List<Double>> m, int L, double c, List<List<Double>> S, 
			List<List<Double>> alpha, List<List<Double>> f, List<Integer> N,List<List<Integer>> attr, 
			List<List<Double>> g){
		
		int K = m.size();
		int n = N.size();
		List<Integer> newSpeciesAttr = Function.uniqueRandom(L, K);
		attr.add(newSpeciesAttr);
		
		List<Double> newS = new ArrayList<Double>();
		
		for(int i = 0; i < n; i++){
			List<Double> tempS = S.get(i);
			//System.out.println(i);
			List<Integer> tempAttr = attr.get(i);
			double sum = 0;
			for(int a = 0; a < L; a++){
				for(int b = 0; b < L; b++){
					sum += m.get(tempAttr.get(a)).get(newSpeciesAttr.get(b));
				}
			}
			if(sum > 0) sum = 0;
			else sum /= L;
			tempS.add(-sum);
			newS.add(sum);
		}
		newS.add(0.0);
		S.add(newS);
		
		List<Double> newQ = new ArrayList<Double>();
		for(int i = 0; i < n; i++){
			double tempQ = 0;
			for(int j = 0; j < L; j++){
				int temp = newSpeciesAttr.get(j);
				if(attr.get(i).contains(temp)){
					tempQ++;
				}
			}
			newQ.add(tempQ/L);
		}
		//Update alpha
		List<Double> newAlpha = new ArrayList<Double>();
		for(int i = 0; i <alpha.size(); i++){
			double temp= c+(1-c)*newQ.get(i); 
			alpha.get(i).add(temp);
			newAlpha.add(temp);
		}
		newAlpha.add(1.0);
		alpha.add(newAlpha);
		
		//update f
		List<Double> newF = new ArrayList<Double>();
		for(int i = 0; i < f.size(); i++){
			double temp = Function.GaussianRNG(0, 1);
			f.get(i).add(temp);
			newF.add(1.0/n+1);
		}
		newF.add(1.0/n+1);
		f.add(newF);
		
		int ave = 0;
		int count = 0;
		for(int i = 1; i < n; i++){
			ave += N.get(i);
			if(N.get(i) != 0)count++;
		}
		if(count == 0) N.add(1);
		else N.add(ave/count);
		
		List<Double> newG = new ArrayList<Double>();
		for(int i = 0; i < g.size(); i++){
			g.get(i).add(0.0);
			newG.add(0.0);
		}
		newG.add(0.0);
		g.add(newG);
		
	}
	


	
}
