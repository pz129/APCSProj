package apcspro.Project;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class NNRunner implements Runnable{
	public NeuralNetwork net;
	public int num_epochs;
	public double learning_rate;
	public ArrayList<ArrayList<Double>> x;
	public ArrayList<ArrayList<Double>> y;
	public ArrayList<Integer> dims;
	public NNRunner(int num_epochs, double learning_rate, ArrayList<Integer> dims, ArrayList<ArrayList<Double> > x, ArrayList<ArrayList<Double> > y) {
		net=new NeuralNetwork(dims);
		this.num_epochs=num_epochs;
		this.learning_rate=learning_rate;
		this.dims=dims;
		this.x=x;
		this.y=y;
	}
	public void run() {
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
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}
