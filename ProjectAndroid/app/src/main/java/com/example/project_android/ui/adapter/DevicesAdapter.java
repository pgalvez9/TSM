package com.example.project_android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.R;
import com.example.project_android.dataClases.DeviceInfo;
import com.example.project_android.ui.DescriptionDeviceActivity;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {
    private List<DeviceInfo> devicesList;
    private final Context context;

    public DevicesAdapter(Context context, List<DeviceInfo> devicesList){
        this.devicesList = devicesList;
        this.context = context;
    }

    public void updateDevices(List<DeviceInfo> devicesList){
        this.devicesList = devicesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View viewDeviceItem = layoutInflater.inflate(R.layout.item_device, parent,  false);
        return new ViewHolder(viewDeviceItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(devicesList.get(position).getName());
        holder.tvID.setText("ID: " + devicesList.get(position).getId());
        if (devicesList.get(position).getStatus() == 0){
            holder.tvStatus.setText("Status: OFF");
            holder.ivStatus.setBackgroundResource(R.drawable.lightoff);
        }
        else {
            holder.tvStatus.setText("Status: ON");
            holder.ivStatus.setBackgroundResource(R.drawable.lighton);
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DescriptionDeviceActivity.class);
                intent.putExtra("ID", devicesList.get(position).getId());
                intent.putExtra("STATUS", devicesList.get(position).getStatus());
                intent.putExtra("NAME", devicesList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvID, tvStatus;
        LinearLayout mainLayout;
        ImageView ivStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textName);
            tvID = itemView.findViewById(R.id.textNumberID);
            tvStatus = itemView.findViewById(R.id.textstatusID);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            ivStatus = itemView.findViewById(R.id.imageID);
        }
    }
}
