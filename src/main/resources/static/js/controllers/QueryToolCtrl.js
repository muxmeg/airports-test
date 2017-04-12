airportsApp.controller("QueryToolCtrl", ["$scope", "AirportsService", "CountriesService", "RunwaysService", "DataService",
    "ReportsService",
    function ($scope, AirportsService, CountriesService, RunwaysService, DataService, ReportsService) {
        $scope.query = {type: "countryName", inProgress: false};
        $scope.reload = {inProgress: false};
        $scope.report = {inProgress: false};
        $scope.currentPage;
        $scope.numPerPage = 20;

        var that = this;

        $scope.submitQuery = function () {
            var promise;
            if ($scope.query.value) {
                switch ($scope.query.type) {
                    case "countryName":
                        promise = that.executeQuery(AirportsService.queryByCountryName);
                        break;
                    case "countryCode":
                        promise = that.executeQuery(AirportsService.queryByCountryCode);
                        break;
                }
            }

            promise.then(function (result) {
                $scope.currentPage = 1;
                that.queryResult = result.data;
            }, function (error) {
                console.log("Query error!", error);
            }).finally(function () {
                $scope.query.inProgress = false;
            });
        };

        that.executeQuery = function (method) {
            $scope.query.inProgress = true;
            that.queryResult = null;
            return method($scope.query.value);
        };

        $scope.$watch("currentPage", function() {
            var begin = (($scope.currentPage - 1) * $scope.numPerPage)
                , end = begin + $scope.numPerPage;

            $scope.filteredQueryResult = that.queryResult.slice(begin, end);
        });

        that.executeReport = function (method, resultProcessor) {
            $scope.report.inProgress = true;
            $scope.reportResult = null;
            method().then(function (result) {
                $scope.reportResult = resultProcessor(result.data);
            }, function (error) {
                console.log("Report error!", error);
            }).finally(function () {
                $scope.report.inProgress = false;
            });
        };

        $scope.findCountriesByAirportsCount = function () {
            that.executeReport(ReportsService.findCountriesByAirportsCount, function(data){
                var result = [];
                result.push({label: "Highest amount of airports: ", data: data.highest});
                result.push({label: "Lowest amount of airports: ", data: data.lowest})
                return result;
            });
        };

        $scope.findRunwaysPerCountry = function () {
            that.executeReport(ReportsService.findRunwaysPerCountry, function(data){
                var result = [];
                for (var country in data) {
                    result.push({country: country, data: data[country]});
                }
                return result;
            });
        };

        $scope.findCommonRunwayIdents = function () {
            that.executeReport(ReportsService.findCommonRunwayIdents, function (data) {
                return data;
            });
        };

        $scope.reloadAll = function () {
            that.executeReload(DataService.reloadAll);
        };

        $scope.reloadAirports = function () {
            that.executeReload(DataService.reloadAirports);
        };

        $scope.reloadRunways = function () {
            that.executeReload(DataService.reloadRunways);
        };

        that.executeReload = function (method) {
            $scope.reload.inProgress = true;
            method().then(function () {
            }, function (error) {
                console.log("Error during data reload!", error);
            }).finally(function () {
                $scope.reload.inProgress = false;
            });
        }
    }]);