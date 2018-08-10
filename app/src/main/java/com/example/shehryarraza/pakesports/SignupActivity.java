package com.example.shehryarraza.pakesports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        setContentView(R.layout.activity_signup);

        editText =  findViewById(R.id.fullname);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this, MainActivity.class));

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
