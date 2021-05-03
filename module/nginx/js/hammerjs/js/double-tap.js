// Get a reference to an element
var square = document.querySelector('.square');

// Create a manager to manager the element
var manager = new Hammer.Manager(square);

// Create a recognizer
var DoubleTap = new Hammer.Tap({
	event: 'doubletap',
	taps: 2
});

// Add the recognizer to the manager
manager.add(DoubleTap);

// Subscribe to desired event
manager.on('doubletap', function (e) {
	e.target.classList.toggle('expand');
});