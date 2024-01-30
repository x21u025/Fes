let count = 0;
let yn = {};

function next() {
	if(count++ < Object.keys(chart).length) {
		document.getElementById('text').innerHTML = chart[count].text.replace('\\n', '<br class="br">');
	} else {
		confirm();
	}
}

function no() {
	yn[chart[count].id] = false;
	next();
}

function yes() {
	yn[chart[count].id] = true;
	next();
}

function start() {
	for(let item of document.getElementsByClassName('yn')) {
		item.style = "display: inline-block;";
	}
	document.getElementsByClassName('start')[0].style = "display: none;";
	next();
}

function confirm() {
	document.getElementsByClassName('buttons')[0].style = "display: none";
	document.getElementsByClassName('text')[0].style = "height: auto";
	document.getElementById('text').textContent = "おすすめはこちら！";


	var xhr = new XMLHttpRequest();

	xhr.open('POST', '/c/yn');
	xhr.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
	xhr.onreadystatechange = function(){
		if (xhr.readyState == 4){
			if (xhr.status == 200){
				let data = xhr.responseText;
				const json = JSON.parse(data);

				let output = "<ul class='bglist'>";
				for(let i = 1; i <= 3; i++) {
					output += "<li><div class='item'>";
					output += "<img src='" + json[i]['pic'] + "' style='float: left'>";
					output += "<span class='name'>" + json[i]['name'] + "</span>";
					output += "<span class='desc'>" + json[i]['desc'] + "</span>";
					output += "<span class='recommended'>推奨人数</span>";
					output += "<span class='player'>" + json[i]['player'] + "</span>";
					output += "</li></div>";
				}
				output += "</ul>"

				output += "<table class='ynchart'><tr><th>No</th><th></th><th>Yes</th></tr>";

				let j = json[0];
				for(let iKeys of Object.keys(j)) {
					let iJ = j[iKeys];
					output += "<tr><td>" + (iJ['yn'] == "X" ? "X" : "") + "</td><td>" + iJ['text'] + "</td><td>" + (iJ['yn'] == "O" ? "O" : "") + "</td></tr>";
				}
				output += "</table><a class='reset' href='/yn'>もう一度遊ぶ丼</a>"

				document.getElementsByClassName('result')[0].innerHTML = output;

				if("DEBUG" == "DEBUG1") {
					console.log(data);

					let output = "<table style='display: inline-block;'><tr><th>連順</th><th>正誤</th><th>テキスト</th></tr>";
					const json = JSON.parse(data);

					for(let keys of Object.keys(json)) {
						let j = json[keys];
						if(keys == 0) {
							for(let iKeys of Object.keys(j)) {
								let iJ = j[iKeys];
								output += "<tr><td>" + iKeys + "</td><td>" + iJ['yn'] + "</td><td>" + iJ['text'] + "</td></tr>";
							}
							output += "<tr style='height: 10px;'><td colspan='3'> </td></tr>";
							output += "<tr><th>順位</th><th>得点</th><th>名前</th></tr>";
						} else {
							output += "<tr><td>" + keys + "</td><td>" + j['count'] + "</td><td>" + j['name'] + "</td></tr>";
						}
					}
					output += "</table>"

					document.getElementsByClassName('result')[0].innerHTML = output;
				}
			}
		}
	}
	xhr.send('yn=' + JSON.stringify(yn));
}