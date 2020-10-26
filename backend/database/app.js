const express=require('express')

const {OAuth2Client} = require('google-auth-library');

var name=""
var userid=""
var email=""
ClientID="270283078640-b1n995h94o3jm1oevmqo4slthg6orhbu.apps.googleusercontent.com"
AudienceClientID="534895538157-17o22r3tq7fn6g6pnhoe0rpl4qskq5ng.apps.googleusercontent.com"
const client = new OAuth2Client(ClientID);

const app=express()
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
   return newUser
  
}

//Middlewares


  async function verify(token) {
  const ticket = await client.verifyIdToken({
      idToken: token,
      audience: AudienceClientID,  // Specify the CLIENT_ID of the app that accesses the backend
      // Or, if multiple clients access the backend:
      //[CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3]
  })
  const payload = ticket.getPayload();

   userid = ticket.getUserId()
   email=payload['email']
   name=payload['name']
 console.log(userid)
 console.log(email)
 console.log(name)
  // If request specified a G Suite domain:
  // const domain = payload['hd'];
}

app.get("/", function (req, res) {
  res.send("Welcome");
});

// app.get("/login", function (req, res) {
//   res.send("Welcome login");
// });






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


module.exports= {verify,createUser,name,userid,email,ClientID}
