var $$ = Dom7;
var app = new Framework7({
	root: '#app',
	routes: [
		// Creates popup from passed HTML string
		{
			path: '/popup-content/',
			popup: {
				content: '<div class="popup"><div class="view"><div class="page"><div class="page-content"><div class="block"><p>Creates popup from passed HTML string</p><p><a class="link popup-close" href="#">Close popup</a></p></div></div></div></div></div>'
			}
		},
		// Load Login Screen from file via Ajax
		{
			path: '/login-screen-ajax/',
			loginScreen: {
				url: './pages/modal/login-screen.html'
			}
		},
		// Load Popup from component file
		{
			path: '/popup-component/',
			loginScreen: {
				componentUrl: './pages/modal/popup-component.html'
			}
		},
		// Use async route to check if the user is logged in:
		{
			path: '/secured-page/',
			async(routeTo, routeFrom, resolve, reject) {
				var userIsLoggedIn = $$('#check-login').prop('checked');
				if (userIsLoggedIn) {
					resolve({
						loginScreen: {
							url: './pages/modal/secured-page.html'
						}
					});
				} else {
					resolve({
						loginScreen: {
							url: './pages/modal/login-screen.html'
						}
					});
				}
			}
		}
	]
});
var mainView = app.views.create('.view-main');