Installation
============

	npm install socket.io --save

C�t� serveur
============

Remplacer

	app.listen(port);
	
par

	var io = require('socket.io').listen(app.listen(port));
	
Pour �couter chaque connexion :

	io.sockets.on('connection', function (socket) {
		socket.emit('message', { message: 'welcome to the chat' });
		socket.on('message', function (data) {
			io.sockets.emit('message', data);
		});
	});
	
	
C�t� client
===========

Ajouter dans le <head> du layout

	script(src='/socket.io/socket.io.js')
	
Pour �couter les �v�nements du serveur et lui parler

	
    var socket = io.connect('http://localhost:3000');
 
    socket.on('message', function (data) {
        if(data) {
            ...
        } else {
            console.log("There is a problem:", data);
        }
    });
	
Pour envoyer vers le serveur

	socket.emit('message', {message: 'message client'});