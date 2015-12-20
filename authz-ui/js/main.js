'use strict';

require.config({
	shim: {
		underscore: {
			exports: '_'
		},
		backbone: {
			deps: [
				'underscore',
				'jquery',
				'flatui'
			],
			exports: 'Backbone'
		},
		backboneLocalstorage: {
			deps: ['backbone'],
			exports: 'Store'
		}
	},
	paths: {
		flatui: 'http://static.hcomb.eu/js/flat-ui.min',
		jquery: 'http://static.hcomb.eu/js/jquery',
		underscore: 'http://static.hcomb.eu/js/underscore',
		backbone: 'http://static.hcomb.eu/js/backbone',
		backboneLocalstorage: 'http://static.hcomb.eu/js/backbone.localStorage',
		text: 'http://static.hcomb.eu/js/text'
	}
});

require([
	'backbone',
	'views/app',
	'router'
], function (Backbone, AppView, Workspace) {

	new Workspace();
	Backbone.history.start();
	new AppView();
	
});
