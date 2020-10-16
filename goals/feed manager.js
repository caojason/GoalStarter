var db = require("../database/db");
var tags= ["weightloss", "competitive sports", "running", "weight training", "medical school", "employment", "undergraduate", "masters/PhD", "diet", "LoL", "Valorant", "Overwatch"]; 



module.exports = {
    tags, 

    computeAffinity: function(userid, tag) {

    },

    timedOut: function(goal) {

    },

    //create user feed, called everytime the user logs in. returns an array of goal ids for the user. 
    generateFeed : function(userid, date) {
        
        var feed = []; 
        var tag_scores = []; 
        var tag_sorted = [];
        var limit = 10; 

        //first add all friends posts

        //then compute for more posts.
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

        var tag_index = 0; 
        while(feed.length < limit) {

            var goals = db.db_goal_search({tag: tag_sorted[tag_index]}); 

            while(goals.length > 0 && feed.length < limit) {
                
                //filter by time last updated
                if(timedOut(goals[0])) {
                    goals.shift();
                }
                else {
                    feed.push(goals[0].id); 
                    goals.shift(); 
                }
            }
            tag_index++; 
        }


    }
}