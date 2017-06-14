// V1
var trustedFields = [
    'dev-comments',
    'description'
];
/**
 * Pour le trust HTML:
 * 1 - importer dans HTML : https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular-sanitize.js
 * 2 - ajouter le module 'ngSanitize' dans le tableau du 2e argument de angular.module
 * 3 - modifier le 2e argument de app.controller : la function devient un tableau : ['$scope', '$sce', function ($scope, $sce) {...}]
 * 4 - Encapsuler chaque valeur contenant du code HTML par $sce.trustAsHtml("<html>")
 * 5 - Utiliser ng-bind-html sur le div dont on veut générer le contenu HTML : <div ng-bind-html="defect['dev-comments']"></div>
 * @param objects le ou les objets sur lesquels on va appliquer (écrase) un $sce.trustAsHtml() pour chaque champs définis dans trustedFields
 * @param $sce
 * @returns {*}
 */
function trustHtml(objects, $sce) {
    var singleMode = !(objects instanceof Array);
    if (singleMode) objects = [objects];

    objects.forEach(function(object) {
        trustedFields.forEach(function(field) {
            object[field] = $sce.trustAsHtml(object[field]);
        });
    });

    return singleMode ? objects[0] : objects;
}

// V2
/**
 * Filtre pour accepter les URL (plus simple que de créer une fonction).
 * Utilisation dans un attribut : <source ng-src="{{song.audio | trustUrl}}" type="audio/mp3" />
 * @author http://stackoverflow.com/a/31313621/1655155
 */
app.filter("trustUrl", ['$sce', function ($sce) {
	return function (url) {
		return $sce.trustAsResourceUrl(url);
	};
}]);

// Prise en compte de la modification d'une variable
$scope.$apply(() => {
  // ...
  $scope.maVar = value;
  // ...
});

// HTTP GET
$http.get("/url").then(res => {
    if (res.status != 200) return console.error("Error GET $http != 200");

    // ...
}, resKO => {    
    console.error("Error GET $http : "+resKO.data);
});

// Debug avec $http (src : http://stackoverflow.com/a/34779338)
var $http = angular.injector(["ng"]).get("$http");