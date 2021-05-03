// Get a reference to an element
var square = document.querySelector('.square');

// Create a manager to manage the element
var manager = new Hammer.Manager(square);

// Create a recognizer
var Tap = new Hammer.Tap({
	taps: 1
});

// Add the recognizer to the manager
manager.add(Tap);

// Subscribe to the desired event
manager.on('tap', function (e) {
	e.target.classList.toggle('expand');
});