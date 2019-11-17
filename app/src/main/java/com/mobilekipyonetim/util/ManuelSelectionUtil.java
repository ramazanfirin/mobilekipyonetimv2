package com.mobilekipyonetim.util;

import android.content.res.Resources;
import android.net.Uri;

import com.mobilekipyonetim.R;
import com.mobilekipyonetim.activity.KeyValueDTO;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ManuelSelectionUtil {


	//static String serverAddress  ="http://ec2-52-11-218-57.us-west-2.compute.amazonaws.com:8090/api/cbs-data-controller/";
	static String  serverAddress = Resources.getSystem().getString(R.string.serverUrl);

	public static KeyValueDTO[] getIlceList(){
		return convertToArray(getData("getIlceList"));
	}
	
	public static String getIlceListAsString(){
		return getDataAsString("getIlceList");
	}
	
	public static String getMahalleListAsString(String param){
		return getDataAsString("getMahalleList/"+param);
	}
	public static KeyValueDTO[] getMahalleList(String param){
		return convertToArray(getData("/getMahalleList/"+param));
	}
	
	public static String getSokakListAsString(String param){
		return getDataAsString("getSokakList/"+param);
	}
	
	public static String getBinaListAsString(String param){
		return getDataAsString("getBinaList/"+param);
	}
	
	public static String getCoordinatesAsString(String param){
		return getDataAsString("getCoordinate/"+param);
	}
	
	public static String getDataAsString(String... urls) {
	      String response = "";

	      //String url = serverAddress+"/"+Uri.encode(urls[0]);;
		String url = serverAddress+"/"+urls[0].replace(" ","%20");;
		   //String query = URLEncoder.encode("apples oranges", "utf-8");
	       DefaultHttpClient client = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(url);
	        try {
	          HttpResponse execute = client.execute(httpGet);
	          InputStream content = execute.getEntity().getContent();

	          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	          String s = "";
	          while ((s = buffer.readLine()) != null) {
	            response += s;
	          }

	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	     
	      return response;
	    }
	
	public static List<KeyValueDTO> getData(String... urls) {
	      String response ="";
	        try {
	        	response = getDataAsString(urls);

	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	     
	      return convert(response);
	    }
	
	public static List<KeyValueDTO> convert(String result){
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
    	try {
			//JSONObject reader = new JSONObject(result);
    		resultList.add(new KeyValueDTO("Seciniz", "Seciniz"));
			JSONArray reader = new JSONArray(result);
			for (int i = 0; i < reader.length(); i++) {
				JSONObject jsonObject = (JSONObject)reader.get(i);
				resultList.add(new KeyValueDTO((String)jsonObject.get("value"), (String)jsonObject.get("key")));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return resultList;
	}
	
	public static KeyValueDTO[] convertToArray(List<KeyValueDTO> resultList){
		return resultList.toArray(new KeyValueDTO[resultList.size()]);
	}
}
