var app = new Framework7({
	root: '#app',
	view: {
		iosDynamicNavbar: false,
		xhrCache: false,
	}
});
var mainView = app.views.create('.view-main', {
	on: {
		pageInit: function () {
			console.log('page init')
		}
	}
});
var currentView = app.views.current;
console.log(currentView.id);
var mainView = app.views.main;
var anotherView = app.views.another;
console.log(mainView.id);
console.log(anotherView.id);