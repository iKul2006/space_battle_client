package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.myapplication.MainActivity.*;
import static com.example.myapplication.model.Infrastructure.player;

public class Registration extends AppCompatActivity {
    EditText nameEditText;
    Button btnEnterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        nameEditText = findViewById(R.id.nameEditText);
        btnEnterName = findViewById(R.id.btnEnterName);

        if (nameEditText.getText().equals("")) {
            btnEnterName.setEnabled(false);
        } else {
            btnEnterName.setEnabled(true);
        }
    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnEnterName:
                player.name = nameEditText.getText().toString();
                i = new Intent(Registration.this, MainActivity.class);
                startActivity(i);
                break;
        }
    }
}
