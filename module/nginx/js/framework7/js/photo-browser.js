var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
/*=== Default standalone ===*/
var myPhotoBrowserStandalone = app.photoBrowser.create({
	photos : [
		'http://lorempixel.com/1024/1024/sports/1/',
		'http://lorempixel.com/1024/1024/sports/2/',
		'http://lorempixel.com/1024/1024/sports/3/',
	]
});
//Open photo browser on click
$$('.pb-standalone').on('click', function () {
	myPhotoBrowserStandalone.open();
});
/*=== Popup ===*/
var myPhotoBrowserPopup = app.photoBrowser.create({
	photos : [
		'http://lorempixel.com/1024/1024/sports/1/',
		'http://lorempixel.com/1024/1024/sports/2/',
		'http://lorempixel.com/1024/1024/sports/3/',
	],
	type: 'popup'
});
$$('.pb-popup').on('click', function () {
	myPhotoBrowserPopup.open();
});
/*=== As Page ===*/
var myPhotoBrowserPage = app.photoBrowser.create({
	photos : [
		'http://lorempixel.com/1024/1024/sports/1/',
		'http://lorempixel.com/1024/1024/sports/2/',
		'http://lorempixel.com/1024/1024/sports/3/',
	],
	type: 'page',
	backLinkText: 'Back'
});
$$('.pb-page').on('click', function () {
	myPhotoBrowserPage.open();
});
/*=== Standalone Dark ===*/
var myPhotoBrowserDark = app.photoBrowser.create({
	photos : [
		'http://lorempixel.com/1024/1024/sports/1/',
		'http://lorempixel.com/1024/1024/sports/2/',
		'http://lorempixel.com/1024/1024/sports/3/',
	],
	theme: 'dark'
});
$$('.pb-standalone-dark').on('click', function () {
	myPhotoBrowserDark.open();
});
/*=== Popup Dark ===*/
var myPhotoBrowserPopupDark = app.photoBrowser.create({
	photos : [
		'http://lorempixel.com/1024/1024/sports/1/',
		'http://lorempixel.com/1024/1024/sports/2/',
		'http://lorempixel.com/1024/1024/sports/3/',
	],
	theme: 'dark',
	type: 'popup'
});
$$('.pb-popup-dark').on('click', function () {
	myPhotoBrowserPopupDark.open();
});
/*=== With Captions ===*/
var myPhotoBrowserPopupDark = app.photoBrowser.create({
	photos : [
		{
			url: 'http://lorempixel.com/1024/1024/sports/1/',
			caption: 'Caption 1 Text'
		},
		{
			url: 'http://lorempixel.com/1024/1024/sports/2/',
			caption: 'Second Caption Text'
		},
		// This one without caption
		{
			url: 'http://lorempixel.com/1024/1024/sports/3/',
		},
	],
	theme: 'dark',
	type: 'standalone'
});
$$('.pb-standalone-captions').on('click', function () {
	myPhotoBrowserPopupDark.open();
});
/*=== With Video ===*/
var myPhotoBrowserPopupDark = app.photoBrowser.create({
	photos : [
		{
			html: '<iframe src="//www.youtube.com/embed/lmc21V-zBq0" frameborder="0" allowfullscreen></iframe>',
			caption: 'Woodkid - Run Boy Run (Official HD Video)'
		},
		{
			url: 'http://lorempixel.com/1024/1024/sports/2/',
			caption: 'Second Caption Text'
		},
		{
			url: 'http://lorempixel.com/1024/1024/sports/3/',
		},
	],
	theme: 'dark',
	type: 'standalone'
});
$$('.pb-standalone-video').on('click', function () {
	myPhotoBrowserPopupDark.open();
});