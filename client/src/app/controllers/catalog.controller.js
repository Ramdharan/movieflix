(function(){
    'use strict';

    angular.module("movieflix").controller("catalogController",catalogData);
    catalogData.$inject=['$scope','catalogService','_','$filter','$location','Notification','APP_CONSTANTS'];
    function catalogData($scope,catalogService,_,$filter,$location,Notification,APP_CONSTANTS){
        var catalog=this;
         catalog.delete=deleteCatalog;
         catalog.createCatalog=createNewCatalog;
        catalog.createdCallback=catalogCreatedCallback;
        catalog.logout=logout;
        catalog.userRole=localStorage.getItem('userRole');
        init();
        function init() {
            getCatalogData();
        }

        /**
         * Get catalog data when Controller is created
         */
        function getCatalogData() {
            catalogService.getAll().then(function(response){
                catalog.catalogList=response;
             setUpPagination();
            },function(error){
                Notification.error({message:APP_CONSTANTS.CATALOG_LOAD_ERROR,positionY: 'top', positionX: 'top', delay: 7000});

            });
        }

        /**
         * Delete Catalog
         * @param catalogId
         */
        function deleteCatalog(catalogId){
            console.log('delete catalog with id: '+catalogId);
            catalogService.deleteCatalog(catalogId).then(function(){
                _.remove(catalog.catalogList,{id:catalogId});

            },function(error){
                Notification.error({message: APP_CONSTANTS.CATALOG_DELETE_ERROR,positionY: 'top', positionX: 'top', delay: 4000});
            });
        }

        /**
         * Set up pagination
         */
        function setUpPagination(){
            catalog.totalItems = catalog.catalogList.length;
            catalog.currentPage = catalogService.currentPage;
            catalog.itemsPerPage = 16;
            setPagingData( catalog.currentPage);
        }

        /**
         * Handle data for each page
         * @param page
         */
        function setPagingData(page) {
            var pagedData = catalog.catalogList.slice(
                (page - 1) * catalog.itemsPerPage,
                page * catalog.itemsPerPage
            );
            catalog.slicedList= pagedData;
            console.log( catalogService.currentPage);

        }
        catalog.pageChanged=function(){
            console.log(catalog.currentPage);
            //Saving the current page in service. So whenever the user comes back from detail page, then same page should be reloaded again.
            catalogService.currentPage=catalog.currentPage;

        }

        /**
         * Callback function for Admin Control Directive.
         * Called when the user tries to create Catalog
         */
        function catalogCreatedCallback(){
             console.log('catalog created....');
             catalog.emptyCatalog.released=  $filter('date')(new Date(catalog.emptyCatalog.released),'yyyy-MM-dd');
             createNewCatalog(catalog.emptyCatalog);

             console.log(catalog.emptyCatalog);

             catalog.emptyCatalog=null;
        }

        /**
         * Create a new Catalog,invoked when Admin tries to create a new catalog
         * @param newCatalog
         */

        function createNewCatalog(newCatalog){
             catalogService.createCatalog(newCatalog).then(function(result){
                 catalog.catalogList.unshift(result);

             },function(error){
                 console.log(error);
                 Notification.error({message:APP_CONSTANTS.CATALOG_CREATE_ERROR,positionY: 'top', positionX: 'top', delay: 4000});

             });
         }

        /**
         * Logout of the application.
         */

        function logout(){
             $location.path('/');
             $location.replace();
             localStorage.clear();

         }

    }

})();
