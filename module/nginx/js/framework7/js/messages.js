var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// Init Messages
var messages = app.messages.create({
	el: '.messages',
	// First message rule
	firstMessageRule: function (message, previousMessage, nextMessage) {
		// Skip if title
		if (message.isTitle) return false;
		/* if:
		  - there is no previous message
		  - or previous message type (send/received) is different
		  - or previous message sender name is different
		*/
		if (!previousMessage || previousMessage.type !== message.type || previousMessage.name !== message.name) return true;
		return false;
	},
	// Last message rule
	lastMessageRule: function (message, previousMessage, nextMessage) {
		// Skip if title
		if (message.isTitle) return false;
		/* if:
		  - there is no next message
		  - or next message type (send/received) is different
		  - or next message sender name is different
		*/
		if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
		return false;
	},
	// Last message rule
	tailMessageRule: function (message, previousMessage, nextMessage) {
		// Skip if title
		if (message.isTitle) return false;
		/* if (bascially same as lastMessageRule):
		- there is no next message
		- or next message type (send/received) is different
		- or next message sender name is different
	  */
		if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
		return false;
	}
});
// Init Messagebar
var messagebar = app.messagebar.create({
	el: '.messagebar'
});
// Response flag
var responseInProgress = false;
// Send Message
$$('.send-link').on('click', function () {
	var text = messagebar.getValue().replace(/\n/g, '<br>').trim();
	// return if empty message
	if (!text.length) return;
	// Clear area
	messagebar.clear();
	// Return focus to area
	messagebar.focus();
	// Add message to messages
	messages.addMessage({
		text: text,
	});
	if (responseInProgress) return;
	// Receive dummy message
	receiveMessage();
});
// Dummy response
var answers = [
	'Yes!',
	'No',
	'Hm...',
	'I am not sure',
	'And what about you?',
	'May be ;)',
	'Lorem ipsum dolor sit amet, consectetur',
	'What?',
	'Are you sure?',
	'Of course',
	'Need to think about it',
	'Amazing!!!'
]
var people = [
	{
		name: 'Kate Johnson',
		avatar: 'http://lorempixel.com/100/100/people/9'
	},
	{
		name: 'Blue Ninja',
		avatar: 'http://lorempixel.com/100/100/people/7'
	}
];
function receiveMessage() {
	responseInProgress = true;
	setTimeout(function () {
		// Get random answer and random person
		var answer = answers[Math.floor(Math.random() * answers.length)];
		var person = people[Math.floor(Math.random() * people.length)];
		// Show typing indicator
		messages.showTyping({
			header: person.name + ' is typing',
			avatar: person.avatar
		});
		setTimeout(function () {
			// Add received dummy message
			messages.addMessage({
				text: answer,
				type: 'received',
				name: person.name,
				avatar: person.avatar
			});
			// Hide typing indicator
			messages.hideTyping();
			responseInProgress = false;
		}, 4000);
	}, 1000);
}