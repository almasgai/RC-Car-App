package com.example.al.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    Button powerButton;
    Button forwardButton;
    ImageButton leftButton;
    ImageButton rightButton;
    ToggleButton toggleAutonomous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Starting app now...", Toast.LENGTH_SHORT).show();

        powerButton = (Button)findViewById(R.id.powerOnOff);
        forwardButton = (Button)findViewById(R.id.forwardButton);
        leftButton = (ImageButton) findViewById(R.id.leftButton);
        rightButton = (ImageButton) findViewById(R.id.rightButton);
        toggleAutonomous = (ToggleButton)findViewById(R.id.toggleAutonomous);

    }

    public void leftArrow(View v){
        Toast.makeText(this, "Left arrow clicked", Toast.LENGTH_SHORT).show();
    }

    public void rightArrow(View v){
        Toast.makeText(this, "Right arrow clicked", Toast.LENGTH_SHORT).show();
    }

    public void power(View v){
        Toast.makeText(this, "Power button clicked. Turning off car now...", Toast.LENGTH_LONG).show();

        powerButton.setEnabled(false);
        forwardButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        toggleAutonomous.setEnabled(false);

        Toast.makeText(this, "Turn on car and restart app to use remote controll again", Toast.LENGTH_LONG).show();
    }

    public void driveButton(View view){
        Toast.makeText(this, "Driving car forward", Toast.LENGTH_SHORT).show();
    }
}
