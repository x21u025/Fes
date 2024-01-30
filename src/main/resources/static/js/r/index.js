let selectList = {};

function incrementMenu(id) {
	selectList[id] = !selectList[id] ? 1 : selectList[id] + 1;
	show();
}

function decrementMenu(id) {
	selectList[id] = !selectList[id] || selectList[id] == 0 ? 0 : selectList[id] - 1;
	if(selectList[id] == 0) delete selectList[id];
	show();
}

function clearCart() {
	selectList = {};
	show();
}

function show() {
	const cartFragments = document.createElement('ul');
	let total = 0;
	let count = 0;

	for(const key of Object.keys(selectList)) {
		const value = selectList[key];

		const itemToClone = document.getElementById(key);
		if (itemToClone) {
			const clonedItem = itemToClone.cloneNode(true);

			// .cart内のinput要素を削除
			const inputs = clonedItem.querySelectorAll('button');
			inputs.forEach(input => input.remove());

			// 個数要素を追加
			const quantity = document.createElement('span');
			quantity.classList.add('quantity');
			quantity.textContent = value;
			clonedItem.getElementsByClassName('item')[0].appendChild(quantity);

			// フラグメントに複製した.item要素を追加
			cartFragments.appendChild(clonedItem);
		}

		total += 100 * selectList[key];
		count += selectList[key];
	}
	document.querySelector('.cart > ul').replaceWith(cartFragments);
	document.querySelector('.total > .money').textContent = count + '点 ' + total.toLocaleString();
}

function checkCart() {
	if(Object.keys(selectList).length != 0) {
		const check = document.getElementsByClassName('check')[0];
		check.classList.add('show');
		check.querySelectorAll('table tr').forEach(tr => {
			if(!tr.classList.contains('nodel')) {
				tr.remove();
			}
		});

		const checkTable = check.getElementsByTagName('tbody')[0];

		let total = 0;
		let count = 0;

		for(const key of Object.keys(selectList)) {
			const item = document.getElementById(key);
			if (item) {
				const tr = document.createElement('tr');

				const tr_name = document.createElement('td');
				tr.appendChild(tr_name);
				tr_name.textContent = item.getElementsByClassName("name")[0].textContent;

				const tr_prize = document.createElement('td');
				tr.appendChild(tr_prize);
				tr_prize.textContent = item.getElementsByClassName("money")[0].textContent;

				const tr_quantity = document.createElement('td');
				tr.appendChild(tr_quantity);
				tr_quantity.textContent = selectList[key];

				checkTable.appendChild(tr);
			}

			total += 100 * selectList[key];
			count += selectList[key];
		}

		const tr_total = document.createElement('tr');
		tr_total.classList.add('check_total');

		const tr_name = document.createElement('td');
		tr_total.appendChild(tr_name);
		tr_name.textContent = '合計';

		const tr_prize = document.createElement('td');
		tr_total.appendChild(tr_prize);
		tr_prize.textContent = total.toLocaleString();

		const tr_quantity = document.createElement('td');
		tr_total.appendChild(tr_quantity);
		tr_quantity.textContent = count;

		checkTable.appendChild(tr_total);
	}
}

function hideCheck() {
	const check = document.getElementsByClassName('check')[0];
	check.classList.remove('show');
}

function sendCart() {
	if(Object.keys(selectList).length != 0) {
		var xhr = new XMLHttpRequest();

		xhr.open('POST', '/r/register');
		xhr.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
		xhr.send('cart=' + JSON.stringify(selectList));
		clearCart();
		hideCheck();
	}
}

document.addEventListener('DOMContentLoaded', function() {
	/* ピッチインピッチアウトによる拡大縮小を禁止 */
	document.documentElement.addEventListener('touchstart', function (e) {
		if (e.touches.length >= 2) {e.preventDefault();}
	}, {passive: false});

	const buttons = document.getElementsByTagName('button');
	for(const button of buttons) {
		button.addEventListener('pointerdown', function(e) {
			if(e.pointerType !== 'pen') return;
			e.target.click();
			e.classList.add('active-button');
			setTimeout(function() {
				e.classList.remove('active-button');
			}, 100); // 100ミリ秒後にクラスを削除
		});
	}

});

function disableScroll(event) {
	event.preventDefault();
}

// イベントと関数を紐付け
document.addEventListener('touchmove', disableScroll, { passive: false });
