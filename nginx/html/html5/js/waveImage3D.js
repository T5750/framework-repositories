var solid = document.getElementsByClassName('solid')[0];
var btn = document.querySelectorAll('ol li');
var oUl = document.getElementsByClassName('oUl')[0];
var css = document.getElementsByTagName('style')[0];
var timer, n = 0;
createDom();
function createDom() {
	var num = 100, uHTML = '', pHTML = '', tHTML = '';
	var allWidth = parseInt(getComputedStyle(solid, null).width);
	var width = allWidth / num;
	for (var i = 0; i < num; i++) {
		uHTML += '<li><div></div><div></div><div></div><div></div></li>';
		pHTML += '.solid ul li:nth-child(' + (i + 1) + ') div{background-position-x: ' + (-i * width) + 'px;}';
		tHTML += '.solid ul li:nth-child(' + (i + 1) + '){transition: 0.8s ' + (0.3 * i / num) + 's}';
	}
	oUl.innerHTML = uHTML;
	css.innerHTML += pHTML + tHTML + '.solid ul li, .solid ul li div{width:' + width + 'px;height:100%}';
	bindEvent();
	play();
}
function bindEvent() {
	for (var i = 0; i < btn.length; i++) {
		btn[i].index = i;
		btn[i].onclick = function () {
			n = this.index;
			for (var i = 0; i < btn.length; i++) {
				btn[i].className = '';
			}
			this.className = 'on';
			css.innerHTML += '.solid ul li{transform: translateZ(-180px) rotateX(' + (n * 90) + 'deg);}';
		}
	}
	;
	solid.onmouseenter = function () {
		clearInterval(timer);
	};
	solid.onmouseleave = function () {
		play();
	};
}
function play() {
	clearInterval(timer);
	timer = setInterval(function () {
		n++;
		n %= 4;
		for (var i = 0; i < btn.length; i++) {
			btn[i].className = '';
		}
		btn[n].className = 'on';
		css.innerHTML += '.solid ul li{transform: translateZ(-180px) rotateX(' + (n * 90) + 'deg);}';
	}, 4000);
}