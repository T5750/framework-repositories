var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
// create searchbar
var searchbar = app.searchbar.create({
	el: '.searchbar',
	searchContainer: '.list',
	searchIn: '.item-title',
	on: {
		search(sb, query, previousQuery) {
			console.log(query, previousQuery);
		}
	}
});