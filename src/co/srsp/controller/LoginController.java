package co.srsp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import co.srsp.jms.Publisher;
import co.srsp.solr.SolrSearchManager;

@Controller
public class LoginController implements AuthenticationSuccessHandler, AuthenticationFailureHandler{

	private final static Logger log = Logger.getLogger(LoginController.class); 
	
	private String defaultTargetUrl;


	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		log.info("login code");
		
		ModelAndView model = new ModelAndView();		
		model.setViewName("login");
		return model;

	}
	
	@RequestMapping(value = { "/sendJMS"}, method = RequestMethod.GET)
	public ModelAndView sendJMS() {
		Publisher topicPubliserBean = null;
		try{
			ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
			log.info("here 1");
			topicPubliserBean = (Publisher) ctx.getBean("topicPublisherBean");
			log.info("here 2");
			topicPubliserBean.sendName("I am the great Cornholio ", " I need teepees for my bunghole");
			log.info("here 3");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("found error : "+e.getMessage());
			log.error("found error : "+e.getStackTrace());
			if(topicPubliserBean != null){
				try{
					topicPubliserBean.closeConnection();
				}catch(Exception ee ){
					ee.printStackTrace();
					log.error("found error 2 : "+ee.getMessage());
					
				}
			}
		}
		
		ModelAndView model = new ModelAndView();		
		model.setViewName("login");
		return model;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println("logout!!!!!!");
		
		request.getSession().invalidate();
	    SecurityContextHolder.getContext().setAuthentication(null);
		
		ModelAndView model = new ModelAndView();
		model.addObject("error", "Successfully logged out!");
		model.setViewName("logout");
		
		log.info(" we here again logout!!!!!!");
		
		//response.sendRedirect("logout");
		
		
		
		return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		//request.getSession(true);
		
		String referrer = request.getHeader("referer");
		
		
		log.info("logger we again login and referrer is :::: "+referrer);
	
		ModelAndView model = new ModelAndView();
		if (error != null) {
			System.out.println("error != null : "+error);
			model.addObject("error", "Incorrect username and password!");
			model.setViewName("login");	
		}else{
			model.setViewName("login");
			log.info("ELSE!!!!!!");
			//response.sendRedirect("login");
		}
		
		
		return model;

	}
	
	@RequestMapping("accessDenied")
	public String accessDenied(){
		log.info("accessDenied");
		return "accessDenied";
		
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse response, Authentication arg2)
			throws IOException, ServletException {
		log.info("onAuthenticationSuccess");
		response.sendRedirect("reviews");
	}

	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
			throws IOException, ServletException {
		log.info("onAuthenticationFailure");
		// TODO Auto-generated method stub
		
	}

	
	
}
