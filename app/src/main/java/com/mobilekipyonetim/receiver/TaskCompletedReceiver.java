package com.mobilekipyonetim.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mobilekipyonetim.application.MyApplication;

public class TaskCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		MyApplication myApplication = (MyApplication)context.getApplicationContext() ;
		Log.i("mobilEkip","task tamamlandi bilgisi alindi");
		String orderId = intent.getStringExtra("orderId");
		myApplication.completedTraking(orderId);
		
	}

}
