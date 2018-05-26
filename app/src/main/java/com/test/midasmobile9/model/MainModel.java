package com.test.midasmobile9.model;

import android.util.Log;

import com.google.gson.Gson;
import com.test.midasmobile9.data.CoffeeMenuItem;
import com.test.midasmobile9.data.CoffeeOrderItem;
import com.test.midasmobile9.network.NetworkDefineConstantOSH;
import com.test.midasmobile9.network.OkHttpAPICall;
import com.test.midasmobile9.network.OkHttpInitSingletonManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainModel {
    // MainActivity의 Model static 함수 정의

    private static final String TAG = "MainModel";

    public static Map<String, Object> getAllMenu() {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Gson gson = new Gson();
        Response response = null;

        boolean isSuccess = false;
        String message = null;
        ArrayList<CoffeeMenuItem> coffeeMenuItems = new ArrayList<>();

        Map<String, Object> resultMap = new HashMap<>();

        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstantOSH.SERVER_URL_GET_MENU);

            if (response == null) {
                Log.e(TAG, "Response of updateUserInfo() is null.");

                return null;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if (jsonFromServer.has("result")) {
                    isSuccess = jsonFromServer.getBoolean("result");
                    resultMap.put("result", isSuccess);
                }

                if (jsonFromServer.has("message")) {
                    message = jsonFromServer.getString("message");
                    resultMap.put("message", message);
                }

                if (isSuccess) {
                    JSONArray jsonArray = jsonFromServer.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CoffeeMenuItem eachCoffeeMenuItem = gson.fromJson(jsonObject.toString(), CoffeeMenuItem.class);
                        coffeeMenuItems.add(eachCoffeeMenuItem);
                    }
                    resultMap.put("data", coffeeMenuItems);
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

        return resultMap;
    }

    public static Map<String, Object> getUserOrderLookup() {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Gson gson = new Gson();
        Response response = null;

        boolean isSuccess = false;
        String message = null;
        ArrayList<CoffeeOrderItem> coffeeOrderItems = new ArrayList<>();

        Map<String, Object> resultMap = new HashMap<>();

        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstantOSH.SERVER_URL_GET_ORDER);

            if (response == null) {
                Log.e(TAG, "Response of updateUserInfo() is null.");

                return null;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if (jsonFromServer.has("result")) {
                    isSuccess = jsonFromServer.getBoolean("result");
                    resultMap.put("result", isSuccess);
                }

                if (jsonFromServer.has("message")) {
                    message = jsonFromServer.getString("message");
                    resultMap.put("message", message);
                }

                if (isSuccess) {
                    JSONArray jsonArray = jsonFromServer.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CoffeeOrderItem eachCoffeeOrderItem = gson.fromJson(jsonObject.toString(), CoffeeOrderItem.class);
                        coffeeOrderItems.add(eachCoffeeOrderItem);
                    }
                    resultMap.put("data", coffeeOrderItems);
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

        return resultMap;
    }


    public static Map<String, Object> getUserHistory(String year, String month) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Gson gson = new Gson();
        Response response = null;

        boolean isSuccess = false;
        String message = null;
        ArrayList<CoffeeOrderItem> coffeeOrderItems = new ArrayList<>();

        Map<String, Object> resultMap = new HashMap<>();

        try {
            if (year.length() == 2) {
                year = "20" + year;
            } else if (year.length() == 3) {
                year = "2" + year;
            } else if (year.length() != 4) {
                year = "2018";
            }

            if (month.length() == 1) {
                month = "0" + month;
            } else {
                month = "05";
            }

            String yearmonthStr = year + "-" + month;

            response = OkHttpAPICall.GET(client, NetworkDefineConstantOSH.SERVER_URL_GET_HISTORY + yearmonthStr);

            if (response == null) {
                Log.e(TAG, "Response of updateUserInfo() is null.");

                return null;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                if (jsonFromServer.has("result")) {
                    isSuccess = jsonFromServer.getBoolean("result");
                    resultMap.put("result", isSuccess);
                }

                if (jsonFromServer.has("message")) {
                    message = jsonFromServer.getString("message");
                    resultMap.put("message", message);
                }

                if (isSuccess) {
                    JSONArray jsonArray = jsonFromServer.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CoffeeOrderItem eachCoffeeOrderItem = gson.fromJson(jsonObject.toString(), CoffeeOrderItem.class);
                        coffeeOrderItems.add(eachCoffeeOrderItem);
                    }
                    resultMap.put("data", coffeeOrderItems);
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

        return resultMap;
    }
}
