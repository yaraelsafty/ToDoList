package com.example.myapplication.todolistapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.todolistapp.R;
import com.example.myapplication.todolistapp.database.DatabaseHandler;
import com.example.myapplication.todolistapp.model.Task;

import java.util.List;

/**
 * Created by Yara on 05-Mar-18.
 */

public class TaskAdapter extends BaseAdapter {
    Context context;
    List<Task> tasksList;

    public TaskAdapter(Context context, List<Task> tasksList) {
        this.context = context;
        this.tasksList = tasksList;
    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int position) {
        return tasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DatabaseHandler db = new DatabaseHandler(context);
        final Task tasks = (Task) getItem(position);
        holder.taskView.setText(tasks.getTask());
        holder.dateView.setText(tasks.getDate());
        holder.timeView.setText(tasks.getTime());
        holder.statusView.setText(tasks.getStatus());
        return convertView;
    }


    class ViewHolder {
        TextView taskView, dateView,timeView,statusView;

        ViewHolder(View v) {
            taskView = (TextView) v.findViewById(R.id.tv_show_task);
            dateView = (TextView) v.findViewById(R.id.tv_show_date);
            timeView=(TextView) v.findViewById(R.id.tv_show_time);
            statusView=(TextView)v.findViewById(R.id.tv_show_status);

        }
    }
}


