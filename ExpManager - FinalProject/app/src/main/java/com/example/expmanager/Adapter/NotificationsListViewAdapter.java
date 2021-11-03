package com.example.expmanager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.expmanager.R;

import java.util.List;

public class NotificationsListViewAdapter  extends BaseAdapter {

    List<String> notifications;

    public NotificationsListViewAdapter(List<String> notifications) {
        this.notifications = notifications;
    }


    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_listitems,parent,false);
        }

        TextView notification = convertView.findViewById(R.id.notif_txt);
        notification.setText(notifications.get(position));

        return convertView;
    }
}
