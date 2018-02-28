package com.cc.battery_monitoring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Context Context;
    TextView textView1,textView2,battery_percentage;
    ImageView image,imgVw_BatteryStatus;
    Animation animationBlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animationBlink = AnimationUtils.loadAnimation(this, R.anim.blink);

        image = (ImageView)findViewById(R.id.battery);
        imgVw_BatteryStatus = (ImageView)findViewById(R.id.imgVw_BatteryStatus);

        // Get the application context
        Context = getApplicationContext();

        // Initialize a new IntentFilter instance
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        // Register the broadcast receiver
        Context.registerReceiver(mBroadcastReceiver,iFilter);

        // Get the widgets reference from XML main
        battery_percentage = (TextView) findViewById(R.id.battey_percentage);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);

    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String charging_status="",battery_condition="",power_source="Unplugged";

            // Get the battery percentage
            int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);

            // Get the battery condition
            int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);

            if(health == BatteryManager.BATTERY_HEALTH_COLD)
            {
                battery_condition = "Cold";
            }
            if (health == BatteryManager.BATTERY_HEALTH_DEAD)
            {
                battery_condition = "Dead";
            }
            if (health == BatteryManager.BATTERY_HEALTH_GOOD)
            {
                battery_condition = "Good";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT)
            {
                battery_condition = "Over Heat";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE)
            {
                battery_condition = "Over Voltage";
            }
            if(health == BatteryManager.BATTERY_HEALTH_UNKNOWN)
            {
                battery_condition = "Unknown";
            }
            if(health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE)
            {
                battery_condition = "Unspecified failure";
            }

            // Get the battery temperature in celcius
            int  temperature_c= (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0))/10;

            //  Celsius to Fahrenheit battery temperature conversion
            int temperature_f = (int)(temperature_c*1.8+32);

            // Get the battery power source
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            if(chargePlug == BatteryManager.BATTERY_PLUGGED_USB)
            {
                power_source = "USB";
            }
            if(chargePlug == BatteryManager.BATTERY_PLUGGED_AC)
            {
                power_source = "AC Adapter";
            }
            if(chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS)
            {
                power_source = "Wireless";
            }

            // Get the status of battery Eg. Charging/Discharging
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            if(status == BatteryManager.BATTERY_STATUS_CHARGING)
            {
                charging_status = "Charging";
                image.startAnimation(animationBlink);
            }

            if(status == BatteryManager.BATTERY_STATUS_DISCHARGING)
            {
                charging_status = "Not Charging";
                image.clearAnimation();
                switch (level){
                    case 10:
                        break;
                    case 20:
                        break;
                    case 30:
                        break;
                    case 40:
                        break;
                    case 50:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_50_black_24dp);
                        break;
                    case 80:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_80_black_24dp);
                        break;
                    case 90:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_90_black_24dp);
                        break;
                    case 100:break;
                    default:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_alert_black_24dp);
                        break;
                }
            }

            if (status == BatteryManager.BATTERY_STATUS_FULL)
            {
                charging_status = "Battery Full";
            }

            if(status == BatteryManager.BATTERY_STATUS_UNKNOWN)
            {
                charging_status = "Unknown";
            }

            if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING)
            {
                charging_status = "Not Charging";
            }

            // Get the battery technology
            String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

            // Get the battery voltage
            int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

            //Display the output of battery Status
            battery_percentage.setText("Battery Percentage: "+level+"%");

            if(status == BatteryManager.BATTERY_STATUS_CHARGING)
            {
                charging_status = "Charging";
                image.startAnimation(animationBlink);

                switch (level){
                    case 10:
                        break;
                    case 20:
                        break;
                    case 30:
                        break;
                    case 40:
                        break;
                    case 50:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_charging_50_black_24dp);
                        break;
                    case 60:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_charging_60_black_24dp);
                        break;
                    case 70:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_charging_60_black_24dp);
                        break;
                    case 80:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_charging_80_black_24dp);
                        break;
                    case 90:break;
                    case 100:break;
                    default:
                        imgVw_BatteryStatus.setImageResource(R.drawable.ic_battery_alert_black_24dp);
                        break;
                }
            }

            textView1.setText("Battery Condition:\n\n"+
                    "Battery Temperature:\n\n"+
                    "Power Source:\n\n"+
                    "Charging Status:\n\n"+
                    "Battery Type:\n\n"+
                    "Voltage:");

            textView2.setText(battery_condition+"\n\n"+
                    temperature_c+" "+ (char) 0x00B0 +"C / "+ temperature_f +" "+ (char) 0x00B0 +"F\n\n"+
                    power_source+"\n\n"+
                    charging_status+"\n\n"+
                    technology+"\n\n"+
                    voltage+"mV"



            );

        }
    };

}
