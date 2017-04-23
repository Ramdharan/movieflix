/**
 * The idea is to hide it when the user is not ADMIN
 * This directive is placed wherever Admin Action is applicable.
 */
(function(){
    'use strict';
    var module=angular.module('movieflix');

    module.directive("adminControl",deleteCatalogDir);

    function deleteCatalogDir(){
        var directive=  {
            scope:{
              role:"@role"
            },
            link:function(scope,element,attrs){
               if(!(scope.role==='ROLE_ADMIN'))
              element.css('display','none');
            }

        };

      return directive;
    }

})();