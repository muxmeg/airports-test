airportsApp.service('AirportsService', ["$http", function($http) {
    var restPath = "rest/airports/";
    var restPathQuery = restPath + "query";
    return {
        queryByCountryName: function (countryName) {
            return $http.get(restPathQuery + "?countryName=" + countryName);
        },

        queryByCountryCode: function (countryCode) {
            return $http.get(restPathQuery + "?countryCode=" + countryCode);
        },

        reloadAirports: function () {
            return $http.post(restPath + "/reload");
        }
}
}]);