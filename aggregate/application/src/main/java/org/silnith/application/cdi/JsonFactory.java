package org.silnith.application.cdi;

import java.util.Collections;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.json.Json;
import javax.json.JsonReaderFactory;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;


/**
 * A factory for CDI beans related to package {@code javax.json}.
 */
@Dependent
public class JsonFactory {

    @Produces
    @ApplicationScoped
    public Jsonb getJsonB() {
        return JsonbBuilder.create();
    }

    @Produces
    @ApplicationScoped
    public JsonReaderFactory getJsonReaderFactory() {
        final Map<String, Object> config = Collections.emptyMap();
        return Json.createReaderFactory(config);
    }

}
