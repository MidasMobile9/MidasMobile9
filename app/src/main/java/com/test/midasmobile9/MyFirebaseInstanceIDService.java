package com.test.midasmobile9;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.test.midasmobile9.util.SharePreferencesUtil;

import java.util.Map;

import static com.test.midasmobile9.model.LoginModel.addTokenResult;
import static com.test.midasmobile9.util.SharePreferencesUtil.KEY_TOKEN;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    private Context mContext;
    public MyFirebaseInstanceIDService(Context context) {
        super();
        mContext = context;
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        if(SharePreferencesUtil.getPreferences(mContext,KEY_TOKEN)==null){
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + refreshedToken);
            // If you want to send messages to this application instance or
            // manage this apps subscriptions on the server side, send the
            // Instance ID token to your app server.
            sendRegistrationToServer(refreshedToken);
        }

    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        new AddTokenTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,token);
    }

    /**
     * 서버에 토큰 보내기
     */
    public class AddTokenTask extends AsyncTask<String, Void, Map<String, Object>> {
        String token;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(String... params) {
            token= params[0];
            Map<String, Object> map = addTokenResult(token);
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);
            if (map == null) {
                // 통신실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";

            } else {
                boolean result = false;
                String message = null;
                if (map.containsKey("result")) {
                    result = (boolean) map.get("result");
                }
                if (map.containsKey("message")) {
                    message = (String) map.get("message");
                }
                if (result) {
                    //성공
                    SharePreferencesUtil.savePreferences(mContext,KEY_TOKEN,token);
                    Log.d(TAG,"토큰저장");
                } else {
                    //실패
                    Log.d(TAG,"토큰실패");
                }
            }
        }
    }
}
