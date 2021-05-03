var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
$$('.open-preloader-indicator').on('click', function () {
	app.preloader.show();
	setTimeout(function () {
		app.preloader.hide();
	}, 3000);
});