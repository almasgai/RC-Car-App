package com.example.al.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button powerButton;
    Button forwardButton;
    ImageButton leftButton;
    ImageButton rightButton;
    ToggleButton toggleAutonomous;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Starting app now...", Toast.LENGTH_SHORT).show();

        powerButton = findViewById(R.id.powerOnOff);
        forwardButton = findViewById(R.id.forwardButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        toggleAutonomous = findViewById(R.id.toggleAutonomous);

        // Creating Bluetooth Adapter, an object that is required for all Bluetooth activity
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Enabling Bluetooth
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, 1);
        }

        // Querying paired devices AKA looking for devices that were previously connected to phone

        //List of paired devices
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        // Checking to see if there are Bluetooth devices already paired with device
        if(pairedDevices.size() > 0){
            // There are paired devices if the program can get in this if statement
            for (BluetoothDevice device : pairedDevices){
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
            }
        }

        // DISCOVERING DEVICES
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

    }

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                // Discovery has found a device. Get the Bluetooth object
                // and its info from the intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // Obtaining device name
                String deviceName = device.getName();

                // Obtaining device MAC address
                String deviceHardwareAddress = device.getAddress();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregistering the ACTION_FOUND reciever
        unregisterReceiver(receiver);
    }

    // Creating onClick functions to make sure all buttons are responsive

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
