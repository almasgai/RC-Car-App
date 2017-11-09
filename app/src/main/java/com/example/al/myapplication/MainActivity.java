package com.example.al.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button powerButton;
    Button forwardButton;
    ImageButton leftButton;
    ImageButton rightButton;
    ToggleButton toggleAutonomous;
    BluetoothAdapter bluetoothAdapter;

    ArrayList list;
    ArrayAdapter arrayAdapter;
    ListView listView;

    private Toolbar mTopToolbar;


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

                Toast.makeText(context.getApplicationContext(),"test", Toast.LENGTH_SHORT);

                Log.e(deviceName.toUpperCase(), " - " + deviceHardwareAddress);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the ACTION_FOUND receiver
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

    public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter){
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public BluetoothAdapter getBluetoothAdapter(){
        return bluetoothAdapter;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopToolbar = (Toolbar)findViewById(R.id.my_toolbar);


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
            list = new ArrayList();
            // There are paired devices if the program can get in this if statement
            for (BluetoothDevice device : pairedDevices){
                // String deviceName = device.getName();
                // String deviceHardwareAddress = device.getAddress();

                // Adding names to list
                list.add(device.getName());

            }
            list.add(pairedDevices);
            arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, list);
            listView = (ListView)findViewById(R.id.listViewID);
            listView.setAdapter(arrayAdapter);
        }

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);
        startActivity(discoverableIntent);

        // DISCOVERING DEVICES
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);


    }
}
