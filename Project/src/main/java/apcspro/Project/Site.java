/**
 * Main class of the website
 * 
 * Uses Java Spark library to set up http connections view get and post mehtods
 * 
 * @author Patrick Zhang
 */
package apcspro.Project;
import java.time.Instant;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Spark;
public class Site {
	public static final transient Logger log=LoggerFactory.getLogger(Site.class);
	public TreeMap<String, NNRunner> activeNeuralNetworks;
	public static void main(String[] args) {
		Site site=new Site();
		site.initWebsite();
		log.info("setup complete");
		site.activeNeuralNetworks=new TreeMap<String, NNRunner>();
	}
	/*
	 * Initiate the website
	 */
	protected void initWebsite() {
		// choose arbitrary port
		Spark.port(1234);
		Spark.staticFileLocation("..");
		createRoutes();
	}
	/*
	 * Make url links
	 */
	protected void createRoutes() {
		//home page
		Spark.get("/", new HomeController(this));
		//about us page
		Spark.get("/about-us", new AboutUsController());
		//submitting form for home page
		Spark.post("/",new HomeController(this));
		//viewing neural network
		Spark.get("/viewprogress", new ViewProgressController(this));
		//Java api for ajax calls to update neural network
		Spark.post("/getupdate", new UpdateAPI(this));
	}
	/*
	 * Add new neural network to existing set of active neural networks
	 */
	public long addRunner(NNRunner nr) {
		long now=Instant.now().getEpochSecond();
		this.activeNeuralNetworks.put(now+"",nr);
		for(String s: this.activeNeuralNetworks.keySet())
			System.out.println(s);
		return now;
	}

}
