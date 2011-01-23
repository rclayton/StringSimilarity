package com.berico.similarity.test;

import static org.junit.Assert.*;
import static com.berico.similarity.CharacterVectorUtils.*;

import java.util.Collection;
import org.junit.Test;

import com.berico.similarity.CharacterVectorUtils;

public class CharacterVectorUtilsTest {

	String testString1 = "unicorn giggles";
	String testString2 = "clown shoe";
	
	
	Character[] unionExpected = new Character[]{ 
			'g',' ', 'e', 'c', 'n', 'o', 'l', 'h', 'i', 'w', 'u', 's', 'r' };
	Character[] intersectExpected = new Character[]{
			' ', 'c', 'e', 'l', 'n', 'o', 's'};
	Character[] characterSetExpected = new Character[]{
			'u', 'n', 'i', 'c', 'o', 'r', 'n',
			' ', 'g', 'i', 'g', 'g', 'l', 'e', 's'
	};
	Character[] uniqueExpected = new Character[] {
			'u','n','i','c','o','r',' ','g','l','e','s'
	};
	
	
	
	@Test
	public void testUnion() {
		//Perform the Union
		Collection<Character> testUnionSet = 
			CharacterVectorUtils.union(testString1, testString2);
		//Get the Union Expected Set
		Collection<Character> unionExpectedSet = characterArrayToSet(unionExpected);
		//Test the contents of the Arrays.
		assertArrayEquals(
				sortAlphabetically(testUnionSet).toArray(), 
				sortAlphabetically(unionExpectedSet).toArray());
	}

	@Test
	public void testIntersect() {
		//Perform the intersection
		Collection<Character> testIntersectSet = 
			CharacterVectorUtils.intersect(testString1, testString2);
		//Get the Intersection Expected Set
		Collection<Character> intersectExpectedSet = characterArrayToSet(intersectExpected);
		//Test the contents of the Arrays.
		assertArrayEquals(
				sortAlphabetically(testIntersectSet).toArray(), 
				sortAlphabetically(intersectExpectedSet).toArray());
	}

	
	@Test
	public void testUniqueCharacters(){
		//Convert the first test string to a character vector
		Collection<Character> testCharacterVector = stringToCharacterSet(testString1);
		//Get the Unique Characters from our test Vector
		Collection<Character> testUniqueCharacters = 
			CharacterVectorUtils.uniqueCharacters(testCharacterVector);
		//Test the Contents of the Arrays.
		assertArrayEquals(
				sortAlphabetically(testUniqueCharacters).toArray(),
				sortAlphabetically(uniqueExpected).toArray()
		);
	}
	
	@Test
	public void testStringToCharacterSet(){
		//Convert the String to a Character Set
		Collection<Character> testStringToCharacterSet = 
			CharacterVectorUtils.stringToCharacterSet(testString1);
		//Convert our Expected Character Array to a Set
		Collection<Character> stringToCharacterSetExpected = 
			characterArrayToSet(characterSetExpected);
		//Compare the Contents of the Arrays.
		assertArrayEquals(
				sortAlphabetically(testStringToCharacterSet).toArray(), 
				sortAlphabetically(stringToCharacterSetExpected).toArray());
	}
	
}
