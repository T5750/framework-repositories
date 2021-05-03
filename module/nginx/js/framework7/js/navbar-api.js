var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
$$('.hide-navbar').on('click', function () {
	app.navbar.hide('.navbar');
});
$$('.show-navbar').on('click', function () {
	app.navbar.show('.navbar');
});