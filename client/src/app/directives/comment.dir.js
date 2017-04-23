(function(){
    var module=angular.module('movieflix');
    module.directive('commentsDirective',commentsDirective);
    commentsDirective.$inject=['$timeout']
    function commentsDirective($timeout){
        return{
            templateUrl:'/app/views/comment.tmpl.html',
            scope:{
                commentsList:'=',
                callBackFunction:"&"
            },
            link:function(scope,element,attrs){
                console.dir(scope);
                console.dir(attrs);
                console.dir(element);
                var sendBtn = angular.element(document.querySelector('#add-comment'));
               sendBtn.bind('click',function(){
                   var comments={
                       comment:scope.userComments
                   }
                   scope.callBackFunction(comments);

                   //Scroll to recently added comment
                   $timeout(function() {
                       var scroller = document.getElementById("comments-list");

                       scroller.scrollTop = scroller.scrollHeight;
                   }, 0, false);
                   scope.userComments=null;
                   scope.commentsform.$setPristine();


               });

            }
        }
    }
})();
