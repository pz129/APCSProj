/**
 * Controller to intiate home page where users submit their data for a neural network
 * 
 * @author Patrick Zhang
 */
package apcspro.Project;
import java.io.File;
import java.util.ArrayList;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import spark.Request;
import spark.Response;
import spark.Route;
public class HomeController implements Route{
	Site site;
	HomeController (Site site){
		this.site=site;
	}
	public Object handle(Request request, Response response) throws Exception {
		JtwigTemplate jtwigTemplate = JtwigTemplate.fileTemplate(new File("src\\main\\java\\resources\\home.html.twig"));
		JtwigModel model= JtwigModel.newModel();
		// on submission of test data to make a new neural network
		if(request.requestMethod().toLowerCase().equals("post")) {
			try {
				//parse information from POST request
				int epochs=Integer.parseInt(request.queryParams("epochs"));
				double learnrate=Double.parseDouble(request.queryParams("learnrate"));
				String x=request.queryParams("xval");
				String y=request.queryParams("yval");
				int dimCount=0;
				String tempDim;
				ArrayList<Integer> dims=new ArrayList<Integer>();
				while(dimCount<Integer.MAX_VALUE) {
					tempDim=request.queryParams("dim"+dimCount);
					if(tempDim==null) break;
					dims.add(Integer.parseInt(tempDim));
					dimCount++;
				}
				ArrayList<ArrayList<Double>> xarr=new ArrayList<ArrayList<Double>>(),
										yarr=new ArrayList<ArrayList<Double>>();
				ParseString(x,xarr,dims.get(0));
				ParseString(y,yarr,dims.get(dims.size()-1));
				//create the neural network after parsing all the data
				NNRunner newrunner=new NNRunner(epochs,learnrate,dims,xarr,yarr);
				long now=site.addRunner(newrunner);
				//run the neural network on a new thread
				Thread t=new Thread(newrunner);
				t.start();
				//go to a new page to view it training live
				response.redirect("/viewprogress?key="+now);
			}catch(Exception x){
				x.printStackTrace();
			}
			
			model.with("message", "An error has occurred, some input is invalid");
		}
		return jtwigTemplate.render(model);
	}
	/*
	 * Turn string of input and ouput and parse the values into doubles to store in Arrays
	 */
	public static void ParseString(String vals,ArrayList<ArrayList<Double>> toFill, int size) {
		String[] split=vals.split("\n");
		for(String s: split) {
			ArrayList<Double> temp=new ArrayList<Double>();
			String[] split2=s.split(" ");
			for(int i=0;i<size;i++) {
				if(i>=split2.length)
					temp.add(0.0);
				else
					temp.add(Double.parseDouble(split2[i]));
			}
			toFill.add(temp);
		}
	}
}