/**
 * NodeJS 4 (cloud9) : https://nodejs.org/dist/latest-v4.x/docs/api/fs.html
 * NodeJS 7 : https://nodejs.org/api/documentation.html
 */

var EOL = require('os').EOL; // src : http://stackoverflow.com/a/14063413/1655155

/** mkdirs pour créer un dossier automatiquement et éviter l'erreur : Error: ENOENT, open '...' */
var mkdirp = require('mkdirp');

/** Dossier du script dans lequel on code : https://nodejs.org/docs/latest/api/globals.html#globals_dirname */
var __dirname;

/**
 * Lecture d'un fichier JSON
 * @param cb : https://nodejs.org/api/fs.html#fs_fs_readfile_file_options_callback
 */
function readJson(file, cb) {
	return fs.readFile(file, function(err, data) {
		if (err) return cb(err);
		var json = JSON.parse(data);
		return cb(null, json);
	});
}

// Lecture d'un fichier => string au lieu de Buffer grâce à 'utf-8'
fs.readFile(filename, 'utf-8', (err, content) => {
    if (err) return cb(err);
    // content {String}
})

// Fichier existant ?
fs.access('/etc/passwd', fs.constants.R_OK | fs.constants.W_OK, (err) => {
  console.log(err ? 'no access!' : 'can read/write');
});