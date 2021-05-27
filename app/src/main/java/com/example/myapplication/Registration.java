package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.Infrastructure;

import java.io.FileWriter;
import java.io.IOException;

import static com.example.myapplication.MainActivity.*;
import static com.example.myapplication.model.Infrastructure.player;

public class Registration extends AppCompatActivity {
    EditText nameEditText;
    Button btnEnterName;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        nameEditText = findViewById(R.id.nameEditText);
        btnEnterName = findViewById(R.id.btnEnterName);
    }

    public void onClick(View v) throws IOException {
        Intent i;
        switch (v.getId()) {
            case R.id.btnEnterName:
                if (!Infrastructure.checkName(nameEditText.getText().toString())) {
                    Toast toast = Toast.makeText(Registration.this, "Ник не должен содержать пробелы или быть пустым", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
                //FileWriter writer = new FileWriter(info);
                player.name = nameEditText.getText().toString();
                //writer.write(player.name);
                i = new Intent(Registration.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
