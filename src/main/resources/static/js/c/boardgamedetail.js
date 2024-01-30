$(function () {
	$(".slider").slick({
		autoplay: false,
		arrows: false,
		fade: true,
		asNavFor: ".thumbnail",
	});
	$(".thumbnail").slick({
		slidesToShow: 8,
		asNavFor: ".slider",
		focusOnSelect: true,
	});
});