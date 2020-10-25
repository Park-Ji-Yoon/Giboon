package com.example.giboon_ver3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentIndividual extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "FragmentIndividual";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_individual, container, false);
        final TextView nameTv = (TextView)view.findViewById(R.id.nameTv);
//        nameTv.setText(mAuth.getCurrentUser().getDisplayName());
        TextView emailTv = (TextView)view.findViewById(R.id.emailTv);
        emailTv.setText(mAuth.getCurrentUser().getEmail());
        final TextView phoneTv = (TextView)view.findViewById(R.id.phoneTv);
//        phoneTv.setText(mAuth.getCurrentUser().getPhoneNumber());

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            nameTv.setText(document.getData().get("name").toString());
                            phoneTv.setText(document.getData().get("phone").toString());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return view;
    }
}