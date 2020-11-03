package com.example.giboon_ver3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class ChangeCampaignActivity extends AppCompatActivity {
    String TAG = "태그입니다 : ";
    FirebaseUser user;
    FirebaseFirestore db;

    Button btnOk;
    Button btnCancel;
    EditText campaignTitle;
    EditText campaignInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_campaign);

        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        campaignTitle = findViewById(R.id.campaignTitle);
        campaignInfo = findViewById(R.id.campaignInfo);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCampaign();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                campaignTitle.setText("");
                campaignInfo.setText("");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                campaignTitle.setText("");
                campaignInfo.setText("");
            }
        });
    }
    private void changeCampaign(){
        final String title = ((EditText)findViewById(R.id.campaignTitle)).getText().toString();
        String contents = ((EditText)findViewById(R.id.campaignInfo)).getText().toString();

        if(title.length() > 0 && contents.length() > 0){
            user = FirebaseAuth.getInstance().getCurrentUser();
            db = FirebaseFirestore.getInstance();

            ChangeInfo changeInfo = new ChangeInfo(title, contents, user.getUid(), new Date());
            change(changeInfo);
        }else{
            startToast("제목과 내용을 모두 입력해주세요");
        }
    }
    private void change(final ChangeInfo changeInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("campaigns").add(changeInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, documentReference.getId());
                        changeInfo.setCount(changeInfo.getCount() + 1);
                        startToast("새로운 캠페인이 등록되었습니다.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error changing campaign", e);
                    }
                });
    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}