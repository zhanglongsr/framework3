package com.zxl.test.net.url;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLTest {

    public URLTest() {
    }

    public static void main(String[] arg) {

        try {
            URL url = new URL("http://image0.lietou-static.com/normal/56ab0c0945cec6c679d8a08701a.png");
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(new File("d:\\x.png"));
            int i;
            while ((i = in.read()) != -1) {
                outputStream.write(i);
            }
            outputStream.flush();
            outputStream.close();

        } catch (MalformedURLException e) {
            // TODO_ZXL Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO_ZXL Auto-generated catch block
            e.printStackTrace();
        }
    }
}
