	Repete
      .findOne(req.params.id)
      .populate('local')
      .populate('enregistrements', {
        sort: 'ordre'
      })

      // populate enregistrements.morceau : http://stackoverflow.com/a/26452990/1655155
      .then(function(repete) {
        return [
          repete,
          Morceau
            .find({
              id: _.pluck(repete.enregistrements, 'morceau') //_.pluck: Retrieves the value of a 'user' property from all elements in the post.comments collection.
            })
            .then(function(morceaux) {
              return morceaux;
            })
        ]
      })
      .spread(function(repete, morceaux) {
        morceaux = _.indexBy(morceaux, 'id');

        //_.indexBy: Creates an object composed of keys generated from the results of running each element of the collection through the given callback. The corresponding value of each key is the last element responsible for generating the key
        repete.enregistrements = _.map(repete.enregistrements, function(enregistrement) {
          enregistrement.morceau = morceaux[enregistrement.morceau];
          return enregistrement;
        });

        res.view({
          repete: repete
        });
      })

      .catch(function(err) {
        return res.serverError(err);
      });