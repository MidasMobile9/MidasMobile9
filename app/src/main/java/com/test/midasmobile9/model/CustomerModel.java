package com.test.midasmobile9.model;

import android.util.Log;

import com.google.gson.Gson;
import com.test.midasmobile9.data.AdminMenuItem;
import com.test.midasmobile9.data.CustomerInfoItem;
import com.test.midasmobile9.network.NetworkDefineConstantCYJ;
import com.test.midasmobile9.network.OkHttpAPICall;
import com.test.midasmobile9.network.OkHttpInitSingletonManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomerModel {
    private static final String TAG = "CustomerModel";

    public static Map<String, Object> getCustomers() {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        Gson gson = new Gson();

        Map<String, Object> map = null;

        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstantCYJ.SERVER_URL_GET_CUSTOMERS);

            if ( response == null ) {
                Log.e(TAG, "Response of getCustomers() is null.");

                return null;
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

                // 고객 정보
                if ( jsonFromServer.has("data") ) {
                    JSONArray jsonArray = jsonFromServer.getJSONArray("data");

                    List<CustomerInfoItem> customerList = new ArrayList<>();

                    for ( int i = 0; i < jsonArray.length(); i++ ) {
                        CustomerInfoItem item = gson.fromJson(jsonArray.get(i).toString(), CustomerInfoItem.class);
                        customerList.add(item);
                    }

                    map.put("customerList", customerList);
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

    public static Map<String, Object> deleteCustomer(int no) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        Map<String, Object> map = null;

        RequestBody requestBody = new FormBody.Builder()
                .add("no", String.valueOf(no))
                .build();

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstantCYJ.SERVER_URL_DELETE_CUSTOMERS, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of deleteCustomer() is null.");

                return null;
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

    public static Map<String, Object> editCustomer(int no, String nickname, String part, String phone, String email, String newpassword, File imageFile, boolean isChangeProfileImage) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        Map<String, Object> map = null;

        // 추가할 메누의 정보를 담은 RequestBody 생성
        RequestBody requestBody = null;

        if ( isChangeProfileImage ) {
            MediaType pngType = MediaType.parse("image/png");
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("no", String.valueOf(no))
                    .addFormDataPart("nickname", nickname)
                    .addFormDataPart("part", part)
                    .addFormDataPart("phone", phone)
                    .addFormDataPart("email", email)
                    .addFormDataPart("newpassword", newpassword)
                    .addFormDataPart("file", imageFile.getName(), RequestBody.create(pngType, imageFile))
                    .build();
        } else {
            requestBody = new FormBody.Builder()
                    .add("no", String.valueOf(no))
                    .add("nickname", nickname)
                    .add("part", part)
                    .add("phone", phone)
                    .add("email", email)
                    .add("newpassword", newpassword)
                    .build();
        }

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstantCYJ.SERVER_URL_EDIT_CUSTOMERS, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of editCustomer() is null.");

                return null;
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

    public static Map<String, Object> addCustomer(String nickname, String part, String phone, String email, String password, File imageFile, boolean isChangeProfileImage, int root) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        Map<String, Object> map = null;

        // 추가할 메누의 정보를 담은 RequestBody 생성
        RequestBody requestBody = null;

        if ( isChangeProfileImage ) {
            MediaType pngType = MediaType.parse("image/png");
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("nickname", nickname)
                    .addFormDataPart("part", part)
                    .addFormDataPart("phone", phone)
                    .addFormDataPart("email", email)
                    .addFormDataPart("password", password)
                    .addFormDataPart("file", imageFile.getName(), RequestBody.create(pngType, imageFile))
                    .addFormDataPart("root", String.valueOf(root))
                    .build();
        } else {
            requestBody = new FormBody.Builder()
                    .add("nickname", nickname)
                    .add("part", part)
                    .add("phone", phone)
                    .add("email", email)
                    .add("password", password)
                    .add("root", String.valueOf(root))
                    .build();
        }

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstantCYJ.SERVER_URL_ADD_CUSTOMERS, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of addCustomer() is null.");

                return null;
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
