var db = require("../database/db");
var express = require("express"); 
var feed = require("./feed manager")

var app = express();
app.use(express.json()); 

db.db_init(); 

/*Hardcoded List of 5 goals */
const list = [
    {id: "test1",
    title: "I want to pass CPEN 331", 
    author: "Eric", 
    date: "October 22, 2020",
    content: "The class is extremely difficult. I will try my best to pass the course and not fail or get depression", 
    milestones: [],
    schedule: [], 
    tag: "undergraduate",
    comments: ["Good Job", "I hope I pass too"], 
    likes: 12
    }, 
    {id: "test2", 
    title: "I want to get Diamond in League", 
    author: "Jason", 
    date: "October 21, 2020",
    content: "I am a hardstuck Wood V Jax main. I want to climb the ladder with Annie and Zed", 
    milestones: [],
    schedule: [], 
    tag: "LoL",
    comments: ["Good Luck", "You suck"], 
    likes: 0
    }, 
    {id: "test3",
    title: "I want to become the Prime Minister of Canada", 
    author: "Steven", 
    date: "July 1, 2019",
    content: "I believe I should be the Prime Minister of Canada, Trudeau is a terrible and corrupt leader and I will do better", 
    milestones: [],
    schedule: [], 
    tag: "employment",
    comments: ["I voted for you", "What qualifies you for this position?"], 
    likes: 100
    }
]

const months = ["January", "February", "March", "April", "May", "June",
"July", "August", "September", "October", "November", "December"
];

//userid is the id of the author.
app.get('/home/:userid', (req, res) => {
    var userid = req.params.userid; 
    //var list = feed.getFeed(userid);
    res.send(list); 
});

/*require title, author, content, milestone, schedule, tag as part of the JSON http request. userid param */
app.post('/home/create_goal/:userid', (req, res) => {
    //generate date string 
    var now = new Date(Date.now()); 
    var date_string = `${months[now.getMonth()]} ${now.getDate()}, ${now.getFullYear()}`; 
    console.log(req); 
    //temporary postid is a concat of userid, date posted, and title of post. 
    var id = `${req.params.userid}${req.body.title}${date_string}`;

    //fill in goal fields.
    var title = req.body.title; 
    var author = req.body.author; 
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
        date: date_string, 
        content: content, 
        milestones: milestones, 
        schedule: schedule,
        tag: tag, 
        comments: comments, 
        likes: likes
    }
    db.db_goal_insert(goal); 
    db.db_user_update({id: userid}, {
        $push: {
            posts: id
        }
    });

    res.send("goal inserted"); 
});

//require comment, goal id, and author name as part of the http request. userid in param
app.put('/home/comment/:userid', (req, res) => {

    var comment = `${req.body.author} : ${req.body.comment}`;
    var id = req.body.id; 
    var userid = req.params.userid; 
    var now = new Date(Date.now()); 
    var date = `${now.getMonth()} ${now.getDay()}, ${now.getFullYear()}`;

    db.db_goals_update({id: id}, {$push: {
        comments: comment
    },
        $set: {
            date: date
        }
    });

    db.db_user_update({id: userid}, {
        $push: {
            comments: id
        }
    }); 
});

//require goal id in body and userid in params
app.put('/home/like/:userid', (req, res) => {
    var id = req.body.id; 
    var userid = req.params.userid; 
    var now = new Date(Date.now()); 
    var date = `${now.getMonth()} ${now.getDay()}, ${now.getFullYear()}`;
    db.db_goals_update({id: id}, {$inc: {
        likes : 1
    },
        $set: {
            date : date 
        }
    }); 

    db.db_user_update({id: userid}, {
        $push: {
            likes: id 
        }
    });
});

//delete a goal. mainly for debugging purposes
app.delete('/home/:goalid', (req, res) => {
    var id = req.params.id; 
    db.db_goal_delete({id : id}); 
});

const port = process.env.PORT || 3000; 
app.listen(port, () => console.log(`Listening on port ${port}`));