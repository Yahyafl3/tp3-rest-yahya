package ma.yahya.tp3restyahya.resource;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
@Path("/guide")
public class GuideTouristiqueResource {
    /**
     * Retourne une réponse pour la ville ou le pays demandé.
     *
     * @param villeOuPays nom de la ville ou du pays
     * @return la réponse pour ce lieu
     */
    @GET
    @Path("lieu/{ville_ou_pays}")
    @Produces("application/json")
    public String[] endroitsDeVisite(
            @PathParam("ville_ou_pays") String villeOuPays) {
        return new String[] { villeOuPays };
    }
}