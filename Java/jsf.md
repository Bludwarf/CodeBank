# Contexte

Acc�der � un fichier dans le dossier `tomcat/webapps/${artifactId}/` :

```java
final PortletContext servletContext = (PortletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
final InputStream jsonStream = servletContext.getResourceAsStream("/WEB-INF/classes/my.json");
```

Le dossier `/WEB-INF/classes` contient le contenu de tous les dossiers sources du classpath Java (src/main/java, src/main/resource, ...).

# Debug

Tr�s bon article de forum : [lien](http://stackoverflow.com/a/2120183).

Ajouter dans chaque page xhtml (comment faire pour les dialog/composite impl) la balise h:messages :

```
<h:messages id="messages" layout="table" infoClass="info" errorClass="error" />
```

# Annotations

## @ViewScoped

**Attention** � bien utiliser `javax.faces.bean.ViewScoped` et pas `javax.faces.view.ViewScoped`.

## @ManagedProperty

3 contraintes � respecter :

  - Le champ doit comporter un getter et un setter ;
  - La classe r�f�renc�e doit comporter un constructeur par d�faut `public` ;
  - Le champ doit �tre de pr�f�rence `transient`.
	
# Commentaires

Les commentaires HTML ne sont pas ignor�s en JSF. Deux solutions existent ([source](https://www.mkyong.com/jsf2/how-to-use-comments-in-jsf-2-0/)) : 

## Solution 1 : facelets.SKIP_COMMENTS

web.xml :

```
<context-param>
    <param-name>facelets.SKIP_COMMENTS</param-name>
    <param-value>true</param-value>
</context-param>
```

## Solution 2 : ui:remove

Tout est ignor� sous cette balise :

```
<ui:remove>
	<!-- Tous ses fils sont ignor�s (comme de vrais commentaires) -->
	<h:commandButton type="button" value="#{msg.buttonLabel}" />
</ui:remove>
```