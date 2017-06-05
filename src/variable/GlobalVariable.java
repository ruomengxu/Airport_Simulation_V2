package variable;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariable {
	private List<List<Integer>> speciesRelation;
	final private int numFeature = 500;
	
	public List<List<Integer>> initializeSpeciesRelation(){
		List<List<Integer>> speciesRelation = new ArrayList<List<Integer>>();
		
		return speciesRelation;
	}
	
	public List<List<Integer>> getSpeciesRelation(){
		return speciesRelation;
	}
}
