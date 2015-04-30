angular.module('demo.services', []).
    factory('planetsApiService', function($http) {

        var planetsAPI = {};

        planetsAPI.getPlanets = function() {
            return $http.get("api/planet/list");
        }

        planetsAPI.getPlanetDetails = function(id) {
            return $http.get('api/planet/'+ id);
        }

        planetsAPI.deletePlanet = function(id) {
            return $http.delete('api/planet/'+ id);
        }

        planetsAPI.persistPlanet = function(formData) {
            return $http.post('api/planet/persist', formData);
        }

        return planetsAPI;
    });