var app = new Framework7({
	root: '#app',
	/*
		common app bundle id must match the one
		you have specified when created app in vi dashboard
	  */
	id: 'io.framework7.testapp',
	// vi module
	vi: {
		placementId: 'pltd4o7ibb9rc653x14'
	}
});
var mainView = app.views.create('.view-main');
app.vi.createAd();