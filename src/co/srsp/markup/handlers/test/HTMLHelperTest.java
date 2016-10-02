package co.srsp.markup.handlers.test;

import org.junit.Assert;
import org.junit.Test;

import co.srsp.config.ConfigHandler;
import co.srsp.markup.handlers.HTMLHelper;
import co.srsp.viewmodel.HTMLModel;

public class HTMLHelperTest {
	
	
	private void setup(){
		ConfigHandler.getInstance().setUnderTest(true);
	}

	@Test
	public void testBookToReview(){
		HTMLModel htmlModel = new HTMLModel();
		htmlModel.setauthor("Paul Newell");
		htmlModel.settitle("My Homies");
		htmlModel.setimageHeight("120");
		htmlModel.setimageWidth("110");
		htmlModel.setpublisher("Harper Collins");
		htmlModel.setthumbnailLocation("/that.png");
		htmlModel.setexcerpt("That cat sat on that mat");
		
		HTMLHelper helper = new HTMLHelper();
		
		
		
		String stringRet = helper.formatReviewBookOnlyHTML(htmlModel);
		System.out.println("stringRet : "+stringRet);
		Assert.assertNotNull(stringRet);
		Assert.assertFalse(stringRet.contains("\""));
	}
	
	@Test
	public void testBookReviewsText(){
		ConfigHandler.getInstance().setUnderTest(true);
		
		HTMLModel htmlModel = new HTMLModel();
		htmlModel.setstarRating("3");
		htmlModel.setreviewersUserName("biggieSmalls");
		htmlModel.setreviewerText("The Quick brown fox jumped over the lazy dog.");

		
		HTMLHelper helper = new HTMLHelper();
		
		String stringRet = helper.formatReviewersHTML(htmlModel);
		System.out.println("stringRet : "+stringRet);
		Assert.assertNotNull(stringRet);
		Assert.assertFalse(stringRet.contains(":"));
		Assert.assertFalse(stringRet.contains("\""));
	}
	
	@Test
	public void testSearchText(){
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
		
		String formattedHTML = helper.formatSearchHTML(htmlModel);
		Assert.assertNotNull(formattedHTML); 
		Assert.assertFalse(formattedHTML.contains("\""));
	}
	
}
