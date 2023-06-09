package com.cs374.paceflex;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button bSignIn;
    private EditText mEmail;
    private EditText mPassword;
    private TextView mregister;
    private TextView info_register;
    private TextView mForgotPassword;
    private Button breg;
    private ProgressDialog mDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
        }
        mDialog=new ProgressDialog(this);
        loginDetails();
    }
    private void loginDetails(){
        mEmail=findViewById(R.id.email_login);
        mPassword=findViewById(R.id.password_login);
        bSignIn=findViewById(R.id.btn_login);
        mForgotPassword=findViewById(R.id.forget_pass);
        mregister=findViewById(R.id.signup);
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String pass=mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email Required to login");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    mPassword.setError("Password Required to login");
                }
                mDialog.setMessage("Processiong...");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                            Toast.makeText(getApplicationContext(),"Login Sucessful...",Toast.LENGTH_SHORT).show();
                        }else{
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Login Failed ...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ResetActivity.class));
            }
        });
    }



}