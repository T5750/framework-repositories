var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// Option 1. Using one 'page:init' handler for all pages
$$(document).on('page:init', function (e) {
	// Do something here when page loaded and initialized
});
// Option 2. Using live 'page:init' event handlers for each page
$$(document).on('page:init', '.page[data-name="about"]', function (e) {
	// Do something here when page with data-name="about" attribute loaded and initialized
});
// In page events:
$$(document).on('page:init', function (e, page) {
	console.log(page);
});
var page = $$('.page[data-name="home"]')[0].f7Page;
console.log(page);