package apcspro.Project;

import java.io.File;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import spark.Request;
import spark.Response;
import spark.Route;

public class ViewProgressController  implements Route{
	Site site;
	String key;
	ViewProgressController (Site site, String key){
		this.site=site;
		this.key=key;
	}
	public Object handle(Request request, Response response) throws Exception {
		JtwigTemplate jtwigTemplate = JtwigTemplate.fileTemplate(new File("src\\main\\java\\resources\\viewprogress.html.twig"));
		JtwigModel model= JtwigModel.newModel();		
		model.with("key", key);
		return jtwigTemplate.render(model);
	}

}
