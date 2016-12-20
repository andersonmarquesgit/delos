package br.com.delos.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.delos.scope.spring.ViewScope;

@Configuration
@EnableWebMvc
@ComponentScan("br.com.delos.controller")
public class MvcConfig extends WebMvcConfigurerAdapter {

	private static final Logger log = Logger.getLogger(MvcConfig.class);
	
	public MvcConfig() {
		log.info("::::Inicialização do MVC Config e Escaneamento dos Controladores::::");
	}

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
	    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	    resolver.setPrefix("/paginas/");
	    resolver.setSuffix(".xhtml");
	    return resolver;
	}
	
	@Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new GzipResourceResolver())
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean
	public static CustomScopeConfigurer customScopeConfigurer(){
		CustomScopeConfigurer view = new CustomScopeConfigurer();
		Map<String,Object> scopes = new HashMap<String,Object>();
		scopes.put("view",new ViewScope());
		view.setScopes(scopes);
		return view;
	}

}
