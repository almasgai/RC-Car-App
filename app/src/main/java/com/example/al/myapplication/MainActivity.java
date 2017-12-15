package com.example.al.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button powerButton;
    Button forwardButton;
    ImageButton leftButton;
    ImageButton rightButton;
    ToggleButton toggleAutonomous;
    BluetoothAdapter bluetoothAdapter;
    Intent enableBluetoothIntent;
    private ProgressDialog progress;
    String address = null;
    BluetoothDevice bluetoothDevice;

    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;

    ArrayList list;
    ArrayAdapter arrayAdapter;
    ListView listView;

    String TAG = "Main Activity";

    private static final UUID MY_UUID_SECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // new UUID: 00001101-0000-1000-8000-00805F9B34FB
    // old UUID: 8ce255c0-200a-11e0-ac64-0800200c9a66

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)) {

                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, bluetoothAdapter.ERROR);

                switch (state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: State Off");
                        break;

                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "onReceive: State turning Off");
                        break;

                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "onReceive: State On");
                        break;

                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "onReceive: State Turning On");
                        break;
                }
            }
        }
    };


    public void btnEnableDisable_Discoverable(View view) {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(bluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2,intentFilter);
    }

//    public void btnDiscover(View view){
//        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");
//
//        if(bluetoothAdapter.isDiscovering()){
//            bluetoothAdapter.cancelDiscovery();
//            Log.d(TAG, "btnDiscover: cancel discovery");
//
//            checkBTPermission();
//
//            bluetoothAdapter.startDiscovery();
//            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
//        }
//
//        if(!bluetoothAdapter.isDiscovering()){
//            checkBTPermission();
//        }
//    }

    private void checkBTPermission(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifist.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifist.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0){
                this.requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 25);
            }else{
                Log.d(TAG, "Device is below lollipop");

            }
        }
    }

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called");
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(mBroadcastReceiver2);

        // Unregister the ACTION_FOUND receiver
    }

    // Creating onClick functions to make sure all buttons are responsive

    public void leftArrow(View v) {
        Toast.makeText(getApplicationContext(), "Left arrow clicked", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Bruhhhhhhhh", Toast.LENGTH_SHORT).show();
    }

    public void rightArrow(View v) {
        Toast.makeText(getApplicationContext(), "Right arrow clicked", Toast.LENGTH_SHORT).show();
    }

    public void power(View v) {
        Toast.makeText(getApplicationContext(), "Power button clicked. Turning off car now...", Toast.LENGTH_LONG).show();

        powerButton.setEnabled(false);
        forwardButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        toggleAutonomous.setEnabled(false);

        Toast.makeText(this, "Turn on car and restart app to use remote controll again", Toast.LENGTH_LONG).show();
    }

    public void driveButton(View view) {
        Toast.makeText(this, "Driving car forward", Toast.LENGTH_SHORT).show();
    }

    public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public BluetoothAdapter getBluetoothAdapter() {
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

    // Displays a list of devices connected
    public void pairedDeviceList(){
    //List of paired devices
    Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

    // Checking to see if there are Bluetooth devices already paired with device
        if(pairedDevices.size()>0)

    {
        list = new ArrayList();
        // There are paired devices if the program can get in this if statement
        for (BluetoothDevice device : pairedDevices) {
            // String deviceName = device.getName();
            // String deviceHardwareAddress = device.getAddress();

            // Adding names to list
            list.add(device.getName() + "\n" + device.getAddress());

        }
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, list);
        listView = (ListView) findViewById(R.id.listViewID);
        listView.setAdapter(arrayAdapter);
    }
}




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerButton = findViewById(R.id.powerOnOff);
        forwardButton = findViewById(R.id.forwardButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        toggleAutonomous = findViewById(R.id.toggleAutonomous);

        // Creating Bluetooth Adapter, an object that is required for all Bluetooth activity
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Enabling Bluetooth
        if (!bluetoothAdapter.isEnabled()) {
            enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, 0);

            IntentFilter BTIntent = new IntentFilter(bluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(receiver, BTIntent);
        }

        // Querying paired devices AKA looking for devices that were previously connected to phone

        // DISCOVERING DEVICES
        //IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //registerReceiver(receiver, filter);

        pairedDeviceList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            boolean ConnectSuccess = true; //if it's here, it's almost connected

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);

                bluetoothAdapter.cancelDiscovery();


                try {
                    if (btSocket == null || !isBtConnected) {
                        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                        BluetoothDevice dispositivo = bluetoothAdapter.getRemoteDevice(address);//connects to the device's address and checks if it's available
                        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(MY_UUID_SECURE);//create a RFCOMM (SPP) connection
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        btSocket.connect();//start connection
                    }
                } catch (IOException e) {
                    ConnectSuccess = false;//if the try failed, you can check the exception here
                    Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String info = ((TextView) view).getText().toString();
        String address = info.substring(info.length() - 17);

        bluetoothAdapter.cancelDiscovery();


        boolean ConnectSuccess;
        try {
            ConnectSuccess = true;
            if (btSocket == null || !isBtConnected) {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                BluetoothDevice dispositivo = bluetoothAdapter.getRemoteDevice(address);//connects to the device's address and checks if it's available
                btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(MY_UUID_SECURE);//create a RFCOMM (SPP) connection
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                btSocket.connect();//start connection
            }
        } catch (IOException e) {
            ConnectSuccess = false;//if the try failed, you can check the exception here
            Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
