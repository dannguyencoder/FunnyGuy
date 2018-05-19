var http = require('http');
var fs = require('fs');

http.createServer(function(req, res) {
	res.writeHead(200, {"Content-Type":"application/json"});

	var obj = [
	{
		id: 0,
		funny_text: "How are you doing ?",
		category: "Polite"
	},
	{
		id: 1,
		funny_text: "Hi there",
		category: "A little"
	},
	{
		id: 2,
		funny_text: "What's up, bitch ?",
		category: "Bad boy"
	},
	{
		id: 3,
		funny_text: "you are fucking hot",
		category: "Super bad boy"
	},
	{
		id: 0,
		funny_text: "How are you fucking doing ?",
		category: "Polite"
	},
	{
		id: 1,
		funny_text: "How is your life ?",
		category: "A little"
	},
	{
		id: 2,
		funny_text: "What's up, bitch ?",
		category: "Bad boy"
	},
	{
		id: 3,
		funny_text: "you are fucking hot",
		category: "Super bad boy"
	}

	];

	res.end(JSON.stringify(obj));
}).listen(7777);