var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
var $$ = Dom7;
// Init Messages
var messages = app.messages.create({
});
// Init messagebar
var messagebar = app.messagebar.create({
	el: '.messagebar',
	attachments: []
});
// Available Images
var images = [
	'http://lorempixel.com/300/300/cats/1/',
	'http://lorempixel.com/200/300/cats/2/',
	'http://lorempixel.com/400/300/cats/3/',
	'http://lorempixel.com/300/150/cats/4/',
	'http://lorempixel.com/150/300/cats/5/',
	'http://lorempixel.com/300/300/cats/6/',
	'http://lorempixel.com/300/300/cats/7/',
	'http://lorempixel.com/200/300/cats/8/',
	'http://lorempixel.com/400/300/cats/9/',
	'http://lorempixel.com/300/150/cats/10/'
];
// Create sheet items
var sheetHtml = '';
images.forEach(function (image) {
	sheetHtml +=
		'<label class="checkbox messagebar-sheet-image" style="background-image:url(' + image + ')">' +
		'<input type="checkbox">' +
		'<i class="icon icon-checkbox"></i>' +
		'</label>';
});
$$('.messagebar-sheet').html(sheetHtml);
/*========================
  Handle Attachments
  ========================*/
function checkAttachments() {
	if (messagebar.attachments.length > 0) {
		messagebar.attachmentsShow();
		messagebar.setPlaceholder('Add comment or Send');
	} else {
		messagebar.attachmentsHide();
		messagebar.setPlaceholder('Message');
	}
}
$$('.messagebar-sheet-image input').on('change', function (e) {
	var index = $$(e.target).parents('.messagebar-sheet-image').index();
	var image = images[index];
	if (e.target.checked) {
		// Add to attachments
		messagebar.attachments.unshift(image)
	} else {
		// Remove from attachments
		messagebar.attachments.splice(messagebar.attachments.indexOf(image), 1);
	}
	messagebar.renderAttachments();
	checkAttachments();
});
messagebar.on('attachmentDelete', function (messagebar, attachmentEl, attachmentIndex) {
	var image = messagebar.attachments.splice(attachmentIndex, 1)[0];
	messagebar.renderAttachments();
	checkAttachments();
	// Uncheck in sheet
	var imageIndex = images.indexOf(image);
	messagebar.$el.find('.messagebar-sheet .checkbox').eq(imageIndex).find('input').prop('checked', false);
});
/*========================
  Toggle Sheet
  ========================*/
$$('a.toggle-sheet').on('click', function () {
	messagebar.sheetToggle();
});