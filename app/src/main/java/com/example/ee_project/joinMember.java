package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;

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
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class joinMember extends AppCompatActivity {
    EditText joinuserName,joinpassWord,joinpassWord2,joineMail;
    Button joinMemberBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_member);
        joinuserName = findViewById(R.id.joinuserName);
        joinpassWord = findViewById(R.id.joinpassWord);
        joinpassWord2 = findViewById(R.id.joinpassWord2);
        joineMail = findViewById(R.id.joineMail);
        joinMemberBut = findViewById(R.id.joinMemberBut);


        
        joinMemberBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String joinuserNameSTR = joinuserName.getText().toString();
                String joinpassWordSTR = joinpassWord.getText().toString();
                String joinpassWord2STR = joinpassWord2.getText().toString();
                String joineMailSTR = joineMail.getText().toString();
                Boolean valia = false;
                if (joinuserNameSTR.equals("") || joinpassWordSTR.equals("") || joinpassWord2STR.equals("") || joineMailSTR.equals("")){
                    Toast.makeText(joinMember.this, "請確認欄位都已填寫",Toast.LENGTH_SHORT).show();
                }else{
                    if (!joinpassWordSTR.equals(joinpassWord2STR)){
                        Toast.makeText(joinMember.this, "請確認兩次密碼是否相同",Toast.LENGTH_SHORT).show();
                    }else{
                        if(!isValidEmailAddress(joineMailSTR)){
                            Toast.makeText(joinMember.this, "請確認郵件信箱格式", Toast.LENGTH_SHORT).show();
                        }else{
                            Load load = new Load();
                            load.execute(joinuserNameSTR, joinpassWordSTR, joineMailSTR);
                        }
                    }
                }
            }
        });
    }


    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }



    class  Load extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... str) {

            try {

                String joinUserName = str[0];
                String joinPassword = str[1];
                String joinEmail = str[2];

//                URL url = new URL("http://10.0.2.2/userRegister.php");

                URL url = new URL("https://eeprojectserver.herokuapp.com/userRegister.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("registUsername=").append(URLEncoder.encode(joinUserName, "UTF-8")).append("&");
                stringBuilder.append("registPassword=").append(URLEncoder.encode(joinPassword, "UTF-8")).append("&");
                stringBuilder.append("registMail=").append(URLEncoder.encode(joinEmail, "UTF-8"));

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

                    final JSONObject jsonObject = new JSONObject(sb.toString());
                    System.out.println(jsonObject);

                    if (jsonObject.getInt("result") == 1){
                        joinMember.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(joinMember.this, "註冊完成", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    else if(jsonObject.getInt("result") == 0){
                        final String errmsg = jsonObject.getString("ErrMsg");
                        joinMember.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(joinMember.this,errmsg, Toast.LENGTH_SHORT).show();
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