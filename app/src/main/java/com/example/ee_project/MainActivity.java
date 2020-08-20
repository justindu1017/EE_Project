package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Load load = new Load();
        load.execute();
    }
    class Load extends AsyncTask<String , String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                String userName = "TestUser";

                URL url = new URL("http://10.0.2.2/index.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
//                connection.connect(); HttpURLConnection do not neet to call connect function
//                connection.setRequestProperty("");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();


                stringBuilder.append("userName=").append(URLEncoder.encode("userName","utf-8"));
                dataOutputStream.writeBytes(stringBuilder.toString());
                dataOutputStream.flush();
                dataOutputStream.close();



                System.out.println("code is "+connection.getResponseCode());
                System.out.println("OKKKKK");



                InputStream is = new BufferedInputStream(connection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer();
                String str;
                while((str = reader.readLine())!= null){
                    sb.append(str);
                }
                System.out.println("res is       "+sb.toString());
//                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }
    }
}