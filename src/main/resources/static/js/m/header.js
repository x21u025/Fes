document.addEventListener("DOMContentLoaded", function() {
	// ボタン要素を取得
	const openbtn1 = document.querySelector(".openbtn1");

	// クリックイベントリスナーを追加
	openbtn1.addEventListener("click", function () {
		// "active" クラスの有無を切り替え
		if (this.classList.contains("active")) {
			this.classList.remove("active");
		} else {
			this.classList.add("active");
		}
	});

	if ('serviceWorker' in navigator) {
		navigator.serviceWorker.register('js/sw.js').then(async (registration) => {
			console.log('ServiceWorker registration successful with scope: ', registration.scope);
		}).catch((err) => {
			console.log('ServiceWorker registration failed: ', err);
		});
	}
});