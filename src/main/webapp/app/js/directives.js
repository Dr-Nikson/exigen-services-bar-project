'use strict';

/* Directives */

(function () {

    var app = angular.module('cleshFilm.directives', []).
        directive('appVersion', ['version', function (version) {
            return function (scope, elm, attrs) {
                elm.text(version);
            };
        }]);

    app.directive('ngThumb', ['$window', function ($window) {
        var helper = {
            support: !!($window.FileReader && $window.CanvasRenderingContext2D),
            isFile: function (item) {
                return angular.isObject(item) && item instanceof $window.File;
            },
            isImage: function (file) {
                var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        };

        return {
            restrict: 'A',
            template: '<canvas/>',
            link: function (scope, element, attributes) {
                if (!helper.support) return;

                var params = scope.$eval(attributes.ngThumb);

                if (!helper.isFile(params.file)) return;
                if (!helper.isImage(params.file)) return;

                var canvas = element.find('canvas');
                var reader = new FileReader();

                reader.onload = onLoadFile;
                reader.readAsDataURL(params.file);

                function onLoadFile(event) {
                    var img = new Image();
                    img.onload = onLoadImage;
                    img.src = event.target.result;
                }

                function onLoadImage() {
                    var width = params.width || this.width / this.height * params.height;
                    var height = params.height || this.height / this.width * params.width;
                    canvas.attr({ width: width, height: height });
                    canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
                }
            }
        };
    }]);


    app.directive('personsNumInput', function () {
        return {
            scope: { selected: '=ngModel' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/persons-num-input.html',
            controller: 'PersonsNumInputCtrl',
            controllerAs: 'personsNumInputCtrl'
        };
    });


    app.directive('timeChooseInput', function () {
        return {
            scope: { selected: '=ngModel', trackingDate: '=trackByDate' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/time-choose-input.html',
            controller: 'TimeChooseInputCtrl',
            controllerAs: 'timeChooseInputCtrl'
        };
    });


    app.directive('phoneCodeInput', function () {
        return {
            scope: { selected: '=ngModel' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/phone-code-select.html',
            controller: 'PhoneCodeInputCtrl',
            controllerAs: 'phoneCodeInputCtrl'
        };
    });

    app.directive('myDropdown', ['$timeout', function ($timeout) {
        return {
            link: function ($scope, element, attrs) {
                $scope.$on('dataloaded', function () {
                    $timeout(function () { // You might need this timeout to be sure its run after DOM render.

                        $(element).selectpicker({style: 'btn-primary', menuStyle: 'dropdown-inverse'});

                    }, 0, false);
                })
            }
        };
    }]);


    app.directive('myCombobox', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',
            link: function ($scope, element, attrs) {
                $scope.$on('dataloaded', function () {
                    $timeout(function () { // You might need this timeout to be sure its run after DOM render.

                        $(element).combobox({bsVersion: '3', menu: '<ul class="typeahead typeahead-long dropdown-menu dropdown-inverse my-dowpdown" role="menu"></ul>',
                            item: '<li rel="1" class=""><a tabindex="-1" href="#" class="active"><span class="pull-left"></span></a></li>',
                            template: function () {
                                return '<div class="combobox-container"> <input type="hidden" /> <div class="input-group open"> <input type="text" autocomplete="off" /> <span class="input-group-addon dropdown-toggle" data-dropdown="dropdown"> <span class="caret" /> <span class="glyphicon glyphicon-remove" /> </span> </div> </div>';
                            }
                        });

                    }, 0, false);
                })
            }
        };
    }]);


    app.directive('myTooltip', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',
            scope: { showTooltipTrigger: "=showTooltipTrigger" },
            link: function ($scope, element, attrs) {
                var $element = $(element);


                /*
                 * При стандартном поведении тултип мигает когда ставишь курсор в инвут
                 * Также тултип мигает, когда он был открыт програмно и ты наводишь на element
                 * Поэтому, trigger = manual - ручное открывание тултипа
                 * Запоминаем, был ли тултип открыт, если да - то второй раз не открываем
                 * Тоже самое и с закрыванием
                 * ВУАЛЯ, все работает
                 */

                var showed = false;


                var showTooltip = function () {
                    if (showed)
                        return;
                    $element.tooltip('show');
                };


                var hideTooltip = function () {
                    if (!showed)
                        return;
                    $element.tooltip('hide');
                };


                $element.tooltip({delay: { show: 500, hide: 100 }, trigger: 'manual'});

                $element.on('hidden.bs.tooltip', function () {
                    showed = false;
                });

                $element.on('show.bs.tooltip', function () {
                    showed = true;
                });

                $element.hover(showTooltip, hideTooltip);

                $scope.$watch('showTooltipTrigger', function (nv) {
                    if (!nv)
                        return;
                    showTooltip();
                    $scope.showTooltipTrigger = false;
                });
            }
        };
    }]);

    app.directive('mySwitch', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',
            /*replace: true,*/
            /*templateUrl: './partials/widgets/fui-checkbox.html',*/
            scope: { selected: "=ngModel" },
            link: function ($scope, element, attrs) {
                var $element = $(element);//.find('.switch');

                console.log("LOOOL");

                $element.bootstrapSwitch();

                $element.on('switch-change', function (e, data) {
                    console.log("CHANGED", data);
                    $scope.selected = data.value;
                    $scope.$apply()
                });
            }
        };
    }]);


    app.factory('myHttpInterceptor', function ($q, $window) {
        return function (promise) {
            return promise.then(function (response) {
                $("#spinner").hide();
                return response;
            }, function (response) {
                $("#spinner").hide();
                return $q.reject(response);
            });
        };
    });

    /*app.directive('mySwitch', ['$timeout', function ($timeout) {
     return {
     restrict: 'E',
     */
    /*replace: true,*/
    /*
     templateUrl: './partials/widgets/fui-checkbox.html',
     scope: { selected: "=ngModel" },
     link: function ($scope, element, attrs) {
     var $element = $(element);//.find('.switch');

     console.log("LOOOL");

     $element.bootstrapSwitch();

     $element.on('switch-change',function (e,data) {
     console.log("CHANGED",data);
     });
     },
     compile: function(elem) {
     //elem.replaceWith(Markdowner.transform(elem.html()));
     console.log(elem.html());

     */
    /*var $element = $(elem.html());

     //console.log("LOOOL");

     $element.bootstrapSwitch();

     $element.on('switch-change',function (e,data) {
     console.log("CHANGED",data);
     });*/
    /*

     var newElem = $(elem.html());
     //newElem.bootstrapSwitch();

     elem.replaceWith(newElem);

     return function (scope, element, attrs, controller) {

     var $element = $(element);//.find('.switch');

     console.log('3333');
     $element.bootstrapSwitch();

     $element.on('switch-change',function (e,data) {
     console.log("CHANGED",data);
     });

     $element.show();
     }

     }
     };
     }]);*/


    // HERE


    app.directive('imgCropped', function () {
        return {
            restrict: 'E',
            replace: true,
            scope: { src: '=ngModel', selected: '&', aspectRatio: '=aspectRatio' },
            link: function (scope, element, attr) {
                var myImg;
                var jcrop_api;
                var clear = function () {
                    if (myImg) {
                        myImg.next().remove();
                        myImg.remove();
                        myImg = undefined;
                    }
                };
                var run = function (nv) {
                    clear();
                    if (nv) {
                        element.after('<img />');
                        myImg = element.next();
                        myImg.attr('src', nv);
                        var $myImg = $(myImg);
                        var $parent = $myImg.parent('div').css({'opacity': 0});
                        //myImg.css({'display':'none'});
                        //myImg.attr('class',attr.class);
                        $myImg.Jcrop({
                            trackDocument: true,
                            onSelect: function (x) {
                                scope.$apply(function () {
                                    scope.selected({cords: x});
                                });
                            },
                            boxWidth: $parent.width()/*,
                             boxHeight: 600*/
                        }, function () {
                            jcrop_api = this;
                            $parent.css({'opacity': 1});
                        });
                    }
                };
                scope.$watch(function () {
                    return scope.src;
                }, run);

                scope.$watch(function () {
                    return scope.aspectRatio;
                }, function (nv) {
                    if (!jcrop_api)
                        return;
                    jcrop_api.setOptions({ aspectRatio: nv });
                    jcrop_api.focus();
                });
                //attr.$observe('src', run);

                scope.$on('$destroy', clear);


                //run(attr.src);
            }
        };
    });


    /*app.directive('myCombobox', ['$timeout', function ($timeout) {
     return {
     restrict: 'A',
     link: function ($scope, element, attrs) {
     $scope.$on('dataloaded', function () {
     $timeout(function () { // You might need this timeout to be sure its run after DOM render.

     $(element).combobox({bsVersion: '3', menu: '<ul class="typeahead typeahead-long dropdown-menu dropdown-inverse my-dowpdown" role="menu"></ul>',
     item : '<li rel="1" class=""><a tabindex="-1" href="#" class="active"><span class="pull-left"></span></a></li>',
     template : function () {
     return '<div class="combobox-container"> <input type="hidden" /> <div class="input-group open"> <input type="text" autocomplete="off" /> <span class="input-group-addon dropdown-toggle" data-dropdown="dropdown"> <span class="caret" /> <span class="glyphicon glyphicon-remove" /> </span> </div> </div>';
     }
     });

     }, 0, false);
     })
     }
     };
     }]);*/


    app.directive('myGoupBtn', ['$timeout', function ($timeout) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/goup-btn.html',
            link: function ($scope, element, attrs) {
                $(element).find('button').click(function () {
                    $('html, body').stop().animate({
                        scrollTop: 0
                    }, 800);
                });
            }
        };
    }]);

    app.directive('imageInput', function () {
        return {
            scope: { selectedImage: '=ngModel', dChosenFun: '&' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/image-input.html',
            controller: 'ImageInputCtrl',
            controllerAs: 'imageInputCtrl'
        };
    });


    app.directive('professionInput', function () {
        return {
            scope: { selected: '=ngModel' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/profession-input.html',
            controller: 'ProfessionsInputCtrl',
            controllerAs: 'professionsInputCtrl'
        };
    });

    app.directive('staffInput', function () {
        return {
            scope: { selected: '=ngModel' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/staff-input.html',
            controller: 'StaffInputCtrl',
            controllerAs: 'staffInputCtrl'
        };
    });

    app.directive('countryInput', function () {
        return {
            scope: { selected: '=ngModel' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/country-input.html',
            controller: 'CountryInputCtrl',
            controllerAs: 'countryInputCtrl'
        };
    });

    app.directive('rightholderInput', function () {
        return {
            scope: { selected: '=ngModel' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/rightholder-input.html',
            controller: 'RightholderInputCtrl',
            controllerAs: 'rightholderInputCtrl'
        };
    });

    app.directive('genreInput', function () {
        return {
            scope: { selected: '=ngModel' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/genre-input.html',
            controller: 'GenreInputCtrl',
            controllerAs: 'genreInputCtrl'
        };
    });

    app.directive('awardInput', function () {
        return {
            scope: { selected: '=ngModel' },
            restrict: 'E',
            replace: true,
            templateUrl: './partials/widgets/award-input.html',
            controller: 'AwardInputCtrl',
            controllerAs: 'awardInputCtrl'
        };
    });

    app.directive('categoryInput', function () {
        return {
            scope: { selected: '=ngModel', categoriesData: '=' },
            restrict: 'E',
            templateUrl: './partials/widgets/category-input.html',
            controller: 'CategoryInputCtrl',
            controllerAs: 'categoryInputCtrl'
        };
    });


    /*app.directive('slideable', function () {
     return {
     restrict:'C',
     compile: function (element, attr) {
     // wrap tag
     var contents = element.html();
     element.html('<div class="slideable_content" style="margin:0 !important; padding:0 !important" >' + contents + '</div>');

     return function postLink(scope, element, attrs) {
     // default properties
     attrs.duration = (!attrs.duration) ? '1s' : attrs.duration;
     attrs.easing = (!attrs.easing) ? 'ease-in-out' : attrs.easing;
     element.css({
     'overflow': 'hidden',
     'height': '0px',
     'transitionProperty': 'height',
     'transitionDuration': attrs.duration,
     'transitionTimingFunction': attrs.easing
     });
     };
     }
     };
     });*/


    app.directive('slideToggle', function () {
        return {
            restrict: 'A',
            scope: { expanded: '=' },
            link: function (scope, element, attrs) {
                var $target, $el;

                //attrs.expanded = false;
                scope.expanded = true;
                $el = $(element);

                $el.click(function () {
                    scope.expanded = !scope.expanded;
                    scope.$apply();
                });

                scope.$watch('expanded', function (nv) {
                    /*if (!$target)
                     $target = $(attrs.slideToggle);*/
                    $target = $(attrs.slideToggle);
                    console.log($target);
                    $target.slideToggle(900);
                    $target.attr('expanded', nv);
                });
            }
        }
    });

    app.directive('tagsinput', function () {
        return {
            restrict: 'C',
            scope: {
                model: '=ngModel'
            },
            link: function (scope, element, attrs) {
                $(element).tagsInput().change(function () {
                    console.log("HELLLLLLOO");

                });
                scope.$watch('model', function (nv) {
                    console.log("HELLLLLLOO2");
                    if (!nv)
                        return;
                    $(element).importTags(nv);
                    //$(element).tagf
                    //console.log($target);
                    //$target.slideToggle(900);
                    //$target.attr('expanded',nv);
                });
            }
        }
    });


})();