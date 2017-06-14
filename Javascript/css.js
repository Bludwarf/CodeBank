/**
 * Importe une feuille de style CSS dans le document.
 * Attention : pour les fichiers RAW de Github utiliser les URL en rawgit. Cf : https://rawgit.com/ (remplacement automatisé dans cette fonction)
 * @param href lien vers la feuille de style
 * @author http://www.abeautifulsite.net/manipulating-stylesheets-with-greasemonkey/
 */
function importCSS(href) {
	// URL en RAW github
	var rawGitRx = /\/\/raw\.githubusercontent\.com\//;
	if (href.match(rawGitRx)) {
		href = href.replace(rawGitRx, "//cdn.rawgit.com/");  // on utilise rawgit pour avoir le bon Content-Type
		console.log('On passe par Raw Git pour charger la feuille de style => %s', href);
	}

	var link = window.document.createElement('link');
	link.rel = 'stylesheet';
	link.type = 'text/css';
	link.href = href;
	document.getElementsByTagName("HEAD")[0].appendChild(link);
}