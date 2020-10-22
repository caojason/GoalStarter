const passport=require('passport')
const GoogleStrategy = require('passport-google-oauth20').Strategy;


passport.serializeUser(function(user, done) {
    done(null, user);
  });
  
  passport.deserializeUser(function(user, done) {
    
      done(null, user);
   
  });




passport.use(new GoogleStrategy({
    clientID: "270283078640-b1n995h94o3jm1oevmqo4slthg6orhbu.apps.googleusercontent.com",
    clientSecret: "VFmbiCja0HIhAQBgnPyvVgYe",
    callbackURL: "http://localhost:3000/google/callback"
  },
  function(accessToken, refreshToken, profile, done) {
      //use user profile to check if user exits in database
    
      return done(null, profile);
    
  }
));