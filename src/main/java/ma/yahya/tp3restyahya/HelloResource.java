package ma.yahya.tp3restyahya;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Path("personnes/{nom}")
    @Produces("text/plain")
    public String hello(@PathParam("nom") String nom) {return "Hello, " + nom;
    }
}