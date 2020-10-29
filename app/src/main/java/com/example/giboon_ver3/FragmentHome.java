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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class FragmentHome extends Fragment {
    private static final String TAG = "FragmentHome";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String manager = "zolpzoluv@gmail.com";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final SlidingDrawer slidingDrawer = (SlidingDrawer)view.findViewById(R.id.slidingdrawer);
        final Button handle = (Button)view.findViewById(R.id.handle);
        final Button slidingButton = (Button)view.findViewById(R.id.slidingButton);
        final TextView campaignTitle = (TextView)view.findViewById(R.id.textCampaignTitle);
        final TextView campaignMoney = (TextView)view.findViewById(R.id.textCampaignMoney);
        final TextView campaignInfo = (TextView)view.findViewById(R.id.textCampaignInfo);

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("campaigns").document("fr0SM2loLlUmGn45E8gM");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    campaignTitle.setText(documentSnapshot.getData().get("title").toString() + " 캠페인");
                    campaignMoney.setText(documentSnapshot.getData().get("money").toString() + "\\");
                    campaignInfo.setText(documentSnapshot.getData().get("contents").toString());
                    if (documentSnapshot.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
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
