package com.example.giboon_ver3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentHome extends Fragment {
    private static final String TAG = "FragmentHome";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String manager = "zolpzoluv@gmail.com";
    CollectionReference campaignslast = FirebaseFirestore.getInstance().collection("campaigns");


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final SlidingDrawer slidingDrawer = (SlidingDrawer)view.findViewById(R.id.slidingdrawer);
        final Button handle = (Button)view.findViewById(R.id.handle);
        final Button slidingButton = (Button)view.findViewById(R.id.slidingButton);
        final TextView campaignTitle = (TextView)view.findViewById(R.id.textCampaignTitle);
        final TextView campaignInfo = (TextView)view.findViewById(R.id.textCampaignInfo);

        Task<QuerySnapshot> querySnapshotTask = FirebaseFirestore.getInstance().collection("campaigns").get();
        querySnapshotTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    campaignTitle.setText(task.getResult().getDocuments().get(ChangeInfo.getCount()).get("title").toString() + " 캠페인");
                    campaignInfo.setText(task.getResult().getDocuments().get(ChangeInfo.getCount()).get("contents").toString());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            public void onDrawerOpened() {
                handle.setText("▼");
            }
        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            public void onDrawerClosed() {
                handle.setText("△");
            }
        });

        if(mAuth.getCurrentUser().getEmail().equals(manager)){
            slidingButton.setText("새 캠페인 등록하기");
        }

        if(slidingButton.getText().toString().equals("새 캠페인 등록하기")){
            slidingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ChangeCampaignActivity.class);
                    startActivity(intent);
                }
            });
        }
        return view;
    }
}
