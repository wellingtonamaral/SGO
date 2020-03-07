package br.pro.tcc.sgo.util;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

//http://localhost:8080/Sgo/rest
@ApplicationPath("rest")
public class SgoResourceConfig extends ResourceConfig {
	public SgoResourceConfig(){
		packages("br.pro.tcc.sgo.service");
	}
}
