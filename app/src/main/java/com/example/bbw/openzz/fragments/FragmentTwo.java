package com.example.bbw.openzz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bbw.openzz.Model.Gank.Gank;
import com.example.bbw.openzz.R;
import com.example.bbw.openzz.activity.PicDetail;
import com.example.bbw.openzz.adapter.GankAdapter;
import com.example.bbw.openzz.util.HttpUntil;
import com.example.bbw.openzz.util.ResponseHandleUtility;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.bbw.openzz.api.GankApi.gank;


/**
 * Created by bbw on 2017/10/21.
 * @author bibingwei
 * Gank
 */

public class FragmentTwo extends Fragment {

    private List<Gank.results> responsePicList;
    private List<Gank.results> showPicList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPic(gank);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_two,container,false);
        RecyclerView mRecyclerView = mView.findViewById(R.id.fragment_recyclerView);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        GankAdapter picAdapter = new GankAdapter(showPicList,getContext());
        mRecyclerView.setAdapter(picAdapter);
        picAdapter.setClickListener(new GankAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(),PicDetail.class);
                //暂定
                intent.putExtra("picId",showPicList.get(position).getUrl());
                startActivity(intent);
            }
        });
        picAdapter.notifyDataSetChanged();
        return mView;
    }

    private void requestPic(String address) {

        HttpUntil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(),"404.....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                initPic(responseText);
            }
        });
    }

    private void initPic(String responseText) {
        try {
            responsePicList = ResponseHandleUtility.handleGankPic(responseText);
            showPicList.clear();
            for (int i =0;i<responsePicList.size();i++){
                Gank.results pic = new Gank.results(responsePicList.get(i).getUrl());
                showPicList.add(pic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
