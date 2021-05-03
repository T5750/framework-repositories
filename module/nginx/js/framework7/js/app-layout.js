var app = new Framework7({
	// App root element
	root: '#app',
	// App Name
	name: 'My App',
	// App id
	id: 'com.myapp.test',
	// Enable swipe panel
	panel: {
		swipe: 'left',
	},
	// Add default routes
	routes: [
		{
			path: '/about/',
			//url: './pages/about.html',
			content: '<div class="page"><div class="page-content"><div class="block"><h3>About Me</h3><p></p></div></div></div>'
		}
	],
	// ... other parameters
});
var mainView = app.views.create('.view-main');