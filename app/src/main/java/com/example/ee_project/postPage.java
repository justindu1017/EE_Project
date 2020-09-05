package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                Load load = new Load();
                load.execute();
            }
        });
    }


    class Load extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}