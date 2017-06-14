/**
 * @returns true si l'élément a été supprimé
 */
function remove(arr, element) {
	var i = arr.indexOf(element);
	if (i != -1) {
		arr.splice(i, 1);
		return true;
	}
	return false;
}

/**
 * @returns true si au moins un élément a été supprimé
 */
function removeAll(arr, elements) {
	var modified = false;
	elements.forEach(function(element) {
        if (remove(arr, element)) {
			modified = true;
		}
    });
}