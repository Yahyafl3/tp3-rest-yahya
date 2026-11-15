package ma.yahya.tp3restyahya.resource;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import ma.yahya.tp3restyahya.llm.LlmClient;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DefaultValue;

@Path("/guide")
public class GuideTouristiqueResource {
    @Inject
    private LlmClient llmClient;
    /**
     * Retourne une réponse pour la ville ou le pays demandé.
     *
     * @param villeOuPays nom de la ville ou du pays
     * @param nb nombre d'endroits à visiter, 2 par défaut
     * @return la réponse pour ce lieu
     */
    @GET
    @Path("lieu/{ville_ou_pays}")
    @Produces("application/json")
    public String endroitsDeVisite(
            @PathParam("ville_ou_pays") String villeOuPays,
            @QueryParam("nb") @DefaultValue("2") int nb) {
        return llmClient.chat(
                "Lieu : " + villeOuPays + "\nNombre d'endroits à visiter : " + nb);
    }
}