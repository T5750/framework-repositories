var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $ = Dom7;
$('.accordion-item').on('accordion:opened', function () {
	app.dialog.alert('Accordion item opened');
});
$('.accordion-item').on('accordion:closed', function (e) {
	app.dialog.alert('Accordion item closed');
});
app.on('accordionOpened', function (el) {
	console.log('The following element opened:');
	console.log(el);
});