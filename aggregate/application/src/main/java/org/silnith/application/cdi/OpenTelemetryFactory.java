package org.silnith.application.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import io.opentelemetry.api.OpenTelemetry;


/**
 * A factory for CDI beans related to package {@code io.opentelemetry}.
 */
@Dependent
public class OpenTelemetryFactory {

    @Produces
    @ApplicationScoped
    public OpenTelemetry getOpenTelemetry() {
        return OpenTelemetry.noop();
    }

}
