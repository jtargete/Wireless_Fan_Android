package com.example.wirelessfanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ovh.karewan.knble.KnBle;
import ovh.karewan.knble.interfaces.BleWriteCallback;
import ovh.karewan.knble.struct.BleDevice;


public class OperationsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations_page);
        Button btnoff = (Button) findViewById(R.id.button5);
        Button btnlow = (Button) findViewById(R.id.button4);
        Button btnhigh = (Button) findViewById(R.id.button);
        String UUIDCHAR = "0000FFE1-0000-1000-8000-00805F9B34FB";
        String UUIDSER = "0000FFE0-0000-1000-8000-00805F9B34FB";
        String off = "J";
        String low = "6";
        String High = "t";
        byte[] byteOff = off.getBytes();
        byte[] byteLow = low.getBytes();
        byte[] byteHigh = High.getBytes();
        BleDevice device = KnBle.getInstance().getBleDeviceFromMac("12:34:56:05:BE:65");
        BleWriteCallback CALLBACK = new BleWriteCallback() {
            @Override
            public void onWriteFailed() {

            }

            @Override
            public void onWriteProgress(int current, int total) {

            }

            @Override
            public void onWriteSuccess() {

            }
        };

        btnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KnBle.getInstance().write(device, UUIDSER, UUIDCHAR, byteOff, CALLBACK);
            }

        });

        btnlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KnBle.getInstance().write(device, UUIDSER, UUIDCHAR, byteLow, CALLBACK);
            }

        });

        btnhigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KnBle.getInstance().write(device, UUIDSER, UUIDCHAR, byteHigh, CALLBACK);

            }

        });
    }
}