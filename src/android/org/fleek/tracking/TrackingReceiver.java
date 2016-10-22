package org.fleek.tracking;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;


public class TrackingReceiver extends BroadcastReceiver {

    private static final String TAG = "InstallReceiver";

    public static final String TRACKING_ID = "tracking_id";
    public static final String UTM_CAMPAIGN = "utm_campaign";
    public static final String UTM_SOURCE = "utm_source";
    public static final String UTM_MEDIUM = "utm_medium";
    public static final String UTM_TERM = "utm_term";
    public static final String UTM_CONTENT = "utm_content";
    public final String[] sources = {
            TRACKING_ID, UTM_CAMPAIGN, UTM_SOURCE, UTM_MEDIUM, UTM_TERM, UTM_CONTENT
    };
    public static final String PREFS_NAME = "Tracking";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            String referrerString = extras.getString("referrer");
            Log.d("referrer", referrerString);
            if (referrerString != null) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 
                                                        Context.MODE_PRIVATE);
                Editor edit = sharedPreferences.edit();


                try {
                    Map<String, String> getParams = getHashMapFromQuery(referrerString);
                    for (String sourceType : sources) {
                        String source = getParams.get(sourceType);
                        edit.putString(sourceType, source);
                        Log.d("referrer", "key: " + sourceType + " value: " + source);
                    }

                } catch (UnsupportedEncodingException e) {

                    Log.e("Referrer Error", e.getMessage());
                } finally {

                    // Pass along to google
                }

                edit.putString("referrer", referrerString);
                edit.commit();
                //new CampaignTrackingReceiver().onReceive(context, intent);
            }
        }
    }

    public static Map<String, String> getHashMapFromQuery(String query)
            throws UnsupportedEncodingException {

        Map<String, String> query_pairs = new LinkedHashMap<String, String>();

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            Log.d(TAG, pair);
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }
} // end of class
