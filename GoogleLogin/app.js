const express=require('express')
const morgan=require('morgan')
const bodyParser=require('body-parser')
const cors=require('cors')
const passport = require('passport')
const cookieSession = require('cookie-session')

require('./passportsetup')
const app=express()

const port = process.env.PORT || 3000

let User=require("./User.json")



//Middlewares
app.use(cors())
app.use(bodyParser.urlencoded({extended:false}))
app.use(bodyParser.json())

app.use(cookieSession({
    name: 'tuto-session',
    keys: ['key1', 'key2']
  }))

  function createUser(id,name){
    var newUser={
        "name":name,
        "ID":id,
        "postsID":[],
        "likedPosts":[]
    }
    
    User.push(newUser)
}
const islogin=(req,res,next)=>{
    if(req.user){
        next()
      createUser(req.user.emails[0].value,req.user.displayName)
      console.log(User);
    }else{
        res.sendStatus(401)
    }
}

app.use(passport.initialize())
app.use(passport.session())

//Routes

app.get('/',(req,res)=>{res.send("You are not logged in !")})
app.get('/failed',(req,res)=>{res.send(`You failed to log in!`)})
app.get('/success',islogin,(req,res)=>{res.send(`Welcome, Mr.${req.user.displayName}}`)})
app.get('/google',passport.authenticate('google', { scope: ['profile','email'] }));

app.get('/google/callback', 
  passport.authenticate('google', { failureRedirect: '/failed' }),
  function(req, res) {
    // Successful authentication, redirect home.
    res.redirect('/success');
  });

app.get('/logout',(req,res)=>{
    req.session=null
    req.logOut()
    res.redirect('/')
})
app.listen(port);
console.log(`server listening at ${port}`)
