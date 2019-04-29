var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// Set progress on inline progressbar
$$('.set-inline-progress').on('click', function (e) {
	var progress = $$(this).attr('data-progress');
	app.progressbar.set('#demo-inline-progressbar', progress);
});
// Function show determinate progressbar and emulate loading
determinateLoading = false;
function showDeterminate(inline) {
	determinateLoading = true;
	var progressBarEl;
	if (inline) {
		// inline progressbar
		progressBarEl = app.progressbar.show('#demo-determinate-container', 0);
	} else {
		// root progressbar
		progressBarEl = app.progressbar.show(0, app.theme === 'md' ? 'yellow' : 'blue');
	}
	var progress = 0;
	function simulateLoading() {
		setTimeout(function () {
			var progressBefore = progress;
			progress += Math.random() * 20;
			app.progressbar.set(progressBarEl, progress);
			if (progressBefore < 100) {
				simulateLoading(); //keep "loading"
			}
			else {
				determinateLoading = false;
				app.progressbar.hide(progressBarEl); //hide
			}
		}, Math.random() * 200 + 200);
	}
	simulateLoading();
}
// show inline determinate progressbar
$$('.show-determinate').on('click', function (e) {
	showDeterminate(true);
});
// show root determinate progressbar
$$('.show-determinate-root').on('click', function (e) {
	showDeterminate(false);
});
var infiniteLoading = false;
// show inline infinite progressbar
$$('.show-infinite').on('click', function () {
	app.progressbar.show(app.theme === 'md' ? 'yellow' : 'blue');
	setTimeout(function () {
		infiniteLoading = false;
		app.progressbar.hide();
	}, 3000);
});
// show root infinite progressbar
$$('.show-infinite-root').on('click', function () {
	app.progressbar.show('multi');
	setTimeout(function () {
		infiniteLoading = false;
		app.progressbar.hide();
	}, 3000);
});