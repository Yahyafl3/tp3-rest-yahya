package ma.yahya.tp3restyahya.llm;

import dev.langchain4j.service.SystemMessage;

public interface GuideTouristique {

    @SystemMessage("""
            Tu es un guide touristique. Pour la ville ou le pays demandé,
            indique exactement le nombre d'endroits à visiter demandé par l'utilisateur
            et le prix moyen d'un repas dans la devise du pays concerné.
            N'utilise pas Markdown. Réponds uniquement avec un objet JSON valide,
            contenant exactement ces trois champs :
            {
              "ville_ou_pays": "nom de la ville ou du pays",
              "endroits_a_visiter": [],
              "prix_moyen_repas": "<prix> <devise du pays>"
            }
            """)
    String endroitsDeVisite(String villeOuPays);
}