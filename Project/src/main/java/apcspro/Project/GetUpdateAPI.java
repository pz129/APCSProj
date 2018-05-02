package apcspro.Project;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetUpdateAPI implements Route{
	Site site;
	GetUpdateAPI (Site site){
		this.site=site;
	}
	public Object handle(Request request, Response response) throws Exception {
		
		return response;
		
	}

}
