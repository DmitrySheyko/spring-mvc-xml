package com.dmitrySheyko;

import com.dmitrySheyko.web.config.WebContextConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class WebAppInitializer implements WebApplicationInitializer {

    Logger logger = Logger.getLogger(WebAppInitializer.class);

    @Override
    public void onStartup(jakarta.servlet.ServletContext servletContext) throws ServletException {

        logger.info("loading app-config");
        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation("classpath:app-config.xml");
        servletContext.addListener(new ContextLoaderListener(appContext));

        logger.info("loading web-config");
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register( WebContextConfig.class);
//        XmlWebApplicationContext w  ebContext = new XmlWebApplicationContext();
//        webContext.setConfigLocation("classpath:web-config.xml");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        logger.info("Dispatcher ready");
    }

}

