/* Student: A00107408
 * Date: 2016-2017
 * Project: Msc Software Engineering Project.
 * College: Athlone Institute of Technology.
 *
 * Credits:
 * Login Based On: https://github.com/tonikami/NEWLoginRegister.git (05-01-2017)
 * Send SMS Based On: https://github.com/embeddedemily/emergency-text.git (05-01-2017)
 */

package com.eoghan.mscprojectapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class UserAreaActivity extends AppCompatActivity {

    boolean STOP = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etICE = (EditText) findViewById(R.id.etICE);
       // final EditText etUserName = (EditText) findViewById(R.id.etUserName);

        final TextView tvICE = (TextView) findViewById(R.id.tvICE);
        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);
        final TextView listenMessage = (TextView) findViewById(R.id.tvListen);

        final Button bListen = (Button) findViewById(R.id.bListen);

        String message = "Welcome to Pulse!";
        welcomeMessage.setText(message);

        String listen = "Click the Listen button to monitor account.";
        listenMessage.setText(listen);

        bListen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            final String ICE = etICE.getText().toString();

            tvICE.setVisibility(View.GONE);
            etICE.setVisibility(View.GONE);

            String listening = "Listening to Pulse Service... ";
            listenMessage.setText(listening);

            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                if(User.name.equals("")){
                    Toast.makeText(UserAreaActivity.this, "Session Expired. Please Log In.",
                    Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(UserAreaActivity.this, LoginActivity.class);
                    UserAreaActivity.this.startActivity(intent);
                }

                if (response.equals(User.name +"LOW")) {
                    System.out.println("sending cardio low SMS");

                    //Emulator Phone Number : 6505551212
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(ICE, null, ""+User.name +"'s Heart Rate Critically Low.", null, null);

                    STOP = true;

                    Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                    UserAreaActivity.this.startActivity(intent);
                }

                if (response.equals(User.name+"HIGH")) {
                    System.out.println("sending cardio high SMS");

                    //Emulator Phone Number : 6505551212
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(ICE, null, ""+User.name+"'s Heart Rate Critically High.", null, null);

                    STOP = true;

                    Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                    UserAreaActivity.this.startActivity(intent);
                }

                if (response.equals("AWAKE")) {
                    System.out.println("sending awake SMS");

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(ICE, null, "User Is Awake.", null, null);

                    Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                    UserAreaActivity.this.startActivity(intent);

                    STOP = true;
                }
                }
            };

            ListenRequest listenRequest = new ListenRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
            queue.add(listenRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(STOP == false) {
                        bListen.performClick();
                    }
                }
            }, 5000);
            }
        });
    }
}
