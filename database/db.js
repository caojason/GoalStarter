
const MongoClient = require("mongodb").MongoClient;
const url = "placeholder";

var db; 

module.exports = {
    /*Initialize the database of the application. Creates two collections for user and goal info */
    db_init : function () {
        MongoClient.connect(url, {useNewUrlParser: true}, function(err, client) {
            if(err) throw err; 
            console.log("GoalStarter Database created\n");
            //create a database
            db = client.db("Goal Database"); 
            //create a collectiion for storing goals
            db.createCollection("goals", function(err, res) {
                if(err) throw err; 
                console.log("Goal Collection Created\n"); 
            });
                //create a collection for storing users. 
            db.createCollection("users", function(err, res) {
                if(err) throw err; 
                console.log("User Collection Created\n"); 
            });
        });  
    }, 

    /*Item is a JSON object, see https://www.w3schools.com/nodejs/nodejs_mongodb_update.asp for query and update formats*/
    db_user_search : function (query) {
    var result = db.collection("users").find(query); 
    return result; 
    },

    db_goal_search: function (query) {
        var result = db.collection("goals").find(query);
        return result; 
    },

    db_goal_insert: function (item) {
        db.collection("goals").insertOne(item); 
    },

    db_user_insert: function (item) {
        db.collection("users").insertOne(item); 
    },


    db_user_delete: function (query) { 
        db.collection("users").deleteOne(query); 
    },

    db_goal_delete: function (query, update) {
        db.collection("goals").deleteOne(query); 
    },

    //use $push to add a goal update or a friend to a friends list. $set to change existing fields. 
    db_user_update: function (query, update) {
        db.collection("users").update(query, update); 
    },

    db_goals_update: function (query, update) {
        db.collection("goals").update(query, update); 
    },
}