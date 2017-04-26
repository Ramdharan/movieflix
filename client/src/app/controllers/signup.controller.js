(function(){
    'use strict';
    var module=angular.module('movieflix');
    module.controller('signUpController',signUpController);

    signUpController.$inject=['signUpService','$location','Notification','APP_CONSTANTS'];

    function signUpController(signUpService,$location,Notification,APP_CONSTANTS){
console.log("sign up controller created");
var signup=this;
signup.submitDetails=submitDetails;

function submitDetails(){
    console.log(signup.user);
 signUpService.submit(signup.user).then(function(response) {
        $location.path('/');
     Notification.success({message:APP_CONSTANTS.SIGNUP_SUCCESS_MSG,positionY: 'top', positionX: 'center',delay:3000});

 },function (error) {

        if(error===APP_CONSTANTS.CONFLICT){
            Notification.error({message:APP_CONSTANTS.USERNAME_EXISTS_MSG,positionY: 'top', positionX: 'center', delay: 4000});

        }
      else{
            Notification.error({message:APP_CONSTANTS.UNKNOWN_REASON,positionY: 'top', positionX: 'center', delay: 3000});

        }


 });

}
    }

})();