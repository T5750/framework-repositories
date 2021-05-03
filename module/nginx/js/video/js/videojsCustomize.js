var video = document.getElementById('my-video');
$("#reload").click(function () {
	video.pause();
	video.setAttribute('src', 'https://vjs.zencdn.net/v/oceans.mp4');
	video.load();
	video.play();
});