var app = new Framework7({
	root: '#app',
	routes: [
		{
			path: '/some-page/',
			componentUrl: './pages/about-me/cv.html'
		}
	]
});
var mainView = app.views.create('.view-main');