/**
 * Controller to initiate page that views a running neural network
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
			//finds neural network from a key
			NNRunner curr=site.activeNeuralNetworks.get(key);
			int r=25, vspace=30,hspace=70; // radius =25 px, vertical space between node= 30 px, vertical space between nodes = 70px
			ArrayList<ArrayList<ArrayList<Double>>> positions=new ArrayList<ArrayList<ArrayList<Double>>>(); // positions of nodes on canvas
			int max=0,calcWidth,calcHeight;
			calcWidth= curr.dims.size()*(2*r+hspace)-hspace+20;
			for(Integer a: curr.dims)
				max=Math.max(a, max);
			calcHeight=max*(2*r+vspace)-vspace+20;
			//calculate the positions of the nodes
			for(int i=0;i<curr.dims.size();i++) {
				ArrayList<ArrayList<Double>> layerpos= new ArrayList<ArrayList<Double>>();
				double margin=(calcHeight-(curr.dims.get(i)*(2*r+vspace)-vspace))/2;
				for(int j=0;j<curr.dims.get(i);j++) {
					ArrayList<Double> XAndY=new ArrayList<Double>();
					XAndY.add(10.0+i*(2*r+hspace)+r);
					XAndY.add(margin+j*(2*r+vspace)+r);
					layerpos.add(XAndY);
				}
				positions.add(layerpos);
			}
			//send all the calculated postitions and lengths to the frontend
			model.with("positions",positions);
			model.with("calcWidth",calcWidth);
			model.with("calcHeight", calcHeight );
			model.with("r",r);
			model.with("error", 0);
			model.with("key",key);
			model.with("dims",curr.dims);
		}
		return jtwigTemplate.render(model);
	}

}
