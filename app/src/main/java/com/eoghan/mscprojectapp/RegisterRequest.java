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
     * http://10.0.2.2:9000/androidUsers
     */
    private static final String REGISTER_REQUEST_URL = "http://" + App.url +":9000/androidUsers";
    private Map<String, String> params;

    public RegisterRequest(String name, int age, String username, String password, Response.Listener<String> listener){

       super(Method.POST,REGISTER_REQUEST_URL, listener, null);

       params = new HashMap<>();
       params.put("name", name);
       params.put("age", age + "");
       params.put("username", username);
       params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
