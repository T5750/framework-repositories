var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// DOM events for About popover
$$('.popover-about').on('popover:open', function (e, popover) {
	console.log('About popover open');
});
$$('.popover-about').on('popover:opened', function (e, popover) {
	console.log('About popover opened');
});
// Create dynamic Popover
var dynamicPopover = app.popover.create({
	targetEl: 'a.dynamic-popover',
	content: '<div class="popover">'+
		'<div class="popover-inner">'+
		'<div class="block">'+
		'<p>Popover created dynamically.</p>'+
		'<p><a href="#" class="link popover-close">Close me</a></p>'+
		'</div>'+
		'</div>'+
		'</div>',
	// Events
	on: {
		open: function (popover) {
			console.log('Popover open');
		},
		opened: function (popover) {
			console.log('Popover opened');
		},
	}
});
// Events also can be assigned on instance later
dynamicPopover.on('close', function (popover) {
	console.log('Popover close');
});
dynamicPopover.on('closed', function (popover) {
	console.log('Popover closed');
});
// Open dynamic popover
$$('.dynamic-popover').on('click', function () {
	dynamicPopover.open();
});