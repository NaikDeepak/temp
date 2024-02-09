package com.jda.snew.services.api;

import javax.ws.rs.ApplicationPath;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
@ApplicationPath("/api")
public class RestConfig  extends ResourceConfig{
	public RestConfig(){
		packages("com.jda.snew.services");
		register(new JacksonJsonProvider());
	}
}