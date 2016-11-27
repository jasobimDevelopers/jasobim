var app = angular.module('Demo', []);
app.config(function ($locationProvider,$httpProvider) {
    console.log("载入angular config");
    // $routeProvider
    // .when('/PARisk/EnterpriseRisk', {
    //     templateUrl:'page/project/html/main.html',
    //     controller: 'PAMainController'
    //   })
    // .otherwise({redirectTo: '/PARisk/home'});
    // $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
    // $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
    $locationProvider.html5Mode(true);
  });
// app.run(function(AuthService,$rootScope) {
//   AuthService.loginIntern().then(function(result) {
//     console.log("广播用户登录数据");
//     $rootScope.$broadcast('UserLogin');
//   });
// });