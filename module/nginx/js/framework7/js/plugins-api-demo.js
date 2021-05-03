// install plugin first
Framework7.use(debugPlugin);
var app = new Framework7({
	root: '#app',
	//enable debugger
	debugger: true
});
var mainView = app.views.create('.view-main');
/*
  we can later disable it by calling
  app.debugger.disable();
*/