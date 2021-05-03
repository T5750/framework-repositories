var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// Dummy Content
var songs = ['Yellow Submarine', 'Don\'t Stop Me Now', 'Billie Jean', 'Californication'];
var authors = ['Beatles', 'Queen', 'Michael Jackson', 'Red Hot Chili Peppers'];
// Pull to refresh content
var $ptrContent = $$('.ptr-content');
// Add 'refresh' listener on it
$ptrContent.on('ptr:refresh', function (e) {
	// Emulate 2s loading
	setTimeout(function () {
		var picURL = 'http://lorempixel.com/88/88/abstract/' + Math.round(Math.random() * 10);
		var song = songs[Math.floor(Math.random() * songs.length)];
		var author = authors[Math.floor(Math.random() * authors.length)];
		var itemHTML =
			'<li class="item-content">' +
			'<div class="item-media"><img src="' + picURL + '" width="44"/></div>' +
			'<div class="item-inner">' +
			'<div class="item-title-row">' +
			'<div class="item-title">' + song + '</div>' +
			'</div>' +
			'<div class="item-subtitle">' + author + '</div>' +
			'</div>' +
			'</li>';
		// Prepend new list element
		$ptrContent.find('ul').prepend(itemHTML);
		// When loading done, we need to reset it
		app.ptr.done(); // or e.detail();
	}, 2000);
});