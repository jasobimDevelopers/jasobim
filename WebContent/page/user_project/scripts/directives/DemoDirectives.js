angular.module('Demo')
  .directive('DemoDirective', function () {
    return {
      restrict: 'A',
      link: function postLink(scope, element) {
        var ulElement = element.find('ul');
        var aElement = element.find('a');
        element.mouseenter(function(){
        	ulElement.stop().show(300);
          element.css('border-top','solid 5px #ed4f28');
          //element.css('margin-bottom','5px');

          aElement.css('margin-left','-1px');
          aElement.css('margin-right','-1px');
          aElement.css('padding-bottom','11px');
          aElement.css('border-left','solid 1px #cccccc');
          aElement.css('border-right','solid 1px #cccccc');
        });
        element.mouseleave(function(){
        	ulElement.stop().hide(300);
          // var aElement = angular.element(element.children()[0]);
          // aElement.css('padding-right','15px');
          element.css('border','none');
          element.css('margin-bottom','0px');
          aElement.css('border','none');
          aElement.css('padding-bottom','15px');
        });
      }
    };
  });