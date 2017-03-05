package com.eoghan.mscprojectapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eoghan on 22/01/2017.
 */

public class RegisterRequest extends StringRequest {

    /*
     * Android localhost
     * http://10.0.2.2:9000/users
     */
    private static final String REGISTER_REQUEST_URL = "http://10.0.2.2:9000/users";
    private Map<String, String> params;

    public RegisterRequest(String name, int age, String username, String password, Response.Listener<String> listener){

       super(Method.POST,REGISTER_REQUEST_URL, listener, null);

       params = new HashMap<>();
       params.put("name", name);
       params.put("age", age + "");
       params.put("username", username);
       params.put("password", password);
       //gender, height. I.C.E. contact phone number.
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
