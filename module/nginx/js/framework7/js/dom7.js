var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
//Export DOM7 to local variable to make it easy accessable
var $$ = Dom7;
//Classes
$$('.something').on('click', function (e) {
	$$(this).addClass('hello').attr('title', 'world').insertAfter('.something-else');
});
$$('p').hasClass('intro'); //-> true
$$('h1, h2').toggleClass('intro');
//Attributes and properties
var isChecked = $$('input').prop('checked');
console.log(isChecked);
var link = $$('a').attr('href'); //-> http://google.com
console.log(link);
var inputVal = $$('#myInput').val(); //-> 'Lorem ipsum'
console.log(inputVal);
$$('input#myInput').val(inputVal + ' | New value here');
//Data storage
var id = $$('p#storage').data('id'); //-> 123
console.log(id);
//Data Set
var dataset = $$('#my-div').dataset();
/*
 dataset will contain plain object with camelCase keys and with formatted boolean and number types:
 {
 loop: true,
 animatePages: false,
 index: 0,
 hello: 'world'
 }
 */
console.log(dataset);
//Events
$$('a').on('click', function (e) {
	console.log('clicked');
});
$$('input[type="text"]').on('keyup keydown change', function (e) {
	console.log('input value changed');
});
$$(document).on('click', 'a', function (e) {
	console.log('link clicked');
});
$$('a').once('click', function (e) {
	console.log('once clicked');
});
$$('input[type="text"]').once('keyup keydown change', function (e) {
	console.log('once input value changed');
});
$$(document).once('click', 'a', function (e) {
	console.log('once link clicked');
});
function clickHandler() {
	console.log('handler clicked');
}
// Add event listener
$$('a').on('click', clickHandler);
// Remove event listener
//$$('a').off('click', clickHandler);
function clickHandler() {
	console.log('link handler clicked');
}
// Add event listener
$$(document).on('click', 'a', clickHandler);
// Remove event listener
//$$(document).off('click', 'a', clickHandler);
//Styles
var boxWidth = $$('div#box').width();
console.log(boxWidth);
var outerWidth = $$('div#box').outerWidth(true);
console.log(outerWidth);
var boxHeight = $$('div#box').height();
console.log(boxHeight);
var outerHeight = $$('div#box').outerHeight(true);
console.log(outerHeight);
var coords = $$('.content').offset(); //-> {top: 100, left: 200}
console.log(coords);
var top = coords.top; //-> 100
console.log(top);
var left = coords.left; //-> 200
console.log(left);
$$('.content').css('left'); //-> 200px
$$('a').css({
	left: '100px',
	top: '200px',
	color: 'red',
	width: '300px',
	marginLeft: '17px',
	'padding-right': '20px'
});
//Dom Manipulation
var links = $$('a');
var divs = $$('div');
links.add('p').addClass('blue');
links.add(divs).addClass('red');
var redLinks = $$('a').filter(function (index, el) {
	return $$(this).hasClass('red');
});
$$('#animate-me').animate(
	/* CSS properties to animate, e.g.: */
	{
		'margin-left': 100,
		'width': 200,
		'height': 300,
		'opacity': 0.5
	},
	/* Animation parameters */
	{
		// Animation duraion in ms, optional (default to 300)
		duration: 300,
		// Animation easing, optional (default to 'swing'), can be also 'linear'
		easing: 'swing',
		/* Callbacks */
		// Animation begins, optional
		begin: function (elements) {
			console.log('animation began');
		},
		// Animation completed, optional
		complete: function (elements) {
			console.log('animation completed');
		},
		// Animation in progress, optional
		progress: function (elements, complete, remaining, start) {
			/* Where
			 complete - The call's completion percentage (as a decimal value)
			 remaining - How much time remains until the call completes (in ms)
			 start - The absolute time at which the call began (in ms)
			 */
			console.log('animation in progress');
		}
	}
);
/*
 $$('#animate-me')
 .animate(
 {
 'margin-left': 100,
 'width': 200,
 'height': 300,
 'opacity': 0.5
 }
 )
 .animate(
 {
 'width': 50,
 'height': 50
 }
 );
 */