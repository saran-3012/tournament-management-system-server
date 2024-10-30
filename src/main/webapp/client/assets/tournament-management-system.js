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
define('tournament-management-system/components/app-loader', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'section',
        classNames: 'app-loader-container'
    });
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
        itemName: '',
        itemNameClass: '',
        attributeBindings: ['title']
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
        attributeBindings: ['type', 'disabled', 'title'],
        click: function click(event) {
            var handleClick = this.get('onClick');
            if (handleClick) {
                handleClick(event);
            }
        }
    });
});
define('tournament-management-system/components/icon-label-item', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['icon-label-item'],
        attributeBindings: ['title']
    });
});
define('tournament-management-system/components/message-box', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: null,
        messageColor: Ember.computed('messageInfo.level', function () {
            switch (this.get('messageInfo.level')) {
                case 0:
                    return 'blue';
                case 1:
                    return 'green';
                case 2:
                    return 'orange';
                case 3:
                    return 'red';
                default:
                    return 'grey';
            }
        }),
        actions: {
            pauseTimer: function pauseTimer() {
                var messageInfo = this.get('messageInfo');
                messageInfo.timeoutController.pause();
            },
            resumeTimer: function resumeTimer() {
                var messageInfo = this.get('messageInfo');
                messageInfo.timeoutController.resume();
            },
            clearTimer: function clearTimer() {
                var messageInfo = this.get('messageInfo');
                messageInfo.timeoutController.clear();
            }
        }
    });
});
define('tournament-management-system/components/message-queue', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'aside',
        classNames: ['message-queue'],
        messageQueueService: Ember.inject.service(),
        messageQueue: Ember.computed('messageQueueService.messageQueue', function () {
            return this.get('messageQueueService').messageQueue;
        })
    });
});
define('tournament-management-system/components/nav-bar', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        // Component config
        tagName: 'header',
        classNames: ['container', 'navbar'],

        // Services
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
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            return this.get('authenticationService').userInfo;
        }),

        // State config
        isProfileMenuOpen: false,
        toggleProfileMenu: function toggleProfileMenu(event, thisRef) {
            var clickEventListener = function clickEventListener(event) {
                if (thisRef.get('isProfileMenuOpen')) {
                    document.removeEventListener('click', clickEventListener);
                    thisRef.set('isProfileMenuOpen', false);
                } else {
                    setTimeout(function () {
                        document.addEventListener('click', clickEventListener);
                    }, 0);
                    thisRef.set('isProfileMenuOpen', true);
                }
            };
            clickEventListener(event);
        },


        // actions
        actions: {
            handleProfileMenuVisibility: function handleProfileMenuVisibility(event) {
                this.get('toggleProfileMenu')(event, this);
            },
            logout: function logout() {
                var router = this.get('router');
                this.get('authenticationService').logout();
                router.transitionTo('index');
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
        organization: {}
    });
});
define('tournament-management-system/components/organization-form', ['exports', 'tournament-management-system/utils/form-validator', 'tournament-management-system/utils/hash-set', 'tournament-management-system/utils/check-characters-present'], function (exports, _formValidator3, _hashSet, _checkCharactersPresent) {
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

    exports.default = Ember.Component.extend({
        tagName: null,
        validationConfig: {
            organizationName: [{ required: true, message: "Organization Name is required!" }, {
                validator: function validator(orgName) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    if ((0, _checkCharactersPresent.default)(orgName, vulnerableCharacters)) {
                        this.message = vulnerableCharacters.toString() + ' are not allowed';
                        return false;
                    }

                    return true;
                },

                message: 'Invalid organization name'
            }],
            organizationAddress: [{ required: true, message: "Organization address is required!" }, {
                validator: function validator(orgName) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    if ((0, _checkCharactersPresent.default)(orgName, vulnerableCharacters)) {
                        this.message = vulnerableCharacters.toString() + ' are not allowed';
                        return false;
                    }

                    return true;
                },

                message: 'Invalid organization address'
            }],
            startedYear: [{ required: true, message: "Started year is required!" }, {
                validator: function validator(year) {
                    if (isNaN(year)) {
                        return false;
                    }
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
        willDestroyElement: function willDestroyElement() {
            this._super.apply(this, arguments);
            this.setErrors({});
        },

        actions: {
            handleSubmit: function handleSubmit(event) {
                event.preventDefault();
                var formData = new FormData(event.target);

                var _formValidator = (0, _formValidator3.default)(formData, this.validationConfig),
                    _formValidator2 = _slicedToArray(_formValidator, 2),
                    validationErrors = _formValidator2[0],
                    hasErrors = _formValidator2[1];

                if (hasErrors) {
                    this.setErrors(validationErrors);
                    return;
                }
                this.get('handleOrganizationUpdate')(formData);
                this.get('closeOrganizationForm')();
            }
        }
    });
});
define('tournament-management-system/components/organization-navbar', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['organization-navbar'],
        authenticationService: Ember.inject.service(),
        isLoggedIn: Ember.computed.readOnly('authenticationService.isLoggedIn'),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            return this.get('authenticationService').userInfo;
        })

    });
});
define('tournament-management-system/components/organization-user-form', ['exports', 'tournament-management-system/utils/rsa-encrypter', 'tournament-management-system/utils/get-month-days-count', 'tournament-management-system/utils/hash-set', 'tournament-management-system/utils/sanitize-input', 'tournament-management-system/utils/date-time-to-mills', 'tournament-management-system/utils/form-validator'], function (exports, _rsaEncrypter, _getMonthDaysCount, _hashSet, _sanitizeInput, _dateTimeToMills, _formValidator3) {
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

    exports.default = Ember.Component.extend({
        envService: Ember.inject.service(),
        messageQueueService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        tagName: null,
        orgUserFormType: 0,
        validationConfig: {
            userName: [{ required: true, message: "User name is required!" }, { minLength: 3, message: "User name must be atleast 3 characters long" }, { maxLength: 30, message: "User name must be less than 30 characters" }],
            dateOfBirth: [{ required: true, message: "Date of birth is required" }, {
                validator: function validator(date) {
                    if (!date) {
                        return false;
                    }

                    var _date$split$map = date.split("/").map(Number),
                        _date$split$map2 = _slicedToArray(_date$split$map, 3),
                        day = _date$split$map2[0],
                        month = _date$split$map2[1],
                        year = _date$split$map2[2];

                    if (!month || month < 1 || month > 12) {
                        return false;
                    }

                    if (!day || day < 1 || day > (0, _getMonthDaysCount.default)(month, year)) {
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
                        this.message = 'Must only contain 10 digits (Only for india)';
                        return false;
                    }
                    return true;
                },

                message: 'Phone number is not valid'
            }],
            email: [{ required: true, message: "Email is required!" }, { pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/i, message: "Entered email is not valid" }],
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
            }]
        },
        validationErrors: {},
        setErrors: function setErrors(validationErrors) {
            this.set('validationErrors', validationErrors);
        },
        cleanUp: function cleanUp() {
            this.setErrors({});
        },
        createNewUser: function createNewUser(thisRef, formData) {

            console.log(thisRef);
            console.log(thisRef.get('closeOrganizationUserForm'));
            console.log(thisRef.get('refreshModel'));

            var loaderService = thisRef.get('loaderService');
            var messageQueueService = thisRef.get('messageQueueService');
            var config = thisRef.get('envService');

            loaderService.setIsLoading(true);

            var organization = thisRef.get('organization');

            var userData = {};

            var vulnerableCharacters = new _hashSet.default("<", ">");

            userData['userName'] = (0, _sanitizeInput.default)(formData.get('userName'), vulnerableCharacters);
            userData['dateOfBirth'] = (0, _dateTimeToMills.default)(formData.get('dateOfBirth'));

            var _arr = ['phoneNumber', 'email'];
            for (var _i = 0; _i < _arr.length; _i++) {
                var key = _arr[_i];
                userData[key] = formData.get(key);
            }

            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + organization.organizationId + '/users';
            var password = formData.get('password');

            (0, _rsaEncrypter.default)(password).then(function (encryptedPassword) {

                userData['password'] = encryptedPassword;

                return $.ajax({
                    method: 'POST',
                    url: apiURL,
                    data: JSON.stringify(userData),
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
                });
            }).then(function (data, textStatus, jqXHR) {
                thisRef.get('refreshModel')();
                messageQueueService.addPopupMessage({
                    message: "User added successfully",
                    level: 1
                });
                thisRef.get('closeOrganizationUserForm')();
            }).catch(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
                messageQueueService.addPopupMessage({
                    message: jqXHR.responseJson.message,
                    level: 3
                });
            }).finally(function () {
                loaderService.setIsLoading(false);
            });
        },
        updateExsistingUser: function updateExsistingUser(formData) {},

        actions: {
            handleOrganizationUserFormSubmit: function handleOrganizationUserFormSubmit(event) {
                event.preventDefault();

                var formData = new FormData(event.target);

                var _formValidator = (0, _formValidator3.default)(formData, this.validationConfig),
                    _formValidator2 = _slicedToArray(_formValidator, 2),
                    validationErrors = _formValidator2[0],
                    hasErrors = _formValidator2[1];

                formData.set('email', formData.get('email').toLowerCase());
                var password = formData.get('password');
                var confirmPassword = formData.get('confirmPassword');
                var isPasswordMatches = password === confirmPassword;
                if (!isPasswordMatches) {
                    validationErrors['confirmPassword'] = "Confirmation password not matching";
                }
                if (hasErrors || !isPasswordMatches) {
                    this.setErrors(validationErrors);
                    return;
                }

                var organizationUserFormType = this.get('organizationUserFormType');
                if (organizationUserFormType === 1) {
                    this.get('createNewUser')(this, formData);
                } else if (organizationUserFormType === 2) {}
            }
        }
    });
});
define('tournament-management-system/components/organization-user-navbar', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['organization-user-navbar'],
        authenticationService: Ember.inject.service(),
        isLoggedIn: Ember.computed.readOnly('authenticationService.isLoggedIn'),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            return this.get('authenticationService').userInfo;
        })
    });
});
define('tournament-management-system/components/participant-card', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        // Component details
        tagName: 'div',
        classNames: ['participant-card'],
        attributeBindings: ['title'],

        // Services
        messageQueueService: Ember.inject.service(),
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),

        // Actions
        actions: {
            changeParticipantStatus: function changeParticipantStatus(newStatus) {
                var _this = this;

                var messageQueueService = this.get('messageQueueService');
                var userInfo = this.get('userInfo');
                var participant = this.get('participant');

                if (+userInfo.role !== 1 && +userInfo.role !== 2) {
                    messageQueueService.addPopupMessage({
                        message: 'You are not allowed to perform this operation',
                        level: 2
                    });
                    return;
                }
                if (newStatus === participant.participantStatus) {
                    return;
                }

                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + userInfo.organizationId + '/tournaments/' + participant.tournamentId + '/participants/' + participant.participantId;

                $.ajax({
                    method: 'PUT',
                    url: apiURL,
                    data: JSON.stringify({
                        participantStatus: newStatus
                    }),
                    dataType: "json",
                    contentType: "application/json",
                    accepts: {
                        json: "application/json"
                    },
                    processData: false
                }).then(function (response, textStatus, jqXHR) {
                    _this.get('refreshModel')();
                    var displayMessage = newStatus === 0 ? 'Participant disqualification revoked' : newStatus === 1 ? 'Participant is disqualified' : 'Participant status changed';
                    messageQueueService.addPopupMessage({
                        message: displayMessage,
                        level: 1
                    });
                }).catch(function (err) {
                    messageQueueService.addPopupMessage({
                        message: err.responseJSON.message,
                        level: 3
                    });
                });
            }
        }
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
        attributeBindings: ['name', 'disabled'],
        isRequired: false,
        isPasswordVisible: false,
        inputName: null,
        inputId: null,
        lableName: null,
        inputPlaceholder: '',
        errorMessage: null,
        actions: {
            togglePasswordVisibility: function togglePasswordVisibility() {
                this.toggleProperty('isPasswordVisible');
            },
            handleInputChange: function handleInputChange() {
                this.set('errorMessage', null);
            }
        }
    });
});
define('tournament-management-system/components/popup-box', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: null,
        // didInsertElement() {
        //     this._super(...arguments);
        //     this.$('.popup-model').on('click', (event) => {
        //         event.stopPropagation();
        //     });
        // },

        // willDestroyElement() {
        //     this._super(...arguments);
        //     this.$('.popup-model').off('click');
        // },
        actions: {}
    });
});
define('tournament-management-system/components/search-bar', ['exports', 'tournament-management-system/utils/delay-calls'], function (exports, _delayCalls) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['search-bar'],
        isFocused: false,
        searchField: '',
        init: function init() {
            this._super.apply(this, arguments);
            this.set('delayedSearchCall', (0, _delayCalls.default)(this.get('minWait'), this.get('searchHandler')));
        },

        searchData: Ember.observer('searchField', function () {
            this.get('delayedSearchCall')(this.get('searchField'));
        })
    });
});
define('tournament-management-system/components/select-input', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['input'],
        attributeBindings: ['disabled'],
        inputName: null,
        inputId: null,
        lableName: null,
        isRequired: false,
        inputPlaceholder: '',
        showDefaultValue: true,
        defaultValue: { value: "", selected: 'true', disabled: 'true', hidden: 'true', displayName: 'Choose here' },
        options: [],
        errorMessage: null,
        actions: {
            handleInputChange: function handleInputChange() {
                this.set('errorMessage', null);
            }
        }
    });
});
define('tournament-management-system/components/team-card', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        // Component details
        tagName: 'div',
        classNames: ['team-card'],
        attributeBindings: ['title'],

        // Services
        messageQueueService: Ember.inject.service(),
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),

        // States
        isMembersPanelOpen: false,

        // API calls
        fetchTeamMembers: function fetchTeamMembers(thisRef) {
            var userInfo = thisRef.get('userInfo');
            var team = thisRef.get('team');
            var config = thisRef.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + userInfo.organizationId + '/tournaments/' + team.tournamentId + '/teams/' + team.teamId + '/members?exclude_limit=true';

            $.ajax({
                method: 'GET',
                url: apiURL,
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                }
            }).then(function (response, textStatus, jqXHR) {
                thisRef.set('members', response.data);
            }).catch(function (err) {
                thisRef.get('messageQueueService').addPopupMessage({
                    message: err.responseJSON.message,
                    level: 3
                });
            });
        },


        actions: {
            toggleMembersPanelOpen: function toggleMembersPanelOpen() {
                this.toggleProperty('isMembersPanelOpen');
                var isMembersPanelOpen = this.get('isMembersPanelOpen');
                if (isMembersPanelOpen && !this.get('members')) {
                    this.get('fetchTeamMembers')(this);
                };
            },
            removeTeamMember: function removeTeamMember(teamMemberId) {
                var _this = this;

                var messageQueueService = this.get('messageQueueService');
                var userInfo = this.get('userInfo');
                var team = this.get('team');

                if (userInfo.userId !== team.teamLeaderId) {
                    messageQueueService.addPopupMessage({
                        message: 'You are not allowed to perform this operation',
                        level: 2
                    });
                    return;
                }
                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + userInfo.organizationId + '/tournaments/' + team.tournamentId + '/teams/' + team.teamId + '/members/' + teamMemberId;

                $.ajax({
                    method: 'DELETE',
                    url: apiURL,
                    accepts: {
                        json: 'application/json'
                    },
                    dataType: 'json',
                    contentType: "application/json"
                }).then(function (response, textStatus, jqXHR) {
                    _this.get('fetchTeamMembers')(_this);
                    messageQueueService.addPopupMessage({
                        message: 'Member removed successfully',
                        level: 1
                    });
                }).catch(function (err) {
                    var authStatus = err.getResponseHeader('Tms-Auth-Status');
                    if (authStatus === '1') {
                        messageQueueService.addPopupMessage({
                            message: "Session expired, login again",
                            level: 0
                        });
                        _this.get('authenticationService').logout();
                        _this.transitionToRoute('index');
                        return;
                    }
                    messageQueueService.addPopupMessage({
                        message: err.responseJSON.message,
                        level: 3
                    });
                });
            },
            changeTeamStatus: function changeTeamStatus(newStatus) {
                var _this2 = this;

                newStatus = +newStatus;
                var messageQueueService = this.get('messageQueueService');
                var userInfo = this.get('userInfo');
                var team = this.get('team');

                if (+userInfo.role !== 1 && +userInfo.role !== 2) {
                    messageQueueService.addPopupMessage({
                        message: 'You are not allowed to perform this operation',
                        level: 2
                    });
                    return;
                }
                if (newStatus === team.teamStatus) {
                    return;
                }

                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + userInfo.organizationId + '/tournaments/' + team.tournamentId + '/teams/' + team.teamId;

                $.ajax({
                    method: 'PUT',
                    url: apiURL,
                    data: JSON.stringify({
                        teamStatus: newStatus
                    }),
                    dataType: "json",
                    contentType: "application/json",
                    accepts: {
                        json: "application/json"
                    },
                    processData: false
                }).then(function (response, textStatus, jqXHR) {
                    _this2.get('refreshModel')();
                    var displayMessage = newStatus === 0 ? 'Team disqualification revoked' : newStatus === 1 ? 'Team is disqualified' : 'Team status changed';
                    messageQueueService.addPopupMessage({
                        message: displayMessage,
                        level: 1
                    });
                }).catch(function (err) {
                    messageQueueService.addPopupMessage({
                        message: err.responseJSON.message,
                        level: 3
                    });
                });
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
        attributeBindings: ['disabled'],
        inputName: null,
        inputId: null,
        lableName: null,
        isRequired: false,
        inputPlaceholder: '',
        defaultValue: '',
        errorMessage: null,
        actions: {
            handleInputChange: function handleInputChange() {
                this.set('errorMessage', null);
            }
        }
    });
});
define('tournament-management-system/components/tournament-card', ['exports', 'tournament-management-system/utils/tournament-image-fallback'], function (exports, _tournamentImageFallback) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['tournament-card'],
        authenticationService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            return this.get('authenticationService').userInfo;
        }),
        isMenuOpen: false,

        toggleMenu: function toggleMenu(event, thisRef) {
            var clickEventListener = function clickEventListener(event) {
                if (thisRef.get('isMenuOpen')) {
                    document.removeEventListener('click', clickEventListener);
                    thisRef.set('isMenuOpen', false);
                } else {
                    setTimeout(function () {
                        document.addEventListener('click', clickEventListener);
                    }, 0);
                    thisRef.set('isMenuOpen', true);
                }
            };
            clickEventListener(event);
        },
        init: function init() {
            this._super.apply(this, arguments);
            var tournament = this.get('tournament') || {};
            var tournamentPoster = (0, _tournamentImageFallback.default)(tournament.sportName);
            tournament['tournamentPoster'] = tournamentPoster;
            this.set('tournament', tournament);
        },

        actions: {
            handleMenuVisibility: function handleMenuVisibility(event) {
                this.get('toggleMenu')(event, this);
            },
            selectAndOpenPopup: function selectAndOpenPopup() {
                this.get('setSelectedTournament')(this.get('tournament'));
                this.get('openCancelPopup')();
            }
        }
    });
});
define('tournament-management-system/components/tournament-navbar', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['tournament-navbar'],
        authenticationService: Ember.inject.service(),
        isLoggedIn: Ember.computed.readOnly('authenticationService.isLoggedIn'),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            return this.get('authenticationService').userInfo;
        })

    });
});
define('tournament-management-system/components/tournament-participation-form', ['exports', 'tournament-management-system/utils/check-characters-present', 'tournament-management-system/utils/form-validator', 'tournament-management-system/utils/hash-set'], function (exports, _checkCharactersPresent, _formValidator3, _hashSet) {
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

    exports.default = Ember.Component.extend({
        // Component details
        tagName: 'section',
        classNames: ['tournament-participation-form-container'],

        // Services
        dataPersistanceService: Ember.inject.service(),
        tournament: Ember.computed('dataPersistanceService.data', function () {
            return this.get('dataPersistanceService').data;
        }),

        // State config
        teamRegistrationType: 0,
        validationConfig: {
            teamName: [{ required: true, message: "Team Name is required!" }, {
                validator: function validator(tName) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    if ((0, _checkCharactersPresent.default)(tName, vulnerableCharacters)) {
                        this.message = vulnerableCharacters.toString() + ' are not allowed';
                        return false;
                    }

                    return true;
                },

                message: 'Invalid team name'
            }],
            teamId: [{ required: true, message: "Team id is required!" }, {
                validator: function validator(tId) {
                    console.log("team id", tId);
                    return !isNaN(tId);
                },

                message: 'Team id should be number'
            }]
        },
        validationErrors: {},
        setErrors: function setErrors(validationErrors) {
            this.set('validationErrors', validationErrors);
        },


        // Calls and Actions
        submit: function submit(event) {
            event.preventDefault();

            var teamRegistrationType = this.get('teamRegistrationType');

            var formData = new FormData(event.target);

            var _formValidator = (0, _formValidator3.default)(formData, this.get('validationConfig')),
                _formValidator2 = _slicedToArray(_formValidator, 2),
                validationErrors = _formValidator2[0],
                hasErrors = _formValidator2[1];

            var isTeamIdNull = false;
            if (teamRegistrationType === 1 && !formData.get('teamId')) {
                validationErrors['teamId'] = 'Select a team!';
                isTeamIdNull = true;
            }
            if (hasErrors || isTeamIdNull) {
                this.setErrors(validationErrors);
                return;
            }

            this.get('onConfirmation')({ teamRegistrationType: teamRegistrationType, formData: formData });
            this.get('closeTournamentForm')();
        },
        willDestroyElement: function willDestroyElement() {
            this._super.apply(this, arguments);
            this.setErrors({});
        },

        actions: {
            setTeamRegistrationType: function setTeamRegistrationType(value) {
                var teamRegistrationType = this.get('teamRegistrationType');
                this.set('teamRegistrationType', teamRegistrationType === value ? 0 : value);
            }
        }
    });
});
define('tournament-management-system/components/tournament-schedule-card', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        // Component details
        tagName: 'div',
        classNames: ['tournament-schedule-card'],
        attributeBindings: ['title'],

        // Services
        messageQueueService: Ember.inject.service(),
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        dataPersistanceService: Ember.inject.service(),
        tournament: Ember.computed('dataPersistanceService.data', function () {
            return this.get('dataPersistanceService').data;
        }),

        // State config
        isContestantsPanelOpen: false,

        // API calls
        fetchEventContestants: function fetchEventContestants(thisRef) {
            var userInfo = thisRef.get('userInfo');
            var schedule = thisRef.get('schedule');
            var tournament = thisRef.get('tournament');
            var config = thisRef.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + userInfo.organizationId + '/tournaments/' + schedule.tournamentId + '/events/' + schedule.tournamentEventId + '/contestants?exclude_limit=true';

            var participationType = +tournament.sportType === 0 ? 'participants' : 'teams';

            $.ajax({
                method: 'GET',
                url: apiURL,
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                }
            }).then(function (response, textStatus, jqXHR) {
                thisRef.set(participationType, response.data);
            }).catch(function (err) {
                thisRef.get('messageQueueService').addPopupMessage({
                    message: err.responseJSON.message,
                    level: 3
                });
            });
        },


        // actions
        actions: {
            toggleContestantsPanelOpen: function toggleContestantsPanelOpen() {
                var tournament = this.get('tournament');
                var participationType = +tournament.sportType === 0 ? 'participants' : 'teams';

                this.toggleProperty('isContestantsPanelOpen');
                var isMembersPanelOpen = this.get('isContestantsPanelOpen');
                if (isMembersPanelOpen && !this.get(participationType)) {
                    this.get('fetchEventContestants')(this);
                };
            },
            reloadContestants: function reloadContestants() {
                this.get('fetchEventContestants')(this);
            }
        }
    });
});
define('tournament-management-system/components/tournament-schedule-form', ['exports', 'tournament-management-system/utils/check-date-valid', 'tournament-management-system/utils/hash-set', 'tournament-management-system/utils/check-characters-present', 'tournament-management-system/utils/form-validator', 'tournament-management-system/utils/date-time-to-mills'], function (exports, _checkDateValid, _hashSet, _checkCharactersPresent, _formValidator3, _dateTimeToMills) {
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

    exports.default = Ember.Component.extend({
        // Component details
        tagName: 'section',
        classNames: ['tournament-schedule-form-container'],

        // Services
        messageQueueService: Ember.inject.service(),
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        dataPersistanceService: Ember.inject.service(),
        tournament: Ember.computed('dataPersistanceService.data', function () {
            return this.get('dataPersistanceService').data;
        }),

        // Configs
        tournamentEventRoundOptions: [{ value: 0, displayName: 'Qualifiers' }, { value: 1, displayName: 'Play-Off' }, { value: 2, displayName: 'Quarter-Finals' }, { value: 3, displayName: 'Semi-Finals' }, { value: 4, displayName: 'Finals' }],
        validationConfig: {
            tournamentEventDate: [{ required: true, message: "Event date is required!" }, {
                validator: function validator(date) {
                    if (!date) {
                        return false;
                    }

                    return (0, _checkDateValid.default)(date);
                },

                message: "Provided date is not valid format (dd/mm/yyyy)"
            }],
            tournamentEventVenue: [{ required: true, message: "Event venue is required!" }, {
                validator: function validator(teVenue) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    if ((0, _checkCharactersPresent.default)(teVenue, vulnerableCharacters)) {
                        this.message = vulnerableCharacters.toString() + ' are not allowed';
                        return false;
                    }

                    return true;
                },

                message: 'Invalid event venue'
            }]
        },
        validationErrors: {},
        setErrors: function setErrors(validationErrors) {
            this.set('validationErrors', validationErrors);
        },


        // Calls and Actions
        scheduleEvent: function scheduleEvent(thisRef, formData) {

            var messageQueueService = thisRef.get('messageQueueService');

            var tournament = thisRef.get('tournament');
            var userInfo = thisRef.get('userInfo');
            var orgId = userInfo.organizationId;
            var config = thisRef.get('envService');

            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournament.tournamentId + '/events';
            var requestData = {};

            requestData.eventData = {
                tournamentEventDate: (0, _dateTimeToMills.default)(formData.get('tournamentEventDate')),
                tournamentEventVenue: formData.get('tournamentEventVenue'),
                tournamentEventRound: +formData.get('tournamentEventRound') || 0
            };

            switch (+tournament.sportType) {
                case 0:
                    var eventParticipantsData = formData.getAll('participantId').map(function (participantId) {
                        return { participantId: +participantId };
                    });
                    requestData.eventParticipantsData = eventParticipantsData;
                    break;
                case 1:
                    var eventTeamsData = formData.getAll('teamId').map(function (teamId) {
                        return { teamId: +teamId };
                    });
                    requestData.eventTeamsData = eventTeamsData;
                    break;
                default:
                    messageQueueService.addPopupMessage({
                        message: "Something went wrong",
                        level: 3
                    });
                    return;
            }

            $.ajax({
                method: 'POST',
                url: apiURL,
                data: JSON.stringify(requestData),
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                },
                processData: false
            }).then(function (data, textStatus, jqXHR) {
                thisRef.get('refreshModel')();
                messageQueueService.addPopupMessage({
                    message: "Event scheduled successfully",
                    level: 1
                });
            }).catch(function (err) {
                console.log(err);
                messageQueueService.addPopupMessage({
                    message: err.responseJSON.message,
                    level: 3
                });
            });
        },
        submit: function submit(event) {
            event.preventDefault();

            var formData = new FormData(event.target);

            var _formValidator = (0, _formValidator3.default)(formData, this.get('validationConfig')),
                _formValidator2 = _slicedToArray(_formValidator, 2),
                validationErrors = _formValidator2[0],
                hasErrors = _formValidator2[1];

            var tournament = this.get('tournament');
            var participantIdType = +tournament.sportType === 0 ? 'participantId' : 'teamId';
            var selectedContestants = formData.getAll(participantIdType);
            var isMinimumContestantSelected = selectedContestants.length > 1;
            if (!isMinimumContestantSelected) {
                validationErrors[participantIdType] = 'Atleast select 2 ' + (+tournament.sportType === 0 ? 'participants' : 'teams');
            }

            if (hasErrors || !isMinimumContestantSelected) {
                this.setErrors(validationErrors);
                return;
            }
            switch (+this.get('tournamentScheduleFormType')) {
                case 1:
                    this.get('scheduleEvent')(this, formData);
                    break;
                case 2:
                case 3:
                default:
                    this.get('messageQueueService').addPopupMessage({
                        message: "Invalid operation",
                        level: 2
                    });
            }
            this.get('closeTournamentScheduleForm')();
        }
    });
});
define('tournament-management-system/components/user-card', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Component.extend({
        tagName: 'div',
        classNames: ['user-card'],
        user: {}
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
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        isLoading: Ember.computed('loaderService.isLoading', function () {
            return this.get('loaderService.isLoading');
        })
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
            email: [{ required: true, message: "Email is required!" }, { pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/i, message: "Entered valid email" }],
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
                var thisRef = this;
                this.get('authenticationService').login(formData.get('email').toLowerCase(), formData.get('password'), function () {
                    thisRef.transitionToRoute('dashboard');
                });
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
        updateOrganization: function updateOrganization(updatedOrg) {
            var orgs = this.get('model');
            var updatedOrgs = orgs.map(function (org) {
                return org.organizationId !== updatedOrg.organizationId ? org : Object.assign({}, org, updatedOrg);
            });
            this.set('model', updatedOrgs);
        },

        actions: {
            changeOrganizationStatus: function changeOrganizationStatus(orgId, newStatus) {
                var _this = this;

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
                }).then(function (response) {
                    _this.updateOrganization(response.data);
                }).catch(function (err) {
                    console.log("error", err);
                });
            }
        }
    });
});
define('tournament-management-system/controllers/organizations/index', ['exports'], function (exports) {
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
        updateOrganization: function updateOrganization(updatedOrg) {
            this._super();
            var orgs = this.get('model');
            var updatedOrgs = orgs.map(function (org) {
                return org.organizationId !== updatedOrg.organizationId ? org : Object.assign({}, org, updatedOrg);
            });
            this.set('model', updatedOrgs);
        },

        actions: {
            changeOrganizationStatus: function changeOrganizationStatus(orgId, newStatus) {
                var _this = this;

                if (this.get('userInfo') == null || this.get('userInfo') == undefined || +this.get('userInfo').role !== 2) {
                    return;
                }

                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId;

                $.ajax({
                    method: 'PUT',
                    url: apiURL,
                    data: JSON.stringify({ organizationStatus: newStatus }),
                    dataType: "json",
                    contentType: "application/json",
                    accepts: {
                        json: "application/json"
                    },
                    processData: false
                }).then(function (response) {
                    _this.updateOrganization(response.data);
                }).catch(function (err) {
                    console.log("error", err);
                });
            },
            searchOrganizations: function searchOrganizations(searchValue) {
                var _this2 = this;

                var userInfo = this.get('userInfo');

                if (+userInfo.role !== 2) {
                    this.transitionTo('index');
                    return;
                }

                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs?filter_orgname=' + searchValue;

                $.ajax({
                    method: "GET",
                    url: apiURL,
                    accepts: {
                        'json': 'application/json'
                    },
                    dataType: 'json'
                }).then(function (response, textStatus, xqXHR) {
                    _this2.set('organizations', response.data);
                }).catch(function (err) {
                    _this2.get('messageQueueService').addPopupMessage({
                        message: err.message,
                        level: 4
                    });
                });
            }
        }
    });
});
define('tournament-management-system/controllers/organizations/organization', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Controller.extend({
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),

        isOrganizationFormOpen: false,
        organizationUserFormType: 0,
        selectedUser: {},
        actions: {
            refreshModel: function refreshModel() {
                this.get('target').router.getHandler('organizations.organization').refresh();
            },
            setIsOrganizationFormOpen: function setIsOrganizationFormOpen(value) {
                this.set('isOrganizationFormOpen', value);
            },
            setOrganizationUserFormType: function setOrganizationUserFormType(value) {
                this.set('organizationUserFormType', value);
            },
            setSelectedUser: function setSelectedUser(user) {
                this.set('selectedUser', user);
            },
            searchOrganizationUsers: function searchOrganizationUsers(searchValue) {
                var _this = this;

                var userInfo = this.get('userInfo');

                var orgId = null;

                orgId = +userInfo.role === 2 ? this.get('organization').organizationId : userInfo.organizationId;

                if ((orgId === null || orgId === undefined) && +userInfo.role !== 2) {
                    this.get('authenticationService').logout();
                    this.transitionTo('login');
                    return;
                }

                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/users?filter_username=' + searchValue;

                $.ajax({
                    method: "GET",
                    url: apiURL,
                    accepts: {
                        'json': 'application/json'
                    },
                    dataType: 'json'
                }).then(function (response, textStatus, xqXHR) {
                    _this.set('users', response.data);
                }).catch(function (err) {
                    _this.get('messageQueueService').addPopupMessage({
                        message: err.message,
                        level: 4
                    });
                });
            },
            updateOrganizationDetails: function updateOrganizationDetails(formData) {
                var organization = this.get('organization');
                var requestBody = {};

                requestBody.organizationName = formData.get('organizationName');
                requestBody.organizationAddress = formData.get('organizationAddress');
                requestBody.startedYear = formData.get('startedYear');

                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + organization.organizationId;

                $.ajax({
                    method: 'PUT',
                    url: apiURL,
                    data: JSON.stringify(requestBody),
                    dataType: "json",
                    contentType: "application/json",
                    accepts: {
                        json: "application/json"
                    },
                    processData: false
                }).then(function (response, textStatus, jqXHR) {
                    console.log(response);
                }).catch(function (err) {
                    console.log(err);
                });
            }
        }
    });
});
define('tournament-management-system/controllers/register', ['exports', 'tournament-management-system/utils/form-validator', 'tournament-management-system/utils/get-month-days-count', 'tournament-management-system/utils/hash-set', 'tournament-management-system/utils/check-characters-present'], function (exports, _formValidator3, _getMonthDaysCount, _hashSet, _checkCharactersPresent) {
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

                    var _date$split$map = date.split("/").map(Number),
                        _date$split$map2 = _slicedToArray(_date$split$map, 3),
                        day = _date$split$map2[0],
                        month = _date$split$map2[1],
                        year = _date$split$map2[2];

                    if (!month || month < 1 || month > 12) {
                        return false;
                    }

                    if (!day || day < 1 || day > (0, _getMonthDaysCount.default)(month, year)) {
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
            email: [{ required: true, message: "Email is required!" }, { pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/i, message: "Entered email is not valid" }],
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
            organizationName: [{ required: true, message: "Organization name is required!" }, { maxLength: 50, message: "Organization Name must be less than 50 characters" }, {
                validator: function validator(orgName) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    if ((0, _checkCharactersPresent.default)(orgName, vulnerableCharacters)) {
                        this.message = vulnerableCharacters.toString() + ' are not allowed';
                        return false;
                    }

                    return true;
                },

                message: 'Invalid organization name'
            }],
            organizationAddress: [{ required: true, message: "Organization address is required!" }, {
                validator: function validator(orgName) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    if ((0, _checkCharactersPresent.default)(orgName, vulnerableCharacters)) {
                        this.message = vulnerableCharacters.toString() + ' are not allowed';
                        return false;
                    }

                    return true;
                },

                message: 'Invalid organization address'
            }],
            startedYear: [{ required: true, message: "Started year is required!" }, {
                validator: function validator(year) {
                    if (isNaN(year)) {
                        return false;
                    }
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

                formData.set('email', formData.get('email').toLowerCase());
                var password = formData.get('password');
                var confirmPassword = formData.get('confirmPassword');
                var isPasswordMatches = password === confirmPassword;
                if (!isPasswordMatches) {
                    validationErrors['confirmPassword'] = "Confirmation password not matching";
                }
                if (hasErrors || !isPasswordMatches) {
                    this.setErrors(validationErrors);
                    return;
                }
                this.get('authenticationService').register(formData);

                this.transitionToRoute('login');
            }
        }
    });
});
define('tournament-management-system/controllers/tournaments', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Controller.extend({
        authenticationService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            return this.get('authenticationService').userInfo;
        })
    });
});
define('tournament-management-system/controllers/tournaments/index', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Controller.extend({
        messageQueueService: Ember.inject.service(),
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        isCancelPopupOpen: false,
        selectedTournament: {},
        actions: {
            setIsCancelPopupOpen: function setIsCancelPopupOpen(value) {
                this.set('isCancelPopupOpen', value);
            },
            setSelectedTournament: function setSelectedTournament(tournament) {
                this.set('selectedTournament', tournament);
            },
            searchTournaments: function searchTournaments(searchValue) {
                var _this = this;

                var orgId = this.get('userInfo').organizationId;

                if (orgId === null || orgId === undefined) {
                    this.get('authenticationService').logout();
                    this.transitionTo('login');
                    return;
                }

                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments?filter_tournament=' + searchValue;

                $.ajax({
                    method: "GET",
                    url: apiURL,
                    accepts: {
                        'json': 'application/json'
                    },
                    dataType: 'json'
                }).then(function (response, textStatus, xqXHR) {
                    _this.set('tournaments', response.data);
                }).catch(function (err) {
                    _this.get('messageQueueService').addPopupMessage({
                        message: err.message,
                        level: 4
                    });
                });
            },
            cancelTournament: function cancelTournament() {
                var _this2 = this;

                var messageQueueService = this.get('messageQueueService');
                var tournament = this.get('selectedTournament');
                if (tournament === null || tournament.tournamentId === null || tournament.tournamentId === undefined) {
                    messageQueueService.addPopupMessage({
                        message: 'No tournament selected',
                        level: 2
                    });
                    return;
                }

                var userInfo = this.get('userInfo');
                if (+userInfo.role !== 1 && +userInfo.role !== 2) {
                    messageQueueService.addPopupMessage({
                        message: "Cannot perform this operation",
                        level: 2
                    });
                    return;
                }

                var tournamentId = tournament.tournamentId;
                var orgId = userInfo.organizationId;
                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournamentId;

                $.ajax({
                    method: 'PUT',
                    url: apiURL,
                    headers: {
                        'Tms-Tournament-Update-Option': '0'
                    },
                    data: JSON.stringify({
                        tournamentData: {
                            tournamentStatus: 3
                        }
                    }),
                    dataType: "json",
                    contentType: "application/json",
                    accepts: {
                        json: "application/json"
                    },
                    processData: false
                }).then(function (data, textStatus, jqXHR) {
                    _this2.get('target').router.getHandler('tournaments.index').refresh();
                    _this2.set('isCancelPopupOpen', false);
                    messageQueueService.addPopupMessage({
                        message: data.message,
                        level: 1
                    });
                }).catch(function (err) {
                    console.log(err);
                    var authStatus = err.getResponseHeader('Tms-Auth-Status');
                    if (authStatus === '1') {
                        messageQueueService.addPopupMessage({
                            message: "User is not authorized",
                            level: 3
                        });
                        _this2.get('authenticationService').logout();
                        _this2.transitionToRoute('index');
                    } else if (+err.status === 401 || +err.status === 403) {
                        messageQueueService.addPopupMessage({
                            message: "User is not authorized",
                            level: 3
                        });
                    } else {
                        messageQueueService.addPopupMessage({
                            message: "Something went wrong",
                            level: 3
                        });
                    }
                });
            }
        }
    });
});
define('tournament-management-system/controllers/tournaments/new', ['exports', 'tournament-management-system/utils/date-time-to-mills', 'tournament-management-system/utils/hash-set', 'tournament-management-system/utils/check-date-valid', 'tournament-management-system/utils/form-validator'], function (exports, _dateTimeToMills, _hashSet, _checkDateValid, _formValidator3) {
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
        messageQueueService: Ember.inject.service(),
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        validationConfig: {
            tournamentName: [{ required: true, message: "Tournament name is required!" }, { minLength: 3, message: "Tournament name must be atleast 3 characters long" }, { maxLength: 50, message: "Tournament name must be less than 50 characters" }, {
                validator: function validator(venue) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    var _iteratorNormalCompletion = true;
                    var _didIteratorError = false;
                    var _iteratorError = undefined;

                    try {
                        for (var _iterator = venue[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                            var ch = _step.value;

                            if (vulnerableCharacters.contains(ch)) {
                                this.message = vulnerableCharacters.toString() + ' are not allowed';
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

                    return true;
                },

                message: 'These characters are not allowed'
            }],
            tournamentdate: [{
                validator: function validator(date) {
                    if (!date) {
                        return true;
                    }

                    return (0, _checkDateValid.default)(date);
                },

                message: "Provided date is not valid format"
            }],
            tournamentVenue: [{
                validator: function validator(venue) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    var _iteratorNormalCompletion2 = true;
                    var _didIteratorError2 = false;
                    var _iteratorError2 = undefined;

                    try {
                        for (var _iterator2 = venue[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
                            var ch = _step2.value;

                            if (vulnerableCharacters.contains(ch)) {
                                this.message = vulnerableCharacters.toString() + ' are not allowed';
                                return false;
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

                    return true;
                },

                message: 'Invalid venue'
            }],
            maxParticipation: [{
                validator: function validator(count) {
                    if (isNaN(count)) {
                        this.message = "Limit should be number";
                        return false;
                    }
                    count = +count;
                    return count > 0;
                },

                message: "Limit should be atleast 1"
            }],
            registrationStartDate: [{ required: true, message: "Opening date is required!" }, {
                validator: function validator(date) {
                    if (!date) {
                        return false;
                    }

                    return (0, _checkDateValid.default)(date);
                },

                message: "Provided date is not valid format (dd/mm/yyyy)"
            }],
            registrationEndDate: [{ required: true, message: "Closing date is required!" }, {
                validator: function validator(date) {
                    if (!date) {
                        return false;
                    }
                    return (0, _checkDateValid.default)(date);
                },

                message: "Provided date is not valid format (dd/mm/yyyy)"
            }],
            sportName: [{ required: true, message: "Sport name is required" }, {
                validator: function validator(venue) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    var _iteratorNormalCompletion3 = true;
                    var _didIteratorError3 = false;
                    var _iteratorError3 = undefined;

                    try {
                        for (var _iterator3 = venue[Symbol.iterator](), _step3; !(_iteratorNormalCompletion3 = (_step3 = _iterator3.next()).done); _iteratorNormalCompletion3 = true) {
                            var ch = _step3.value;

                            if (vulnerableCharacters.contains(ch)) {
                                this.message = vulnerableCharacters.toString() + ' are not allowed';
                                return false;
                            }
                        }
                    } catch (err) {
                        _didIteratorError3 = true;
                        _iteratorError3 = err;
                    } finally {
                        try {
                            if (!_iteratorNormalCompletion3 && _iterator3.return) {
                                _iterator3.return();
                            }
                        } finally {
                            if (_didIteratorError3) {
                                throw _iteratorError3;
                            }
                        }
                    }

                    return true;
                },

                message: 'Invalid sport'
            }],
            sportType: [{ required: true, message: "Sport type is required" }, {
                validator: function validator(type) {
                    return +type === 0 || +type === 1;
                },

                message: 'Sport type can be only individual or team'
            }],
            teamSize: [{ required: true, message: "Team size is required" }, {
                validator: function validator(size) {
                    if (isNaN(size)) {
                        this.message = "Team size should be number";
                        return false;
                    }
                    size = +size;
                    return size > 0;
                },

                message: 'Team size should be atleast 1'
            }]
        },
        validationErrors: {},
        setErrors: function setErrors(validationErrors) {
            this.set('validationErrors', validationErrors);
        },
        cleanUp: function cleanUp() {
            this.setErrors({});
        },
        createTournament: function createTournament(formData) {
            var _this = this;

            var tournamentData = {};
            var sportData = {};

            var _arr = ['tournamentName', 'tournamentVenue', 'maxParticipation'];
            for (var _i = 0; _i < _arr.length; _i++) {
                var key = _arr[_i];
                if (!formData.get(key)) continue;
                tournamentData[key] = formData.get(key);
            }

            if (formData.get('tournamentDate')) {
                tournamentData['tournamentDate'] = (0, _dateTimeToMills.default)(formData.get('tournamentDate'));
            }
            tournamentData['registrationStartDate'] = (0, _dateTimeToMills.default)(formData.get('registrationStartDate'));
            tournamentData['registrationEndDate'] = (0, _dateTimeToMills.default)(formData.get('registrationEndDate'));

            var _arr2 = ['sportName', 'sportType', 'teamSize'];
            for (var _i2 = 0; _i2 < _arr2.length; _i2++) {
                var _key = _arr2[_i2];
                sportData[_key] = formData.get(_key);
            }

            var userInfo = this.get('userInfo');
            var orgId = userInfo.organizationId;
            var config = this.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments';

            $.ajax({
                method: 'POST',
                url: apiURL,
                data: JSON.stringify({ tournamentData: tournamentData, sportData: sportData }),
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                },
                processData: false
            }).then(function (date, textStatus, jqXHR) {
                console.log(data);
            }).catch(function (err) {
                console.log(err);
                var authStatus = err.getResponseHeader('Tms-Auth-Status');
                if (authStatus === '1') {
                    _this.get('authenticationService').logout();
                    _this.transitionToRoute('index');
                }
            });
        },

        actions: {
            createNewTournament: function createNewTournament(event) {
                event.preventDefault();

                var formData = new FormData(event.target);

                var _formValidator = (0, _formValidator3.default)(formData, this.get('validationConfig')),
                    _formValidator2 = _slicedToArray(_formValidator, 2),
                    validationErrors = _formValidator2[0],
                    hasErrors = _formValidator2[1];

                var validEndDate = (0, _dateTimeToMills.default)(formData.get('registrationStartDate')) < (0, _dateTimeToMills.default)(formData.get('registrationEndDate'));
                if (!validEndDate) {
                    validationErrors.registrationEndDate = 'Closing date should be after the opening date';
                }
                if (hasErrors || !validEndDate) {
                    this.setErrors(validationErrors);
                    return;
                }
                this.createTournament(formData);

                this.transitionToRoute('tournaments');
            }
        }
    });
});
define('tournament-management-system/controllers/tournaments/tournament', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Controller.extend({
        // Sercices
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        messageQueueService: Ember.inject.service(),

        // User info
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),

        // State config
        contestantsPage: 0,
        schedulesPage: 0,
        limitPerPage: 20,
        tournamentFormType: 0,
        tournamentScheduleFormType: 0,
        eventPageType: 0,

        // API Calls
        fetchRegisteredContestants: function fetchRegisteredContestants() {
            var _this = this;

            var includeLimit = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : true;

            var messageQueueService = this.get('messageQueueService');

            var tournament = this.get('tournament');
            var userInfo = this.get('userInfo');
            var orgId = userInfo.organizationId;
            var config = this.get('envService');
            var participationType = tournament.sportType === 0 ? 'participants' : 'teams';
            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournament.tournamentId + '/' + participationType + '?' + (includeLimit ? 'page=' + this.get('contestantsPage') + '&limit=' + this.get('limitPerPage') : 'exclude_limit=true');

            Ember.$.ajax({
                method: 'GET',
                url: apiURL,
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                }
            }).then(function (data, textStatus, jqXHR) {
                _this.set(participationType, data.data);
            }).catch(function (err) {
                console.log(err);
                messageQueueService.addPopupMessage({
                    message: err.message,
                    level: 3
                });
            });
        },
        fetchTournamentEvents: function fetchTournamentEvents() {
            var _this2 = this;

            var includeLimit = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : true;

            var messageQueueService = this.get('messageQueueService');

            var tournament = this.get('tournament');
            var userInfo = this.get('userInfo');
            var orgId = userInfo.organizationId;
            var config = this.get('envService');

            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournament.tournamentId + '/events';

            Ember.$.ajax({
                method: 'GET',
                url: apiURL,
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                }
            }).then(function (response, textStatus, jqXHR) {
                _this2.set('schedules', response.data);
            }).catch(function (err) {
                console.log(err);
                messageQueueService.addPopupMessage({
                    message: err.responseJSON.message,
                    level: 3
                });
            });
        },
        registerTournament: function registerTournament(thisRef, formData, teamRegistrationType) {
            var messageQueueService = thisRef.get('messageQueueService');

            var tournament = thisRef.get('tournament');
            var userInfo = thisRef.get('userInfo');
            var orgId = userInfo.organizationId;
            var config = thisRef.get('envService');

            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournament.tournamentId + '/';
            var requestData = {};

            if (tournament.sportType === 0) {
                apiURL += 'participants';
                requestData.userId = userInfo.userId;
            } else if (teamRegistrationType === 0) {
                apiURL += 'teams';
                requestData.teamLeaderId = userInfo.userId;
                requestData.teamName = formData.get('teamName');
            } else {
                apiURL += 'teams/' + formData.get('teamId') + '/members';
                requestData.userId = userInfo.userId;
            }

            Ember.$.ajax({
                method: 'POST',
                url: apiURL,
                data: JSON.stringify(requestData),
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                },
                processData: false
            }).then(function (data, textStatus, jqXHR) {
                console.log(data, textStatus, jqXHR);
                thisRef.get('target').router.getHandler('tournaments.tournament').refresh();
                messageQueueService.addPopupMessage({
                    message: "Tournament registered successfully",
                    level: 1
                });
            }).catch(function (err) {
                console.log(err);
                messageQueueService.addPopupMessage({
                    message: err.responseJSON.message,
                    level: 3
                });
            });
        },
        unregisterTournament: function unregisterTournament(thisRef, teamRegistrationType) {
            var messageQueueService = thisRef.get('messageQueueService');

            var tournament = thisRef.get('tournament');
            var userInfo = thisRef.get('userInfo');
            var orgId = userInfo.organizationId;
            var config = thisRef.get('envService');
            var userParticipation = thisRef.get('userParticipation');

            if (!userParticipation.userRegistered) {
                messageQueueService.addPopupMessage({
                    message: "Not yet registered",
                    level: 2
                });
                return;
            }

            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournament.tournamentId + '/';
            if (tournament.sportType === 0) {
                apiURL += 'participants/' + userParticipation.participantId;
            } else if (teamRegistrationType === 0) {
                apiURL += 'teams/' + userParticipation.teamId;
            } else {
                apiURL += 'teams/' + userParticipation.teamId + '/member/' + userParticipation.teamMemberId;
            }

            Ember.$.ajax({
                method: 'DELETE',
                url: apiURL,
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                }
            }).then(function (data, textStatus, jqXHR) {
                console.log(data, textStatus, jqXHR);
                thisRef.get('target').router.getHandler('tournaments.tournament').refresh();
                messageQueueService.addPopupMessage({
                    message: "Tournament unregistered successfully",
                    level: 1
                });
            }).catch(function (err) {
                console.log(err);
                messageQueueService.addPopupMessage({
                    message: err.message,
                    level: 3
                });
            });
        },
        updateDetails: function updateDetails(thisRef, formData, teamRegistrationType) {
            var messageQueueService = thisRef.get('messageQueueService');

            var tournament = thisRef.get('tournament');
            var userInfo = thisRef.get('userInfo');
            var orgId = userInfo.organizationId;
            var config = thisRef.get('envService');
            var userParticipation = thisRef.get('userParticipation');

            if (!userParticipation.userRegistered) {
                messageQueueService.addPopupMessage({
                    message: "Not yet registered",
                    level: 2
                });
                return;
            }

            if (tournament.sportType !== 1 || userParticipation.teamLeaderId !== userInfo.userId) {
                return;
            }

            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournament.tournamentId + '/teams/' + userParticipation.teamId;

            var requestData = {};
            var newTeamName = formData.get('teamName');
            var newTeamLeaderId = formData.get('teamLeaderId');

            if (newTeamName) {
                requestData.teamName = newTeamName;
            }
            if (newTeamLeaderId) {
                requestData.teamLeaderId = newTeamLeaderId;
            }

            Ember.$.ajax({
                method: 'PUT',
                url: apiURL,
                data: JSON.stringify(requestData),
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                },
                processData: false
            }).then(function (data, textStatus, jqXHR) {
                console.log(data, textStatus, jqXHR);
                thisRef.get('target').router.getHandler('tournaments.tournament').refresh();
                messageQueueService.addPopupMessage({
                    message: "Details updated successfully",
                    level: 1
                });
            }).catch(function (err) {
                console.log(err);
                messageQueueService.addPopupMessage({
                    message: err.message,
                    level: 3
                });
            });
        },


        // controller clean up on route change
        cleanUp: function cleanUp() {
            this.set('contestantsPage', 0);
            this.set('schedulesPage', 0);
            this.set('tournamentFormType', 0);
            this.set('tournamentScheduleFormType', 0);
            this.set('eventPageType', 0);
            this.set('schedules', null);
        },


        // Actions
        actions: {
            refreshModel: function refreshModel() {
                this.get('target').router.getHandler('tournaments.tournament').refresh();
            },
            setEventPageType: function setEventPageType(value) {
                if (this.get('eventPageType') === value) return;
                if (value === 1 && !this.get('schedules')) {
                    this.fetchTournamentEvents(false);
                }
                this.set('eventPageType', value);
            },
            setTournamentFormType: function setTournamentFormType(value) {
                if (+this.get('tournament').sportType === 1) {
                    if (+this.get('tournamentFormType') === 1 && value === 0) {
                        this.fetchRegisteredContestants();
                    } else if (value === 1) {
                        this.fetchRegisteredContestants(false);
                    }
                }
                this.set('tournamentFormType', value);
            },
            setTournamentScheduleFormType: function setTournamentScheduleFormType(value) {
                this.set('tournamentScheduleFormType', value);
            },
            navigateNextPage: function navigateNextPage() {
                var currPage = this.get('contestantsPage');
                var totalReg = this.get('tournament.registeredCount');
                var limitPerPage = this.get('limitPerPage');

                if ((currPage + 1) * limitPerPage >= totalReg) {
                    return;
                }

                this.set('contestantsPage', currPage + 1);
                this.get('fetchRegisteredContestants')();
            },
            navigatePreviousPage: function navigatePreviousPage() {
                var currPage = this.get('contestantsPage');

                if (currPage === 0) {
                    return;
                }

                this.set('contestantsPage', currPage - 1);
                this.get('fetchRegisteredContestants')();
            },
            handleTournamentRegistration: function handleTournamentRegistration(_ref) {
                var teamRegistrationType = _ref.teamRegistrationType,
                    formData = _ref.formData;


                this.get('registerTournament')(this, formData, teamRegistrationType);
            },
            handleTournamentUnregistration: function handleTournamentUnregistration(_ref2) {
                var teamRegistrationType = _ref2.teamRegistrationType;


                this.get('unregisterTournament')(this, teamRegistrationType);
            },
            handleUpdateTournamentRegistration: function handleUpdateTournamentRegistration(_ref3) {
                var teamRegistrationType = _ref3.teamRegistrationType,
                    formData = _ref3.formData;


                this.get('updateDetails')(this, formData, teamRegistrationType);
            },
            searchContestants: function searchContestants(searchValue) {
                var _this3 = this;

                var orgId = this.get('userInfo').organizationId;
                var tournament = this.get('tournament');

                var participationType = null;
                var filterParticipationType = null;

                if (+tournament.sportType === 0) {
                    participationType = 'participants';
                    filterParticipationType = 'filter_participantname';
                } else if (+tournament.sportType === 1) {
                    participationType = 'teams';
                    filterParticipationType = 'filter_teamname';
                }

                if (orgId === null || orgId === undefined) {
                    this.get('authenticationService').logout();
                    this.transitionTo('login');
                    return;
                }

                var config = this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournament.tournamentId + '/' + participationType + '?' + filterParticipationType + '=' + searchValue + '&exclude_limit=true';

                Ember.$.ajax({
                    method: "GET",
                    url: apiURL,
                    accepts: {
                        'json': 'application/json'
                    },
                    dataType: 'json'
                }).then(function (response, textStatus, xqXHR) {
                    _this3.set(participationType, response.data);
                }).catch(function (err) {
                    _this3.get('messageQueueService').addPopupMessage({
                        message: err.message,
                        level: 4
                    });
                });
            }
        }
    });
});
define('tournament-management-system/controllers/tournaments/tournament/edit', ['exports', 'tournament-management-system/utils/check-characters-present', 'tournament-management-system/utils/form-validator', 'tournament-management-system/utils/hash-set', 'tournament-management-system/utils/check-date-valid', 'tournament-management-system/utils/date-time-to-mills'], function (exports, _checkCharactersPresent, _formValidator3, _hashSet, _checkDateValid, _dateTimeToMills) {
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
        dataPersistanceService: Ember.inject.service(),
        tournament: Ember.computed('dataPersistanceService.data', function () {
            return this.get('dataPersistanceService').data;
        }),
        messageQueueService: Ember.inject.service(),
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        validationConfig: {
            tournamentName: [{ required: true, message: "Tournament name is required!" }, { minLength: 3, message: "Tournament name must be atleast 3 characters long" }, { maxLength: 50, message: "Tournament name must be less than 50 characters" }, {
                validator: function validator(tournamentName) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    if ((0, _checkCharactersPresent.default)(tournamentName, vulnerableCharacters)) {
                        this.message = vulnerableCharacters.toString() + ' are not allowed';
                        return false;
                    }

                    return true;
                },

                message: 'These characters are not allowed'
            }],
            tournamentdate: [{
                validator: function validator(date) {
                    if (!date) {
                        return true;
                    }

                    return (0, _checkDateValid.default)(date);
                },

                message: "Provided date is not valid format"
            }],
            tournamentVenue: [{
                validator: function validator(venue) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    if ((0, _checkCharactersPresent.default)(venue, vulnerableCharacters)) {
                        this.message = vulnerableCharacters.toString() + ' are not allowed';
                        return false;
                    }

                    return true;
                },

                message: 'Invalid venue'
            }],
            maxParticipation: [{
                validator: function validator(count) {
                    if (isNaN(count)) {
                        this.message = "Limit should be number";
                        return false;
                    }
                    count = +count;
                    return count > 0;
                },

                message: "Limit should be atleast 1"
            }],
            registrationStartDate: [{ required: true, message: "Opening date is required!" }, {
                validator: function validator(date) {
                    if (!date) {
                        return false;
                    }

                    return (0, _checkDateValid.default)(date);
                },

                message: "Provided date is not valid format (dd/mm/yyyy)"
            }],
            registrationEndDate: [{ required: true, message: "Closing date is required!" }, {
                validator: function validator(date) {
                    if (!date) {
                        return false;
                    }
                    return (0, _checkDateValid.default)(date);
                },

                message: "Provided date is not valid format (dd/mm/yyyy)"
            }],
            sportName: [{ required: true, message: "Sport name is required" }, {
                validator: function validator(sportName) {
                    var vulnerableCharacters = new _hashSet.default('<', '>');

                    if ((0, _checkCharactersPresent.default)(sportName, vulnerableCharacters)) {
                        this.message = vulnerableCharacters.toString() + ' are not allowed';
                        return false;
                    }

                    return true;
                },

                message: 'Invalid sport'
            }],
            sportType: [{ required: true, message: "Sport type is required" }, {
                validator: function validator(type) {
                    return +type === 0 || +type === 1;
                },

                message: 'Sport type can be only individual or team'
            }],
            teamSize: [{ required: true, message: "Team size is required" }, {
                validator: function validator(size) {
                    if (isNaN(size)) {
                        this.message = "Team size should be number";
                        return false;
                    }
                    size = +size;
                    return size > 0;
                },

                message: 'Team size should be atleast 1'
            }]
        },
        validationErrors: {},
        setErrors: function setErrors(validationErrors) {
            this.set('validationErrors', validationErrors);
        },
        cleanUp: function cleanUp() {
            this.setErrors({});
        },
        updateTournament: function updateTournament(formData) {
            var _this = this;

            var tournament = this.get('tournament');
            var tournamentData = {};
            var sportData = {};

            var _arr = ['tournamentName', 'tournamentVenue', 'maxParticipation'];
            for (var _i = 0; _i < _arr.length; _i++) {
                var key = _arr[_i];
                if (!formData.get(key)) continue;
                tournamentData[key] = formData.get(key);
            }

            if (formData.get('tournamentDate')) {
                tournamentData['tournamentDate'] = (0, _dateTimeToMills.default)(formData.get('tournamentDate'));
            }
            tournamentData['registrationStartDate'] = (0, _dateTimeToMills.default)(formData.get('registrationStartDate'));
            tournamentData['registrationEndDate'] = (0, _dateTimeToMills.default)(formData.get('registrationEndDate'));

            var _arr2 = ['sportName', 'sportType', 'teamSize'];
            for (var _i2 = 0; _i2 < _arr2.length; _i2++) {
                var _key = _arr2[_i2];
                sportData[_key] = formData.get(_key);
            }

            var tournamentId = tournament.tournamentId;

            var userInfo = this.get('userInfo');
            var orgId = userInfo.organizationId;
            var config = this.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournamentId;

            if (+tournament.registeredCount > 0 || +tournament.teamSize > sportData.teamSize) {
                // 
            }

            $.ajax({
                method: 'PUT',
                url: apiURL,
                headers: {
                    'Tms-Tournament-Update-Option': ''
                },
                data: JSON.stringify({ tournamentData: tournamentData, sportData: sportData }),
                dataType: "json",
                contentType: "application/json",
                accepts: {
                    json: "application/json"
                },
                processData: false
            }).then(function (date, textStatus, jqXHR) {
                console.log(data);
            }).catch(function (err) {
                console.log(err);
                var authStatus = err.getResponseHeader('Tms-Auth-Status');
                if (authStatus === '1') {
                    _this.get('authenticationService').logout();
                    _this.transitionToRoute('index');
                }
            });
        },

        actions: {
            editTournament: function editTournament(event) {
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
                this.updateTournament(formData);

                this.transitionToRoute('tournaments');
            }
        }
    });
});
define('tournament-management-system/helpers/and', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.and = and;
  function and(params) {
    var _iteratorNormalCompletion = true;
    var _didIteratorError = false;
    var _iteratorError = undefined;

    try {
      for (var _iterator = params[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
        var logic = _step.value;

        if (!logic) {
          return logic;
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

    return true;
  }

  exports.default = Ember.Helper.helper(and);
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
    var _ref2 = _slicedToArray(_ref, 2),
        startMilliseconds = _ref2[0],
        endMilliseconds = _ref2[1];

    var currentTime = new Date().getTime();
    if (currentTime < startMilliseconds) {
      return 'Not Started';
    }
    var differenceInMillis = endMilliseconds - currentTime;
    if (differenceInMillis <= 0) {
      return 'Closed';
    }
    var leftDays = Math.floor(differenceInMillis / (1000 * 60 * 60 * 24));
    return leftDays + ' day' + (leftDays == 1 ? '' : 's') + ' left';
  }

  exports.default = Ember.Helper.helper(calculateDeadline);
});
define('tournament-management-system/helpers/concat', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.concat = concat;
  function concat(params) {
    return params.join('');
  }

  exports.default = Ember.Helper.helper(concat);
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
define('tournament-management-system/helpers/instance-gt', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.instanceGt = instanceGt;

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

  function instanceGt(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        millis = _ref2[0];

    return millis < Date.now();
  }

  exports.default = Ember.Helper.helper(instanceGt);
});
define('tournament-management-system/helpers/instance-lt', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.instanceLt = instanceLt;

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

  function instanceLt(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        millis = _ref2[0];

    return millis > Date.now();
  }

  exports.default = Ember.Helper.helper(instanceLt);
});
define('tournament-management-system/helpers/is-empty', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.isEmpty = isEmpty;

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

  function isEmpty(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        array = _ref2[0];

    return !array || array.length === 0;
  }

  exports.default = Ember.Helper.helper(isEmpty);
});
define('tournament-management-system/helpers/millis-to-date-time', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.millisToDateTime = millisToDateTime;

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

  function millisToDateTime(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        millis = _ref2[0];

    if (!millis) {
      return 'Invalid Date';
    }
    return new Date(millis).toLocaleDateString() + ' ' + new Date(millis).toLocaleTimeString('en-US', { hour12: true });
  }

  exports.default = Ember.Helper.helper(millisToDateTime);
});
define('tournament-management-system/helpers/millis-to-date', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.millisToDate = millisToDate;

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

  function millisToDate(_ref) {
    var _ref2 = _slicedToArray(_ref, 2),
        millis = _ref2[0],
        message = _ref2[1];

    if (!millis) {
      return message !== null && message !== undefined ? message : 'Not specified';
    }
    return new Date(millis).toLocaleDateString();
  }

  exports.default = Ember.Helper.helper(millisToDate);
});
define('tournament-management-system/helpers/n-eq', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.nEq = nEq;

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

  function nEq(_ref) {
    var _ref2 = _slicedToArray(_ref, 2),
        value1 = _ref2[0],
        value2 = _ref2[1];

    return value1 !== value2;
  }

  exports.default = Ember.Helper.helper(nEq);
});
define('tournament-management-system/helpers/object', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.object = object;
  function object(entries) {
    var obj = {};
    var n = entries.length;
    for (var i = 0; i < n; i += 2) {
      obj[entries[i]] = entries[i + 1];
    }
    return obj;
  }

  exports.default = Ember.Helper.helper(object);
});
define('tournament-management-system/helpers/or', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.or = or;
  function or(params) {
    var _iteratorNormalCompletion = true;
    var _didIteratorError = false;
    var _iteratorError = undefined;

    try {
      for (var _iterator = params[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
        var logic = _step.value;

        if (logic) {
          return logic;
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

    return false;
  }

  exports.default = Ember.Helper.helper(or);
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
define('tournament-management-system/helpers/prepend-root', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.prependRoot = prependRoot;

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

  function prependRoot(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        url = _ref2[0];

    if (!url) {
      return '';
    }
    if (url.startsWith("http://") || url.startsWith("https://")) {
      return url;
    }
    return '/tms/client/assets/' + url;
  }

  exports.default = Ember.Helper.helper(prependRoot);
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
    var _ref2 = _slicedToArray(_ref, 2),
        sportType = _ref2[0],
        teamSize = _ref2[1];

    if (sportType === 0) {
      return 'Individual';
    }
    if (!teamSize) {
      return 'Team';
    }
    return 'Team - ' + teamSize + ' members';
  }

  exports.default = Ember.Helper.helper(sportType);
});
define('tournament-management-system/helpers/test-logger', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.testLogger = testLogger;

  function _toConsumableArray(arr) {
    if (Array.isArray(arr)) {
      for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
        arr2[i] = arr[i];
      }

      return arr2;
    } else {
      return Array.from(arr);
    }
  }

  function testLogger(values) {
    var _console;

    (_console = console).log.apply(_console, ["Logger"].concat(_toConsumableArray(values)));
    return 'Logger';
  }

  exports.default = Ember.Helper.helper(testLogger);
});
define('tournament-management-system/helpers/tournament-event-round', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.tournamentEventRound = tournamentEventRound;

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

  function tournamentEventRound(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        value = _ref2[0];

    switch (+value) {
      case 0:
        return 'Qualifiers';
      case 1:
        return 'Play-Off';
      case 2:
        return 'Quarter-Finals';
      case 3:
        return 'Semi-Finals';
      case 4:
        return 'Finals';
      default:
        return 'Match';
    }
  }

  exports.default = Ember.Helper.helper(tournamentEventRound);
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
define('tournament-management-system/helpers/truncate-name', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.truncateName = truncateName;

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

  function truncateName(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        value = _ref2[0];

    if (!value) {
      return value;
    }
    return value.split(' ')[0];
  }

  exports.default = Ember.Helper.helper(truncateName);
});
define('tournament-management-system/helpers/user-role', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.userRole = userRole;

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

  function userRole(_ref) {
    var _ref2 = _slicedToArray(_ref, 1),
        role = _ref2[0];

    switch (role) {
      case 0:
        return 'Member';
      case 1:
        return 'Organization Admin';
      case 2:
        return 'App Admin';
      default:
        return 'No Role';
    }
  }

  exports.default = Ember.Helper.helper(userRole);
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
    this.route('profile');
    this.route('tournaments', function () {
      this.route('tournament', { path: ':tournament_id' }, function () {
        this.route('edit');
      });
      this.route('new');
    });
    this.route('organizations', function () {
      this.route('organization', { path: ':organization_id' }, function () {
        this.route('user', { path: 'users/:user_id' });
      });
    });
    this.route('access-denied');

    this.route('not-found', { path: '/*' });
  });

  exports.default = Router;
});
define('tournament-management-system/routes/access-denied', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.Route.extend({});
});
define('tournament-management-system/routes/dashboard', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend({
        authenticationService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            return this.get('authenticationService').userInfo;
        }),
        isLoggedIn: Ember.computed('authenticationService.isLoggedIn', function () {
            return this.get('authenticationService').isLoggedIn;
        }),
        beforeModel: function beforeModel(transition) {
            var isLoggedIn = this.get('isLoggedIn');
            if (isLoggedIn === false) {
                this.transitionTo('login');
                return;
            }
        }
    });
});
define('tournament-management-system/routes/index', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend({
        authenticationService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            return this.get('authenticationService').userInfo;
        }),
        isLoggedIn: Ember.computed('authenticationService.isLoggedIn', function () {
            return this.get('authenticationService').isLoggedIn;
        }),
        beforeModel: function beforeModel(transition) {
            var isLoggedIn = this.get('isLoggedIn');
            if (isLoggedIn === true) {
                this.transitionTo('dashboard');
                return;
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
                self.transitionTo('dashboard');
            });
        },

        actions: {
            willTransition: function willTransition(transition) {
                this.controllerCleanup();
            }
        }
    });
});
define('tournament-management-system/routes/not-found', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.Route.extend({});
});
define('tournament-management-system/routes/organizations', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.Route.extend({});
});
define('tournament-management-system/routes/organizations/index', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend({
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
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

            if (+this.get('userInfo').role !== 2) {
                this.transitionTo('access-denied');
                return;
            }

            this.get('loaderService').setIsLoading(true);
        },
        model: function model() {
            var _this = this;

            var config = this.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs';
            console.log($.ajax());
            return $.ajax({
                method: 'GET',
                url: apiURL,
                accepts: {
                    'json': 'application/json'
                },
                dataType: 'json'
            }).then(function (response) {
                return response.data;
            }).catch(function (err) {
                var authStatus = err.getResponseHeader('Tms-Auth-Status');
                if (authStatus === '1') {
                    _this.get('authenticationService').logout();
                    _this.transitionToRoute('index');
                    return;
                }
                if (err.status === 401 || err.status === 403) {
                    _this.transitionTo('access-denied');
                    return;
                }
                console.log("error", err);
            }).always(function () {
                _this.get('loaderService').setIsLoading(false);
            });
        },

        actions: {
            error: function error() {
                console.log("error occured!");
                return true;
            }
        },
        setupController: function setupController(controller, model) {
            controller.set('organizations', model);
        }
    });
});
define('tournament-management-system/routes/organizations/organization', ['exports'], function (exports) {
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

    exports.default = Ember.Route.extend({
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        beforeModel: function beforeModel(transition) {

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

            var params = transition.params['organizations.organization'];
            var organizationId = params["organization_id"];

            if (+userInfo.role !== 2 && +userInfo.organizationId !== +organizationId) {
                this.transitionTo('access-denied');
                return;
            }

            this.get('loaderService').setIsLoading(true);
        },
        model: function model(params) {
            var _this = this;

            var organizationId = params["organization_id"];
            var config = this.get('envService');
            var orgApiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + organizationId;
            var usersApiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + organizationId + '/users';

            var organizationRequest = $.ajax({
                method: 'GET',
                url: orgApiURL,
                accepts: {
                    'json': 'application/json'
                },
                dataType: 'json'
            }).then(function (data, textStatus, jqXHR) {
                return {
                    status: jqXHR.status,
                    data: data.data,
                    message: data.message
                };
            }).then(function (res) {
                return res.data;
            }).catch(function (err) {
                var authStatus = err.getResponseHeader('Tms-Auth-Status');
                if (authStatus === '1') {
                    _this.get('authenticationService').logout();
                    _this.transitionTo('index');
                    return;
                }
                if (err.status === 401 || err.status === 403) {
                    _this.transitionTo('access-denied');
                    return;
                }
                throw err;
            });

            var usersRequest = $.ajax({
                method: 'GET',
                url: usersApiURL,
                accepts: {
                    'json': 'application/json'
                }
            }).then(function (data, textStatus, jqXHR) {
                return {
                    status: jqXHR.status,
                    data: data.data,
                    message: data.message
                };
            }).then(function (res) {
                return res.data;
            }).catch(function (err) {
                var authStatus = err.getResponseHeader('X-Auth-Status');
                if (authStatus === '1') {
                    _this.get('authenticationService').logout();
                    _this.transitionToRoute('index');
                    return;
                }
                if (err.status === 401 || err.status === 403) {
                    _this.transitionTo('access-denied');
                    return;
                }
                throw err;
            });

            return Promise.all([organizationRequest, usersRequest]).then(function (response) {
                var _response = _slicedToArray(response, 2),
                    organization = _response[0],
                    users = _response[1];

                var admin = users.find(function (user) {
                    return user.role === 1;
                });
                return {
                    organization: organization,
                    users: users,
                    admin: admin
                };
            }).catch(function (err) {
                console.log(err);
            }).finally(function () {
                _this.get('loaderService').setIsLoading(false);
            });
        },
        setupController: function setupController(controller, model) {
            this.get('loaderService').setIsLoading(false);
            controller.set('organization', model.organization);
            controller.set('users', model.users);
            controller.set('admin', model.admin);
        }
    });
});
define('tournament-management-system/routes/organizations/organization/user', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.Route.extend({});
});
define('tournament-management-system/routes/profile', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend({
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        beforeModel: function beforeModel() {},
        setupController: function setupController(controller) {
            var userInfo = this.get('userInfo');

            controller.set('user', userInfo);
        }
    });
});
define('tournament-management-system/routes/register', ['exports', 'tournament-management-system/mixins/controller-cleanup'], function (exports, _controllerCleanup) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend(_controllerCleanup.default, {
        authenticationService: Ember.inject.service(),
        beforeModel: function beforeModel() {
            var authenticationService = this.get('authenticationService');
            if (authenticationService.isLoggedIn) {
                this.transitionTo('dashboard');
            }
        },

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
define('tournament-management-system/routes/tournaments/index', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend({
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
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

            if (+this.get('userInfo').role === 2) {
                this.transitionTo('index');
                return;
            }

            if (+this.get('userInfo').role !== 0 && +this.get('userInfo').role !== 1) {
                this.get('authenticationService').logout();
                this.transitionTo('login');
                return;
            }

            this.get('loaderService').setIsLoading(true);
        },
        model: function model() {
            var _this = this;

            var orgId = this.get('userInfo').organizationId;

            if (orgId === null || orgId === undefined) {
                this.get('authenticationService').logout();
                this.transitionTo('login');
                return;
            }

            var config = this.get('envService');
            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments';

            return $.ajax({
                method: 'GET',
                url: apiURL,
                accepts: {
                    'json': 'application/json'
                },
                dataType: 'json'
            }).then(function (response, textStatus, jqXHR) {
                if (jqXHR.status === 401 || jqXHR.status === 403) {
                    _this.transitionTo('access-denied');
                }
                return response.data;
            }).catch(function (err) {
                var authStatus = err.getResponseHeader('Tms-Auth-Status');

                if (authStatus === '1') {
                    _this.get('authenticationService').logout();
                    _this.transitionTo('index');
                    return;
                }
                if (err.status === 401 || err.status === 403) {
                    _this.transitionTo('access-denied');
                    return;
                }
                console.log("error", err);
            }).always(function () {
                _this.get('loaderService').setIsLoading(false);
            });
        },
        setupController: function setupController(controller, model) {
            controller.set('tournaments', model);
        },

        actions: {
            error: function error() {
                this.get('loaderService').setIsLoading(false);
            }
        }

    });
});
define('tournament-management-system/routes/tournaments/new', ['exports', 'tournament-management-system/mixins/controller-cleanup'], function (exports, _controllerCleanup) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend(_controllerCleanup.default, {
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
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

            if (userInfo.role !== 1 && userInfo.role !== 2) {
                this.transitionTo('index');
                return;
            }
        },

        actions: {
            willTransition: function willTransition(transition) {
                this.controllerCleanup();
            }
        }
    });
});
define('tournament-management-system/routes/tournaments/tournament', ['exports', 'tournament-management-system/utils/tournament-image-fallback', 'tournament-management-system/mixins/controller-cleanup'], function (exports, _tournamentImageFallback, _controllerCleanup) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend(_controllerCleanup.default, {
        messageQueueService: Ember.inject.service(),
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        dataPersistanceService: Ember.inject.service(),
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

            this.get('loaderService').setIsLoading(true);
        },
        model: function model(params) {
            var _this = this;

            var messageQueueService = this.get('messageQueueService');

            var userInfo = this.get('userInfo');
            var orgId = +userInfo.organizationId;
            var tournamentId = +params.tournament_id;
            var config = this.get('envService');

            var apiURL = config.getEnv('BASE_API_URL') + '/api/v1/orgs/' + orgId + '/tournaments/' + tournamentId + '/contestants?include_count=true&include_tournament=true&include_user=true';

            return Ember.$.ajax({
                method: 'GET',
                url: apiURL,
                accepts: {
                    'json': 'application/json'
                },
                dataType: 'json'
            }).then(function (data, textStatus, jqXHR) {
                if (jqXHR.status === 401 || jqXHR.status === 403) {
                    _this.transitionTo('access-denied');
                }

                return data.data;
            }).catch(function (err) {
                var authStatus = err.getResponseHeader('Tms-Auth-Status');
                if (authStatus === '1') {
                    messageQueueService.addPopupMessage({
                        message: "Session expired, login again",
                        level: 0
                    });
                    _this.get('authenticationService').logout();
                    _this.transitionToRoute('index');
                    return;
                }
                if (err.status === 401 || err.status === 403) {
                    messageQueueService.addPopupMessage({
                        message: "Not allowed to perform this operation",
                        level: 3
                    });
                    _this.transitionTo('access-denied');
                    return;
                }

                console.log(err);
            }).always(function () {
                _this.get('loaderService').setIsLoading(false);
            });
        },
        setupController: function setupController(controller, model) {
            if (!model) return;
            var tournament = model.tournament;
            tournament['registeredCount'] = '' + model.count;
            tournament.tournamentPoster = (0, _tournamentImageFallback.default)(tournament.sportName);
            controller.set('tournament', tournament);
            var participationType = +tournament.sportType === 0 ? 'participants' : 'teams';
            controller.set(participationType, model[participationType]);
            var userParticipation = model.userParticipation;
            if (userParticipation.teamId !== undefined && userParticipation.teamId !== null || userParticipation.participantId !== undefined && userParticipation.participantId !== null) {
                userParticipation.userRegistered = true;
            }
            controller.set("userParticipation", userParticipation);
            this.get('dataPersistanceService').setData(tournament);
        },

        actions: {
            willTransition: function willTransition(transition) {
                this.controllerCleanup();
            }
        }
    });
});
define('tournament-management-system/routes/tournaments/tournament/edit', ['exports', 'tournament-management-system/mixins/controller-cleanup'], function (exports, _controllerCleanup) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Route.extend(_controllerCleanup.default, {
        envService: Ember.inject.service(),
        authenticationService: Ember.inject.service(),
        loaderService: Ember.inject.service(),
        userInfo: Ember.computed('authenticationService.userInfo', function () {
            var userInfo = this.get('authenticationService').userInfo;
            return userInfo || {};
        }),
        beforeModel: function beforeModel() {
            var authenticationService = this.get('authenticationService');
            if (!authenticationService.isLoggedIn) {
                this.transitionTo('login');
                return;
            }
            if (+authenticationService.userInfo.role !== 1 && +authenticationService.userInfo.role !== 2) {
                this.transitionTo('dashboard');
                return;
            }
        },

        actions: {
            willTransition: function willTransition(transition) {
                this.controllerCleanup();
            }
        }
    });
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
define('tournament-management-system/services/authentication-service', ['exports', 'tournament-management-system/utils/hash-set', 'tournament-management-system/utils/sanitize-input', 'tournament-management-system/utils/date-time-to-mills', 'tournament-management-system/utils/rsa-encrypter'], function (exports, _hashSet, _sanitizeInput, _dateTimeToMills, _rsaEncrypter) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Service.extend({
        envService: Ember.inject.service(),
        userInfo: JSON.parse(sessionStorage.getItem('userInfo')),
        isLoggedIn: sessionStorage.getItem('isLoggedIn') === 'true',
        messageQueueService: Ember.inject.service(),

        _setUserInfo: function _setUserInfo(userInfo, isLoggedIn) {
            this.set("userInfo", userInfo);
            this.set("isLoggedIn", isLoggedIn);
            sessionStorage.setItem('userInfo', JSON.stringify(userInfo));
            sessionStorage.setItem('isLoggedIn', '' + isLoggedIn);
        },
        register: function register(formData) {
            var _this = this;

            var thisRef = this;

            var orgData = {};
            var userData = {};

            var vulnerableCharacters = new _hashSet.default("<", ">");

            orgData['startedYear'] = +formData.get('startedYear');

            var _arr = ['organizationName', 'organizationAddress'];
            for (var _i = 0; _i < _arr.length; _i++) {
                var key = _arr[_i];
                orgData[key] = (0, _sanitizeInput.default)(formData.get(key), vulnerableCharacters);
            }

            userData['userName'] = (0, _sanitizeInput.default)(formData.get('userName'), vulnerableCharacters);
            userData['dateOfBirth'] = (0, _dateTimeToMills.default)(formData.get('dateOfBirth'));

            var _arr2 = ['phoneNumber', 'email'];
            for (var _i2 = 0; _i2 < _arr2.length; _i2++) {
                var _key = _arr2[_i2];
                userData[_key] = formData.get(_key);
            }

            var password = formData.get('password');

            (0, _rsaEncrypter.default)(password).then(function (encryptedPassword) {

                userData['password'] = encryptedPassword;

                var config = _this.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/auth/register';

                return Ember.$.ajax({
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
                });
            }).then(function (data, textStatus, jqXHR) {
                thisRef._setUserInfo(data.data, true);
            }).catch(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
                _this.get('messageQueueService').addPopupMessage({
                    message: jqXHR.responseJson.message,
                    level: 3
                });
            });
        },
        login: function login(email, password, callBack) {
            var _this2 = this;

            var messageQueueService = this.get('messageQueueService');
            var thisRef = this;

            (0, _rsaEncrypter.default)(password).then(function (encryptedPassword) {
                var config = _this2.get('envService');
                var apiURL = config.getEnv('BASE_API_URL') + '/auth/login';

                return Ember.$.ajax({
                    method: "POST",
                    url: apiURL,
                    data: JSON.stringify({
                        email: email,
                        password: encryptedPassword
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
                });
            }).then(function (data, textStatus, jqXHR) {
                thisRef._setUserInfo(data.data, true);
                callBack();
            }).catch(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
                messageQueueService.addPopupMessage({
                    message: "Invalid user credentials",
                    level: 3
                });
            });
        },
        checkin: function checkin(callBack) {
            var _this3 = this;

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
            }).then(function (data, textStatus, jqXHR) {
                thisRef._setUserInfo(data.data, true);
                callBack();
            }).catch(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);

                _this3.get('messageQueueService').addPopupMessage({
                    message: jqXHR.responseJson.message,
                    level: 0
                });
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
            }).then(function (data, textStatus, jqXHR) {
                thisRef._setUserInfo(null, false);
                sessionStorage.clear();
            }).catch(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
                messageQueueService.addPopupMessage({
                    message: jqXHR.responseJson.message,
                    level: 3
                });
            });
        }
    });
});
define('tournament-management-system/services/data-persistance-service', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Service.extend({
        data: null,
        setData: function setData(_data) {
            this.set('data', _data);
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
            BASE_API_URL: '/tms'
        },
        getEnv: function getEnv(variableName) {
            return this.get('_config')[variableName];
        }
    });
});
define('tournament-management-system/services/loader-service', ['exports'], function (exports) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = Ember.Service.extend({
        isLoading: false,
        setIsLoading: function setIsLoading(value) {
            this.set('isLoading', value);
        }
    });
});
define('tournament-management-system/services/message-queue-service', ['exports', 'tournament-management-system/utils/controllable-timeout'], function (exports, _controllableTimeout) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    function _toConsumableArray(arr) {
        if (Array.isArray(arr)) {
            for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
                arr2[i] = arr[i];
            }

            return arr2;
        } else {
            return Array.from(arr);
        }
    }

    exports.default = Ember.Service.extend({
        messageQueue: [],
        addPopupMessage: function addPopupMessage(messageInfo) {
            messageInfo.timeout = messageInfo.timeout || 4000;
            messageInfo.id = Date.now();
            this.set('messageQueue', [].concat(_toConsumableArray(this.get('messageQueue')), [messageInfo]));
            var thisRef = this;
            var messageTimeoutController = new _controllableTimeout.default(function () {
                var updatedMessageQueue = thisRef.get('messageQueue').filter(function (msgInfo) {
                    return messageInfo.id !== msgInfo.id;
                });
                thisRef.set('messageQueue', updatedMessageQueue);
            }, messageInfo.timeout);
            messageInfo.timeoutController = messageTimeoutController;
            messageTimeoutController.start();
        }

        /*
            Message info model
             message : 'User died successfully' (string)
             type: 1
                    (   
                        * 0 -> (info , blue)
                        * 1 -> (success, green)
                        * 2 -> (warning, orange)
                        * 3 -> (error, red)
                    )
             timeout: 3000 (millis)
         */

    });
});
define("tournament-management-system/templates/access-denied", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "Bh+LqmaR", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section\"],[13],[0,\"\\n    \"],[11,\"h1\",[]],[13],[0,\"Access Denied 🫵😡\"],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/access-denied.hbs" } });
});
define("tournament-management-system/templates/application", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "en6hlq8/", "block": "{\"statements\":[[1,[26,[\"nav-bar\"]],false],[0,\"\\n\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\\n\"],[1,[26,[\"message-queue\"]],false],[0,\"\\n\\n\"],[6,[\"if\"],[[28,[\"isLoading\"]]],null,{\"statements\":[[0,\"    \"],[1,[26,[\"app-loader\"]],false],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/application.hbs" } });
});
define("tournament-management-system/templates/components/app-loader", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "ZEP212e6", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"app-loader\"],[13],[0,\"\\n    \"],[11,\"svg\",[]],[15,\"viewBox\",\"22 22 44 44\"],[15,\"class\",\"app-loader-svg\"],[13],[0,\"\\n        \"],[11,\"circle\",[]],[15,\"cx\",\"44\"],[15,\"cy\",\"44\"],[15,\"r\",\"20.2\"],[15,\"fill\",\"none\"],[15,\"stroke-width\",\"3.6\"],[15,\"class\",\"loader-circle\"],[13],[14],[0,\"\\n    \"],[14],[0,\"\\n\"],[14]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/app-loader.hbs" } });
});
define("tournament-management-system/templates/components/card-item", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "W03eNeqr", "block": "{\"statements\":[[11,\"div\",[]],[16,\"class\",[34,[\"card-item-icon \",[26,[\"itemIconClass\"]]]]],[13],[14],[0,\"\\n\"],[11,\"p\",[]],[16,\"class\",[34,[\"card-item-name \",[26,[\"itemNameClass\"]]]]],[13],[1,[26,[\"itemName\"]],false],[14],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/card-item.hbs" } });
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
  exports.default = Ember.HTMLBars.template({ "id": "G/smQ3Pm", "block": "{\"statements\":[[6,[\"if\"],[[28,[\"buttonIcon\"]]],null,{\"statements\":[[0,\"    \"],[11,\"img\",[]],[15,\"class\",\"button-icon\"],[16,\"src\",[33,[\"prepend-root\"],[[28,[\"buttonIcon\"]]],null],null],[15,\"alt\",\"ButtonIcon\"],[13],[14],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"if\"],[[29,\"default\"]],null,{\"statements\":[[6,[\"if\"],[[28,[\"isSVG\"]]],null,{\"statements\":[[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"button-svg\"],[13],[0,\"\\n            \"],[18,\"default\"],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[0,\"        \"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[]}]],\"locals\":[]},null],[6,[\"if\"],[[28,[\"buttonName\"]]],null,{\"statements\":[[0,\"    \"],[11,\"span\",[]],[15,\"class\",\"button-name\"],[13],[1,[26,[\"buttonName\"]],false],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/general-button.hbs" } });
});
define("tournament-management-system/templates/components/icon-label-item", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "XSf0P89U", "block": "{\"statements\":[[11,\"div\",[]],[16,\"class\",[34,[\"icon-label-item-icon \",[33,[\"if\"],[[28,[\"iconBackground\"]],\"bg\",\"\"],null],\" \",[26,[\"itemIconClass\"]]]]],[13],[0,\"\\n    \"],[11,\"img\",[]],[16,\"src\",[33,[\"prepend-root\"],[[28,[\"iconUrl\"]]],null],null],[15,\"alt\",\"icon\"],[13],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[11,\"div\",[]],[16,\"class\",[34,[\"icon-label-item-box \",[26,[\"iconLabelItemBoxClass\"]]]]],[13],[0,\"\\n\"],[6,[\"if\"],[[28,[\"itemLabel\"]]],null,{\"statements\":[[0,\"        \"],[11,\"span\",[]],[16,\"class\",[26,[\"itemLabelClass\"]],null],[13],[1,[26,[\"itemLabel\"]],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"if\"],[[28,[\"itemValue\"]]],null,{\"statements\":[[0,\"        \"],[11,\"span\",[]],[16,\"class\",[26,[\"itemValueClass\"]],null],[13],[1,[26,[\"itemValue\"]],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[18,\"default\"],[0,\"\\n\"],[14],[0,\"\\n\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/icon-label-item.hbs" } });
});
define("tournament-management-system/templates/components/message-box", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "abba00wl", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"message-box\"],[5,[\"action\"],[[28,[null]],\"pauseTimer\"],[[\"on\"],[\"mouseEnter\"]]],[5,[\"action\"],[[28,[null]],\"resumeTimer\"],[[\"on\"],[\"mouseLeave\"]]],[13],[0,\"\\n    \"],[11,\"div\",[]],[16,\"class\",[34,[\"message-box-icon ft-\",[26,[\"messageColor\"]]]]],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"messageInfo\",\"level\"]],0],null]],null,{\"statements\":[[0,\"            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"width\",\"512\"],[15,\"height\",\"512\"],[15,\"x\",\"0\"],[15,\"y\",\"0\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"style\",\"enable-background:new 0 0 512 512\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[11,\"g\",[]],[13],[11,\"path\",[]],[15,\"d\",\"M12 0C5.383 0 0 5.383 0 12s5.383 12 12 12 12-5.383 12-12S18.617 0 12 0zm0 5a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm2.25 14h-4.5a1 1 0 1 1 0-2H11v-6h-.75a1 1 0 1 1 0-2H12a1 1 0 0 1 1 1v7h1.25a1 1 0 1 1 0 2z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[14],[14],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"messageInfo\",\"level\"]],1],null]],null,{\"statements\":[[0,\"            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"width\",\"512\"],[15,\"height\",\"512\"],[15,\"x\",\"0\"],[15,\"y\",\"0\"],[15,\"viewBox\",\"0 0 408.576 408.576\"],[15,\"style\",\"enable-background:new 0 0 512 512\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[11,\"g\",[]],[13],[11,\"path\",[]],[15,\"d\",\"M204.288 0C91.648 0 0 91.648 0 204.288s91.648 204.288 204.288 204.288 204.288-91.648 204.288-204.288S316.928 0 204.288 0zm114.176 150.528-130.56 129.536c-7.68 7.68-19.968 8.192-28.16.512L90.624 217.6c-8.192-7.68-8.704-20.48-1.536-28.672 7.68-8.192 20.48-8.704 28.672-1.024l54.784 50.176L289.28 121.344c8.192-8.192 20.992-8.192 29.184 0s8.192 20.992 0 29.184z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[14],[14],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"messageInfo\",\"level\"]],2],null]],null,{\"statements\":[[0,\"            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"width\",\"512\"],[15,\"height\",\"512\"],[15,\"x\",\"0\"],[15,\"y\",\"0\"],[15,\"viewBox\",\"0 0 512 512\"],[15,\"style\",\"enable-background:new 0 0 512 512\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[11,\"g\",[]],[13],[11,\"path\",[]],[15,\"d\",\"M507.494 426.066 282.864 53.537a31.372 31.372 0 0 0-53.73 0L4.506 426.066a31.37 31.37 0 0 0 26.864 47.569h449.259a31.372 31.372 0 0 0 26.865-47.569zM256.167 167.227c12.901 0 23.817 7.278 23.817 20.178 0 39.363-4.631 95.929-4.631 135.292 0 10.255-11.247 14.554-19.186 14.554-10.584 0-19.516-4.3-19.516-14.554 0-39.363-4.63-95.929-4.63-135.292 0-12.9 10.584-20.178 24.146-20.178zm.331 243.791c-14.554 0-25.471-11.908-25.471-25.47 0-13.893 10.916-25.47 25.471-25.47 13.562 0 25.14 11.577 25.14 25.47 0 13.562-11.578 25.47-25.14 25.47z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[14],[14],[0,\" \\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"messageInfo\",\"level\"]],3],null]],null,{\"statements\":[[0,\"            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"width\",\"512\"],[15,\"height\",\"512\"],[15,\"x\",\"0\"],[15,\"y\",\"0\"],[15,\"viewBox\",\"0 0 512 512\"],[15,\"style\",\"enable-background:new 0 0 512 512\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[11,\"g\",[]],[13],[11,\"path\",[]],[15,\"d\",\"M256 0C114.837 0 0 114.837 0 256s114.837 256 256 256 256-114.837 256-256S397.163 0 256 0zm0 409.6c-14.137 0-25.6-11.463-25.6-25.6s11.463-25.6 25.6-25.6 25.6 11.463 25.6 25.6-11.463 25.6-25.6 25.6zm25.6-122.312c0 14.137-11.463 25.6-25.6 25.6s-25.6-11.463-25.6-25.6V99.563c0-14.137 11.463-25.6 25.6-25.6s25.6 11.463 25.6 25.6z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[14],[14],[0,\"\\n        \"]],\"locals\":[]},null]],\"locals\":[]}]],\"locals\":[]}]],\"locals\":[]}],[0,\"    \"],[14],[0,\"\\n    \"],[11,\"p\",[]],[15,\"class\",\"message-box-text\"],[13],[1,[28,[\"messageInfo\",\"message\"]],false],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"message-box-loader\"],[13],[14],[0,\"\\n\"],[14]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/message-box.hbs" } });
});
define("tournament-management-system/templates/components/message-queue", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "i5tUVPFo", "block": "{\"statements\":[[6,[\"each\"],[[28,[\"messageQueue\"]]],null,{\"statements\":[[0,\"    \"],[1,[33,[\"message-box\"],null,[[\"messageInfo\"],[[28,[\"messageInfo\"]]]]],false],[0,\"\\n\"]],\"locals\":[\"messageInfo\"]},null]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/message-queue.hbs" } });
});
define("tournament-management-system/templates/components/nav-bar", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "xd560Q3f", "block": "{\"statements\":[[6,[\"link-to\"],[\"index\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"    \"],[11,\"div\",[]],[15,\"class\",\"navbar-logo\"],[13],[14],[0,\"\\n\"]],\"locals\":[]},null],[11,\"nav\",[]],[15,\"class\",\"navbar-menu\"],[13],[0,\"\\n\"],[6,[\"if\"],[[28,[\"isLoggedIn\"]]],null,{\"statements\":[[6,[\"link-to\"],[\"dashboard\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"nav-button bg-transparent no-border ft-grey\",\"Dashboard\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"unless\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null]],null,{\"statements\":[[6,[\"link-to\"],[\"tournaments\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"                 \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"nav-button bg-transparent no-border ft-grey\",\"Tournaments\"]]],false],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],0],null]],null,{\"statements\":[[0,\"\\n\"]],\"locals\":[]},null],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],1],null]],null,{\"statements\":[[6,[\"link-to\"],[\"organizations.organization\",[28,[\"userInfo\",\"organizationId\"]]],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"nav-button bg-transparent no-border ft-grey\",\"Organization\"]]],false],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null]],null,{\"statements\":[[6,[\"link-to\"],[\"organizations\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"nav-button bg-transparent no-border ft-grey\",\"Organizations\"]]],false],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null],[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"navbar-profile-menu-container\"],[13],[0,\"\\n\"],[6,[\"general-button\"],null,[[\"class\",\"isSVG\",\"onClick\"],[\"br-transparent bg-transparent no-padding icon-4xl\",true,[33,[\"action\"],[[28,[null]],\"handleProfileMenuVisibility\"],null]]],{\"statements\":[[0,\"                \"],[11,\"svg\",[]],[15,\"viewBox\",\"0 0 24 24\"],[15,\"fill\",\"none\"],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"stroke\",\"#bbbbbb\"],[15,\"stroke-width\",\"1.104\"],[13],[0,\"\\n                    \"],[11,\"g\",[]],[15,\"stroke-width\",\"0\"],[13],[14],[0,\"\\n                    \"],[11,\"g\",[]],[15,\"stroke-linecap\",\"round\"],[15,\"stroke-linejoin\",\"round\"],[15,\"stroke\",\"#CCCCCC\"],[15,\"stroke-width\",\"0.144\"],[13],[14],[0,\"\\n                    \"],[11,\"g\",[]],[13],[0,\" \\n                        \"],[11,\"path\",[]],[15,\"opacity\",\"0.4\"],[15,\"d\",\"M12 22.01C17.5228 22.01 22 17.5329 22 12.01C22 6.48716 17.5228 2.01001 12 2.01001C6.47715 2.01001 2 6.48716 2 12.01C2 17.5329 6.47715 22.01 12 22.01Z\"],[15,\"fill\",\"#bbbbbb\"],[13],[14],[0,\" \\n                        \"],[11,\"path\",[]],[15,\"d\",\"M12 6.93994C9.93 6.93994 8.25 8.61994 8.25 10.6899C8.25 12.7199 9.84 14.3699 11.95 14.4299C11.98 14.4299 12.02 14.4299 12.04 14.4299C12.06 14.4299 12.09 14.4299 12.11 14.4299C12.12 14.4299 12.13 14.4299 12.13 14.4299C14.15 14.3599 15.74 12.7199 15.75 10.6899C15.75 8.61994 14.07 6.93994 12 6.93994Z\"],[15,\"fill\",\"#bbbbbb\"],[13],[14],[0,\" \\n                        \"],[11,\"path\",[]],[15,\"d\",\"M18.7807 19.36C17.0007 21 14.6207 22.01 12.0007 22.01C9.3807 22.01 7.0007 21 5.2207 19.36C5.4607 18.45 6.1107 17.62 7.0607 16.98C9.7907 15.16 14.2307 15.16 16.9407 16.98C17.9007 17.62 18.5407 18.45 18.7807 19.36Z\"],[15,\"fill\",\"#bbbbbb\"],[13],[14],[0,\" \\n                    \"],[14],[0,\"\\n                \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"if\"],[[28,[\"isProfileMenuOpen\"]]],null,{\"statements\":[[0,\"                \"],[11,\"div\",[]],[15,\"class\",\"navbar-profile-menu\"],[13],[0,\"\\n\"],[6,[\"link-to\"],[\"profile\"],[[\"class\"],[\"no-decoration\"]],{\"statements\":[[0,\"                        \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"no-border bg-white ft-night-black full-width\",\"Profile\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[0,\"                    \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"no-border bg-white ft-red soft-corner full-width\",\"Logout\",[33,[\"action\"],[[28,[null]],\"logout\"],null]]]],false],[0,\"\\n                \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"        \"],[14],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"link-to\"],[\"register\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"bg-blue br-blue ft-white\",\"Register\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"link-to\"],[\"login\"],[[\"class\"],[\"navbar-link\"]],{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"no-border bg-transparent ft-blue\",\"Login\"]]],false],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]}],[14],[0,\"\\n\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/nav-bar.hbs" } });
});
define("tournament-management-system/templates/components/organization-card", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "fgaXeG4s", "block": "{\"statements\":[[1,[33,[\"card-item\"],null,[[\"class\",\"itemIconClass\",\"itemNameClass\",\"itemName\"],[\"organization-card-title\",\"no-icon\",\"elipsis captialize\",[28,[\"organization\",\"organizationName\"]]]]],false],[0,\"\\n\"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemNameClass\",\"itemName\"],[\"location-icon\",\"elipsis\",[28,[\"organization\",\"organizationAddress\"]]]]],false],[0,\"\\n\"],[11,\"div\",[]],[15,\"class\",\"organization-card-box\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"organization-card-detail-box\"],[13],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"calander-icon\",[28,[\"organization\",\"startedYear\"]]]]],false],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemNameClass\",\"itemName\"],[\"admin-icon\",\"elipsis\",[33,[\"or\"],[[28,[\"organization\",\"userName\"]],\"Not assigned\"],null]]]],false],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemNameClass\",\"itemName\"],[\"badge-icon\",\"uppercase ft-sm\",[33,[\"organization-status\"],[[28,[\"organization\",\"organizationStatus\"]]],null]]]],false],[0,\"\\n    \"],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"organization-card-button-box\"],[13],[0,\"\\n\"],[6,[\"link-to\"],[\"organizations.organization\",[28,[\"organization\",\"organizationId\"]]],[[\"class\"],[\"no-decoration\"]],{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"bg-transparent no-border ft-blue soft-corner\",\"Details\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"organization\",\"organizationStatus\"]],0],null]],null,{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"bg-green br-green ft-white soft-corner\",\"Approve\",[33,[\"action\"],[[28,[null]],[28,[\"changeOrganizationStatus\"]],[28,[\"organization\",\"organizationId\"]],1],null]]]],false],[0,\"\\n\"]],\"locals\":[]},null],[0,\"\\n\"],[6,[\"unless\"],[[33,[\"eq\"],[[28,[\"organization\",\"organizationStatus\"]],2],null]],null,{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-red bg-white ft-red soft-corner\",\"Block\",[33,[\"action\"],[[28,[null]],[28,[\"changeOrganizationStatus\"]],[28,[\"organization\",\"organizationId\"]],2],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-green bg-white ft-green soft-corner\",\"Unblock\",[33,[\"action\"],[[28,[null]],[28,[\"changeOrganizationStatus\"]],[28,[\"organization\",\"organizationId\"]],0],null]]]],false],[0,\"\\n\"]],\"locals\":[]}],[0,\"    \"],[14],[0,\"\\n\"],[14]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/organization-card.hbs" } });
});
define("tournament-management-system/templates/components/organization-form", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "KAAYcvku", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container edit-organization\"],[13],[0,\"\\n\"],[6,[\"form-model\"],null,[[\"class\",\"formHeader\",\"onSubmit\"],[\"edit-organization-form\",\"Update Organization\",[33,[\"action\"],[[28,[null]],\"handleSubmit\"],null]]],{\"statements\":[[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"organization-form-wrapper\"],[13],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"organization-form-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"organization-form-innerbox\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"defaultValue\",\"isRequired\",\"errorMessage\"],[\"organizationName\",\"edit-organizationname\",\"Organization Name\",[28,[\"organization\",\"organizationName\"]],true,[28,[\"validationErrors\",\"organizationName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"defaultValue\",\"isRequired\",\"errorMessage\"],[\"organizationAddress\",\"edit-organizationaddress\",\"Organization Address\",[28,[\"organization\",\"organizationAddress\"]],true,[28,[\"validationErrors\",\"organizationAddress\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"defaultValue\",\"errorMessage\"],[\"startedYear\",\"edit-startedyear\",\"Started Year\",true,[28,[\"organization\",\"startedYear\"]],[28,[\"validationErrors\",\"startedYear\"]]]]],false],[0,\"\\n\\n\"],[6,[\"if\"],[0],null,{\"statements\":[[0,\"                    \"],[11,\"div\",[]],[15,\"class\",\"edit-adminbox\"],[13],[0,\"\\n                        \"],[11,\"select\",[]],[15,\"name\",\"adminId\"],[15,\"id\",\"edit-adminid\"],[13],[0,\"\\n                            \"],[11,\"option\",[]],[16,\"value\",[28,[\"admin\",\"userId\"]],null],[15,\"selected\",\"true\"],[15,\"disabled\",\"true\"],[15,\"hidden\",\"true\"],[13],[1,[28,[\"admin\",\"userName\"]],false],[14],[0,\"\\n\"],[6,[\"if\"],[[28,[\"changeAdminSelected\"]]],null,{\"statements\":[[6,[\"each\"],[[28,[\"users\"]]],null,{\"statements\":[[0,\"                                        \"],[11,\"option\",[]],[16,\"value\",[28,[\"user\",\"userId\"]],null],[13],[1,[28,[\"user\",\"userName\"]],false],[14],[0,\"\\n\"]],\"locals\":[\"user\"]},null]],\"locals\":[]},null],[0,\"                        \"],[14],[0,\"\\n                        \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"br-blue bg-blue ft-white\",[33,[\"if\"],[[28,[\"changeAdminSelected\"]],\"Confirm\",\"Change Admin\"],null]]]],false],[0,\"\\n                    \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"\\n\\n                \"],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"organization-form-button-box\"],[13],[0,\"\\n                    \"],[1,[33,[\"general-button\"],null,[[\"buttonName\",\"class\",\"onClick\"],[\"Cancel\",\"br-light-grey bg-pale-white ft-night-black full-width\",[33,[\"action\"],[[28,[null]],[28,[\"closeOrganizationForm\"]]],null]]]],false],[0,\"\\n                    \"],[1,[33,[\"general-button\"],null,[[\"type\",\"buttonName\",\"class\"],[\"submit\",\"Save\",\"br-blue bg-blue ft-white full-width\"]]],false],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[14],[0,\"\\n\\n\\n\"],[18,\"default\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/organization-form.hbs" } });
});
define("tournament-management-system/templates/components/organization-navbar", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "8Un23fUC", "block": "{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null]],null,{\"statements\":[[6,[\"link-to\"],[\"tournaments.new\"],[[\"class\"],[\"no-decoration\"]],{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"buttonName\",\"isSVG\"],[\"br-green bg-green ft-white soft-corner\",\"New Organization\",true]],{\"statements\":[[0,\"            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"fill\",\"none\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"stroke-width\",\"1.75\"],[15,\"stroke\",\"currentColor\"],[13],[0,\"\\n                \"],[11,\"path\",[]],[15,\"stroke-linecap\",\"round\"],[15,\"stroke-linejoin\",\"round\"],[15,\"d\",\"M12 4.5v15m7.5-7.5h-15\"],[13],[14],[0,\"\\n            \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null]],\"locals\":[]},null],[1,[33,[\"search-bar\"],null,[[\"minWait\",\"searchHandler\",\"searchBarId\",\"class\"],[300,[28,[\"searchOrganizations\"]],\"organization-search-bar\",\"bg-white max-width-lg\"]]],false],[0,\"\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/organization-navbar.hbs" } });
});
define("tournament-management-system/templates/components/organization-user-form", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "MsCIXkxk", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container organization-user-form-container\"],[13],[0,\"\\n\"],[6,[\"form-model\"],null,[[\"class\",\"formHeader\",\"onSubmit\"],[\"organization-user-form-model\",\"Add Member\",[33,[\"action\"],[[28,[null]],\"handleOrganizationUserFormSubmit\"],null]]],{\"statements\":[[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"organization-user-form-wrapper\"],[13],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"organization-user-form-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"organization-user-form-innerbox\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"userName\",\"register-username\",\"Full Name\",true,[28,[\"validationErrors\",\"userName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"dateOfBirth\",\"register-dateofbirth\",\"Date Of Birth (dd/mm/yyyy)\",true,[28,[\"validationErrors\",\"dateOfBirth\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"phoneNumber\",\"register-phonenumber\",\"Phone Number\",true,[28,[\"validationErrors\",\"phoneNumber\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"email\",\"register-email\",\"Email\",true,[28,[\"validationErrors\",\"email\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"password-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"password\",\"register-password\",\"Password\",true,[28,[\"validationErrors\",\"password\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"password-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"confirmPassword\",\"register-confirmpassword\",\"Confirm Password\",true,[28,[\"validationErrors\",\"confirmPassword\"]]]]],false],[0,\"\\n                \"],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"organization-user-form-buttons\"],[13],[0,\"\\n                    \"],[1,[33,[\"general-button\"],null,[[\"buttonName\",\"class\",\"onClick\"],[\"Cancel\",\"br-light-grey bg-pale-white ft-night-black soft-corner full-width\",[33,[\"action\"],[[28,[null]],[28,[\"closeOrganizationUserForm\"]]],null]]]],false],[0,\"\\n                    \"],[1,[33,[\"general-button\"],null,[[\"type\",\"buttonName\",\"class\"],[\"submit\",\"Save\",\"br-blue bg-blue ft-white soft-corner full-width\"]]],false],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[14],[0,\"\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/organization-user-form.hbs" } });
});
define("tournament-management-system/templates/components/organization-user-navbar", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "1QxO4tAq", "block": "{\"statements\":[[6,[\"if\"],[[33,[\"or\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],1],null],[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null]],null]],null,{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"buttonName\",\"isSVG\",\"onClick\"],[\"br-green bg-green ft-white soft-corner\",\"New Member\",true,[33,[\"action\"],[[28,[null]],[28,[\"openAddUserForm\"]]],null]]],{\"statements\":[[0,\"        \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"fill\",\"none\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"stroke-width\",\"1.75\"],[15,\"stroke\",\"currentColor\"],[13],[0,\"\\n            \"],[11,\"path\",[]],[15,\"stroke-linecap\",\"round\"],[15,\"stroke-linejoin\",\"round\"],[15,\"d\",\"M12 4.5v15m7.5-7.5h-15\"],[13],[14],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null],[1,[33,[\"search-bar\"],null,[[\"minWait\",\"searchHandler\",\"searchBarId\",\"class\"],[300,[28,[\"searchOrganizationUsers\"]],\"organization-user-search-bar\",\"bg-pale-white max-width-lg\"]]],false],[0,\"\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/organization-user-navbar.hbs" } });
});
define("tournament-management-system/templates/components/participant-card", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "maE2itCR", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"participant-card-info\"],[13],[0,\"\\n    \"],[11,\"span\",[]],[15,\"class\",\"ft-night-black capitalize\"],[13],[1,[28,[\"participant\",\"userName\"]],false],[14],[0,\"\\n\"],[6,[\"if\"],[[28,[\"pinCard\"]]],null,{\"statements\":[[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"ft-night-black pincard\"],[13],[0,\"\\n            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 20 20\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                \"],[11,\"path\",[]],[15,\"d\",\"M19.3 4.3 15.7.7C14.4-.6 12.1.1 11.8 2l-.2 1.4-3.3 3.3c-2.4-.6-4.9.1-6.6 1.8-.4.4-.4 1 0 1.4l3.5 3.5-4.9 4.9c-.4.4-.4 1 0 1.4s1 .4 1.4 0l4.9-4.9 3.5 3.5c.4.4 1 .4 1.4 0 1.7-1.7 2.4-4.3 1.8-6.6l3.4-3.4 1.3-.2c1.9-.2 2.7-2.5 1.3-3.8zm-1.5 1.9-1.7.2c-.2 0-.4.1-.6.3l-4 4c-.3.3-.4.7-.2 1 .5 1.5.3 3.1-.5 4.4-1.2-1.1-1.7-1.6-7-6.8 1.3-.8 2.9-1.1 4.4-.5.4.1.8 0 1-.2l4-4c.2-.2.3-.4.3-.6l.2-1.7c0-.2.3-.3.5-.2l3.6 3.6c.3.2.2.4 0 .5z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"currentColor\"],[13],[14],[0,\"\\n            \"],[14],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[14],[0,\"\\n\"],[11,\"div\",[]],[15,\"class\",\"participant-card-buttons\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"and\"],[[28,[\"showContestantStatusControls\"]],[33,[\"or\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],1],null],[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null]],null]],null]],null,{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"participant\",\"participantStatus\"]],0],null]],null,{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"isSVG\",\"onClick\",\"title\"],[\"br-transparent bg-transparent ft-red pd-sq-sm\",true,[33,[\"action\"],[[28,[null]],\"changeParticipantStatus\",1],null],\"Disqualify\"]],{\"statements\":[[0,\"                \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 512 512\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                    \"],[11,\"path\",[]],[15,\"d\",\"M436.3 75.7C388 27.401 324.101 0 256 0 115.343 0 0 115.116 0 256c0 140.958 115.075 256 256 256 140.306 0 256-114.589 256-256 0-68.101-27.4-132.001-75.7-180.3zM256 451c-107.786 0-195-86.985-195-195 0-42.001 13.2-81.901 37.5-114.901l272.401 272.1C337.899 437.8 298.001 451 256 451zm157.2-80.101L141.099 98.5C174.101 74.2 213.999 61 256 61c107.789 0 195 86.985 195 195 0 41.999-13.2 81.899-37.8 114.899z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[0,\"\\n                \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"participant\",\"participantStatus\"]],1],null]],null,{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"isSVG\",\"onClick\",\"title\"],[\"br-transparent bg-transparent ft-grey pd-sq-sm\",true,[33,[\"action\"],[[28,[null]],\"changeParticipantStatus\",0],null],\"Permit\"]],{\"statements\":[[0,\"                \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 512 512\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                    \"],[11,\"path\",[]],[15,\"d\",\"M436.3 75.7C388 27.401 324.101 0 256 0 115.343 0 0 115.116 0 256c0 140.958 115.075 256 256 256 140.306 0 256-114.589 256-256 0-68.101-27.4-132.001-75.7-180.3zM256 451c-107.786 0-195-86.985-195-195 0-42.001 13.2-81.901 37.5-114.901l272.401 272.1C337.899 437.8 298.001 451 256 451zm157.2-80.101L141.099 98.5C174.101 74.2 213.999 61 256 61c107.789 0 195 86.985 195 195 0 41.999-13.2 81.899-37.8 114.899z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[0,\"\\n                \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"disabled\"],[\"br-light-grey bg-pale-white ft-light-grey pd-sm ft-xs curved\",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"participant\",\"participantStatus\"]],3],null],\"Won\",\"Lost\"],null],true]]],false],[0,\"\\n        \"]],\"locals\":[]}]],\"locals\":[]}]],\"locals\":[]},null],[14],[0,\"\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/participant-card.hbs" } });
});
define("tournament-management-system/templates/components/password-input", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "Wv9PfxZ5", "block": "{\"statements\":[[11,\"div\",[]],[16,\"class\",[34,[\"input-box \",[33,[\"unless\"],[[28,[\"errorMessage\"]],\"br-light-grey\",\"br-red\"],null]]]],[13],[0,\"\\n\"],[6,[\"if\"],[[28,[\"labelName\"]]],null,{\"statements\":[[0,\"        \"],[11,\"label\",[]],[15,\"class\",\"input-label\"],[16,\"for\",[26,[\"inputId\"]],null],[13],[0,\"\\n            \"],[1,[26,[\"labelName\"]],false],[0,\" \\n\"],[6,[\"if\"],[[28,[\"isRequired\"]]],null,{\"statements\":[[0,\"                \"],[11,\"span\",[]],[15,\"class\",\"input-required ft-red\"],[13],[0,\"*\"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[11,\"input\",[]],[16,\"id\",[26,[\"inputId\"]],null],[16,\"name\",[26,[\"inputName\"]],null],[16,\"type\",[33,[\"if\"],[[28,[\"isPasswordVisible\"]],\"text\",\"password\"],null],null],[15,\"class\",\"input-field\"],[16,\"placeholder\",[26,[\"inputPlaceholder\"]],null],[15,\"autocomplete\",\"off\"],[5,[\"action\"],[[28,[null]],\"handleInputChange\"],[[\"on\"],[\"input\"]]],[13],[14],[0,\"\\n    \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonIcon\",\"onClick\"],[\"no-border bg-transparent no-padding icon-sm\",[33,[\"if\"],[[28,[\"isPasswordVisible\"]],\"images/eye-icon.svg\",\"images/eye-slash-icon.svg\"],null],[33,[\"action\"],[[28,[null]],\"togglePasswordVisibility\"],null]]]],false],[0,\"\\n\"],[14],[0,\"\\n\"],[6,[\"if\"],[[28,[\"errorMessage\"]]],null,{\"statements\":[[0,\"    \"],[11,\"p\",[]],[15,\"class\",\"input-error\"],[13],[1,[26,[\"errorMessage\"]],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/password-input.hbs" } });
});
define("tournament-management-system/templates/components/popup-box", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "vArBifCD", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"popup-container\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"popup-model\"],[13],[0,\"\\n        \"],[18,\"default\"],[0,\"\\n    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/popup-box.hbs" } });
});
define("tournament-management-system/templates/components/search-bar", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "D/xrCDvD", "block": "{\"statements\":[[11,\"label\",[]],[15,\"class\",\"search-icon\"],[16,\"for\",[26,[\"searchBarId\"]],null],[13],[14],[0,\"\\n\"],[1,[33,[\"input\"],null,[[\"type\",\"class\",\"value\",\"id\"],[\"text\",\"search-input\",[28,[\"searchField\"]],[28,[\"searchBarId\"]]]]],false],[0,\"\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/search-bar.hbs" } });
});
define("tournament-management-system/templates/components/select-input", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "izjXwt//", "block": "{\"statements\":[[11,\"div\",[]],[16,\"class\",[34,[\"input-box \",[33,[\"unless\"],[[28,[\"errorMessage\"]],\"br-light-grey\",\"br-red\"],null]]]],[13],[0,\"\\n\"],[6,[\"if\"],[[28,[\"labelName\"]]],null,{\"statements\":[[0,\"        \"],[11,\"label\",[]],[15,\"class\",\"input-label select-label\"],[16,\"for\",[26,[\"inputId\"]],null],[13],[0,\"\\n            \"],[1,[26,[\"labelName\"]],false],[0,\" \\n\"],[6,[\"if\"],[[28,[\"isRequired\"]]],null,{\"statements\":[[0,\"                \"],[11,\"span\",[]],[15,\"class\",\"input-required ft-red\"],[13],[0,\"*\"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[11,\"select\",[]],[16,\"id\",[26,[\"inputId\"]],null],[16,\"name\",[26,[\"inputName\"]],null],[16,\"value\",[26,[\"defaultValue\"]],null],[15,\"class\",\"input-field\"],[16,\"placeholder\",[26,[\"inputPlaceholder\"]],null],[5,[\"action\"],[[28,[null]],\"handleInputChange\"],[[\"on\"],[\"change\"]]],[13],[0,\"\\n\"],[6,[\"if\"],[[28,[\"showDefaultValue\"]]],null,{\"statements\":[[0,\"            \"],[11,\"option\",[]],[16,\"value\",[28,[\"defaultValue\",\"value\"]],null],[16,\"selected\",[28,[\"defaultValue\",\"selected\"]],null],[16,\"disabled\",[28,[\"defaultValue\",\"disabled\"]],null],[16,\"hidden\",[28,[\"defaultValue\",\"hidden\"]],null],[13],[1,[28,[\"defaultValue\",\"displayName\"]],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"each\"],[[28,[\"options\"]]],null,{\"statements\":[[0,\"            \"],[11,\"option\",[]],[16,\"value\",[28,[\"option\",\"value\"]],null],[13],[1,[28,[\"option\",\"displayName\"]],false],[14],[0,\"\\n\"]],\"locals\":[\"option\"]},null],[0,\"        \"],[18,\"default\"],[0,\"\\n    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[6,[\"if\"],[[28,[\"errorMessage\"]]],null,{\"statements\":[[0,\"    \"],[11,\"p\",[]],[15,\"class\",\"input-error\"],[13],[1,[26,[\"errorMessage\"]],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/select-input.hbs" } });
});
define("tournament-management-system/templates/components/team-card", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "T30V4VZ1", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"team-card-info-box\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"team-card-info\"],[13],[0,\"\\n        \"],[11,\"span\",[]],[15,\"class\",\"ft-night-black wt-500\"],[13],[1,[28,[\"team\",\"teamName\"]],false],[14],[0,\"\\n\"],[6,[\"if\"],[[28,[\"pinCard\"]]],null,{\"statements\":[[0,\"            \"],[11,\"div\",[]],[15,\"class\",\"ft-night-black pincard\"],[13],[0,\"\\n                \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 20 20\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                    \"],[11,\"path\",[]],[15,\"d\",\"M19.3 4.3 15.7.7C14.4-.6 12.1.1 11.8 2l-.2 1.4-3.3 3.3c-2.4-.6-4.9.1-6.6 1.8-.4.4-.4 1 0 1.4l3.5 3.5-4.9 4.9c-.4.4-.4 1 0 1.4s1 .4 1.4 0l4.9-4.9 3.5 3.5c.4.4 1 .4 1.4 0 1.7-1.7 2.4-4.3 1.8-6.6l3.4-3.4 1.3-.2c1.9-.2 2.7-2.5 1.3-3.8zm-1.5 1.9-1.7.2c-.2 0-.4.1-.6.3l-4 4c-.3.3-.4.7-.2 1 .5 1.5.3 3.1-.5 4.4-1.2-1.1-1.7-1.6-7-6.8 1.3-.8 2.9-1.1 4.4-.5.4.1.8 0 1-.2l4-4c.2-.2.3-.4.3-.6l.2-1.7c0-.2.3-.3.5-.2l3.6 3.6c.3.2.2.4 0 .5z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"currentColor\"],[13],[14],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"team-card-buttons\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"and\"],[[28,[\"showContestantStatusControls\"]],[33,[\"or\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],1],null],[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null]],null]],null]],null,{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"team\",\"teamStatus\"]],0],null]],null,{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"isSVG\",\"onClick\",\"title\"],[\"br-transparent bg-transparent ft-red pd-sq-sm\",true,[33,[\"action\"],[[28,[null]],\"changeTeamStatus\",1],null],\"Disqualify\"]],{\"statements\":[[0,\"                    \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 512 512\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                        \"],[11,\"path\",[]],[15,\"d\",\"M436.3 75.7C388 27.401 324.101 0 256 0 115.343 0 0 115.116 0 256c0 140.958 115.075 256 256 256 140.306 0 256-114.589 256-256 0-68.101-27.4-132.001-75.7-180.3zM256 451c-107.786 0-195-86.985-195-195 0-42.001 13.2-81.901 37.5-114.901l272.401 272.1C337.899 437.8 298.001 451 256 451zm157.2-80.101L141.099 98.5C174.101 74.2 213.999 61 256 61c107.789 0 195 86.985 195 195 0 41.999-13.2 81.899-37.8 114.899z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[0,\"\\n                    \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"team\",\"teamStatus\"]],1],null]],null,{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"isSVG\",\"onClick\",\"title\"],[\"br-transparent bg-transparent ft-grey pd-sq-sm\",true,[33,[\"action\"],[[28,[null]],\"changeTeamStatus\",0],null],\"Permit\"]],{\"statements\":[[0,\"                    \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 512 512\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                        \"],[11,\"path\",[]],[15,\"d\",\"M436.3 75.7C388 27.401 324.101 0 256 0 115.343 0 0 115.116 0 256c0 140.958 115.075 256 256 256 140.306 0 256-114.589 256-256 0-68.101-27.4-132.001-75.7-180.3zM256 451c-107.786 0-195-86.985-195-195 0-42.001 13.2-81.901 37.5-114.901l272.401 272.1C337.899 437.8 298.001 451 256 451zm157.2-80.101L141.099 98.5C174.101 74.2 213.999 61 256 61c107.789 0 195 86.985 195 195 0 41.999-13.2 81.899-37.8 114.899z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[0,\"\\n                    \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},{\"statements\":[[0,\"                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"disabled\"],[\"br-light-grey bg-pale-white ft-light-grey pd-sm ft-xs curved\",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"team\",\"teamStatus\"]],3],null],\"Won\",\"Lost\"],null],true]]],false],[0,\"\\n            \"]],\"locals\":[]}]],\"locals\":[]}]],\"locals\":[]},null],[6,[\"general-button\"],null,[[\"class\",\"onClick\",\"isSVG\",\"title\"],[\"br-transparent bg-transparent ft-grey circle icon-sm no-padding\",[33,[\"action\"],[[28,[null]],\"toggleMembersPanelOpen\"],null],true,\"Team Members\"]],{\"statements\":[[0,\"            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                \"],[11,\"g\",[]],[16,\"transform\",[33,[\"if\"],[[28,[\"isMembersPanelOpen\"]],\"matrix(1,0,0,-1,0,24)\",\"\"],null],null],[13],[0,\"\\n                    \"],[11,\"path\",[]],[15,\"d\",\"M12 2.25c-5.376 0-9.75 4.374-9.75 9.75s4.374 9.75 9.75 9.75 9.75-4.374 9.75-9.75S17.376 2.25 12 2.25zm4.53 9.28-3.646 3.646c-.243.244-.563.365-.884.365s-.641-.121-.884-.365L7.47 11.53a.75.75 0 1 1 1.061-1.061l3.47 3.47 3.47-3.47a.75.75 0 1 1 1.061 1.061z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[6,[\"if\"],[[28,[\"isMembersPanelOpen\"]]],null,{\"statements\":[[0,\"    \"],[11,\"div\",[]],[15,\"class\",\"team-members-box\"],[13],[0,\"\\n\"],[6,[\"each\"],[[28,[\"members\"]]],null,{\"statements\":[[0,\"            \"],[11,\"div\",[]],[15,\"class\",\"team-member-info-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"team-member-info\"],[13],[0,\"\\n                    \"],[11,\"span\",[]],[15,\"class\",\"ft-night-black capitalize\"],[13],[1,[28,[\"member\",\"userName\"]],false],[14],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"team\",\"teamLeaderId\"]],[28,[\"member\",\"userId\"]]],null]],null,{\"statements\":[[0,\"                        \"],[11,\"span\",[]],[15,\"class\",\"br-light-grey bg-pale-white ft-grey ft-xs uppercase team-leader-tag\"],[13],[0,\"Leader\"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"                \"],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"team-member-buttons\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"and\"],[[28,[\"showRemoveMember\"]],[33,[\"eq\"],[[28,[\"userInfo\",\"userId\"]],[28,[\"team\",\"teamLeaderId\"]]],null],[33,[\"n-eq\"],[[28,[\"member\",\"userId\"]],[28,[\"team\",\"teamLeaderId\"]]],null]],null]],null,{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"isSVG\",\"onClick\",\"title\"],[\"br-transparent bg-transparent ft-red pd-sq-sm\",true,[33,[\"action\"],[[28,[null]],\"removeTeamMember\",[28,[\"member\",\"teamMemberId\"]]],null],\"Remove member\"]],{\"statements\":[[0,\"                            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                                \"],[11,\"g\",[]],[13],[0,\"\\n                                    \"],[11,\"path\",[]],[15,\"d\",\"M12 1a11 11 0 1 0 11 11A11.013 11.013 0 0 0 12 1zm0 20a9 9 0 1 1 9-9 9.01 9.01 0 0 1-9 9z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[0,\"\\n                                    \"],[11,\"path\",[]],[15,\"d\",\"M16 11H8a1 1 0 0 0 0 2h8a1 1 0 0 0 0-2z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[0,\"\\n                                \"],[14],[0,\"\\n                            \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null],[0,\"                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n\"]],\"locals\":[\"member\"]},null],[0,\"    \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/team-card.hbs" } });
});
define("tournament-management-system/templates/components/text-input", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "Cb2okaie", "block": "{\"statements\":[[11,\"div\",[]],[16,\"class\",[34,[\"input-box \",[33,[\"unless\"],[[28,[\"errorMessage\"]],\"br-light-grey\",\"br-red\"],null]]]],[13],[0,\"\\n\"],[6,[\"if\"],[[28,[\"labelName\"]]],null,{\"statements\":[[0,\"        \"],[11,\"label\",[]],[15,\"class\",\"input-label\"],[16,\"for\",[26,[\"inputId\"]],null],[13],[0,\"\\n            \"],[1,[26,[\"labelName\"]],false],[0,\" \\n\"],[6,[\"if\"],[[28,[\"isRequired\"]]],null,{\"statements\":[[0,\"                \"],[11,\"span\",[]],[15,\"class\",\"input-required ft-red\"],[13],[0,\"*\"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[11,\"input\",[]],[16,\"id\",[26,[\"inputId\"]],null],[16,\"name\",[26,[\"inputName\"]],null],[16,\"value\",[26,[\"defaultValue\"]],null],[15,\"class\",\"input-field\"],[16,\"placeholder\",[26,[\"inputPlaceholder\"]],null],[5,[\"action\"],[[28,[null]],\"handleInputChange\"],[[\"on\"],[\"input\"]]],[13],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[6,[\"if\"],[[28,[\"errorMessage\"]]],null,{\"statements\":[[0,\"    \"],[11,\"p\",[]],[15,\"class\",\"input-error\"],[13],[1,[26,[\"errorMessage\"]],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/text-input.hbs" } });
});
define("tournament-management-system/templates/components/tournament-card", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "ocyQF/or", "block": "{\"statements\":[[6,[\"link-to\"],[\"tournaments.tournament\",[28,[\"tournament\",\"tournamentId\"]]],[[\"class\"],[\"no-decoration tournament-card-image-box\"]],{\"statements\":[[0,\"    \"],[11,\"img\",[]],[15,\"class\",\"tournament-card-image\"],[16,\"src\",[33,[\"prepend-root\"],[[28,[\"tournament\",\"tournamentPoster\"]]],null],null],[15,\"alt\",\"Tournament Poster\"],[13],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-status\"],[13],[1,[33,[\"tournament-status\"],[[28,[\"tournament\",\"tournamentStatus\"]]],null],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[11,\"div\",[]],[15,\"class\",\"tournament-card-details\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-title-box\"],[13],[0,\"\\n\"],[6,[\"link-to\"],[\"tournaments.tournament\",[28,[\"tournament\",\"tournamentId\"]]],[[\"class\"],[\"no-decoration tournament-card-title\"]],{\"statements\":[[0,\"            \"],[1,[28,[\"tournament\",\"tournamentName\"]],false],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"if\"],[[33,[\"or\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],1],null],[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null]],null]],null,{\"statements\":[[0,\"            \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-menu-box\"],[13],[0,\"\\n                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonIcon\",\"onClick\",\"title\"],[\"pd-sq-sm no-border bg-transparent soft-corner\",\"images/dots-menu-icon.svg\",[33,[\"action\"],[[28,[null]],\"handleMenuVisibility\"],null],\"Menu\"]]],false],[0,\"\\n\"],[6,[\"if\"],[[28,[\"isMenuOpen\"]]],null,{\"statements\":[[0,\"                    \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-menu\"],[13],[0,\"\\n\"],[6,[\"link-to\"],[\"tournaments.tournament.edit\",[28,[\"tournament\",\"tournamentId\"]]],[[\"class\"],[\"no-decoration\"]],{\"statements\":[[0,\"                            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"full-width bg-white no-border\",\"Edit\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[0,\"                        \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"full-width bg-white no-border\",\"Cancel\",[33,[\"action\"],[[28,[null]],\"selectAndOpenPopup\"],null]]]],false],[0,\"\\n                    \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"            \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-box\"],[13],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\",\"title\"],[\"cube-icon\",[28,[\"tournament\",\"sportName\"]],\"Sport\"]]],false],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"class\",\"itemIconClass\",\"itemName\",\"title\"],[\"align-end\",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],0],null],\"individual-icon\",\"people-icon\"],null],[33,[\"sport-type\"],[[28,[\"tournament\",\"sportType\"]]],null],\"Participation Type\"]]],false],[0,\"\\n    \"],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-box\"],[13],[0,\"\\n        \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-innerbox\"],[13],[0,\"\\n            \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\",\"title\"],[\"calander-icon\",[33,[\"get-date\"],[[28,[\"tournament\",\"tournamentDate\"]]],null],\"Tournament Date\"]]],false],[0,\"\\n            \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\",\"title\"],[\"clock-icon\",[33,[\"calculate-deadline\"],[[28,[\"tournament\",\"registrationStartDate\"]],[28,[\"tournament\",\"registrationEndDate\"]]],null],\"Deadline\"]]],false],[0,\"\\n        \"],[14],[0,\"\\n\"],[6,[\"link-to\"],[\"tournaments.tournament\",[28,[\"tournament\",\"tournamentId\"]]],[[\"class\"],[\"no-decoration\"]],{\"statements\":[[0,\"            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"br-blue bg-white ft-blue curved\",\"Details\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[18,\"default\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/tournament-card.hbs" } });
});
define("tournament-management-system/templates/components/tournament-navbar", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "LfuepEJl", "block": "{\"statements\":[[1,[33,[\"search-bar\"],null,[[\"minWait\",\"searchHandler\",\"searchBarId\",\"class\"],[200,[28,[\"searchTournaments\"]],\"tournament-search-bar\",\"bg-white max-width-lg\"]]],false],[0,\"\\n\"],[6,[\"if\"],[[33,[\"or\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],1],null],[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null]],null]],null,{\"statements\":[[6,[\"link-to\"],[\"tournaments.new\"],[[\"class\"],[\"no-decoration\"]],{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"buttonName\",\"isSVG\"],[\"br-green bg-green ft-white soft-corner\",\"Create\",true]],{\"statements\":[[0,\"            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"fill\",\"none\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"stroke-width\",\"1.75\"],[15,\"stroke\",\"currentColor\"],[13],[0,\"\\n                \"],[11,\"path\",[]],[15,\"stroke-linecap\",\"round\"],[15,\"stroke-linejoin\",\"round\"],[15,\"d\",\"M12 4.5v15m7.5-7.5h-15\"],[13],[14],[0,\"\\n            \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null]],\"locals\":[]},null],[18,\"default\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/tournament-navbar.hbs" } });
});
define("tournament-management-system/templates/components/tournament-participation-form", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "utXzdiTK", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"tournament-participation-form-wrapper\"],[13],[0,\"\\n    \"],[11,\"h2\",[]],[15,\"class\",\"tournament-participation-form-header\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],1],null]],null,{\"statements\":[[0,\"            Join Tournament\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],2],null]],null,{\"statements\":[[0,\"            Update Details\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],3],null]],null,{\"statements\":[[0,\"            Unregister Tournament\\n        \"]],\"locals\":[]},null]],\"locals\":[]}]],\"locals\":[]}],[0,\"    \"],[14],[0,\"\\n\"],[6,[\"if\"],[[33,[\"and\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],1],null],[33,[\"eq\"],[[28,[\"tournamentFormType\"]],1],null]],null]],null,{\"statements\":[[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"tournament-participation-team-buttons\"],[13],[0,\"\\n            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[[33,[\"concat\"],[\"no-border bg-transparent no-padding ft-sm \",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"teamRegistrationType\"]],0],null],\"ft-blue\",\"ft-grey\"],null]],null],\"Create Team\",[33,[\"action\"],[[28,[null]],\"setTeamRegistrationType\",0],null]]]],false],[0,\"\\n            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[[33,[\"concat\"],[\"no-border bg-transparent no-padding ft-sm \",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"teamRegistrationType\"]],1],null],\"ft-blue\",\"ft-grey\"],null]],null],\"Join Team\",[33,[\"action\"],[[28,[null]],\"setTeamRegistrationType\",1],null]]]],false],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"    \\n    \"],[11,\"form\",[]],[15,\"class\",\"tournament-participation-form\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],1],null]],null,{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],1],null]],null,{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"teamRegistrationType\"]],0],null]],null,{\"statements\":[[0,\"                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"teamName\",\"create-teamname\",\"Team Name\",true,[28,[\"validationErrors\",\"teamName\"]]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"teamRegistrationType\"]],1],null]],null,{\"statements\":[[6,[\"select-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"defaultValue\",\"isRequired\",\"errorMessage\"],[\"teamId\",\"join-teamlist\",\"Select Team\",[33,[\"object\"],[\"value\",\"\",\"selected\",true,\"disabled\",true,\"hidden\",true,\"displayName\",\"Select Team\"],null],true,[28,[\"validationErrors\",\"teamId\"]]]],{\"statements\":[[6,[\"each\"],[[28,[\"teams\"]]],null,{\"statements\":[[0,\"                            \"],[11,\"option\",[]],[16,\"value\",[28,[\"team\",\"teamId\"]],null],[13],[1,[28,[\"team\",\"teamName\"]],false],[14],[0,\"\\n\"]],\"locals\":[\"team\"]},null]],\"locals\":[]},null],[0,\"                \"]],\"locals\":[]},null]],\"locals\":[]}]],\"locals\":[]},null]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],2],null]],null,{\"statements\":[[0,\"            \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"defaultValue\",\"errorMessage\"],[\"teamName\",\"update-teamname\",\"New Team Name\",true,[28,[\"userParticipation\",\"teamName\"]],[28,[\"validationErrors\",\"teamName\"]]]]],false],[0,\"\\n        \"]],\"locals\":[]},null]],\"locals\":[]}],[0,\"        \"],[11,\"p\",[]],[15,\"class\",\"tournament-participation-form-info\"],[13],[0,\"\\n            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"fill\",\"currentColor\"],[13],[0,\"\\n                \"],[11,\"path\",[]],[15,\"fill-rule\",\"evenodd\"],[15,\"d\",\"M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12Zm8.706-1.442c1.146-.573 2.437.463 2.126 1.706l-.709 2.836.042-.02a.75.75 0 0 1 .67 1.34l-.04.022c-1.147.573-2.438-.463-2.127-1.706l.71-2.836-.042.02a.75.75 0 1 1-.671-1.34l.041-.022ZM12 9a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Z\"],[15,\"clip-rule\",\"evenodd\"],[13],[14],[0,\"\\n            \"],[14],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],1],null]],null,{\"statements\":[[0,\"                Registering for \\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],2],null]],null,{\"statements\":[[0,\"                Save changes for \\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],3],null]],null,{\"statements\":[[0,\"                Are you sure? Want to unregister \\n            \"]],\"locals\":[]},null]],\"locals\":[]}]],\"locals\":[]}],[0,\"            \"],[11,\"span\",[]],[13],[0,\"\\n                \"],[1,[28,[\"tournament\",\"tournamentName\"]],false],[0,\"\\n            \"],[14],[0,\"\\n        \"],[14],[0,\"\\n        \"],[11,\"div\",[]],[15,\"class\",\"tournament-participation-form-buttons\"],[13],[0,\"\\n            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-orange bg-white ft-orange soft-corner\",\"Cancel\",[33,[\"action\"],[[28,[null]],[28,[\"closeTournamentForm\"]]],null]]]],false],[0,\"\\n            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"type\",\"buttonName\"],[[33,[\"concat\"],[\"ft-white soft-corner \",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],3],null],\"br-red bg-red\",\"br-blue bg-blue\"],null]],null],\"submit\",\"Confirm\"]]],false],[0,\"\\n        \"],[14],[0,\"\\n    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/tournament-participation-form.hbs" } });
});
define("tournament-management-system/templates/components/tournament-schedule-card", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "9D89thUD", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"tournament-schedule-card-info-box\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-schedule-card-info\"],[13],[0,\"\\n        \"],[11,\"strong\",[]],[15,\"class\",\"ft-lg wt-500 ft-night-black\"],[13],[1,[33,[\"tournament-event-round\"],[[28,[\"schedule\",\"tournamentEventRound\"]]],null],false],[14],[0,\"\\n        \"],[1,[33,[\"icon-label-item\"],null,[[\"itemIconClass\",\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"icon-sm\",\"images/calander-icon.svg\",\"ft-night-black\",\"On Date :\",\"ft-dark-grey wt-300\",[33,[\"millis-to-date\"],[[28,[\"schedule\",\"tournamentEventDate\"]]],null]]]],false],[0,\"\\n        \"],[1,[33,[\"icon-label-item\"],null,[[\"itemIconClass\",\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"icon-sm\",\"images/location-icon.svg\",\"ft-night-black\",\"On Venue :\",\"ft-dark-grey wt-300\",[28,[\"schedule\",\"tournamentEventVenue\"]]]]],false],[0,\"\\n    \"],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-schedule-card-buttons\"],[13],[0,\"\\n        \"],[11,\"div\",[]],[15,\"class\",\"tournament-schedule-card-button-box\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"instance-lt\"],[[28,[\"schedule\",\"tournamentEventDate\"]]],null]],null,{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"isSVG\",\"title\"],[\"br-blue bg-blue ft-white soft-corner pd-sq-sm icon-sm\",true,\"Edit event schedule\"]],{\"statements\":[[0,\"                    \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 128 128\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                        \"],[11,\"g\",[]],[15,\"transform\",\"matrix(1.0999999999999996,0,0,1.0999999999999996,-0.899999999999963,-11.910000133514387)\"],[13],[0,\"\\n                            \"],[11,\"path\",[]],[15,\"d\",\"M91.4 63.5 64.5 36.6 1 100.1V127h26.9zM9 119v-15.6l55.5-55.5 15.6 15.6L24.6 119zM55 119h44v8H55zM109 119h8v8h-8zM71.6 29.6l26.9 26.9L116.8 38 90 11.2zm26.8 15.5L82.9 29.6l7.1-7.1L105.5 38z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[0,\"\\n                        \"],[14],[0,\"\\n                    \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"general-button\"],null,[[\"class\",\"isSVG\",\"title\"],[\"br-red bg-red ft-white soft-corner pd-sq-sm icon-sm\",true,\"Cancel event schedule\"]],{\"statements\":[[0,\"                    \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"version\",\"1.1\"],[15,\"xmlns:xlink\",\"http://www.w3.org/1999/xlink\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 512.001 512.001\"],[15,\"xml:space\",\"preserve\",\"http://www.w3.org/XML/1998/namespace\"],[13],[0,\"\\n                        \"],[11,\"g\",[]],[13],[0,\"\\n                            \"],[11,\"path\",[]],[15,\"d\",\"M284.286 256.002 506.143 34.144c7.811-7.811 7.811-20.475 0-28.285-7.811-7.81-20.475-7.811-28.285 0L256 227.717 34.143 5.859c-7.811-7.811-20.475-7.811-28.285 0-7.81 7.811-7.811 20.475 0 28.285l221.857 221.857L5.858 477.859c-7.811 7.811-7.811 20.475 0 28.285a19.938 19.938 0 0 0 14.143 5.857 19.94 19.94 0 0 0 14.143-5.857L256 284.287l221.857 221.857c3.905 3.905 9.024 5.857 14.143 5.857s10.237-1.952 14.143-5.857c7.811-7.811 7.811-20.475 0-28.285L284.286 256.002z\"],[15,\"fill\",\"currentColor\"],[15,\"opacity\",\"1\"],[15,\"data-original\",\"#000000\"],[13],[14],[0,\"\\n                        \"],[14],[0,\"\\n                    \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null],[0,\"        \"],[14],[0,\"\\n\"],[6,[\"general-button\"],null,[[\"class\",\"isSVG\",\"buttonName\",\"onClick\"],[\"br-grey bg-transparent ft-grey curved icon-xs pd-sm rrev\",true,[33,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],0],null],\"Participants\",\"Teams\"],null],[33,[\"action\"],[[28,[null]],\"toggleContestantsPanelOpen\"],null]]],{\"statements\":[[6,[\"if\"],[[28,[\"isContestantsPanelOpen\"]]],null,{\"statements\":[[0,\"                \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"fill\",\"none\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"stroke-width\",\"2.5\"],[15,\"stroke\",\"currentColor\"],[13],[0,\"\\n                    \"],[11,\"path\",[]],[15,\"stroke-linecap\",\"round\"],[15,\"stroke-linejoin\",\"round\"],[15,\"d\",\"m4.5 15.75 7.5-7.5 7.5 7.5\"],[13],[14],[0,\"\\n                \"],[14],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[0,\"                \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"fill\",\"none\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"stroke-width\",\"2.5\"],[15,\"stroke\",\"currentColor\"],[13],[0,\"\\n                    \"],[11,\"path\",[]],[15,\"stroke-linecap\",\"round\"],[15,\"stroke-linejoin\",\"round\"],[15,\"d\",\"m19.5 8.25-7.5 7.5-7.5-7.5\"],[13],[14],[0,\"\\n                \"],[14],[0,\"\\n\"]],\"locals\":[]}]],\"locals\":[]},null],[0,\"    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[6,[\"if\"],[[28,[\"isContestantsPanelOpen\"]]],null,{\"statements\":[[0,\"    \"],[11,\"div\",[]],[15,\"class\",\"event-contestansts-wrapper\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],0],null]],null,{\"statements\":[[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"participants\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"participants\"]]],null,{\"statements\":[[0,\"                    \"],[1,[33,[\"participant-card\"],null,[[\"participant\",\"refreshModel\"],[[28,[\"participant\"]],[33,[\"action\"],[[28,[null]],\"reloadContestants\"],null]]]],false],[0,\"\\n\"]],\"locals\":[\"participant\"]},null]],\"locals\":[]},{\"statements\":[[0,\"                \"],[11,\"p\",[]],[13],[0,\"No participants registered yet\"],[14],[0,\"\\n\"]],\"locals\":[]}]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],1],null]],null,{\"statements\":[[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"teams\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"teams\"]]],null,{\"statements\":[[0,\"                    \"],[1,[33,[\"team-card\"],null,[[\"team\",\"refreshModel\"],[[28,[\"team\"]],[33,[\"action\"],[[28,[null]],\"reloadContestants\"],null]]]],false],[0,\"\\n\"]],\"locals\":[\"team\"]},null]],\"locals\":[]},{\"statements\":[[0,\"                \"],[11,\"p\",[]],[13],[0,\"No teams registered yet\"],[14],[0,\"\\n\"]],\"locals\":[]}],[0,\"        \"]],\"locals\":[]},null]],\"locals\":[]}],[0,\"    \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/tournament-schedule-card.hbs" } });
});
define("tournament-management-system/templates/components/tournament-schedule-form", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "LLGRU0LJ", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"tournament-schedule-form-wrapper\"],[13],[0,\"\\n    \"],[11,\"h2\",[]],[15,\"class\",\"tournament-schedule-form-header\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentScheduleFormType\"]],1],null]],null,{\"statements\":[[0,\"            Schedule Event\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentScheduleFormType\"]],2],null]],null,{\"statements\":[[0,\"            Update Event\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentScheduleFormType\"]],3],null]],null,{\"statements\":[[0,\"            Cancel Event\\n        \"]],\"locals\":[]},null]],\"locals\":[]}]],\"locals\":[]}],[0,\"    \"],[14],[0,\"\\n    \\n    \"],[11,\"form\",[]],[15,\"class\",\"tournament-schedule-form\"],[13],[0,\"\\n\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentScheduleFormType\"]],1],null]],null,{\"statements\":[[0,\"            \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"tournamentEventDate\",\"schedule-eventdate\",\"Event Date (dd/mm/yyyy)\",true,[28,[\"validationErrors\",\"tournamentEventDate\"]]]]],false],[0,\"\\n            \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"tournamentEventVenue\",\"schedule-eventvenue\",\"Event Venue\",true,[28,[\"validationErrors\",\"tournamentEventVenue\"]]]]],false],[0,\"\\n            \"],[1,[33,[\"select-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"defaultValue\",\"options\",\"errorMessage\"],[\"tournamentEventRound\",\"schedule-eventround\",\"Event Round\",[33,[\"object\"],[\"value\",\"\",\"selected\",true,\"disabled\",true,\"hidden\",true,\"displayName\",\"Select Round\"],null],[28,[\"tournamentEventRoundOptions\"]],[28,[\"validationErrors\",\"tournamentEventRound\"]]]]],false],[0,\"\\n\\n            \"],[11,\"div\",[]],[15,\"class\",\"tournament-schedule-select-contestants-wrapper\"],[13],[0,\"\\n                \"],[11,\"p\",[]],[15,\"class\",\"tournament-schedule-select-contestants-label\"],[13],[0,\"Select event contestants \"],[11,\"span\",[]],[15,\"class\",\"ft-red\"],[13],[0,\"*\"],[14],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"tournament-schedule-select-contestants\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],0],null]],null,{\"statements\":[[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"participants\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"participants\"]]],null,{\"statements\":[[0,\"                                \"],[11,\"div\",[]],[15,\"class\",\"tournament-schedule-select-contestant-card\"],[13],[0,\"\\n                                    \"],[11,\"input\",[]],[15,\"type\",\"checkbox\"],[15,\"name\",\"participantId\"],[16,\"id\",[34,[\"schedule-participant-\",[28,[\"participant\",\"participantId\"]]]]],[16,\"value\",[28,[\"participant\",\"participantId\"]],null],[15,\"hidden\",\"true\"],[13],[14],[0,\"\\n                                    \"],[11,\"label\",[]],[16,\"for\",[34,[\"schedule-participant-\",[28,[\"participant\",\"participantId\"]]]]],[15,\"class\",\"schedule-contestant-card\"],[13],[0,\"\\n                                        \"],[11,\"div\",[]],[15,\"class\",\"schedule-contestant-card-info\"],[13],[0,\"\\n                                            \"],[11,\"span\",[]],[15,\"class\",\"ft-night-black\"],[13],[1,[28,[\"participant\",\"userName\"]],false],[14],[0,\"\\n                                        \"],[14],[0,\"\\n                                        \"],[11,\"div\",[]],[15,\"class\",\"schedule-contestant-card-extra\"],[13],[0,\"\\n\"],[0,\"                                        \"],[14],[0,\"\\n                                    \"],[14],[0,\"\\n                                \"],[14],[0,\"\\n\"]],\"locals\":[\"participant\"]},null]],\"locals\":[]},{\"statements\":[[0,\"                            \"],[11,\"p\",[]],[13],[0,\"No participants available\"],[14],[0,\"\\n\"]],\"locals\":[]}]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],1],null]],null,{\"statements\":[[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"teams\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"teams\"]]],null,{\"statements\":[[0,\"                                \"],[11,\"div\",[]],[15,\"class\",\"tournament-schedule-select-contestant-card\"],[13],[0,\"\\n                                    \"],[11,\"input\",[]],[15,\"type\",\"checkbox\"],[15,\"name\",\"teamId\"],[16,\"id\",[34,[\"schedule-team-\",[28,[\"team\",\"teamId\"]]]]],[16,\"value\",[28,[\"team\",\"teamId\"]],null],[15,\"hidden\",\"true\"],[13],[14],[0,\"\\n                                    \"],[11,\"label\",[]],[16,\"for\",[34,[\"schedule-team-\",[28,[\"team\",\"teamId\"]]]]],[15,\"class\",\"schedule-contestant-card\"],[13],[0,\"\\n                                        \"],[11,\"div\",[]],[15,\"class\",\"schedule-contestant-card-info\"],[13],[0,\"\\n                                            \"],[11,\"span\",[]],[15,\"class\",\"ft-night-black\"],[13],[1,[28,[\"team\",\"teamName\"]],false],[14],[0,\"\\n                                            \"],[11,\"span\",[]],[15,\"class\",\"ft-sm ft-grey\"],[13],[1,[28,[\"team\",\"userName\"]],false],[14],[0,\"\\n                                        \"],[14],[0,\"\\n                                        \"],[11,\"div\",[]],[15,\"class\",\"schedule-contestant-card-extra\"],[13],[0,\"\\n\"],[0,\"                                        \"],[14],[0,\"\\n                                    \"],[14],[0,\"\\n                                \"],[14],[0,\"\\n\"]],\"locals\":[\"team\"]},null]],\"locals\":[]},{\"statements\":[[0,\"                            \"],[11,\"p\",[]],[13],[0,\"No teams available\"],[14],[0,\"\\n\"]],\"locals\":[]}],[0,\"                    \"]],\"locals\":[]},null]],\"locals\":[]}],[0,\"                \"],[14],[0,\"\\n\"],[6,[\"if\"],[[33,[\"or\"],[[28,[\"validationErrors\",\"participantId\"]],[28,[\"validationErrors\",\"teamId\"]]],null]],null,{\"statements\":[[0,\"                    \"],[11,\"p\",[]],[15,\"class\",\"input-error\"],[13],[1,[33,[\"or\"],[[28,[\"validationErrors\",\"participantId\"]],[28,[\"validationErrors\",\"teamId\"]]],null],false],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"            \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[0,\"\\n        \"],[11,\"p\",[]],[15,\"class\",\"tournament-schedule-form-info\"],[13],[0,\"\\n            \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"fill\",\"currentColor\"],[13],[0,\"\\n                \"],[11,\"path\",[]],[15,\"fill-rule\",\"evenodd\"],[15,\"d\",\"M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12Zm8.706-1.442c1.146-.573 2.437.463 2.126 1.706l-.709 2.836.042-.02a.75.75 0 0 1 .67 1.34l-.04.022c-1.147.573-2.438-.463-2.127-1.706l.71-2.836-.042.02a.75.75 0 1 1-.671-1.34l.041-.022ZM12 9a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Z\"],[15,\"clip-rule\",\"evenodd\"],[13],[14],[0,\"\\n            \"],[14],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentScheduleFormType\"]],1],null]],null,{\"statements\":[[0,\"                Schedule the event\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentScheduleFormType\"]],2],null]],null,{\"statements\":[[0,\"                Want to save changes?\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentScheduleFormType\"]],3],null]],null,{\"statements\":[[0,\"                Are you sure? Want to cancel the schedule\\n            \"]],\"locals\":[]},null]],\"locals\":[]}]],\"locals\":[]}],[0,\"        \"],[14],[0,\"\\n\\n        \"],[11,\"div\",[]],[15,\"class\",\"tournament-schedule-form-buttons\"],[13],[0,\"\\n            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-orange bg-white ft-orange soft-corner\",\"Cancel\",[33,[\"action\"],[[28,[null]],[28,[\"closeTournamentScheduleForm\"]]],null]]]],false],[0,\"\\n            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"type\",\"buttonName\"],[[33,[\"concat\"],[\"ft-white soft-corner \",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentScheduleFormType\"]],3],null],\"br-red bg-red\",\"br-blue bg-blue\"],null]],null],\"submit\",\"Confirm\"]]],false],[0,\"\\n        \"],[14],[0,\"\\n    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[18,\"default\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/tournament-schedule-form.hbs" } });
});
define("tournament-management-system/templates/components/user-card", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "iBx5crRq", "block": "{\"statements\":[[11,\"div\",[]],[15,\"class\",\"user-card-image-box\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"user-card-profile-img\"],[13],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[11,\"div\",[]],[15,\"class\",\"user-card-detail-box\"],[13],[0,\"\\n    \"],[11,\"p\",[]],[15,\"class\",\"user-card-item\"],[13],[11,\"span\",[]],[15,\"class\",\"ft-grey\"],[13],[1,[28,[\"user\",\"userId\"]],false],[14],[0,\" - \"],[11,\"strong\",[]],[15,\"class\",\"capitalize ft-lg wt-500\"],[13],[1,[28,[\"user\",\"userName\"]],false],[14],[14],[0,\"\\n    \"],[11,\"p\",[]],[15,\"class\",\"user-card-item ft-sm ft-grey\"],[13],[1,[28,[\"user\",\"email\"]],false],[14],[0,\"\\n    \"],[11,\"span\",[]],[15,\"class\",\"user-card-item ft-sm wt-300\"],[13],[1,[33,[\"user-role\"],[[28,[\"user\",\"role\"]]],null],false],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[18,\"default\"],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[\"default\"],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/components/user-card.hbs" } });
});
define("tournament-management-system/templates/dashboard", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "z+Vh3hAV", "block": "{\"statements\":[[11,\"h1\",[]],[13],[0,\"Dash board\"],[14],[0,\"\\n\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/dashboard.hbs" } });
});
define("tournament-management-system/templates/index", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "fF1c3na7", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"section container home\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"home-details-container\"],[13],[0,\"\\n        \"],[11,\"h1\",[]],[15,\"class\",\"home-details-title\"],[13],[0,\"Tournament Management System.\"],[14],[0,\"\\n        \"],[11,\"p\",[]],[15,\"class\",\"ft-4xl\"],[13],[0,\"Manage all your tournaments in single place\"],[14],[0,\"\\n        \"],[11,\"p\",[]],[15,\"class\",\"ft-xl\"],[13],[0,\"Register you organization now\"],[14],[0,\"\\n\"],[6,[\"link-to\"],[\"register\"],[[\"class\"],[\"no-decoration\"]],{\"statements\":[[6,[\"general-button\"],null,[[\"class\",\"buttonName\",\"isSVG\"],[\"br-blue bg-blue ft-white rrev\",\"Get Started\",true]],{\"statements\":[[0,\"                \"],[11,\"svg\",[]],[15,\"xmlns\",\"http://www.w3.org/2000/svg\",\"http://www.w3.org/2000/xmlns/\"],[15,\"viewBox\",\"0 0 24 24\"],[15,\"fill\",\"currentColor\"],[13],[0,\"\\n                    \"],[11,\"path\",[]],[15,\"fill-rule\",\"evenodd\"],[15,\"d\",\"M12.97 3.97a.75.75 0 0 1 1.06 0l7.5 7.5a.75.75 0 0 1 0 1.06l-7.5 7.5a.75.75 0 1 1-1.06-1.06l6.22-6.22H3a.75.75 0 0 1 0-1.5h16.19l-6.22-6.22a.75.75 0 0 1 0-1.06Z\"],[15,\"clip-rule\",\"evenodd\"],[13],[14],[0,\"\\n                \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null],[0,\"    \"],[14],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"home-image-container\"],[13],[0,\"\\n\\n    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/index.hbs" } });
});
define("tournament-management-system/templates/login", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "knNZpMyS", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section form-bg\"],[13],[0,\"\\n\"],[6,[\"form-model\"],null,[[\"class\",\"formHeader\",\"onSubmit\"],[\"login-form\",\"Login\",[33,[\"action\"],[[28,[null]],\"handleSubmit\"],null]]],{\"statements\":[[0,\"        \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"errorMessage\"],[\"email\",\"login-email\",\"Email\",[28,[\"validationErrors\",\"email\"]]]]],false],[0,\"\\n        \"],[1,[33,[\"password-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"errorMessage\"],[\"password\",\"login-password\",\"Password\",[28,[\"validationErrors\",\"password\"]]]]],false],[0,\"\\n        \"],[11,\"p\",[]],[15,\"class\",\"auth-switch\"],[13],[0,\"\\n            Don't have an account? \"],[6,[\"link-to\"],[\"register\"],null,{\"statements\":[[0,\"Register\"]],\"locals\":[]},null],[0,\"\\n        \"],[14],[0,\" \\n        \"],[1,[33,[\"general-button\"],null,[[\"type\",\"buttonName\",\"class\"],[\"submit\",\"Login\",\"bg-blue ft-white full-width no-border\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[14],[0,\"\\n\"],[1,[26,[\"outlet\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/login.hbs" } });
});
define("tournament-management-system/templates/not-found", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "CFM9vhUO", "block": "{\"statements\":[[11,\"h1\",[]],[13],[0,\"404 ☹️\"],[14],[0,\"\\n\"],[11,\"h2\",[]],[13],[0,\"Page not found\"],[14],[0,\"\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/not-found.hbs" } });
});
define("tournament-management-system/templates/organizations", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "bXjEzFED", "block": "{\"statements\":[[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/organizations.hbs" } });
});
define("tournament-management-system/templates/organizations/index", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "58i2bX95", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section bg-pale-white organizations\"],[13],[0,\"\\n    \"],[1,[33,[\"organization-navbar\"],null,[[\"searchOrganizations\"],[[33,[\"action\"],[[28,[null]],\"searchOrganizations\"],null]]]],false],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"organization-card-wrapper\"],[13],[0,\"\\n\"],[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"organizations\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"organizations\"]]],null,{\"statements\":[[0,\"                \"],[1,[33,[\"organization-card\"],null,[[\"class\",\"organization\",\"changeOrganizationStatus\"],[\"bg-white\",[28,[\"organization\"]],[33,[\"action\"],[[28,[null]],\"changeOrganizationStatus\"],null]]]],false],[0,\"\\n\"]],\"locals\":[\"organization\"]},null]],\"locals\":[]},{\"statements\":[[0,\"            \"],[11,\"p\",[]],[13],[0,\"No organizations found\"],[14],[0,\"\\n\"]],\"locals\":[]}],[0,\"    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/organizations/index.hbs" } });
});
define("tournament-management-system/templates/organizations/organization", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "jqWI5tZR", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section organization bg-pale-white\"],[13],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"org-wrapper bg-white\"],[13],[0,\"\\n        \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemNameClass\",\"itemName\"],[\"no-icon\",\"ft-3xl wt-600 txt-wrap capitalize\",[28,[\"organization\",\"organizationName\"]]]]],false],[0,\"\\n        \"],[11,\"div\",[]],[15,\"class\",\"org-details-wrapper\"],[13],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"org-details-box\"],[13],[0,\"\\n                \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemNameClass\",\"itemName\"],[\"location-icon\",\"txt-wrap\",[28,[\"organization\",\"organizationAddress\"]]]]],false],[0,\"\\n                \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemName\"],[\"calander-icon\",[28,[\"organization\",\"startedYear\"]]]]],false],[0,\"\\n                \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemNameClass\",\"itemName\"],[\"badge-icon\",\"uppercase ft-sm\",[33,[\"organization-status\"],[[28,[\"organization\",\"organizationStatus\"]]],null]]]],false],[0,\"\\n                \"],[11,\"p\",[]],[15,\"class\",\"txt-wrap ft-sm ft-light-grey\"],[13],[0,\"Created at \"],[1,[33,[\"millis-to-date-time\"],[[28,[\"organization\",\"organizationCreatedAt\"]]],null],false],[14],[0,\"\\n            \"],[14],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"org-admin-details-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"org-admin-details-inner-box\"],[13],[0,\"\\n                    \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemNameClass\",\"itemName\"],[\"admin-icon\",\"elipsis\",[33,[\"or\"],[[28,[\"admin\",\"userName\"]],\"Not assigned\"],null]]]],false],[0,\"\\n                    \"],[1,[33,[\"card-item\"],null,[[\"itemIconClass\",\"itemNameClass\",\"itemName\"],[\"mail-icon\",\"elipsis\",[33,[\"or\"],[[28,[\"admin\",\"email\"]],\"Not assigned\"],null]]]],false],[0,\"\\n                \"],[14],[0,\"  \\n                \"],[11,\"div\",[]],[15,\"class\",\"org-admin-details-button-box\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"or\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],1],null],[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],2],null]],null]],null,{\"statements\":[[0,\"                        \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-blue bg-blue ft-white\",\"Edit Organization\",[33,[\"action\"],[[28,[null]],\"setIsOrganizationFormOpen\",true],null]]]],false],[0,\"\\n\"]],\"locals\":[]},null],[0,\"                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n        \"],[14],[0,\"\\n    \"],[14],[0,\"\\n    \"],[1,[33,[\"organization-user-navbar\"],null,[[\"searchOrganizationUsers\",\"openAddUserForm\"],[[33,[\"action\"],[[28,[null]],\"searchOrganizationUsers\"],null],[33,[\"action\"],[[28,[null]],\"setOrganizationUserFormType\",1],null]]]],false],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"org-users-wrapper\"],[13],[0,\"\\n\"],[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"users\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"users\"]]],null,{\"statements\":[[0,\"                \"],[1,[33,[\"user-card\"],null,[[\"class\",\"user\"],[\"bg-white\",[28,[\"user\"]]]]],false],[0,\"\\n\"]],\"locals\":[\"user\"]},null]],\"locals\":[]},{\"statements\":[[0,\"            \"],[11,\"p\",[]],[13],[0,\"No users found\"],[14],[0,\"\\n\"]],\"locals\":[]}],[0,\"    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\\n\"],[6,[\"if\"],[[28,[\"isOrganizationFormOpen\"]]],null,{\"statements\":[[0,\"    \"],[1,[33,[\"organization-form\"],null,[[\"organization\",\"admin\",\"handleOrganizationUpdate\",\"closeOrganizationForm\",\"refreshModel\"],[[28,[\"organization\"]],[28,[\"admin\"]],[33,[\"action\"],[[28,[null]],\"updateOrganizationDetails\"],null],[33,[\"action\"],[[28,[null]],\"setIsOrganizationFormOpen\",false],null],[33,[\"action\"],[[28,[null]],\"refreshModel\"],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"organizationUserFormType\"]],1],null]],null,{\"statements\":[[0,\"    \"],[1,[33,[\"organization-user-form\"],null,[[\"organizationUserFormType\",\"organization\",\"closeOrganizationUserForm\",\"refreshModel\"],[[28,[\"organizationUserFormType\"]],[28,[\"organization\"]],[33,[\"action\"],[[28,[null]],\"setOrganizationUserFormType\",0],null],[33,[\"action\"],[[28,[null]],\"refreshModel\"],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"organizationUserFormType\"]],2],null]],null,{\"statements\":[[0,\"    \"],[1,[33,[\"organization-user-form\"],null,[[\"organizationUserFormType\",\"organization\",\"user\",\"closeOrganizationUserForm\",\"refreshModel\"],[[28,[\"organizationUserFormType\"]],[28,[\"organization\"]],[28,[\"selectedUser\"]],[33,[\"action\"],[[28,[null]],\"setOrganizationUserFormType\",0],null],[33,[\"action\"],[[28,[null]],\"refreshModel\"],null]]]],false],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]}]],\"locals\":[]}],[0,\"\\n\"],[1,[26,[\"outlet\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/organizations/organization.hbs" } });
});
define("tournament-management-system/templates/organizations/organization/user", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "NeIVSBfZ", "block": "{\"statements\":[[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/organizations/organization/user.hbs" } });
});
define("tournament-management-system/templates/profile", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "2TnGtxcJ", "block": "{\"statements\":[[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/profile.hbs" } });
});
define("tournament-management-system/templates/register", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "MdQkycjd", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section form-bg\"],[13],[0,\"\\n\"],[6,[\"form-model\"],null,[[\"class\",\"formHeader\",\"onSubmit\"],[\"register-form\",\"Register\",[33,[\"action\"],[[28,[null]],\"handleSubmit\"],null]]],{\"statements\":[[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"auth-wrapper\"],[13],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"auth-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"auth-innerbox\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"userName\",\"register-username\",\"Full Name\",true,[28,[\"validationErrors\",\"userName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"dateOfBirth\",\"register-dateofbirth\",\"Date Of Birth (dd/mm/yyyy)\",true,[28,[\"validationErrors\",\"dateOfBirth\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"phoneNumber\",\"register-phonenumber\",\"Phone Number\",true,[28,[\"validationErrors\",\"phoneNumber\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"email\",\"register-email\",\"Email\",true,[28,[\"validationErrors\",\"email\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"password-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"password\",\"register-password\",\"Password\",true,[28,[\"validationErrors\",\"password\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"password-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"confirmPassword\",\"register-confirmpassword\",\"Confirm Password\",true,[28,[\"validationErrors\",\"confirmPassword\"]]]]],false],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"auth-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"auth-innerbox\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"organizationName\",\"register-organizationname\",\"Organization Name\",true,[28,[\"validationErrors\",\"organizationName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"organizationAddress\",\"register-organizationaddress\",\"Organization Address\",true,[28,[\"validationErrors\",\"organizationAddress\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"startedYear\",\"register-startedyear\",\"Started Year\",true,[28,[\"validationErrors\",\"startedYear\"]]]]],false],[0,\"\\n                \"],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"space-filler\"],[13],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"auth-innerbox\"],[13],[0,\"\\n                    \"],[11,\"p\",[]],[15,\"class\",\"auth-switch\"],[13],[0,\"\\n                        Already have an account? \"],[6,[\"link-to\"],[\"login\"],null,{\"statements\":[[0,\"Login\"]],\"locals\":[]},null],[0,\"\\n                    \"],[14],[0,\" \\n                    \"],[1,[33,[\"general-button\"],null,[[\"type\",\"buttonName\",\"class\"],[\"submit\",\"Register\",\"bg-blue ft-white full-width no-border\"]]],false],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[14],[0,\"\\n\"],[1,[26,[\"outlet\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/register.hbs" } });
});
define("tournament-management-system/templates/tournaments", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "3slSQHbl", "block": "{\"statements\":[[0,\"\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/tournaments.hbs" } });
});
define("tournament-management-system/templates/tournaments/index", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "y/Ae8/ok", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section bg-pale-white flex column\"],[13],[0,\"\\n    \"],[1,[33,[\"tournament-navbar\"],null,[[\"searchTournaments\"],[[33,[\"action\"],[[28,[null]],\"searchTournaments\"],null]]]],false],[0,\"\\n    \"],[11,\"div\",[]],[15,\"class\",\"tournament-card-wrapper\"],[13],[0,\"\\n\"],[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"tournaments\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"tournaments\"]]],null,{\"statements\":[[0,\"                \"],[1,[33,[\"tournament-card\"],null,[[\"class\",\"tournament\",\"openCancelPopup\",\"setSelectedTournament\"],[\"bg-white\",[28,[\"tournament\"]],[33,[\"action\"],[[28,[null]],\"setIsCancelPopupOpen\",true],null],[33,[\"action\"],[[28,[null]],\"setSelectedTournament\"],null]]]],false],[0,\"\\n\"]],\"locals\":[\"tournament\"]},null]],\"locals\":[]},{\"statements\":[[0,\"            \"],[11,\"p\",[]],[13],[0,\"No tournament available\"],[14],[0,\"\\n\"]],\"locals\":[]}],[0,\"    \"],[14],[0,\"\\n\"],[14],[0,\"\\n\\n\"],[6,[\"if\"],[[28,[\"isCancelPopupOpen\"]]],null,{\"statements\":[[6,[\"popup-box\"],null,[[\"class\",\"closePopup\"],[\"cancel-tournament-popup-box\",[33,[\"action\"],[[28,[null]],\"setIsCancelPopupOpen\",false],null]]],{\"statements\":[[0,\"        \"],[11,\"h2\",[]],[15,\"class\",\"ft-night-black ft-3xl wt-400\"],[13],[0,\"Cancel Tournament\"],[14],[0,\"\\n        \"],[11,\"div\",[]],[15,\"class\",\"cancel-tournament-popup-details\"],[13],[0,\"\\n            \"],[11,\"p\",[]],[15,\"class\",\"ft-grey ft-sm\"],[13],[0,\"Are you sure? want to cancel \"],[6,[\"link-to\"],[\"tournaments.tournament\",[28,[\"selectedTournament\",\"tournamentId\"]]],[[\"class\"],[\"no-decoration ft-blue\"]],{\"statements\":[[1,[28,[\"selectedTournament\",\"tournamentName\"]],false]],\"locals\":[]},null],[14],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"cancel-tournament-popup-buttons\"],[13],[0,\"\\n                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-blue bg-blue ft-white soft-corner\",\"Go back\",[33,[\"action\"],[[28,[null]],\"setIsCancelPopupOpen\",false],null]]]],false],[0,\"\\n                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-red bg-red ft-white soft-corner\",\"Cancel\",[33,[\"action\"],[[28,[null]],\"cancelTournament\"],null]]]],false],[0,\"\\n            \"],[14],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null]],\"locals\":[]},null],[0,\"\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/tournaments/index.hbs" } });
});
define("tournament-management-system/templates/tournaments/new", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "G+KV0lIi", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"container section form-bg\"],[13],[0,\"\\n\"],[6,[\"form-model\"],null,[[\"class\",\"formHeader\",\"onSubmit\"],[\"tournament-form-model\",\"Create Tournament\",[33,[\"action\"],[[28,[null]],\"createNewTournament\"],null]]],{\"statements\":[[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"tournament-form-wrapper\"],[13],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"tournament-form-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"tournament-form-inner-box\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"tournamentName\",\"create-tournamentname\",\"Tournament Name\",true,[28,[\"validationErrors\",\"tournamentName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"maxParticipation\",\"create-maxparticipation\",\"Participation Limit\",true,[28,[\"validationErrors\",\"maxParticipation\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"errorMessage\"],[\"tournamentDate\",\"create-tournamentdate\",\"Start Date\",[28,[\"validationErrors\",\"tournamentDate\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"errorMessage\"],[\"tournamentVenue\",\"create-tournamentvenue\",\"Venue\",[28,[\"validationErrors\",\"tournamentVenue\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"registrationStartDate\",\"create-registrationstartdate\",\"Registration Opening (dd/mm/yyyy)\",true,[28,[\"validationErrors\",\"registrationStartDate\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"registrationEndDate\",\"create-registrationenddate\",\"Registration Closing (dd/mm/yyyy)\",true,[28,[\"validationErrors\",\"registrationEndDate\"]]]]],false],[0,\"\\n                \"],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"tournament-form-inner-box\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"sportName\",\"create-sportname\",\"Sport Name\",true,[28,[\"validationErrors\",\"sportName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"sportType\",\"create-sporttype\",\"Sport Type\",true,[28,[\"validationErrors\",\"sportType\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"errorMessage\"],[\"teamSize\",\"create-teamsize\",\"Team Size\",true,[28,[\"validationErrors\",\"teamSize\"]]]]],false],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n\\n            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"type\",\"buttonName\"],[\"br-blue bg-blue ft-white soft-corner\",\"submit\",\"Create\"]]],false],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[14],[0,\"\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/tournaments/new.hbs" } });
});
define("tournament-management-system/templates/tournaments/tournament", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "AEoMbFIK", "block": "{\"statements\":[[6,[\"if\"],[[28,[\"tournament\"]]],null,{\"statements\":[[0,\"\\n    \"],[11,\"section\",[]],[15,\"class\",\"section container tournament bg-pale-white\"],[13],[0,\"\\n        \"],[11,\"div\",[]],[15,\"class\",\"tournament-wrapper\"],[13],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"tournament-poster-image-container\"],[13],[0,\"\\n                \"],[11,\"img\",[]],[15,\"class\",\"tournament-poster-image\"],[16,\"src\",[33,[\"prepend-root\"],[[28,[\"tournament\",\"tournamentPoster\"]]],null],null],[15,\"alt\",\"Tournament Image\"],[13],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"tournament-poster-overlay\"],[13],[0,\"\\n                    \"],[11,\"h1\",[]],[15,\"class\",\"tournament-poster-header ft-white capitalize\"],[13],[1,[28,[\"tournament\",\"tournamentName\"]],false],[14],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"tournament-detail-container\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"tournament-detail-wrapper\"],[13],[0,\"\\n                    \"],[11,\"div\",[]],[15,\"class\",\"tournament-detail-box bg-white\"],[13],[0,\"\\n                        \"],[11,\"div\",[]],[15,\"class\",\"tournament-detail-header-box\"],[13],[0,\"\\n                            \"],[11,\"div\",[]],[15,\"class\",\"tournament-detail-header-icon\"],[13],[14],[0,\"\\n                            \"],[11,\"h2\",[]],[15,\"class\",\"tournament-detail-header\"],[13],[1,[28,[\"tournament\",\"tournamentName\"]],false],[14],[0,\"\\n                        \"],[14],[0,\"\\n                        \"],[1,[33,[\"icon-label-item\"],null,[[\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"images/location-icon.svg\",\"ft-night-black\",\"Venue:\",\"ft-dark-grey wt-300\",[33,[\"or\"],[[28,[\"tournament\",\"tournamentVenue\"]],\"Not Specified\"],null]]]],false],[0,\"\\n                        \"],[1,[33,[\"icon-label-item\"],null,[[\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"images/calander-icon.svg\",\"ft-night-black\",\"Date:\",\"ft-dark-grey wt-300\",[33,[\"millis-to-date\"],[[28,[\"tournament\",\"tournamentDate\"]],\"Not Specified\"],null]]]],false],[0,\"\\n                        \"],[1,[33,[\"icon-label-item\"],null,[[\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"images/rocket-icon.svg\",\"ft-night-black\",\"Open:\",\"ft-dark-grey wt-300\",[33,[\"millis-to-date-time\"],[[28,[\"tournament\",\"registrationStartDate\"]]],null]]]],false],[0,\"\\n                        \"],[1,[33,[\"icon-label-item\"],null,[[\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"images/stop-watch-icon.svg\",\"ft-night-black\",\"Close:\",\"ft-dark-grey wt-300\",[33,[\"millis-to-date-time\"],[[28,[\"tournament\",\"registrationEndDate\"]]],null]]]],false],[0,\"\\n                        \"],[11,\"div\",[]],[15,\"class\",\"tournament-detail-tags-box\"],[13],[0,\"\\n                            \"],[11,\"div\",[]],[15,\"class\",\"tournament-detail-tag\"],[13],[1,[28,[\"tournament\",\"sportName\"]],false],[14],[0,\"\\n                            \"],[11,\"div\",[]],[15,\"class\",\"tournament-detail-tag\"],[13],[1,[33,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],0],null],\"Individual\",\"Team\"],null],false],[14],[0,\"\\n                        \"],[14],[0,\"\\n                    \"],[14],[0,\"\\n                    \"],[11,\"div\",[]],[15,\"class\",\"tournament-registration-box bg-white\"],[13],[0,\"\\n                        \"],[11,\"div\",[]],[15,\"class\",\"tournament-registration-button-box\"],[13],[0,\"\\n\"],[6,[\"unless\"],[[28,[\"userParticipation\",\"userRegistered\"]]],null,{\"statements\":[[6,[\"if\"],[[33,[\"instance-lt\"],[[28,[\"tournament\",\"registrationStartDate\"]]],null]],null,{\"statements\":[[0,\"                                    \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"disabled\"],[\"br-light-grey bg-pale-white ft-light-grey\",\"Comming soon\",true]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"instance-gt\"],[[28,[\"tournament\",\"registrationEndDate\"]]],null]],null,{\"statements\":[[0,\"                                    \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"disabled\"],[\"br-light-grey bg-pale-white ft-light-grey\",\"Closed\",true]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[0,\"                                    \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-blue bg-blue ft-white\",\"Register\",[33,[\"action\"],[[28,[null]],\"setTournamentFormType\",1],null]]]],false],[0,\"\\n                                \"]],\"locals\":[]}]],\"locals\":[]}]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"and\"],[[33,[\"instance-gt\"],[[28,[\"tournament\",\"registrationStartDate\"]]],null],[33,[\"instance-lt\"],[[28,[\"tournament\",\"registrationEndDate\"]]],null]],null]],null,{\"statements\":[[6,[\"if\"],[[33,[\"and\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],1],null],[33,[\"eq\"],[[28,[\"userParticipation\",\"teamLeaderId\"]],[28,[\"userInfo\",\"userId\"]]],null]],null]],null,{\"statements\":[[0,\"                                    \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-green bg-green ft-white\",\"Update Details\",[33,[\"action\"],[[28,[null]],\"setTournamentFormType\",2],null]]]],false],[0,\"\\n\"]],\"locals\":[]},null],[0,\"                                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-red bg-white ft-red\",\"Unregister\",[33,[\"action\"],[[28,[null]],\"setTournamentFormType\",3],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[0,\"                                \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\"],[\"br-orange bg-white ft-orange\",\"Registered\"]]],false],[0,\"\\n                            \"]],\"locals\":[]}]],\"locals\":[]}],[0,\"                        \"],[14],[0,\"\\n                        \"],[11,\"div\",[]],[15,\"class\",\"tournament-registration-details-box\"],[13],[0,\"\\n                            \"],[1,[33,[\"icon-label-item\"],null,[[\"iconLabelItemBoxClass\",\"iconBackground\",\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"col ai-start jc-center\",true,\"images/cube-icon.svg\",\"ft-xs ft-grey wt-300\",\"Sport\",\"ft-dark-grey\",[28,[\"tournament\",\"sportName\"]]]]],false],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],0],null]],null,{\"statements\":[[0,\"                                \"],[1,[33,[\"icon-label-item\"],null,[[\"iconLabelItemBoxClass\",\"iconBackground\",\"iconUrl\",\"itemValueClass\",\"itemValue\"],[\"col ai-start jc-center\",true,\"images/individual-icon.svg\",\"ft-dark-grey\",\"Individual\"]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[0,\"                                \"],[1,[33,[\"icon-label-item\"],null,[[\"iconLabelItemBoxClass\",\"iconBackground\",\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"col ai-start jc-center\",true,\"images/people-icon.svg\",\"ft-xs ft-grey wt-300\",\"Team\",\"ft-dark-grey\",[33,[\"concat\"],[[28,[\"tournament\",\"teamSize\"]],\" members\"],null]]]],false],[0,\"\\n\"]],\"locals\":[]}],[0,\"                            \"],[1,[33,[\"icon-label-item\"],null,[[\"iconLabelItemBoxClass\",\"iconBackground\",\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"col ai-start jc-center\",true,\"images/exclamation-triangle-icon.svg\",\"ft-xs ft-grey wt-300\",\"Participation Limit\",\"ft-dark-grey\",[28,[\"tournament\",\"maxParticipation\"]]]]],false],[0,\"\\n                            \"],[1,[33,[\"icon-label-item\"],null,[[\"iconLabelItemBoxClass\",\"iconBackground\",\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"col ai-start jc-center\",true,\"images/bw-person-icon.svg\",\"ft-xs ft-grey wt-300\",\"Registered\",\"ft-dark-grey\",[28,[\"tournament\",\"registeredCount\"]]]]],false],[0,\"        \\n                            \"],[1,[33,[\"icon-label-item\"],null,[[\"iconLabelItemBoxClass\",\"iconBackground\",\"iconUrl\",\"itemLabelClass\",\"itemLabel\",\"itemValueClass\",\"itemValue\"],[\"col ai-start jc-center\",true,\"images/clock-icon.svg\",\"ft-xs ft-grey wt-300\",\"Deadline\",\"ft-dark-grey\",[33,[\"calculate-deadline\"],[[28,[\"tournament\",\"registrationStartDate\"]],[28,[\"tournament\",\"registrationEndDate\"]]],null]]]],false],[0,\"\\n                        \"],[14],[0,\"\\n                    \"],[14],[0,\"\\n                \"],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"event-details-wrapper\"],[13],[0,\"\\n                    \"],[11,\"div\",[]],[15,\"class\",\"event-navbar\"],[13],[0,\"\\n                        \"],[11,\"div\",[]],[15,\"class\",\"event-navbar-buttons\"],[13],[0,\"\\n                            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[[33,[\"concat\"],[\"bg-transparent curved \",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"eventPageType\"]],0],null],\"br-blue ft-blue\",\"br-transparent ft-night-black\"],null]],null],\"Contestants\",[33,[\"action\"],[[28,[null]],\"setEventPageType\",0],null]]]],false],[0,\"\\n                            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[[33,[\"concat\"],[\"bg-transparent curved \",[33,[\"if\"],[[33,[\"eq\"],[[28,[\"eventPageType\"]],1],null],\"br-blue ft-blue\",\"br-transparent ft-night-black\"],null]],null],\"Schedules\",[33,[\"action\"],[[28,[null]],\"setEventPageType\",1],null]]]],false],[0,\"\\n                        \"],[14],[0,\"\\n                        \"],[11,\"div\",[]],[15,\"class\",\"event-navbar-options\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"eventPageType\"]],0],null]],null,{\"statements\":[[0,\"                                \"],[1,[33,[\"search-bar\"],null,[[\"minWait\",\"searchHandler\"],[400,[33,[\"action\"],[[28,[null]],\"searchContestants\"],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"eventPageType\"]],1],null]],null,{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"userInfo\",\"role\"]],1],null]],null,{\"statements\":[[0,\"                                    \"],[1,[33,[\"general-button\"],null,[[\"class\",\"buttonName\",\"onClick\"],[\"br-green bg-green ft-white soft-corner\",\"Add Schedule\",[33,[\"action\"],[[28,[null]],\"setTournamentScheduleFormType\",1],null]]]],false],[0,\"\\n\"]],\"locals\":[]},null],[0,\"                            \"]],\"locals\":[]},null]],\"locals\":[]}],[0,\"                        \"],[14],[0,\"\\n                    \"],[14],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"eventPageType\"]],0],null]],null,{\"statements\":[[0,\"                        \"],[11,\"div\",[]],[15,\"class\",\"contestansts-wrapper\"],[13],[0,\"\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],0],null]],null,{\"statements\":[[6,[\"if\"],[[28,[\"userParticipation\",\"userRegistered\"]]],null,{\"statements\":[[0,\"                                    \"],[1,[33,[\"participant-card\"],null,[[\"class\",\"participant\",\"pinCard\",\"refreshModel\",\"title\"],[\"my-participation\",[28,[\"userParticipation\"]],true,[33,[\"action\"],[[28,[null]],\"refreshModel\"],null],\"My participation\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"participants\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"participants\"]]],null,{\"statements\":[[0,\"                                        \"],[1,[33,[\"participant-card\"],null,[[\"participant\",\"showContestantStatusControls\",\"refreshModel\"],[[28,[\"participant\"]],true,[33,[\"action\"],[[28,[null]],\"refreshModel\"],null]]]],false],[0,\"\\n\"]],\"locals\":[\"participant\"]},null]],\"locals\":[]},{\"statements\":[[0,\"                                    \"],[11,\"p\",[]],[13],[0,\"No participants registered yet\"],[14],[0,\"\\n\"]],\"locals\":[]}]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],1],null]],null,{\"statements\":[[6,[\"if\"],[[28,[\"userParticipation\",\"userRegistered\"]]],null,{\"statements\":[[0,\"                                    \"],[1,[33,[\"team-card\"],null,[[\"class\",\"team\",\"pinCard\",\"showRemoveMember\",\"refreshModel\",\"title\"],[\"my-participation\",[28,[\"userParticipation\"]],true,true,[33,[\"action\"],[[28,[null]],\"refreshModel\"],null],\"My team\"]]],false],[0,\"\\n\"]],\"locals\":[]},null],[0,\"                                \\n\"],[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"teams\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"teams\"]]],null,{\"statements\":[[0,\"                                        \"],[1,[33,[\"team-card\"],null,[[\"team\",\"showContestantStatusControls\",\"showRemoveMember\",\"refreshModel\"],[[28,[\"team\"]],true,true,[33,[\"action\"],[[28,[null]],\"refreshModel\"],null]]]],false],[0,\"\\n\"]],\"locals\":[\"team\"]},null]],\"locals\":[]},{\"statements\":[[0,\"                                    \"],[11,\"p\",[]],[13],[0,\"No teams registered yet\"],[14],[0,\"\\n\"]],\"locals\":[]}],[0,\"\\n                            \"]],\"locals\":[]},null]],\"locals\":[]}],[0,\"                        \"],[14],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"eventPageType\"]],1],null]],null,{\"statements\":[[0,\"                        \"],[11,\"div\",[]],[15,\"class\",\"schedules-wrapper\"],[13],[0,\"\\n\"],[6,[\"unless\"],[[33,[\"is-empty\"],[[28,[\"schedules\"]]],null]],null,{\"statements\":[[6,[\"each\"],[[28,[\"schedules\"]]],null,{\"statements\":[[0,\"                                    \"],[1,[33,[\"tournament-schedule-card\"],null,[[\"schedule\",\"refreshModel\"],[[28,[\"schedule\"]],[33,[\"action\"],[[28,[null]],\"refreshModel\"],null]]]],false],[0,\"\\n\"]],\"locals\":[\"schedule\"]},null]],\"locals\":[]},{\"statements\":[[0,\"                                \"],[11,\"p\",[]],[13],[0,\"No schedules assigned yet\"],[14],[0,\"\\n\"]],\"locals\":[]}],[0,\"                        \"],[14],[0,\"\\n                    \"]],\"locals\":[]},null]],\"locals\":[]}],[0,\"                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n        \"],[14],[0,\"\\n\\n    \"],[14],[0,\"\\n\\n\\n\"],[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],1],null]],null,{\"statements\":[[0,\"        \"],[1,[33,[\"tournament-participation-form\"],null,[[\"tournamentFormType\",\"teams\",\"closeTournamentForm\",\"onConfirmation\"],[[28,[\"tournamentFormType\"]],[33,[\"if\"],[[33,[\"eq\"],[[28,[\"tournament\",\"sportType\"]],1],null],[28,[\"teams\"]],null],null],[33,[\"action\"],[[28,[null]],\"setTournamentFormType\",0],null],[33,[\"action\"],[[28,[null]],\"handleTournamentRegistration\"],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],2],null]],null,{\"statements\":[[0,\"        \"],[1,[33,[\"tournament-participation-form\"],null,[[\"tournamentFormType\",\"userParticipation\",\"closeTournamentForm\",\"onConfirmation\"],[[28,[\"tournamentFormType\"]],[28,[\"userParticipation\"]],[33,[\"action\"],[[28,[null]],\"setTournamentFormType\",0],null],[33,[\"action\"],[[28,[null]],\"handleUpdateTournamentRegistration\"],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentFormType\"]],3],null]],null,{\"statements\":[[0,\"        \"],[1,[33,[\"tournament-participation-form\"],null,[[\"tournamentFormType\",\"userParticipation\",\"closeTournamentForm\",\"onConfirmation\"],[[28,[\"tournamentFormType\"]],[28,[\"userParticipation\"]],[33,[\"action\"],[[28,[null]],\"setTournamentFormType\",0],null],[33,[\"action\"],[[28,[null]],\"handleTournamentUnregistration\"],null]]]],false],[0,\"\\n\"]],\"locals\":[]},{\"statements\":[[6,[\"if\"],[[33,[\"eq\"],[[28,[\"tournamentScheduleFormType\"]],1],null]],null,{\"statements\":[[0,\"        \"],[1,[33,[\"tournament-schedule-form\"],null,[[\"tournamentScheduleFormType\",\"teams\",\"participants\",\"closeTournamentScheduleForm\",\"refreshModel\"],[[28,[\"tournamentScheduleFormType\"]],[28,[\"teams\"]],[28,[\"participants\"]],[33,[\"action\"],[[28,[null]],\"setTournamentScheduleFormType\",0],null],[33,[\"action\"],[[28,[null]],\"refreshModel\"],null]]]],false],[0,\"\\n    \"]],\"locals\":[]},null]],\"locals\":[]}]],\"locals\":[]}]],\"locals\":[]}],[0,\"\\n    \"],[1,[26,[\"outlet\"]],false],[0,\"\\n\\n\"]],\"locals\":[]},{\"statements\":[[0,\"\\n    \"],[11,\"h1\",[]],[13],[0,\"it's 404 ☹️\"],[14],[0,\"\\n    \"],[11,\"h2\",[]],[13],[0,\"Tournament not found\"],[14],[0,\"\\n\\n\"]],\"locals\":[]}]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/tournaments/tournament.hbs" } });
});
define("tournament-management-system/templates/tournaments/tournament/edit", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = Ember.HTMLBars.template({ "id": "sY7nepn9", "block": "{\"statements\":[[11,\"section\",[]],[15,\"class\",\"section container form-bg edit-tournament\"],[13],[0,\"\\n\"],[6,[\"form-model\"],null,[[\"class\",\"formHeader\",\"onSubmit\"],[\"tournament-form-model\",\"Edit Tournament\",[33,[\"action\"],[[28,[null]],\"editTournament\"],null]]],{\"statements\":[[0,\"        \"],[11,\"div\",[]],[15,\"class\",\"tournament-form-wrapper\"],[13],[0,\"\\n            \"],[11,\"div\",[]],[15,\"class\",\"tournament-form-box\"],[13],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"tournament-form-inner-box\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"defaultValue\",\"errorMessage\"],[\"tournamentName\",\"create-tournamentname\",\"Tournament Name\",true,[28,[\"tournament\",\"tournamentName\"]],[28,[\"validationErrors\",\"tournamentName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"defaultValue\",\"errorMessage\"],[\"maxParticipation\",\"create-maxparticipation\",\"Participation Limit\",true,[33,[\"if\"],[[28,[\"tournament\",\"maxParticipation\"]],[28,[\"tournament\",\"maxParticipation\"]],\"\"],null],[28,[\"validationErrors\",\"maxParticipation\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"defaultValue\",\"errorMessage\"],[\"tournamentDate\",\"create-tournamentdate\",\"Start Date\",[33,[\"millis-to-date\"],[[28,[\"tournament\",\"tournamentDate\"]],\"\"],null],[28,[\"validationErrors\",\"tournamentDate\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"defaultValue\",\"errorMessage\"],[\"tournamentVenue\",\"create-tournamentvenue\",\"Venue\",[28,[\"tournament\",\"tournamentVenue\"]],[28,[\"validationErrors\",\"tournamentVenue\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"defaultValue\",\"errorMessage\"],[\"registrationStartDate\",\"create-registrationstartdate\",\"Registration Opening (dd/mm/yyyy)\",true,[33,[\"millis-to-date\"],[[28,[\"tournament\",\"registrationStartDate\"]]],null],[28,[\"validationErrors\",\"registrationStartDate\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"defaultValue\",\"errorMessage\"],[\"registrationEndDate\",\"create-registrationenddate\",\"Registration Closing (dd/mm/yyyy)\",true,[33,[\"millis-to-date\"],[[28,[\"tournament\",\"registrationEndDate\"]]],null],[28,[\"validationErrors\",\"registrationEndDate\"]]]]],false],[0,\"\\n                \"],[14],[0,\"\\n                \"],[11,\"div\",[]],[15,\"class\",\"tournament-form-inner-box\"],[13],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"defaultValue\",\"errorMessage\"],[\"sportName\",\"create-sportname\",\"Sport Name\",true,[28,[\"tournament\",\"sportName\"]],[28,[\"validationErrors\",\"sportName\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"defaultValue\",\"errorMessage\"],[\"sportType\",\"create-sporttype\",\"Sport Type\",true,[28,[\"tournament\",\"sportType\"]],[28,[\"validationErrors\",\"sportType\"]]]]],false],[0,\"\\n                    \"],[1,[33,[\"text-input\"],null,[[\"inputName\",\"inputId\",\"labelName\",\"isRequired\",\"defaultValue\",\"errorMessage\"],[\"teamSize\",\"create-teamsize\",\"Team Size\",true,[28,[\"tournament\",\"teamSize\"]],[28,[\"validationErrors\",\"teamSize\"]]]]],false],[0,\"\\n                \"],[14],[0,\"\\n            \"],[14],[0,\"\\n            \"],[1,[33,[\"general-button\"],null,[[\"class\",\"type\",\"buttonName\"],[\"br-blue bg-blue ft-white soft-corner\",\"submit\",\"Save\"]]],false],[0,\"\\n        \"],[14],[0,\"\\n\"]],\"locals\":[]},null],[14],[0,\"\\n\\n\"],[1,[26,[\"outlet\"]],false],[0,\"\\n\"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}", "meta": { "moduleName": "tournament-management-system/templates/tournaments/tournament/edit.hbs" } });
});
define("tournament-management-system/utils/check-characters-present", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = checkCharactersPresent;
  function checkCharactersPresent(data, characters) {
    var _iteratorNormalCompletion = true;
    var _didIteratorError = false;
    var _iteratorError = undefined;

    try {
      for (var _iterator = data[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
        var ch = _step.value;

        if (characters.contains(ch)) {
          return true;
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

    return false;
  };
});
define("tournament-management-system/utils/check-date-valid", ["exports", "tournament-management-system/utils/get-month-days-count"], function (exports, _getMonthDaysCount) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = checkDateValid;

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

  function checkDateValid(date) {
    var _date$split$map = date.split("/").map(Number),
        _date$split$map2 = _slicedToArray(_date$split$map, 3),
        day = _date$split$map2[0],
        month = _date$split$map2[1],
        year = _date$split$map2[2];

    if (!year) {
      return false;
    }
    if (!month || month < 1 || month > 12) {
      return false;
    }
    if (!day || day < 1 || day > (0, _getMonthDaysCount.default)(month, year)) {
      return false;
    }
    return true;
  };
});
define("tournament-management-system/utils/controllable-timeout", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = ControllableTimeout;
  function ControllableTimeout(handler, timeout) {
    for (var _len = arguments.length, args = Array(_len > 2 ? _len - 2 : 0), _key = 2; _key < _len; _key++) {
      args[_key - 2] = arguments[_key];
    }

    var remainingTime = timeout;
    var startTime = void 0;
    var timeOut = void 0;

    this.pause = function () {
      clearTimeout(timeOut);
      remainingTime -= Date.now() - startTime;
    };

    this.resume = function () {
      startTime = Date.now();
      timeOut = setTimeout.apply(undefined, [handler, remainingTime].concat(args));
    };

    this.clear = function () {
      clearTimeout(timeOut);
      handler.apply(undefined, args);
    };

    this.start = function () {
      this.resume();
    };
  };
});
define("tournament-management-system/utils/date-time-to-mills", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = dateTimeToMills;
  function dateTimeToMills(dateTime) {
    var dateArr = dateTime.split(/[-\/:]/);
    var formattedDate = dateArr[1] + "-" + dateArr[0] + "-" + dateArr[2];
    if (dateArr.length > 3) {
      formattedDate += " " + dateArr[3] + ":";
    }
    if (dateArr.length > 4) {
      formattedDate += dateArr[4] + ":";
    }
    if (dateArr.length > 5) {
      formattedDate += "" + dateArr[5];
    }
    return new Date(formattedDate).getTime();
  }
});
define("tournament-management-system/utils/delay-calls", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = delayCalls;
  function delayCalls(minWait, callBack) {
    var lastCall = 0;
    var callBackTimeout = null;
    return function () {
      for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
        args[_key] = arguments[_key];
      }

      var currentTime = Date.now();
      if (currentTime - lastCall < minWait && callBackTimeout !== null) {
        clearTimeout(callBackTimeout);
      }
      lastCall = currentTime;
      callBackTimeout = setTimeout(function () {
        lastCall = Date.now();
        callBack.apply(undefined, args);
      }, minWait);
    };
  };
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
define("tournament-management-system/utils/get-month-days-count", ["exports", "tournament-management-system/utils/is-leap-year"], function (exports, _isLeapYear) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = getMonthDaysCount;
  function getMonthDaysCount(month, year) {
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
        return 28 + (0, _isLeapYear.default)(year);
      default:
        return 30;
    }
  }
});
define('tournament-management-system/utils/hash-set', ['exports'], function (exports) {
  'use strict';

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

    this.toString = function () {
      return Object.keys(_obj).join(', ');
    };
  }
});
define("tournament-management-system/utils/is-leap-year", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = isLeapYear;
  function isLeapYear(year) {
    if (year / 400) {
      return true;
    } else if (year / 100) {
      return false;
    } else if (year / 4) {
      return true;
    }
    return false;
  }
});
define("tournament-management-system/utils/limit-calls", ["exports"], function (exports) {
    "use strict";

    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = limitCalls;
    function limitCalls(minWait, callBack) {
        var lastCall = 0;
        return function () {
            var currentTime = Date.now();
            if (currentTime - lastCall < minWait) {
                return;
            }
            lastCall = currentTime;
            callBack.apply(undefined, arguments);
        };
    };
});
define("tournament-management-system/utils/millis-to-date", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = millisToDate;
  function millisToDate(millis) {
    if (!millis) {
      return "Not Specified";
    }
    return new Date(millis).toLocaleDateString();
  }
});
define("tournament-management-system/utils/millis-to-time", ["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = millisToTime;
  function millisToTime(millis) {
    if (!millis) {
      return "Not Specified";
    }
    return new Date(millis).toLocaleTimeString('en-US', { hour12: true });
  }
});
define('tournament-management-system/utils/rsa-encrypter', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = rsaEncrypter;

  function _toConsumableArray(arr) {
    if (Array.isArray(arr)) {
      for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) {
        arr2[i] = arr[i];
      }

      return arr2;
    } else {
      return Array.from(arr);
    }
  }

  function convertPemToBinary(pem) {
    var b64 = pem.replace(/-----BEGIN PUBLIC KEY-----/g, '').replace(/-----END PUBLIC KEY-----/g, '').replace(/\n/g, '').replace(/ /g, '');
    var binaryString = window.atob(b64);
    var len = binaryString.length;
    var bytes = new Uint8Array(len);
    for (var i = 0; i < len; i++) {
      bytes[i] = binaryString.charCodeAt(i);
    }
    return bytes.buffer;
  }

  function encryptData(pem, data) {
    var binaryDer = convertPemToBinary(pem);

    return window.crypto.subtle.importKey("spki", binaryDer, {
      name: "RSA-OAEP",
      hash: { name: "SHA-256" }
    }, true, ["encrypt"]).then(function (publicKey) {
      var encoder = new TextEncoder();
      var encodedData = encoder.encode(data);
      return window.crypto.subtle.encrypt({
        name: "RSA-OAEP"
      }, publicKey, encodedData);
    }).then(function (encryptedData) {
      var base64Encrypted = btoa(String.fromCharCode.apply(String, _toConsumableArray(new Uint8Array(encryptedData))));
      return base64Encrypted;
    }).catch(function (error) {
      console.error("Encryption error:", error);
      throw new Error('Something went wrong');
    });
  }

  function rsaEncrypter(data) {
    return encryptData('-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm590+M8v/WDQkONQ0dRF\n8gTncNJ2kCZSBKoBsAm1vuqMKP4a6DeqXlblOUvVQDnlz9CXaN3lyUsLaoImSKKA\npt9eB/tVvkAig/rUNYRSBoMUYBkp7LKpobUouJIE21FUXI7c2hPa6ep7khNKfiMg\nlLR+iOGqLTHKLF+Wcv9Z1mcVXkLBmsOOyGtRpopcO7WaraIPlLxe0OtzMsJYDWXR\n9rylX3Vyke5Cak5ZuaCCrruonFUZOZspR6sjvYo/hd1maXve3aK/M7K15T1xFz82\nQdpvuo6tcp+rUaSSSZTRmicW+5aYNDNIC9h3p/Ispui236tze0Y0v338DdDqjCK/\nqQIDAQAB\n-----END PUBLIC KEY-----\n', data);
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
define('tournament-management-system/utils/tournament-image-fallback', ['exports', 'tournament-management-system/utils/tournament-posters'], function (exports, _tournamentPosters) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = tournamentImageFallback;
  function tournamentImageFallback(sportName) {
    if (!sportName) {
      return 'images/tournament-place-holder.svg';
    }
    var sportNameKey = sportName.split(' ').join('').toLowerCase();
    return _tournamentPosters.default[sportNameKey] || 'images/tournament-place-holder.svg';
  }
});
define('tournament-management-system/utils/tournament-posters', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    cricket: 'images/tournament-posters/cricket-tournament.png',
    football: 'images/tournament-posters/football-tournament.png',
    basketball: 'images/tournament-posters/basketball-tournament.png',
    tennis: 'images/tournament-posters/tennis-tournament.png',
    tabletennis: 'images/tournament-posters/table-tennis-tournament.png',
    running: 'images/tournament-posters/running-tournament.png',
    coding: 'images/tournament-posters/binary-screen.png',
    chess: 'images/tournament-posters/chess-tournament.png'
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
  require("tournament-management-system/app")["default"].create({"name":"tournament-management-system","version":"0.0.0+06027f17"});
}
//# sourceMappingURL=tournament-management-system.map
