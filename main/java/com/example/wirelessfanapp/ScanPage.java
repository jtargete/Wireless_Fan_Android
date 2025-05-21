package com.example.wirelessfanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.List;

import ovh.karewan.knble.KnBle;
import ovh.karewan.knble.interfaces.BleGattCallback;
import ovh.karewan.knble.interfaces.BleScanCallback;
import ovh.karewan.knble.scan.ScanFilters;
import ovh.karewan.knble.scan.ScanSettings;
import ovh.karewan.knble.struct.BleDevice;


import static android.widget.Toast.makeText;
public class ScanPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);
        KnBle.getInstance().init(getApplicationContext());
        ProgressDialog dialog = new ProgressDialog(ScanPage.this);
        ProgressDialog dialog2 = new ProgressDialog(ScanPage.this);
        ListView BLELIST = (ListView)findViewById(R.id.BTDEVICES);
        ArrayAdapter <String> BLEDEVICELIST= new ArrayAdapter <String> (ScanPage.this, android.R.layout.simple_list_item_1);
        BLELIST.setAdapter(BLEDEVICELIST);
        BLELIST.setClickable(false);
        ScanSettings settings = new ScanSettings.Builder()//
                .setScanTimeout(10000)//
                .setScanMode(0)//
                .build();
        KnBle.getInstance().setScanSettings(settings);
        Button scnbtn = (Button) findViewById(R.id.button3);
        scnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KnBle.getInstance().startScan(settings,new BleScanCallback() {
                    @Override
                    public void onScanStarted() {
                           dialog.setMessage("Scanning please wait...");
                           dialog.setCancelable(false);
                           dialog.show();

                    }

                    @Override
                    public void onScanFailed(int error) {

                    }

                    @Override
                    public void onScanResult(@NonNull BleDevice bleDevice) {
                        BleDevice device = KnBle.getInstance().getBleDeviceFromMac("12:34:56:05:BE:65");
                        KnBle.getInstance().connect(device,new BleGattCallback() {
                            @Override
                            public void onConnecting() {
                                dialog2.setMessage("Connecting please wait...");
                                dialog.setCancelable(false);
                                dialog2.show();
                            }

                            @Override
                            public void onConnectFailed() {

                            }

                            @Override
                            public void onConnectSuccess(List<BluetoothGattService> services) {
                                startActivity(new Intent(ScanPage.this, OperationsPage.class));
                            }

                            @Override
                            public void onDisconnected() {


                            }
                        });
                    }

                    @Override
                    public void onScanFinished(@NonNull HashMap<String, BleDevice> scanResult) {
                        Toast.makeText(ScanPage.this, "Scanning done",Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder Scanagainbuild = new AlertDialog.Builder(ScanPage.this);
                            Scanagainbuild.setMessage("Wireless Fan could not be found, make sure to turn device on before scanning");
                            Scanagainbuild.setCancelable(true);
                            Scanagainbuild.setPositiveButton(
                                    "Scan again",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert1 = Scanagainbuild.create();
                            alert1.show();
                        }

                });

            }
        });
        BLELIST.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
}
}


