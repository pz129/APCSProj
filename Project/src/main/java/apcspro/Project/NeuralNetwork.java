/**
 * A library of neural network operations.
 * 
 * @author Evan Weissburg
 */

package apcspro.Project;

import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetwork {
	public ArrayList<ArrayList<ArrayList<Double> > > weights;
	public ArrayList<ArrayList<Double> > outputs;
	public ArrayList<ArrayList<Double> > gradients;
	
	/**
	 * @return the 3D matrix of weights
	 */
	public ArrayList<ArrayList<ArrayList<Double> > > getWeights() {
		return weights;
	}
	
	/**
	 * Construct and initialize a neural network based on given dimensions.
	 * 
	 * @param dims the list of neural network layer sizes
	 */
	public NeuralNetwork(ArrayList<Integer> dims) {
		weights = new ArrayList<ArrayList<ArrayList<Double>>>();
		for(int i = 0; i<dims.size()-1; i++) {
			weights.add(new ArrayList<ArrayList<Double>>());
			for(int j = 0; j<dims.get(i+1); j++) {
				weights.get(i).add(new ArrayList<Double>());
				for(int k = 0; k<=dims.get(i); k++) { // add bias
					weights.get(i).get(j).add(Math.random());
				}
			}
		}
	}
	
	/**
	 * @param x the single training input vector used for forward propagation
	 * @return the single prediction vector based on x
	 */
	public ArrayList<Double> forwardProp(ArrayList<Double> x) {
		outputs = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i<weights.size(); i++) {
			x = MatrixOps.tanh(MatrixOps.layerMult(x, weights.get(i)));
			x.add(1.0);
			outputs.add(x);
		}
		x.remove(x.size()-1);
		return x;
	}
	
	/**
	 * Calculate gradients w.r.t to each neuron based on calculated error.
	 * 
	 * @param y the ground-truth output vector for comparison to prediction vector
	 */
	public void backProp(ArrayList<Double> y) {
		gradients = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> deltas = new ArrayList<Double>();
		for(int i = 0; i<weights.size(); i++) {
			gradients.add(new ArrayList<Double>());
		}
		for(int i = weights.size()-1; i >= 0; i--) {
			deltas.clear();
			if(i == weights.size()-1) {
				for(int j = 0; j<y.size(); j++) {
					deltas.add(j, y.get(j)-outputs.get(i).get(j));
				}
			} else {
				for(int j = 0; j<weights.get(i).size(); j++) {
					double delta = 0;
					for(int k = 0; k<weights.get(i+1).size(); k++) {
						delta += weights.get(i+1).get(k).get(j) * gradients.get(i+1).get(k);
					}
					deltas.add(j, delta);
				}
			}
            for(int j = 0; j<deltas.size(); j++) {
            		gradients.get(i).add(j, deltas.get(j) * MatrixOps.dtanh(outputs.get(i).get(j)));
            }
		}
	}
	
	/**
	 * Create and apply gradients w.r.t each weight based on the gradients w.r.t each neuron.
	 * 
	 * @param x the original inputs; used to calculate gradients relative to each weight
	 * @param learning_rate the alpha used to slow-down gradient descent
	 */
	public void gradientUpdate(ArrayList<Double> x, double learning_rate) {
		ArrayList<Double> inputs = new ArrayList<Double>();
		for(double n: x) {
			inputs.add(n);
		}
		for(int i = 0; i<weights.size(); i++) {
			for(int j = 0; j<weights.get(i).size(); j++) {
				for(int k = 0; k<weights.get(i).get(j).size()-1; k++) {
					double update = learning_rate * gradients.get(i).get(j) * inputs.get(k);
					weights.get(i).get(j).set(k, weights.get(i).get(j).get(k) + update);
				}
			}
			inputs.clear();
			for(double n: outputs.get(i)) {
				inputs.add(n);
			}
		}
	}
	
	/**
	 * Calculate mean-squared error for a training example.
	 * 
	 * @param x the input vector for prediction
	 * @param y the ground-truth output vector
	 * @return
	 */
	public double assess(ArrayList<Double> x, ArrayList<Double> y) {
		ArrayList<Double> preds = forwardProp(x);
		double accuracy = 0;
		for(int i = 0; i<preds.size(); i++) {
			System.out.print(preds.get(i) + " ");
			accuracy += Math.abs(preds.get(i) - y.get(i));
		}
		System.out.println();
		return accuracy/x.size();
	}
}