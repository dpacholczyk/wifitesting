package org.fixus.wifitesting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.fixus.wifitesting.utils.WifiScanner;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "IOTAR";
    private static final String SSID = "oliwkoland";

    public static int readCounter = 0;

    private WifiManager wifiManager;
    private List<ScanResult> wifiScanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        final WifiScanner scanner = new WifiScanner(this.wifiManager);
        registerReceiver(scanner, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        final TextView levelText = (TextView) findViewById(R.id.levelValue);
        final TextView distanceText = (TextView) findViewById(R.id.distanceValue);
        final TextView readText = (TextView) findViewById(R.id.readValue);
        readText.setText(MainActivity.readCounter + "");

        Button scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DecimalFormat df = new DecimalFormat("#.##");
                MainActivity.this.wifiManager.startScan();
                List<ScanResult> wifiList = scanner.getWifiScanList();
                if(wifiList != null){
                    for(ScanResult network : wifiList) {
                        if(network.SSID.equals(SSID)) {
                            Log.d(TAG, "level: " + network.level);
                            Log.d(TAG, "distance: " + scanner.calculateDistance(network.level, network.frequency));

                            levelText.setText(network.level + "");
                            distanceText.setText(df.format(scanner.calculateDistance(network.level, network.frequency)) + "m");
                            MainActivity.readCounter++;
                            readText.setText(MainActivity.readCounter + "");
                        }
                    }
                }
            }
        });
    }
}
