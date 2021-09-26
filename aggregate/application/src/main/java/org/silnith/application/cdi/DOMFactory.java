package org.silnith.application.cdi;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import org.w3c.dom.bootstrap.DOMImplementationRegistry;


/**
 * A factory for CDI beans related to package {@code org.w3c.dom}.
 */
@Dependent
public class DOMFactory {

    /**
     * Returns the default DOM implementation registry.
     *
     * @return the default DOM implementation registry
     * @throws ClassNotFoundException if no registry could be found on the classpath
     * @throws InstantiationException if there was a problem instantiating the
     *     registry
     * @throws IllegalAccessException if there was a problem instantiating the
     *     registry
     * @throws ClassCastException if there was a problem instantiating the registry
     */
    @Produces
    @Dependent
    public DOMImplementationRegistry getDomImplementationRegistry()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
        return DOMImplementationRegistry.newInstance();
    }

}
