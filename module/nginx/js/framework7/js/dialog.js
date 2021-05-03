var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// Alert
$$('.open-alert').on('click', function () {
	app.dialog.alert('Hello');
});
// Confirm
$$('.open-confirm').on('click', function () {
	app.dialog.confirm('Are you feel good today?', function () {
		app.dialog.alert('Great!');
	});
});
// Prompt
$$('.open-prompt').on('click', function () {
	app.dialog.prompt('What is your name?', function (name) {
		app.dialog.confirm('Are you sure that your name is ' + name + '?', function () {
			app.dialog.alert('Ok, your name is ' + name);
		});
	});
});
// Login
$$('.open-login').on('click', function () {
	app.dialog.login('Enter your username and password', function (username, password) {
		app.dialog.alert('Thank you!<br>Username:' + username + '<br>Password:' + password);
	});
});
// Password
$$('.open-password').on('click', function () {
	app.dialog.password('Enter your username and password', function (password) {
		app.dialog.alert('Thank you!<br>Password:' + password);
	});
});
// Vertical Buttons
$$('.open-vertical').on('click', function () {
	app.dialog.create({
		title: 'Vertical Buttons',
		text: 'Dialog with vertical buttons',
		buttons: [
			{
				text: 'Button 1',
			},
			{
				text: 'Button 2',
			},
			{
				text: 'Button 3',
			},
		],
		verticalButtons: true,
	}).open();
});
// Preloader
$$('.open-preloader').on('click', function () {
	app.dialog.preloader();
	setTimeout(function () {
		app.dialog.close();
	}, 3000);
});
// Preloader with custom text
$$('.open-preloader-custom').on('click', function () {
	app.dialog.preloader('My text...');
	setTimeout(function () {
		app.dialog.close();
	}, 3000);
});
// Progress
$$('.open-progress').on('click', function () {
	var progress = 0;
	var dialog = app.dialog.progress('Loading assets', progress);
	dialog.setText('Image 1 of 10');
	var interval = setInterval(function () {
		progress += 10;
		dialog.setProgress(progress);
		dialog.setText('Image ' + ((progress) / 10) + ' of 10');
		if (progress === 100) {
			clearInterval(interval);
			dialog.close();
		}
	}, 300)
});
// Progress Infinite
$$('.open-progress-infinite').on('click', function () {
	app.dialog.progress();
	setTimeout(function () {
		app.dialog.close();
	}, 3000);
});