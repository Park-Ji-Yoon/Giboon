package com.example.giboon_ver3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

import listener.OnPostListener;

public class FragmentCampaign extends Fragment {
    private Object MainActivity;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "자유게시판 리스트";
    View view = null;
    MainAdapter mainAdapter;
    private ArrayList<PostInfo> postList;
    FirebaseFirestore firebaseFirestore;
    MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_campaign, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        // 게시판 리스트
//        db.collection("posts")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            ArrayList<PostInfo> postList = new ArrayList<>();
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                postList.add(new PostInfo(
//                                        document.getData().get("title").toString(),
//                                        document.getData().get("contents").toString(),
//                                        document.getData().get("publisher").toString(),
//                                        new Date(document.getDate("createdAt").getTime()),
//                                        document.getData().get("name").toString()
//                                        ));
//                            }
//                            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//                            recyclerView.setHasFixedSize(true);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//                            RecyclerView.Adapter mAdapter = new MainAdapter(FragmentCampaign.this, postList);
//                            recyclerView.setAdapter(mAdapter);
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

        // 글 추가하기 버튼
        FloatingActionButton addPostBtn = view.findViewById(R.id.addPostBtn);
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WritePostActivity.class);
                startActivity(intent);
            }
        });

        postList = new ArrayList<>();
        mainAdapter = new MainAdapter(FragmentCampaign.this, postList);
        mainAdapter.setOnPostListener(onPostListener);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mainAdapter);

        return view;
    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelect(String id) {
            Log.e("로그", "삭제" + id);
            firebaseFirestore.collection("posts").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(mainActivity.mContext, "글이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            postUpdate();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mainActivity.mContext, "글 삭제에 실패했습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        @Override
        public void onModify(String id) {
            Log.e("로그", "수정" + id);
        }
    };

    public void onResume(){
        super.onResume();
        postUpdate();
    }

    public void postUpdate(){
        CollectionReference collectionReference = db.collection("posts");
        collectionReference
                .orderBy("createdAt", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            postList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                postList.add(new PostInfo(
                                        document.getData().get("title").toString(),
                                        document.getData().get("contents").toString(),
                                        document.getData().get("publisher").toString(),
                                        new Date(document.getDate("createdAt").getTime()),
                                        document.getData().get("name").toString(),
                                        document.getId()
                                ));
                            }
                            mainAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}