package co.srsp.markup.handlers;

import org.apache.log4j.Logger;

import co.srsp.config.ConfigHandler;
import co.srsp.viewmodel.HTMLModel;


public class HTMLHelper {
	
	private final static Logger log = Logger.getLogger(HTMLHelper.class); 
	
	public String formatReviewBookOnlyHTML(HTMLModel htmlModel){
		
		String bookToReviewsHTML = ConfigHandler.getInstance().readApplicationProperty("reviewsListHTML")  +
				 ConfigHandler.getInstance().readApplicationProperty("reviewsListHTML2") +
				 ConfigHandler.getInstance().readApplicationProperty("reviewsListHTML3") ;
		
		String substitutionPlaceholders = ConfigHandler.getInstance().readApplicationProperty("bookToReviewHTMLSubstititionVars");
		String[] subArray = substitutionPlaceholders.split(",");

		System.out.println("subArray : "+subArray.length);
		
		for(int i = 0; i < subArray.length; i++){
			
			String subVar = subArray[i].trim();
			
			System.out.println("sub array ::: "+subArray[i]);
			
			try{
				java.lang.reflect.Method method = htmlModel.getClass().
					getDeclaredMethod("get"+subVar, new Class[] {});
			
				Object obj = method.invoke(htmlModel);
				String value = (obj != null) ? obj.toString() : "";
				System.out.println("value : "+value);
				bookToReviewsHTML = bookToReviewsHTML.replace(":"+subVar, value );
				
			}catch(Throwable t){
				t.printStackTrace();
				System.out.println("we in here ");
			}
		}
		
		return bookToReviewsHTML;
	}
	
	public String formatSearchHTML(HTMLModel htmlModel){
		
		String searchListHTML = ConfigHandler.getInstance().readApplicationProperty("searchListHTML")  +
				 ConfigHandler.getInstance().readApplicationProperty("searchListHTML2") + ConfigHandler.getInstance().readApplicationProperty("searchListHTML3") +
				 ConfigHandler.getInstance().readApplicationProperty("searchListHTML4") +ConfigHandler.getInstance().readApplicationProperty("searchListHTML5");
		
		String substitutionPlaceholders = ConfigHandler.getInstance().readApplicationProperty("searchHTMLSubstititionVars");
		String[] subArray = substitutionPlaceholders.split(",");

		System.out.println("subArray : "+subArray.length);
		
		for(int i = 0; i < subArray.length; i++){
	
			
			System.out.println("sub array ::: "+subArray[i]);
			
			String subVar = subArray[i].trim();
			
			try{
				java.lang.reflect.Method method = htmlModel.getClass().
					getDeclaredMethod("get"+subVar, new Class[] {});
			
				Object obj = method.invoke(htmlModel);
				String value = (obj != null) ? obj.toString() : "";
				System.out.println("value : "+value);
				searchListHTML = searchListHTML.replace(":"+subVar, value );
				
			}catch(Throwable t){
				t.printStackTrace();
				System.out.println("we in here ");
			}
		}
		
		return searchListHTML;
	}
	
	public String formatReviewersHTML(HTMLModel htmlModel){

		String reviewsListHTML = ConfigHandler.getInstance().readApplicationProperty("reviewsStarRatingHTML")  +
				 ConfigHandler.getInstance().readApplicationProperty("reivewsReviewTextHTML");
		
		String substitutionPlaceholders = ConfigHandler.getInstance().readApplicationProperty("reviewsHTMLSubstititionVars");
		String[] subArray = substitutionPlaceholders.split(",");

		System.out.println("subArray : "+subArray.length);
		
		for(int i = 0; i < subArray.length; i++){
	
			
			System.out.println("sub array ::: "+subArray[i]);
			
			String subVar = subArray[i].trim();
			
			try{
				java.lang.reflect.Method method = htmlModel.getClass().
					getDeclaredMethod("get"+subVar, new Class[] {});
			
				Object obj = method.invoke(htmlModel);
				String value = (obj != null) ? obj.toString() : "";
				System.out.println("value : "+value);
				reviewsListHTML = reviewsListHTML.replace(":"+subVar, value );
				
			}catch(Throwable t){
				t.printStackTrace();
				System.out.println("we in here ");
			}
		}
		
		return reviewsListHTML;
		
	}
	
	public static void main(String args[]){
		
		ConfigHandler.getInstance().setUnderTest(true);
		
		HTMLModel htmlModel = new HTMLModel();
		htmlModel = new HTMLModel();
		htmlModel.setauthor("Paul Newell");
		htmlModel.settitle("My Homies");
		htmlModel.setimageHeight("120");
		htmlModel.setimageWidth("110");
		htmlModel.setpublisher("Harper Collins");
		htmlModel.setthumbnailLocation("that.png");
		htmlModel.setexcerpt("That cat sat on that mat");
		htmlModel.setbookDetails("My Homies - Paul Newell");

		
		HTMLHelper helper = new HTMLHelper();
		
		System.out.println("formatted html returned : "+helper.formatSearchHTML(htmlModel));
		
//		HTMLModel htmlModel = new HTMLModel();
//		htmlModel.setstarRating("3");
//		htmlModel.setreviewersUserName("biggieSmalls");
//		htmlModel.setreviewerText("The Quick brown fox jumped over the lazy dog.");
//
//		
//		HTMLHelper helper = new HTMLHelper();
//		
//		System.out.println("formatted html returned : "+helper.formatReviewersHTML(htmlModel));
		
//		
//		htmlModel = new HTMLModel();
//		htmlModel.setauthor("Paul Newell");
//		htmlModel.settitle("My Homies");
//		htmlModel.setimageHeight("120");
//		htmlModel.setimageWidth("110");
//		htmlModel.setpublisher("Harper Collins");
//		htmlModel.setthumbnailLocation("/that.png");
//		htmlModel.setexcerpt("That cat sat on that mat");
//		
//		
//		System.out.println("formatted html returned : "+helper.formatReviewBookOnlyHTML(htmlModel));
//		
	}

}
