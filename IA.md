# Utilisation de l’IA — TP3 Jakarta EE REST avec LangChain4j

Ce document retient les échanges les plus utiles de notre conversation pendant le TP3. Les prompts cités sont réels ; les réponses de l’IA sont résumées. J’ai créé le projet, appliqué les changements dans IntelliJ, lancé les tests et effectué les commits. L’IA a proposé plusieurs extraits Java, notamment l’interface du service et son instruction système : je ne les présente pas comme du code écrit seul.

## 1. Service LangChain4j et retour JSON

- **Problème rencontré :** intégrer le client Gemini du TP2 dans une ressource REST du TP3 et savoir ce qui venait réellement de l’énoncé.
- **Raison de l’utilisation de l’IA :** comprendre l’architecture et adapter le code déjà réalisé dans mon dépôt `tp2-yahya-web`.
- **Prompt utilisé :** Je souhaite vérifier que la solution proposée respecte l’énoncé du TP3 et réutiliser correctement le client Gemini du TP2 avec une interface GuideTouristique et une ressource REST.
- **Réponse de l’IA :** elle a précisé que l’interface `GuideTouristique` était demandée par le TP3, mais que l’extrait proposé n’était pas une citation du professeur. Elle a guidé l’adaptation de mon `LlmClient` du TP2, proposé l’interface et le texte de `@SystemMessage`, puis l’appel depuis la ressource par injection CDI (`@Inject`). `AiServices` crée l’implémentation du service LangChain4j. La ressource annonce `application/json` avec `@Produces` ; le modèle doit renvoyer un objet JSON valide avec `ville_ou_pays`, `endroits_a_visiter` et `prix_moyen_repas`. J’ai testé des réponses pour Nice et le Maroc.
- **Ce que j’ai appris — question à compléter :** quel est le rôle respectif de `@Inject`, de `AiServices`, de `@SystemMessage` et de `@Produces("application/json")` ? Pourquoi cette dernière annotation ne garantit-elle pas, à elle seule, un JSON valide ?

## 2. Gestion des versions Maven et utilisation du BOM LangChain4j (`langchain4j-bom`)

- **Problème rencontré :** ne pas savoir quelle version exacte spécifier pour de nouvelles dépendances Maven comme `langchain4j-easy-rag` et risquer des incohérences ou des conflits de versions entre les différents modules LangChain4j du projet, tel que décrit dans le [support du TP sur les dépendances LangChain4j](http://richard.grin.free.fr/emsi/casablanca-ia/tp/tp2-langchain4j/dependance.html).
- **Raison de l’utilisation de l’IA :** comprendre comment trouver la version adaptée par rapport au reste du projet et découvrir la bonne pratique de centralisation des versions.
- **Prompt utilisé :** Comment trouver la bonne version pour `langchain4j-easy-rag` sachant que mes autres dépendances LangChain4j utilisent déjà une version spécifique, et comment éviter d'avoir à spécifier la version pour chaque dépendance ?
- **Réponse de l’IA :** en s'appuyant sur les instructions de la page [dépendances LangChain4j](http://richard.grin.free.fr/emsi/casablanca-ia/tp/tp2-langchain4j/dependance.html), elle a expliqué qu'il faut utiliser la même version que les autres dépendances LangChain4j (ou la vérifier sur Maven Central). Elle a ensuite recommandé d'utiliser une section `<dependencyManagement>` avec l'artifact `langchain4j-bom` et une propriété personnalisée pour centraliser la version, ce qui permet d'omettre la balise `<version>` dans la section `<dependencies>`.
- **Ce que j’ai appris — questions à compléter :**
    1. Quel est le rôle exact d'un BOM (`Bill of Materials`) géré via `<dependencyManagement>` dans Maven, et pourquoi est-il particulièrement utile pour un framework modulaire comme LangChain4j ?
    2. Pourquoi est-il indispensable que tous les modules LangChain4j d'un même projet partagent rigoureusement le même numéro de version ?

## 3. Passage de paramètres dynamiques avec `@PathParam` et `@UserMessage`

- **Problème rencontré :** transmettre dynamiquement une valeur saisie dans l’URL de l’API REST (par exemple, le nom d'une ville) pour qu'elle soit intégrée dans le prompt envoyé au modèle Gemini via l'interface LangChain4j.
- **Raison de l’utilisation de l’IA :** comprendre comment lier les paramètres d'une ressource JAX-RS (`@PathParam`) aux variables dynamiques d'un service LangChain4j (`@UserMessage` et `@V`).
- **Prompt utilisé :** Comment récupérer un paramètre depuis l'URL de ma ressource REST et l'injecter proprement dans la méthode de mon interface LangChain4j pour personnaliser la requête du LLM ?
- **Réponse de l’IA :** elle a expliqué qu'il faut utiliser `@PathParam("lieu")` sur le paramètre de la méthode de la ressource REST pour capturer la valeur de l'URL (`@Path("/{lieu}")`). Ensuite, dans l'interface LangChain4j, il faut annoter la méthode avec `@UserMessage("Donne-moi un guide pour {{ville}}")` et utiliser l'annotation `@V("ville")` sur le paramètre Java correspondant pour lier la valeur de l'URL au template du prompt.
- **Ce que j’ai appris — question à compléter :** quel est le rôle combiné de `@PathParam`, de `@UserMessage` et de `@V` dans la chaîne de transmission des données ? Pourquoi est-il indispensable d’utiliser des variables de template (`{{ville}}`) plutôt que de concaténer directement des chaînes de caractères en Java pour construire le prompt ?
- 
## 4. Nombre de lieux demandé par le client

- **Problème rencontré :** permettre quatre endroits à visiter sans changer la valeur par défaut de deux.
- **Raison de l’utilisation de l’IA :** traduire l’option du TP en paramètre de requête et vérifier l’ordre des étapes.
- **Prompt utilisé :** « Je veux savoir si je dois terminer les tests REST avant de faire le bonus, et comment permettre à l’utilisateur de demander quatre lieux sans modifier la valeur par défaut. », puis « ok en pass ».
- **Réponse de l’IA :** elle est revenue à la section de test de l’énoncé, puis a proposé `@QueryParam("nb")` et `@DefaultValue("2")`. Le nombre est transmis au service LangChain4j. J’ai vérifié que `?nb=4` renvoyait quatre lieux pour Paris.
- **Ce que j’ai appris — question à compléter :** quelle différence y a-t-il entre `@PathParam("ville_ou_pays")` et `@QueryParam("nb")`, et quand s’applique la valeur par défaut ?

## 5. Page HTML locale et CORS

- **Problème rencontré :** faire appeler l’API par la page HTML optionnelle ouverte comme fichier local.
- **Raison de l’utilisation de l’IA :** adapter l’URL à mon application et comprendre les en-têtes de la réponse.
- **Prompt utilisé :** j’ai collé la partie « Je cherche à adapter l’URL de la requête dans la page HTML et à comprendre pourquoi une page locale a besoin d’une autorisation CORS pour lire la réponse de l’API. » de l’énoncé, notamment `Access-Control-Allow-Origin`.
- **Réponse de l’IA :** elle a indiqué comment remplacer le `context-path` et ajouter `/api/guide/lieu/` dans l’URL. Elle a repris l’approche `Response.ok(reponseJson)` et les en-têtes CORS/cache montrés par le professeur. La page locale a affiché les informations demandées pour le Maroc.
- **Ce que j’ai appris — question à compléter :** pourquoi le navigateur vérifie-t-il CORS pour cette page locale, et quel est le rôle de l’en-tête `Access-Control-Allow-Origin` ?