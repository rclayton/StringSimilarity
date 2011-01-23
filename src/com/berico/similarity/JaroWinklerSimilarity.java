package com.berico.similarity;

/**
 * Find the similarity of two strings using the Jaro-Winkler
 * distance algorithm.
 * http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance
 * @author Richard C. (Berico Technologies)
 */
public class JaroWinklerSimilarity implements ISimilarityCalculator {

	//Bonus weighting for string starting with the same characters
	//(e.g.: prefix scaling factor)
	public static double PREFIX_SCALING_FACTOR = 0.1;
	
	/**
	 * Instead of making two seperate functions for matching
	 * and transposes (which would be terrible since you
	 * find the transpose while doing matching), I created this
	 * little class to hold the results of both.
	 */
	public static class MatchResults {
		public int numberOfMatches = 0;
		public int numberOfTransposes = 0;
	}
	
	/**
	 * Calculate the Jaro-Winkler Similarity of two strings
	 * @param stringOne First String
	 * @param stringTwo Second String
	 * @return Jaro-Winkler similarity value
	 */
	@Override
	public double calculate(String stringOne, String stringTwo) {
		//Get Matches and Transposes
		MatchResults matchResults = 
			determineMatchesAndTransposes(stringOne, stringTwo);
		//Get the Jaro Distance
		double jaroDistance = 
			jaroDistance(
				matchResults.numberOfMatches, 
				matchResults.numberOfTransposes, 
				stringOne.length(), 
				stringTwo.length());
		//Find the Winkler common prefix length (maxes at 4 characters)
		int winklerCommonPrefix = 
			winklerCommonPrefix(stringOne, stringTwo);
		//Find the Jaro-Winkler Distance
		// = Jd + (l * p * ( 1 - Jd));
		double jaroWinklerDistance = 
			jaroDistance + (winklerCommonPrefix * PREFIX_SCALING_FACTOR)
				* (1 - jaroDistance);
		//Return the distance
		return jaroWinklerDistance;
	}
	
	/**
	 * Find the all of the matching and transposed characters in 
	 * two strings
	 * @param stringOne First String
	 * @param stringTwo Second String
	 * @return A Match Result with both the number of matches and
	 * number of transposed characters
	 */
	public static MatchResults determineMatchesAndTransposes(
						String stringOne, String stringTwo){
		//Create the match result instance
		MatchResults matchResults = new MatchResults();
		//Find the matching window (how far left and right to
		//look for matches)
		int window = matchingWindow(stringOne, stringTwo);
		//We need to find the shortest and longest character string
		//because we iterate over the shortest
		char[] shortest, longest;
		//If string one is less than or equal to string two
		if(stringOne.length() <= stringTwo.length()){
			//use string one as the shortest
			shortest = stringOne.toCharArray();
			longest = stringTwo.toCharArray();
		} else {
			//otherwise use string two as the shortest
			shortest = stringTwo.toCharArray();
			longest = stringOne.toCharArray();
		}
		//we need to find the number of times we find a match
		//out of position (ex: the 4th character of string one
		//matches the 5th character of string two
		int matchedOutOfPosition = 0;
		//Iterate over the shortest string
		for(int i = 0; i < shortest.length; i++){
			//If we have an in-position match
			if(shortest[i] == longest[i]){
				//increment the number of matches
				matchResults.numberOfMatches++;
				//go to the next iteration
				continue;
			}
			//Set the boundary of how far back we
			//can look at the longest string
			//given the string's size and the
			//window size
			int backwardBoundary = 
				((i - window) < 0)? 
					0 : i - window;
			//Set the boundary of how far we
			//can look ahead at the longest string
			//given the string's size and the
			//window size
			int forwardBoundary = 
				((i + window) > (longest.length - 1))? 
					longest.length - 1 : i + window;
			//Starting at the back-most point, and
			//moving to the forward-most point of the
			//longest string, iterate looking for matches
			//of our current character on the shortest 
			//string
			for(int b = backwardBoundary; 
			        b <= forwardBoundary; 
			        b++){
				//If theres a match within our window
				if(longest[b] == shortest[i]){
					//increment the number of matches
					matchResults.numberOfMatches++;
					//but note that this is out of
					//position
					matchedOutOfPosition++;
					//break out of this inner loop if we
					//aren't on the last element
					break;
				}
			}
		}
		//Determine the number of transposes by halving
		//the number of out-of-position matches.
		//This works because if we had two strings:
		//"martha" and "marhta", the 'th' and 'ht' are
		//transposed, but would be counted twice in the
		//process above.
		matchResults.numberOfTransposes = matchedOutOfPosition / 2;
		//return the match result
		return matchResults;
	}
	
	/**
	 * Determine the maximum window size to use when looking for matches.
	 * The window is basically a little less than the half the longest
	 * string's length.
	 * Equation: [ Max(A, B) / 2 ] -1
	 * @param stringOne First String
	 * @param stringTwo Second String
	 * @return Max window size
	 */
	public static int matchingWindow(String stringOne, String stringTwo){
		return 
			(Math.max(stringOne.length(), stringTwo.length()) / 2) - 1;
	}
	
	/**
	 * Determine the Jaro Distance.  Winkler stole some of Jaro's
	 * thunder by adding some more math onto his algorithm and getting
	 * equal parts credit!  However, we still need Jaro's Distance
	 * to get the Jaro-Winkler Distance.
	 * Equation: 1/3 * ((|A| / m) + (|B| / m) + ((m - t) / m))
	 * Where: |A| = length of first string
	 *        |B| = length of second string
	 *         m  = number of matches
	 *         t  = number of transposes
	 * @param numMatches Number of matches
	 * @param numTransposes Number of transposes
	 * @param stringOneLength Length of String one
	 * @param stringTwoLength Length of String two
	 * @return Jaro Distance
	 */
	public static double jaroDistance(
			int numMatches, int numTransposes, int stringOneLength, 
			int stringTwoLength){
		//I hate Java's facility for math.  We have to cast these int's as doubles to
		//be able to properly retrieve the decimal result
		double third = 1d / 3d;
		// (|A| / m)
		double stringOneNorm = 
			(double)numMatches / (double)stringOneLength;
		// (|B| / m)
		double stringTwoNorm = 
			(double)numMatches / (double)stringTwoLength;
		// ((m - t) / m)
		double matchTransNorm = 
			((double)numMatches - (double)numTransposes) / (double)numMatches;
		// 1/3 * ((|A| / m) + (|B| / m) + ((m - t) / m))
		return third * (stringOneNorm + stringTwoNorm + matchTransNorm);
	}
	
	/**
	 * Find the Winkler Common Prefix of two strings.  In Layman's terms,
	 * find the number of character that match at the beginning of the
	 * two strings, with a maximum of 4.
	 * @param stringOne First string
	 * @param stringTwo Second string
	 * @return Integer between 0 and 4 representing the number of
	 * matching characters at the beginning of both strings.
	 */
	public static int winklerCommonPrefix(
			String stringOne, String stringTwo){
		
		int commonPrefix = 0;
		//Find the shortest string (we don't want an index out of bounds
		//exception).
		int boundary = (stringOne.length() <= stringTwo.length())? 
						stringOne.length() : stringTwo.length();
		//iterate until the boundary is hit (shortest string length)
		for(int i = 0; 
		        i < boundary;
		        i++){
			//If the character at the current position matches
			if(stringOne.charAt(i) == stringTwo.charAt(i)){
				//increment the common prefix
				commonPrefix++;
			} else {
				//otherwise, continue no further, we are done.
				break;
			}
			//If we hit our max number of matches, bust out
			//of the loop.
			if(commonPrefix == 4){ break; }
		}
		//return the number of matches at the beginning of 
		//both strings
		return commonPrefix;
	}

}
