(function(){
    'use strict';
    var module= angular.module('movieflix',['ngRoute','ngMessages','ui.bootstrap','ngRateIt','ui-notification']);

    console.log('module created');
    //Add lodash support
    module.constant('_', window._);

    //Application Constants
    module.constant("APP_CONSTANTS",{
        URL:'http://localhost:8080/movieflix',
        INVALID_CREDENTIALS_MSG:'Invalid Username/Password',
        SIGNUP_SUCCESS_MSG:'Sign up success, now you can login' ,
        USERNAME_EXISTS_MSG:'Username already exists',
        UNAUTHORIZED:401,
        UNKNOWN_REASON:'Cannot perform this now',
        CONFLICT:409,
        CATALOG_DELETE_ERROR:'Cannot delete catalog now. You are unauthorized or some error with server',
        CATALOG_CREATE_ERROR:'Cannot create catalog. You are unauthorized or data you submitted is improper',
        CATALOG_LOAD_ERROR:'Unable to fetch information now. Logout and try again'
    });

    //Configure routes
    module.config(configPaths);

    //Configure Notification
    module.config(configNotifications);
    module.config(['$locationProvider', function($locationProvider) {
        $locationProvider.hashPrefix('');
    }]);
    configPaths.$inject=['$routeProvider'];

    function configPaths($routeProvider){

        $routeProvider.
        when('/catalog',{
            templateUrl:'app/views/catalog.tmpl.html',
            controller:'catalogController',
            controllerAs:'catalogCtrl'
        }).
        when('/',{
            templateUrl:'app/views/login.tmpl.html',
            controller:'loginController',
            controllerAs:'lgnCtrl'


        }).
        when('/signup',{
            templateUrl:'app/views/signup.tmpl.html',
            controller:'signUpController',
            controllerAs:'signUpCtrl'
        })
            .when('/detail/:catalogId',{
                templateUrl:'app/views/catalog.detail.tmpl.html',
                controller:'catalogDetailController',
                controllerAs:'catalogDetailCtrl'
            })
            .when('/filter/:type',{
                templateUrl:'app/views/catalog.filter.tmpl.html',
                controller:'catalogFilterController',
                controllerAs:'CatalogFilterCtrl'
            });

    }


    //This options can be changed later
    configNotifications.$inject=['NotificationProvider'];
    function configNotifications(NotificationProvider){

        NotificationProvider.setOptions({
            delay: 10000,
            startTop: 20,
            startRight: 10,
            verticalSpacing: 20,
            horizontalSpacing: 20,
            positionX: 'left',
            positionY: 'bottom'
        });

    }

})();
