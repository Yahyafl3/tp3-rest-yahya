package ma.yahya.tp3restyahya.resource;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import ma.yahya.tp3restyahya.llm.LlmClient;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.core.Response;

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
    public Response endroitsDeVisite(
            @PathParam("ville_ou_pays") String villeOuPays,
            @QueryParam("nb") @DefaultValue("2") int nb) {
        String reponseJson = llmClient.chat(
                "Lieu : " + villeOuPays + "\nNombre d'endroits à visiter : " + nb);

        Response.ResponseBuilder responseBuilder = Response.ok(reponseJson);
        responseBuilder.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        responseBuilder.header("Pragma", "no-cache");
        responseBuilder.header("Expires", "0");
        responseBuilder.header("Access-Control-Allow-Origin", "*");
        return responseBuilder.build();
    }
}