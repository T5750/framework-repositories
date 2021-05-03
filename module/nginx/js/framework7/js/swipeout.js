var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
//Swipe for actions
var moreActions = app.actions.create({
	buttons: [
		[
			{
				text: 'Here comes some optional description or warning for actions below',
				label: true,
			},
			{
				text: 'Action 1',
			},
			{
				text: 'Action 2',
			},
		],
		[
			{
				text: 'Cancel',
				bold: true,
			}
		]
	],
});
$$('.open-more-actions').on('click', function () {
	moreActions.open();
});
//With callback on remove
$$('.deleted-callback').on('swipeout:deleted', function () {
	app.dialog.alert('Thanks, item removed!');
});
//With actions on left side (swipe to right)
$$('.alert-reply').on('click', function () {
	app.dialog.alert('Reply');
});
$$('.alert-forward').on('click', function () {
	app.dialog.alert('Forward');
});