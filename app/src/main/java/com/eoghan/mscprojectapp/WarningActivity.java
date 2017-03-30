package com.eoghan.mscprojectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

/**
 * Created by eoghan on 12/03/2017.
 */

public class WarningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        final Button bReset = (Button) findViewById(R.id.bReset);
        final Button bLogout = (Button) findViewById(R.id.bWarnLogout);
        final TextView warningMessage = (TextView) findViewById(R.id.tvWarning);

        String message = "Warning Received from Pulse Services. SMS Sent to I.C.E.";
        warningMessage.setText(message);

        bReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                Intent intent = new Intent(WarningActivity.this, UserAreaActivity.class);
                WarningActivity.this.startActivity(intent);
                }
            };
            ListenRequest listenRequest = new ListenRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(WarningActivity.this);
            queue.add(listenRequest);
            }
        });
        bLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(WarningActivity.this,
                        LoginActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
