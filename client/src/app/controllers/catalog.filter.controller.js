(function(){
   'use strict';
    var module=angular.module('movieflix');
    module.controller('catalogFilterController',catalogFilterController);
    catalogFilterController.$inject=['$routeParams','catalogFilterService'];

    function catalogFilterController($routeParams,catalogFilterService){

        var filter=this;
        filter.orderByField='imdbRating';
        filter.reverseOrder=false;

       filter.selected=$routeParams.type;

        console.log('filter :'+filter.selected);
        if(filter.selected==='movie'||filter.selected==='series'){
           getFilterType(filter.selected);
        }else {
          getFilterGenre(filter.selected);
        }

        /**
         * Filter by type movies or series
         * @param type
         */
           function getFilterType(type){
          
            filter.filterList= catalogFilterService.filterType(type);
           }

        /**
         * Filter by genre
         * @param genre
         */

           function getFilterGenre(genre){

            filter.filterList= catalogFilterService.filterByGenre(genre);

           }

    }
})();