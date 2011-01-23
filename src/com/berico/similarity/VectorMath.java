package com.berico.similarity;

import java.util.Collection;

public class VectorMath {

	/**
	 * Calculate the Dot Product (inner product) of two vectors
	 * @param vectorOne Vector
	 * @param vectorTwo Vector
	 * @return Dot Product
	 * @throws VectorMathException Thrown if vectors are not of equal length
	 */
	public static int dotp(Collection<Integer> vectorOne, Collection<Integer> vectorTwo) throws VectorMathException {
		return dotp(vectorOne.toArray(new Integer[0]), vectorTwo.toArray(new Integer[0]));
	}
	
	/**
	 * Calculate the Dot Produce (inner produce) of two vectors using doubles.
	 * This is a gross implementation, in part, because Java Generics does
	 * not allow mathematical operations on generics, even if the generic
	 * type extends number (e.g.: <N extends Number>) [since they are object types
	 * and not primitives].
	 * @param vectorOne Vector
	 * @param vectorTwo Vector
	 * @return Dot Produce
	 * @throws VectorMathException Thrown if vectors are not of equal length
	 */
	public static double dotpd(Collection<Double> vectorOne, Collection<Double> vectorTwo) throws VectorMathException {
		return dotpd(vectorOne.toArray(new Double[0]), vectorTwo.toArray(new Double[0]));
	}
	
	/**
	 * Calculate the Dot Product (inner product) of two vectors
	 * @param vectorOne Vector
	 * @param vectorTwo Vector
	 * @return Dot Product
	 * @throws VectorMathException Thrown if vectors are not of equal length
	 */
	public static int dotp(Integer[] vectorOne, Integer[] vectorTwo) throws VectorMathException {
		if(vectorOne.length != vectorTwo.length){
			throw new VectorMathException(
					"Input Vectors do not have the same number of dimensions.");
		}
		int dotProduct = 0;
		for(int i = 0; i < vectorOne.length; i++){
			dotProduct += (vectorOne[i] * vectorTwo[i]);
		}
		return dotProduct;	
	}
	
	/**
	 * Calculate the Dot Produce (inner produce) of two vectors using doubles.
	 * This is a gross implementation, in part, because Java Generics does
	 * not allow mathematical operations on generics, even if the generic
	 * type extends number (e.g.: <N extends Number>) [since they are object types
	 * and not primitives].
	 * @param vectorOne Vector
	 * @param vectorTwo Vector
	 * @return Dot Produce
	 * @throws VectorMathException Thrown if vectors are not of equal length
	 */
	public static double dotpd(Double[] vectorOne, Double[] vectorTwo) throws VectorMathException {
		if(vectorOne.length != vectorTwo.length){
			throw new VectorMathException(
					"Input Vectors do not have the same number of dimensions.");
		}
		double dotProduct = 0;
		for(int i = 0; i < vectorOne.length; i++){
			dotProduct += (vectorOne[i] * vectorTwo[i]);
		}
		return dotProduct;	
	}
	
	/**
	 * Calculate the Magnitude of a vector
	 * @param vector Vector
	 * @return Magnitude of the Vector
	 */
	public static double magnitude(Collection<Integer> vector){
		return magnitude(vector.toArray(new Integer[0]));
	}
	
	/**
	 * Calculate the Magnitude of a vector
	 * @param vector Vector
	 * @return Magnitude of the Vector
	 */
	public static double magnitude(Integer[] vector){
		double magnitude = 0;
		for(int i = 0; i < vector.length; i++){
			magnitude += Math.pow(vector[i], 2);
		}
		return Math.sqrt(magnitude);
	}
	
}
