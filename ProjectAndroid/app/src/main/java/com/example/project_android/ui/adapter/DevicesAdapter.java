package com.example.project_android.ui.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.R;
import com.example.project_android.dataClases.DeviceInfo;
import com.example.project_android.services.Utils;
import com.example.project_android.ui.DescriptionDeviceActivity;

import java.util.Calendar;
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

        holder.alarmLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String finalHour, finalMinute;

                        finalHour = "" + selectedHour;
                        finalMinute = "" + selectedMinute;
                        if (selectedHour < 10) finalHour = "0" + selectedHour;
                        if (selectedMinute < 10) finalMinute = "0" + selectedMinute;

<<<<<<< HEAD
=======
                        //holder.alarmTime.setText(finalHour + ":" + finalMinute);

>>>>>>> origin/master
                        Calendar today = Calendar.getInstance();

                        today.set(Calendar.HOUR_OF_DAY, selectedHour);
                        today.set(Calendar.MINUTE, selectedMinute);
                        today.set(Calendar.SECOND, 0);

                   /* SharedPreferences.Editor edit = settings.edit();
                    edit.putString("hour", finalHour);
                    edit.putString("minute", finalMinute);

                    //SAVE ALARM TIME TO USE IT IN CASE OF REBOOT
                    edit.putInt("alarmID", alarmID);
                    edit.putLong("alarmTime", today.getTimeInMillis());

                    edit.commit();*/

                        Toast.makeText(context, "Alarma puesta a las "+finalHour+":"+finalMinute, Toast.LENGTH_SHORT).show();

                        Utils.setAlarm(devicesList.get(position).getId(), today.getTimeInMillis(), context, devicesList.get(position).getName());
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecciona la hora");
                mTimePicker.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

<<<<<<< HEAD
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvID, tvStatus;
        LinearLayout alarmLayout;
        RelativeLayout mainLayout;
        public LinearLayout layoutAborrar;
=======
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvID, tvStatus, alarmTime;

        LinearLayout mainLayout, alarmLayout;
>>>>>>> origin/master
        ImageView ivStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textName);
            tvID = itemView.findViewById(R.id.textNumberID);
            tvStatus = itemView.findViewById(R.id.textstatusID);
            //alarmTime=itemView.findViewById(R.id.textalarmaID);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            ivStatus = itemView.findViewById(R.id.imageID);
            alarmLayout = itemView.findViewById(R.id.alarm);
<<<<<<< HEAD
            layoutAborrar = itemView.findViewById(R.id.layoutAborrar);
=======
>>>>>>> origin/master
        }
    }

    public void removeDevice (int position)
    {
        devicesList.remove(position);
        notifyItemRemoved(position);

    }

    public void restoreDevice ( DeviceInfo deviceInfo, int position)
    {
        devicesList.add(position, deviceInfo);
        notifyItemInserted(position);

    }

}
