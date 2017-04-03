package com.egen.movieflix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
@Configuration
public class WebAppInit extends WebMvcConfigurerAdapter{
	@Bean
	public ViewResolver viewResolver() {
	InternalResourceViewResolver resolver =
	new InternalResourceViewResolver();
	resolver.setPrefix("/WEB-INF/views/");
	resolver.setSuffix(".jsp");
	resolver.setViewClass(JstlView.class);

	resolver.setExposeContextBeansAsAttributes(true);
	
	return resolver;
	}
	@Override
	public void configureDefaultServletHandling(
	DefaultServletHandlerConfigurer configurer) {
	configurer.enable();
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
                        .addResourceLocations("/resources/");
	}
	

}
