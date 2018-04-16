package com.cc.activitycustomanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
    }

    public void intentB(View view) {
        startActivity(new Intent(this,ActivityB.class));
    }

    public void intentC(View view) {
        startActivity(new Intent(this,ActivityC.class));
    }
}
