var app = angular.module('demo', [
    'demo.controllers',
    'demo.services',
    'ngRoute',
    'ngMessages'
]).

config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when("/planets", {templateUrl: "partials/planets.html", controller: "indexController"}).
        otherwise({redirectTo: '/planets'});
}]);

app.directive('smartFloat', function ($filter) {
    var FLOAT_REGEXP_1 = /^\$?\d+.(\d{3})*(\,\d*)$/; //Numbers like: 1.123,56
    var FLOAT_REGEXP_2 = /^\$?\d+,(\d{3})*(\.\d*)$/; //Numbers like: 1,123.56
    var FLOAT_REGEXP_3 = /^\$?\d+(\.\d*)?$/; //Numbers like: 1123.56
    var FLOAT_REGEXP_4 = /^\$?\d+(\,\d*)?$/; //Numbers like: 1123,56
    var FLOAT_REGEXP_5 = /^\$?\d+$/; //Numbers like: 1123

    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {

            function customValidator(ngModelValue) {
                if (FLOAT_REGEXP_1.test(ngModelValue)) {
                    ctrl.$setValidity('float', true);
                    return parseFloat(ngModelValue.replace('.', '').replace(',', '.'));
                } else if (FLOAT_REGEXP_2.test(ngModelValue)) {
                    ctrl.$setValidity('float', true);
                    return parseFloat(ngModelValue.replace(',', ''));
                } else if (FLOAT_REGEXP_3.test(ngModelValue)) {
                    ctrl.$setValidity('float', true);
                    return parseFloat(ngModelValue);
                } else if (FLOAT_REGEXP_4.test(ngModelValue)) {
                    ctrl.$setValidity('float', true);
                    return parseFloat(ngModelValue.replace(',', '.'));
                }  else if (FLOAT_REGEXP_5.test(ngModelValue)) {
                    ctrl.$setValidity('float', true);
                } else {
                    ctrl.$setValidity('float', false);
                    return undefined;
                }
            }

            ctrl.$parsers.push(customValidator);

        }
    };
});