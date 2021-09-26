package org.silnith.cdi.opentelemetry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;


/**
 * A factory for CDI beans related to package {@code io.opentelemetry}.
 */
@Dependent
public class TracerFactory {

    /**
     * Returns a {@link Tracer} specific to this library.
     * 
     * @param openTelemetry the Open Telemetry root object
     * @return a tracer
     */
    @Produces
    @ApplicationScoped
    @OpenTelemetryCDI
    public Tracer getTracer(final OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("opentelemetry-cdi-interceptor", "0.0.1-SNAPSHOT");
    }

}
