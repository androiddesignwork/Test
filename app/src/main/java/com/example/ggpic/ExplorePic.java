package com.example.ggpic;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplorePic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplorePic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private List<Explorer> ListExplorer = new ArrayList<>();
    private Context context = null;//使用adapter
    private View rootView;//可使用findbyid
    private String[] text = null;//图片类型
    private TypedArray images;//图片封面
    ArrayList<Explorer> mdata=new ArrayList<Explorer>();

    // TODO: Rename and change types of parameters
    private RecyclerView mlist;
    private ExplorerAdapter adapter;
    private String[] uri;
    private String body;
    String url;
    Request request;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploerPic.
     */
    // TODO: Rename and change types and number of parameters
    public static ExplorePic newInstance(String param1, String param2) {
        ExplorePic fragment = new ExplorePic();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();

        rootView = inflater.inflate(R.layout.fragment_explorer_pic,
                container, false);

        initView();

        initData();

        setonclicklistener();

        return rootView;
    }
    private void initData(){
        uri=getResources().getStringArray(R.array.uri);
        for (int i = 0; i < uri.length; i++) {
            url=uri[i];
            request = new Request.Builder()
                    .url(url)
                    .get()  //默认为GET请求，可以不写
                    .build();
            getimage(i);
            adapter.notifyDataSetChanged();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView(){
        mlist = rootView.findViewById(R.id.explorer);

        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,1);//实现瀑布流效果

        mlist.setLayoutManager(layoutManager);

        adapter =new ExplorerAdapter(mdata,context);

        setonclicklistener();

        mlist.setAdapter(adapter);
    }

    private void setonclicklistener(){
        adapter.setOnItemClickListener(new ExplorerAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context,
                        PicView.class);

                intent.putExtra("url",
                        uri[position]);

                Explorer explorer = adapter.getItem(position);

                intent.putExtra("img",
                        explorer.getPicUrl());
                startActivity(intent);

//                onPause();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(context, "长按", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getimage(int i) {
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    body = response.body().string();
                    Gson gson = new Gson();
                    Type jsonType =
                            new TypeToken<BaseResponse<List<Explorer>>>() {
                            }.getType();
                    try {
                        BaseResponse<List<String>> explorersListResponse =
                                gson.fromJson(body, jsonType);
                        Explorer explorer = new Explorer();
                        explorer.setPicUrl(explorersListResponse.getData());
                        explorer.setText("uri"+i);
                        adapter.add(explorer);
                    }catch(Exception e){
                        e.printStackTrace();
                    }


                }
            }
        });
    }
}