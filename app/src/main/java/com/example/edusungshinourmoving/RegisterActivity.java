package com.example.edusungshinourmoving;

import android.content.Intent;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;






public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editTextEmail; //이메일 입력
    private EditText editTextPassword; //비밀번호 입력


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.edit_email);
        editTextPassword = (EditText) findViewById(R.id.edit_pw);

        Button signinbutton = (Button) findViewById(R.id.join1); //회원가입 버튼


        signinbutton.setOnClickListener(new View.OnClickListener() { //회원가입 버튼
            @Override
            public void onClick(View view) {
                createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(RegisterActivity.this,MainActivity2.class);
                            startActivity(i);
                        }

                        // ...
                    }
                });
    }

}