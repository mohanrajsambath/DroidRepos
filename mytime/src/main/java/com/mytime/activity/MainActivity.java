package com.mytime.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.mytime.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
private Button button_In_Time,button_Out_Time,button_punch;
    TextView txtVw_val_status;
    private boolean is_PunchIn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiviews();
    }

    private void intiviews() {
        button_In_Time =(Button)findViewById(R.id.button_In_Time);
        button_Out_Time =(Button)findViewById(R.id.button_Out_Time);
        button_punch =(Button)findViewById(R.id.button_punch);
        txtVw_val_status =(TextView)findViewById(R.id.txtVw_val_status);

        button_punch.setOnClickListener(this);
        TextClock textClock = (TextClock) findViewById(R.id.textClock);
        textClock.setFormat12Hour(null);
        //textClock.setFormat24Hour("dd/MM/yyyy hh:mm:ss a");
        textClock.setFormat24Hour("hh:mm:ss a  EEE MMM d");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_punch:
                if(is_PunchIn){
                getCurrentTime("Check-IN");
                is_PunchIn=false;
                }
                else {
                getCurrentTime("Check-OUT");
                is_PunchIn=true;
                }
                break;
        }
    }

    public void getCurrentTime(String punchtime) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());
        //String mPunchStatus=
        display(strDate,punchtime);
    }

    private void display(String num, String punchtime) {
        txtVw_val_status.setText(num);
        button_punch.setText(punchtime);
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    //TextView txtCurrentTime= (TextView)findViewById(R.id.myText);
                    Date dt = new Date();
                    int hours = dt.getHours();
                    int minutes = dt.getMinutes();
                    int seconds = dt.getSeconds();
                    String curTime = hours + ":" + minutes + ":" + seconds;
                    txtVw_val_status.setText(curTime);
                }catch (Exception e) {e.printStackTrace();}
            }
        });
    }
}
