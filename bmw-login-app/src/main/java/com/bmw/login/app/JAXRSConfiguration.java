package com.bmw.login.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 *
 */
@ApplicationPath("app")
public class JAXRSConfiguration extends Application {
	
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("jersey.config.server.disableMoxyJson", true); 
        props.put("jersey.config.jsonFeature", "JacksonFeature"); 
        return props;
    }
    
    /*
     * En payara5!! Para registrar Jackson como el proveedor de JSON tenemos que pasarle desde ahí el JacksonFeature
     * */
    
    
}