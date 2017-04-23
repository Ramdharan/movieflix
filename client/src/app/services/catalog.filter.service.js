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
         * @returns {Promise}
         */

        function filterType(type) {

                var deferred = $q.defer();
                function filterData() {
                    var data = _.filter(catalogService.catalogList, function (catalog) {

                        return catalog != null && catalog.type == type;
                    });
                    console.log(data);
                  deferred.resolve(data)
                }
            filterData();

                return deferred.promise
            }

        /**
         * Filter catalog data by genre using lodash
         * @param genre
         * @returns {Promise}
         */

        function filterGenre(genre){
            console.log(genre);
                var deferred = $q.defer();
                filterGenreData();
                function filterGenreData(){
                 var genrelist  =_.filter(catalogService.catalogList,function(catalog){
                     console.log(genre);

                     return catalog.genre.indexOf(genre)>-1;
                    });
                deferred.resolve(genrelist);
                }

                return deferred.promise;

            }


    }

})();