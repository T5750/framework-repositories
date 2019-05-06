var app = new Framework7({
	root: '#app',
	// touch: {
	// 	// Disable fast clicks
	// 	fastClicks: false
	// }
	touch: {
		tapHold: true //enable tap hold events
	}
});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
$$('.my-sheet').on('sheet:open', function (e, sheet) {
	console.log('my-sheet open');
	$$('.block a').addClass('my-button');
});
$$('.my-sheet').on('sheet:opened', function (e, sheet) {
	console.log('my-sheet opened');
});
//Tap Hold Event (Long Tap)
$$('.some-link').on('taphold', function () {
	app.dialog.alert('Tap hold fired!');
});