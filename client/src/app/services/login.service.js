(function(){
    'use strict';
    var module=angular.module('movieflix');
    module.service('loginService',loginService);
    loginService.$inject=['$http','$q','APP_CONSTANTS'];

    function loginService($http,$q,APP_CONSTANTS) {
        var login=this;
        console.log('service is initialized');
        login.validateUser=verifyUser;
        login.userRole;

        /**
         * Verify User
         * @param user
         */

        function verifyUser(user){
            return $http.post(APP_CONSTANTS.URL+'/login',user,{
                headers:{
                    'Content-Type': 'application/json'
                }

            }
          ).then(function(response){
                localStorage.setItem('token',response.data.token);
                localStorage.setItem('userRole',response.data.role[0].authority);
                login.userRole=localStorage.getItem('userRole');
                localStorage.setItem('userName',response.data.username);
              return response.data;
            },function(error){
                return $q.reject(error.status);
            });

        }

    }

})();
