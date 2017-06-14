var express = require("express");
var morgan = require('morgan'); // Charge le middleware de logging
var favicon = require('serve-favicon'); // Charge le middleware de favicon
var session = require('cookie-session'); // Charge le middleware de sessions
var bodyParser = require('body-parser'); // Charge le middleware de gestion des paramètres
var urlencodedParser = bodyParser.urlencoded({ extended: false });

var app = express()
    .use(morgan('combined')) // Active le middleware de logging
    .use(express.static(__dirname + '/public')) // Indique que le dossier /public contient des fichiers statiques (middleware chargé de base)
    .use('/socket.io/socket.io.js', express.static(__dirname+'/node_modules/socket.io/node_modules/socket.io-client/dist/socket.io.min.js')) // pour utiliser socket.io sans copier le js dans un dossier public
    .use(bodyParser.json()) // for parsing application/json
    .use(bodyParser.urlencoded({ extended: true })) // for parsing application/x-www-form-urlencoded
    .use(favicon(__dirname + '/public/favicon.png')) // Active la favicon indiquée
    
    // EJS pour les views (res.render)
    .set('view engine', 'ejs')
    
    /*Configure the multer.*/ // http://codeforgeek.com/2014/11/file-uploads-using-node-js/
    .use(multer({
        dest: './uploads/',
        rename: function (fieldname, filename) {
            return filename+Date.now();
        },
        onFileUploadStart: function (file) {
            console.log(file.originalname + ' is starting ...');
        },
        onFileUploadComplete: function (file) {
            console.log(file.fieldname + ' uploaded to  ' + file.path);
            done = true;
        }
    }))
    
    .get('/planning/:projectionniste?', function(req, res) {
        var projectionniste = req.params.projectionniste;
        var date = new Date(req.query.date); // par défaut new Date()
    })
     
    .post('/planning', function(req, res) {
        res.send(500, 'Not implemented');
    })
    
    .use(function(req, res) {
        res.send(404, 'Page inconnue');
    })
    
    .listen(process.env.PORT, process.env.IP, function(err) {
        console.log("Appli démarrée : " + new Date());
    });