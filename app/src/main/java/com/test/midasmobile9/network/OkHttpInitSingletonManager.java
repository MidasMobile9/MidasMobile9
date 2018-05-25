package com.test.midasmobile9.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpInitSingletonManager {
    private static OkHttpClient okHttpClient = null;

    static {
        // 최대 20초 동안 연결
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    private OkHttpInitSingletonManager() {
        // Singleton Pattern
    }

    public static OkHttpClient getOkHttpClient(){

        if ( okHttpClient != null ) {
            return okHttpClient;
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
