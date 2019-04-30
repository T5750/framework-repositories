var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// Create bottom toast
var toastBottom = app.toast.create({
	text: 'This is default bottom positioned toast',
	closeTimeout: 2000,
});
// Create top toast
var toastTop = app.toast.create({
	text: 'Top positioned toast',
	position: 'top',
	closeTimeout: 2000,
});
// Create center toast
var toastCenter = app.toast.create({
	text: 'I\'m on center',
	position: 'center',
	closeTimeout: 2000,
});
// Create toast with icon
var toastIcon = app.toast.create({
	icon: app.theme === 'ios' ? '<i class="f7-icons">star</i>' : '<i class="material-icons">star</i>',
	text: 'I\'m with icon',
	position: 'center',
	closeTimeout: 2000,
});
// Create toast with large message
var toastLargeMessage = app.toast.create({
	text: 'This toast contains a lot of text. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nihil, quae, ab. Delectus amet optio facere autem sapiente quisquam beatae culpa dolore.',
	closeTimeout: 2000,
});
// Create toast with button
var toastWithButton = app.toast.create({
	text: 'Toast with additional close button',
	closeButton: true,
});
// Create toast with custom button text
var toastWithCustomButton = app.toast.create({
	text: 'Custom close button',
	closeButton: true,
	closeButtonText: 'Close Me',
	closeButtonColor: 'red',
});
// Create toast with callback on close
var toastWithCallback = app.toast.create({
	text: 'Callback on close',
	closeButton: true,
	on: {
		close: function () {
			app.dialog.alert('Toast closed');
		},
	}
});
// Open toasts
$$('.open-toast-bottom').on('click', function () {
	toastBottom.open();
});
$$('.open-toast-top').on('click', function () {
	toastTop.open();
});
$$('.open-toast-center').on('click', function () {
	toastCenter.open();
});
$$('.open-toast-icon').on('click', function () {
	toastIcon.open();
});
$$('.open-toast-large').on('click', function () {
	toastLargeMessage.open();
});
$$('.open-toast-button').on('click', function () {
	toastWithButton.open();
});
$$('.open-toast-custom-button').on('click', function () {
	toastWithCustomButton.open();
});
$$('.open-toast-callback').on('click', function () {
	toastWithCallback.open();
});