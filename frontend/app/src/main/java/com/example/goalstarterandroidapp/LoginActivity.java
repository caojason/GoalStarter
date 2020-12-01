package com.example.goalstarterandroidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check if the user has already signed in to your app using Google, on this device or another device
        mGoogleSignInClient.silentSignIn().addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
            @Override
            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                handleSignInResult(task);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
            Log.d(TAG, "id token is: " + idToken);

            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://52.188.108.13:3000/login";

            Map<String, String>  params = new HashMap<String, String>();
            params.put("idToken", idToken);

            JSONObject body = new JSONObject(params);


            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    // asynchronous task to get the firebase cloud messaging token
                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.d(TAG, "Fetching FCM registration token failed", task.getException());
                            }

                            // Get new FCM registration token
                            String token = task.getResult();
                            Log.d(TAG, "Firebase token: " + token);
                            // get user id
                            String userid = null;
                            try {
                                userid = response.getString("userid");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // make request url
                            String url = "http://52.188.108.13:3000/home/firebase/" + userid;
                            // make request body
                            Map<String, String>  requestBody = new HashMap<String, String>();
                            requestBody.put("token", token);
                            // make request
                            StringRequest sendFCMToken = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d(TAG, "Successfully sent firebase token");
                                    Log.d(TAG, "URL used: " + url);
                                    Log.d(TAG, "body: " + requestBody.toString());
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(TAG, "Failed to sent firebase token, error: " + error.toString());
                                    Log.d(TAG, "url used: " + url);
                                    error.printStackTrace();
                                }
                            }){
                                @Override
                                public Map<String, String> getParams(){
                                    return requestBody;
                                }
                            };

                            queue.add(sendFCMToken);
                        }
                    });

                    String userInfo = response.toString();
                    Log.d(TAG, userInfo);

                    updateUI(account,userInfo);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error response
                    error.printStackTrace();
                }
            });

            queue.add(loginRequest);


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null, "");
        }
//        // firebase exeptions, commented out for now
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account, String userInfo) {
        if (account != null) {
            Intent intent = new Intent(LoginActivity.this, HostActivity.class);
            intent.putExtra("userInfo", userInfo);
            startActivity(intent);
            finish();
        }

    }
    
}