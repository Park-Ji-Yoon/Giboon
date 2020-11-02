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

import listener.OnNoticeListener;

public class FragmentNotice extends Fragment {
    private Object MainActivity;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    String TAG3 = "자유게시판 리스트";
    View view3 = null;
    NoticeAdapter noticeAdapter;
    private ArrayList<NoticeInfo> noticeList;
    FirebaseFirestore firebaseFirestore2;
    MainActivity mainActivity2;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view3 = inflater.inflate(R.layout.fragment_notice, container, false);
        firebaseFirestore2 = FirebaseFirestore.getInstance();

        // 글 추가하기 버튼
        FloatingActionButton addNoticeBtn = view3.findViewById(R.id.addNoticeBtn);
        addNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getContext(), WriteNoticeActivity.class);
                startActivity(intent2);
            }
        });

        noticeList = new ArrayList<>();
        noticeAdapter = new NoticeAdapter(FragmentNotice.this, noticeList);
        noticeAdapter.setOnNoticeListener(onNoticeListener);

        RecyclerView recyclerView2 = view3.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setAdapter(noticeAdapter);

        return view3;
    }

    OnNoticeListener onNoticeListener = new OnNoticeListener() {
        @Override
        public void onDelect2(String id) {
            Log.e("로그", "삭제" + id);
            firebaseFirestore2.collection("notices").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(mainActivity2.mContext, "글이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            noticeUpdate();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mainActivity2.mContext, "글 삭제에 실패했습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        @Override
        public void onModify2(String id) {
            Log.e("로그", "수정" + id);
            myStartActivity(WriteNoticeActivity.class, id);
        }
    };

    public void onResume(){
        super.onResume();
        noticeUpdate();
    }

    public void noticeUpdate(){
        CollectionReference collectionReference2 = database.collection("notices");
        collectionReference2
                .orderBy("createdAt2", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            noticeList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG3, document.getId() + " => " + document.getData());
                                noticeList.add(new NoticeInfo(
                                        document.getData().get("title2").toString(),
                                        document.getData().get("contents2").toString(),
                                        document.getData().get("publisher2").toString(),
                                        new Date(document.getDate("createdAt2").getTime()),
                                        document.getData().get("name2").toString(),
                                        document.getId()
                                ));
                            }
                            noticeAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG3, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void myStartActivity(Class c, String id){
        Intent intent = new Intent(getActivity(), c);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}