package com.example.ee_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ee_project.utility.ArticalListRecyclerView;
import com.example.ee_project.utility.Item;

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

public class ArticalListPage extends AppCompatActivity {

    Button button;
    RecyclerView recyclerView;
    String LoadFrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_list_page);
        loadData load = new loadData();
        LoadFrom = String.valueOf(0);
        load.execute(LoadFrom);
        button = findViewById(R.id.postPage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPostACT();
            }
        });
//        recyclerView = findViewById(R.id.recycleV);
////        ArticalListRecyclerView articalListRecyclerView = new ArticalListRecyclerView(ArticalListPage.this, );
////        recyclerView.setAdapter(articalListRecyclerView);
//        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

    }


    class loadData extends AsyncTask<String, String, ArrayList>{

        ArrayList<Item> contentArray = new ArrayList<>();
        @Override
        protected ArrayList doInBackground(String... strings) {

            String LoadFrom = strings[0];

            try {
                URL url = new URL("http://10.0.2.2/articalListxxx.php");
//                URL url = new URL("http://10.0.2.2/articalListxxx.php");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();
//    P
                stringBuilder.append("LoadFrom=").append(URLEncoder.encode(LoadFrom, "UTF-8"));
                System.out.println("uuu = "+stringBuilder.toString());
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
                    System.out.println("sb"+sb);
                    final JSONObject jsonObject = new JSONObject(sb.toString());

                    if(jsonObject.getInt("result") == 1){
                        System.out.println("enter if");
                        System.out.println("it is "+jsonObject.getJSONArray("Arr"));
                        for (int i = 0; i<jsonObject.getJSONArray("Arr").length();i++){
                            Item item = new Item(jsonObject.getJSONArray("Arr").getJSONArray(i).getInt(0), jsonObject.getJSONArray("Arr").getJSONArray(i).getString(1), jsonObject.getJSONArray("Arr").getJSONArray(i).getString(2), jsonObject.getJSONArray("Arr").getJSONArray(i).getString(3));
                            contentArray.add(item);
                        }
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println("content array is "+contentArray);
            return contentArray;
        }

        @Override
        protected void onPostExecute(final ArrayList arrayList) {
            System.out.println("from post is "+arrayList);
            ArticalListPage.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView = findViewById(R.id.recycleV);
                    ArticalListRecyclerView articalListRecyclerView = new ArticalListRecyclerView(ArticalListPage.this, arrayList);
                    recyclerView.setAdapter(articalListRecyclerView);
                    recyclerView.setLayoutManager((new LinearLayoutManager(ArticalListPage.this)));

                }
            });
        }
    }

    private void gotoPostACT(){
        Intent intent = new Intent(ArticalListPage.this, postPage.class);
        startActivity(intent);
    }
}