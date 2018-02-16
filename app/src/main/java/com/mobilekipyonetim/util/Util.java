package com.mobilekipyonetim.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Util {

	public static final String ORDER_STATUS_NEW ="NEW";
	public static final String ORDER_STATUS_STARTED ="STARTED";
	public static final String ORDER_STATUS_COMPLETED ="COMPLETED";
	public static final String ORDER_STATUS_CANCELLED ="CANCELLED";
	
	public static HashMap<String, String> findHashMap(ArrayList<HashMap<String, String>> arrayList, String id){
		HashMap<String, String> map = new HashMap<String, String>();
		
		for (Iterator iterator = arrayList.iterator(); iterator.hasNext();) {
			HashMap<String, String> hashMap = (HashMap<String, String>) iterator.next();
			if(hashMap.get("id").equals(id))
				return hashMap;
		}
		
		
		return map;
	}
	
	public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
}
