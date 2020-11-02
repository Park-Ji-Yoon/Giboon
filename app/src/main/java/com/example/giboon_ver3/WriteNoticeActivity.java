package com.example.giboon_ver3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class WriteNoticeActivity extends AppCompatActivity {
    String TAG2 = "태그입니다 : ";
    FirebaseUser user2;
    FirebaseFirestore db2;
    String name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notice);

        findViewById(R.id.check2).setOnClickListener(onClickListener);

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG2, "DocumentSnapshot data: " + document.getData());
                            name2 = (document.getData().get("name").toString());
                            System.out.println(document.getData().get("name").toString() +  " " + document.getData().get("phone").toString());
                        } else {
                            Log.d(TAG2, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG2, "get failed with ", task.getException());
                }
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.check2:
                    profileUpdate();
                    FragmentNotice fragmentNotice = new FragmentNotice();
                    replaceFragment(fragmentNotice);
                    break;
            }
        }
    };
    private void profileUpdate(){
        final String title2 = ((EditText)findViewById(R.id.titleEditText2)).getText().toString();
        String contents2 = ((EditText)findViewById(R.id.contentEditText2)).getText().toString();
//        final String contentsList = new ArrayList<>();

        if(title2.length() > 0 && contents2.length() > 0){
            user2 = FirebaseAuth.getInstance().getCurrentUser();
            db2 = FirebaseFirestore.getInstance();
            NoticeInfo noticeInfo = new NoticeInfo(title2, contents2, user2.getUid(), new Date(), name2);

            String id = getIntent().getStringExtra("id");
            DocumentReference dr;
/*            Log.e("id : ", id);*/
            if(id == null){
                dr = db2.collection("notices").document();
            }else{
                dr = db2.collection("notices").document(id);
            }
            final DocumentReference documentReference = dr;
            uploader(noticeInfo);
        }else{
            startToast("제목과 내용을 모두 입력해주세요");
        }
    }
    private void uploader(NoticeInfo noticeInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notices").add(noticeInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG2, documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG2, "Error adding document", e);
                    }
                });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager2 = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
        fragmentTransaction.replace(R.id.activity_write_notice_lin, fragment);
        fragmentTransaction.commit();
    }
}
