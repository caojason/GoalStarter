const express=require('express')
const morgan=require('morgan')
const bodyParser=require('body-parser')
const cors=require('cors')
const passport = require('passport')
const cookieSession = require('cookie-session')
const {OAuth2Client} = require('google-auth-library');
ClientID="270283078640-b1n995h94o3jm1oevmqo4slthg6orhbu.apps.googleusercontent.com"
const client = new OAuth2Client(ClientID);
const token=""
require('./passportsetup')
const app=express()
const db=require('../database/db')

const port = process.env.PORT || 3000

let User=require("./User.json")


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
 if(!db.db_goal_search({id:uid})){
   db.db_user_insert(newUser)
 }
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
  });
  const payload = ticket.getPayload();
  const aud=payload['aud']
  const iss=payload['iss']
  const exp=payload['exp']
  if(aud==ClientID && (iss=='https://accounts.google.com'|| iss=='accounts.google.com') && exp==0){
    const userid = payload['sub'];
    const email=payload['email']
    const name=payload['name']
  createUser(userid,name,email)
  }
 else {
   return console.error
 }
  // If request specified a G Suite domain:
  // const domain = payload['hd'];
}
verify().catch(console.error);

app.get('./login',verify,(req,res)=>{
 token =req.header['idToken']
  
  res.send(`Welcome Mr.${User[0].name}`)
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
app.listen(port);
console.log(`server listening at ${port}`)

module.export= {verify}
