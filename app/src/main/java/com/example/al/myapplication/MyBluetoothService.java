package com.example.al.myapplication;
/*
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Handler;

/**
 * Created by Al on 11/7/17.
 */
/*
public class MyBluetoothService {
    private static final String TAG = "MY_APP_DEBUG_TAG";

    // Handler that gets info from Bluetooth service
    private Handler handler;

    // Define several constants used when transmitting messages between the service & UI
    private interface MessageContent{
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        /**
        ########################################################################################
        @TODO: Add other message types here. This is where commands are sent I believe
        ########################################################################################
         */
 /*   }

    private class ConnectedThread extends Thread{
        private final BluetoothSocket socket;

        public BluetoothSocket getInStream() {
            return inStream;
        }

        public BluetoothSocket getOutStream() {
            return outStream;
        }

        private final BluetoothSocket inStream;
        private final BluetoothSocket outStream;

        // buffer store for the stream
        private byte [] buffer;


        public ConnectThread(BluetoothSocket socket){
            this.socket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams using temp objects because
            // member streams are final


            // Input Stream try-catch
            try{
                tmpIn = socket.getInputStream();
            }catch(IOException e){
                Log.e(TAG, "Error has occured for input stream", e);
            }

            // Output Stream try-catch
            try{
                tmpOut = socket.getOutputStream();
            }catch(IOException e){
                Log.e(TAG, "Error has occured for input stream", e);
            }

            InStream = tmpIn;
            OutStream = tmpOut;
        }

        public void run(){
            buffer = new byte[1024];

            // bytes returned from read()
            int numBytes;

            // Keep listening to the InputStream until an exception occurs
            while (true){
                try{
                    // Try to read from the input stream
                    numBytes = InStream.read(buffer);

                    // Send the obtained bytes to the UI activity
                    Message readMessage = handler.obtainMessage(

                            MessageConstants.MESSAGE_READ, numBytes, -1, buffer);

                    readMessage.sendToTarget();

                }catch (IOException e){
                    Log.d(TAG, "INPUT STREAM WAS DISCONNECTED", e);
                    break;
                }
            }
        }

        // Call this from the main activity to send data to the remote device
        public void write (byte [] bytes){
            try{
                OutputStream.write(bytes);

                // Share the sent message with the UI activity
                Message writtenMessage = handler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, 01, buffer);
                writtenMessage.sendToTarget();

            }catch (IOException e){
                Log.e(TAG, "ERROR OCCURED WHEN SENDING DATA", e);


                // Send a failure message back to the activity
                Message messageError = handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast", "Couldn't send data to other device");

                /**
                 * @TODO: Where was this intialized
                 */

               /* writeErrorMsg.setData(bundle);
                handler.sendMessage(writeErrorMsg);
            }
        }

        // Call this metho from the main activity to shut down the connection
        public void cancel(){
            try{
                socket.close();
            }catch (IOException e){
                Log.e(TAG, "Couldn't close the connect socket", e);
            }
        }
    }


}
*/