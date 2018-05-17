package apcspro.Project;

import java.io.File;
import java.util.ArrayList;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import spark.Request;
import spark.Response;
import spark.Route;

public class ViewProgressController  implements Route{
	Site site;
	/*
	 * reference back to site
	 */
	ViewProgressController (Site site){
		this.site=site;
	}
	/*
	 * (non-Javadoc)
	 * @see spark.Route#handle(spark.Request, spark.Response)
	 * 
	 * Uses viewprogress.html.twig
	 * Creates page to view state of neural network
	 */
	public Object handle(Request request, Response response) throws Exception {
		JtwigTemplate jtwigTemplate = JtwigTemplate.fileTemplate(new File("src\\main\\java\\resources\\viewprogress.html.twig"));
		JtwigModel model= JtwigModel.newModel();
		String key=request.queryParams("key");
		if(key==null || site.activeNeuralNetworks.get(key)==null) {
			model.with("error", 1);
		}
		else {
			NeuralNetwork curr=site.activeNeuralNetworks.get(key).net;
			model.with("error", 0);
			model.with("key",key);
			model.with("calcWidth", curr.outputs.size()*75+25);
			int max=0;
			for(ArrayList a: curr.outputs)
				max=Math.max(a.size(), max);
			model.with("calcHeight", max*75+25);
		}
		return jtwigTemplate.render(model);
	}

}
