var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// Create full-layout notification
var notificationFull = app.notification.create({
	icon: '<i class="icon demo-icon">7</i>',
	title: 'Framework7',
	titleRightText: 'now',
	subtitle: 'This is a subtitle',
	text: 'This is a simple notification message',
	closeTimeout: 3000,
});
// Create notification with close button
var notificationWithButton = app.notification.create({
	icon: '<i class="icon demo-icon">7</i>',
	title: 'Framework7',
	subtitle: 'Notification with close button',
	text: 'Click (x) button to close me',
	closeButton: true,
});
// Create notification with click to close
var notificationClickToClose = app.notification.create({
	icon: '<i class="icon demo-icon">7</i>',
	title: 'Framework7',
	titleRightText: 'now',
	subtitle: 'Notification with close on click',
	text: 'Click me to close',
	closeOnClick: true,
});
// With callback on close
var notificationCallbackOnClose = app.notification.create({
	icon: '<i class="icon demo-icon">7</i>',
	title: 'Framework7',
	titleRightText: 'now',
	subtitle: 'Notification with close on click',
	text: 'Click me to close',
	closeOnClick: true,
	on: {
		close: function () {
			app.dialog.alert('Notification closed');
		},
	},
});
// Open Notifications
$$('.open-full').on('click', function () {
	notificationFull.open();
});
$$('.open-with-button').on('click', function () {
	notificationWithButton.open();
});
$$('.open-click-to-close').on('click', function () {
	notificationClickToClose.open();
});
$$('.open-callback-on-close').on('click', function () {
	notificationCallbackOnClose.open();
});