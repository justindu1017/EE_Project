package com.example.ee_project;

import androidx.annotation.NonNull;
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
import java.util.concurrent.ExecutionException;

public class ArticalListPage extends AppCompatActivity {

    Button button;
    RecyclerView recyclerView;
    String LoadFrom;
    ArrayList<Item> contentArray = new ArrayList<>();
    ArticalListRecyclerView articalListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_list_page);
        final loadData load = new loadData();
        LoadFrom = String.valueOf(0);
        try {
            load.execute(LoadFrom).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        button = findViewById(R.id.postPage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPostACT();
            }
        });

        recyclerView = findViewById(R.id.recycleV);
        articalListRecyclerView = new ArticalListRecyclerView(ArticalListPage.this, contentArray);
        recyclerView.setAdapter(articalListRecyclerView);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ArticalListPage.this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Boolean loading = true;

                if (dy > 0) {
                    System.out.println("dududu");
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            System.out.println("tell end!!!!!");
                            LoadFrom = String.valueOf(Integer.parseInt(LoadFrom) + 20);
                            System.out.println("Load from " + LoadFrom);
                            loadData LoadMore = new loadData();
                            try {
                                LoadMore.execute(LoadFrom).get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            articalListRecyclerView.notifyDataSetChanged();

                        }
                        loading = false;
                        System.out.println("Boolean is " + loading);
                    }
                }
            }
        });

        System.out.println("outside is " + contentArray);
    }


    @Override
    protected void onResume() {
        super.onResume();
        contentArray.clear();
//        Toast.makeText(this, "resss", Toast.LENGTH_LONG).show();
        loadData load = new loadData();
        LoadFrom = String.valueOf(0);
        try {
            load.execute(LoadFrom).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        articalListRecyclerView.notifyDataSetChanged();
        System.out.println("onRes = " + contentArray);
    }

    class loadData extends AsyncTask<String, String, Void> {

//        ArrayList<Item> contentArray = new ArrayList<>();

        @Override
        protected Void doInBackground(String... strings) {

            String LoadFrom = strings[0];

            try {

                URL url = new URL("https://eeprojectserver.herokuapp.com/articalList.php");
//                URL url = new URL("http://10.0.2.2/articalList.php");
//                URL url = new URL("http://10.0.2.2/articalListxxx.php");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                StringBuilder stringBuilder = new StringBuilder();
//    P
                stringBuilder.append("LoadFrom=").append(URLEncoder.encode(LoadFrom, "UTF-8"));
                System.out.println("uuu = " + stringBuilder.toString());
                dataOutputStream.writeBytes(stringBuilder.toString());
                dataOutputStream.flush();
                dataOutputStream.close();
                InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer();
                String string;

                while ((string = reader.readLine()) != null) {
                    sb.append(string);
                    System.out.println("sb" + sb);
                    final JSONObject jsonObject = new JSONObject(sb.toString());

                    if (jsonObject.getInt("result") == 1) {
//                        System.out.println("enter if");
//                        System.out.println("it is " + jsonObject.getJSONArray("Arr"));
                        for (int i = 0; i < jsonObject.getJSONArray("Arr").length(); i++) {
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

//            System.out.println("content array is " + contentArray);
            return null;
        }

//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            System.out.println("from post is " + contentArray);
//            ArticalListPage.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
////                    recyclerView = findViewById(R.id.recycleV);
////                    ArticalListRecyclerView articalListRecyclerView = new ArticalListRecyclerView(ArticalListPage.this, contentArray);
////                    recyclerView.setAdapter(articalListRecyclerView);
////                    recyclerView.setLayoutManager((new LinearLayoutManager(ArticalListPage.this)));
//                    recyclerView = findViewById(R.id.recycleV);
//                    ArticalListRecyclerView articalListRecyclerView = new ArticalListRecyclerView(ArticalListPage.this, contentArray);
//                    recyclerView.setAdapter(articalListRecyclerView);
//                    final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ArticalListPage.this);
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                        @Override
//                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                            super.onScrolled(recyclerView, dx, dy);
//                            int visibleItemCount = layoutManager.getChildCount();
//                            int totalItemCount = layoutManager.getItemCount();
//                            int pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//                            Boolean loading = true;
//
//                            if (dy > 0) {
//                                if (loading) {
//                                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                                        LoadFrom = String.valueOf(Integer.parseInt(LoadFrom) + 10);
//                                        System.out.println("Load from " + LoadFrom);
//                                        System.out.println("tell end!!!!!");
////                                        loadData load = new loadData();
////                                        load.execute(LoadFrom);
//                                        // TODO: 2020/11/15 LoadMore there are two ways 1.create a new Thread, 2.dont use onpostexecute
//                                    }
//                                    loading = false;
//                                }
//                            }
//
//                        }
//                    });
//                }
//            });
//        }
    }


    private void gotoPostACT() {
        Intent intent = new Intent(ArticalListPage.this, postPage.class);
        startActivity(intent);

    }
}