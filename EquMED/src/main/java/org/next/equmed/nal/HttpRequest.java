package org.next.equmed.nal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.next.equmed.bal.BusinessAccessLayer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class HttpRequest {

	private static HttpRequest instance;
	private String requestString;
	public String responseString;
	public static String data = "";
	 static JSONObject jObj = null;
	
	 
	
	private HttpRequest() {

	}// Hide constructor

	/**
	 * @Type static Single Argument Method
	 * @Access_Specifier public
	 * @Created_By Mohanraj.S
	 * @Created_On 11-05-2015
	 * @Updated_By
	 * @Updated_On
	 * @Description get InternetConnection Availability.*/

	public static boolean isOnline(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		NetworkInfo typemo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo tywi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		NetworkInfo tywifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (netInfo != null && netInfo.isConnectedOrConnecting()
				|| typemo != null && typemo.isConnectedOrConnecting()
				|| tywi != null && tywi.isConnectedOrConnecting()
				|| tywifi != null && tywifi.isConnectedOrConnecting()) {
			BusinessAccessLayer.IS_INTERNETAVAILABLE = true;
			return BusinessAccessLayer.IS_INTERNETAVAILABLE;
		} else {
			BusinessAccessLayer.IS_INTERNETAVAILABLE = false;
			return BusinessAccessLayer.IS_INTERNETAVAILABLE;
		}

	}

	public static HttpRequest sharedInstance() {

		if (instance == null) {
			instance = new HttpRequest();

		}
		return instance;
		// End of sharedinstance
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {

		throw new CloneNotSupportedException("Clone is not allowed.");

	}// End of clone

	public void doHttpRequest(String requestUrl, List<NameValuePair> params) {

		requestString = requestUrl;
		Log.i("Request String ", requestUrl);
		// Clear the response String
		responseString = "";
		InputStream is = null;

		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(requestString);			
			httppost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();			
			is = entity.getContent();




		} catch (Exception e) {
			System.out.println("JSON Result Admin_Login String e ====>" + e);
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			/*BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);*/
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();			
			responseString = sb.toString();			
		
			Log.e("responseString", "responseString" + responseString);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}// End of catch
	
	}// End of do http request
		// String requestUrl



	public JSONObject downloadUrl(String strUrl) throws IOException{
	       // String data = "";
	        InputStream iStream = null;
	        try{
	                URL url = new URL(strUrl); 
	                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();              
	                urlConnection.connect();
	                iStream = urlConnection.getInputStream();   
	                	
	                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));                
	              
	                StringBuffer sb  = new StringBuffer();
	                
	                String line = "";
	                while( ( line = br.readLine())  != null){
	                	sb.append(line);
	                }
	                
	                data = sb.toString();
	                
	                br.close();

	        }catch(Exception e){
	                Log.d("Exception while downloading url", e.toString());
	        }
	        
	        try
	        {
	        	 jObj = new JSONObject(data);
	        	
	        }
	        catch (JSONException e) {
	        	 Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }

	        return jObj;
	    }
	
	
	public String md5(final String password) {
				try {
		
					MessageDigest digest = MessageDigest.getInstance("MD5");
					digest.update(password.getBytes());
					byte messageDigest[] = digest.digest();
		
					StringBuffer hexString = new StringBuffer();
					for (int i = 0; i < messageDigest.length; i++) {
						String h = Integer.toHexString(0xFF & messageDigest[i]);
						while (h.length() < 2)
							h = "0" + h;
						hexString.append(h);
		
					}
					Log.v("Md", "" + hexString);
					return hexString.toString();
		
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				return "";
			}


}// End of Http Request Class