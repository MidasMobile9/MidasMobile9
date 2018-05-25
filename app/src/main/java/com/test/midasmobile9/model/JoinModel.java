package com.test.midasmobile9.model;

import android.util.Log;

import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.network.OkHttpAPICall;
import com.test.midasmobile9.network.OkHttpInitSingletonManager;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

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
    public static boolean getJoinResult(String email, String password, String nickname, File file) {
        String TAG = "JoinModel";
        String functionName = "getJoinResult()";
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        String message = null;
        boolean result = false;

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
                return result;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if ( jsonFromServer.has("result") ) {
                    result = jsonFromServer.getBoolean("result");
                }

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");

                    Log.e(TAG, message);
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
        return result;
    }


    /**
     * 이메일 체크하는 백그라운드 메소드
     * @return 이메일체크 결과
     * @param strEmail 체크할 이메일
     */
    public static boolean getEmailCheckResult(String strEmail) {
        String TAG = "JoinModel";
        String functionName = "getEmailCheckResult()";
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        String message = null;
        boolean result = false;
        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstant.checkEmail+strEmail);

            if ( response == null ) {
                Log.e(TAG, "Response of "+functionName+" is null.");
                return result;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if ( jsonFromServer.has("result") ) {
                    result = jsonFromServer.getBoolean("result");
                }

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");
                    Log.e(TAG, message);
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
        return result;
    }


    /**
     * 닉네임 체크하는 백그라운드 메소드
     * @return 닉네임체크 결과
     * @param strNickName 체크할 닉네임
     */
    public static boolean getNicknameCheckResult(String strNickName) {
        String TAG = "JoinModel";
        String functionName = "getNicknameCheckResult()";
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        String message = null;
        boolean result = false;
        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstant.checkNickname+strNickName);

            if ( response == null ) {
                Log.e(TAG, "Response of "+functionName+" is null.");
                return result;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if ( jsonFromServer.has("result") ) {
                    result = jsonFromServer.getBoolean("result");
                }

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");
                    Log.e(TAG, message);
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
        return result;
    }

}
