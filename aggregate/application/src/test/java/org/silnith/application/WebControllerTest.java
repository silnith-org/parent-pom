package org.silnith.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.silnith.render.RenderException;
import org.silnith.render.Renderer;
import org.w3c.dom.Document;


@ExtendWith({ MockitoExtension.class, })
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class WebControllerTest {

    WebController controller;

    @Mock
    Renderer renderer;

    @BeforeEach
    void setUp() throws Exception {
        controller = new WebController();
        controller.setXhtmlRenderer(renderer);
    }

    @Test
    void testGetTextIndex() throws RenderException {
    }

    @Test
    void testGetXML() throws RenderException {
        final Document document = Mockito.mock(Document.class);
        Mockito.when(renderer.renderAsDocument(ArgumentMatchers.any())).thenReturn(document);

        final Response response = controller.getXML();

        final Response.StatusType statusInfo = response.getStatusInfo();
        assertEquals(Response.Status.Family.SUCCESSFUL, statusInfo.getFamily());
        assertEquals(Response.Status.OK, statusInfo.toEnum());
        assertSame(document, response.getEntity());

        Mockito.verify(renderer).renderAsDocument(ArgumentMatchers.isNull());
    }

    @Test
    void testPostXML() throws RenderException {
        final Document document = Mockito.mock(Document.class);
        Mockito.when(renderer.renderAsDocument(ArgumentMatchers.any())).thenReturn(document);

        final Object body = "Foo.";

        final Response response = controller.postXML(body);

        final Response.StatusType statusInfo = response.getStatusInfo();
        assertEquals(Response.Status.Family.SUCCESSFUL, statusInfo.getFamily());
        assertEquals(Response.Status.OK, statusInfo.toEnum());
        assertSame(document, response.getEntity());

        Mockito.verify(renderer).renderAsDocument(ArgumentMatchers.same(body));
    }

}
