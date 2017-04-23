package com.egen.movieflix.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"com.egen.movieflix.component","com.egen.movieflix.security","com.egen.movieflix.service","com.egen.movieflix.repository","com.egen.movieflix.controller"})
@EnableWebMvc
public class AppConfig {
}
