package apcspro.Project;
import java.time.Instant;
import java.util.ArrayList;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Spark;
public class Site {
	public static final transient Logger log=LoggerFactory.getLogger(Site.class);
	public TreeMap<String, Temp> activeNeuralNetworks;
	public static void main(String[] args) {
		Site site=new Site();
		site.initWebsite();
		log.info("setup complete");
		site.activeNeuralNetworks=new TreeMap<String, Temp>();
	}
	protected void initWebsite() {
		Spark.port(1234);
		Spark.staticFileLocation("..");
		createRoutes();
	}
	protected void createRoutes() {
		Spark.get("/", new HomeController(this));
		Spark.get("/about-us", new AboutUsController());
		Spark.post("/",new HomeController(this));
		Spark.get("/viewprogress", new ViewProgressController(this));
		Spark.post("/getupdate", new UpdateAPI(this));
		Spark.post("/update", new UpdateAPI(this));
	}
	public void tempAdd() {
		this.activeNeuralNetworks.put(Instant.now().toEpochMilli()+"",new Temp());
		for(String s: this.activeNeuralNetworks.keySet())
			System.out.println(s);
	}
	public class Temp{
		ArrayList<ArrayList<ArrayList<Double>>> weights;
		ArrayList<ArrayList<Double>> nodes;
		String status;
		public Temp() {
		}
	}

}
