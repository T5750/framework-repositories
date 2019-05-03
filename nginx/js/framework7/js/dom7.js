var app = new Framework7({root: '#app'});
var mainView = app.views.create('.view-main');
//Export DOM7 to local variable to make it easy accessable
var $$ = Dom7;
$$('.something').on('click', function (e) {
	$$(this).addClass('hello').attr('title', 'world').insertAfter('.something-else');
});
$$('#animate-me').animate(
	/* CSS properties to animate, e.g.: */
	{
		'margin-left': 100,
		'width': 200,
		'height': 300,
		'opacity': 0.5
	},
	/* Animation parameters */
	{
		// Animation duraion in ms, optional (default to 300)
		duration: 300,
		// Animation easing, optional (default to 'swing'), can be also 'linear'
		easing: 'swing',
		/* Callbacks */
		// Animation begins, optional
		begin: function (elements) {
			console.log('animation began');
		},
		// Animation completed, optional
		complete: function (elements) {
			console.log('animation completed');
		},
		// Animation in progress, optional
		progress: function (elements, complete, remaining, start) {
			/* Where
			 complete - The call's completion percentage (as a decimal value)
			 remaining - How much time remains until the call completes (in ms)
			 start - The absolute time at which the call began (in ms)
			 */
			console.log('animation in progress');
		}
	}
);
/*
$$('#animate-me')
	.animate(
		{
			'margin-left': 100,
			'width': 200,
			'height': 300,
			'opacity': 0.5
		}
	)
	.animate(
		{
			'width': 50,
			'height': 50
		}
	);
	*/