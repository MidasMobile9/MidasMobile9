package com.test.midasmobile9.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

public class ProfileManagerModel {
    // ProfileManagerActivity의 Model static 함수 정의
    private static final String TAG = "ProfileManagerModel";

    public static boolean updateUserInfo(String email, String password, String nickname, String newpassword, boolean isChangeProfileImage, File imageFile) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        boolean isUpdated = false;
        String message = null;

        // 수정할 데이터의 정보를 담은 RequestBody 생성
        RequestBody requestBody = null;

        if ( isChangeProfileImage ) {
            MediaType pngType = MediaType.parse("image/png");
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", email)
                    .addFormDataPart("password", password)
                    .addFormDataPart("nickname", nickname)
                    .addFormDataPart("newpassword", newpassword)
                    .addFormDataPart("file", imageFile.getName(), RequestBody.create(pngType, imageFile))
                    .build();
        } else {
            requestBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .add("nickname", nickname)
                    .add("newpassword", newpassword)
                    .build();
        }

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstant.SERVER_URL_UPDATE, requestBody);

            if (response == null) {
                Log.e(TAG, "Response of updateUserInfo() is null.");

                return false;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if (jsonFromServer.has("result")) {
                    isUpdated = jsonFromServer.getBoolean("result");
                }

                if (jsonFromServer.has("message")) {
                    message = jsonFromServer.getString("message");
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

        return isUpdated;
    }

    public static boolean deleteUser(String email, String password){
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        boolean isDeleted = false;
        String message = null;

        // 수정할 데이터의 정보를 담은 RequestBody 생성
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstant.SERVER_URL_DELETE, requestBody);

            if (response == null) {
                Log.e(TAG, "Response of deleteUser() is null.");

                return false;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if (jsonFromServer.has("result")) {
                    isDeleted = jsonFromServer.getBoolean("result");
                }

                if (jsonFromServer.has("message")) {
                    message = jsonFromServer.getString("message");
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

        return isDeleted;
    }
}
