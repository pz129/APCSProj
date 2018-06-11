/**
 * A library of matrix and mathematical operations.
 * 
 * @author Evan Weissburg
 */

package apcspro.Project;

import java.util.ArrayList;

public class MatrixOps {
	
	/**
	 * Add two vectors of equal length.
	 * 
	 * @param a
	 * @param b
	 * @return the resulting vector
	 */
	public static ArrayList<Double> add(ArrayList<Double> a, ArrayList<Double> b) {
		assert a.size() == b.size() : "Arrays must be equal length for element-wise addition.";
		ArrayList<Double> ret = new ArrayList<Double>(a.size());
		for(int i = 0; i<a.size(); i++) {
			ret.add(i, a.get(i) - b.get(i));
		}
		return ret;
	}
	
	/**
	 * Subtract two vectors of equal length.
	 * 
	 * @param a
	 * @param b
	 * @return the resulting vector
	 */
	public static ArrayList<Double> subtract(ArrayList<Double> a, ArrayList<Double> b) {
		assert a.size() == b.size() : "Arrays must be equal length for element-wise subtraction.";
		ArrayList<Double> ret = new ArrayList<Double>(a.size());
		for(int i = 0; i<a.size(); i++) {
			ret.add(i, a.get(i) - b.get(i));
		}
		return ret;
	}
	
	/**
	 * Perform element-wise multiplication (inner-product) on two vectors of equal length.
	 * 
	 * @param a
	 * @param b
	 * @return the resulting vector
	 */
	public static ArrayList<Double> multiply(ArrayList<Double> a, ArrayList<Double> b) {
		assert a.size() == b.size() : "Arrays must be equal length for element-wise multiplication.";
		ArrayList<Double> ret = new ArrayList<Double>(a.size());
		for(int i = 0; i<a.size(); i++) {
			ret.add(i, a.get(i) * b.get(i));
		}
		return ret;
	}
	
	/**
	 * Left-multiply a row input vector by the affine transformation matrix.
	 * 
	 * @param inputs the input vector from the last layer (or the original input vector x)
	 * @param weights the 2D transformation matrix of weights (with biases appended)
	 * @return
	 */
	public static ArrayList<Double> layerMult(ArrayList<Double> inputs, ArrayList<ArrayList<Double> > weights) {
		ArrayList<Double> ret = new ArrayList<Double>();
		for(int i = 0; i<weights.size(); i++) {
			assert inputs.size() == weights.get(i).size() : "Dimensions (1, b) x (b, c) must match for layer multiplication.";
			double total = 0;
			for(int j = 0; j<inputs.size(); j++) {
				total += inputs.get(j) * weights.get(i).get(j);
			}
			ret.add(total);
		}
		return ret;
	}
	
	/**
	 * Apply tanh normalization element-wise to the output vector.
	 * 
	 * @param a the result of a matrix multiplication (not normalized)
	 * @return a a vector with elements normalized between -1 and 1
	 */
	public static ArrayList<Double> tanh(ArrayList<Double> a) {
		ArrayList<Double> ret = new ArrayList<Double>();
		for(int i = 0; i<a.size(); i++) {
			ret.add(i, Math.tanh(a.get(i)));
		}
		return ret;
	}
	
	/**
	 * Calculate the derivative of tanh based on the x-value (not the y!).
	 * 
	 * @param a the x-value of the point
	 * @return the derivative
	 */
	public static double dtanh(double a) {
		return 1-Math.pow(Math.tanh(a), 2);
	}
}
