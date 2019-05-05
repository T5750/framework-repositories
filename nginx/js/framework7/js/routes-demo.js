var app = new Framework7({
	root: '#app',
	routes: [
		// Load via Ajax
		{
			path: '/about/',
			//url: './pages/about.html',
			// Fill this tab content from content string
			content: '<div class="page"><div class="page-content"><div class="block"><h3>About Me</h3><p></p></div></div></div>',
			on: {
				pageBeforeIn: function (event, page) {
					console.log("pageBeforeIn");
				},
				pageAfterIn: function (event, page) {
					console.log("pageAfterIn");
				},
				pageInit: function (event, page) {
					console.log("pageInit");
				},
				pageBeforeRemove: function (event, page) {
					console.log("pageBeforeRemove");
				}
			}
		},
		// Dynamic page from content
		{
			path: '/news/',
			content: '<div class="page"><div class="page-content"><div class="block"><p>This page created dynamically</p></div></div></div>',
		},
		// By page name (data-name="services") presented in DOM
		{
			path: '/services/',
			pageName: 'services',
		},
		// By page HTMLElement
		{
			path: '/contacts/',
			el: document.querySelector('.page[data-name="contacts"]'),
		},
		// By template
		{
			path: '/template/:name/',
			template: '<div class="page"><div class="page-content"><div class="block"><p>Hello {{$route.params.name}}</p></div></div></div>',
		},
		// By template URL
		{
			path: '/blog/',
			templateUrl: './pages/blog.html',
		},
		// By component
		{
			path: '/posts/',
			component: {
				// look below
			},
		},
		// By component url
		{
			path: '/post/:id/',
			componentUrl: './pages/component.html',
		},
		// Async
		{
			path: '/something/',
			async: function (routeTo, routeFrom, resolve, reject) {
				// Requested route
				console.log(routeTo);
				// Get external data and return template7 template
				app.request.json('http://some-endpoint/', function (data) {
					resolve(
						// How and what to load: template
						{
							template: '<div class="page">{{users}}</div>'
						},
						// Custom template context
						{
							context: {
								users: data,
							},
						}
					);
				});
			}
		},
		// Default route, match to all pages (e.g. 404 page)
		{
			path: '/popup-content/',
			popup: {
				content: '<div class="popup"><div class="view"><div class="page"><div class="page-content"><div class="block"><p>Creates popup from passed HTML string</p><p><a class="link popup-close" href="#">Close popup</a></p></div></div></div></div></div>'
			}
		},
		{
			path: '(.*)',
			url: './pages/404.html',
		},
	],
	// ... other parameters
});
var mainView = app.views.create('.view-main');
//手动触发事件
app.on('myCustomEvent', function (a, b) {
	console.log(a); // -> 'foo'
	console.log(b); // -> 'bar'
});
app.emit('myCustomEvent', 'foo', 'bar');
//事件处理上下文
app.on('popupOpen', function () {
	console.log(this); // -> app instance
});