package com.eoghan.mscprojectapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


/**
 * Created by eoghan on 12/03/2017.
 */

public class ListenRequest extends StringRequest {

    /*
    * Emulator localhost
    * http://10.0.2.2:9000/androidLogin
    */
    private static final String LISTEN_REQUEST_URL = "http://"+ App.url+":9000/androidListen" +App.userName;

    public ListenRequest(Response.Listener<String> listener) {

        super(Method.GET, LISTEN_REQUEST_URL, listener, null);
    }
}
