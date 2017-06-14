var fs		= require('fs');
var zlib	= require('zlib');

function readZip(file, cb) {
	return fs.readFile(file, function(err, data) {
		if (err) return cb(err);
		zlib.gunzip(data, cb);
	});
}