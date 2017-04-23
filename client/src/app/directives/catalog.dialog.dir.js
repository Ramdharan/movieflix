(function(){
    'use strict';
    var module=angular.module('movieflix');

    module.directive('catalogDialog',catalogDialog);

    catalogDialog.$inject=['$timeout']

    function catalogDialog($timeout){
    return{
        templateUrl:'/app/views/catalog.dialog.tmpl.html',
        scope:{
            callBackFunction: '&',
            catalog:'=',
            title:'@title',
            action:'@action'
        },
        link:function(scope,element,attrs){
            var editBtn= angular.element(document.querySelector('#modal-edit'));
            editBtn.bind('click',function(){
            scope.callBackFunction();
                if(scope.action==='Create'){
                    console.log(scope.action);
                    scope.catalog=null;
                }
            });
            scope.format='yyyy/MM/dd';
            scope.valuationDate = new Date();
            scope.valuationDatePickerIsOpen = false;

            scope.valuationDatePickerOpen = function ($event) {
                if ($event) {
                    $event.preventDefault();
                    $event.stopPropagation(); // Source: https://blog.johnnyreilly.com/2015/05/angular-ui-bootstrap-datepicker-weirdness.html
                }
                scope.valuationDatePickerIsOpen = true;
            };



        }


    }
    }
})();
