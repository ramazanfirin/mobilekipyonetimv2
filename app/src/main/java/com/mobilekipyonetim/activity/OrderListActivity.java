package com.mobilekipyonetim.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilekipyonetim.R;
import com.mobilekipyonetim.service.GCMRegisterCheckTask;
import com.mobilekipyonetim.service.GCMRegisterTask;
import com.mobilekipyonetim.service.GetOrderListTask;
import com.mobilekipyonetim.util.Util;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderListActivity extends BaseActivity{
	
	public ListView mainListView;
	 public ArrayList<HashMap<String, String>> orderList;

	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.main, menu);
			return true;
		}
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == R.id.regControl) {
				new GCMRegisterCheckTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,getServerUrl()+"/hello/checkRegister/"+getDeviceId());
			}
			
			if (item.getItemId() == R.id.register) {
				new GCMRegisterTask(getApplication(),getSENDER_ID()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,getServerUrl()+"/hello/RegisterId/"+getDeviceId());
			}
			return super.onOptionsItemSelected(item);
		}

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list_activity);
        
        Bundle bundle = getIntent().getExtras();
        
        if(bundle!=null){
        String messageType = (String) bundle.get("messageType");
        if(messageType != null && !messageType.equals(""))
        	Log.i("mobilEkip", "message Type = "+messageType);
        
        String message =(String) bundle.get("message");
        if(message != null && !message.equals("")){
        	if("newOrder".equals(message)){
        		Log.i("mobilEkip", "Yeni order geldi");
        	}if("cancelled".equals(message)){
        		Log.i("mobilEkip", "order silindi");
        		 String deletedOrderId =(String) bundle.get("orderId");
        		 String currentOrderId = getGlobalCurrentOrderId();
        		 String currentOrderStatus = getGlobalCurrentOrderStatus();
        		 if(currentOrderId.equals(deletedOrderId) && currentOrderStatus.endsWith(Util.ORDER_STATUS_STARTED)){
        			 getMyApplication().stopTracking();
        			 //update user
        		 }
        	}
        }
        }
        
        orderList = new ArrayList<HashMap<String, String>>();
        
        mainListView = (ListView) findViewById( R.id.list );
        mainListView.setOnItemClickListener(new OnItemClickListener()
        {
        public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
        {
        	Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
        	String orderId = ((TextView) v.findViewById(R.id.id)).getText().toString();
        	
        	
        	intent.putExtra("order", Util.findHashMap(orderList, orderId));               
            startActivity(intent);
        }
        });
        
       

//new RequestItemsServiceTask().execute(getServerUrl()+"/hello/"+getDeviceId());
      try {
		new GetOrderListTask(this).executeOnExecutor(mCustomThreadPoolExecutor,getServerUrl()+"/hello/"+getDeviceId());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}   
	
	public void refreshPage(View v){
		new GetOrderListTask(this).execute(getServerUrl()+"/hello/"+getDeviceId());
		Log.i("mobilEkip","Refresh Yapıldı.");
	}
}


