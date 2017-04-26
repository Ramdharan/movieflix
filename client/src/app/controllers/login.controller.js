(function(){
    'use strict';
    angular.module('movieflix').controller('loginController',validateLogin);
    validateLogin.$inject=['loginService','$location','Notification','$scope','APP_CONSTANTS'];

    function validateLogin(loginService,$location,Notification,$scope,APP_CONSTANTS){
        console.log('controller initialized');

        var validate=this;
        validate.authenticated=false;
        validate.submitDetails=validateDetails;

        /**
         * Validate user details
         */
        function validateDetails(){
            loginService.validateUser(validate.user).then(function(response){
                validate.authenticated=true;
                navigateToCatalogPage();
            },function(error){
                console.log(error);
                if(error===APP_CONSTANTS.UNAUTHORIZED){
                    Notification.error({message:APP_CONSTANTS.INVALID_CREDENTIALS_MSG,positionY: 'top', positionX: 'center', delay: 3000});

                }else{
                    Notification.error({message:APP_CONSTANTS.UNKNOWN_REASON,positionY: 'top', positionX: 'center', delay: 3000});

                }




            });

        }

        /**
         * Navigate to catalog page
         */
        function navigateToCatalogPage(){
           $location.path('/catalog');

        }

        /**
         * Disable browser back and front when the user logged out or pressed back from Catalog Page
         *Allow back and forth from Sign up page
         */
        $scope.$on('$locationChangeStart', function(event,newurl){
            if(!validate.authenticated && !(newurl.endsWith('signup')))
            event.preventDefault();

        });
    }
})();
