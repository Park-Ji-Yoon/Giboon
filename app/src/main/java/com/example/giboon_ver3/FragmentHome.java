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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentHome extends Fragment {
    private static final String TAG = "FragmentHome";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String manager = "zolpzoluv@gmail.com";
    FirebaseUser user;
    MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final SlidingDrawer slidingDrawer = (SlidingDrawer) view.findViewById(R.id.slidingdrawer);
        final Button handle = (Button) view.findViewById(R.id.handle);
        final Button slidingButton = (Button) view.findViewById(R.id.slidingButton);
        final TextView campaignTitle = (TextView) view.findViewById(R.id.textCampaignTitle);
        final TextView campaignInfo = (TextView) view.findViewById(R.id.textCampaignInfo);
        final TextView campaignChk = (TextView) view.findViewById(R.id.textCampaignChk);
        final String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        setTextChk(userEmail, campaignChk, slidingButton);


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

        if (mAuth.getCurrentUser().getEmail().equals(manager)) {
            slidingButton.setText("새 캠페인 등록하기");
            campaignChk.setText("캠페인 시작한 지 " + NowDate() + "일 지남");
        }

        if (slidingButton.getText().toString().equals("오늘 참여")) {
            slidingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TodayDid(campaignChk, slidingButton);
                }
            });
        }

        if (slidingButton.getText().toString().equals("새 캠페인 등록하기")) {
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

    public void setTextChk(final String userEmail, final TextView campaignChk, final Button slidingButton){
        FirebaseFirestore.getInstance().collection("do").document(userEmail).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            DateClean();
                            campaignChk.setText(task.getResult().get("setTextChk").toString());
                            slidingButton.setText(task.getResult().get("setBtnChk").toString());
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                }else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    public static void DateClean(){
        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("do").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            if (document.getData().get("publisher").equals(documentReference.getId())){
                                System.out.println("========================================================================");
                                final String index = "chk" + NowDate();
                                final String forward = "chk" + (NowDate()-1);
                                if(document.get(index).equals(false)){
                                    documentReference.update("setTextChk", "TODAY    -    X");
                                    documentReference.update("setBtnChk", "오늘 참여");
                                    if(index.equals("chk1")){
                                        documentReference.update("chk28", false);
                                        documentReference.update("chk29", false);
                                        documentReference.update("chk30", false);
                                        documentReference.update("chk31", false);
                                    }else{
                                        documentReference.update(forward, false);
                                    }
                                }
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    public void TodayDid(final TextView campaignChk, final Button slidingButton) {
        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("do").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                            if (document.getData().get("publisher").equals(documentReference.getId())){
                                if(document.get("setBtnChk").equals("참여 완료")){
                                    Toast.makeText(mainActivity.mContext, "이미 참여한 캠페인입니다!!", Toast.LENGTH_LONG).show();
                                } else{
                                    final String index = "chk" + NowDate();
                                    documentReference.update(index, true);
                                    documentReference.update("setTextChk", "TODAY    -    O");
                                    documentReference.update("setBtnChk", "참여 완료");
                                    setTextChk(FirebaseAuth.getInstance().getCurrentUser().getEmail(), campaignChk, slidingButton);
                                    Toast.makeText(mainActivity.mContext, "캠페인 참여 완료!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Log.d(TAG, "No such document");
                            user = FirebaseAuth.getInstance().getCurrentUser();

                            CheckedInfo checkedInfo = new CheckedInfo(documentReference.getId(), "TODAY    -    O", "참여 완료",
                                    false, false, false, false, false, false, false, false,
                                    false, false, false, false,false, false, false, false,
                                    false, false, false,false,false, false, false, false,
                                    false, false, false, false,false, false, false);
                            checkedInfo.setIndex(NowDate());
                            checked(checkedInfo, campaignChk, slidingButton);
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    private void checked(final CheckedInfo checkedInfo, final TextView campaignChk, final Button slidingButton){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("do").document(checkedInfo.getPublisher()).set(checkedInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mainActivity.mContext, "캠페인 참여 완료!!", Toast.LENGTH_LONG).show();
                setTextChk(FirebaseAuth.getInstance().getCurrentUser().getEmail(), campaignChk, slidingButton);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error checking campaign", e);
            }
        });
    }

    public static int NowDate(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("d");
        return Integer.parseInt(simpleDate.format(mDate));
    }
}
