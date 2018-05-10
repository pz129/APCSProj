package apcspro.Project;

import java.util.ArrayList;

public class MatrixOps {
	
	public static ArrayList<Double> subtract(ArrayList<Double> a, ArrayList<Double> b) {
		assert a.size() == b.size() : "Arrays must be equal length for element-wise subtraction.";
		ArrayList<Double> ret = new ArrayList<Double>(a.size());
		for(int i = 0; i<a.size(); i++) {
			ret.add(i, a.get(i) - b.get(i));
		}
		return ret;
	}
	
	public static ArrayList<Double> multiply(ArrayList<Double> a, ArrayList<Double> b) {
		assert a.size() == b.size() : "Arrays must be equal length for element-wise multiplication.";
		ArrayList<Double> ret = new ArrayList(a.size());
		for(int i = 0; i<a.size(); i++) {
			ret.add(i, a.get(i) * b.get(i));
		}
		return ret;
	}
	
	public static ArrayList<Double> layerMult(ArrayList<ArrayList<Double> > weights, ArrayList<Double> inputs) {
		ArrayList<Double> ret = new ArrayList();
		for(int i = 0; i<weights.size(); i++) {
			assert inputs.size() == weights.get(i).size() : "Dimensions (a, b) x (b, c) must match for layer multiplication.";
			double total = 0;
			for(int j = 0; j<inputs.size(); j++) {
				total += inputs.get(j) * weights.get(i).get(j); 
			}
			ret.add(total);
		}
		return ret;
	}
	
	public static ArrayList<Double> tanh(ArrayList<Double> a) {
		ArrayList<Double> ret = new ArrayList();
		for(int i = 0; i<a.size(); i++) {
			ret.add(i, Math.tanh(a.get(i)));
		}
		return ret;
	}
	
	public static double dtanh(double a) {
		return 1-Math.pow(Math.tanh(a), 2);
	}
}