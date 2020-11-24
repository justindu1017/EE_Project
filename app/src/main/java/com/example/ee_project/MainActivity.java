package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText loginAccount, loginPassword;
    Button loginButton, joinButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean LoginCheck = getSharedPreferences("accountInfo", MODE_PRIVATE).getBoolean("isLogin", false);
        if(LoginCheck){
            gotoListACT();
            finish();
        }

        loginAccount = findViewById(R.id.loginAccount);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        joinButton = findViewById(R.id.joinButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Load load = new Load();
                load.execute();
            }
        });


        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoJoinACT();
            }
        });


    }

    class Load extends AsyncTask <String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String inputAccount = loginAccount.getText().toString();
            String inputPassword = loginPassword.getText().toString();
            inputPassword = "fvgbhjnkgbhjhdc"+inputPassword+"suclihmlnurgfb";
            try {
//                URL url = new URL("http://10.0.2.2/index.php");
                URL url = new URL("https://eeprojectserver.herokuapp.com");
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
                    System.out.println("articalContent = "+sb);
                    final JSONObject jsonObject = new JSONObject(sb.toString());
//                    System.out.println(jsonObject);
//                    String result = jsonObject.getString("result");

                    if (jsonObject.getInt("result") == 1){
                        String UID = jsonObject.getString("UID");
                        String userName = jsonObject.getString("userName");
//                        String passWord = jsonObject.getString("passWord");
                        String eMail = jsonObject.getString("eMail");

                        SharedPreferences sharedPreferences = getSharedPreferences("accountInfo",MODE_PRIVATE);

                        sharedPreferences.edit()
                                .putBoolean("isLogin", true)
                                .putString("UID", UID)
                                .putString("userName", userName)
//                                .putString("passWord", passWord)
                                .putString("eMail", eMail)
                                .commit();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            }
                        });

                        gotoListACT();

                        finish();

                    }
                    else if(jsonObject.getInt("result") == 0){
                        final String errmsg = jsonObject.getString("ErrMsg");
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, errmsg, Toast.LENGTH_SHORT).show();
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



    private void gotoJoinACT(){
        Intent intent = new Intent(MainActivity.this, joinMember.class);
        startActivity(intent);
    }

    private void gotoListACT(){
        Intent intent = new Intent(MainActivity.this, ArticalListPage.class);
        startActivity(intent);
    }
}