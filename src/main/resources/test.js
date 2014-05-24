console.log('Loading a web page');

var url = 'http://www.flashscore.com/tennis';

var page = require('webpage').create();
page.onLoadFinished = function (status) {
	var homeTeams = page.evaluate(function() {
	    console.log("indede");
	    var players = []

        $("table.tennis td.team-home").each(function( index ) {
            players.push( $( this ).text() )
        });
        return players;
    });
    console.log(homeTeams.length);
    console.log(homeTeams);

    if (homeTeams.length > 1) {
        phantom.exit();
	}

    console.log("loaded");
};


page.open(url);