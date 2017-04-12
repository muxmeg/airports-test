airportsApp.service('ReportsService', ["$http", function($http) {
    var restPath = "rest/reports";
    return {
        findCountriesByAirportsCount: function () {
            return $http.get(restPath + "/topCountriesByAirports");
        },

        findRunwaysPerCountry: function () {
            return $http.get(restPath + "/runwayTypesPerCountry");
        },

        findCommonRunwayIdents: function () {
            return $http.get(restPath + "/commonLeIdent");
        }
    }
}]);