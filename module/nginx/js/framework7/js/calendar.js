var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var calendarDefault = app.calendar.create({
	inputEl: '#demo-calendar-default',
});
//Custom Date Format
var calendarDateFormat = app.calendar.create({
	inputEl: '#demo-calendar-date-format',
	dateFormat: 'DD, MM dd, yyyy'
});
//Multiple Values
var calendarMultiple = app.calendar.create({
	inputEl: '#demo-calendar-multiple',
	dateFormat: 'M dd yyyy',
	multiple: true
});
//Range Picker
var calendarRange = app.calendar.create({
	inputEl: '#demo-calendar-range',
	dateFormat: 'M dd yyyy',
	rangePicker: true
});
//Open In Modal
var calendarModal = app.calendar.create({
	inputEl: '#demo-calendar-modal',
	openIn: 'customModal',
	header: true,
	footer: true,
	dateFormat: 'MM dd yyyy',
});
//With Events
var today = new Date();
var weekLater = new Date().setDate(today.getDate() + 7);
var calendarEvents = app.calendar.create({
	inputEl: '#demo-calendar-events',
	dateFormat: 'M dd yyyy',
	events: {
		from: today,
		to: weekLater
	}
});
//Disabled Dates
var today = new Date();
var weekLater = new Date().setDate(today.getDate() + 7);
var calendarDisabled = app.calendar.create({
	inputEl: '#demo-calendar-disabled',
	dateFormat: 'M dd yyyy',
	disabled: {
		from: today,
		to: weekLater
	}
});
//Inline With Custom Toolbar
var $$ = Dom7;
var monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August' , 'September' , 'October', 'November', 'December'];
var calendarInline = app.calendar.create({
	containerEl: '#demo-calendar-inline-container',
	value: [new Date()],
	weekHeader: false,
	renderToolbar: function () {
		return '<div class="toolbar calendar-custom-toolbar no-shadow">' +
			'<div class="toolbar-inner">' +
			'<div class="left">' +
			'<a href="#" class="link icon-only"><i class="icon icon-back ' + (app.theme === 'md' ? 'color-black' : '') + '"></i></a>' +
			'</div>' +
			'<div class="center"></div>' +
			'<div class="right">' +
			'<a href="#" class="link icon-only"><i class="icon icon-forward ' + (app.theme === 'md' ? 'color-black' : '') + '"></i></a>' +
			'</div>' +
			'</div>' +
			'</div>';
	},
	on: {
		init: function (c) {
			$$('.calendar-custom-toolbar .center').text(monthNames[c.currentMonth] +', ' + c.currentYear);
			$$('.calendar-custom-toolbar .left .link').on('click', function () {
				calendarInline.prevMonth();
			});
			$$('.calendar-custom-toolbar .right .link').on('click', function () {
				calendarInline.nextMonth();
			});
		},
		monthYearChangeStart: function (c) {
			$$('.calendar-custom-toolbar .center').text(monthNames[c.currentMonth] +', ' + c.currentYear);
		}
	}
});