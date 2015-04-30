angular.module('demo.controllers', []).
    controller('indexController', function($scope, planetsApiService) {
        $scope.nameFilter = null;
        $scope.planetsList = [];

        planetsApiService.getPlanets().success(function (response) {
            $scope.edit = true;
            $scope.planetsList = response;
        });

        function clearForm() {
            $scope.pk = '';
            $scope.name = '';
            $scope.distanceFromEarth = '';
            $scope.discoveredBy = '';
            $scope.diameter = '';
            $scope.atmosphere = '';
            $scope.description = '';

            $scope.planetForm.$setPristine();
        }

        $scope.editPlanet = function(id) {
            if (id == 'new') {
                $scope.edit = true;
                clearForm();
            } else {
                $scope.edit = false;

                planetsApiService.getPlanetDetails(id).success(function (response) {
                    $scope.planet = response;

                    $scope.pk = response.pk;
                    $scope.name = response.name;
                    $scope.distanceFromEarth = response.distanceFromEarth;
                    $scope.discoveredBy = response.discoveredBy;
                    $scope.diameter = response.diameter;
                    $scope.atmosphere = response.atmosphere;
                    $scope.description = response.description;
                });
            }
        };

        $scope.deletePlanet = function(id) {
            planetsApiService.deletePlanet(id).success(function() {
                planetsApiService.getPlanets().success(function (response) {
                    $scope.planetsList = response;
                    clearForm();
                    $scope.edit = true;
                });
            });
        };

        $scope.persistPlanet = function() {

            if ($scope.planetForm.$valid) {
                var formData = {
                    "pk" : $scope.pk,
                    "name" : $scope.name,
                    "distanceFromEarth" : $scope.distanceFromEarth,
                    "discoveredBy" : $scope.discoveredBy,
                    "diameter" : $scope.diameter,
                    "atmosphere" : $scope.atmosphere,
                    "description" : $scope.description
                };

                var response = planetsApiService.persistPlanet(formData);
                response.success(function(data, status, headers, config) {
                    planetsApiService.getPlanets().success(function (response) {
                        $scope.planetsList = response;
                        clearForm();
                    });
                });
                response.error(function(data, status, headers, config) {
                    $scope.errors = data;
                    alert( "Exception details: " + JSON.stringify({data: data}));
                });

            } else {
                $scope.planetForm.submitted = true;
            }
        };

    });