var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
// If we need it in place where we don't have access to app instance or before we init the app
if (!Framework7.support.touch) {
	console.log('No touch support');
}
// After we init the app we can access it as app instance property
if (!app.support.touch) {
	console.log('No touch support');
}