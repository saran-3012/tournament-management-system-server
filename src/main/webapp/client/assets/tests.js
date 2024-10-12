'use strict';

define('tournament-management-system/tests/app.lint-test', [], function () {
  'use strict';

  QUnit.module('ESLint | app');

  QUnit.test('app.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'app.js should pass ESLint\n\n');
  });

  QUnit.test('components/card-item.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/card-item.js should pass ESLint\n\n');
  });

  QUnit.test('components/card-wrapper.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/card-wrapper.js should pass ESLint\n\n');
  });

  QUnit.test('components/form-model.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/form-model.js should pass ESLint\n\n');
  });

  QUnit.test('components/general-button.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'components/general-button.js should pass ESLint\n\n10:11 - \'event\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('components/nav-bar.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/nav-bar.js should pass ESLint\n\n');
  });

  QUnit.test('components/organization-card.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/organization-card.js should pass ESLint\n\n');
  });

  QUnit.test('components/password-input.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/password-input.js should pass ESLint\n\n');
  });

  QUnit.test('components/text-input.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/text-input.js should pass ESLint\n\n');
  });

  QUnit.test('components/tournament-card.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'components/tournament-card.js should pass ESLint\n\n');
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

  QUnit.test('controllers/register.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'controllers/register.js should pass ESLint\n\n85:25 - Unexpected \'debugger\' statement. (no-debugger)');
  });

  QUnit.test('helpers/calculate-deadline.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/calculate-deadline.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/eq.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/eq.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/get-date.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/get-date.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/image-fallback.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/image-fallback.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/organization-status.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/organization-status.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/sport-type.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/sport-type.js should pass ESLint\n\n');
  });

  QUnit.test('helpers/tournament-status.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'helpers/tournament-status.js should pass ESLint\n\n');
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

  QUnit.test('routes/dashboard.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'routes/dashboard.js should pass ESLint\n\n');
  });

  QUnit.test('routes/login.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/login.js should pass ESLint\n\n14:24 - \'transition\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('routes/organizations.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/organizations.js should pass ESLint\n\n34:16 - \'$\' is not defined. (no-undef)');
  });

  QUnit.test('routes/register.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'routes/register.js should pass ESLint\n\n6:24 - \'transition\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('routes/tournaments.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'routes/tournaments.js should pass ESLint\n\n');
  });

  QUnit.test('routes/tournaments/tournament.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'routes/tournaments/tournament.js should pass ESLint\n\n');
  });

  QUnit.test('services/authentication-service.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'services/authentication-service.js should pass ESLint\n\n57:42 - \'jqXHR\' is defined but never used. (no-unused-vars)\n92:42 - \'jqXHR\' is defined but never used. (no-unused-vars)\n120:42 - \'jqXHR\' is defined but never used. (no-unused-vars)\n148:42 - \'jqXHR\' is defined but never used. (no-unused-vars)');
  });

  QUnit.test('services/env-service.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'services/env-service.js should pass ESLint\n\n');
  });

  QUnit.test('utils/form-validator.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/form-validator.js should pass ESLint\n\n35:2 - Unnecessary semicolon. (no-extra-semi)');
  });

  QUnit.test('utils/hash-set.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'utils/hash-set.js should pass ESLint\n\n');
  });

  QUnit.test('utils/sanitize-input.js', function (assert) {
    assert.expect(1);
    assert.ok(false, 'utils/sanitize-input.js should pass ESLint\n\n13:2 - Unnecessary semicolon. (no-extra-semi)');
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
define('tournament-management-system/tests/integration/components/card-wrapper-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('card-wrapper', 'Integration | Component | card wrapper', {
    integration: true
  });

  (0, _emberQunit.test)('it renders', function (assert) {

    // Set any properties with this.set('myProperty', 'value');
    // Handle any actions with this.on('myAction', function(val) { ... });

    this.render(Ember.HTMLBars.template({
      "id": "LRGdeL4Z",
      "block": "{\"statements\":[[1,[26,[\"card-wrapper\"]],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
      "meta": {}
    }));

    assert.equal(this.$().text().trim(), '');

    // Template block usage:
    this.render(Ember.HTMLBars.template({
      "id": "v0A4TlkW",
      "block": "{\"statements\":[[0,\"\\n\"],[6,[\"card-wrapper\"],null,null,{\"statements\":[[0,\"      template block text\\n\"]],\"locals\":[]},null],[0,\"  \"]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
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
define('tournament-management-system/tests/integration/helpers/image-fallback-test', ['ember-qunit'], function (_emberQunit) {
  'use strict';

  (0, _emberQunit.moduleForComponent)('image-fallback', 'helper:image-fallback', {
    integration: true
  });

  // Replace this with your real tests.
  (0, _emberQunit.test)('it renders', function (assert) {
    this.set('inputValue', '1234');

    this.render(Ember.HTMLBars.template({
      "id": "dK4uFYcN",
      "block": "{\"statements\":[[1,[33,[\"image-fallback\"],[[28,[\"inputValue\"]]],null],false]],\"locals\":[],\"named\":[],\"yields\":[],\"hasPartials\":false}",
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

  QUnit.test('integration/components/card-item-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/card-item-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/card-wrapper-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/card-wrapper-test.js should pass ESLint\n\n');
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

  QUnit.test('integration/components/nav-bar-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/nav-bar-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/organization-card-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/organization-card-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/password-input-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/password-input-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/text-input-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/text-input-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/components/tournament-card-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/components/tournament-card-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/calculate-deadline-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/calculate-deadline-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/eq-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/eq-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/get-date-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/get-date-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/image-fallback-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/image-fallback-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/organization-status-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/organization-status-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/sport-type-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/sport-type-test.js should pass ESLint\n\n');
  });

  QUnit.test('integration/helpers/tournament-status-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'integration/helpers/tournament-status-test.js should pass ESLint\n\n');
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

  QUnit.test('unit/controllers/register-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/controllers/register-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/mixins/controller-cleanup-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/mixins/controller-cleanup-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/dashboard-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/dashboard-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/login-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/login-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/organizations-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/organizations-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/register-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/register-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/tournaments-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/tournaments-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/routes/tournaments/tournament-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/routes/tournaments/tournament-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/services/authentication-service-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/services/authentication-service-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/services/authentication-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/services/authentication-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/services/env-service-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/services/env-service-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/form-validator-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/form-validator-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/hash-set-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/hash-set-test.js should pass ESLint\n\n');
  });

  QUnit.test('unit/utils/sanitize-input-test.js', function (assert) {
    assert.expect(1);
    assert.ok(true, 'unit/utils/sanitize-input-test.js should pass ESLint\n\n');
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
define('tournament-management-system/tests/unit/utils/form-validator-test', ['tournament-management-system/utils/form-validator', 'qunit'], function (_formValidator, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | form validator');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _formValidator.default)();
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
define('tournament-management-system/tests/unit/utils/sanitize-input-test', ['tournament-management-system/utils/sanitize-input', 'qunit'], function (_sanitizeInput, _qunit) {
  'use strict';

  (0, _qunit.module)('Unit | Utility | sanitize input');

  // Replace this with your real tests.
  (0, _qunit.test)('it works', function (assert) {
    var result = (0, _sanitizeInput.default)();
    assert.ok(result);
  });
});
require('tournament-management-system/tests/test-helper');
EmberENV.TESTS_FILE_LOADED = true;
//# sourceMappingURL=tests.map
