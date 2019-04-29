var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
//- One group, three buttons
var ac1 = app.actions.create({
	buttons: [
		{
			text: 'Button1',
			bold: true
		},
		{
			text: 'Button2'
		},
		{
			text: 'Cancel',
			color: 'red'
		},
	]
})
//- One group, title, three buttons
var ac2 = app.actions.create({
	buttons: [
		{
			text: 'Do something',
			label: true
		},
		{
			text: 'Button1',
			bold: true
		},
		{
			text: 'Button2',
		},
		{
			text: 'Cancel',
			color: 'red'
		},
	]
});
//- Two groups
var ac3 = app.actions.create({
	buttons: [
		// First group
		[
			{
				text: 'Do something',
				label: true
			},
			{
				text: 'Button1',
				bold: true
			},
			{
				text: 'Button2',
			}
		],
		// Second group
		[
			{
				text: 'Cancel',
				color: 'red'
			}
		]
	]
});
//- Three groups
var ac4 = app.actions.create({
	buttons: [
		[
			{
				text: 'Share',
				label: true
			},
			{
				text: 'Mail',
			},
			{
				text: 'Messages',
			}
		],
		[
			{
				text: 'Social share',
				label: true
			},
			{
				text: 'Facebook',
			},
			{
				text: 'Twitter',
			}
		],
		[
			{
				text: 'Cancel',
				color: 'red'
			}
		]
	],
});
//- With callbacks on click
var ac5 = app.actions.create({
	buttons: [
		{
			text: 'Button1',
			onClick: function () {
				app.dialog.alert('Button1 clicked')
			}
		},
		{
			text: 'Button2',
			onClick: function () {
				app.dialog.alert('Button2 clicked')
			}
		},
		{
			text: 'Cancel',
			color: 'red',
			onClick: function () {
				app.dialog.alert('Cancel clicked')
			}
		},
	]
});
//- Actions Grid
var ac6 = app.actions.create({
	grid: true,
	buttons: [
		[
			{
				text: 'Button 1',
				icon: '<img src="http://lorempixel.com/96/96/people/1" width="48"/>'
			},
			{
				text: 'Button 2',
				icon: '<img src="http://lorempixel.com/96/96/people/2" width="48">'
			},
			{
				text: 'Button 3',
				icon: '<img src="http://lorempixel.com/96/96/people/3" width="48">'
			},
		],
		[
			{
				text: 'Button 1',
				icon: '<img src="http://lorempixel.com/96/96/fashion/4" width="48"/>'
			},
			{
				text: 'Button 2',
				icon: '<img src="http://lorempixel.com/96/96/fashion/5" width="48">'
			},
			{
				text: 'Button 3',
				icon: '<img src="http://lorempixel.com/96/96/fashion/6" width="48">'
			},
		]
	]
});
$$('.ac-1').on('click', function () {
	ac1.open();
});
$$('.ac-2').on('click', function () {
	ac2.open();
});
$$('.ac-3').on('click', function () {
	ac3.open();
});
$$('.ac-4').on('click', function () {
	ac4.open();
});
$$('.ac-5').on('click', function () {
	ac5.open();
});
$$('.ac-6').on('click', function () {
	ac6.open();
});