const express=require("express");

const {OAuth2Client} = require("google-auth-library");

var ClientID="270283078640-b1n995h94o3jm1oevmqo4slthg6orhbu.apps.googleusercontent.com";
var AudienceClientID="534895538157-17o22r3tq7fn6g6pnhoe0rpl4qskq5ng.apps.googleusercontent.com";
const googleClient = new OAuth2Client(ClientID);
//Middlewares

module.exports= {AudienceClientID,googleClient}; 
