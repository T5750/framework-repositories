var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
//Request Setup
// After the following setup all XHR requests will have additional 'Autorization' header
Framework7.request.setup({
	headers: {
		'Authorization': 'sometokenvalue'
	}
});
// If we need it in place where we don't have access to app instance or before we init the app
Framework7.request.get('../routes/pages/about-me/contacts.html', function (data) {
	console.log(data);
});
app.request.get('../routes/pages/about-me/contacts.html', function (data) {
	console.log(data);
});
// statusCode
app.request({
	url: 'somepage.html',
	statusCode: {
		404: function (xhr) {
			// alert('page not found');
			$$('#response').html('page not found');
		}
	}
});
// Post
app.request.post('../routes/pages/about-me/contacts.html', {foo: 'bar', id: 5}, function (data) {
	$$('.articles').html(data);
	console.log('Load was performed');
});
// Json
app.request.json('../json/autocomplete-languages.json', function (data) {
	console.log(data);
});