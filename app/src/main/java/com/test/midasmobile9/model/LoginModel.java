package com.test.midasmobile9.model;

import android.util.Log;

import com.google.gson.Gson;
import com.test.midasmobile9.data.User;
import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.network.OkHttpAPICall;
import com.test.midasmobile9.network.OkHttpInitSingletonManager;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginModel {
    private static final String TAG = "LoginModel";

    // LoginActivity의 Model static 함수 정의
    public static Map<String, Object> loginUser(String email, String password) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        Gson gson = new Gson();

        Map<String, Object> map = null;

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstant.SERVER_URL_LOGIN_USER, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of loginUser() is null.");

                return null;
            } else {
                map = new HashMap<String, Object>();

                JSONObject jsonFromServer = new JSONObject(response.body().string());

                // 통신결과 체크
                if ( jsonFromServer.has("result") ) {
                    map.put("result", jsonFromServer.getBoolean("result"));
                }

                // 결과 메시지
                if ( jsonFromServer.has("message") ) {
                    map.put("message", jsonFromServer.getString("message"));
                }

                // 유저 로그인 정보
                if ( jsonFromServer.has("data") ) {
                    JSONObject jsonObjectUser = jsonFromServer.getJSONObject("data");
                    User user = gson.fromJson(jsonObjectUser.toString(), User.class);
                    user.setEmail(email);

                    map.put("data", user);
                }

                // 쿠키
                String cookie = response.header("set-cookie");
                map.put("cookie", cookie);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( response != null ) {
                response.close();
            }
        }

        return map;
    }
}
