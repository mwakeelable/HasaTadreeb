package com.linked_sys.tadreeb_ihssa.core;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.linked_sys.tadreeb_ihssa.R;
import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Locale;

import in.myinnos.customfontlibrary.TypefaceUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppController extends MultiDexApplication {
    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // Load fonts
        String customFont = "DINNextLTArabic-Light.ttf";
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/" + customFont)
                        .build()
        );
//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/DINNextLTArabic-Light.ttf");
        CookieHandler.setDefault(new CookieManager());
        SharedManager prefs = new SharedManager();
        String appLanguage = prefs.getLanguage(this);
        if (!Locale.getDefault().getLanguage().equalsIgnoreCase(appLanguage)) {
            LocaleUtils.changeLocale(getApplicationContext(), appLanguage);
        }
        SimpleChromeCustomTabs.initialize(this);
        MultiDex.install(this);
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
