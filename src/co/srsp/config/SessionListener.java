package co.srsp.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import co.srsp.controller.PaginationController;

public class SessionListener implements HttpSessionListener  {
	
	private final static Logger log = Logger.getLogger(SessionListener.class); 
	
	@Override
    public void sessionCreated(HttpSessionEvent event) {
		log.info("==== Session is created ==== "+event.getSession().getServletContext().getServerInfo());

        event.getSession().setMaxInactiveInterval(2*60);
        log.info("event source obj : "+event.getSource().getClass());

    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        log.info("==== Session is destroyed ==== : "+event.getSession().getServletContext().getServerInfo());
    }
}
