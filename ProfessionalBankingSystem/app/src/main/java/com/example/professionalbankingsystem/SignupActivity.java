package com.example.professionalbankingsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private EditText editEmail, editPassword;
    private Button btnSignup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(this::registerUser);
    }

    private void registerUser(View view) {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        User user = new User(email, userId, 0); // default balance 0
                        FirebaseDatabase.getInstance().getReference("users").child(userId).setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                    } else {
                        Toast.makeText(SignupActivity.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
