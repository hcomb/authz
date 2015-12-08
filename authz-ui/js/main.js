'use strict';

require.config({
	shim: {
		underscore: {
			exports: '_'
		},
		backbone: {
			deps: [
				'underscore',
				'jquery'
			],
			exports: 'Backbone'
		},
		backboneLocalstorage: {
			deps: ['backbone'],
			exports: 'Store'
		}
	},
	paths: {
		jquery: 'vendor/jquery',
		underscore: 'vendor/underscore',
		backbone: 'vendor/backbone',
		backboneLocalstorage: 'vendor/backbone.localStorage',
		text: 'vendor/text'
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
