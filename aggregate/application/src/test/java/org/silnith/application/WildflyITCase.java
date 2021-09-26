package org.silnith.application;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class WildflyITCase {
    
    Client client;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        client = ClientBuilder.newClient();
    }

    @AfterEach
    void tearDown() throws Exception {
        client.close();
    }

    @Test
    void testWebConsole() {
        final WebTarget target = client.target("http://localhost:9990/console/index.html");
        final Invocation invocation = target.request(MediaType.TEXT_HTML_TYPE).buildGet();
        final Response response = invocation.invoke();
        final Response.StatusType statusInfo = response.getStatusInfo();
        assertEquals(Response.Status.Family.SUCCESSFUL, statusInfo.getFamily());
        assertEquals(Response.Status.OK, statusInfo.toEnum());
    }

}
