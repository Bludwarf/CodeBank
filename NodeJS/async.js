async.auto({

  photos: function(cb) {
	Photo.query('SELECT * FROM girl_photos WHERE girl = ' + req.params.girlId+ ' ORDER BY jizzes DESC, duree DESC, updatedAt DESC', cb);
  },

  girl: function(cb) {
	Girl.findOne({id: req.params.girlId})
	  .populate('sets')
	  .exec(cb);
  },
  
  // attend le résultat de photos et de girl
  addedPhoto: ['photos', 'girl', function(results, cb) {
    var photo = results.photos[0];
    var girl = results.girl;
    photo.girls.add(girl);
    photo.save(cb);
  }]

}, function(e, results) {
  if (e) return res.serverError(e);
  var girl = results.girl;
  return res.view({
	//title: 'Photos de #' + req.params.girlId,
	title: 'Photos de '+girl.name,
	params: req.params,
	photos: results.photos,
	girl: girl,
	sets: girl.sets
  });
});