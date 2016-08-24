package co.srsp.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	private final static Logger log = Logger.getLogger(TestController.class); 
	
	@RequestMapping(value = { "/compTest"}, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("we getting in here - "+auth.getName());
		ModelAndView model = new ModelAndView();		
		model.setViewName("compTest");
		return model;
	}
}
