package apcspro.Project;

/**
 * Controller for AJAX calls by website in order to update the live neuralnetwork feed
 * 
 * @author Patrick Zhang
 */
import spark.Request;
import spark.Response;
import spark.Route;

public class UpdateAPI implements Route{
	Site site;
	/*
	 * reference back to site
	 */
	UpdateAPI (Site site){
		this.site=site;
	}
	/*
	 * (non-Javadoc)
	 * @see spark.Route#handle(spark.Request, spark.Response)
	 * 
	 * Uses parameter "key" of request to return the current state of the neural network associated with the key
	 * State includes node weights, edge weights, status, loss, and accuracy
	 */
	public Object handle(Request request, Response response) throws Exception {
		//find neural network associated with key
		String key=request.queryParams("key"), type=request.queryParams("type");
		if(key==null||type==null||site.activeNeuralNetworks.get(key)==null)
			return "";
		else if(type.equals("neuralnet")){
			NNRunner c=site.activeNeuralNetworks.get(key);
			NeuralNetwork curr=c.net;
			//return json object of node weights, edge weights, status, and mean-squared error
			return "{ \"nodes\": \""+curr.outputs+"\", \"edges\": \""+curr.weights+"\", \"status\": \""+c.done+"\" , \"error\":"+c.error+"}";
		}
		else
			return "";

		
	}
}
