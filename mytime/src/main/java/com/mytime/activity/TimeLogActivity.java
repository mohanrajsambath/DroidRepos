package com.mytime.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.mytime.R;
import com.mytime.utils.Stopwatch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeLogActivity extends AppCompatActivity  implements View.OnClickListener{
    TextView txtVw_val_status;
    Stopwatch mSw=null;
    private Button button_In_Time,button_Out_Time,button_punch;
    private boolean isChecked =false,isLogin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelog);
        intiViews();
        //initThread();


    }

    private void initThread() {
            Thread myThread = null;
            Runnable myRunnableThread = new CountDownRunner();
            myThread = new Thread(myRunnableThread);
            myThread.start();
    }

    private void intiViews() {
        mSw= new Stopwatch();
        button_In_Time = findViewById(R.id.button_In_Time);
        button_Out_Time = findViewById(R.id.button_Out_Time);
        button_punch = findViewById(R.id.button_punch);
        txtVw_val_status = findViewById(R.id.txtVw_val_status);

        button_In_Time.setOnClickListener(this);
        button_Out_Time.setOnClickListener(this);
        button_punch.setOnClickListener(this);
        TextClock textClock = findViewById(R.id.textClock);
        textClock.setFormat12Hour(null);
        //textClock.setFormat24Hour("dd/MM/yyyy hh:mm:ss a");
        textClock.setFormat24Hour("hh:mm:ss a  EEE MMM d");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_In_Time:
                if(!isLogin) {
                    initThread();
                    getCurrentTime("Check-OUT");
                    isChecked = true;
                    isLogin = true;
                    mSw.start();
                    //txtVw_val_status.setText(""+mSw.toString());

                }
                break;
            case R.id.button_punch:
                if(isLogin) {
                    if (isChecked) {
                        Thread.currentThread().interrupt();
                        getCurrentTime("Check-IN");
                        isChecked = false;
                        mSw.pause();
                    } else {
                        getCurrentTime("Check-OUT");
                        isChecked = true;
                        mSw.resume();
                    }
                }else{
                    Toast.makeText(TimeLogActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.button_Out_Time:
                if(isLogin) {
                    Thread.currentThread().interrupt();
                    getCurrentTime("Check-IN");
                    isChecked = false;
                    isLogin = false;
                    mSw.stop();
                }
                break;
        }
    }

    public void getCurrentTime(String punchtime) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());
        display(strDate,punchtime);
    }

    private void display(String num, String punchtime) {
        if(isLogin) {
            txtVw_val_status.setText(num);
        }else{
            txtVw_val_status.setText("");
        }
        button_punch.setText(punchtime);
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    Date dt = new Date();
                    int hours = dt.getHours();
                    int minutes = dt.getMinutes();
                    int seconds = dt.getSeconds();
                    String curTime = hours + ":" + minutes + ":" + seconds;
                    //txtVw_val_status.setText(curTime);
                    txtVw_val_status.setText(mSw.toString());
                }catch (Exception e) {e.printStackTrace();}
            }
        });
    }


    public class CountDownRunner implements Runnable{
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(10); // Pause of 1 Second
                    //Thread.sleep(1000); // Pause of 1 Second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                    throw e;
                }
            }
        }
    }
}
