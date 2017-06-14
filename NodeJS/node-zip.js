var zlib	= require('zlib');
require('node-zip'); // JSZip

// Pour un ZIP dans un Autre ZIP au format GunZIP
zlib.gunzip(zip.files[gzipFilename].asNodeBuffer(), function(err, xml) {
	console.log("test : " + xml);
});