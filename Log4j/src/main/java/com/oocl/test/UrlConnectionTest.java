package com.oocl.test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by CHENCO7 on 7/26/2017.
 */
public class UrlConnectionTest {
    public static void main(String[] args) throws Exception {

        URL url = new URL("http://localhost:8081/sec/list");
        URLConnection connection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Cookie","JSESSIONID=47311021B48E48144F7C513B7447C97E");
        int len = httpURLConnection.getContentLength();
        InputStream in = httpURLConnection.getInputStream();
        byte[] buf = new byte[len];
        in.read(buf);
        in.close();
        httpURLConnection.disconnect();
        System.out.println(new String(buf));
    }
}
