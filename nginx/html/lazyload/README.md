# 图片懒加载

## lazysizes
1. Download the [lazysizes.min.js](http://afarkas.github.io/lazysizes/lazysizes.min.js) script and include **lazysizes** in your webpage.
	```
	<script src="lazysizes.min.js" async=""></script>
	```
2. lazysizes does not need any JS configuration: Add the `class` `"lazyload"` to your images/iframes in conjunction with a `data-src` and/or `data-srcset` attribute. Optionally you can also add a `src` attribute with a low quality image:
	```
	<!-- non-responsive: -->
	<img data-src="image.jpg" class="lazyload" />
	```
	```
	<!-- responsive example with automatic sizes calculation: -->
	<img
		data-sizes="auto"
		data-src="image2.jpg"
		data-srcset="image1.jpg 300w,
		image2.jpg 600w,
		image3.jpg 900w" class="lazyload" />
	```
	```
	<!-- iframe example -->
	<iframe frameborder="0"
		class="lazyload"
		allowfullscreen=""
		data-src="//www.youtube.com/embed/ZfV-aYdU4uE">
	</iframe>
	```

## Results
- `lazysizes.html`
- `lazysizes-examples.html`
- `lazy-image.html`
- `lazyload.html`

## References
- [lazysizes](https://github.com/aFarkas/lazysizes)
- [图片懒加载](https://juejin.im/post/5bbc60e8f265da0af609cd04)
- [原生js实现图片懒加载（lazyLoad）](https://zhuanlan.zhihu.com/p/55311726)