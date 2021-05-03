var app = new Framework7({
	root: '#app',
	routes: [
		{
			path: '/',
			url: './block.html'
		}
	]
});
var mainView = app.views.create('.view-main', {
	url: '/'
});
console.log(mainView.routes);