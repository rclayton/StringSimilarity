package com.berico.similarity;

import static com.berico.similarity.CharacterVectorUtils.*;
import static com.berico.similarity.VectorMath.*;

import java.util.Collection;
import java.util.Vector;

public class CosineSimilarity implements ISimilarityCalculator {

	
	/**
	 * Calculate the similarity of two strings using Cosine Similarity
	 * @param stringOne First input string
	 * @param stringTwo Second input string
	 * @return cosine of the two angles (percentage of similarity)
	 */
	@Override
	public double calculate(String stringOne, String stringTwo) {
		Collection<Character> unionOfStringsOneAndTwo = union(stringOne, stringTwo);
		Collection<Integer> stringOneOccurrenceVector = createFrequencyOfOccurrenceVector(stringOne, unionOfStringsOneAndTwo);
		Collection<Integer> stringTwoOccurrenceVector = createFrequencyOfOccurrenceVector(stringTwo, unionOfStringsOneAndTwo);

		int dotProduct = 0;
		//This should be an unnecessary exception since we're submitting the union
		//of both strings
		try {
			dotProduct = dotp(stringOneOccurrenceVector, stringTwoOccurrenceVector);
		} catch (VectorMathException e){
			e.printStackTrace();
			System.out.println(stringOneOccurrenceVector);
			System.out.println(stringTwoOccurrenceVector);
			return -2;
		}
		
		double vectorOneMagnitude = magnitude(stringOneOccurrenceVector);
		double vectorTwoMagnitude = magnitude(stringTwoOccurrenceVector);
			
		return dotProduct / (vectorOneMagnitude * vectorTwoMagnitude);
	}
	
	/**
	 * Get the Frequency of Occurrence Vector from the Union Set and
	 * target string
	 * @param string Input String
	 * @param unionSet Set of all Character-dimensions
	 * @return Frequency of Occurrence Vector
	 */
	private static Collection<Integer> createFrequencyOfOccurrenceVector(String string, Collection<Character> unionSet){
		Collection<Integer> occurrenceVector = new Vector<Integer>();
		for(Character c : unionSet){
			occurrenceVector.add((Integer)countCharacter(string, c));
		}
		return occurrenceVector;
	}

	/**
	 * Count the number of times a character occurs in a string
	 * @param string Input String
	 * @param character Character to be counted
	 * @return Frequency of Occurrence
	 */
	private static int countCharacter(String string, Character character){
		int count = 0;
		for(Character c : string.toCharArray()){
			if(c.equals(character)){
				count++;
			}
		}
		return count;
	}
}
