package com.example.krot.wifiscannerdemo.model;

import android.net.wifi.ScanResult;
import android.support.annotation.Nullable;

/**
 * Created by Krot on 1/9/18.
 */

public class WifiObject {

    @Nullable
    private String SSID;
    @Nullable
    private String BSSID;

    public WifiObject(String SSID, String BSSID) {
        this.SSID = SSID;
        this.BSSID = BSSID;
    }

    @Nullable
    public String getSSID() {
        return SSID;
    }

    @Nullable
    public String getBSSID() {
        return BSSID;
    }
}
