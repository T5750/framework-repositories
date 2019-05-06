var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// If we need it in place where we don't have access to app instance or before we init the app
// var now = Framework7.utils.now();
var now = app.utils.now();
console.log(now);
// parseUrlQuery
var query = app.utils.parseUrlQuery('../routes/pages/about-me/contacts.html?id=5&foo=bar');
console.log(query); // { id: 5, foo: 'bar' }
// serializeObject
var params = {foo: 'bar', id: 5};
console.log(app.utils.serializeObject(params)); // 'foo=bar&id=5'
// requestAnimationFrame
var animId;

function anim() {
	var current = app.utils.now();
	if (current - now >= 5000) {
		// cancelAnimationFrame
		app.utils.cancelAnimationFrame(animId);
	} else {
		var left = parseInt($$('#anim').css('left'), 10) + 1;
		$$('#anim').css({left: left + 'px'});
		animId = app.utils.requestAnimationFrame(anim);
	}
}

animId = app.utils.requestAnimationFrame(anim);
// removeDiacritics
var text = app.utils.removeDiacritics('ÁÓL');
console.log(text); //-> 'AOL'
// nextFrame
app.utils.nextFrame(function (callback) {
	// do something on next frame
	console.log(callback);
});
// nextTick
app.utils.nextTick(function (callback, delay) {
	// do something on next tick
});
// now
setTimeout(function () {
	var timeDiff = app.utils.now() - now;
	console.log(timeDiff + 'ms past');
}, 2000);
// extend
var a = {
	apple: 0,
	cherry: 97
};
// Pass as empty object as target to copy a into b
var b = app.utils.extend({}, a);
console.log(b); // { apple:0, cherry: 97 }
console.log(a === b); // false
var a = {
	apple: 0,
	cherry: 97
};
var b = {
	bannana: 10,
	pineapple: 20
};
// Extends a with b
app.utils.extend(a, b);
console.log(a); // { apple: 0, cherry: 97, bannana: 10, peneapple: 20 }
var a = {
	apple: 0,
	cherry: 97
};
var b = {
	bannana: 10,
	pineapple: 20
};
// Create new object c from the merge of a and b
var c = app.utils.extend({}, a, b);
console.log(c); // { apple: 0, cherry: 97, bannana: 10, peneapple: 20 }
var a = {
	apple: 0,
	cherry: 97
};
var b = {
	apple: 10,
	pineapple: 20
};
// Extend a with b
app.utils.extend(a, b);
console.log(a); // { apple: 10, cherry: 97, peneapple: 20 }