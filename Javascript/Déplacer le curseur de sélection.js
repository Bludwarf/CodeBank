/**
 * Ce script ne marche que sous IE
 * sources :
 * - http://forum.hardware.fr/hfr/Programmation/HTML-CSS-Javascript/decaler-curseur-input-sujet_94510_1.htm
 * - http://javascript.nwbox.com/cursor_position/
 */

function getSelectionStart(o) {
	if (o.createTextRange) {
		var r = document.selection.createRange().duplicate()
		r.moveEnd('character', o.value.length)
		if (r.text == '') return o.value.length
		return o.value.lastIndexOf(r.text)
	} else return o.selectionStart
}

function moveSelection(position) {
	  var range = document.selection.createRange();
	  range.move('character', position);
	  range.select();
}