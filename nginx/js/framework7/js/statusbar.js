var app = new Framework7({
	root: '#app',
	statusbar: {
		iosOverlaysWebView: true,
	}
});
var mainView = app.views.create('.view-main');
//Statusbar App Methods
app.statusbar.show();
app.statusbar.setBackgroundColor("#38dcff");