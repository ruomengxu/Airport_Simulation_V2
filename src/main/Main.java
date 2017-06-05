package main;

import functions.*;
import variable.GlobalVariable;
import variable.Species;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args){
		/* 
		old
		GlobalVariable globalVariable = new GlobalVariable();
		List<Species> speciesList = new ArrayList<Species>();
		int numSpecies = 10;
		SimulationEngine.initialize(numSpecies, speciesList);
		SimulationEngine.update(speciesList, globalVariable.getSpeciesRelation());
		*/
		
		int attributeNumTotal = 200;
		int attributeNumSelect = 20;
		int speciesNum = 50;
		double c = 0.2;
		double b = 0.005;
		double deltaT=0.005;
		List<List<Double>> m = new ArrayList<List<Double>>();
		List<List<Double>> S = new ArrayList<>();
		List<List<Double>> alpha = new ArrayList<>();
		List<List<Double>> f = new ArrayList<>();
		List<List<Double>> g = new ArrayList<>();
		List<Integer> N = new ArrayList<>();
		List<List<Integer>> attr = new ArrayList<List<Integer>>();
		
		SimulationEngine.initialize(m, attributeNumTotal, attributeNumSelect, speciesNum, c, S, alpha, f, g, N, attr);
		//SimulationEngine.generateNewSpecies(m, attributeNumSelect, c, S, alpha, f, N, attr, g);
		
		boolean unstable = true;
		int index=0;
		for (int step=0;step<10;step++){
			while(unstable){
				unstable = SimulationEngine.update(deltaT,b,f,g,alpha,N,S);
				if(index % 3 ==0){
					for(int i =0; i<N.size();i++){
						System.out.print(N.get(i)+" ");
					}
					System.out.println();
				}
				index++;
				//unstable = true;
			}
			System.out.println("New generated!");
			SimulationEngine.generateNewSpecies(m, attributeNumSelect, c, S, alpha, f, N, attr, g);
			unstable=true;
		}
		/*
		for(int i = 0; i < 1000; i++){
			//System.out.println(i);
			
			//test g
			
			for(int j=0; j<g.size(); j++) {
				for(int k=0; k<g.get(j).size(); k++) {
					System.out.print(Function.printFormat2(g.get(j).get(k)) + " ");
				}
				System.out.println(" ");
			}
			System.out.println("------------------------------------------------------------"
					 + "------------------------------------------------------------");
			
		}
		
		*/
	}
}
