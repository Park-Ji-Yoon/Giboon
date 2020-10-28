package com.example.giboon_ver3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentCampaign extends Fragment {
    private Object MainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campaign, container, false);

        // 게시판 리스트
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("test text1");
        arrayList.add("test text2");
        arrayList.add("test text3");
        System.out.println("arrayList");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView.Adapter mAdapter = new MainAdapter(this, arrayList);
        recyclerView.setAdapter(mAdapter);

        // 글 추가하기 버튼
        FloatingActionButton addPostBtn = view.findViewById(R.id.addPostBtn);
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WritePostActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}