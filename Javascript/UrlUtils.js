/**
 * @returns la liste des param�tres dans l'URL (URL d�cod�)
 */
function getParams(url) {
    var result = {};

    var search;
    if (url) {
        var i = url.indexOf('?');
        if (i === -1) return result;
        search = url.substring(i);
    }
    else {
        search = window.location.search;
    }

    if (search) {
        search.substr(1) // apr�s '?'
            .split("&")
            .forEach(function (item) {
                var tmp = item.split("=");
                var key = decodeURIComponent(tmp[0]);
                var val = decodeURIComponent(tmp[1]);
                result[key] = val;
            });
    }

    return result;
}

/**
 * Alternative � getParams(url) sans parsing complet des param�tres
 * @author http://stackoverflow.com/a/901144
 */
function getParameterByName(name, url) {
    if (!url) {
      url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

/** @author http://stackoverflow.com/a/111545/1655155 */
function encodeQueryData(data) {
   var ret = [];
   for (var d in data)
      ret.push(encodeURIComponent(d) + "=" + encodeURIComponent(data[d]));
   return ret.join("&");
}