package org.fleek.tracking;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;

import android.util.Log;


import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class TrackingPlugin extends CordovaPlugin {
    private static final String TAG = "TrackingPlugin";
    public static final String PREFS_NAME = "Tracking";
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.v(TAG, "Init NativeStorage");
        sharedPref = cordova.getActivity().getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        editor = sharedPref.edit();
    }


    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        if (("get").equals(action)) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    try {
            /* getting arguments */
                        String ref = args.getString(0);
                        //System.out.println("Receveived reference: " + ref);
                        String s = sharedPref.getString(ref, "null");
                        callbackContext.success(s);
                    } catch (Exception e) {
                        Log.e(TAG, "GetString failed :", e);
                        callbackContext.error(e.getMessage());
                    }
                }
            });
            return true;
        }
        return false;
    }


}