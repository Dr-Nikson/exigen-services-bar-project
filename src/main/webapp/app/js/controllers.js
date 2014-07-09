'use strict';

/* Controllers */

(function () {

    var app = angular.module('cleshFilm.controllers', [ 'truncate', 'angularFileUpload' ]);

    app.controller('HatCtr', ['$scope', '$route', function ($scope, $route) {
        var self = this;
        self.activeTab = undefined;

        $scope.$on('$routeChangeSuccess', function (event, current) {
            self.activeTab = current.$$route === undefined ? undefined : current.$$route.activeTab;
        });

        self.isActive = function (tabName) {
            return tabName == self.activeTab;
        };
    }]);


    app.controller('OrdersListCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
        var self = this;
        self.$scope = $scope;
        self.$scope.orders = [];

        $http.get('./../api/orders/get').then(function (response) {

            var data = response.data;
            console.log(data);

            self.$scope.orders = data.data;
            //$location.path("/home");
            //successHandler(data.data);

        });

    }]);


    app.controller('UsersListCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
        var self = this;
        self.$scope = $scope;
        self.$scope.users = [];

        $http.get('./../api/users/get').then(function (response) {

            var data = response.data;
            console.log(data);

            self.$scope.users = data.data;
            //$location.path("/home");
            //successHandler(data.data);

        });

    }]);

    app.controller('AuthorizationCtrl', ['$scope', '$http', '$routeParams', '$timeout', function ($scope, $http, $routeParams, $timeout) {
        var self = this;
        self.$scope = $scope;
        self.lol = true;
        self.user = null;
        self.isFormSended = false;
        self.showLoginBtn = false;
        self.showStatusBar = true;
        self.showErrorMessage = false;

        var showErrorMsg = function () {
            self.showErrorMessage = true;
            self.errorMessage = 'Вы не авторизовны';

            $timeout(function () {
                self.showErrorMessage = false;
                self.showLoginBtn = true;
                self.showStatusBar = false;
            }, 5000);
        };

        var getAuthorizedUserData = function () {

            self.isFormSended = true;

            //get_authorized
            $timeout(function () {
                $http.get('./../api/users/get_authorized').then(function (response) {

                    var data = response.data;
                    console.log(data);
                    self.isFormSended = false;

                    if (data.status == 'ERROR') {
                        //errorHandler(data.errorCode,data.errorDetails);
                        showErrorMsg();
                        return;
                    }

                    self.authorizeUser(data.data);
                    self.showStatusBar = false;
                    //$location.path("/home");
                    //successHandler(data.data);

                }, function () {
                    self.isFormSended = false;
                    showErrorMsg();
                    //errorHandler('order.not_enough_space','4');
                    //errorHandler('user.user_exist','');
                    //errorHandler('unknown','');
                    //successHandler(prepareOrderData());
                });
            }, 500);
        };

        self.authorizeUser = function (user) {
            self.showLoginBtn = false;
            self.user = user;
        };

        self.isAuthorized = function () {
            return (self.user !== null);
        };

        self.isAdmin = function () {
            return self.isAuthorized() && self.user.role == 'ADMIN';
        };

        getAuthorizedUserData();

    }]);

    app.controller('LoginFormCtrl', ['$scope', '$http', '$routeParams', '$location', '$timeout', function ($scope, $http, $routeParams, $location, $timeout) {
        var self = this;
        self.$scope = $scope;
        self.$scope.isFormSended = false;
        self.$scope.user = {};

        var errorHandler = function (errorCode, errorDetails) {
            switch (errorCode) {
                case 'authorization.failed':
                    self.$scope.errorMessage = 'Пользователя с таким email и паролем не существует';
                    break;
                default :
                    self.$scope.errorMessage = 'Неизвестная ошибка';
                    break;
            }
        };

        self.submitForm = function () {

            self.$scope.loginForm.$setDirty();

            if (self.$scope.loginForm.$invalid) {
                return;
            }

            console.log("login submit");
            self.$scope.isFormSended = true;

            ///api/users/authorize
            $timeout(function () {
                $http.post('./../api/users/authorize', self.$scope.user).then(function (response) {

                    var data = response.data;
                    console.log(data);
                    self.$scope.isFormSended = false;


                    if (data.status == 'ERROR') {
                        errorHandler(data.errorCode, data.errorDetails);
                        return;
                    }

                    self.$scope.authorizationCtrl.authorizeUser(data.data);
                    $location.path("/home");
                    //successHandler(data.data);


                }, function () {
                    self.$scope.isFormSended = false;
                    //errorHandler('order.not_enough_space','4');
                    //errorHandler('user.user_exist','');
                    errorHandler('unknown', '');
                    //successHandler(prepareOrderData());
                });
            }, 500);
        };

    }]);

    app.controller('HomePageCtrl', ['$scope', function ($scope) {
        var self = this;
        self.$scope = $scope;
        //self.$scope.au

    }]);

    app.controller('OrderFormCtrl', ['$scope', '$http', '$route', '$filter', '$timeout', function ($scope, $http, $route, $filter, $timeout) {
        var self = this;
        self.$scope = $scope;
        self.$scope.order = {
            personsNum: 2,
            ownAlcohol: false
        };
        //self.$scope.todayDate = new Date(new Date().getTime() + 21 * 60 * 60 * 1000);
        self.$scope.todayDate = new Date();
        self.$scope.tomorrowDate = new Date(self.$scope.todayDate.getTime() + 24 * 60 * 60 * 1000);
        self.$scope.tomorrowDate.setHours(0);
        self.$scope.tomorrowDate.setMinutes(0);
        self.$scope.afterTomorrowDate = new Date(self.$scope.tomorrowDate.getTime() + 24 * 60 * 60 * 1000);
        self.$scope.afterTomorrowDate.setHours(0);
        self.$scope.afterTomorrowDate.setMinutes(0);

        self.$scope.todayBtnDisabled = false;
        self.$scope.dataChoosedBy = 'today';
        self.$scope.timeInputTrackingDate = self.$scope.todayDate;

        self.$scope.order.allRestaurant = $route.current.$$route.allRestaurant;

        self.$scope.tmpPhoneCode = '+7';
        self.$scope.tmpPhoneNumber = '';
        self.$scope.tooltipTriggers = {};

        self.$scope.errorMessage = '';

        self.$scope.showForm = true;
        self.$scope.$parent.showTableSucceesMsg = false;
        self.$scope.$parent.showBanquetSucceesMsg = false;
        self.$scope.$parent.successOrder = {};


        if (self.$scope.order.allRestaurant)
            self.$scope.order.personsNum = null;

        self.$scope.tmpOrderTime = { raw: "", formatted: "" };

        self.$scope.isFormSended = false;

        //console.log('auc',self.$scope.authorizationCtrl);


        //self.$scope.timeInputTrackingDate = new Date(self.$scope.todayDate.getTime() + 1.5 * 60 * 60 * 1000);
        /*if(self.$scope.trackingDate.getHours() >= 22 && self.$scope.trackingDate.getMinutes() >= 30)
         {
         self.$scope.timeInputTrackingDate = self.$scope.todayDate;
         }
         else
         {
         self.$scope.timeInputTrackingDate = self.$scope.todayDate;
         }*/


        self.$scope.$watch('tmpOrderDate.raw', function (nv) {
            if (!nv)
                return;
            var tmp1 = new Date(self.$scope.timeInputTrackingDate.getTime());
            tmp1.setHours(0);
            tmp1.setMinutes(0);
            tmp1.setSeconds(0);
            tmp1.setMilliseconds(0);
            var tmp2 = new Date(nv.getTime());
            tmp2.setHours(0);
            tmp2.setMinutes(0);
            tmp2.setSeconds(0);
            tmp2.setMilliseconds(0);


            if (tmp2.getTime() < tmp1.getTime())
                return;

            self.$scope.tmpOrderDate.formatted = $filter('date')(nv, 'dd-MM-yyyy');
            self.$scope.dataChoosedBy = 'calendar';
        });

        //console.log(self.$scope.todayDate);
        //console.log(self.$scope.tmpDate);

        self.chooseDate = function (date, chosenBy) {
            self.$scope.dataChoosedBy = chosenBy;
            self.$scope.tmpOrderDate.formatted = $filter('date')(date, 'dd-MM-yyyy');
            self.$scope.timeInputTrackingDate = new Date(date.getTime() + 1 * 60 * 60 * 1000);
        };

        var showErrorFieldsTooltip = function (value, key) {
            if (value.length) {
                angular.forEach(value, showErrorFieldsTooltip);
                return;
            }
            if (!value.$name)
                return;
            self.$scope.tooltipTriggers[value.$name] = true;
        };


        var prepareDate = function () {
            console.log("here");
            //var tmpDate = $filter('date')(self.$scope.tmpOrderDate.formatted,'dd-MM-yyyy');
            var preparedDate = new Date(0);
            var tmpDate = self.$scope.tmpOrderDate.formatted.split('-');
            var tmpTime = self.$scope.tmpOrderTime.raw;
            preparedDate.setDate(tmpDate[0]);
            preparedDate.setMonth(tmpDate[1] - 1);
            preparedDate.setYear(tmpDate[2]);
            preparedDate.setHours(tmpTime.getHours());
            preparedDate.setMinutes(tmpTime.getMinutes());
            return preparedDate;
        };

        var prepareUserPhone = function () {
            return self.$scope.tmpPhoneCode + self.$scope.tmpPhoneNumber;
        };

        var prepareOrderData = function () {
            var pOrder = {};

            angular.extend(pOrder, self.$scope.order);
            pOrder.startTime = prepareDate().getTime();

            if (self.$scope.authorizationCtrl.isAuthorized())
                pOrder.user = self.$scope.authorizationCtrl.user;
            else {
                pOrder.user.phone = prepareUserPhone();
            }

            if (pOrder.allRestaurant)
                pOrder.personsNum = self.$scope.tmpPersonsBanquetNum;

            //pOrder.allRestaurant = self.$scope.order.allRestaurant;
            return pOrder;

        };


        var errorHandler = function (errorCode, errorDetails) {
            switch (errorCode) {
                case 'order.not_enough_space':
                    self.$scope.errorMessage = 'Невозможно добавить заказ - недостаточно свободных мест в выбранное время.'
                        + ' Всего свободно: ' + errorDetails + ' мест(а)';
                    break;
                case 'user.user_exist':
                    self.$scope.errorMessage = 'Пользователь с таким email существует. Проверьте пароль от аккаунта на Вашей почте'
                        + ' и выполните вход.';
                    break;
                default :
                    self.$scope.errorMessage = 'Неизвестная ошибка';
                    break;
            }
        };


        var successHandler = function (order) {

            self.$scope.showForm = false;

            if (!order.allRestaurant) {
                self.$scope.$parent.showTableSucceesMsg = true;
            }
            else {
                self.$scope.$parent.showBanquetSucceesMsg = true;
            }

            self.$scope.$parent.successOrder.targetDate = $filter('date')(order.startTime, 'dd');
            self.$scope.$parent.successOrder.targetTime = $filter('date')(order.startTime, 'HH.mm');

            self.$scope.authorizationCtrl.authorizeUser(order.user);
        };

        self.submitForm = function () {

            self.$scope.addOrderForm.$setDirty();

            if (self.$scope.addOrderForm.$invalid) {
                angular.forEach($scope.addOrderForm.$error, showErrorFieldsTooltip);
                return;
            }

            self.$scope.isFormSended = true;

            $timeout(function () {
                $http.post('./../api/orders/add', prepareOrderData()).then(function (response) {
                    var data = response.data;
                    console.log(data);
                    self.$scope.isFormSended = false;


                    if (data.status == 'ERROR') {
                        errorHandler(data.errorCode, data.errorDetails);
                        return;
                    }

                    successHandler(data.data);


                }, function () {
                    self.$scope.isFormSended = false;
                    //errorHandler('order.not_enough_space','4');
                    //errorHandler('user.user_exist','');
                    errorHandler('unknown', '');
                    //successHandler(prepareOrderData());
                });
            }, 500);


            //prepareDate();
            //self.$scope.tooltipTriggers.email = true;
        };


        self.initCurrentDataChosen = function () {
            if (self.$scope.todayDate.getHours() >= 22 && self.$scope.todayDate.getMinutes() >= 30) {
                self.$scope.todayBtnDisabled = true;
                self.$scope.dataChoosedBy = 'tomorrow';
                self.$scope.timeInputTrackingDate = self.$scope.tomorrowDate;
            }
            else {
                self.$scope.timeInputTrackingDate = new Date(self.$scope.todayDate.getTime() + 1 * 60 * 60 * 1000);
            }
        };

        self.initCurrentDataChosen();

        self.$scope.tmpOrderDate = {
            formatted: $filter('date')(self.$scope.timeInputTrackingDate, 'dd-MM-yyyy')
        };
    }]);


    app.controller('PersonsNumInputCtrl', ['$scope', '$timeout', function ($scope, $timeout) {
        var self = this;
        self.$scope = $scope;
        self.$scope.availableChooses = [2, 3, 4, 5, 6, 7, 8, 9, 10];
        $timeout(function () { // You might need this timeout to be sure its run after DOM render.

            self.$scope.$broadcast('dataloaded');

        }, 0, false);

    }]);


    app.controller('TimeChooseInputCtrl', ['$scope', '$timeout', function ($scope, $timeout) {
        var self = this;
        self.$scope = $scope;
        self.$scope.chosedTimeIndex = 0;
        self.$scope.availableTimeVariants = [];


        self.$scope.chooseTime = function (index) {
            //console.log(index);
            self.$scope.chosedTimeIndex = index;
            self.$scope.selected = self.$scope.availableTimeVariants[index];
        };

        self.$scope.calculateIndex = function (index, parent) {
            //console.log(index,parent);
            return index + parent * 12;
        };

        self.initTimeVariants = function () {
            // Date(0) == 4:00. I don't know why, --- oh! timezone!
            var startTime = new Date(0);

            self.$scope.availableTimeVariants = [];

            startTime.setHours(12);
            startTime.setMinutes(0);

            if (self.$scope.trackingDate.getHours() >= 12) {
                startTime.setHours(self.$scope.trackingDate.getHours());
                startTime.setMinutes(self.$scope.trackingDate.getMinutes());

                if (startTime.getMinutes() < 30) {
                    //startTime.setHours(tmpDate.getHours());
                    startTime.setMinutes(30);
                }
                else {
                    startTime.setMinutes(0);
                    startTime.setHours(startTime.getHours() + 1);
                }
            }

            //if(self.$scope.trackingDate.getMinutes() > 12)

            //var minutesModifier = self.$scope.trackingDate.getMinutes() >= 30 ? 2 : 1;
            //var tmpDate = new Date(self.$scope.trackingDate.getTime() + minutesModifier * 60 * 60 * 1000);

            //if(self.$scope.trackingDate.getHours() >= 22 && self.$scope.trackingDate.getMinutes() >= 30)


            console.log('track', self.$scope.trackingDate, 'start', startTime);
            for (var i = 0, currTime = new Date(startTime.getTime()); i != 23 && currTime.getHours() >= 12; i++, currTime = new Date(currTime.getTime() + 30 * 60 * 1000)) {
                self.$scope.availableTimeVariants.push(currTime);
            }
            $timeout(function () { // You might need this timeout to be sure its run after DOM render.

                //console.log(self.$scope.availableTimeVariants);
                self.$scope.$broadcast('dataloaded');

            }, 0, false);
        };

        self.$scope.$watch('trackingDate', function (nv) {
            self.initTimeVariants();
        });
        self.initTimeVariants();
        self.$scope.chooseTime(0);

    }]);


    app.controller('PhoneCodeInputCtrl', ['$scope', '$timeout', '$http', function ($scope, $timeout, $http) {
        var self = this;
        self.$scope = $scope;
        self.$scope.countryCodes = [];
        $http.get("json/CountryCodes.json").then(function (response) {
            self.$scope.countryCodes = response.data;
            self.$scope.$broadcast('dataloaded');
        });

        /*$timeout(function () { // You might need this timeout to be sure its run after DOM render.

         self.$scope.$broadcast('dataloaded');

         }, 0, false);*/

    }]);


})();