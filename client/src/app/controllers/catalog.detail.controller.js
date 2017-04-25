(function () {
    'use strict';
    var module = angular.module('movieflix');
    module.controller('catalogDetailController', catalogDetail);
    catalogDetail.$inject = ['$routeParams', 'catalogService', 'catalogDetailService', '$filter','Notification','APP_CONSTANTS']
    function catalogDetail($routeParams, catalogService, catalogDetailService, $filter,Notification,APP_CONSTANTS) {
        var detail = this;

        detail.userRole = localStorage.getItem('userRole');

        detail.userRated = userRated;
        detail.postComment = postNewComment;
        detail.catalogId = $routeParams.catalogId;
        detail.updateCallback = updateCallback;
        getAllComments(detail.catalogId);
        getAvgRating(detail.catalogId);
        getUserRating(detail.catalogId);
        detail.ratingId;


        catalogDetailService.filter(catalogService.catalogList, detail.catalogId).then(function (response) {
            detail.catalog = response;

        });
        /*
         Call back from directive to update the catalog
         * */
        function updateCallback() {
            updateCatalog();
        }

        /**
         * Send catalog update request to Service
         */
        function updateCatalog() {
            detail.catalog.released = $filter('date')(new Date(detail.catalog.released), 'yyyy-MM-dd');
            console.log(detail.catalog.released);
            catalogDetailService.update(detail.catalog);
        }

       /*
       * Update or insert new rating for the user.
       * If the user did not rate previously it ratingId will be null
       * */

        function userRated() {
            console.log("user rated..." + detail.userRating);
            if (detail.ratingId != null) {
                updateRating(detail.ratingId, detail.userRating);
            } else {
                insertNewRating(detail.catalogId, detail.userRating);

            }
        }

        /**
         * Get all the comments related to the catalog
         * @param catalogId
         */
        function getAllComments(catalogId) {
            catalogDetailService.commentsList(catalogId).then(function (response) {

                detail.commentsList = response;
            }, function (error) {
                detail.commentsList=[];
            })
        }
        /*
        * Post new comments to server
        * @comments
        * */
        function postNewComment(comments) {
            var newComment = {
                comment: comments,
                username: localStorage.getItem('userName'),
                catalogid: detail.catalogId
            };
            catalogDetailService.postComment(newComment);
            detail.commentsList.push(newComment);

        }

        /*
        * Get average rating for the catalog
        * @param catalogid
        * */
        function getAvgRating(catalogid) {
            catalogDetailService.avgRating(catalogid).then(function (response) {
                detail.avgRating = response.rating === null ? 0 : response.rating
            }, function (error) {
                detail.avgRating = 0;
            });
        }

        /**
         * Get user rating for the catalog
         * @param catalogid
         */
        function getUserRating(catalogid) {
            catalogDetailService.userRating(catalogid).then(function (response) {
                detail.userRating = response.rating;
                detail.ratingId = response.ratingid


            }, function (error) {
                detail.userRating = 0;
                detail.ratingId = null;
            });
        }

        /**
         * Insert new rating
         * @param catalogid
         * @param rating
         */
        function insertNewRating(catalogid, rating) {
            catalogDetailService.newRating(catalogid, rating).then(function (response) {
                detail.ratingId = response.id
                getAvgRating(detail.catalogId);
            }, function (error) {
                Notification.error({message:APP_CONSTANTS.UNKNOWN_REASON,positionY: 'top', positionX: 'center', delay: 1000});
            })

        }

        /*
        * Update existing rating for the user
        *
        * */

        function updateRating(ratingid, rating) {

            catalogDetailService.updateRating(ratingid, rating).then(function (response) {
                detail.userRating = response.rating;
                getAvgRating(detail.catalogId);

            }, function (error) {
                Notification.error({message: APP_CONSTANTS.UNKNOWN_REASON,positionY: 'top', positionX: 'center', delay: 1000});
            })
        }


    }

})();