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
import java.util.ArrayList;

public class articalView extends AppCompatActivity {

    TextView articalVTitle, articalVContent,articalUserName, articalDate,answerVContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_view);
        articalVTitle = findViewById(R.id.articalVTitle);
        articalUserName = findViewById(R.id.articalUserName);
        answerVContent = findViewById(R.id.answerVContent);
        articalDate = findViewById(R.id.articalDate);
        articalVContent = findViewById(R.id.articalVContent);
        Intent intent = getIntent();
        String ArticaluserName = intent.getStringExtra("ArticaluserName");
        String Dates = intent.getStringExtra("Dates");
        String title = intent.getStringExtra("Title");
        int AID = intent.getIntExtra("AID", 00000);
//        Toast.makeText(this, String.valueOf(AID), Toast.LENGTH_LONG).show();
        articalDate.setText(Dates);
        articalUserName.setText(ArticaluserName);
        articalVTitle.setText(title);

        Load load = new Load();
        load.execute(AID);
    }




//    class Load extends AsyncTask<Integer,String,String>{
    class Load extends AsyncTask<Integer, ArrayList<String>,ArrayList<String>>{


        @Override
        protected ArrayList<String> doInBackground(Integer... ints) {
            String articalContent = null;
            ArrayList<String> returnArray = new ArrayList<>();
            try {

                int AID = ints[0];
                URL url = new URL("https://eeprojectserver.herokuapp.com/articalContent.php");
//                URL url = new URL("http://10.0.2.2/articalContent.php");
                System.out.println("in load AID is "+AID);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AID=").append(AID);

                dataOutputStream.writeBytes(stringBuilder.toString());
                dataOutputStream.flush();
                dataOutputStream.close();

                InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer();
                String string;
                while((string = reader.readLine())!= null){
                    sb.append(string);
                    System.out.println("it is "+sb);

                    final JSONObject jsonObject = new JSONObject(sb.toString());
//                    articalContent =  jsonObject.getString("articalContent");
                    returnArray.add(jsonObject.getString("articalContent"));
                    System.out.println("1 = "+jsonObject.getString("articalContent"));
                    returnArray.add(jsonObject.getString("hasAnswer"));
                    System.out.println("2 = "+jsonObject.getString("hasAnswer"));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return returnArray;
        }

        @Override
        protected void onPostExecute(final ArrayList<String> s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    articalVContent.setText(s.get(0));
                    if(s.get(1).equals("")){
                        answerVContent.setText("這個問題尚未被回答喔");
                    }else {
                        answerVContent.setText(s.get(1));
                    }
                }
            });
        }
    }
    }