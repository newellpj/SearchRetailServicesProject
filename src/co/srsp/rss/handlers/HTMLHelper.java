package co.srsp.rss.handlers;

import org.apache.log4j.Logger;

import co.srsp.config.ConfigHandler;
import co.srsp.viewmodel.HTMLModel;


public class HTMLHelper {
	
	private final static Logger log = Logger.getLogger(HTMLHelper.class); 
	
	public String formatReviewBookOnlyHTML(HTMLModel htmlModel){
		
		String reviewsListHTML = ConfigHandler.getInstance().readApplicationProperty("reviewsListHTML")  +
				 ConfigHandler.getInstance().readApplicationProperty("reviewsListHTML2") +
				 ConfigHandler.getInstance().readApplicationProperty("reviewsListHTML3") ;
		
		String substitutionPlaceholders = ConfigHandler.getInstance().readApplicationProperty("reviewsHTMLSubstititionVars");
		String[] subArray = substitutionPlaceholders.split(",");

		System.out.println("subArray : "+subArray.length);
		
		for(int i = 0; i < subArray.length; i++){
	
			
			System.out.println("sub array ::: "+subArray[i]);
			
			try{
				java.lang.reflect.Method method = htmlModel.getClass().
					getDeclaredMethod("get"+subArray[i], new Class[] {});
			
				Object obj = method.invoke(htmlModel);
				String value = (obj != null) ? obj.toString() : "";
				System.out.println("value : "+value);
				reviewsListHTML = reviewsListHTML.replace(":"+subArray[i], value );
				
			}catch(Throwable t){
				t.printStackTrace();
				System.out.println("we in here ");
			}
		}
		
		return reviewsListHTML;
	}
	
	public static void main(String args[]){
		
		HTMLModel htmlModel = new HTMLModel();
		htmlModel.setauthor("Paul Newell");
		htmlModel.settitle("My Homies");
		htmlModel.setimageHeight("120");
		htmlModel.setimageWidth("110");
		htmlModel.setpublisher("Harper Collins");
		htmlModel.setthumbnailLocation("/that.png");
		htmlModel.setexcerpt("That cat sat on that mat");
		
		HTMLHelper helper = new HTMLHelper();
		
		System.out.println("formatted html returned : "+helper.formatReviewBookOnlyHTML(htmlModel));
	}

}
