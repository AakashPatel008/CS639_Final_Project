package com.cs374.paceflex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResetActivity extends AppCompatActivity {
    private TextView info_reset;
    private Button back_signin1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        info_reset=findViewById(R.id.info_reset);
        info_reset.setMovementMethod(LinkMovementMethod.getInstance());
        back_signin1=findViewById(R.id.back_signin1);
        backtomain(back_signin1);
    }
    protected void backtomain(Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }

}