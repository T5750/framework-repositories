var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
$$('.convert-form-to-data').on('click', function(){
	var formData = app.form.convertToData('#my-form');
	alert(JSON.stringify(formData));
});
$$('.fill-form-from-data').on('click', function(){
	var formData = {
		'name': 'John',
		'email': 'john@doe.com',
		'gender': 'female',
		'toggle': ['yes'],
	}
	app.form.fillFromData('#my-form', formData);
});