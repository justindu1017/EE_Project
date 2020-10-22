package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class articalView extends AppCompatActivity {

    TextView articalVTitle, articalVContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_view);
        articalVTitle = findViewById(R.id.articalVTitle);
        articalVContent = findViewById(R.id.articalVContent);
        Intent intent = getIntent();

        int AID = intent.getIntExtra("AID", 00000);
        Toast.makeText(this, String.valueOf(AID), Toast.LENGTH_LONG).show();

        Load load = new Load();
        load.execute(AID);
    }

    class Load extends AsyncTask<Integer,String,String>{


        @Override
        protected String doInBackground(Integer... ints) {

            try {
                int AID = ints[0];
                URL url = new URL("http://10.0.2.2/articalContent.php");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AID=").append(URLEncoder.encode(String.valueOf(AID), "UTF-8")).append("&");
                stringBuilder.append("AID=").append(URLEncoder.encode(String.valueOf(AID), "UTF-8"));

                dataOutputStream.writeBytes(stringBuilder.toString());
                dataOutputStream.flush();
                dataOutputStream.close();

                // TODO: 2020/9/3 ready to get input String

                InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer();
                String string;
                while((string = reader.readLine())!= null){
                    sb.append(string);
                    System.out.println("it is "+sb);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            catch (JSONException e) {
//                e.printStackTrace();
//            }
            return null;
        }
        }
    }