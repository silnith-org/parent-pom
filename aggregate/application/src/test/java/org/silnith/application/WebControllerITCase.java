package org.silnith.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class WebControllerITCase {
    
    static String host;
    static String name;
    static int port;
    
    Client client;
    WebTarget serviceTarget;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        final Properties properties = new Properties();
        properties.load(WebControllerITCase.class.getResourceAsStream("port.properties"));
        host = "localhost";
        name = properties.getProperty("servlet.name");
        port = Integer.parseInt(properties.getProperty("servlet.port"));
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        client = ClientBuilder.newClient();
        final String uri = String.format(Locale.ENGLISH,
                "http://%s:%d/%s/%s",
                host, port, name, WebApp.APPLICATION_PATH);
        serviceTarget = client.target(uri);
    }

    @AfterEach
    void tearDown() throws Exception {
        client.close();
    }

    @Test
    void testGetTextIndex() {
        final WebTarget target = serviceTarget.path("web");
        final Invocation invocation = target.request(MediaType.TEXT_PLAIN_TYPE).buildGet();
        final Response response = invocation.invoke();
        final Response.StatusType statusInfo = response.getStatusInfo();
        assertEquals(Response.Status.Family.SUCCESSFUL, statusInfo.getFamily());
        assertEquals(Response.Status.OK, statusInfo.toEnum());
        assertTrue(response.getMediaType().isCompatible(MediaType.TEXT_PLAIN_TYPE));
        assertEquals("Something.", response.readEntity(String.class));
    }

    @Test
    void testGetXML() {
        final WebTarget target = serviceTarget.path("web");
        final Invocation invocation = target.request(MediaType.APPLICATION_XHTML_XML_TYPE).buildGet();
        final Response response = invocation.invoke();
        final Response.StatusType statusInfo = response.getStatusInfo();
        assertEquals(Response.Status.Family.SUCCESSFUL, statusInfo.getFamily());
        assertEquals(Response.Status.OK, statusInfo.toEnum());
        assertTrue(response.getMediaType().isCompatible(MediaType.APPLICATION_XHTML_XML_TYPE));
        assertFalse(response.readEntity(String.class).isBlank());
    }

    @Test
    void testPostXML() {
        final WebTarget target = serviceTarget.path("web");
        final Entity<String> json = Entity.json("{\"name\":\"Soybeans\",\"type\":{\"genus\":\"foo\",\"species\":\"bar\"},\"counts\":[1,2,3,4,5]}");
        final Invocation invocation = target.request(MediaType.APPLICATION_XHTML_XML_TYPE).buildPost(json);
        final Response response = invocation.invoke();
        final Response.StatusType statusInfo = response.getStatusInfo();
        assertEquals(Response.Status.Family.SUCCESSFUL, statusInfo.getFamily());
        assertEquals(Response.Status.OK, statusInfo.toEnum());
        assertTrue(response.getMediaType().isCompatible(MediaType.APPLICATION_XHTML_XML_TYPE));
        assertFalse(response.readEntity(String.class).isBlank());
    }

}
