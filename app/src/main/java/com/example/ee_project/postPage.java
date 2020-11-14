package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class postPage extends AppCompatActivity {
    EditText content, title;
    Button postButtom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        content = findViewById(R.id.content);
        title = findViewById(R.id.title);
        postButtom = findViewById(R.id.postButtom);

        postButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(title.getText().toString().equals("")||content.getText().toString().equals("")){
                    Toast.makeText(postPage.this, "請確認所有內容皆已輸入" ,Toast.LENGTH_SHORT).show();
                }else {
                    Load load = new Load();
                    load.execute(title.getText().toString(), content.getText().toString());
                }
            }
        });
    }


    class Load extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... strings) {

            String title = strings[0];
            String content = strings[1];

            try {
//                URL url = new URL("http://10.0.2.2/articalMaker.php");

                URL url = new URL("https://eeprojectserver.herokuapp.com/articalMaker.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();

                String userName = getSharedPreferences("accountInfo", MODE_PRIVATE).getString("userName", "Err");
                String eMail = getSharedPreferences("accountInfo", MODE_PRIVATE).getString("eMail", "Err");

                stringBuilder.append("userName=").append(URLEncoder.encode(userName, "UTF-8")).append("&");
                stringBuilder.append("eMail=").append(URLEncoder.encode(eMail, "UTF-8")).append("&");
                stringBuilder.append("articalTitle=").append(URLEncoder.encode(title, "UTF-8")).append("&");
                stringBuilder.append("articalContent=").append(URLEncoder.encode(content, "UTF-8"));

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
                    System.out.println("ret = "+sb);
                    final JSONObject jsonObject = new JSONObject(sb.toString());
                    System.out.println(jsonObject);

                    if (jsonObject.getInt("result") == 1){
                        postPage.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(postPage.this, "發文成功", Toast.LENGTH_SHORT).show();
                                finish();
                                
//                                Intent intent = new Intent(postPage.this, ArticalListPage.class);
//                                startActivity(intent);
                            }
                        });
                    }
                    else if(jsonObject.getInt("result") == 0){
                        final String errmsg = jsonObject.getString("ErrMsg");
                        postPage.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(postPage.this,errmsg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}