var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
// Plugin Structure
var myPlugin = {
	// Module Name
	name: 'demo-module',
	/* Install callback
	It will be executed right after component is installed
	Context of this callback points to Class where it was installed
	*/
	install() {
		const Class = this;
		console.log(Class);
	},
	/* Create callback
	It will be executed in the very beginning of class initilization (when we create new instance of the class)
	*/
	create(instance) {
		console.log('init', instance);
	},
	/*
	Object with default class/plugin parameters
	*/
	params: {
		myPlugin: {
			a: 1,
			b: 2,
			c: 3,
		}
	},
	/* proto object extends Class prototype */
	proto: {
		demo() {
			return 'demo-module-proto-method';
		},
		demoStatic: 'demo-module-proto-static',
	},
	// Extend Class with static props and methods, e.g. Class.myMethod
	static: {
		demo() {
			return 'demo-module-class-method';
		},
		demoStatic: 'demo-module-class-static',
	},
	/* Initialized instance Props & Methods */
	instance: {
		demoProp: true,
		demoMethod() {
			return 'demo-method';
		},
	},
	/* Event handlers */
	on: {
		demoEvent(a, b) {
			console.log('demo-event', a, b);
		},
	},
	/* Handle clicks */
	clicks: {
		// prop name means CSS selector of element to add click handler
		'p': function ($clickedEl, data) {
			// $clickedEl: Dom7 instance of clicked element
			// data: element data set (data- attributes)
		},
	}
};
// Install Plugin
Framework7.use(myPlugin);