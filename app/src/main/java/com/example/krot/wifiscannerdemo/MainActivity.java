package com.example.krot.wifiscannerdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.krot.wifiscannerdemo.adapter.WifiAdapter;
import com.example.krot.wifiscannerdemo.model.WifiObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private IntentFilter wifiIntentFilter = new IntentFilter();
    private WifiManager wifiManager;
    private WifiAdapter wifiAdapter;

    @BindView(R.id.network_list)
    RecyclerView mNetworkList;
    @BindView(R.id.btn_re_scan)
    Button btnReScan;
    @BindView(R.id.loading_progress_bar)
    ProgressBar loadingProgressBar;

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String scanWifiAction = intent.getAction();
            if (scanWifiAction != null) {
                if (scanWifiAction.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                    List<ScanResult> currentScanResult = wifiManager.getScanResults();
                    List<WifiObject> wifiObjectList = wifiAdapter.getWifiList();
                    if (wifiObjectList == null) {
                        wifiObjectList = new ArrayList<>();
                    }

                    if (currentScanResult != null) {
                        for (int i = 0; i < currentScanResult.size(); i++) {
                            wifiObjectList.add(new WifiObject(currentScanResult.get(i).SSID.toString(), currentScanResult.get(i).BSSID.toString()));
                        }
                    }

                    wifiAdapter.setWifiList(wifiObjectList);
                }
            } else {
                Log.i("WTF", "scanWifiAction = null");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            Log.i("WTF", "not support wifi on this device");
            finish();
        } else {
            setupWifiAdapter();
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }

            doScanForAMinute();

        }
    }

    @OnClick(R.id.btn_re_scan)
    public void doReScanWifi() {
        wifiAdapter.setWifiList(new ArrayList<WifiObject>());
        doScanForAMinute();
    }


    public void doScanForAMinute() {
        btnReScan.setEnabled(false);
        loadingProgressBar.setVisibility(View.VISIBLE);
        wifiIntentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiReceiver, wifiIntentFilter);
        wifiManager.startScan();

        //stop scan after a minute
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unregisterReceiver(wifiReceiver);
                btnReScan.setEnabled(true);
                loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        }, 30000);
    }

    public void setupWifiAdapter(){
        wifiAdapter = new WifiAdapter();
        mNetworkList.setAdapter(wifiAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mNetworkList.setLayoutManager(manager);
        mNetworkList.setItemAnimator(new DefaultItemAnimator());
    }
}
