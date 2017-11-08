package com.example.al.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by Al on 11/7/17.
 */

public class ConnectThread extends Thread{

    /**@TODO: Figure out why these aren't working
     *################### Deleting private final for now...
     */
    BluetoothSocket btSocket;
    BluetoothDevice btDevice;
    private BluetoothAdapter bluetoothAdapter;

    public ConnectThread(BluetoothDevice device) {
        // Use temporary object that is later assigned to btSocket
        // becuase btSocket is final

        BluetoothSocket temp = null;
        btDevice = device;
    }

    public void run(){

        this.bluetoothAdapter = bluetoothAdapter;

        // Cancel discovery becuase it otherwise slows down connection
        bluetoothAdapter.cancelDiscovery();

        try{
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            // MY_UUID is the app's UUID string, also used i the server code

            btSocket.connect();

        }catch (IOException connectException){
            // Unable to connect; close the socket and return

            try{
               btSocket.close();

            }catch (IOException closeException){
                Log.e(TAG, "Could not close the client socket,", closeException);
            }
            return;
        }

        // The connection attemtp succeeded. Perform work associated with the connection in a
        // separate thread
        // manageMyConnectedSocket(btSocket);

    }

    // Close client socket and causes thread to finish
    public void cancel(){
        try{
            btSocket.close();
        }catch (IOException e){
            Log.e(TAG, "Could not close the client socket", e);
        }
    }

}
