package com.test.midasmobile9.model;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.network.OkHttpAPICall;
import com.test.midasmobile9.network.OkHttpInitSingletonManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


/***
 *가입시 필요한 백그라운드 메소드
 */
public class JoinModel {
    // JoinActivity의 Model static 함수 정의

    /**
     * 가입하는 백그라운드 메소드
     * @param email
     * @param password
     * @param nickname
     * @param file
     * @return 가입하는 결과
     */
    public static Map<String, Object> getJoinResult(String email, String password, String nickname, File file) {
        String TAG = "JoinModel";
        String functionName = "getJoinResult()";
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        String message = null;
        boolean result = false;
        Map<String, Object> map = null;
        RequestBody requestBody;

        if(file!=null){
            MediaType pngType = MediaType.parse("image/png");
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", email)
                    .addFormDataPart("password", password)
                    .addFormDataPart("nickname", nickname)
                    .addFormDataPart("file", file.getName(),RequestBody.create(pngType, file))
                    .build();
        }else{
            requestBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .add("nickname", nickname)
                    .build();
        }

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstant.join,requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of "+functionName+" is null.");
                return map;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                // 통신결과 체크
                if ( jsonFromServer.has("result") ) {
                    map = new HashMap<String, Object>();
                    map.put("result", jsonFromServer.getBoolean("result"));
                }

                // 결과 메시지
                if ( jsonFromServer.has("message") ) {
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
            if ( response != null ) {
                response.close();
            }
        }
        return map;
    }


    /**
     * 이메일 체크하는 백그라운드 메소드
     * @return 이메일체크 결과
     * @param strEmail 체크할 이메일
     */
    public static Map<String, Object> getEmailCheckResult(String strEmail) {
        String TAG = "JoinModel";
        String functionName = "getEmailCheckResult()";
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        String message = null;
        boolean result = false;
        Map<String, Object> map = null;
        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstant.checkEmail+strEmail);

            if ( response == null ) {
                Log.e(TAG, "Response of "+functionName+" is null.");
                return map;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                // 통신결과 체크
                if ( jsonFromServer.has("result") ) {
                    map = new HashMap<String, Object>();
                    map.put("result", jsonFromServer.getBoolean("result"));
                }

                // 결과 메시지
                if ( jsonFromServer.has("message") ) {
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
            if ( response != null ) {
                response.close();
            }
        }
        return map;
    }


    /**
     * 닉네임 체크하는 백그라운드 메소드
     * @return 닉네임체크 결과
     * @param strNickName 체크할 닉네임
     */
    public static Map<String, Object> getNicknameCheckResult(String strNickName) {
        String TAG = "JoinModel";
        String functionName = "getNicknameCheckResult()";
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        String message = null;
        boolean result = false;
        Map<String, Object> map = null;
        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstant.checkNickname+strNickName);

            if ( response == null ) {
                Log.e(TAG, "Response of "+functionName+" is null.");
                return map;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                // 통신결과 체크
                if ( jsonFromServer.has("result") ) {
                    map = new HashMap<String, Object>();
                    map.put("result", jsonFromServer.getBoolean("result"));
                }

                // 결과 메시지
                if ( jsonFromServer.has("message") ) {
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
            if ( response != null ) {
                response.close();
            }
        }
        return map;
    }

}
