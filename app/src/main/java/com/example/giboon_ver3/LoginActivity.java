package com.example.giboon_ver3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

    private TextView textView6;
    private TextView textView7;
    private EditText idText;
    private EditText textPassword;
    private Button signIn;
    private CheckBox cb_save;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT1 = "text1";
    public static final String TEXT2 = "text2";
    public static final String CHECKBOX1 = "CHECKBOX1";

    private String tt1;
    private String tt2;
    private boolean onOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        idText = (EditText) findViewById(R.id.idText);
        textPassword = (EditText) findViewById(R.id.pwText);
        signIn = (Button) findViewById(R.id.signIn);
        cb_save = (CheckBox) findViewById(R.id.cb_save);

        TextView signup = (TextView) findViewById(R.id.signUp);
        signup.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(signupIntent);
            }
        });

        findViewById(R.id.signIn).setOnClickListener(onClickLisener);

        loadData();
        updateViews();
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
                    if(cb_save.isChecked()){
                        saveData();
                    }
                    break;
                case R.id.pwResetBtn:
                    startMyActivity(ResetPasswordActivity.class);
                    break;
            }
        }
    };
    private void signUp(){
        textView6.setText(idText.getText().toString());
        textView7.setText(textPassword.getText().toString());

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
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT1, textView6.getText().toString());
        editor.putString(TEXT2, textView7.getText().toString());
        editor.putBoolean(CHECKBOX1, cb_save.isChecked());

        editor.apply();
        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        tt1 = sharedPreferences.getString(TEXT1, "");
        tt2 = sharedPreferences.getString(TEXT2, "");
        onOff = sharedPreferences.getBoolean(CHECKBOX1, false);
    }
    public void updateViews(){
        textView6.setText(tt1);
        textView7.setText(tt2);
        idText.setText(tt1);
        textPassword.setText(tt2);
        cb_save.setChecked(onOff);
    }
}