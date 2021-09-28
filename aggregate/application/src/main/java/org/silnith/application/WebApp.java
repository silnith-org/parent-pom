package org.silnith.application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


/**
 * The identifier that this is a JAX-RS application.
 */
@ApplicationPath("application")
public class WebApp extends Application {

    public static final String APPLICATION_PATH = "application";

}
