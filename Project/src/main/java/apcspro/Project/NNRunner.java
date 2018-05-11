package apcspro.Project;

import java.util.ArrayList;

public class NNRunner {	
	public NNRunner(int num_epochs, double learning_rate, NeuralNetwork net, ArrayList<ArrayList<Double> > x, ArrayList<ArrayList<Double> > y) {
		
		System.out.println("Initializing Neural Network.");
		
		double error;
		for(int i = 0; i<num_epochs; i++) {
			System.out.println("Beginning epoch " + i);
			for(int j = 0; j<x.size(); j++) {
				error = net.assess(x.get(j), y.get(j));
				net.forwardProp(x.get(j));
				net.backProp(y.get(j));
				net.gradientUpdate(x.get(j), learning_rate);
				System.out.println("Training sample " + j + " finished with " + error + " mean-squared error.");
			}
		}
	}
}
