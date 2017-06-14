var TimeUtils = {};

/**
 * Usage 1 : hms(3666.50) -> '01:01:06.5'
 * Usage 2 : hms('01:01:06.5') -> 3666.50
 */
TimeUtils.hms = function(seconds) {
	if (typeof seconds == 'number') {
		var h = Math.floor(seconds / 3600);
		var m = Math.floor(seconds % 3600 / 60);
		var s = seconds % 60;
		return pad(h, 2) + ':' + pad(m, 2) + ':' + pad(s, 2);
	} else {
		var hms = seconds.split(':');
		hms.forEach(function(val, index) {
			hms[index] = parseInt(val);
		});
		return hms;
	}
}

/**
 * Exemple : 
 *   TimeUtil.sleep(500).then(() => {
 *     console.log('hey');
 *   });
 * @return {Promise}
 */
TimeUtils.sleep = function (time) {
  return new Promise((resolve) => setTimeout(resolve, time));
}

// Usage!
