package org.acme.quarkus.sample;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/apiv1")
@RegisterRestClient
public interface RdevClient {

    // Sans internet, sur localhost:
    // @Path("/talks.json")
    
    // Avec internet
    @Path("/talks")
    @GET
    @Produces("application/json")
    List<TalkTransfer> talks();
}

