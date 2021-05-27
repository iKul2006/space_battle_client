package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.model.Infrastructure;

public class Settings extends AppCompatActivity {
    EditText editName;
    Button btnEnterEditName;
    ImageButton btnExitFromSettings;
    ImageButton btnVk;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        editName = findViewById(R.id.editName);
        btnEnterEditName = findViewById(R.id.btnEnterEditName);
        btnExitFromSettings = findViewById(R.id.btnExitFromSettings);
        btnVk = findViewById(R.id.btnOpenLinkVk);
    }
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnExitFromSettings:
                finish();
                break;
            case R.id.btnEnterEditName:
                if (!Infrastructure.checkName(editName.getText().toString())) {
                    Toast toast = Toast.makeText(Settings.this, "Ник не изменен", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Infrastructure.player.name = editName.getText().toString();
                    Toast toast = Toast.makeText(Settings.this, "Ник изменен на: " + Infrastructure.player.name, Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
                break;
            case R.id.btnOpenLinkVk:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/ikul2006"));
                startActivity(i);
                break;
        }
    }
}
