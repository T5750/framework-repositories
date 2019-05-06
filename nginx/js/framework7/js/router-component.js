var app = new Framework7({
	root: '#app',
	routes: [
		{
			path: '/some-page/',
			// Component Object
			component: {
				template: '<div class="page"><div class="navbar"><div class="navbar-inner"><div class="title">{{title}}</div></div></div><div class="page-content"><a @click="openAlert" class="red-link">Open Alert</a><div class="list simple-list"><ul>{{#each names}}<li>{{this}}</li>{{/each}}</ul></div></div></div>',
				style: '.red-link {color: red;}',
				data: function () {
					return {
						username: 'T5750',
						title: 'Component Page',
						names: ['John', 'Vladimir', 'Timo']
					}
				},
				methods: {
					openAlert: function () {
						var self = this;
						self.$app.dialog.alert('Hello world!');
					}
				},
				on: {
					pageMounted: function (e, page) {
						console.log('page mounted');
					},
					pageInit: function (e, page) {
						console.log(this.username); // -> 'T5750'
					},
					pageBeforeIn: function (e, page) {
						console.log('page before in');
					},
					pageAfterIn: function (e, page) {
						console.log('page after in');
					},
					pageBeforeOut: function (e, page) {
						console.log('page before out');
					},
					pageAfterOut: function (e, page) {
						console.log('page after out');
					},
					pageBeforeRemove: function (e, page) {
						console.log('page before remove');
					}
				}
			}
		}
	]
});
var mainView = app.views.create('.view-main');