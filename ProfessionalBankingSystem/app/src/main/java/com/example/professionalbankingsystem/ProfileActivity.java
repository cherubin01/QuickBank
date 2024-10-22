package com.example.professionalbankingsystem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private EditText editEmail;
    private Button btnUpdate;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        editEmail = findViewById(R.id.editEmail);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(v -> updateEmail());
    }

    private void updateEmail() {
        String newEmail = editEmail.getText().toString();
        mAuth.getCurrentUser().updateEmail(newEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        databaseReference.child("email").setValue(newEmail);
                        Toast.makeText(ProfileActivity.this, "Email updated successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Email update failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
