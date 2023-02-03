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

public class Register extends AppCompatActivity {
EditText etName, etEmail, etPassword, etConfirmPassword;
Button btnRegister;
FirebaseAuth fAuth;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (Exception e){}
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.fullName);
        etEmail = findViewById(R.id.Email);
        etPassword = findViewById(R.id.password);
        etConfirmPassword = findViewById(R.id.confirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        TextView tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(onClickLogin());
//        if (fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                if (email.isEmpty()){
                    etEmail.setError("Email is required");
                    return;
                }
                if (password.isEmpty()){
                    etPassword.setError("Password is required");
                    return;
                }
                if (confirmPassword.isEmpty()){
                    etConfirmPassword.setError("Confirm Password is required");
                    return;
                }
                if (password.length() < 6){
                    etPassword.setError("Password must be >= 6 characters");
                    return;
                }
                if (!password.equals(confirmPassword)){
                    etConfirmPassword.setError("Password does not match");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else {
                        Toast.makeText(Register.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
    private View.OnClickListener onClickLogin(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        };
    }
}