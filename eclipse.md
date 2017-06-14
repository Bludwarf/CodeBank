# Préférences

## Sauvegarde des couleurs

Copier le fichier `.metadata\.plugins\org.eclipse.core.runtime\.settings\org.eclipse.ui.workbench.prefs`.

L'idéal est de faire un lien symbolique (jonction Windows) vers un dossier partagé `.settings` pour toutes les instances d'Eclipse.
En se plaçant dans le répertoire du workspace lancer le commande Windows suivante :

```batch
mklink /j .metadata\.plugins\org.eclipse.core.runtime\.settings D:\Presets\Eclipse\.settings
```

# Maven

## Plugin introuvable lors de l'import d'un projet Maven

Exemple d'erreur :

```
Sous-titre du menu : Discover and map Eclipse plugins to Maven plugin goal executions.
Description : No marketplace entries found to handle gmaven-plugin:1.5:execute in Eclipse.  Please see Help for more information.
```

[Solution](http://stackoverflow.com/a/13733232).