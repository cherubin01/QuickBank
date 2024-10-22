package com.example.professionalbankingsystem;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TransactionHistoryActivity extends AppCompatActivity {
    private TextView txtHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        txtHistory = findViewById(R.id.txtHistory);
        // Retrieve and display transaction history here
    }
}
