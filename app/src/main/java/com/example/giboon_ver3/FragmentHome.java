package com.example.giboon_ver3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentHome extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String manager = "zolpzoluv@gmail.com";
    private static final String TAG = "ChangeCampaign";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final SlidingDrawer slidingDrawer = (SlidingDrawer)view.findViewById(R.id.slidingdrawer);
        final Button handle = (Button)view.findViewById(R.id.handle);
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

        final Button slidingButton = (Button)view.findViewById(R.id.slidingButton);
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
