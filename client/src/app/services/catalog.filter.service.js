(function(){
    'use strict';
    var module=angular.module('movieflix');
    module.service('catalogFilterService',catalogFilterService);
    catalogFilterService.$inject=['_','catalogService','$q']

    function catalogFilterService(_,catalogService,$q){
        var vm=this;
        vm.filterType=filterType;
        vm.filterByGenre=filterGenre;


        /**
         * Filter catalog data by type using lodash
         * @param type
         *
         */

        function filterType(type) {
            var data = _.filter(catalogService.catalogList, function (catalog) {

                return catalog != null && catalog.type == type;
            });

            return data;

        }

        /**
         * Filter catalog data by genre using lodash
         * @param genre
         *
         */

        function filterGenre(genre){

            var genrelist  =_.filter(catalogService.catalogList,function(catalog){
                console.log(genre);

                return catalog.genre.indexOf(genre)>-1;
            });

            return genrelist;



            }


    }

})();