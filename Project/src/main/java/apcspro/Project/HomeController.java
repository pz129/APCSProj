package apcspro.Project;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

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
		System.out.println("here");
		if(request.requestMethod().toLowerCase().equals("post")) {
			System.out.println("here");
			String location = "/sounds";          // the directory location where files will be stored
			long maxFileSize = 100000000;       // the maximum size allowed for uploaded files
			long maxRequestSize = 100000000;    // the maximum size allowed for multipart/form-data requests
			int fileSizeThreshold = 2048;       // the size threshold after which files will be written to disk

			MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
			location, maxFileSize, maxRequestSize, fileSizeThreshold);
			request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
			multipartConfigElement);
			try {
			Collection<Part> parts = request.raw().getParts();
			for (Part part : parts) {
			   System.out.println("Name: " + part.getName());
			   System.out.println("Size: " + part.getSize());
			   System.out.println("Filename: " + part.getSubmittedFileName());
			}
			String fName = request.raw().getPart("zip-file").getSubmittedFileName();
			System.out.println("Title: " + request.raw().getParameter("title"));
			System.out.println("File: " + fName);
			String[] t=fName.split("\\.");
			String trimed=fName.substring(0, fName.length()-t[t.length-1].length());
			String tt=t[t.length-1];
			if(tt.equals("tar")||tt.equals("zip")) {
				Part uploadedFile = request.raw().getPart("sound-file");
				InputStream inputStream=uploadedFile.getInputStream();
				byte[] buffer=new byte[inputStream.available()];
				inputStream.read(buffer);
				OutputStream outStream=new FileOutputStream(new File("src/sounds/"+fName));
				outStream.write(buffer);
				//pythonRun()
				System.out.println(trimed);
				//site.downloadGet(trimed+'.txt');
				model.with("message", "Download the file <a href='/download/"+trimed+".txt'>here</a>");
			}
			else if(fName.trim().equals("")) {
				model.with("message", "Please input a file.");
			}else {
				model.with("message","File type not recognized, please submit mp3 or wav format");
			}
			}catch(IOException e){
				System.err.println(e.getMessage());
				model.with("message", "Please input a file.");
			}
		}
		else {
			model.with("message", "");
		}
		return jtwigTemplate.render(model);
	}

}