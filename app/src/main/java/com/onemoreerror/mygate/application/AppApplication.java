package com.onemoreerror.mygate.application;

import android.app.Application;
import android.support.annotation.NonNull;

public class AppApplication extends Application {

    private static AppApplication sInstance;
//    private RequestQueue mRequestQueue;
//    private ImageLoader mImageLoader;
//    LruBitmapCache mLruBitmapCache;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
//    public LruBitmapCache getLruBitmapCache() {
//        if (mLruBitmapCache == null)
//            mLruBitmapCache = new LruBitmapCache();
//        return this.mLruBitmapCache;
//    }
//    public ImageLoader getImageLoader() {
//        getRequestQueue();
//        if (mImageLoader == null) {
//            getLruBitmapCache();
//            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
//        }
//
//        return this.mImageLoader;
//    }
    public static AppApplication getInstance() {
        if (sInstance == null)
            sInstance = new AppApplication();

        return sInstance;
    }

//    public RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        }
//        return mRequestQueue;
//    }
//
//    public void clearAllVolleyCache() {
//        try {
//            getRequestQueue().getCache().clear();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public <T> void addToRequestQueue(Request<T> req, @NonNull String tag) {
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(8 * 1000, 0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        addToRequestQueue(req, tag, true, retryPolicy);
//    }
//
//    public <T> void addToRequestQueue(Request<T> req, @NonNull String tag, boolean cache, RetryPolicy retryPolicy) {
//        req.setTag(tag);
//        req.setShouldCache(cache);
//        req.setRetryPolicy(retryPolicy);
//        getRequestQueue().add(req);
//    }
}
