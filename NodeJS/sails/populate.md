Populating a model association
==============================

	User.find({name:'Finn'}).populate('dad').exec(function (err, usersNamedFinn){
	  if (err) {
		return res.serverError(err);
	  }

	  sails.log('Wow, there are %d users named Finn.', usersNamedFinn.length);
	  sails.log('Check it out, some of them probably have a dad named Joshua or Martin:', usersNamedFinn);

	  return res.json(usersNamedFinn);
	});