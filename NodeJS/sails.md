Model
=====

M�thodes disponibles
--------------------

[lien][http://dennisrongo.com/introduction-to-sails-js-orm-queries-part-2/]

  - findOneBy...(value)
    - **findOneBy**Attribute
    - **findOneBy**Attribute**In**
    - **findOneBy**Attribute**Like**
  - findBy
    - **findBy**Attribute
    - **findBy**Attribute**In**
    - **findBy**Attribute**Like**
  - countBy
    - **countBy**Attribute
    - **countBy**Attribute**In**
    - **countBy**Attribute**Like**
  - attribute
    - attribute**StartsWith**
    - attribute**Contains**
    - attribute**EndsWith**
  - count
  - join
  - createEachAttribute
  - findOrCreateEachAttribute
  - findOrCreateAttribute
  - findOneAttribute
  - findAttribute
  - whereAttribute
  - selectAttribute
  - findAllAttribute
  - findOneLikeAttribute
  - findLikeAttribute
  - startsWithAttribute
  - endsWithAttribute
  - containsAttribute
  - stream
  - watch
  - unwatch

Lancement dans WebStorm
=======================

Working Dir : D:\projets\Autres\roadie
JS file : C:\Users\mlavigne\AppData\Roaming\npm\node_modules\sails\bin\sails.js
Params  : lift
		
G�n�ration d'une API
===============

	sails generate api <Model>
	
Config
======

Cr�er un fichier de propri�t�s perso.js ("perso" exemple) de la forme :

```javascript
module.exports.perso = {
	// *: *
}
```

Utilisation dans une view EJS (simple) :

```javascript
sails.config.perso.*
```

Installation d'un g�n�rateur
============================

Installation
------------

	npm install -g sails-generate-x
	
Exemple avec Bower :

	npm install sails-generate-gulp-bower
	sails generate gulp-bower
	
.sailsrc
--------

Modifier le fichier `.sailsrc` du projet en ajoutant le nom du module complet.

	{
		"generators": {
			"modules": {
				"x": "sails-generate-x"
			}
		}
	}
	
Utilisation
-----------

	sails generate x
	
Reco de code
============

Gestion des erreurs Express
---------------------------

	if (e) {
	  console.trace(e);
	  return res.status(500).send(e);
	}
	

	
Bower
=====================

Auteur : [http://stackoverflow.com/a/22456574/1655155], doc : [grunt-bower-task]

	npm install --save grunt-bower-task

ajouter une ligne dans **tasks\register\compileAssets.js** :

	grunt.registerTask('compileAssets', [
		'clean:dev',
		'bower:dev',
		'jst:dev',
		'less:dev',
		'copy:dev',
		'coffee:dev'
	]);
	
cr�er tasks/config/bower.js :

	module.exports = function(grunt) {
	  grunt.config.set('bower', {
		dev: {
		  dest: '.tmp/public',
		  js_dest: '.tmp/public/js',
		  css_dest: '.tmp/public/css',

		  // Ajout du fran�ais pour moment.js
		  options: {
			packageSpecific: {
			  moment: {
				files: [
				  "moment.js",
				  "locale/fr.js"
				]
			  }
			}
		  }
		}
	  });

	  grunt.loadNpmTasks('grunt-bower-task');

	};


	
Supprimer toutes biblioth�ques de js/dependencies qui se trouvent d�j� dans bower_components.

Modifier pipeline.js si un script doit �tre charg� avant les autres (relatif au dossier .tmp/public).
Exemple (projet roadie) :

	/* ... */
	
	

	/* ... */

	// Client-side javascript files to inject in order
	// (uses Grunt-style wildcard/glob/splat expressions)
	var jsFilesToInject = [

	  // Load sails.io before everything else
	  'js/dependencies/sails.io.js',

	  // Dependencies like jQuery, or Angular are brought in here
	  // ne pas mettre jQuery dedans quand on utilise Bower
	  'js/dependencies/**/*.js',

	  // D�pendance pour d'autres plugins
	  'js/dist/jquery.js',
	  'js/moment.js',
	  'js/dist/js/bootstrap.js',

	  // All of the rest of your client-side js files
	  // will be injected here in no particular order.
	  'js/**/*.js'
	];
	
	/* ... */
	
Surveiller l'ordre dans le fichier layout.ejs et modifier en utilisant le dossier .tmp/public.




Cr�ation d'une table de relation
--------------------------------

Entre Girl et Photo :
	Girl.photos -> Photo
	Photo.girls -> Girl
	
	alors cr�er la table girl_photos__photo_girls avec les colonnes :
		girl_photos = la girl
		photo_girls = la photo
		
Erreurs
=======

Recommandations
---------------

Ne pas utiliser de mod�le nomm� :
	- File
	- Blob

Error (E_UNKNOWN) :: Encountered an unexpected error: ER_BAD_FIELD_ERROR: Unknown column 'NaN' in 'where clause'
----------------------------------------------------------------------------------------------------------------

Se produit � cause d'un populate qui plante car on recherche un id vide dans la table cible de la relation.

Tout d'abord supprimer tous les param�tres envoy�s � la vue => faire juste un :

	return res.view(/* sans param�tre */);
	
Si �a marche alors le probl�me vient d'un champs exploit� dans la vue.

Pour voir quel champs plante customizer la lib :
Ajouter la ligne suivante dans : sails-mysql\node_modules\mysql\lib\protocol\Protocol.js:Protocol.prototype._enqueue:sequence.on('error'...

	if (sequence.sql) console.error('Requ�te SQL KO : '+sequence.sql);
	
Erreur : objet vide dans le 2e argument de model.save(e, updated)
-----------------------------------------------------------------

C'est un changement voulu dans les nouvelles version de sails. � voir [ici][http://sailsjs.com/version-notes/0point12-migration-guide#?save-no-longer-provides-a-second-argument-to-its-callback]

remplacer chaque appel � save :

	object.save(function (err, modifiedObject){
      if (err) { /* ... */  return; }

      // ... use modifiedObject
    });

par

	object.save(function (err){
      if (err) { /* ... */  return; }

      // ... use object
    });
	
	
Erreur de connexion avec les socket
-----------------------------------

Si l'objet `io.socket` n'a pas �t� initialis�, se connecter manuellement � la socket sails avec `io.sails.connect()`.

[grunt-bower-task]: https://github.com/yatskevich/grunt-bower-task