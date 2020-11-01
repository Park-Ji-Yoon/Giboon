package com.example.giboon_ver3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        TextView signup = (TextView) findViewById(R.id.signUp);
        signup.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(signupIntent);
            }
        });

        findViewById(R.id.signIn).setOnClickListener(onClickLisener);
//        findViewById(R.id.pwResetBtn).setOnClickListener(onClickLisener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickLisener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.signIn:
                    Log.e("클릭", "클릭");
                    signUp();
                    break;
                case R.id.pwResetBtn:
                    startMyActivity(ResetPasswordActivity.class);
                    break;
            }
        }
    };
    private void signUp(){
        String email = ((EditText)findViewById(R.id.idText)).getText().toString();
        String password = ((EditText)findViewById(R.id.pwText)).getText().toString();

        if(email.length() > 0 && password.length() > 0){
            final RelativeLayout loaderLayout = findViewById(R.id.loaderLayout);
            loaderLayout.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            loaderLayout.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인에 성공하였습니다");
                                startMyActivity(MainActivity.class);
                                startBundle();
                            } else {
                                if(task.getException() != null){
                                    startToast("이메일 또는 비밀번호가 일치하지 않습니다");
                                }
                            }
                        }
                    });
        }else{
            startToast("빈 칸을 채워주세요");
        }
    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void startMyActivity(Class c){
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void startBundle(){
        FragmentIndividual myFragment = new FragmentIndividual();
        Bundle bundle = new Bundle(1); // 파라미터의 숫자는 전달하려는 값의 갯수
        bundle.putString("key", "value");
        myFragment.setArguments(bundle);
    }
}