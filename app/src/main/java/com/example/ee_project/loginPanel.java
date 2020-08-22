package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class loginPanel extends AppCompatActivity {
    EditText loginAccount, loginPassword;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_panel);

        loginAccount = findViewById(R.id.loginAccount);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Load load = new Load();
                load.execute();
            }
        });


    }

    class Load extends AsyncTask <String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String inputAccount = loginAccount.getText().toString();
            String inputPassword = loginPassword.getText().toString();
            System.out.println("get Account "+inputAccount);
            System.out.println("get pass "+inputPassword);

            try {
                URL url = new URL("http://10.0.2.2/index.php");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("userName=").append(URLEncoder.encode(inputAccount,"UTF-8")).append("&");
                stringBuilder.append("passWord=").append(URLEncoder.encode(inputPassword,"UTF-8"));

                dataOutputStream.writeBytes(stringBuilder.toString());
                dataOutputStream.flush();
                dataOutputStream.close();

                InputStream is = new BufferedInputStream(connection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer();
                String str;
                while((str = reader.readLine())!= null){
                    sb.append(str);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}