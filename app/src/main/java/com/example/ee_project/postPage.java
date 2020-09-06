package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
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
                URL url = new URL("http://10.0.2.2/postPage.php");
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
                stringBuilder.append("newTitle=").append(URLEncoder.encode(title, "UTF-8")).append("&");
                stringBuilder.append("newcontent=").append(URLEncoder.encode(content, "UTF-8"));

                dataOutputStream.writeBytes(stringBuilder.toString());
                dataOutputStream.flush();
                dataOutputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}