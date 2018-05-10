package apcspro.Project;

import java.util.ArrayList;

public class NeuralNetwork {
	ArrayList<ArrayList<ArrayList<Double> > > weights;
	ArrayList<ArrayList<Double> > outputs;
	ArrayList<ArrayList<Double> > gradients;
	String status="";
	public NeuralNetwork(ArrayList<Integer> dims) {
		weights = new ArrayList();
		for(int i = 0; i<dims.size()-1; i++) {
			weights.add(new ArrayList());
			for(int j = 0; j<dims.get(i+1); j++) {
				weights.get(i).add(new ArrayList());
				for(int k = 0; k<dims.get(i); k++) {
					weights.get(i).get(j).add(Math.random());
				}
			}
		}
		
		System.out.println("Printing out neural network");
		for(ArrayList<ArrayList<Double> > layer: weights) {
			System.out.println("Beginning new layer");
			for(ArrayList<Double> neuron: layer) {
				for(double weight: neuron) {
					System.out.print(weight + " ");
				}
				System.out.println();
			}
		}
	}
	
	private ArrayList<Double> forwardProp(ArrayList<Double> x) {
		outputs = new ArrayList();
		for(int i = 0; i<weights.size(); i++) {
			x = MatrixOps.tanh(MatrixOps.layerMult(weights.get(i), x));
			outputs.add(x);
		}
		return x;
	}
	
	private void backProp(ArrayList<Double> y) {
		gradients = new ArrayList();
		ArrayList<Double> deltas = new ArrayList();
		for(int i = 0; i<weights.size(); i++) {
			gradients.add(new ArrayList());
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
	
	private void gradientUpdate(ArrayList<Double> x, double learning_rate) {
		ArrayList<Double> inputs = new ArrayList();
		for(double n: x) {
			inputs.add(n);
		}
		for(int i = 0; i<weights.size(); i++) {
			for(int j = 0; j<weights.get(i).size(); j++) {
				for(int k = 0; k<weights.get(i).get(j).size(); k++) {
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
	
	public void step(ArrayList<Double> x, ArrayList<Double> y, double learning_rate) {
		predict(x);
		forwardProp(x);
		backProp(y);
		gradientUpdate(x, learning_rate);
		
	}
	
	public void predict(ArrayList<Double> x) {
		ArrayList<Double> preds = forwardProp(x);
		for(double n: preds) System.out.print(n + " ");
		System.out.println();
	}
}