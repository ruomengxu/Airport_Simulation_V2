package variable;

import java.util.List;
import functions.*;

public class Species {
	private int population;
	private int speciesNo;
	//private List<Integer> predator;
	//private List<Integer> prey;
	
	public void setPopulation(int population){
		this.population = population;
	}
	
	public int getPopulation(){
		return population;
	}
	
	public Species initializeSpecies(){
		Species species = new Species();
		return species;
	}
	
	/*
	public void setPredator(List<Integer> predator){
		this.predator = predator;
	}
	
	public List<Integer> getPredator(){
		return predator;
	}
	
	public void setPrey(List<Integer> prey){
		this.prey = prey;
	}
	
	*/
}
