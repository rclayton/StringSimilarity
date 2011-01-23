package com.berico.similarity;

import static com.berico.similarity.CharacterVectorUtils.*;

/**
 * Calculate the Jaccard Similarity of two strings.
 * @author Richard C (Berico Technologies)
 */
public class JaccardSimilarity implements ISimilarityCalculator {

	/**
	 * Find the Jaccard Similarity of two strings
	 * @param stringOne the first string to compare
	 * @param stringTwo the second string to compare
	 * @return the Jaccard Similarity (intersect(A,B) / union(A, B))
	 */
	@Override
	public double calculate(String stringOne, String stringTwo) {
		return (double) intersect(stringOne, stringTwo).size() /
			   (double) union(stringOne, stringTwo).size();
	}

}
