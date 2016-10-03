package com.untitledapps.meetasweedt;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shaotime on 10/2/2016.
 */

public class DownloadUrl {

    public String readUrl(String strUrl) throws IOException{
        String data = "";

        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            System.out.println("new URL");
            urlConnection = (HttpURLConnection) url.openConnection();
            System.out.println("HTTPURLCONNECTION");
            urlConnection.connect();
            System.out.println("urlconnection.connect");
            iStream = urlConnection.getInputStream(); //error here
            System.out.println("istream = getinputstream");
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            System.out.println("new bufferedReader");
            StringBuffer sb = new StringBuffer();
            System.out.println("new StringBuffer");
            String line = "";
            while((line = br.readLine())!=null){
                System.out.println("***");
                sb.append(line);
            }
            System.out.println("finished while loop");
            data = sb.toString();
            System.out.println("data to string");
            Log.d("downloadUrl", data.toString());
            br.close();
        } catch(Exception e){
            System.out.println("ERROR");
            Log.d("Exception", e.toString());
        } finally {
            System.out.println("finally");
            iStream.close(); //error here
            System.out.println("stream.close");
            urlConnection.disconnect();
            System.out.println("disconnect");
        }
        return data; //data is null
    }
}
