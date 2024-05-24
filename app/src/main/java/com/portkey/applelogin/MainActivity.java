package com.portkey.applelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.portkey.nativeappleloginactivity.NativeAppleLoginActivity;

public class MainActivity extends AppCompatActivity {

    public class LoginCallback implements NativeAppleLoginActivity.Callback {
        public void onResult(String authToken) {
            Log.d("Unity WebView Success: ", authToken);
        }
        public void onError(String error) {
            Log.d("Unity Error: ", error);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NativeAppleLoginActivity.SetCallback(new LoginCallback());
        NativeAppleLoginActivity.url = "https://openlogin.portkey.finance/social-login/Apple";
        NativeAppleLoginActivity.Call(this);
    }
}