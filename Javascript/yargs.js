var argv = require('yargs')
	.usage('Usage: node chapterdb.js <command> -i [inputMediaFile] --acodec')
	.command('ffmpeg', 'Affiche la liste des commandes ffmpeg pour découper un média par chapitre') // TODO : limiter le choix des commandes possible ?
	.demand(1)
	.example('node chapterdb.js ffmpeg -i "Edward.Scissorhands.X0..mp3" --acodec > "Edward.Scissorhands.X0..bat"', 'Pour créer un batch qui créera un fichier mp3 par chapitre')
	.example('node chapterdb.js ffmpeg -i "Edward.Scissorhands.X0..mp3" --acodec > "Edward.Scissorhands.X0..sh"',  'Pour créer un bash  qui créera un fichier mp3 par chapitre')
	.check(function(argv, options) {
		argv.outputMode = argv._[0]; // par défaut "ffmpeg"
		if (argv.outputMode != 'ffmpeg') return 'Mode "' + argv.outputMode + '" inconnu. Voir liste des commandes ci-dessus.';
		return true;
	})
	.option('i', {
		alias: 'input',
		demand: true,
		describe: 'Fichier contenant le média source (vidéo ou audio)',
		type: 'string'
	})
	.option('acodec', {
		describe: 'Mode audio pour ffmpeg (si input de type audio)',
		type: 'boolean'
	})
	.help('help').alias('h', 'help')
	.version(function() {
		return require('./package').version;
	})
	.argv;