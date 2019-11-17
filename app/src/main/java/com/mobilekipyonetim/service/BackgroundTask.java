package com.mobilekipyonetim.service;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mobilekipyonetim.activity.MainActivity;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class BackgroundTask extends AsyncTask<Void, Integer, Void> {



   //asList //private ProgressBar spinner = new ProgressBar();

    //@Override
    protected void onPreExecute() {
        super.onPreExecute();
        //spinner.setVisibility(View.VISIBLE);
        Log.d("onPreExecute","onPreExecute");
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (int i = 0; i < 101; i = i + 10) {
            try {
                publishProgress(i);
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
            }
        }
        Log.d("doInBackground","doInBackground");
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
       // spinner.setVisibility(View.GONE);
        Log.d("onPostExecute","onPostExecute");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
       // spinner.setProgress(values[0]);
    }

    @Override
    protected void onCancelled(Void result) {
        //super.onCancelled(result);

    }
}