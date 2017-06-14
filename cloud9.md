mySQL
=====

Démarrage
---------

	mysql-ctl start
	
PhpMyAdmin
----------

Installation :

	phpmyadmin-ctl install
	
Démarrer ensuite mySQL.

Se connecter à https://[workspacename]-[username].c9users.io/phpmyadmin.

Arrêt de PhpMyAdmin
-------------------

Couper le serveur Apache (pour pouvoir lancer l'appli par exemple) :

	sudo service apache2 stop

Connexion
---------

  - host : The same local IP as the application you run on Cloud9
  - user : bludwarf
  - password : "" (No password since you can only access the DB from within the workspace)
  - database : c9
  
Exécution de script sql
-----------------------

	mysql -u bludwarf -p c9 < api/models/sql/views.sql

Changer le port d'Apache (défaut 8080)
------------------------

	sudo nano /etc/apache2/ports.conf
	
RPM
===

Passer plutôt par apt-get (src : http://stackoverflow.com/a/35880751/1655155). Exemple avec ImageMagick :

	sudo apt-get update
	sudo apt-get install imagemagick
