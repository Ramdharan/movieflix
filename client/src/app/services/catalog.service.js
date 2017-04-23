(function(){
    'use strict';
    var module=angular.module('movieflix');
    module.service('catalogService',catalogService);
    catalogService.$inject=['$http','$q','APP_CONSTANTS'];

    function catalogService($http,$q,APP_CONSTANTS){
         var service=this;
         service.getAll=getCatalogList;
         service.currentPage=1;
         service.catalogList;
        // localStorage.setItem('token','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb290QG1vdmllZmxpeC5jb20iLCJjcmVhdGVkIjoxNDkxOTM5MzEwNzU3fQ.bJuoxCKrzS_UubvaE53omdDxQFecYEGzOiucj5CIFpyWH56Gjgn477uzKR56UBSNCgnlKVgfpOXTxhYsglo-oQ');
         service.deleteCatalog=deleteCatalog;
         service.createCatalog=createCatalog;

        /**
         * Fetch Catalog list from the Server. If the list is already fetched, return it
         * @returns {Promise}.
         */
         function getCatalogList() {
             if (service.catalogList === undefined) {
                 return $http.get(APP_CONSTANTS.URL+'/catalog/getall', {
                         headers: {
                             'Authorization':localStorage.getItem('token')

                         }
                     }
                 ).then(function (response) {
                         console.log(response.data);
                         service.catalogList = response.data;
                         return service.catalogList;
                     }, function (error) {
                         return $q.reject(error.status);

                     }
                 );
             }else{
                 var deferred=$q.defer();
                 deferred.resolve(service.catalogList);

                 return deferred.promise;
             }
         }

        /**
         * Delete Catalog
         * @param catalogId
         */
         function deleteCatalog(catalogId){
            return  $http.delete(APP_CONSTANTS.URL+'/catalog/delete',
                 {
                     params:{
                       catalogId:catalogId
                     },
                     headers:{
                         'Authorization':localStorage.getItem('token')

                     }

                 }).then(function(response){
                     return response.data;

             },function(error){
                  return $q.reject(error.status);
             })
         }

        /**
         * Create catalog
         * @param catalog
         */
         function createCatalog(catalog){
             return $http.post(APP_CONSTANTS.URL+'/catalog/create',catalog,{
                 headers:{
                     'Authorization':localStorage.getItem('token')

                 }

             }).then(function(response){
                 return response.data;
             },function (error){
                 return $q.reject(error.status);
             });
         }

    }
})();
