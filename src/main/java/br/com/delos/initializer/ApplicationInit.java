package br.com.delos.initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.delos.config.MvcConfig;
import br.com.delos.config.RepositoryConfig;
import br.com.delos.config.SecurityConfig;
import br.com.delos.config.ServiceConfig;

public class ApplicationInit extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {MvcConfig.class, SecurityConfig.class, RepositoryConfig.class, ServiceConfig.class};
	}

//	@Override
//	protected Class<?>[] getServletConfigClasses() {
//		return new Class[] {MvcConfig.class};
//	}
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}


}

