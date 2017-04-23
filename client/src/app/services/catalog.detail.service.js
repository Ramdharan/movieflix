(function(){
    'use strict';
    var module=angular.module('movieflix');
    module.service('catalogDetailService',catalogDetailService);

    catalogDetailService.$inject=['$http','$q','_','APP_CONSTANTS'];

    function catalogDetailService($http,$q,_,APP_CONSTANTS){
        console.log('service created');
     var ctlDtlService=this;
     ctlDtlService.commentsList=getAllComments;
     ctlDtlService.filter=filterData;
     ctlDtlService.postComment=postNewComment;
     ctlDtlService.update=updateCatalog;
     ctlDtlService.avgRating=getAvgRating;
     ctlDtlService.userRating=getUserRating;
        ctlDtlService.newRating=insertNewRating;
        ctlDtlService.updateRating=updateRating;


        /**
         * Get the selected catalog from catalog list
         * @param catalogList
         * @param catalogId
         * @returns {Promise}
         */
     function filterData(catalogList,catalogId){
            var deferred=$q.defer();
           filterCatalog();

            function filterCatalog(){
              var result=  _.find(catalogList, {id:catalogId});
                deferred.resolve(result);

            }
            return deferred.promise

     }

        /**
         * Get all comments for catalog
         * @param catalogId
         */
     function getAllComments(catalogId){

         return $http.get(APP_CONSTANTS.URL+'/comments/getall',{
           headers:{
               'Authorization':localStorage.getItem('token')

           },
             params:{
               catalogid:catalogId
             }


         }).then(function(response){
             console.log(response.data);
             return response.data;
         },function(error){
            return $q.reject(error)
         });
     }

        /**
         * Post new comment for catalog
         * @param comment
         */
     function postNewComment(comment){

         return $http.post(APP_CONSTANTS.URL+'/comments/post',comment,

             {
             headers:{
                 'Authorization':localStorage.getItem('token')

             }

         }).then(function(response){
             return response.data;
         },function(error){
             return $q.reject(error)
         });
     }

        /**
         * Update catalog information
         * @param catalog
         */
     function updateCatalog(catalog){

         return $http.put(APP_CONSTANTS.URL+'/catalog/update',catalog,{
             headers:{
                 'Authorization':localStorage.getItem('token')

             }
         }).then(function(response){
             return response.data;

         },function(error){
             return $q.reject(error.status)

         });

     }

        /**
         * Get average rating for the catalog
         * @param catalogid
         */

     function getAvgRating(catalogid){
         return $http.get(APP_CONSTANTS.URL+'/rating/getavg',{
             headers:{
                 'Authorization':localStorage.getItem('token')

             },
             params:{
                 catalogid:catalogid
             }
         }).then(function(response){
             return response.data;
         },function(error){
             $q.reject(error.status);
         });
     }

        /**
         * Get user rating for the selected catalog
         * @param catalogid
         */
     function getUserRating(catalogid){
         return $http.get(APP_CONSTANTS.URL+'/rating/getuserrating',{
             headers:{
                 'Authorization':localStorage.getItem('token')

             },
             params:{
                 catalogid:catalogid
             }
         }).then(function(response){
             return response.data;
         },function(error){
             $q.reject(error.status);
         });
     }

        /**
         * Insert new  rating by user
         * @param catalogid
         * @param rating
         */
     function insertNewRating(catalogid,rating){
         var rating={
             catalogid:catalogid,
             rating:rating
         }
         console.log('insert new rating with :'+rating)
         return $http.post(APP_CONSTANTS.URL+'/rating/insert',rating,{
             headers:{
                 'Authorization':localStorage.getItem('token')

             }
         }).then(function(response){
             console.log(response.data);
             return response.data;
         },function(error){
           return  $q.reject(error.status);
         });
     }

        /**
         * Update existing rating by user
         * @param ratingid
         * @param rating
         */

     function updateRating(ratingid,rating){
         var rating={
             ratingid:ratingid,
             rating:rating
         }
         return $http.put(APP_CONSTANTS.URL+'/rating/updaterating',rating,{
             headers:{
                 'Authorization':localStorage.getItem('token')

             }
         }).then(function(response){
             return response.data;
         },function(error){
           return  $q.reject(error.status);
         });
     }

    }
})();
