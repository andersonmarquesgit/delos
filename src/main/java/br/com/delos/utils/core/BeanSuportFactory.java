package br.com.delos.utils.core;

import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component
public class BeanSuportFactory implements ApplicationContextAware {
	static final Logger log = Logger.getLogger(BeanSuportFactory.class.getName());
	private static ApplicationContext applicationContext;
	  
	@Override
	public void setApplicationContext(ApplicationContext actx)
			throws BeansException {
  		inicializar(actx);
	}

	private static void inicializar(ApplicationContext actx) {
		applicationContext = actx;
	}

	public static String obterProperty(String nomeProperty) {
		Environment env = (Environment) applicationContext
				.getBean("environment");

		return env.getProperty(nomeProperty);
	}

	public static Object obterBean(String nomeBean) {
		Object bean = null;
		try {
			bean = applicationContext.getBean(nomeBean);
		} catch (Exception be) {
			log.info("O bean: '" + nomeBean
					+ "' , não foi localizado no Application Context!");
			be.printStackTrace();
		}

		return bean;
	}
}
