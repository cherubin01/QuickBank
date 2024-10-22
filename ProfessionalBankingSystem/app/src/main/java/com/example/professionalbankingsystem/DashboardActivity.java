package com.example.professionalbankingsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {
    private TextView txtBalance;
    private Button btnDeposit, btnWithdraw, btnViewHistory;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        txtBalance = findViewById(R.id.txtBalance);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        btnViewHistory = findViewById(R.id.btnViewHistory);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        btnDeposit.setOnClickListener(this::deposit);
        btnWithdraw.setOnClickListener(this::withdraw);
        btnViewHistory.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, TransactionHistoryActivity.class)));

        fetchUserBalance();
    }

    private void fetchUserBalance() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    txtBalance.setText("Balance: $" + user.getBalance());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DashboardActivity.this, "Failed to load balance.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deposit(View view) {
        double amount = 100; // Example amount, implement a dialog to get user input
        databaseReference.child("balance").setValue(ServerValue.increment(amount))
                .addOnSuccessListener(aVoid -> Toast.makeText(DashboardActivity.this, "Deposited $" + amount, Toast.LENGTH_SHORT).show());
    }

    private void withdraw(View view) {
        double amount = 50; // Example amount, implement a dialog to get user input
        databaseReference.child("balance").setValue(ServerValue.increment(-amount))
                .addOnSuccessListener(aVoid -> Toast.makeText(DashboardActivity.this, "Withdrew $" + amount, Toast.LENGTH_SHORT).show());
    }
}
