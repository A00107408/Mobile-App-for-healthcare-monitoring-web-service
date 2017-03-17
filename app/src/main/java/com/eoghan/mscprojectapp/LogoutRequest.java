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
 * Created by eoghan on 15/03/2017.
 */

public class LogoutRequest extends StringRequest {

    /*
     * Emulator localhost
     * http://10.0.2.2:9000/androidLogin
     */
    private static final String LOGOUT_REQUEST_URL = "http://10.0.2.2:9000/logout";
    private Map<String, String> params;

    public LogoutRequest(String username, Response.Listener<String> listener){

        super(Method.GET,LOGOUT_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("username", username);
        //params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
