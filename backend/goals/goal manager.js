var db = require("../database/db");
var express = require("express"); 
var feed = require("./feed manager");
const { verify,userid,name,token} = require("crypto");


require('../GoogleLogin/app')
var app = express();
app.use(express.json()); 

// send res a list of JSON objects/user feed
app.get('/home/:userid', (req, res) => {
    var userid = req.params.userid; 
    var list = feed.getFeed(userid); 
    for(var i = 0; i < list.length; i++) {
        res.send(list[i]); 
    }
});
app.get('./login',(req,res)=>{
    token =req.header['idToken']
    try {
        verify();
        res.send(`successfully inserted user with id:${userid} and name:${name} in database`)
    } catch (error) {
        res.send(error);
    }
     
   })
// post a new goal. req is formatted like a goal. 
app.post('/home/create_goal', (req, res) => {
    var id = req.body.id; 
    var title = req.body.title; 
    var author = req.body.author; 
    var date = req.body.date; 
    var content = req.body.content; 
    var milestones = req.body.milestones; 
    var schedule = req.body.schedule; 
    var tag = req.body.tag; 
    var comments = [];
    var likes = 0; 

    var goal = {
        id: id, 
        title: title, 
        author: author, 
        date: date, 
        content: content, 
        milestones: milestones, 
        schedule: schedule,
        tag: tag, 
        comments: comments, 
        likes: likes
    }
    db.db_goal_insert(goal); 
    res.send("goal inserted"); 
});

//post an update to a goal. req contains goalid and comment
app.put('/home/comment', (req, res) => {
    var comment = req.params.comment;
    var id = req.params.id; 
    var now = Date(Date.now()); 
    var date = `${now.getMonth()} ${now.getDay()}, ${now.getFullYear()}`;
    db.db_goals_update({id: id}, {$push: {
        comments: comment
    },
        $set: {
            date: date
        }
    });
});

//add a like to a goal, req contains goalid
app.put('/home/like', (req, res) => {
    var id = req.params.id; 
    var now = Date(Date.now()); 
    var date = `${now.getMonth()} ${now.getDay()}, ${now.getFullYear()}`;
    db.db_goals_update({id: id}, {$inc: {
        likes : 1
    },
        $set: {
            date : date 
        }
    }); 
});

//delete a goal
app.delete('/home/:goalid', (req, res) => {
    var id = req.params.id; 
    db.db_goal_delete({id : id}); 
});