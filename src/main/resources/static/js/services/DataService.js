airportsApp.service('DataService', ["$http", function($http) {
    var restPath = "rest/data";
    return {
        reloadRunways: function () {
            return $http.post(restPath + "/reloadRunways");
        },
        reloadAirports: function () {
            return $http.post(restPath + "/reloadAirports");
        },
        reloadAll: function () {
            return $http.post(restPath + "/reloadCountries");
        }
    }
}]);