package com.example.ggpic;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplorePic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class temp extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    private ExploreAdapter exploreadapter_left=null;
//    private ExploreAdapter exploreadapter_right=null;
    private List<Explorer> ListExplorer = new ArrayList<>();
    private Context context = null;//使用adapter
    private View rootView;//可使用findbyid
    private String[] text = null;//图片类型
    private TypedArray images;//图片封面
    ListView ex_left=null;
    ListView ex_right=null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public temp() {
        // Required empty public constructor
    }

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();

        rootView = inflater.inflate(R.layout.fragment_explorer_pic,
                container, false);

//        initView();

//        initData();

        return rootView;
    }
    /*    private void initView(){
     *//*左列*//*
        exploreadapter_left = new ExploreAdapter(context, R.layout.list_explore, ListExplorer);

        ex_left =rootView.findViewById(R.id.ex_left);

        ex_left.setAdapter(exploreadapter_left);

        *//*右列*//*
        exploreadapter_right = new ExploreAdapter(context, R.layout.list_explore, ListExplorer);

        ex_right = rootView.findViewById(R.id.ex_right);

        ex_right.setAdapter(exploreadapter_right);
    }*/

/*    private void initData(){
        text = getResources().getStringArray(R.array.text);
        images = getResources().obtainTypedArray(R.array.images);

        for (int i = 0; i < text.length; i++) {
            Explorer pic = new Explorer();
            pic.setText(text[i]);
            pic.setPicUrl(images.getResourceId(i, 0));
            exploreadapter_left.add(pic);
        }
    }*/
}