package com.test.midasmobile9.network;

import android.util.Log;

import com.test.midasmobile9.application.MidasMobile9Application;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpAPICall {
    static final String TAG = "==========OkHttpAPICall";

    // GET Network request
    public static Response GET(OkHttpClient client, String url) throws IOException {
        // 요청결과 확인
        boolean RESPONSE_FLAG = false;
        // Request 생성
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", MidasMobile9Application.getCookie())
                .get()
                .build();
        // Request 요청 및 Response 응답
        Response response = client.newCall(request).execute();
        RESPONSE_FLAG = response.isSuccessful();

        if ( !RESPONSE_FLAG ) {
            Log.e(TAG, "Response of GET method returns false.");

            return null;
        }

        return response;
    }

    // POST Network request
    public static Response POST(OkHttpClient client, String url, RequestBody requestBody) throws IOException {
        // 요청결과 확인
        boolean RESPONSE_FLAG = false;
        // Request 생성
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", MidasMobile9Application.getCookie())
                .post(requestBody)
                .build();
        // Request 요청 및 Response 응답
        Response response = client.newCall(request).execute();
        RESPONSE_FLAG = response.isSuccessful();

        if ( !RESPONSE_FLAG ) {
            Log.e(TAG, "Response of POST method returns false.");

            return null;
        }

        return response;
    }

    // PUT Network request
    public static Response PUT(OkHttpClient client, String url, RequestBody requestBody) throws IOException {
        // 요청결과 확인
        boolean RESPONSE_FLAG = false;
        // Request 생성
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", MidasMobile9Application.getCookie())
                .put(requestBody)
                .build();
        // Request 요청 및 Response 응답
        Response response = client.newCall(request).execute();
        RESPONSE_FLAG = response.isSuccessful();

        if ( !RESPONSE_FLAG ) {
            Log.e(TAG, "Response of PUT method returns false.");

            return null;
        }

        return response;
    }

    // DELETE Network request
    public static Response DELETE(OkHttpClient client, String url, RequestBody requestBody) throws IOException {

        // 요청결과 확인
        boolean RESPONSE_FLAG = false;
        // Request 생성
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", MidasMobile9Application.getCookie())
                .delete(requestBody)
                .build();
        // Request 요청 및 Response 응답
        Response response = client.newCall(request).execute();
        RESPONSE_FLAG = response.isSuccessful();

        if ( !RESPONSE_FLAG ) {
            Log.e(TAG, "Response of DELETE method returns false.");

            return null;
        }

        return response;
    }
}
