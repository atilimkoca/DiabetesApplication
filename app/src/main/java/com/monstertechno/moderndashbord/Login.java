package com.monstertechno.moderndashbord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
EditText etEmail, etPassword;
Button btnLogin;
ProgressBar progressBar;
ProgressBar progressBar2;
FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.Email);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar2 = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        
        TextView tvSignUp = findViewById(R.id.tvSignUp);
        TextView tvSignUp2 = findViewById(R.id.tvSignUp2);
        tvSignUp2.setOnClickListener(onClickSignUp());

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty()){
                    etEmail.setError("Email is required");
                    return;
                }
                if (password.isEmpty()){
                    etPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 6){
                    etPassword.setError("Password must be >= 6 characters");
                    return;
                }

                progressBar2.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else {
                        Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
    private View.OnClickListener onClickSignUp(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        };
    }
}