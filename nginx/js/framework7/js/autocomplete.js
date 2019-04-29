var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// Fruits data demo array
var fruits = ('Apple Apricot Avocado Banana Melon Orange Peach Pear Pineapple').split(' ');
//Simple Dropdown Autocomplete
var autocompleteDropdownSimple = app.autocomplete.create({
	inputEl: '#autocomplete-dropdown',
	openIn: 'dropdown',
	source: function (query, render) {
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Find matched items
		for (var i = 0; i < fruits.length; i++) {
			if (fruits[i].toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(fruits[i]);
		}
		// Render items by passing array with result items
		render(results);
	}
});
//Dropdown With Input Expand
var autocompleteDropdownExpand = app.autocomplete.create({
	inputEl: '#autocomplete-dropdown-expand',
	openIn: 'dropdown',
	expandInput: true, // expand input
	source: function (query, render) {
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Find matched items
		for (var i = 0; i < fruits.length; i++) {
			if (fruits[i].toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(fruits[i]);
		}
		// Render items by passing array with result items
		render(results);
	}
});
//Dropdown With All Values
var autocompleteDropdownAll = app.autocomplete.create({
	inputEl: '#autocomplete-dropdown-all',
	openIn: 'dropdown',
	source: function (query, render) {
		var results = [];
		// Find matched items
		for (var i = 0; i < fruits.length; i++) {
			if (fruits[i].toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(fruits[i]);
		}
		// Render items by passing array with result items
		render(results);
	}
});
//Dropdown With Placeholder
var autocompleteDropdownPlaceholder = app.autocomplete.create({
	inputEl: '#autocomplete-dropdown-placeholder',
	openIn: 'dropdown',
	dropdownPlaceholderText: 'Try to type "Apple"',
	source: function (query, render) {
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Find matched items
		for (var i = 0; i < fruits.length; i++) {
			if (fruits[i].toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(fruits[i]);
		}
		// Render items by passing array with result items
		render(results);
	}
});
//Dropdown With Typeahead
var autocompleteDropdownTypeahead = app.autocomplete.create({
	inputEl: '#autocomplete-dropdown-typeahead',
	openIn: 'dropdown',
	dropdownPlaceholderText: 'Try to type "Pineapple"',
	typeahead: true,
	source: function (query, render) {
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Find matched items
		for (var i = 0; i < fruits.length; i++) {
			if (fruits[i].toLowerCase().indexOf(query.toLowerCase()) === 0) results.push(fruits[i]);
		}
		// Render items by passing array with result items
		render(results);
	}
});
//Dropdown With Ajax-Data
var autocompleteDropdownAjax = app.autocomplete.create({
	inputEl: '#autocomplete-dropdown-ajax',
	openIn: 'dropdown',
	preloader: true, //enable preloader
	/* If we set valueProperty to "id" then input value on select will be set according to this property */
	valueProperty: 'name', //object's "value" property name
	textProperty: 'name', //object's "text" property name
	limit: 20, //limit to 20 results
	dropdownPlaceholderText: 'Try "JavaScript"',
	source: function (query, render) {
		var autocomplete = this;
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Show Preloader
		autocomplete.preloaderShow();
		// Do Ajax request to Autocomplete data
		app.request({
			url: 'json/autocomplete-languages.json',
			method: 'GET',
			dataType: 'json',
			//send "query" to server. Useful in case you generate response dynamically
			data: {
				query: query,
			},
			success: function (data) {
				// Find matched items
				for (var i = 0; i < data.length; i++) {
					if (data[i].name.toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(data[i]);
				}
				// Hide Preoloader
				autocomplete.preloaderHide();
				// Render items by passing array with result items
				render(results);
			}
		});
	}
});
//Dropdown With Ajax-Data + Typeahead
var autocompleteDropdownAjaxTypeahead = app.autocomplete.create({
	inputEl: '#autocomplete-dropdown-ajax-typeahead',
	openIn: 'dropdown',
	preloader: true, //enable preloader
	/* If we set valueProperty to "id" then input value on select will be set according to this property */
	valueProperty: 'name', //object's "value" property name
	textProperty: 'name', //object's "text" property name
	limit: 20, //limit to 20 results
	typeahead: true,
	dropdownPlaceholderText: 'Try "JavaScript"',
	source: function (query, render) {
		var autocomplete = this;
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Show Preloader
		autocomplete.preloaderShow();
		// Do Ajax request to Autocomplete data
		app.request({
			url: 'json/autocomplete-languages.json',
			method: 'GET',
			dataType: 'json',
			//send "query" to server. Useful in case you generate response dynamically
			data: {
				query: query,
			},
			success: function (data) {
				// Find matched items
				for (var i = 0; i < data.length; i++) {
					if (data[i].name.toLowerCase().indexOf(query.toLowerCase()) === 0) results.push(data[i]);
				}
				// Hide Preoloader
				autocomplete.preloaderHide();
				// Render items by passing array with result items
				render(results);
			}
		});
	}
});
//Searchbar Dropdown
var searchbar = app.searchbar.create({
	el: '#searchbar-autocomplete',
	customSearch: true,
	on: {
		search: function (query) {
			console.log(query);
		}
	}
});
var autocompleteSearchbar = app.autocomplete.create({
	openIn: 'dropdown',
	inputEl: '#searchbar-autocomplete input[type="search"]',
	dropdownPlaceholderText: 'Type "Apple"',
	source: function (query, render) {
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Find matched items
		for (var i = 0; i < fruits.length; i++) {
			if (fruits[i].toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(fruits[i]);
		}
		// Render items by passing array with result items
		render(results);
	}
});
//Simple Standalone Autocomplete
var autocompleteStandaloneSimple = app.autocomplete.create({
	openIn: 'page', //open in page
	openerEl: '#autocomplete-standalone', //link that opens autocomplete
	closeOnSelect: true, //go back after we select something
	source: function (query, render) {
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Find matched items
		for (var i = 0; i < fruits.length; i++) {
			if (fruits[i].toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(fruits[i]);
		}
		// Render items by passing array with result items
		render(results);
	},
	on: {
		change: function (value) {
			console.log(value);
			// Add item text value to item-after
			$$('#autocomplete-standalone').find('.item-after').text(value[0]);
			// Add item value to input value
			$$('#autocomplete-standalone').find('input').val(value[0]);
		},
	},
});
//Popup Autocomplete
var autocompleteStandalonePopup = app.autocomplete.create({
	openIn: 'popup', //open in page
	openerEl: '#autocomplete-standalone-popup', //link that opens autocomplete
	closeOnSelect: true, //go back after we select something
	source: function (query, render) {
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Find matched items
		for (var i = 0; i < fruits.length; i++) {
			if (fruits[i].toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(fruits[i]);
		}
		// Render items by passing array with result items
		render(results);
	},
	on: {
		change: function (value) {
			// Add item text value to item-after
			$$('#autocomplete-standalone-popup').find('.item-after').text(value[0]);
			// Add item value to input value
			$$('#autocomplete-standalone-popup').find('input').val(value[0]);
		},
	},
});
//Multiple Values
var autocompleteStandaloneMultiple = app.autocomplete.create({
	openIn: 'page', //open in page
	openerEl: '#autocomplete-standalone-multiple', //link that opens autocomplete
	multiple: true, //allow multiple values
	source: function (query, render) {
		var autocomplete = this;
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Find matched items
		for (var i = 0; i < fruits.length; i++) {
			if (fruits[i].toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(fruits[i]);
		}
		// Render items by passing array with result items
		render(results);
	},
	on: {
		change: function (value) {
			// Add item text value to item-after
			$$('#autocomplete-standalone-multiple').find('.item-after').text(value.join(', '));
			// Add item value to input value
			$$('#autocomplete-standalone-multiple').find('input').val(value.join(', '));
		}
	}
});
//Standalone With Ajax-Data
var autocompleteStandaloneAjax = app.autocomplete.create({
	openIn: 'page', //open in page
	openerEl: '#autocomplete-standalone-ajax', //link that opens autocomplete
	multiple: true, //allow multiple values
	valueProperty: 'id', //object's "value" property name
	textProperty: 'name', //object's "text" property name
	limit: 50,
	preloader: true, //enable preloader
	source: function (query, render) {
		var autocomplete = this;
		var results = [];
		if (query.length === 0) {
			render(results);
			return;
		}
		// Show Preloader
		autocomplete.preloaderShow();
		// Do Ajax request to Autocomplete data
		app.request({
			url: 'json/autocomplete-languages.json',
			method: 'GET',
			dataType: 'json',
			//send "query" to server. Useful in case you generate response dynamically
			data: {
				query: query
			},
			success: function (data) {
				// Find matched items
				for (var i = 0; i < data.length; i++) {
					if (data[i].name.toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(data[i]);
				}
				// Hide Preoloader
				autocomplete.preloaderHide();
				// Render items by passing array with result items
				render(results);
			}
		});
	},
	on: {
		change: function (value) {
			var itemText = [],
				inputValue = [];
			for (var i = 0; i < value.length; i++) {
				itemText.push(value[i].name);
				inputValue.push(value[i].id);
			}
			// Add item text value to item-after
			$$('#autocomplete-standalone-ajax').find('.item-after').text(itemText.join(', '));
			// Add item value to input value
			$$('#autocomplete-standalone-ajax').find('input').val(inputValue.join(', '));
		},
	},
});