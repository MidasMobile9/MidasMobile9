package com.test.midasmobile9.model;

import android.util.Log;

import com.google.gson.Gson;
import com.test.midasmobile9.network.NetworkDefineConstantOSH;
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

public class UserOrderOptionModel {
    public static final String TAG = "UserOrderOptionModelTag";

    public static Map<String, Object> postOrderMenu(int user_no, int menu_no, int size, int hotcold, int count, int price) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        Gson gson = new Gson();

        Map<String, Object> map = null;

        RequestBody requestBody = new FormBody.Builder()
                .add("user_no", String.valueOf(user_no))
                .add("menu_no", String.valueOf(menu_no))
                .add("size", String.valueOf(size))
                .add("hotcold", String.valueOf(hotcold))
                .add("count", String.valueOf(count))
                .add("price", String.valueOf(price))
                .build();

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstantOSH.SERVER_URL_GET_ORDER, requestBody);

            if (response == null) {
                Log.e(TAG, "Response of loginUser() is null.");

                return null;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                // 통신결과 체크
                if (jsonFromServer.has("result")) {
                    map = new HashMap<String, Object>();
                    map.put("result", jsonFromServer.getBoolean("result"));
                }

                // 결과 메시지
                if (jsonFromServer.has("message")) {
                    map.put("message", jsonFromServer.getString("message"));
                }


            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return map;
    }
}
