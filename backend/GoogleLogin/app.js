const express=require('express')
const morgan=require('morgan')
const bodyParser=require('body-parser')
const cors=require('cors')
const passport = require('passport')
const cookieSession = require('cookie-session')
const {OAuth2Client} = require('google-auth-library');
ClientID="534895538157-17o22r3tq7fn6g6pnhoe0rpl4qskq5ng.apps.googleusercontent.com"
var name=""
var userid=""
var email=""
var token=""

const client = new OAuth2Client(ClientID);
var router=express.Router();
require('./passportsetup')
const app=express()


const port = process.env.PORT || 3000

let User=require("./User.json")
const { assert } = require('joi')
//const db = require('../database/db')



function createUser(uid,name,email){
  var newUser={
      id:uid,
      username:name,
      email:email,
      friendslist:[],
      posts:[],
      comments:[],
      likes:[]
    
  }
  //  if(!db.collection("users").find(uid)){
  //    db.collection("users").insert(newUser);
  //  }
  User.push(newUser)
}

app.use(cors())
app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())
//Middlewares
app.use(passport.initialize())
app.use(passport.session())
app.use(cookieSession({
    name: 'tuto-session',
    keys: ['key1', 'key2']
  }))


  async function verify() {
  const ticket = await client.verifyIdToken({
      idToken: token,
      audience: ClientID,  // Specify the CLIENT_ID of the app that accesses the backend
      // Or, if multiple clients access the backend:
      //[CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3]
  })
  const payload = ticket.getPayload();
   userid = payload['sub'];
   email=payload['email']
   name=payload['name']
  createUser(userid,name,email)
  
 
  // If request specified a G Suite domain:
  // const domain = payload['hd'];
}




 app.get('/login',(req,res)=>{
   token =req.body.idToken
  
       verify().catch((error)=>{
         if(error){
           res.send({
            error:error.message
           })
         }
         else{
       res.send({
        method:'Get',
        idToken:token,
        userid:userid,
        name:name,
        email:email
       
       })
      }
       })
      
       
   
  })



//cookieSession.
//Routes

// app.get('/',(req,res)=>{res.send("You are not logged in !")})
// app.get('/failed',(req,res)=>{res.send(`You failed to log in!`)})
// app.get('/success',islogin,(req,res)=>{res.send(`Welcome, Mr.${req.user.displayName}`)})
// app.get('/google',passport.authenticate('google', { scope: ['profile','email'] }));

// app.get('/google/callback', 
//   passport.authenticate('google', { failureRedirect: '/failed' }),
//   function(req, res) {
//     // Successful authentication, redirect home.
//     res.redirect('/success');
//   });

// app.get('/logout',(req,res)=>{
//     req.session=null
//     req.logOut()
//     res.redirect('/')
// })
app.listen(port,function(){
  console.log(`server listening at ${port}`)
});


module.export= {verify}
