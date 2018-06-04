package apcspro.Project;
import java.util.ArrayList;

public class NeuralNetwork {
	public ArrayList<ArrayList<ArrayList<Double> > > weights;
	public ArrayList<ArrayList<Double> > outputs;
	public ArrayList<ArrayList<Double> > gradients;
	
	public ArrayList<ArrayList<ArrayList<Double> > > getWeights() {
		return weights;
	}
	
	public NeuralNetwork(ArrayList<Integer> dims) {
		weights = new ArrayList();
		for(int i = 0; i<dims.size()-1; i++) {
			weights.add(new ArrayList());
			for(int j = 0; j<dims.get(i+1); j++) {
				weights.get(i).add(new ArrayList());
				for(int k = 0; k<=dims.get(i); k++) { // add bias
					weights.get(i).get(j).add(Math.random());
				}
			}
		}
		System.out.println(weights);
	}
	
	public ArrayList<Double> forwardProp(ArrayList<Double> x) {
		outputs = new ArrayList();
		for(int i = 0; i<weights.size(); i++) {
			x = MatrixOps.tanh(MatrixOps.layerMult(weights.get(i), x));
			x.add(1.0);
			outputs.add(x);
		}
		x.remove(x.size()-1);
		return x;
	}
	
	public void backProp(ArrayList<Double> y) {
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
	
	public void gradientUpdate(ArrayList<Double> x, double learning_rate) {
		ArrayList<Double> inputs = new ArrayList();
		for(double n: x) {
			inputs.add(n);
		}
		for(int i = 0; i<weights.size(); i++) {
			for(int j = 0; j<weights.get(i).size(); j++) {
				for(int k = 0; k<weights.get(i).get(j).size()-1; k++) {
					System.out.println(i+" "+j+" "+k+" "+inputs.size());
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