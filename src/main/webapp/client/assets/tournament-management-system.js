"use strict";



define('tournament-management-system/app', ['exports', 'tournament-management-system/resolver', 'ember-load-initializers', 'tournament-management-system/config/environment'], function (exports, _resolver, _emberLoadInitializers, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });


  var App = Ember.Application.extend({
    modulePrefix: _environment.default.modulePrefix,
    podModulePrefix: _environment.default.podModulePrefix,
    Resolver: _resolver.default
  });

  (0, _emberLoadInitializers.default)(App, _environment.default.modulePrefix);

  exports.default = App;
});
define('tournament-management-system/components/card-item', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['card-item'],
        itemIconClass: '',
        itemName: ''
    });
});
define('tournament-management-system/components/card-wrapper', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['card-wrapper'],
        cardDetails: []
    });
});
define('tournament-management-system/components/form-model', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['form-model'],
        formHeader: null,
        submit: function submit(e) {
            this.get('onSubmit')(e);
        }
    });
});
define('tournament-management-system/components/general-button', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'button',
        classNames: ['button'],
        type: 'button',
        buttonIcon: null,
        buttonName: null,
        attributeBindings: ['type', 'disabled'],
        click: function click(event) {
            var handleClick = this.get('onClick');
            if (handleClick) {
                handleClick();
            }
        }
    });
});
define('tournament-management-system/components/nav-bar', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'header',
        classNames: ['container', 'navbar'],
        authenticationService: Ember.inject.service(),
        router: Ember.computed(function () {
            return Ember.getOwner(this).lookup('router:main');
        }),
        isLoggedIn: Ember.computed.readOnly('authenticationService.isLoggedIn'),
        userName: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo.userName.split(' ')[0] || 'User';
        }),
        userRole: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo.role || 0;
        }),
        actions: {
            logout: function logout() {
                this.get('authenticationService').logout();
                this.get('router').transitionTo('index');
            }
        }
    });
});
define('tournament-management-system/components/organization-card', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['organization-card'],
        cardDetail: {}
    });
});
define('tournament-management-system/components/password-input', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['input'],
        attributeBindings: ['name'],
        isRequired: false,
        isPasswordVisible: false,
        actions: {
            togglePassword: function togglePassword() {
                this.toggleProperty('isPasswordVisible');
            }
        }
    });
});
define('tournament-management-system/components/text-input', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['input'],
        inputName: null,
        inputId: null,
        lableName: null,
        isRequired: false,
        inputPlaceholder: '',
        errorMessage: null,
        actions: {
            handleInputChange: function handleInputChange() {
                this.set('errorMessage', null);
            }
        }
    });
});
define('tournament-management-system/components/tournament-card', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['tournament-card'],
        cardDetail: {}
    });
});
define('tournament-management-system/components/welcome-page', ['exports', 'ember-welcome-page/components/welcome-page'], function (exports, _welcomePage) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  Object.defineProperty(exports, 'default', {
    enumerable: true,
    get: function () {
      return _welcomePage.default;
    }
  });
});
define('tournament-management-system/controllers/application', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Controller.extend({
        authenticationService: Ember.inject.service()
    });
});
define('tournament-management-system/controllers/dashboard', ['exports'], function (exports) {
   'use strict';

   Object.defineProperty(exports, "__esModule", {
      value: true
   });
   exports.default = Ember.Controller.extend({});
});
define('tournament-management-system/controllers/login', ['exports', 'tournament-management-system/utils/form-validator'], function (exports, _formValidator3) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _slicedToArray = function () {
        function sliceIterator(arr, i) {
            var _arr = [];
            var _n = true;
            var _d = false;
            var _e = undefined;

            try {
                for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
                    _arr.push(_s.value);

                    if (i && _arr.length === i) break;
                }
            } catch (err) {
                _d = true;
                _e = err;
            } finally {
                try {
                    if (!_n && _i["return"]) _i["return"]();
                } finally {
                    if (_d) throw _e;
                }
            }

            return _arr;
        }

        return function (arr, i) {
            if (Array.isArray(arr)) {
                return arr;
            } else if (Symbol.iterator in Object(arr)) {
                return sliceIterator(arr, i);
            } else {
                throw new TypeError("Invalid attempt to destructure non-iterable instance");
            }
        };
    }();

    exports.default = Ember.Controller.extend({
        authenticationService: Ember.inject.service(),
        validationConfig: {
            email: [{ required: true, message: "Email is required!" }, { pattern: /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/g, message: "Entered email is not valid" }],
            password: [{ required: true, message: "Password is required!" }]
        },
        validationErrors: {},
        setErrors: function setErrors(validationErrors) {
            this.set('validationErrors', validationErrors);
        },
        cleanUp: function cleanUp() {
            this.setErrors({});
        },

        actions: {
            handleSubmit: function handleSubmit(event) {
                event.preventDefault();

                var formData = new FormData(event.target);

                var _formValidator = (0, _formValidator3.default)(formData, this.get('validationConfig')),
                    _formValidator2 = _slicedToArray(_formValidator, 2),
                    validationErrors = _formValidator2[0],
                    hasErrors = _formValidator2[1];

                if (hasErrors) {
                    this.setErrors(validationErrors);
                    return;
                }
                this.get('authenticationService').login(formData.get('email').toLowerCase(), formData.get('password'));

                this.transitionToRoute('index');
            }
        }
    });
});
define('tournament-management-system/controllers/organizations', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Controller.extend({
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        actions: {
            changeOrganizationStatus: function changeOrganizationStatus(orgId, newStatus) {
                if (this.get('userInfo') == null || this.get('userInfo') == undefined || +this.get('userInfo').role !== 2) {
                    return;
                }
                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId;

                Ember.$.ajax({
                    method: 'PUT',
                    url: apiURL,
                    data: JSON.stringify({ organizationStatus: newStatus }),
                    dataType: "json",
                    contentType: "application/json",
                    accepts: {
                        json: "application/json"
                    },
                    processData: false
                }).done(function (data, textStatus, jqXHR) {
                    console.log(data, textStatus, jqXHR);
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR, textStatus, errorThrown);
                });
            }
        }
    });
});
define('tournament-management-system/controllers/register', ['exports', 'tournament-management-system/utils/form-validator'], function (exports, _formValidator3) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _slicedToArray = function () {
        function sliceIterator(arr, i) {
            var _arr = [];
            var _n = true;
            var _d = false;
            var _e = undefined;

            try {
                for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
                    _arr.push(_s.value);

                    if (i && _arr.length === i) break;
                }
            } catch (err) {
                _d = true;
                _e = err;
            } finally {
                try {
                    if (!_n && _i["return"]) _i["return"]();
                } finally {
                    if (_d) throw _e;
                }
            }

            return _arr;
        }

        return function (arr, i) {
            if (Array.isArray(arr)) {
                return arr;
            } else if (Symbol.iterator in Object(arr)) {
                return sliceIterator(arr, i);
            } else {
                throw new TypeError("Invalid attempt to destructure non-iterable instance");
            }
        };
    }();

    exports.default = Ember.Controller.extend({
        authenticationService: Ember.inject.service(),
        validationConfig: {
            userName: [{ required: true, message: "User name is required!" }, { minLength: 3, message: "User name must be atleast 3 characters long" }, { maxLength: 30, message: "User name must be less than 30 characters" }],
            dateOfBirth: [{ required: true, message: "Date of birth is required" }, {
                validator: function validator(date) {
                    if (!date) {
                        return false;
                    }
                    var isLeapYear = function isLeapYear(year) {
                        if (year / 400) {
                            return true;
                        } else if (year / 100) {
                            return false;
                        } else if (year / 4) {
                            return true;
                        }
                        return false;
                    };

                    var getDaysCount = function getDaysCount(month, year) {
                        switch (month) {
                            case 1:
                            case 3:
                            case 5:
                            case 7:
                            case 8:
                            case 10:
                            case 12:
                                return 31;
                            case 2:
                                return 28 + isLeapYear(year);
                            default:
                                return 30;
                        }
                    };

                    var _date$split$map = date.split("/").map(Number),
                        _date$split$map2 = _slicedToArray(_date$split$map, 3),
                        day = _date$split$map2[0],
                        month = _date$split$map2[1],
                        year = _date$split$map2[2];

                    if (!month || month < 1 || month > 12) {
                        return false;
                    }

                    if (!day || day < 1 || day > getDaysCount(month, year)) {
                        return false;
                    }

                    if (!year || year > new Date().getFullYear()) {
                        return false;
                    }

                    return true;
                },

                message: "Provided date is not valid format"
            }],
            phoneNumber: [{ required: true, message: "Phone number is required!" }, {
                validator: function validator(phoneNumber) {
                    console.log(phoneNumber);
                    if (!phoneNumber) {
                        return false;
                    }
                    phoneNumber = phoneNumber.trim();
                    var _iteratorNormalCompletion = true;
                    var _didIteratorError = false;
                    var _iteratorError = undefined;

                    try {
                        for (var _iterator = phoneNumber[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                            var digit = _step.value;

                            if (isNaN(digit) || digit === ' ') {
                                this.message = 'Phone number should only contain digits';
                                return false;
                            }
                        }
                    } catch (err) {
                        _didIteratorError = true;
                        _iteratorError = err;
                    } finally {
                        try {
                            if (!_iteratorNormalCompletion && _iterator.return) {
                                _iterator.return();
                            }
                        } finally {
                            if (_didIteratorError) {
                                throw _iteratorError;
                            }
                        }
                    }

                    if (phoneNumber.length !== 10) {
                        debugger;
                        this.message = 'Must only contain 10 digits (Only for india)';
                        return false;
                    }
                    return true;
                },

                message: 'Phone number is not valid'
            }],
            email: [{ required: true, message: "Email is required!" }],
            password: [{ required: true, message: "Password is required!" }, {
                validator: function validator(password) {
                    if (!password) {
                        return false;
                    }
                    var lwrCse = 0;
                    var uprCse = 0;
                    var digits = 0;
                    var splchs = 0;
                    if (password.length < 8) {
                        this.message = "Password must be atleast 8 character long";
                        return false;
                    }
                    var _iteratorNormalCompletion2 = true;
                    var _didIteratorError2 = false;
                    var _iteratorError2 = undefined;

                    try {
                        for (var _iterator2 = password[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
                            var ch = _step2.value;

                            if (ch >= 'a' && ch <= 'z') {
                                lwrCse++;
                            } else if (ch >= 'A' && ch <= 'Z') {
                                uprCse++;
                            } else if (ch >= '0' && ch <= '9') {
                                digits++;
                            } else {
                                splchs++;
                            }
                        }
                    } catch (err) {
                        _didIteratorError2 = true;
                        _iteratorError2 = err;
                    } finally {
                        try {
                            if (!_iteratorNormalCompletion2 && _iterator2.return) {
                                _iterator2.return();
                            }
                        } finally {
                            if (_didIteratorError2) {
                                throw _iteratorError2;
                            }
                        }
                    }

                    if (!lwrCse) {
                        this.message = "Password must contain atleast one lower case character";
                        return false;
                    }
                    if (!uprCse) {
                        this.message = "Password must contain atleast one upper case character";
                        return false;
                    }
                    if (!digits) {
                        this.message = "Password must contain atleast one digit";
                        return false;
                    }
                    if (!splchs) {
                        this.message = "Password must contain atleast one special character";
                        return false;
                    }
                    return true;
                },

                message: "Entered password is not valid"
            }],
            organizationName: [{ required: true, message: "Organization name is required!" }, { maxLength: 50, message: "Organization Name must be less than 50 characters" }],
            organizationAddress: [{ required: true, message: "Organization address is required!" }],
            startedYear: [{ required: true, message: "Started year is required!" }, {
                validator: function validator(year) {
                    year = +year;
                    if (!year || year > new Date().getFullYear()) {
                        return false;
                    }
                    return true;
                },

                message: "Started year is not valid"
            }]
        },
        validationErrors: {},
        setErrors: function setErrors(validationErrors) {
            this.set('validationErrors', validationErrors);
        },
        cleanUp: function cleanUp() {
            this.setErrors({});
        },

        actions: {
            handleSubmit: function handleSubmit(event) {
                event.preventDefault();

                var formData = new FormData(event.target);

                var _formValidator = (0, _formValidator3.default)(formData, this.get('validationConfig')),
                    _formValidator2 = _slicedToArray(_formValidator, 2),
                    validationErrors = _formValidator2[0],
                    hasErrors = _formValidator2[1];

                formData.set('email', formData.get('email').toLocaleLowerCase());
                if (hasErrors) {
                    this.setErrors(validationErrors);
                    return;
                }
                this.get('authenticationService').register(formData);

                this.transitionToRoute('index');
            }
        }
    });
});
define('tournament-management-system/helpers/app-version', ['exports', 'tournament-management-system/config/environment', 'ember-cli-app-version/utils/regexp'], function (exports, _environment, _regexp) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.appVersion = appVersion;
  function appVersion(_) {
    var hash = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    var version = _environment.default.APP.version;
    // e.g. 1.0.0-alpha.1+4jds75hf

    // Allow use of 'hideSha' and 'hideVersion' For backwards compatibility
    var versionOnly = hash.versionOnly || hash.hideSha;
    var shaOnly = hash.shaOnly || hash.hideVersion;

    var match = null;

    if (versionOnly) {
      if (hash.showExtended) {
        match = version.match(_regexp.versionExtendedRegExp); // 1.0.0-alpha.1
      }
      // Fallback to just version
      if (!match) {
        match = version.match(_regexp.versionRegExp); // 1.0.0
      }
    }

    if (shaOnly) {
      match = version.match(_regexp.shaRegExp); // 4jds75hf
    }

    return match ? match[0] : version;
  }

  exports.default = Ember.Helper.helper(appVersion);
});
define('tournament-management-system/helpers/calculate-deadline', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.calculateDeadline = calculateDeadline;

  var _slicedToArray = function () {
    function sliceIterator(arr, i) {
      var _arr = [];
      var _n = true;
      var _d = false;
      var _e = undefined;

      try {
        for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
          _arr.push(_s.value);

          if (i && _arr.length === i) break;
        }
      } catch (err) {
        _d = true;
        _e = err;
      } finally {
        try {
          if (!_n && _i["return"]) _i["return"]();
        } finally {
          if (_d) throw _e;
        }
      }

      return _arr;
    }

    return function (arr, i) {
      if (Array.isArray(arr)) {
        return arr;
      } else if (Symbol.iterator in Object(arr)) {
        return sliceIterator(arr, i);
      } else {
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
      }
    };
  }();

  function calculateDeadline(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        endMilliseconds = _ref2[0];

    var differenceInMillis = endMilliseconds - new Date().getTime();
    if (differenceInMillis < 0) {
      return 'Closed';
    }
    var leftDays = new Date(differenceInMillis).getDay();
    return leftDays + ' day' + (leftDays == 1 ? '' : 's') + ' left';
  }

  exports.default = Ember.Helper.helper(calculateDeadline);
});
define('tournament-management-system/helpers/eq', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.eq = eq;

  var _slicedToArray = function () {
    function sliceIterator(arr, i) {
      var _arr = [];
      var _n = true;
      var _d = false;
      var _e = undefined;

      try {
        for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
          _arr.push(_s.value);

          if (i && _arr.length === i) break;
        }
      } catch (err) {
        _d = true;
        _e = err;
      } finally {
        try {
          if (!_n && _i["return"]) _i["return"]();
        } finally {
          if (_d) throw _e;
        }
      }

      return _arr;
    }

    return function (arr, i) {
      if (Array.isArray(arr)) {
        return arr;
      } else if (Symbol.iterator in Object(arr)) {
        return sliceIterator(arr, i);
      } else {
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
      }
    };
  }();

  function eq(_ref) {
    var _ref2 = _slicedToArray(_ref, 2),
        value1 = _ref2[0],
        value2 = _ref2[1];

    return value1 === value2;
  }

  exports.default = Ember.Helper.helper(eq);
});
define('tournament-management-system/helpers/get-date', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.getDate = getDate;

  var _slicedToArray = function () {
    function sliceIterator(arr, i) {
      var _arr = [];
      var _n = true;
      var _d = false;
      var _e = undefined;

      try {
        for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
          _arr.push(_s.value);

          if (i && _arr.length === i) break;
        }
      } catch (err) {
        _d = true;
        _e = err;
      } finally {
        try {
          if (!_n && _i["return"]) _i["return"]();
        } finally {
          if (_d) throw _e;
        }
      }

      return _arr;
    }

    return function (arr, i) {
      if (Array.isArray(arr)) {
        return arr;
      } else if (Symbol.iterator in Object(arr)) {
        return sliceIterator(arr, i);
      } else {
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
      }
    };
  }();

  function getDate(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        millis = _ref2[0];

    if (!millis) {
      return 'Not specified';
    }
    return new Date(millis).toLocaleDateString();
  }

  exports.default = Ember.Helper.helper(getDate);
});
define('tournament-management-system/helpers/image-fallback', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.imageFallback = imageFallback;

  var _slicedToArray = function () {
    function sliceIterator(arr, i) {
      var _arr = [];
      var _n = true;
      var _d = false;
      var _e = undefined;

      try {
        for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
          _arr.push(_s.value);

          if (i && _arr.length === i) break;
        }
      } catch (err) {
        _d = true;
        _e = err;
      } finally {
        try {
          if (!_n && _i["return"]) _i["return"]();
        } finally {
          if (_d) throw _e;
        }
      }

      return _arr;
    }

    return function (arr, i) {
      if (Array.isArray(arr)) {
        return arr;
      } else if (Symbol.iterator in Object(arr)) {
        return sliceIterator(arr, i);
      } else {
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
      }
    };
  }();

  function imageFallback(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        imageUrl = _ref2[0];

    return imageUrl || 'images/tournament-place-holder.svg';
  }

  exports.default = Ember.Helper.helper(imageFallback);
});
define('tournament-management-system/helpers/organization-status', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.organizationStatus = organizationStatus;

  var _slicedToArray = function () {
    function sliceIterator(arr, i) {
      var _arr = [];
      var _n = true;
      var _d = false;
      var _e = undefined;

      try {
        for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
          _arr.push(_s.value);

          if (i && _arr.length === i) break;
        }
      } catch (err) {
        _d = true;
        _e = err;
      } finally {
        try {
          if (!_n && _i["return"]) _i["return"]();
        } finally {
          if (_d) throw _e;
        }
      }

      return _arr;
    }

    return function (arr, i) {
      if (Array.isArray(arr)) {
        return arr;
      } else if (Symbol.iterator in Object(arr)) {
        return sliceIterator(arr, i);
      } else {
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
      }
    };
  }();

  function organizationStatus(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        orgStatus = _ref2[0];

    switch (orgStatus) {
      case 0:
        return 'Not Verified';
      case 1:
        return 'Verified';
      case 2:
        return 'Banned';
      default:
        return 'Closed';
    }
  }

  exports.default = Ember.Helper.helper(organizationStatus);
});
define('tournament-management-system/helpers/pluralize', ['exports', 'ember-inflector/lib/helpers/pluralize'], function (exports, _pluralize) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = _pluralize.default;
});
define('tournament-management-system/helpers/singularize', ['exports', 'ember-inflector/lib/helpers/singularize'], function (exports, _singularize) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = _singularize.default;
});
define('tournament-management-system/helpers/sport-type', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.sportType = sportType;

  var _slicedToArray = function () {
    function sliceIterator(arr, i) {
      var _arr = [];
      var _n = true;
      var _d = false;
      var _e = undefined;

      try {
        for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
          _arr.push(_s.value);

          if (i && _arr.length === i) break;
        }
      } catch (err) {
        _d = true;
        _e = err;
      } finally {
        try {
          if (!_n && _i["return"]) _i["return"]();
        } finally {
          if (_d) throw _e;
        }
      }

      return _arr;
    }

    return function (arr, i) {
      if (Array.isArray(arr)) {
        return arr;
      } else if (Symbol.iterator in Object(arr)) {
        return sliceIterator(arr, i);
      } else {
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
      }
    };
  }();

  function sportType(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        sportType = _ref2[0];

    return sportType ? 'Individual' : 'Team';
  }

  exports.default = Ember.Helper.helper(sportType);
});
define('tournament-management-system/helpers/tournament-status', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.tournamentStatus = tournamentStatus;

  var _slicedToArray = function () {
    function sliceIterator(arr, i) {
      var _arr = [];
      var _n = true;
      var _d = false;
      var _e = undefined;

      try {
        for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
          _arr.push(_s.value);

          if (i && _arr.length === i) break;
        }
      } catch (err) {
        _d = true;
        _e = err;
      } finally {
        try {
          if (!_n && _i["return"]) _i["return"]();
        } finally {
          if (_d) throw _e;
        }
      }

      return _arr;
    }

    return function (arr, i) {
      if (Array.isArray(arr)) {
        return arr;
      } else if (Symbol.iterator in Object(arr)) {
        return sliceIterator(arr, i);
      } else {
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
      }
    };
  }();

  function tournamentStatus(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        tournamentStatus = _ref2[0];

    switch (tournamentStatus) {
      case 0:
        return 'Upcoming';
      case 1:
        return 'Ongoing';
      case 2:
        return 'Completed';
      case 3:
        return 'Cancelled';
      default:
        return 'Closed';
    }
  }

  exports.default = Ember.Helper.helper(tournamentStatus);
});
define('tournament-management-system/initializers/app-version', ['exports', 'ember-cli-app-version/initializer-factory', 'tournament-management-system/config/environment'], function (exports, _initializerFactory, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });


  var name = void 0,
      version = void 0;
  if (_environment.default.APP) {
    name = _environment.default.APP.name;
    version = _environment.default.APP.version;
  }

  exports.default = {
    name: 'App Version',
    initialize: (0, _initializerFactory.default)(name, version)
  };
});
define('tournament-management-system/initializers/container-debug-adapter', ['exports', 'ember-resolver/resolvers/classic/container-debug-adapter'], function (exports, _containerDebugAdapter) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    name: 'container-debug-adapter',

    initialize: function initialize() {
      var app = arguments[1] || arguments[0];

      app.register('container-debug-adapter:main', _containerDebugAdapter.default);
      app.inject('container-debug-adapter:main', 'namespace', 'application:main');
    }
  };
});
define('tournament-management-system/initializers/data-adapter', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    name: 'data-adapter',
    before: 'store',
    initialize: function initialize() {}
  };
});
define('tournament-management-system/initializers/ember-data', ['exports', 'ember-data/setup-container', 'ember-data'], function (exports, _setupContainer) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    name: 'ember-data',
    initialize: _setupContainer.default
  };
});
define('tournament-management-system/initializers/export-application-global', ['exports', 'tournament-management-system/config/environment'], function (exports, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.initialize = initialize;
  function initialize() {
    var application = arguments[1] || arguments[0];
    if (_environment.default.exportApplicationGlobal !== false) {
      var theGlobal;
      if (typeof window !== 'undefined') {
        theGlobal = window;
      } else if (typeof global !== 'undefined') {
        theGlobal = global;
      } else if (typeof self !== 'undefined') {
        theGlobal = self;
      } else {
        // no reasonable global, just bail
        return;
      }

      var value = _environment.default.exportApplicationGlobal;
      var globalName;

      if (typeof value === 'string') {
        globalName = value;
      } else {
        globalName = Ember.String.classify(_environment.default.modulePrefix);
      }

      if (!theGlobal[globalName]) {
        theGlobal[globalName] = application;

        application.reopen({
          willDestroy: function willDestroy() {
            this._super.apply(this, arguments);
            delete theGlobal[globalName];
          }
        });
      }
    }
  }

  exports.default = {
    name: 'export-application-global',

    initialize: initialize
  };
});
define('tournament-management-system/initializers/injectStore', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    name: 'injectStore',
    before: 'store',
    initialize: function initialize() {}
  };
});
define('tournament-management-system/initializers/store', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    name: 'store',
    after: 'ember-data',
    initialize: function initialize() {}
  };
});
define('tournament-management-system/initializers/transforms', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    name: 'transforms',
    before: 'store',
    initialize: function initialize() {}
  };
});
define("tournament-management-system/instance-initializers/ember-data", ["exports", "ember-data/instance-initializers/initialize-store-service"], function (exports, _initializeStoreService) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    name: "ember-data",
    initialize: _initializeStoreService.default
  };
});
define('tournament-management-system/mixins/controller-cleanup', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Mixin.create({
        controllerCleanup: function controllerCleanup() {
            var controller = this.controller;
            if (controller && typeof controller.cleanUp === 'function') {
                controller.cleanUp();
            }
        }
    });
});
define('tournament-management-system/resolver', ['exports', 'ember-resolver'], function (exports, _emberResolver) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = _emberResolver.default;
});
define('tournament-management-system/router', ['exports', 'tournament-management-system/config/environment'], function (exports, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });


  var Router = Ember.Router.extend({
    location: _environment.default.locationType,
    rootURL: _environment.default.rootURL
  });

  Router.map(function () {
    this.route('login');
    this.route('register');
    this.route('dashboard');
    this.route('tournaments', function () {
      this.route('tournament', { path: '/:tournament_id' });
    });
    this.route('organizations');
  });

  exports.default = Router;
});
define('tournament-management-system/routes/dashboard', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend({
        authenticationService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        requestUrl: '',
        beforeModel: function beforeModel() {

            if (!this.get('authenticationService').isLoggedIn) {
                this.transitionTo('login');
            }

            var userInfo = this.get('userInfo');
            if (userInfo.role === null || userInfo.role === undefined || userInfo.organizationId === null || userInfo.organizationId === undefined) {
                this.get('authenticationService').logout();
                this.transitionTo('login');
                return;
            }

            switch (+this.get('userInfo').role) {
                case 0:
                    console.log("Role", 0);
                    this.set('requestUrl', 'http://localhost:8080/tms/api/v1/orgs/' + userInfo.organizationId + '/tournaments?filter_userId=' + userInfo.userId);
                    break;
                case 1:
                    console.log("Role", 1);
                    this.set('requestUrl', 'http://localhost:8080/tms/api/v1/orgs/' + userInfo.organizationId + '/tournaments');
                    break;
                case 2:
                    console.log("Role", 2);
                    this.set('requestUrl', 'http://localhost:8080/tms/api/v1/orgs');
                    break;
                default:
                    console.log("Role", "default");
                    this.get('authenticationService').logout();
                    this.transitionTo('login');
            }
        },
        model: function model() {
            var thisRef = this;
            return Ember.$.ajax({
                method: 'GET',
                url: this.get('requestUrl'),
                accepts: {
                    'json': 'application/json'
                },
                dataType: 'json'
            }).done(function (data, textStatus, jqXHR) {
                console.log("res", data, textStatus, jqXHR);
                console.log(thisRef.get('userInfo'));
                console.log(thisRef.get('userInfo').role);
                return data;
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log("error", jqXHR, textStatus, errorThrown);
            });
        },

        actions: {
            loading: function loading() {
                console.log("loading");
            },
            error: function error() {
                console.log("error occured!");
                return true;
            }
        }
    });
});
define('tournament-management-system/routes/login', ['exports', 'tournament-management-system/mixins/controller-cleanup'], function (exports, _controllerCleanup) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend(_controllerCleanup.default, {

        authenticationService: Ember.inject.service(),
        beforeModel: function beforeModel() {
            var self = this;
            this.get('authenticationService').checkin(function () {
                self.transitionTo('index');
            });
        },

        actions: {
            willTransition: function willTransition(transition) {
                this.controllerCleanup();
            }
        }
    });
});
define('tournament-management-system/routes/organizations', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend({
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        beforeModel: function beforeModel() {

            if (!this.get('authenticationService').isLoggedIn) {
                this.transitionTo('login');
                return;
            }

            var userInfo = this.get('userInfo');
            if (userInfo.role === null || userInfo.role === undefined || userInfo.organizationId === null || userInfo.organizationId === undefined) {
                this.get('authenticationService').logout();
                this.transitionTo('login');
                return;
            }

            if (+this.get('userInfo').role != 2) {
                this.get('authenticationService').logout();
                this.transitionTo('login');
                return;
            }
        },
        model: function model() {
            var config = this.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs';
            var thisRef = this;
            return $.ajax({
                method: 'GET',
                url: apiURL,
                accepts: {
                    'json': 'application/json'
                },
                dataType: 'json'
            }).done(function (data, textStatus, jqXHR) {
                console.log("res", data, textStatus, jqXHR);
                console.log(thisRef.get('userInfo'));
                console.log(thisRef.get('userInfo').role);
                return data;
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log("error", jqXHR, textStatus, errorThrown);
            });
        },

        actions: {
            loading: function loading() {
                console.log("loading");
            },
            error: function error() {
                console.log("error occured!");
                return true;
            }
        }
    });
});
define('tournament-management-system/routes/register', ['exports', 'tournament-management-system/mixins/controller-cleanup'], function (exports, _controllerCleanup) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend(_controllerCleanup.default, {
        actions: {
            willTransition: function willTransition(transition) {
                this.controllerCleanup();
            }
        }
    });
});
define('tournament-management-system/routes/tournaments', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.Route.extend({});
});
define('tournament-management-system/routes/tournaments/tournament', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.Route.extend({});
});
define('tournament-management-system/services/ajax', ['exports', 'ember-ajax/services/ajax'], function (exports, _ajax) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  Object.defineProperty(exports, 'default', {
    enumerable: true,
    get: function () {
      return _ajax.default;
    }
  });
});
define('tournament-management-system/services/authentication-service', ['exports', 'tournament-management-system/utils/hash-set', 'tournament-management-system/utils/sanitize-input'], function (exports, _hashSet, _sanitizeInput) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Service.extend({
        envService: Ember.inject.service(),
        userInfo: JSON.parse(sessionStorage.getItem('userInfo')),
        isLoggedIn: sessionStorage.getItem('isLoggedIn') == 'true',

        _setUserInfo: function _setUserInfo(userInfo, isLoggedIn) {
            this.set("userInfo", userInfo);
            this.set("isLoggedIn", isLoggedIn);
            sessionStorage.setItem('userInfo', JSON.stringify(userInfo));
            sessionStorage.setItem('isLoggedIn', '' + isLoggedIn);
        },
        register: function register(formData) {
            var thisRef = this;
            var orgData = {};
            var userData = {};

            var vulnerableCharacters = new _hashSet.default("<", ">", "&", "'", "\"", "/", "\\", "=", "(", ")", ":", "%", "{", "}", ";");

            orgData['startedYear'] = +formData.get('startedYear');

            var _arr = ['organizationName', 'organizationAddress'];
            for (var _i = 0; _i < _arr.length; _i++) {
                var key = _arr[_i];
                orgData[key] = (0, _sanitizeInput.default)(formData.get(key), vulnerableCharacters);
            }

            userData['userName'] = (0, _sanitizeInput.default)(formData.get('userName'), vulnerableCharacters);
            userData['dateOfBirth'] = new Date(formData.get('dateOfBirth')).getTime();

            var _arr2 = ['phoneNumber', 'email', 'password'];
            for (var _i2 = 0; _i2 < _arr2.length; _i2++) {
                var _key = _arr2[_i2];
                userData[_key] = formData.get(_key);
            }

            var config = this.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/auth/register';

            Ember.$.ajax({
                method: "POST",
                url: apiURL,
                data: JSON.stringify({ orgData: orgData, userData: userData }),
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                },
                processData: false,
                headers: {
                    'Cache-Control': 'no-cache',
                    'Pragma': 'no-cache'
                },
                cache: false
            }).done(function (data, textStatus, jqXHR) {
                thisRef._setUserInfo(data.data, true);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
            });
        },
        login: function login(email, password) {
            var config = this.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/auth/login';

            var thisRef = this;
            Ember.$.ajax({
                method: "POST",
                url: apiURL,
                data: JSON.stringify({
                    email: email,
                    password: password
                }),
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                },
                processData: false,

                headers: {
                    'Cache-Control': 'no-cache',
                    'Pragma': 'no-cache'
                },
                cache: false
            }).done(function (data, textStatus, jqXHR) {
                thisRef._setUserInfo(data.data, true);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
            });
        },
        checkin: function checkin(callBack) {

            var config = this.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/auth/checkin';

            var thisRef = this;
            return Ember.$.ajax({
                method: "POST",
                url: apiURL,
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                },
                headers: {
                    'Cache-Control': 'no-cache,no-store,max-age=0,must-revalidate',
                    'Pragma': 'no-cache'
                },
                cache: false
            }).done(function (data, textStatus, jqXHR) {
                thisRef._setUserInfo(data.data, true);
                callBack();
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
            });
        },
        logout: function logout() {

            var config = this.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/auth/logout';

            var thisRef = this;
            Ember.$.ajax({
                method: "POST",
                url: apiURL,
                accepts: {
                    json: "application/json"
                },
                headers: {
                    'Cache-Control': 'no-cache',
                    'Pragma': 'no-cache'
                },
                cache: false
            }).done(function (data, textStatus, jqXHR) {
                thisRef._setUserInfo(null, false);
                sessionStorage.clear();
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
            });
        }
    });
});
define('tournament-management-system/services/env-service', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Service.extend({
        _config: {
            BASE_API_URL: 'http://localhost:8080/tms'
        },
        getEnv: function getEnv(variableName) {
            return this.get('_config')[variableName];
        }
    });
});
define("tournament-management-system/templates/application", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "h+Kx0tWG", "block": "{\"statements\":[[1,[26,[\"nav-bar\"]],false],[0,\"\\n\\n\"],[1,[26,[\"outlet\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/application.hbs" } });
});
define("tournament-management-system/templates/components/card-item", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "aDf4N3hV", "block": "{\"statements\":[[11,\"div\",[]],[16,\"class\",[34,[\"card-item-icon \",[26,[\"itemIconClass\"]]]]],[13],[14],[0,\"\\n\"],[11,\"p\",[]],[15,\"class\",\"card-item-name\"],[13],[1,[26,[\"itemName\"]],false],[14],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/card-item.hbs" } });
});
define("tournament-management-system/templates/components/card-wrapper", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "dahqm8R1", "block": "{\"statements\":[[6,[\"each\"],[[28,[\"cardDetails\"]]],null,{\"statements\":[[0,\"    \"],[1,[33,[\"component\"],[[28,[\"card-component\"]]],[[\"cardDetail\",\"handleEvent\"],[[28,[\"cardDetail\"]],[28,[\"handleEvent\"]]]]],false],[0,\"\\n\"]],\"locals\":[\"cardDetail\"]},null],[0,\"\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/card-wrapper.hbs" } });
});
define("tournament-management-system/templates/components/form-model", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "XD92yXZt", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"form-header-box\"],[13],[0,\"\\n    \"],[11,\"h2\",[]],[15,\"class\",\"form-header\"],[13],[1,[26,[\"formHeader\"]],false],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"form-logo\"],[13],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[11,\"form\",[]],[15,\"class\",\"form\"],[13],[0,\"\\n    \"],[18,\"default\"],[0,\"\\n\"],[14],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/form-model.hbs" } });
});
define("tournament-management-system/templates/components/general-button", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "5TuN+W6/", "block": "{\"statements\":[[6,[\"if\"],[[28,[\"buttonIcon\"]]],null,{\"statements\":[[0,\"    \"],[11,\"img\",[]],[16,\"src\",[26,[\"buttonIcon\"]],null],[15,\"alt\",\"ButtonIcon\"],[13],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"\\n\"],[6,[\"if\"],[[28,[\"buttonName\"]]],null,{\"statements\":[[0,\"    \"],[11,\"span\",[]],[13],[1,[26,[\"buttonName\"]],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/general-button.hbs" } });
});
define("tournament-management-system/templates/components/nav-bar", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "5jlFXrCe", "block": "{\"statements\":[[6,[\"link-to\"],[\"index\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"    \"],[11,\"div\",[]],[15,\"class\",\"navbar-logo\"],[13],[14],[0,\"\\n\"]],\"locals\":[]},null],[11,\"nav\",[]],[15,\"class\",\"navbar-menu\"],[13],[0,\"\\n\"],[6,[\"if\"],[[28,[\"isLoggedIn\"]]],null,{\"statements\":[[6,[\"link-to\"],[\"dashboard\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"nav-button bg-transparent no-border ft-grey\",\"Dashboard\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"userRole\"]],0],null]],null,{\"statements\":[[0,\"\\n\"]],\"locals\":[]},null],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"userRole\"]],1],null]],null,{\"statements\":[[6,[\"link-to\"],[\"tournaments\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[],\"locals\":[]},null]],\"locals\":[]},null],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"userRole\"]],2],null]],null,{\"statements\":[[6,[\"link-to\"],[\"organizations\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"nav-button bg-transparent no-border ft-grey\",\"Organizations\"]]],false],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null],[0,\"        \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"bg-white br-grey ft-grey curved\",[28,[\"userName\"]]]]],false],[0,\"\\n        \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"bg-red br-red ft-white\",\"Logout\",[33,[\"action\"],[[28,[null]],\"logout\"],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"link-to\"],[\"register\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"bg-blue br-blue ft-white\",\"Register\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"link-to\"],[\"login\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"no-border bg-transparent ft-blue\",\"Login\"]]],false],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]}],[14],[0,\"\\n\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/nav-bar.hbs" } });
});
define("tournament-management-system/templates/components/organization-card", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "kjR5bAuo", "block": "{\"statements\":[[1,[33,[\"card-item\"],null,[[\"class\",\"itemIconClass\",\"itemName\"],[\"organization-card-title\",\"building-icon\",[28,[\"cardDetail\",\"organizationName\"]]]]],false],[0,\"\\n\"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"location-icon\",[28,[\"cardDetail\",\"organizationAddress\"]]]]],false],[0,\"\\n\"],[11,\"div\",[]],[15,\"class\",\"organization-card-box\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"organization-card-detail-box\"],[13],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"calander-icon\",[28,[\"cardDetail\",\"startedYear\"]]]]],false],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"admin-icon\",[28,[\"cardDetail\",\"userName\"]]]]],false],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"badge-icon\",[33,[\"organization-status\"],[[28,[\"cardDetail\",\"organizationStatus\"]]],null]]]],false],[0,\"\\n    \"],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"organization-card-button-box\"],[13],[0,\"\\n        \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"bg-transparent no-border ft-blue soft-corner\",\"Details\"]]],false],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"cardDetail\",\"organizationStatus\"]],0],null]],null,{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"bg-green br-green ft-white soft-corner\",\"Approve\",[33,[\"action\"],[[28,[null]],\"handleEvent\",[28,[\"cardDetail\",\"organizationId\"]],1],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"cardDetail\",\"organizationStatus\"]],1],null]],null,{\"statements\":[[0,\"                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"bg-orange br-organe ft-white soft-corner\",\"Restrict\",[33,[\"action\"],[[28,[null]],\"handleEvent\",[28,[\"cardDetail\",\"organizationId\"]],0],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[0,\"                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"disabled\"],[\"bg-pale-white br-grey ft-light-grey soft-corner cr-not-allowed\",\"Blocked\",true]]],false],[0,\"\\n\"]],\"locals\":[]}]],\"locals\":[]}],[0,\"\\n\"],[6,[\"unless\"],[[33,[\"eq\"],[[28,[\"cardDetail\",\"organizationStatus\"]],2],null]],null,{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-red bg-white ft-red soft-corner\",\"Block\",[33,[\"action\"],[[28,[null]],\"handleEvent\",[28,[\"cardDetail\",\"organizationId\"]],2],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-green bg-black ft-green soft-corner\",\"Unblock\",[33,[\"action\"],[[28,[null]],\"handleEvent\",[28,[\"cardDetail\",\"organizationId\"]],1],null]]]],false],[0,\"\\n\"]],\"locals\":[]}],[0,\"    \"],[14],[0,\"\\n\"],[14]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/organization-card.hbs" } });
});
define("tournament-management-system/templates/components/password-input", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "MFkIIyRg", "block": "{\"statements\":[[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/password-input.hbs" } });
});
define("tournament-management-system/templates/components/text-input", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "hIIjKRSS", "block": "{\"statements\":[[11,\"div\",[]],[16,\"class\",[34,[\"input-box \",[33,[\"unless\"],[[28,[\"errorMessage\"]],\"br-light-grey\",\"br-red\"],null]]]],[13],[0,\"\\n    \"],[11,\"label\",[]],[15,\"class\",\"input-label\"],[16,\"for\",[26,[\"inputId\"]],null],[13],[0,\"\\n        \"],[1,[26,[\"labelName\"]],false],[0,\" \\n\"],[6,[\"if\"],[[28,[\"isRequired\"]]],null,{\"statements\":[[0,\"            \"],[11,\"span\",[]],[15,\"class\",\"input-required\"],[13],[0,\"*\"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[14],[0,\"\\n    \"],[11,\"input\",[]],[16,\"id\",[26,[\"inputId\"]],null],[16,\"name\",[26,[\"inputName\"]],null],[15,\"class\",\"text-input\"],[16,\"placeholder\",[26,[\"inputPlaceholder\"]],null],[5,[\"action\"],[[28,[null]],\"handleInputChange\"],[[\"on\"],[\"input\"]]],[13],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[6,[\"if\"],[[28,[\"errorMessage\"]]],null,{\"statements\":[[0,\"    \"],[11,\"p\",[]],[15,\"class\",\"input-error\"],[13],[1,[26,[\"errorMessage\"]],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/text-input.hbs" } });
});
define("tournament-management-system/templates/components/tournament-card", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "+r813SYV", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"tournament-card-image\"],[15,\"style\",\"--tournament-image : url('images/tournament-place-holder.svg');\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-status\"],[13],[1,[33,[\"tournament-status\"],[[28,[\"cardDetail\",\"tournamentStatus\"]]],null],false],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[11,\"div\",[]],[15,\"class\",\"tournament-card-details\"],[13],[0,\"\\n    \"],[11,\"h2\",[]],[15,\"class\",\"tournament-card-title\"],[13],[1,[28,[\"cardDetail\",\"tournamentName\"]],false],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-box\"],[13],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"cube-icon\",[28,[\"cardDetail\",\"sportName\"]]]]],false],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"people-icon\",[33,[\"sport-type\"],[[28,[\"cardDetail\",\"sportType\"]]],null]]]],false],[0,\"\\n    \"],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-box\"],[13],[0,\"\\n        \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-innerbox\"],[13],[0,\"\\n            \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"calander-icon\",[33,[\"get-date\"],[[28,[\"cardDetail\",\"tournamentDate\"]]],null]]]],false],[0,\"\\n            \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"clock-icon\",[33,[\"calculate-deadline\"],[[28,[\"cardDetail\",\"registrationEndDate\"]]],null]]]],false],[0,\"\\n        \"],[14],[0,\"\\n\"],[6,[\"link-to\"],[\"index\"],[[\"class\"],[\"no-decoration\"]],{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"bg-light-grey br-grey rounded\",\"Details\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[18,\"default\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/tournament-card.hbs" } });
});
define("tournament-management-system/templates/dashboard", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "shOiPEfy", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section\"],[13],[0,\"\\n    \"],[1,[33,[\"card-wrapper\"],null,[[\"class\",\"card-component\",\"cardDetails\"],[\"wrap\",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null],\"organization-card\",\"tournament-card\"],null],[28,[\"model\",\"data\"]]]]],false],[0,\"\\n\"],[14],[0,\"\\n\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/dashboard.hbs" } });
});
define("tournament-management-system/templates/login", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "Qg5twIUp", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section auth\"],[13],[0,\"\\n\"],[6,[\"form-model\"],null,[[\"class\",\"formHeader\",\"onSubmit\"],[\"w-50 mxw-500\",\"Login\",[33,[\"action\"],[[28,[null]],\"handleSubmit\"],null]]],{\"statements\":[[0,\"        \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"email\",\"login-email\",\"Email\",[28,[\"validationErrors\",\"email\"]]]]],false],[0,\"\\n        \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"password\",\"login-password\",\"Password\",[28,[\"validationErrors\",\"password\"]]]]],false],[0,\"\\n        \"],[11,\"p\",[]],[15,\"class\",\"auth-switch\"],[13],[0,\"\\n            Don't have an account? \"],[6,[\"link-to\"],[\"register\"],null,{\"statements\":[[0,\"Register\"]],\"locals\":[]},null],[0,\"\\n        \"],[14],[0,\" \\n        \"],[1,[33,[\"general-button\"],null,[[\"type\",\"buttonName\",\"class\"],[\"submit\",\"Login\",\"bg-blue ft-white full-width no-border\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[14],[0,\"\\n\"],[1,[26,[\"outlet\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/login.hbs" } });
});
define("tournament-management-system/templates/organizations", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "WtEviCHm", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section\"],[13],[0,\"\\n    \"],[1,[33,[\"card-wrapper\"],null,[[\"class\",\"card-component\",\"cardDetails\",\"handleEvent\"],[\"wrap\",\"organization-card\",[28,[\"model\",\"data\"]],[33,[\"action\"],[[28,[null]],\"changeOrganizationStatus\"],null]]]],false],[0,\"\\n\"],[14],[0,\"\\n\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/organizations.hbs" } });
});
define("tournament-management-system/templates/register", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "uSxvBbNE", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section auth\"],[13],[0,\"\\n\"],[6,[\"form-model\"],null,[[\"class\",\"formHeader\",\"onSubmit\"],[\"w-80\",\"Register\",[33,[\"action\"],[[28,[null]],\"handleSubmit\"],null]]],{\"statements\":[[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"auth-wrapper\"],[13],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"auth-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"auth-innerbox\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"userName\",\"register-username\",\"User Name\",[28,[\"validationErrors\",\"userName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"dateOfBirth\",\"register-dateofbirth\",\"Date Of Birth (dd/mm/yyyy)\",[28,[\"validationErrors\",\"dateOfBirth\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"phoneNumber\",\"register-phonenumber\",\"Phone Number\",[28,[\"validationErrors\",\"phoneNumber\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"email\",\"register-email\",\"Email\",[28,[\"validationErrors\",\"email\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"password\",\"register-password\",\"Password\",[28,[\"validationErrors\",\"password\"]]]]],false],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"auth-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"auth-innerbox\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"organizationName\",\"register-organizationname\",\"Organization Name\",[28,[\"validationErrors\",\"organizationName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"organizationAddress\",\"register-organizationaddress\",\"Organization Address\",[28,[\"validationErrors\",\"organizationAddress\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"inputPlaceholder\",\"errorMessage\"],[\"startedYear\",\"register-startedyear\",\"Started Year\",[28,[\"validationErrors\",\"startedYear\"]]]]],false],[0,\"\\n                \"],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"space\"],[13],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"auth-innerbox\"],[13],[0,\"\\n                    \"],[11,\"p\",[]],[15,\"class\",\"auth-switch\"],[13],[0,\"\\n                        Already have an account? \"],[6,[\"link-to\"],[\"login\"],null,{\"statements\":[[0,\"Login\"]],\"locals\":[]},null],[0,\"\\n                    \"],[14],[0,\" \\n                    \"],[1,[33,[\"general-button\"],null,[[\"type\",\"buttonName\",\"class\"],[\"submit\",\"Register\",\"bg-blue ft-white full-width no-border\"]]],false],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[14],[0,\"\\n\"],[1,[26,[\"outlet\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/register.hbs" } });
});
define("tournament-management-system/templates/tournaments", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "daPq6uwW", "block": "{\"statements\":[[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/tournaments.hbs" } });
});
define("tournament-management-system/templates/tournaments/tournament", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "4461yXvX", "block": "{\"statements\":[[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/tournaments/tournament.hbs" } });
});
define('tournament-management-system/utils/form-validator', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = formValidator;

  var _slicedToArray = function () {
    function sliceIterator(arr, i) {
      var _arr = [];
      var _n = true;
      var _d = false;
      var _e = undefined;

      try {
        for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) {
          _arr.push(_s.value);

          if (i && _arr.length === i) break;
        }
      } catch (err) {
        _d = true;
        _e = err;
      } finally {
        try {
          if (!_n && _i["return"]) _i["return"]();
        } finally {
          if (_d) throw _e;
        }
      }

      return _arr;
    }

    return function (arr, i) {
      if (Array.isArray(arr)) {
        return arr;
      } else if (Symbol.iterator in Object(arr)) {
        return sliceIterator(arr, i);
      } else {
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
      }
    };
  }();

  function formValidator(formData, validationConfig) {

    var validationErrors = {};
    var hasErrors = false;

    var _loop = function _loop(key, value) {
      if (!validationConfig[key]) return 'continue';

      validationConfig[key].some(function (condition) {
        if (condition.required && !value) {
          validationErrors[key] = condition.message;
          return hasErrors = true;
        }
        if (condition.minLength && value && value.length < condition.minLength) {
          validationErrors[key] = condition.message;
          return hasErrors = true;
        }
        if (condition.maxLength && value && value.length > condition.maxLength) {
          validationErrors[key] = condition.message;
          return hasErrors = true;
        }
        if (condition.pattern && !condition.pattern.test(value)) {
          validationErrors[key] = condition.message;
          return hasErrors = true;
        }
        if (condition.validator && typeof condition.validator === 'function' && !condition.validator(value)) {
          console.log(condition.validator, condition.validator(value));
          validationErrors[key] = condition.message;
          return hasErrors = true;
        }
      });
    };

    var _iteratorNormalCompletion = true;
    var _didIteratorError = false;
    var _iteratorError = undefined;

    try {
      for (var _iterator = formData.entries()[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
        var _ref = _step.value;

        var _ref2 = _slicedToArray(_ref, 2);

        var key = _ref2[0];
        var value = _ref2[1];

        var _ret = _loop(key, value);

        if (_ret === 'continue') continue;
      }
    } catch (err) {
      _didIteratorError = true;
      _iteratorError = err;
    } finally {
      try {
        if (!_iteratorNormalCompletion && _iterator.return) {
          _iterator.return();
        }
      } finally {
        if (_didIteratorError) {
          throw _iteratorError;
        }
      }
    }

    return [validationErrors, hasErrors];
  };
});
define("tournament-management-system/utils/hash-set", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = HashSet;
  function HashSet() {
    var _obj = {};

    for (var _len = arguments.length, values = Array(_len), _key = 0; _key < _len; _key++) {
      values[_key] = arguments[_key];
    }

    values.forEach(function (value) {
      _obj[value] = value;
    });

    this.contains = function (val) {
      return _obj.hasOwnProperty(val);
    };

    this.add = function (val) {
      _obj[val] = val;
    };

    this.remove = function (val) {
      delete _obj[val];
    };

    this.toArray = function () {
      return Object.keys(_obj);
    };
  }
});
define("tournament-management-system/utils/sanitize-input", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = sanitizeInput;
  function sanitizeInput(inputValue, vulnerableCharacters) {
    if (!inputValue) {
      return inputValue;
    }

    var sanitizedInput = "";
    var _iteratorNormalCompletion = true;
    var _didIteratorError = false;
    var _iteratorError = undefined;

    try {
      for (var _iterator = inputValue[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
        var ch = _step.value;

        if (!vulnerableCharacters.contains(ch)) {
          sanitizedInput += ch;
        }
      }
    } catch (err) {
      _didIteratorError = true;
      _iteratorError = err;
    } finally {
      try {
        if (!_iteratorNormalCompletion && _iterator.return) {
          _iterator.return();
        }
      } finally {
        if (_didIteratorError) {
          throw _iteratorError;
        }
      }
    }

    return sanitizedInput;
  };
});


define('tournament-management-system/config/environment', ['ember'], function(Ember) {
  var prefix = 'tournament-management-system';
try {
  var metaName = prefix + '/config/environment';
  var rawConfig = document.querySelector('meta[name="' + metaName + '"]').getAttribute('content');
  var config = JSON.parse(unescape(rawConfig));

  var exports = { 'default': config };

  Object.defineProperty(exports, '__esModule', { value: true });

  return exports;
}
catch(err) {
  throw new Error('Could not read config from meta tag with name "' + metaName + '".');
}

});

if (!runningTests) {
  require("tournament-management-system/app")["default"].create({"LOG_RESOLVER":true,"LOG_ACTIVE_GENERATION":true,"LOG_TRANSITIONS":true,"LOG_TRANSITIONS_INTERNAL":true,"LOG_VIEW_LOOKUPS":true,"name":"tournament-management-system","version":"0.0.0+25543d5b"});
}
//# sourceMappingURL=tournament-management-system.map
