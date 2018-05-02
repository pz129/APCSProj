package apcspro.Project;

import java.util.ArrayList;

import apcspro.Project.Site.Temp;
import spark.Request;
import spark.Response;
import spark.Route;

public class UpdateAPI implements Route{
	Site site;
	UpdateAPI (Site site){
		this.site=site;
	}
	public Object handle(Request request, Response response) throws Exception {
		String key=request.queryParams("key"), type=request.queryParams("type");
		if(key==null||type==null||site.activeNeuralNetworks.get(key)==null)
			return "";
		else if(type.equals("neuralnet")){
			Temp curr=site.activeNeuralNetworks.get(key);
			return "{ nodes: \""+nodesToString(curr.nodes)+"\", edges: \""+weightsToString(curr.weights)+"\", status: \""+curr.status+"\"}";
		}
		else if(type.equals("accuracy")){
			return "100%";
		}
		else
			return "";

		
	}
	public static String weightsToString(ArrayList<ArrayList<ArrayList<Double>>> weights) {
		StringBuilder sb=new StringBuilder();
		sb.append('[');
		ArrayList<ArrayList<Double>> ar1;
		ArrayList<Double> ar2;
		for(int i=0;i<weights.size();i++) {
			sb.append('[');
			ar1=weights.get(i);
			for(int j=0;j<ar1.size();j++) {
				sb.append('[');
				ar2=ar1.get(j);
				for(int k=0;k<ar2.size();k++) {
					sb.append(ar2.get(k));
					if(i<weights.size()-1) 
						sb.append(',');
				}
				sb.append(']');
				if(i<weights.size()-1) 
					sb.append(',');
			}
			sb.append(']');
			if(i<weights.size()-1)
				sb.append(',');
		}
		sb.append(']');
		return sb.toString();
	}
	public String nodesToString(ArrayList<ArrayList<Double>> weights) {
		StringBuilder sb=new StringBuilder();
		sb.append('[');
		ArrayList<Double> ar1;
		for(int i=0;i<weights.size();i++) {
			sb.append('[');
			ar1=weights.get(i);
			for(int j=0;j<ar1.size();j++) {
				sb.append(ar1.get(j));
				if(i<weights.size()-1) 
					sb.append(',');
			}
			sb.append(']');
			if(i<weights.size()-1)
				sb.append(',');
		}
		sb.append(']');
		return sb.toString();
	}

}