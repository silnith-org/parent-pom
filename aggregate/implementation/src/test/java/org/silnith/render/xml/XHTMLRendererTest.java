package org.silnith.render.xml;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.json.Json;
import javax.json.JsonReaderFactory;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.silnith.render.RenderException;
import org.silnith.render.Renderer;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;


class XHTMLRendererTest {

    Jsonb jsonb;

    Renderer renderer;

    @BeforeEach
    void setUp() throws Exception {
        final DOMImplementationRegistry domImplementationRegistry = DOMImplementationRegistry.newInstance();
        jsonb = JsonbBuilder.create();
        final JsonReaderFactory jsonReaderFactory = Json.createReaderFactory(null);
        renderer = new XHTMLRenderer(domImplementationRegistry, jsonb, jsonReaderFactory);
    }

    @AfterEach
    void tearDown() throws Exception {
        jsonb.close();
    }

    @Test
    void testRenderAsDocument() throws RenderException {
        final Document document = renderer.renderAsDocument(null);

        assertNotNull(document);
    }

}
