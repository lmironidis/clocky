package com.example.loukas.clocky;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DigitalClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Process;




public class MainActivity extends AppCompatActivity {

    //Broadcast Reciever for Battery information
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            ProgressBar pb = (ProgressBar) findViewById(R.id.progressbar);
            pb.setProgress(level);

            TextView tv = (TextView) findViewById(R.id.textfield);
            tv.setText(Integer.toString(level)+"%");

            //Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
            //Toast.makeText(MainActivity.this, "Battery Level: "+Integer.toString(level)+"%", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Removes the top status bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Keeps the screen active until closed.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        //DigitalClock digital = (DigitalClock) findViewById(R.id.mydigitalclock);
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    @Override
    protected void onStart() {
        BattIndicator();
        super.onStart();
    }

    //When screen is tapped, the app will exit.
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        finish();
        return super.dispatchTouchEvent(ev);
    }

    //Informs user if the battery is charging or not or is full.
    public void BattIndicator(){
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = this.registerReceiver(null, filter);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            Toast.makeText(this, "Charging", Toast.LENGTH_SHORT).show();
        }else if (status == BatteryManager.BATTERY_STATUS_FULL){
            Toast.makeText(this, "Full", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Not Charging", Toast.LENGTH_SHORT).show();
        }
    }




}
