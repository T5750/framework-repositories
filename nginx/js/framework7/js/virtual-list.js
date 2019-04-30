var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
// Dummy items array
var items = [];
for (var i = 1; i <= 10000; i++) {
	items.push({
		title: 'Item ' + i,
		subtitle: 'Subtitle ' + i
	});
}
var virtualList = app.virtualList.create({
	// List Element
	el: '.virtual-list',
	// Pass array with items
	items: items,
	// Custom search function for searchbar
	searchAll: function (query, items) {
		var found = [];
		for (var i = 0; i < items.length; i++) {
			if (items[i].title.toLowerCase().indexOf(query.toLowerCase()) >= 0 || query.trim() === '') found.push(i);
		}
		return found; //return array with mathced indexes
	},
	// List item Template7 template
	itemTemplate:
		'<li>' +
		'<a href="#" class="item-link item-content">' +
		'<div class="item-inner">' +
		'<div class="item-title-row">' +
		'<div class="item-title">{{title}}</div>' +
		'</div>' +
		'<div class="item-subtitle">{{subtitle}}</div>' +
		'</div>' +
		'</a>' +
		'</li>',
	// Item height
	height: app.theme === 'ios' ? 63 : 73,
});