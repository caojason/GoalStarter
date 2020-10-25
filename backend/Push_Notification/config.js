var admin = require("firebase-admin");

var serviceAccount = require("./goalstarter-firebase-adminsdk-t0gsa-c7145389e3.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://goalstarter.firebaseio.com"
  });

module.exports.admin = admin