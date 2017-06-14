/**
 * @returns true si l'�l�ment a �t� supprim�
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
 * @returns true si au moins un �l�ment a �t� supprim�
 */
function removeAll(arr, elements) {
	var modified = false;
	elements.forEach(function(element) {
        if (remove(arr, element)) {
			modified = true;
		}
    });
}