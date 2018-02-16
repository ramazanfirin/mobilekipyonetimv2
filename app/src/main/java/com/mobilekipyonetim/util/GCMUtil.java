package com.mobilekipyonetim.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.mobilekipyonetim.activity.BaseActivity;

import java.sql.Timestamp;

public class GCMUtil {

	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_ON_SERVER_EXPIRATION_TIME ="onServerExpirationTimeMs";
    public static final long REGISTRATION_EXPIRY_TIME_MS = 1000 * 3600 * 24 * 7;
    static final String TAG = "GCMDemo";
	
	public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.length() == 0) {
            Log.v(TAG, "Registration not found.");
            return "";
        }
        // check if app was updated; if so, it must clear registration id to
        // avoid a race condition if GCM sends a message
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion || isRegistrationExpired(context)) {
            Log.v(TAG, "App version changed or registration expired.");
            return "";
        }
        return registrationId;
    }
	
	
	 public static SharedPreferences getGCMPreferences(Context context) {
	        return context.getSharedPreferences(BaseActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	    }
	 
	 private static int getAppVersion(Context context) {
	        try {
	            PackageInfo packageInfo = context.getPackageManager()
	                    .getPackageInfo(context.getPackageName(), 0);
	            return packageInfo.versionCode;
	        } catch (NameNotFoundException e) {
	            // should never happen
	            throw new RuntimeException("Could not get package name: " + e);
	        }
	    }
	 
	 public  static boolean isRegistrationExpired(Context context ) {
	        final SharedPreferences prefs = getGCMPreferences(context);
	        // checks if the information is not stale
	        long expirationTime =
	                prefs.getLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, -1);
	        return System.currentTimeMillis() > expirationTime;
	    }
	 
	 public static void setRegistrationId(Context context, String regId) {
	        final SharedPreferences prefs = getGCMPreferences(context);
	        int appVersion = getAppVersion(context);
	        Log.v(TAG, "Saving regId on app version " + appVersion);
	        SharedPreferences.Editor editor = prefs.edit();
	        editor.putString(PROPERTY_REG_ID, regId);
	        editor.putInt(PROPERTY_APP_VERSION, appVersion);
	        long expirationTime = System.currentTimeMillis() + REGISTRATION_EXPIRY_TIME_MS;

	        Log.v(TAG, "Setting registration expiry time to " +
	                new Timestamp(expirationTime));
	        editor.putLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, expirationTime);
	        editor.commit();
	    }
	 
	 
}
