package org.fixus.wifitesting.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 5/25/2016.
 */

public class WifiScanner extends BroadcastReceiver {
    private static final double K = 27.55;

    private WifiManager wifiManager;
    private List<ScanResult> wifiScanList = new ArrayList<ScanResult>();

    public WifiScanner(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.wifiScanList = wifiManager.getScanResults();
        int test = 1;
    }

    public List<ScanResult> getWifiScanList() {
        return this.wifiScanList;
    }

    public double calculateDistance(int rssi, int frequence) {
        double distance = 0;

        distance = (K - (20 * Math.log10(frequence)) + Math.abs(rssi)) / 20.0;
        distance = Math.pow(10.0, distance);

        return distance;
    }
}
