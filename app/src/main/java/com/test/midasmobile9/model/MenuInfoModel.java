package com.test.midasmobile9.model;

import android.util.Log;

import com.google.gson.Gson;
import com.test.midasmobile9.data.AdminCoffeeOrderItem;
import com.test.midasmobile9.data.AdminMenuItem;
import com.test.midasmobile9.data.User;
import com.test.midasmobile9.network.NetworkDefineConstant;
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

public class MenuInfoModel {
    private static final String TAG = "MenuInfoModel";

    public static Map<String, Object> getMenuInfo() {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;
        Gson gson = new Gson();

        Map<String, Object> map = null;

        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstantCYJ.SERVER_URL_GET_MENU_INFO);

            if ( response == null ) {
                Log.e(TAG, "Response of getOrders() is null.");

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

                // 유저 로그인 정보
                if ( jsonFromServer.has("data") ) {
                    JSONArray jsonArray = jsonFromServer.getJSONArray("data");

                    List<AdminMenuItem> menuInfoList = new ArrayList<>();

                    for ( int i = 0; i < jsonArray.length(); i++ ) {
                        AdminMenuItem item = gson.fromJson(jsonArray.get(i).toString(), AdminMenuItem.class);
                        menuInfoList.add(item);
                    }

                    map.put("menuInfoList", menuInfoList);
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

    public static Map<String, Object> addNewMenu(String name, int price, String info, int hotcold, File imageFile, boolean isChangeProfileImage) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        Map<String, Object> map = null;

        // 추가할 메누의 정보를 담은 RequestBody 생성
        RequestBody requestBody = null;

        if ( isChangeProfileImage ) {
            MediaType pngType = MediaType.parse("image/png");
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", name)
                    .addFormDataPart("info", info)
                    .addFormDataPart("price", String.valueOf(price))
                    .addFormDataPart("file", imageFile.getName(), RequestBody.create(pngType, imageFile))
                    .addFormDataPart("hotcold", String.valueOf(hotcold))
                    .build();
        } else {
            requestBody = new FormBody.Builder()
                    .add("name", name)
                    .add("info", info)
                    .add("price", String.valueOf(price))
                    .add("hotcold", String.valueOf(hotcold))
                    .build();
        }

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstantCYJ.SERVER_URL_ADD_NEW_MENU, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of addNewMenu() is null.");

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

    public static Map<String, Object> editMenu(int no, String name, int price, String info, int hotcold, File imageFile, boolean isChangeProfileImage) {
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
                    .addFormDataPart("name", name)
                    .addFormDataPart("info", info)
                    .addFormDataPart("price", String.valueOf(price))
                    .addFormDataPart("file", imageFile.getName(), RequestBody.create(pngType, imageFile))
                    .addFormDataPart("hotcold", String.valueOf(hotcold))
                    .build();
        } else {
            requestBody = new FormBody.Builder()
                    .add("no", String.valueOf(no))
                    .add("name", name)
                    .add("info", info)
                    .add("price", String.valueOf(price))
                    .add("hotcold", String.valueOf(hotcold))
                    .build();
        }

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstantCYJ.SERVER_URL_EDIT_MENU, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of editMenu() is null.");

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

    public static Map<String, Object> deleteMenu(int no) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        Map<String, Object> map = null;

        RequestBody requestBody = new FormBody.Builder()
                .add("no", String.valueOf(no))
                .build();

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstantCYJ.SERVER_URL_DELETE_MENU, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of deleteMenu() is null.");

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
