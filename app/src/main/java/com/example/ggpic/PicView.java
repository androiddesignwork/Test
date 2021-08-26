package com.example.ggpic;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PicView extends AppCompatActivity {

    private View rootView;
    private String[] author;
    private String[] title;
    private TypedArray images;
    ArrayList<Pics> mdata = new ArrayList<Pics>();
    private RecyclerView mlist;
    private PicsAdapter adapter;
    private Context context;
    private String url;
    private Request request;
    private String body;
    private RefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_picview);
        context = getBaseContext();
        Intent intent = getIntent();
        url = intent.getStringExtra(
                "url");

        String img = intent.getStringExtra(
                "img");

        request = new Request.Builder()
                .url(url)
                .get()  //默认为GET请求，可以不写
                .build();

        initView();

        loadimage();

        Pics pic = new Pics();

        pic.setmPicUrl(img);

        adapter.add(pic);

//
/*        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void loadimage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        client.newCall(request).enqueue(callback);
                        TimeUnit.MILLISECONDS.sleep(500);
                        adapter.notifyDataSetChanged();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void initView() {
        mlist = findViewById(R.id.pic);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);//实现瀑布流效果

        mlist.setLayoutManager(layoutManager);

        adapter = new PicsAdapter(mdata, context);

        setonclicklistener();

        mlist.setAdapter(adapter);
    }

    private void setonclicklistener() {
        swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadimage();
                        swipe.setRefreshing(false);
                    }
                });

        swipe.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                loadimage();
                swipe.setLoading(false);
            }
        });

        adapter.setOnItemClickListener(
                new PicsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(PicView.this,
                                PicDetail.class);

                        Pics pic = adapter.getItem(position);

                        intent.putExtra("url",
                                pic.getmPicUrl());

                        onPause();

                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                }
        );
    }

    private okhttp3.Callback callback = new okhttp3.Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            if (response.isSuccessful()) {
                try {
                    body = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                Type jsonType =
                        new TypeToken<BaseResponse<List<Pics>>>() {
                        }.getType();
                BaseResponse<List<String>> picsListResponse =
                        gson.fromJson(body, jsonType);
                Pics pic = new Pics();
                pic.setmPicUrl(picsListResponse.getData());
                adapter.add(pic);
            }
        }
    };
}
