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
			NNRunner curr=site.activeNeuralNetworks.get(key);
			NeuralNetwork currNet=curr.net;
			System.out.println(curr==null);
			int r=25, space=30;
			ArrayList<ArrayList<ArrayList<Double>>> positions=new ArrayList<ArrayList<ArrayList<Double>>>();
			int max=0,calcWidth,calcHeight;
			calcWidth= curr.dims.size()*(2*r+space)-space+20;
			for(Integer a: curr.dims)
				max=Math.max(a, max);
			calcHeight=max*(2*r+space)-space+20;
			for(int i=0;i<curr.dims.size();i++) {
				ArrayList<ArrayList<Double>> layerpos= new ArrayList<ArrayList<Double>>();
				double margin=(calcHeight-(curr.dims.get(i)*(2*r+space)-space))/2;
				for(int j=0;j<curr.dims.get(i);j++) {
					ArrayList<Double> XAndY=new ArrayList<Double>();
					XAndY.add(10.0+i*(2*r+space)+r);
					XAndY.add(margin+j*(2*r+space)+r);
					layerpos.add(XAndY);
				}
				positions.add(layerpos);
			}
			
			model.with("positions",positions);
			model.with("calcWidth",calcWidth);
			model.with("calcHeight", calcHeight );
			model.with("r",r);
			model.with("space", space);
			model.with("error", 0);
			model.with("key",key);
			model.with("dims",curr.dims);
		}
		return jtwigTemplate.render(model);
	}

}
