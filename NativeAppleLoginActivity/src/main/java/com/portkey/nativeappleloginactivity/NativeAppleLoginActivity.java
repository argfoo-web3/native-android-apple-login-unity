package com.portkey.nativeappleloginactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NativeAppleLoginActivity extends Activity {

    public interface Callback {
        void onResult(String authToken);
        void onError(String error);
    }
    public static Callback callback;
    public static String url;

    public static void SetCallback(Callback cb)
    {
        callback = cb;
    }

    private AnimationDrawable loadingAnimation;

    public static void Call(Activity activity)
    {
        // Creating an intent with the current activity and the activity we wish to start
        Intent myIntent = new Intent(activity, NativeAppleLoginActivity.class);
        activity.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_native_apple_login);

        WebView webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //load redirect url
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Uri uri = Uri.parse(url);
                String accessToken = uri.getQueryParameter("id_token");
                if(accessToken != null && !accessToken.isEmpty())
                {
                    callback.onResult(accessToken);
                    view.destroy();
                    finish();
                }
            }

            /*@Override
            public void onReceivedHttpError (WebView view,
                                             WebResourceRequest request,
                                             WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);

                callback.onError(errorResponse.getReasonPhrase().toString());
            }*/
        });

        webView.loadUrl(url);
    }
}