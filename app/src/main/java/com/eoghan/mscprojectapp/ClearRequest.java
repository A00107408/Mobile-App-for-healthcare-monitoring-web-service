package com.eoghan.mscprojectapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by eoghan on 19/04/2017.
 */

public class ClearRequest extends StringRequest {

   /*
   * Emulator localhost
   * http://10.0.2.2:9000/androidLogin
   */
    private static final String CLEAR_REQUEST_URL = "http://"+ App.url+":9000/androidClear" +App.userName;

    public ClearRequest(Response.Listener<String> listener) {

        super(Method.GET, CLEAR_REQUEST_URL, listener, null);
    }
}
