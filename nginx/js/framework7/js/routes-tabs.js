var app = new Framework7({
	root: '#app',
	routes: [
		{
			path: '/about-me/',
			url: './routes-tabs.html',
			// Pass "tabs" property to route
			tabs: [
				// First (default) tab has the same url as the page itself
				{
					path: '/',
					id: 'about',
					// Fill this tab content from content string
					content: '<div class="block"><h3>About Me</h3></div>'
				},
				// Second tab
				{
					path: '/contacts/',
					id: 'contacts',
					// Fill this tab content via Ajax request
					url: './pages/about-me/contacts.html'
				},
				// Third tab
				{
					path: '/cv/',
					id: 'cv',
					// Load this tab content as a component via Ajax request
					componentUrl: './pages/about-me/cv.html'
				}
			]
		}
	]
});
var mainView = app.views.create('.view-main', {
	url: '/about-me/'
});
console.log(mainView.routes);
var $$ = Dom7;
$$('.tab').on('tab:init', function (e) {
	console.log('tab:init' + " " + e.detail.id);
});
