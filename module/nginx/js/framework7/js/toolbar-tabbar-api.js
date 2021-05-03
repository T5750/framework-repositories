var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
$$('.hide-toolbar').on('click', function () {
	app.toolbar.hide('.toolbar');
});
$$('.show-toolbar').on('click', function () {
	app.toolbar.show('.toolbar');
});