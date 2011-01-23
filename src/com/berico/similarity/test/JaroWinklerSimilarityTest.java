package com.berico.similarity.test;

import static org.junit.Assert.*;

import com.berico.similarity.JaroWinklerSimilarity;
import com.berico.similarity.JaroWinklerSimilarity.MatchResults;

import org.junit.Test;

/**
 * There's a lot of math involved in the Jaro-Winkler
 * Distance calculator.  My implementation came straight
 * from the formulas on the Wikipedia article for the
 * calculation (http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance).
 * Many of the test samples (martha, marhta) are from
 * the website, including the values of each step in 
 * the calculation (matching window, jaro distance, matches, transposes, etc).
 * If these values are wrong, someone needs to update Wikipedia,
 * because I'm use their results to validate my implementation!
 * @author Richard C. (Berico Technologies)
 */
public class JaroWinklerSimilarityTest {

	@Test
	public void Matching_window_returns_valid_distance(){
		
		String one = "martha";
		String two = "marhta";
		
		assertEquals(2, JaroWinklerSimilarity.matchingWindow(one, two));
		//Reverse the strings as parameters
		assertEquals(2, JaroWinklerSimilarity.matchingWindow(two, one));
		
		String three = "mart";
		String four = "marha";
		
		assertEquals(1, JaroWinklerSimilarity.matchingWindow(three, four));
		//Reverse the strings as parameters
		assertEquals(1, JaroWinklerSimilarity.matchingWindow(four, three));
	}
	
	@Test
	public void Winkler_common_prefix_returns_the_correct_prefix_size(){
	
		String str1OfShouldReturn3 = "martha";
		String str2OfShouldReturn3 = "marhta";
		
		assertEquals(3, JaroWinklerSimilarity.winklerCommonPrefix(str1OfShouldReturn3, str2OfShouldReturn3));
		//Reverse the strings as parameters
		assertEquals(3, JaroWinklerSimilarity.winklerCommonPrefix(str2OfShouldReturn3, str1OfShouldReturn3));
		
		String str1OfShouldReturn2 = "joe";
		String str2OfShouldReturn2 = "joseph";
		
		assertEquals(2, JaroWinklerSimilarity.winklerCommonPrefix(str1OfShouldReturn2, str2OfShouldReturn2));
		//Reverse the strings as parameters
		assertEquals(2, JaroWinklerSimilarity.winklerCommonPrefix(str2OfShouldReturn2, str1OfShouldReturn2));
		
		String str1OfShouldReturn4 = "abcdfg";
		String str2OfShouldReturn4 = "abcdef";
		
		assertEquals(4, JaroWinklerSimilarity.winklerCommonPrefix(str1OfShouldReturn4, str2OfShouldReturn4));
		//Reverse the strings as parameters
		assertEquals(4, JaroWinklerSimilarity.winklerCommonPrefix(str2OfShouldReturn4, str1OfShouldReturn4));
		
		String str1OfShouldReturn4ButCouldMore = "abcdef";
		String str2OfShouldReturn4ButCouldMore = "abcdef";
		
		assertEquals(4, JaroWinklerSimilarity.winklerCommonPrefix(str1OfShouldReturn4ButCouldMore, str2OfShouldReturn4ButCouldMore));
		//Reverse the strings as parameters
		assertEquals(4, JaroWinklerSimilarity.winklerCommonPrefix(str2OfShouldReturn4ButCouldMore, str1OfShouldReturn4ButCouldMore));
	}
	
	@Test
	public void Determine_matches_and_transposes_returns_the_correct_values(){
		String one = "martha";
		String two = "marhta";
		
		MatchResults mr = JaroWinklerSimilarity.determineMatchesAndTransposes(one, two);
		
		assertEquals(6, mr.numberOfMatches);
		assertEquals(1, mr.numberOfTransposes);
		
	}
	
	@Test
	public void Jaro_distance_returns_the_correct_value(){
		String one = "martha";
		String two = "marhta";
		
		MatchResults matchResults = 
			JaroWinklerSimilarity
				.determineMatchesAndTransposes(one, two);
		
		double jaroDistance = 
			JaroWinklerSimilarity
				.jaroDistance(
					matchResults.numberOfMatches, 
					matchResults.numberOfTransposes, 
					one.length(), 
					two.length());
		
		assertEquals(.944d, jaroDistance, 0.05);
	}
	
	@Test
	public void Jaro_winkler_similarity_returns_the_correct_value(){
		String one = "martha";
		String two = "marhta";
		
		double jaroWinklerSimilarity = (new JaroWinklerSimilarity()).calculate(one, two);
		
		assertEquals(.961d, jaroWinklerSimilarity, 0.05);
		
	}
	
}
