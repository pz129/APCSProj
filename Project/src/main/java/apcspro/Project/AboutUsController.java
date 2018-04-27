package apcspro.Project;

import java.io.File;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import spark.Request;
import spark.Response;
import spark.Route;

public class AboutUsController  implements Route{

	public Object handle(Request arg0, Response arg1) throws Exception {
		JtwigTemplate jtwigTemplate = JtwigTemplate.fileTemplate(new File("src\\main\\java\\resources\\about-us.html.twig"));
		JtwigModel model= JtwigModel.newModel();		
		return jtwigTemplate.render(model);
	}

}