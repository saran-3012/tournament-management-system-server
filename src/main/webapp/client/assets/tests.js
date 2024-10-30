'use strict';

define('tournament-management-system/tests/app.lint-test', [], function () {
  'use strict';

  QUnit.module('ESLint | app');

  QUnit.test('app.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'app.js should pass ESLint\n\n');
  });

  QUnit.test('components/app-loader.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/app-loader.js should pass ESLint\n\n');
  });

  QUnit.test('components/card-item.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/card-item.js should pass ESLint\n\n');
  });

  QUnit.test('components/form-model.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/form-model.js should pass ESLint\n\n');
  });

  QUnit.test('components/general-button.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/general-button.js should pass ESLint\n\n');
  });

  QUnit.test('components/icon-label-item.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/icon-label-item.js should pass ESLint\n\n');
  });

  QUnit.test('components/message-box.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/message-box.js should pass ESLint\n\n');
  });

  QUnit.test('components/message-queue.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/message-queue.js should pass ESLint\n\n');
  });

  QUnit.test('components/nav-bar.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'components/nav-bar.js should pass ESLint\n\n29:37 - \'event\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('components/organization-card.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/organization-card.js should pass ESLint\n\n');
  });

  QUnit.test('components/organization-form.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/organization-form.js should pass ESLint\n\n');
  });

  QUnit.test('components/organization-navbar.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/organization-navbar.js should pass ESLint\n\n');
  });

  QUnit.test('components/organization-user-form.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'components/organization-user-form.js should pass ESLint\n\n169:20 - \'$\' is not defined. (no-undef)\n186:34 - \'jqXHR\' is defined but never used. (no-unused-vars)\n206:25 - \'formData\' is defined but never used. (no-unused-vars)\n231:52 - Empty block statement. (no-empty)');
  });

  QUnit.test('components/organization-user-navbar.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/organization-user-navbar.js should pass ESLint\n\n');
  });

  QUnit.test('components/participant-card.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'components/participant-card.js should pass ESLint\n\n41:13 - \'$\' is not defined. (no-undef)\n54:42 - \'jqXHR\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('components/password-input.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/password-input.js should pass ESLint\n\n');
  });

  QUnit.test('components/popup-box.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/popup-box.js should pass ESLint\n\n');
  });

  QUnit.test('components/search-bar.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/search-bar.js should pass ESLint\n\n');
  });

  QUnit.test('components/select-input.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/select-input.js should pass ESLint\n\n');
  });

  QUnit.test('components/team-card.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'components/team-card.js should pass ESLint\n\n29:9 - \'$\' is not defined. (no-undef)\n38:38 - \'jqXHR\' is defined but never used. (no-unused-vars)\n55:14 - Unnecessary semicolon. (no-extra-semi)\n72:13 - \'$\' is not defined. (no-undef)\n81:42 - \'jqXHR\' is defined but never used. (no-unused-vars)\n125:13 - \'$\' is not defined. (no-undef)\n138:42 - \'jqXHR\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('components/text-input.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/text-input.js should pass ESLint\n\n');
  });

  QUnit.test('components/tournament-card.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'components/tournament-card.js should pass ESLint\n\n14:37 - \'event\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('components/tournament-navbar.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/tournament-navbar.js should pass ESLint\n\n');
  });

  QUnit.test('components/tournament-participation-form.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/tournament-participation-form.js should pass ESLint\n\n');
  });

  QUnit.test('components/tournament-schedule-card.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'components/tournament-schedule-card.js should pass ESLint\n\n37:9 - \'$\' is not defined. (no-undef)\n46:38 - \'jqXHR\' is defined but never used. (no-unused-vars)\n67:14 - Unnecessary semicolon. (no-extra-semi)');
  });

  QUnit.test('components/tournament-schedule-form.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'components/tournament-schedule-form.js should pass ESLint\n\n91:13 - Unexpected lexical declaration in case block. (no-case-declarations)\n95:13 - Unexpected lexical declaration in case block. (no-case-declarations)\n108:9 - \'$\' is not defined. (no-undef)\n119:34 - \'jqXHR\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('components/user-card.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/user-card.js should pass ESLint\n\n');
  });

  QUnit.test('controllers/application.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'controllers/application.js should pass ESLint\n\n');
  });

  QUnit.test('controllers/dashboard.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'controllers/dashboard.js should pass ESLint\n\n');
  });

  QUnit.test('controllers/login.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'controllers/login.js should pass ESLint\n\n');
  });

  QUnit.test('controllers/organizations.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'controllers/organizations.js should pass ESLint\n\n');
  });

  QUnit.test('controllers/organizations/index.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'controllers/organizations/index.js should pass ESLint\n\n25:13 - \'$\' is not defined. (no-undef)\n54:13 - \'$\' is not defined. (no-undef)\n62:42 - \'xqXHR\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('controllers/organizations/organization.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'controllers/organizations/organization.js should pass ESLint\n\n45:13 - \'$\' is not defined. (no-undef)\n53:42 - \'xqXHR\' is defined but never used. (no-unused-vars)\n76:13 - \'$\' is not defined. (no-undef)\n87:42 - \'jqXHR\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('controllers/register.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'controllers/register.js should pass ESLint\n\n58:25 - Unexpected \'debugger\' statement. (no-debugger)');
  });

  QUnit.test('controllers/tournaments.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'controllers/tournaments.js should pass ESLint\n\n');
  });

  QUnit.test('controllers/tournaments/index.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'controllers/tournaments/index.js should pass ESLint\n\n34:13 - \'$\' is not defined. (no-undef)\n42:42 - \'xqXHR\' is defined but never used. (no-unused-vars)\n79:13 - \'$\' is not defined. (no-undef)\n97:38 - \'jqXHR\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('controllers/tournaments/new.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'controllers/tournaments/new.js should pass ESLint\n\n179:9 - \'$\' is not defined. (no-undef)\n190:34 - \'jqXHR\' is defined but never used. (no-unused-vars)\n191:25 - \'data\' is not defined. (no-undef)');
  });

  QUnit.test('controllers/tournaments/tournament.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'controllers/tournaments/tournament.js should pass ESLint\n\n45:34 - \'jqXHR\' is defined but never used. (no-unused-vars)\n58:27 - \'includeLimit\' is assigned a value but never used. (no-unused-vars)\n77:38 - \'jqXHR\' is defined but never used. (no-unused-vars)\n204:38 - \'teamRegistrationType\' is defined but never used. (no-unused-vars)\n376:42 - \'xqXHR\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('controllers/tournaments/tournament/edit.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'controllers/tournaments/tournament/edit.js should pass ESLint\n\n184:9 - \'$\' is not defined. (no-undef)\n198:34 - \'jqXHR\' is defined but never used. (no-unused-vars)\n199:25 - \'data\' is not defined. (no-undef)');
  });

  QUnit.test('helpers/and.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/and.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/calculate-deadline.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/calculate-deadline.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/concat.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/concat.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/eq.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/eq.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/get-date.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/get-date.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/instance-gt.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/instance-gt.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/instance-lt.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/instance-lt.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/is-empty.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/is-empty.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/millis-to-date-time.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/millis-to-date-time.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/millis-to-date.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/millis-to-date.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/n-eq.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/n-eq.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/object.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/object.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/or.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/or.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/organization-status.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/organization-status.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/prepend-root.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/prepend-root.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/sport-type.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/sport-type.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/test-logger.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/test-logger.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/tournament-event-round.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/tournament-event-round.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/tournament-status.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/tournament-status.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/truncate-name.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/truncate-name.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/user-role.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/user-role.js should pass ESLint\n\n');
  });

  QUnit.test('mixins/controller-cleanup.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'mixins/controller-cleanup.js should pass ESLint\n\n');
  });

  QUnit.test('resolver.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'resolver.js should pass ESLint\n\n');
  });

  QUnit.test('router.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'router.js should pass ESLint\n\n');
  });

  QUnit.test('routes/access-denied.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'routes/access-denied.js should pass ESLint\n\n');
  });

  QUnit.test('routes/dashboard.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/dashboard.js should pass ESLint\n\n11:17 - \'transition\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('routes/index.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/index.js should pass ESLint\n\n11:17 - \'transition\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('routes/login.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/login.js should pass ESLint\n\n14:24 - \'transition\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('routes/not-found.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'routes/not-found.js should pass ESLint\n\n');
  });

  QUnit.test('routes/organizations.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'routes/organizations.js should pass ESLint\n\n');
  });

  QUnit.test('routes/organizations/index.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/organizations/index.js should pass ESLint\n\n36:21 - \'$\' is not defined. (no-undef)\n37:16 - \'$\' is not defined. (no-undef)');
  });

  QUnit.test('routes/organizations/organization.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/organizations/organization.js should pass ESLint\n\n41:37 - \'$\' is not defined. (no-undef)\n73:30 - \'$\' is not defined. (no-undef)\n104:17 - \'Promise\' is not defined. (no-undef)');
  });

  QUnit.test('routes/organizations/organization/user.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'routes/organizations/organization/user.js should pass ESLint\n\n');
  });

  QUnit.test('routes/profile.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'routes/profile.js should pass ESLint\n\n');
  });

  QUnit.test('routes/register.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/register.js should pass ESLint\n\n13:24 - \'transition\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('routes/tournaments.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'routes/tournaments.js should pass ESLint\n\n');
  });

  QUnit.test('routes/tournaments/index.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/tournaments/index.js should pass ESLint\n\n51:16 - \'$\' is not defined. (no-undef)');
  });

  QUnit.test('routes/tournaments/new.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/tournaments/new.js should pass ESLint\n\n32:24 - \'transition\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('routes/tournaments/tournament.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/tournaments/tournament.js should pass ESLint\n\n101:24 - \'transition\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('routes/tournaments/tournament/edit.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/tournaments/tournament/edit.js should pass ESLint\n\n24:24 - \'transition\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('services/authentication-service.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'services/authentication-service.js should pass ESLint\n\n69:34 - \'jqXHR\' is defined but never used. (no-unused-vars)\n113:34 - \'jqXHR\' is defined but never used. (no-unused-vars)\n147:34 - \'jqXHR\' is defined but never used. (no-unused-vars)\n180:34 - \'jqXHR\' is defined but never used. (no-unused-vars)\n186:13 - \'messageQueueService\' is not defined. (no-undef)');
  });

  QUnit.test('services/data-persistance-service.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'services/data-persistance-service.js should pass ESLint\n\n');
  });

  QUnit.test('services/env-service.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'services/env-service.js should pass ESLint\n\n');
  });

  QUnit.test('services/loader-service.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'services/loader-service.js should pass ESLint\n\n');
  });

  QUnit.test('services/message-queue-service.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'services/message-queue-service.js should pass ESLint\n\n');
  });

  QUnit.test('utils/check-characters-present.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/check-characters-present.js should pass ESLint\n\n8:2 - Unnecessary semicolon. (no-extra-semi)');
  });

  QUnit.test('utils/check-date-valid.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/check-date-valid.js should pass ESLint\n\n15:2 - Unnecessary semicolon. (no-extra-semi)');
  });

  QUnit.test('utils/controllable-timeout.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/controllable-timeout.js should pass ESLint\n\n24:2 - Unnecessary semicolon. (no-extra-semi)');
  });

  QUnit.test('utils/date-time-to-mills.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'utils/date-time-to-mills.js should pass ESLint\n\n');
  });

  QUnit.test('utils/delay-calls.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/delay-calls.js should pass ESLint\n\n15:2 - Unnecessary semicolon. (no-extra-semi)');
  });

  QUnit.test('utils/form-validator.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/form-validator.js should pass ESLint\n\n34:2 - Unnecessary semicolon. (no-extra-semi)');
  });

  QUnit.test('utils/get-month-days-count.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'utils/get-month-days-count.js should pass ESLint\n\n');
  });

  QUnit.test('utils/hash-set.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'utils/hash-set.js should pass ESLint\n\n');
  });

  QUnit.test('utils/is-leap-year.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'utils/is-leap-year.js should pass ESLint\n\n');
  });

  QUnit.test('utils/limit-calls.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/limit-calls.js should pass ESLint\n\n11:2 - Unnecessary semicolon. (no-extra-semi)');
  });

  QUnit.test('utils/millis-to-date.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'utils/millis-to-date.js should pass ESLint\n\n');
  });

  QUnit.test('utils/millis-to-time.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'utils/millis-to-time.js should pass ESLint\n\n');
  });

  QUnit.test('utils/rsa-encrypter.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/rsa-encrypter.js should pass ESLint\n\n8:21 - \'Uint8Array\' is not defined. (no-undef)\n40:61 - \'Uint8Array\' is not defined. (no-undef)');
  });

  QUnit.test('utils/sanitize-input.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/sanitize-input.js should pass ESLint\n\n13:2 - Unnecessary semicolon. (no-extra-semi)');
  });

  QUnit.test('utils/tournament-image-fallback.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'utils/tournament-image-fallback.js should pass ESLint\n\n');
  });

  QUnit.test('utils/tournament-posters.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'utils/tournament-posters.js should pass ESLint\n\n');
  });
});
define('tournament-management-system/tests/helpers/destroy-app', ['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = destroyApp;
  function destroyApp(application) {
    Ember.run(application, 'destroy');
  }
});
define('tournament-management-system/tests/helpers/module-for-acceptance', ['exports', 'qunit', 'tournament-management-system/tests/helpers/start-app', 'tournament-management-system/tests/helpers/destroy-app'], function (exports, _qunit, _startApp, _destroyApp) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  exports.default = function (name) {
    var options = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    (0, _qunit.module)(name, {
      beforeEach: function beforeEach() {
        this.application = (0, _startApp.default)();

        if (options.beforeEach) {
          return options.beforeEach.apply(this, arguments);
        }
      },
      afterEach: function afterEach() {
        var _this = this;

        var afterEach = options.afterEach && options.afterEach.apply(this, arguments);
        return resolve(afterEach).then(function () {
          return (0, _destroyApp.default)(_this.application);
        });
      }
    });
  };

  var resolve = Ember.RSVP.resolve;
});
define('tournament-management-system/tests/helpers/resolver', ['exports', 'tournament-management-system/resolver', 'tournament-management-system/config/environment'], function (exports, _resolver, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });


  var resolver = _resolver.default.create();

  resolver.namespace = {
    modulePrefix: _environment.default.modulePrefix,
    podModulePrefix: _environment.default.podModulePrefix
  };

  exports.default = resolver;
});
define('tournament-management-system/tests/helpers/start-app', ['exports', 'tournament-management-system/app', 'tournament-management-system/config/environment'], function (exports, _app, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = startApp;
  function startApp(attrs) {
    var attributes = Ember.merge({}, _environment.default.APP);
    attributes = Ember.merge(attributes, attrs); // use defaults, but you can override;

    return Ember.run(function () {
      var application = _app.default.create(attributes);
      application.setupForTesting();
      application.injectTestHelpers();
      return application;
    });
  }
});
define('tournament-management-system/tests/integration/components/app-loader-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('app-loader', 'Integration | Component | app loader', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "fAediTpf",
      "block": "{\"statements\":[[1,[26,[\"app-loader\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "1w0/4fy6",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"app-loader\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/card-item-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('card-item', 'Integration | Component | card item', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "GGyxIiiF",
      "block": "{\"statements\":[[1,[26,[\"card-item\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "HPkMKbnv",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"card-item\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/form-model-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('form-model', 'Integration | Component | form model', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "yewW2Y6H",
      "block": "{\"statements\":[[1,[26,[\"form-model\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "VQQxVTz4",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"form-model\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/form-wrapper-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('form-wrapper', 'Integration | Component | form wrapper', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "i/WjWnvf",
      "block": "{\"statements\":[[1,[26,[\"form-wrapper\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "N0vq5GLV",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"form-wrapper\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/general-button-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('general-button', 'Integration | Component | general button', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "71P2q9Fh",
      "block": "{\"statements\":[[1,[26,[\"general-button\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "WYqybJpP",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"general-button\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/icon-label-item-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('icon-label-item', 'Integration | Component | icon label item', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "HxtgmsK4",
      "block": "{\"statements\":[[1,[26,[\"icon-label-item\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "q4XB3sV5",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"icon-label-item\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/message-box-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('message-box', 'Integration | Component | message box', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "B7LxuT7C",
      "block": "{\"statements\":[[1,[26,[\"message-box\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "64E4bAJ2",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"message-box\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/message-queue-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('message-queue', 'Integration | Component | message queue', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "Yc2TZDvo",
      "block": "{\"statements\":[[1,[26,[\"message-queue\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "NGTLp+DZ",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"message-queue\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/nav-bar-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('nav-bar', 'Integration | Component | nav bar', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "ntAlUnVm",
      "block": "{\"statements\":[[1,[26,[\"nav-bar\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "UKwhv7kh",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"nav-bar\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/organization-card-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('organization-card', 'Integration | Component | organization card', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "doMu68fh",
      "block": "{\"statements\":[[1,[26,[\"organization-card\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "i2tiLPq/",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"organization-card\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/organization-form-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('organization-form', 'Integration | Component | organization form', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "tIMdAFQ3",
      "block": "{\"statements\":[[1,[26,[\"organization-form\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "x/n2wgkY",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"organization-form\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/organization-navbar-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('organization-navbar', 'Integration | Component | organization navbar', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "WzrGcU53",
      "block": "{\"statements\":[[1,[26,[\"organization-navbar\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "fRr5nFXF",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"organization-navbar\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/organization-user-form-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('organization-user-form', 'Integration | Component | organization user form', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "Tb/IWwWR",
      "block": "{\"statements\":[[1,[26,[\"organization-user-form\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "pQhyscID",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"organization-user-form\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/organization-user-navbar-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('organization-user-navbar', 'Integration | Component | organization user navbar', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "Uywk/PQY",
      "block": "{\"statements\":[[1,[26,[\"organization-user-navbar\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "/c/JViaY",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"organization-user-navbar\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/participant-card-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('participant-card', 'Integration | Component | participant card', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "TnpJe1qn",
      "block": "{\"statements\":[[1,[26,[\"participant-card\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "9ofdM//X",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"participant-card\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/password-input-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('password-input', 'Integration | Component | password input', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "HufZJssU",
      "block": "{\"statements\":[[1,[26,[\"password-input\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "0gemUFKa",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"password-input\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/popup-box-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('popup-box', 'Integration | Component | popup box', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "vyrZr8/6",
      "block": "{\"statements\":[[1,[26,[\"popup-box\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "q4ClLWw4",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"popup-box\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/search-bar-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('search-bar', 'Integration | Component | search bar', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "FzvoM0hE",
      "block": "{\"statements\":[[1,[26,[\"search-bar\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "jKfMJFsx",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"search-bar\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/select-input-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('select-input', 'Integration | Component | select input', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "PKEo+AQ5",
      "block": "{\"statements\":[[1,[26,[\"select-input\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "B0r6KLBu",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"select-input\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/team-card-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('team-card', 'Integration | Component | team card', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "LzztbreS",
      "block": "{\"statements\":[[1,[26,[\"team-card\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "/m2cq2I3",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"team-card\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/text-input-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('text-input', 'Integration | Component | text input', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "oTdtTCVk",
      "block": "{\"statements\":[[1,[26,[\"text-input\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "Jfz3+hYP",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"text-input\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/tournament-card-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('tournament-card', 'Integration | Component | tournament card', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "FhRQuZWI",
      "block": "{\"statements\":[[1,[26,[\"tournament-card\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "68SJVVLn",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"tournament-card\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/tournament-navbar-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('tournament-navbar', 'Integration | Component | tournament navbar', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "xlxGQWmz",
      "block": "{\"statements\":[[1,[26,[\"tournament-navbar\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "X7rkQUcb",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"tournament-navbar\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/tournament-participation-form-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('tournament-participation-form', 'Integration | Component | tournament participation form', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "AEqMWFoc",
      "block": "{\"statements\":[[1,[26,[\"tournament-participation-form\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "/ocNO4Cm",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"tournament-participation-form\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/tournament-schedule-card-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('tournament-schedule-card', 'Integration | Component | tournament schedule card', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "AVIkuj7x",
      "block": "{\"statements\":[[1,[26,[\"tournament-schedule-card\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "/5YQ/IdU",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"tournament-schedule-card\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/tournament-schedule-form-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('tournament-schedule-form', 'Integration | Component | tournament schedule form', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "H/MSwTQQ",
      "block": "{\"statements\":[[1,[26,[\"tournament-schedule-form\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "dXPkQl1j",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"tournament-schedule-form\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/components/user-card-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('user-card', 'Integration | Component | user card', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "L0Zkuh6E",
      "block": "{\"statements\":[[1,[26,[\"user-card\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "lqdb257Q",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"user-card\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), 'template block text');
  });
});
define('tournament-management-system/tests/integration/helpers/and-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('and', 'helper:and', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "etSau6Tt",
      "block": "{\"statements\":[[1,[33,[\"and\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/calculate-deadline-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('calculate-deadline', 'helper:calculate-deadline', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "MRl5P5mg",
      "block": "{\"statements\":[[1,[33,[\"calculate-deadline\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/concat-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('concat', 'helper:concat', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "v5p/cP+F",
      "block": "{\"statements\":[[1,[33,[\"concat\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/eq-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('eq', 'helper:eq', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "1SYOrDlN",
      "block": "{\"statements\":[[1,[33,[\"eq\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/get-date-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('get-date', 'helper:get-date', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "MKjU55p+",
      "block": "{\"statements\":[[1,[33,[\"get-date\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/instance-gt-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('instance-gt', 'helper:instance-gt', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "qQjVAVVi",
      "block": "{\"statements\":[[1,[33,[\"instance-gt\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/instance-lt-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('instance-lt', 'helper:instance-lt', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "m7P+coVa",
      "block": "{\"statements\":[[1,[33,[\"instance-lt\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/is-empty-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('is-empty', 'helper:is-empty', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "gbmaNUDG",
      "block": "{\"statements\":[[1,[33,[\"is-empty\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/millis-to-date-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('millis-to-date', 'helper:millis-to-date', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "emicvaTd",
      "block": "{\"statements\":[[1,[33,[\"millis-to-date\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/millis-to-date-time-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('millis-to-date-time', 'helper:millis-to-date-time', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "HWciwLuj",
      "block": "{\"statements\":[[1,[33,[\"millis-to-date-time\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/n-eq-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('n-eq', 'helper:n-eq', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "CaD9ZqUL",
      "block": "{\"statements\":[[1,[33,[\"n-eq\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/object-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('object', 'helper:object', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "wknbmZhb",
      "block": "{\"statements\":[[1,[33,[\"object\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/or-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('or', 'helper:or', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "l5tNo+6p",
      "block": "{\"statements\":[[1,[33,[\"or\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/organization-status-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('organization-status', 'helper:organization-status', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "IHXkhXdD",
      "block": "{\"statements\":[[1,[33,[\"organization-status\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/prepend-root-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('prepend-root', 'helper:prepend-root', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "1HwrDMm0",
      "block": "{\"statements\":[[1,[33,[\"prepend-root\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/sport-type-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('sport-type', 'helper:sport-type', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "usOY/hrn",
      "block": "{\"statements\":[[1,[33,[\"sport-type\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/test-logger-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('test-logger', 'helper:test-logger', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "AeRz8n1S",
      "block": "{\"statements\":[[1,[33,[\"test-logger\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/tournament-event-round-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('tournament-event-round', 'helper:tournament-event-round', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "azLGWi87",
      "block": "{\"statements\":[[1,[33,[\"tournament-event-round\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/tournament-status-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('tournament-status', 'helper:tournament-status', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "+9H9dRbd",
      "block": "{\"statements\":[[1,[33,[\"tournament-status\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/truncate-name-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('truncate-name', 'helper:truncate-name', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "Z/Bs3sAx",
      "block": "{\"statements\":[[1,[33,[\"truncate-name\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/integration/helpers/user-role-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('user-role', 'helper:user-role', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "FlozRhsc",
      "block": "{\"statements\":[[1,[33,[\"user-role\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '1234');
  });
});
define('tournament-management-system/tests/test-helper', ['tournament-management-system/tests/helpers/resolver', 'ember-qunit', 'ember-cli-qunit'], function (_resolver, _emberQunit, _emberCliQunit) {
  'use strict';

  (0, _emberQunit.setResolver)(_resolver.default);
  (0, _emberCliQunit.start)();
});
define('tournament-management-system/tests/tests.lint-test', [], function () {
  'use strict';

  QUnit.module('ESLint | tests');

  QUnit.test('helpers/destroy-app.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/destroy-app.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/module-for-acceptance.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/module-for-acceptance.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/resolver.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/resolver.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/start-app.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/start-app.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/app-loader-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/app-loader-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/card-item-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/card-item-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/form-model-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/form-model-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/form-wrapper-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/form-wrapper-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/general-button-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/general-button-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/icon-label-item-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/icon-label-item-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/message-box-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/message-box-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/message-queue-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/message-queue-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/nav-bar-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/nav-bar-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/organization-card-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/organization-card-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/organization-form-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/organization-form-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/organization-navbar-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/organization-navbar-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/organization-user-form-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/organization-user-form-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/organization-user-navbar-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/organization-user-navbar-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/participant-card-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/participant-card-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/password-input-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/password-input-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/popup-box-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/popup-box-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/search-bar-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/search-bar-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/select-input-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/select-input-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/team-card-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/team-card-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/text-input-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/text-input-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/tournament-card-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/tournament-card-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/tournament-navbar-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/tournament-navbar-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/tournament-participation-form-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/tournament-participation-form-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/tournament-schedule-card-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/tournament-schedule-card-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/tournament-schedule-form-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/tournament-schedule-form-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/user-card-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/user-card-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/and-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/and-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/calculate-deadline-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/calculate-deadline-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/concat-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/concat-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/eq-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/eq-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/get-date-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/get-date-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/instance-gt-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/instance-gt-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/instance-lt-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/instance-lt-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/is-empty-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/is-empty-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/millis-to-date-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/millis-to-date-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/millis-to-date-time-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/millis-to-date-time-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/n-eq-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/n-eq-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/object-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/object-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/or-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/or-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/organization-status-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/organization-status-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/prepend-root-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/prepend-root-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/sport-type-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/sport-type-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/test-logger-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/test-logger-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/tournament-event-round-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/tournament-event-round-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/tournament-status-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/tournament-status-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/truncate-name-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/truncate-name-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/user-role-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/user-role-test.js should pass ESLint\n\n');
  });

  QUnit.test('test-helper.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'test-helper.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/application-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/application-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/dashboard-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/dashboard-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/login-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/login-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/organizations-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/organizations-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/organizations/index-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/organizations/index-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/organizations/organization-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/organizations/organization-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/register-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/register-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/tournaments-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/tournaments-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/tournaments/index-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/tournaments/index-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/tournaments/new-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/tournaments/new-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/tournaments/tournament-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/tournaments/tournament-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/controllers/tournaments/tournament/edit-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/tournaments/tournament/edit-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/mixins/controller-cleanup-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/mixins/controller-cleanup-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/access-denied-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/access-denied-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/dashboard-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/dashboard-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/index-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/index-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/login-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/login-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/not-found-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/not-found-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/organizations-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/organizations-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/organizations/index-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/organizations/index-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/organizations/organization-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/organizations/organization-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/organizations/organization/user-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/organizations/organization/user-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/profile-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/profile-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/register-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/register-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/tournaments-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/tournaments-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/tournaments/index-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/tournaments/index-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/tournaments/new-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/tournaments/new-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/tournaments/tournament-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/tournaments/tournament-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/tournaments/tournament/edit-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/tournaments/tournament/edit-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/services/authentication-service-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/services/authentication-service-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/services/authentication-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/services/authentication-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/services/data-persistance-service-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/services/data-persistance-service-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/services/env-service-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/services/env-service-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/services/loader-service-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/services/loader-service-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/services/message-queue-service-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/services/message-queue-service-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/check-characters-present-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/check-characters-present-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/check-date-valid-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/check-date-valid-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/controllable-timeout-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/controllable-timeout-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/date-time-to-mills-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/date-time-to-mills-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/delay-calls-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/delay-calls-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/form-validator-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/form-validator-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/get-month-days-count-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/get-month-days-count-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/hash-set-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/hash-set-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/is-leap-year-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/is-leap-year-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/limit-calls-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/limit-calls-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/millis-to-date-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/millis-to-date-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/millis-to-time-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/millis-to-time-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/rsa-encrypter-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/rsa-encrypter-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/sanitize-input-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/sanitize-input-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/tournament-image-fallback-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/tournament-image-fallback-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/tournament-posters-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/tournament-posters-test.js should pass ESLint\n\n');
  });
});
define('tournament-management-system/tests/unit/controllers/application-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:application', 'Unit | Controller | application', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/dashboard-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:dashboard', 'Unit | Controller | dashboard', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/login-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:login', 'Unit | Controller | login', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/organizations-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:organizations', 'Unit | Controller | organizations', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/organizations/index-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:organizations/index', 'Unit | Controller | organizations/index', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/organizations/organization-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:organizations/organization', 'Unit | Controller | organizations/organization', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/register-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:register', 'Unit | Controller | register', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/tournaments-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:tournaments', 'Unit | Controller | tournaments', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/tournaments/index-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:tournaments/index', 'Unit | Controller | tournaments/index', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/tournaments/new-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:tournaments/new', 'Unit | Controller | tournaments/new', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/tournaments/tournament-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:tournaments/tournament', 'Unit | Controller | tournaments/tournament', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/controllers/tournaments/tournament/edit-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('controller:tournaments/tournament/edit', 'Unit | Controller | tournaments/tournament/edit', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var controller = this.subject();
    assert.ok(controller);
  });
});
define('tournament-management-system/tests/unit/mixins/controller-cleanup-test', ['tournament-management-system/mixins/controller-cleanup', 'qunit'], function (_controllerCleanup, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Mixin | controller cleanup');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var ControllerCleanupObject = Ember.Object.extend(_controllerCleanup.default);
    var subject = ControllerCleanupObject.create();
    assert.ok(subject);
  });
});
define('tournament-management-system/tests/unit/routes/access-denied-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:access-denied', 'Unit | Route | access denied', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/dashboard-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:dashboard', 'Unit | Route | dashboard', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/index-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:index', 'Unit | Route | index', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/login-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:login', 'Unit | Route | login', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/not-found-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:not-found', 'Unit | Route | not found', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/organizations-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:organizations', 'Unit | Route | organizations', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/organizations/index-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:organizations/index', 'Unit | Route | organizations/index', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/organizations/organization-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:organizations/organization', 'Unit | Route | organizations/organization', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/organizations/organization/user-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:organizations/organization/user', 'Unit | Route | organizations/organization/user', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/profile-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:profile', 'Unit | Route | profile', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/register-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:register', 'Unit | Route | register', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/tournaments-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:tournaments', 'Unit | Route | tournaments', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/tournaments/index-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:tournaments/index', 'Unit | Route | tournaments/index', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/tournaments/new-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:tournaments/new', 'Unit | Route | tournaments/new', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/tournaments/tournament-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:tournaments/tournament', 'Unit | Route | tournaments/tournament', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/routes/tournaments/tournament/edit-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('route:tournaments/tournament/edit', 'Unit | Route | tournaments/tournament/edit', {
    // Specify the other units that are required for this test.
    // needs: ['controller:foo']
  });

  (0, _emberQunit.test)('it exists', function (assert) {
    var route = this.subject();
    assert.ok(route);
  });
});
define('tournament-management-system/tests/unit/services/authentication-service-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('service:authentication-service', 'Unit | Service | authentication service', {
    // Specify the other units that are required for this test.
    // needs: ['service:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var service = this.subject();
    assert.ok(service);
  });
});
define('tournament-management-system/tests/unit/services/authentication-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('service:authentication', 'Unit | Service | authentication', {
    // Specify the other units that are required for this test.
    // needs: ['service:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var service = this.subject();
    assert.ok(service);
  });
});
define('tournament-management-system/tests/unit/services/data-persistance-service-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('service:data-persistance-service', 'Unit | Service | data persistance service', {
    // Specify the other units that are required for this test.
    // needs: ['service:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var service = this.subject();
    assert.ok(service);
  });
});
define('tournament-management-system/tests/unit/services/env-service-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('service:env-service', 'Unit | Service | env service', {
    // Specify the other units that are required for this test.
    // needs: ['service:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var service = this.subject();
    assert.ok(service);
  });
});
define('tournament-management-system/tests/unit/services/loader-service-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('service:loader-service', 'Unit | Service | loader service', {
    // Specify the other units that are required for this test.
    // needs: ['service:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var service = this.subject();
    assert.ok(service);
  });
});
define('tournament-management-system/tests/unit/services/message-queue-service-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleFor)('service:message-queue-service', 'Unit | Service | message queue service', {
    // Specify the other units that are required for this test.
    // needs: ['service:foo']
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it exists', function (assert) {
    var service = this.subject();
    assert.ok(service);
  });
});
define('tournament-management-system/tests/unit/utils/check-characters-present-test', ['tournament-management-system/utils/check-characters-present', 'qunit'], function (_checkCharactersPresent, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | check characters present');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _checkCharactersPresent.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/check-date-valid-test', ['tournament-management-system/utils/check-date-valid', 'qunit'], function (_checkDateValid, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | check date valid');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _checkDateValid.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/controllable-timeout-test', ['tournament-management-system/utils/controllable-timeout', 'qunit'], function (_controllableTimeout, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | controllable timeout');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _controllableTimeout.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/date-time-to-mills-test', ['tournament-management-system/utils/date-time-to-mills', 'qunit'], function (_dateTimeToMills, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | date time to mills');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _dateTimeToMills.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/delay-calls-test', ['tournament-management-system/utils/delay-calls', 'qunit'], function (_delayCalls, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | delay calls');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _delayCalls.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/form-validator-test', ['tournament-management-system/utils/form-validator', 'qunit'], function (_formValidator, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | form validator');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _formValidator.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/get-month-days-count-test', ['tournament-management-system/utils/get-month-days-count', 'qunit'], function (_getMonthDaysCount, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | get month days count');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _getMonthDaysCount.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/hash-set-test', ['tournament-management-system/utils/hash-set', 'qunit'], function (_hashSet, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | hash set');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _hashSet.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/is-leap-year-test', ['tournament-management-system/utils/is-leap-year', 'qunit'], function (_isLeapYear, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | is leap year');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _isLeapYear.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/limit-calls-test', ['tournament-management-system/utils/limit-calls', 'qunit'], function (_limitCalls, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | limit calls');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _limitCalls.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/millis-to-date-test', ['tournament-management-system/utils/millis-to-date', 'qunit'], function (_millisToDate, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | millis to date');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _millisToDate.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/millis-to-time-test', ['tournament-management-system/utils/millis-to-time', 'qunit'], function (_millisToTime, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | millis to time');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _millisToTime.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/rsa-encrypter-test', ['tournament-management-system/utils/rsa-encrypter', 'qunit'], function (_rsaEncrypter, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | rsa encrypter');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _rsaEncrypter.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/sanitize-input-test', ['tournament-management-system/utils/sanitize-input', 'qunit'], function (_sanitizeInput, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | sanitize input');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _sanitizeInput.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/tournament-image-fallback-test', ['tournament-management-system/utils/tournament-image-fallback', 'qunit'], function (_tournamentImageFallback, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | tournament image fallback');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _tournamentImageFallback.default)();
    assert.ok(result);
  });
});
define('tournament-management-system/tests/unit/utils/tournament-posters-test', ['tournament-management-system/utils/tournament-posters', 'qunit'], function (_tournamentPosters, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | tournament posters');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _tournamentPosters.default)();
    assert.ok(result);
  });
});
require('tournament-management-system/tests/test-helper');
EmberENV.TESTS_FILE_LOADED = true;
//# sourceMappingURL=tests.map
