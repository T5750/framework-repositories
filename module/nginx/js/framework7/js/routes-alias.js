var app = new Framework7({
	root: '#app',
	routes: [
		{
			path: '/about/',
			content: '<div class="page"><div class="page-content"><div class="block"><h3>About Me</h3><p></p></div></div></div>',
			alias: '/bar/'
		},
		{
			path: '/foo/',
			content: '<div class="page"><div class="page-content"><div class="block"><h3>About baz</h3><p></p></div></div></div>',
			alias: ['/bar2/', '/baz/', '/baz2/']
		},
		{
			path: '/foobar/',
			redirect: '/about/'
		},
		{
			path: '/foobaz/',
			redirect: function (route, resolve, reject) {
				// if we have "user" query parameter
				if (route.query.user) {
					// redirect to such url
					resolve('/foo/?user=' + route.query.user);
				}
				// otherwise do nothing
				else reject();
			}
		}
	]
});
var mainView = app.views.create('.view-main');