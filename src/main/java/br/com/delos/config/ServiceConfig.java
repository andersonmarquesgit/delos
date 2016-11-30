package br.com.delos.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("br.com.delos.service")
public class ServiceConfig {

	private static final Logger log = Logger.getLogger(ServiceConfig.class);

	public ServiceConfig() {
		log.info("::::Inicializa��o do Service Config e Escaneamento dos Servi�os::::");
	}
	
}