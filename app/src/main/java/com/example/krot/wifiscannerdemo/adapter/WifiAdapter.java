package com.example.krot.wifiscannerdemo.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.krot.wifiscannerdemo.R;
import com.example.krot.wifiscannerdemo.model.WifiObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Krot on 1/9/18.
 */

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.WifiViewHolder> {

    @Nullable
    private List<WifiObject> wifiList;

    @Override
    public WifiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new WifiViewHolder(view);
    }

    @Nullable
    public List<WifiObject> getWifiList() {
        return wifiList;
    }

    public void setWifiList(@Nullable List<WifiObject> wifiList) {
        this.wifiList = wifiList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(WifiViewHolder holder, int position) {
        WifiObject currentObj = getItemAt(position);
        holder.bindData(currentObj);
    }

    @Override
    public int getItemCount() {
        return (wifiList != null ? wifiList.size() : 0);
    }

    public WifiObject getItemAt(int position) {
        return (wifiList != null ? wifiList.get(position) : null);
    }

    class WifiViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.network_name)
        TextView mNetworkName;
        @BindView(R.id.access_point_address)
        TextView mAccessPointAddress;

        public WifiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(WifiObject currentObj) {
            mNetworkName.setText(currentObj.getSSID());
            mAccessPointAddress.setText(currentObj.getBSSID());
        }
    }
}
