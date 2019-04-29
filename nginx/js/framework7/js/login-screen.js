var $$ = Dom7;
var app = new Framework7({
	root: '#app',
	routes: [{
		path: '/login-screen/',
		/*
		We can load it from url like:
		url: 'login-screen.html'
		But in this example we load it just from content string
		*/
		content: '\<div class="page no-navbar no-toolbar no-swipeback">\<div class="page-content login-screen-content">\<div class="login-screen-title">My App</div>\<form>\<div class="list">\...</div>\<div class="list">\<ul>\<li><a href="#" class="item-link list-button">Sign In</a></li>\</ul>\<div class="block-footer">\<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>\<p><a href="#" class="link back">Close Login Screen</a></p>\</div>\</div>\</form>\</div>\</div>'
	}]
});
// Login Screen-Modal DOM events
$$('.login-screen').on('loginscreen:open', function (e, loginScreen) {
	console.log('Login screen open')
});
$$('.login-screen').on('loginscreen:opened', function (e, loginScreen) {
	console.log('Login screen opened')
});
$$('.login-screen').on('loginscreen:close', function (e, loginScreen) {
	console.log('Login screen close')
});
$$('.login-screen').on('loginscreen:closed', function (e, loginScreen) {
	console.log('Login screen closed')
});
var mainView = app.views.create('.view-main');