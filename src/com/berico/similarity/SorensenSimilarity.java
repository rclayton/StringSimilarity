package com.berico.similarity;

import static com.berico.similarity.CharacterVectorUtils.*;

/**
 * From Wikipedia, Sorensen Similarity calculates the similarity of two samples
 * by taking twice the size of the shared population of two species divided by
 * the sum of both populations.
 * @author Richard C (Berico Technologies)
 */
public class SorensenSimilarity implements ISimilarityCalculator {

	/**
	 * Calculate the Sorensen Similarity of two strings.
	 * Equation: (2 * intersect(A, B)) / (|A| + |B|)
	 * @param stringOne First String
	 * @param stringTwo Second String
	 * @return The Sorensen similarity of two strings.
	 */
	@Override
	public double calculate(String stringOne, String stringTwo) {
		return  (double) (2 * intersect(stringOne, stringTwo).size()) /
		        (double) (stringOne.length() + stringTwo.length());
	}

}
