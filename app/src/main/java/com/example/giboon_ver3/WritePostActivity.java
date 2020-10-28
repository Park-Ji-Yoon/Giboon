package com.example.giboon_ver3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class WritePostActivity extends AppCompatActivity {
    String TAG = "태그입니다 : ";
    FirebaseUser user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        findViewById(R.id.check).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.check:
                    profileUpdate();
                    break;
            }
        }
    };
    private void profileUpdate(){
        final String title = ((EditText)findViewById(R.id.titleEditText)).getText().toString();
        String Contents = ((EditText)findViewById(R.id.contentEditText)).getText().toString();
        final ArrayList<String> contentsList = new ArrayList<>();

        if(title.length() > 0 && Contents.length() > 0){
            user = FirebaseAuth.getInstance().getCurrentUser();
            db = FirebaseFirestore.getInstance();

            PostInfo postInfo = new PostInfo(title, contentsList, user.getUid(), new Date());
            uploader(postInfo);
        }else{
            startToast("제목과 내용을 모두 입력해주세요");
        }
    }
    private void uploader(PostInfo postInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(postInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error adding document", e);
                    }
                });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void startMyActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
