package org.silnith.application;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.silnith.render.RenderException;
import org.silnith.render.Renderer;
import org.silnith.render.xml.XHTML;


/**
 * A controller for web stuff.
 */
@Path("web")
public class WebController {

    @Inject
    @XHTML
    private Renderer xhtmlRenderer;

    /**
     * Returns a response of type {@literal MediaType#TEXT_PLAIN}.
     * 
     * @return a {@literal MediaType#TEXT_PLAIN} response
     */
    @GET
    @Produces({ MediaType.TEXT_PLAIN, })
    public Response getTextIndex() {
        return Response.ok("Something.", MediaType.TEXT_PLAIN_TYPE).build();
    }

    /**
     * Returns a response of type {@literal MediaType#APPLICATION_XHTML_XML}.
     * 
     * @return an {@literal MediaType#APPLICATION_XHTML_XML} response
     */
    @GET
    @Produces({ MediaType.APPLICATION_XHTML_XML, })
    public Response getXML() throws RenderException {
        return Response.ok(xhtmlRenderer.renderAsDocument(null), MediaType.APPLICATION_XHTML_XML_TYPE).build();
    }

    /**
     * Returns the request body as a response of type {@literal MediaType#APPLICATION_XHTML_XML}.
     * 
     * @param body a {@code POST} body of type {@literal MediaType#APPLICATION_JSON}
     * @return an {@literal MediaType#APPLICATION_XHTML_XML} response
     */
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, })
    @Produces({ MediaType.APPLICATION_XHTML_XML, })
    public Response postXML(final Object body) throws RenderException {
        return Response.ok(xhtmlRenderer.renderAsDocument(body), MediaType.APPLICATION_XHTML_XML_TYPE).build();
    }

}
