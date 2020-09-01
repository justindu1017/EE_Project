package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.StringReader;

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

        final String joinuserNameSTR = joinuserName.getText().toString();
        final String joinpassWordSTR = joinpassWord.getText().toString();
        final String joinpassWord2STR = joinpassWord2.getText().toString();
        final String joineMailSTR = joineMail.getText().toString();
        
        joinMemberBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (joinuserNameSTR==""||joinpassWordSTR==""||joinpassWord2STR==""||joineMailSTR==""){
                    Toast.makeText(joinMember.this, "請確認欄位都已填寫",Toast.LENGTH_SHORT).show();
                }else{
                    // TODO: 2020/9/2 下email格式檢查 
                    
                    if (!joinpassWord.equals(joinpassWord2)){
                        Toast.makeText(joinMember.this, "請確認兩次密碼是否相同",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}