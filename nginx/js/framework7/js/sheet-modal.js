var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// DOM events for my-sheet sheet
$$('.my-sheet').on('sheet:open', function (e, sheet) {
	console.log('my-sheet open');
});
$$('.my-sheet').on('sheet:opened', function (e, sheet) {
	console.log('my-sheet opened');
});
// Create dynamic Sheet
var dynamicSheet = app.sheet.create({
	content: '<div class="sheet-modal">'+
		'<div class="toolbar">'+
		'<div class="toolbar-inner">'+
		'<div class="left"></div>'+
		'<div class="right">'+
		'<a class="link sheet-close">Done</a>'+
		'</div>'+
		'</div>'+
		'</div>'+
		'<div class="sheet-modal-inner">'+
		'<div class="block">'+
		'<p>Sheet created dynamically.</p>'+
		'<p><a href="#" class="link sheet-close">Close me</a></p>'+
		'</div>'+
		'</div>'+
		'</div>',
	// Events
	on: {
		open: function (sheet) {
			console.log('Sheet open');
		},
		opened: function (sheet) {
			console.log('Sheet opened');
		},
	}
});
// Events also can be assigned on instance later
dynamicSheet.on('close', function (sheet) {
	console.log('Sheet close');
});
dynamicSheet.on('closed', function (sheet) {
	console.log('Sheet closed');
});
// Open dynamic sheet
$$('.dynamic-sheet').on('click', function () {
	// Close inline sheet before
	app.sheet.close('.my-sheet');
	// Open dynamic sheet
	dynamicSheet.open();
});