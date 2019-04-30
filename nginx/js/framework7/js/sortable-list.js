var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
app.on('sortableSort', function (listEl, indexes) {
	console.log(indexes);
});