/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobilekipyonetim.receiver;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mobilekipyonetim.R;
import com.mobilekipyonetim.activity.DetailsActivity;
import com.mobilekipyonetim.activity.MainActivity;
import com.mobilekipyonetim.application.MyApplication;

/**
 * Handling of GCM messages.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {
    static final String TAG = "GCMDemo";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Context ctx;
    @Override
    public void onReceive(Context context, Intent intent) {
    	try {
			Log.i("mobilEkip", "GCM Mesaj Geldi");
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
			ctx = context;
			
			String messageType = gcm.getMessageType(intent);
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
			    sendNotification("Send error: " + intent.getExtras().toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
			    sendNotification("Deleted messages on server: " + intent.getExtras().toString());
			} else {
			    //sendNotification("Received: " + intent.getExtras().toString());
			    //Intent newIntent = new Intent();
				
				
				String message =(String)intent.getExtras().get("message");
				String orderId =(String)intent.getExtras().get("orderId");
				String immediately = (String)intent.getExtras().get("immediately");
				
				sendNotification("mesaj geldi="+ " "+ message+ " "+ orderId);
				Log.i("mobilEkip", "GCM Mesaj = "+ message+ " "+ orderId);
				
				if("true".equals(immediately)){
					String latValue = (String)intent.getExtras().get("lat");
					String lngValue = (String)intent.getExtras().get("lng");
					
					Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		            if (uri != null) {
		              Ringtone rt= RingtoneManager.getRingtone(context,uri);
		              if (rt != null) {
		                rt.setStreamType(AudioManager.STREAM_NOTIFICATION);
		                rt.play();
		              }
		            }
					
		            Log.i("Wakeup", "Display Wakeup");
		            PowerManager pm = (PowerManager)context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
		            WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "Phone WakeUp");
		            wakeLock.acquire();
					
					  wakeLock.release();;
		            
					Location dest = new Location("Destination2");
			   	 	dest.setLatitude(Double.valueOf(latValue));
			   	 	dest.setLongitude(Double.valueOf(lngValue));
			   	 	MyApplication myApplication = (MyApplication)context.getApplicationContext() ;
			   	 	myApplication.startTracking(orderId, dest);
			   	 	
			   	 	//String uri2 ="route66://?daddr="+latValue+","+lngValue;
					String uri2 ="http://maps.google.com/maps?f=d&daddr="+latValue+","+lngValue;
					Toast.makeText(context.getApplicationContext(),uri2, Toast.LENGTH_LONG).show();
			   	
			   		Intent newIntent = new Intent(Intent.ACTION_VIEW);
			   		newIntent.setData(Uri.parse(uri2));
			   		newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   		context.startActivity(newIntent);
				
			   	  wakeLock.release();;
				
					
				}else{
				
				
				
					Intent newIntent = new Intent(context,MainActivity.class);
					  newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					  newIntent.putExtra("orderId", orderId);
					  newIntent.putExtra("message", message);
					  newIntent.putExtra("messageType", "GCM");
					context.startActivity(newIntent);
				
				/*
			    Intent newIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://"+intent.getExtras().get("message")));
			    //newIntent.setClass(context, AndroidMobilePushApp.class);
			    newIntent.putExtras(intent.getExtras());
			    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    context.startActivity(newIntent);
			    */
			}
			setResultCode(Activity.RESULT_OK);
   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context.getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
		}
    }
    // Put the GCM message into a notification and post it.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, DetailsActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
        //.setSmallIcon(R.drawable.ic_stat_gcm)
        .setContentTitle("GCM Notification")
		.setSmallIcon(R.drawable.ic_dashboard_black_24dp)
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
