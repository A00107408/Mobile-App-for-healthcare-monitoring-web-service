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

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
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
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String LKL = "No Location Available";
    private String PhoneNumber = "No. Unavailable";


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
        final Button bListenStop = (Button) findViewById(R.id.bListenStop);

        String message = "Welcome to Pulse!";
        welcomeMessage.setText(message);

        String listen = "Click Listen to monitor " + App.userName;
        listenMessage.setText(listen);

        etICE.setText(App.ice);

        bListen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String ICE = etICE.getText().toString();
                App.ice = ICE;

                tvICE.setVisibility(View.GONE);
                etICE.setVisibility(View.GONE);
                bListen.setText("Listening...");

                String listening = "Monitoring " + App.userName + " on " + App.url
                        +"\nWill alert " +App.ice +".\nHave a nice day.";
                listenMessage.setText(listening);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("response: " +response);
                        //Emulator Phone Number : 6505551212
                        final SmsManager smsManager = SmsManager.getDefault();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            //Device newer than lollipop
                            //new library for phone number.
                        }else {
                            TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            final String mPhoneNumber = tMgr.getLine1Number(); //Wont work on older devices either
                            if (!"".equals(mPhoneNumber)) {
                                PhoneNumber = mPhoneNumber;
                            }
                        }
                        if (App.userName.equals("")) {
                            Toast.makeText(UserAreaActivity.this, "Session Expired. Please Log In.",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UserAreaActivity.this, LoginActivity.class);
                            UserAreaActivity.this.startActivity(intent);
                        }

                        if (response.equals(App.userName + "LOW")) {
                            System.out.println("sending cardio low SMS");

                            getCoords();

                            //Wait for any coords before texting.
                            //20 Seconds arrived at during testing.
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    smsManager.sendTextMessage(ICE, null, "" + App.userName + "'s BPM Below Threshold.\n" + LKL
                                            +"\nTry contact " + App.userName + ": " +PhoneNumber, null, null);
                                }
                            }, 25000);

                            STOP = true;

                            Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                            UserAreaActivity.this.startActivity(intent);
                        }

                        if (response.equals(App.userName + "HIGH")) {
                            System.out.println("sending cardio high SMS");

                            getCoords();

                            //Wait for any GPS before texting.
                            //20 Seconds arrived at during testing.
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    smsManager.sendTextMessage(ICE, null, "" + App.userName + "'s BPM Above Threshold.\n"
                                            + LKL +"\nTry contact " + App.userName + ": " +PhoneNumber, null, null);
                                }
                            }, 25000);

                            STOP = true;

                            Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                            UserAreaActivity.this.startActivity(intent);
                        }

                        if (response.equals(App.userName + "ASLEEP")) {
                            System.out.println("sending asleep SMS");

                            getCoords();

                            //Wait for any coords before texting.
                            //20 Seconds arrived at during testing.
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    smsManager.sendTextMessage(ICE, null, "" + App.userName + " fell alseep.\n"
                                             + LKL +"\n" +App.userName + ": " +PhoneNumber, null, null);
                                }
                            }, 25000);

                            Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                            UserAreaActivity.this.startActivity(intent);

                            STOP = true;
                        }

                        if (response.equals(App.userName + "AWAKE")) {
                            System.out.println("sending awake SMS");

                            getCoords();

                            //Wait for any coords before texting.
                            //20 Seconds arrived at during testing.
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    smsManager.sendTextMessage(ICE, null, "" + App.userName + " has awakened.\n"
                                             + LKL +"\n" +App.userName + ": " +PhoneNumber, null, null);
                                }
                            }, 25000);

                            Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                            UserAreaActivity.this.startActivity(intent);

                            STOP = true;
                        }

                        if ( response.equals(App.userName + "BRADY") ) {
                            System.out.println("sending dying SMS");

                            getCoords();

                            //Wait for any coords before texting.
                            //20 Seconds arrived at during testing.
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    smsManager.sendTextMessage(ICE, null, "" + App.userName + " has Bradycardia.\n" + LKL
                                            +"\n" + App.userName +": " +PhoneNumber, null, null);
                                }
                            }, 25000);

                            STOP = true;

                            Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                            UserAreaActivity.this.startActivity(intent);
                        }

                        if ( response.equals(App.userName + "TACHY") ) {
                            System.out.println("sending dying SMS");

                            getCoords();

                            //Wait for any coords before texting.
                            //20 Seconds arrived at during testing.
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    smsManager.sendTextMessage(ICE, null, "" + App.userName + " has Tachycardia.\n" + LKL
                                            +"\n" + App.userName +": " +PhoneNumber, null, null);
                                }
                            }, 25000);

                            STOP = true;

                            Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                            UserAreaActivity.this.startActivity(intent);
                        }

                        if (response.equals(App.userName + "DEAD")) {
                            System.out.println("sending deceased SMS");

                            getCoords();

                            //Wait for any coords before texting.
                            //20 Seconds arrived at during testing.
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    smsManager.sendTextMessage(ICE, null, "" + App.userName + " is Deceased.\n" + LKL
                                            +"\n" + App.userName +": " +PhoneNumber, null, null);
                                }
                            }, 25000);

                            STOP = true;

                            Intent intent = new Intent(UserAreaActivity.this, WarningActivity.class);
                            UserAreaActivity.this.startActivity(intent);
                        }
                    }
                };

                final ListenRequest listenRequest = new ListenRequest(responseListener);
                final RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(listenRequest);
                queue.getCache().remove(String.valueOf(listenRequest)); // Clear Volley Cache.

                //Poll server every 5 secs.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (STOP == false) {
                            bListen.performClick();
                        }
                    }
                }, 5000);
            }
        });

        bListenStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAreaActivity.this, LoginActivity.class);
                UserAreaActivity.this.startActivity(intent);
            }

        });
    }

    public void getCoords() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {

                LKL = "https://maps.google.com/maps?q=";

               double latitude = location.getLatitude();
               double longitude = location.getLongitude();

                String lat = String.valueOf(latitude);
                String lon = String.valueOf(longitude);

                LKL = LKL + lat + "," + lon;
                return;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //return;
            }

            @Override
            public void onProviderEnabled(String provider) {
                //return;
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Prompt user to turn on location services.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
            }
        } else {

            /**
             * @params
             * param 2 = zero time elapsed
             * param 3 = no movement.
             * In other words, just return coordinates without location update.
             * Will call onLocationChanged with result.
             * Get either network or GPS Location
             */
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates("gps", 0L, 0.0f, locationListener);

        }
        return;
    }

    /**
     * Handler for GPS permission. Has to be here.
     * Does nothing really.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(UserAreaActivity.this, "GPS is on?", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
