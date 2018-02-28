package org.next.equmed.nal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.next.equmed.bal.BusinessAccessLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.List;

/**
 * Created by nextmoveo-1 on 11/12/15.
 */
public class NetworkAccessLayer_RESTFUL
{

    private static String reponseStr="";
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;


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


    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d( "InputStream", e.getLocalizedMessage() );
        }

        return result;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    /**
     * @Type static No Argument Method
     * @Access_Specifier public
     * @Created_By Mohanraj.S
     * @Created_On 11-05-2015
     * @Updated_By
     * @Updated_On
     * @Description get MasterData From Server Using the RESTFUL WebService*/

    public static String getMasterDataFromServer(String imeiNo,String syncdate) {


        return reponseStr;
    }

    /*
    * Making service call
    * @url - url to make request
    * @method - http request method
    * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    /*
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {

        try {

            HttpParams httpParameters = new BasicHttpParams();
// Set the timeout in milliseconds until a connection is established.
// The default value is zero, that means the timeout is not used.
            HttpConnectionParams.setConnectionTimeout(httpParameters, 20000);
// Set the default socket timeout (SO_TIMEOUT)
// in milliseconds which is the timeout for waiting for data.
            HttpConnectionParams.setSoTimeout(httpParameters, 20000);

            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;


            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);

            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException | SocketException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }
}
