# GoalStarter
Android app for tracking goals (work in progress)

Goal: 

{
    id, title, author, date, content, milestones[], schedule[], tag, comments[], likes

}

date "March 21, 2012" string can be parsed
title, author, date, content, milestones, schedule, comments are strings. 
likes number

User: 

{
    id, username,email, friendslist[], posts[], comments[], likes[]; 

    arrays contains ids not objects. 
}

URLs: 

/ : login page
/home: homescreen with goals feed 
/home/postgoal: create a new goal
/home/postgoal: 

/home/comment/:userid
in body:
- id (id for goal)
- author (author of comment)
- comment (actual comment)

/home/like/:userid
in body
- id (id for goal)

/home/firebase/:userid
in body
- token (token of firebase)
