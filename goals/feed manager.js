var db = require("../database/db");
var tags= ["weightloss", "competitive sports", "running", "weight training", "medical school", "employment", "undergraduate", "masters/PhD", "diet", "LoL", "Valorant", "Overwatch"]; 

function updatescore( score,  tag, weight) {

    for(var i = 0; i < tags.length; i++) {
        if(tags[i] === tag) {
            return score + weight; 
        }
    }

    return 0; 
}

function computeAffinity(userid, tag) {
    var user = db.db_user_search({id : userid});
    var likes = user.likes; 
    var comments = user.comments; 
    var posts = user.posts;  
    var score = 0;

    //factor in likes
    for(var i = 0; i < likes.length; i++) {
        var goal_tag = db.db_goal_search({id : likes[i]}),tag; 
        if(goal_tag === tag) {score = updatescore(score, goal_tag, 1);} 
    }
    //factor in comments
    for(var i = 0; i < comments.length; i++) {
        var goal_tag = db.db_goal_search({id : comments[i]}).tag;
        if(goal_tag === tag) {score = updatescore(score, goal_tag, 2);} 

    }
    //factor in posts. 
    for(var i = 0; i < posts.length; i++) {
        var goal_tag = db.db_goal_search({id: posts[i]}).tag; 
        if(goal_tag === tag) {score = updatescore(score, goal_tag, 5);}  
    }

    return score; 
}

function timedOut(goal) {
    var difference = 60 * 86400000; //allow for 60 days since last update. 
    var date = new Date(Date.now()); 
    var result = db.db_goal_search({id : goal}); 
    var goal_date = Date.parse(result.date);
    if(date.now() - difference >= goal_date) return true; 
    else return false; 

}

function generateFeed(userid) {
        
    var feed = []; 
    var tag_scores = []; 
    var tag_sorted = [];
    var limit = 10; 

    //first add the most recent goal of each friend
    var friends = db.db_user_search({id : userid}).friendslist; 
    for(var i = 0; i < friends.length && feed.length < limit; i++) {
        var friend = db.db_user_search({id: friends[i]}); 
        var post = friend.posts[posts.length - 1]; 
        if(!timedOut(post)) {
            feed.push(post); 
        }
    }

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
            if(timedOut(goals[0]).id) {
                goals.shift();
            }
            else {
                feed.push(goals[0].id); 
                goals.shift(); 
            }
        }
        tag_index++; 
    }

    return feed; 
}

module.exports =  {
    tags, 
    getFeed: function (userid) {
        var feed = [];
        var user_feed = [];
        feed = generateFeed(userid); 

        for(var i = 0; i < feed.length; i++) {
            var elem = db.db_goal_search({id : feed[i]});
            user_feed.push(elem); 
        }

        return user_feed; 
    }
}