var db = require("../database/db");
var tags= ["weightloss", "competitive sports", "running", "weight training", "medical school", "employment", "undergraduate", "masters/PhD", "diet", "LoL", "Valorant", "Overwatch"]; 



module.exports = {
    tags, 

        computeAffinity: function(userid, tag) {

    },

    getAllGoals: function(tag) {

    },
    //create user feed, called everytime the user logs in. 
    generateFeed : function(userid, date) {
        
        var feed = []; 
        var tag_scores = []; 
        var tag_sorted = [];
        
        for(var i = 0; i < tags.length; i++) {
            var score = computeAffinity(userid, tags[i]); 
            tag_scores.push(score); 
        }

        for(var i = 0; i < tag_scores.length; i++) {
            var index = 0; 
            var max = tag_scores[0];
            for(var j = 0; j < tag_scores.length; j++) {
                index = (tag_scores[j] > max) ? j : max; 
            }
            tag_sorted.push(tags[index]);
            tag_scores[index] = -1; 
        }

        while(feed.length < 10) {
            
        }


    }
}