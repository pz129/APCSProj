package apcspro.Project;

import java.io.File;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import spark.Request;
import spark.Response;
import spark.Route;

public class ViewProgressController  implements Route{
	Site site;
	ViewProgressController (Site site){
		this.site=site;
	}
	public Object handle(Request request, Response response) throws Exception {
		JtwigTemplate jtwigTemplate = JtwigTemplate.fileTemplate(new File("src\\main\\java\\resources\\viewprogress.html.twig"));
		JtwigModel model= JtwigModel.newModel();
		String key=request.queryParams("key");
		if(key==null || site.activeNeuralNetworks.get(key)==null) {
			model.with("error", 1);
		}
		else {
			NeuralNetwork curr=site.activeNeuralNetworks.get(key);
			model.with("error", 0);
			model.with("key",key);
			model.with("calcWidth", curr.outputs.size()*75+25);
			model.with("calcHeight", curr.outputs.get(0).size()*75+25);
		}
		return jtwigTemplate.render(model);
	}

}
