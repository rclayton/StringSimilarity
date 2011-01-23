package com.berico.similarity;

/**
 * @author Richard C (Berico Technologies)
 */
public class SimilarityRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
		String one = "string";
		String two = "gnirts";
		
		printSimilarity(new CosineSimilarity(), one, two);
		printSimilarity(new JaccardSimilarity(), one, two);
		printSimilarity(new SorensenSimilarity(), one, two);
		printSimilarity(new JaroWinklerSimilarity(), one, two);
	}

	private static void printSimilarity(
			ISimilarityCalculator simCalculator, String one, String two){
		
		double percentSimilar = simCalculator.calculate(one, two) * 100;
	    System.out.println(
	    		String.format("[%s] %s and %s are %s%% similar", 
	    		simCalculator.getClass().getSimpleName(),
	    		one, two, percentSimilar));
	}
	
}
