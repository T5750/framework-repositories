var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
$$('#price-filter').on('range:change', function (e, range) {
	$$('.price-value').text('$'+(range.value[0])+' - $'+(range.value[1]));
});