
(function (){
'use strict';
var module=angular.module('movieflix');
module.service('signUpService',signUpService);
signUpService.$inject=['$http','$q'];
function signUpService($http,$q){
    var signUp=this;
    
    signUp.submit=submitUserDetails;

    /**
     * Post new user details to the server
     * @param user, New user object
     */
    function submitUserDetails(user){
      return  $http.post("http://localhost:8080/movieflix/signup",user).
            then(function(response){
              return response.data;
        },function (error) {
           return $q.reject(error.status);
        })
    }
}

})();