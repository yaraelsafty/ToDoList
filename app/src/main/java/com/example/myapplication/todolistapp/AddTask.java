package com.example.myapplication.todolistapp;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.todolistapp.adapter.TaskAdapter;
import com.example.myapplication.todolistapp.database.DatabaseHandler;
import com.example.myapplication.todolistapp.model.Task;

import java.util.Calendar;
import java.util.List;

public class AddTask extends AppCompatActivity {

    EditText get_task;
    Button save,cancle;
    ImageButton calendar,alarm;
    TextView show;
    String date,time;

    DatabaseHandler db = new DatabaseHandler(this);
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        get_task= (EditText) findViewById(R.id.et_get_task);
        save= (Button) findViewById(R.id.btn_save);
        cancle=(Button)findViewById(R.id.btn_cancle);
        calendar=(ImageButton) findViewById(R.id.ibnt_set_date);
        alarm=(ImageButton)findViewById(R.id.ibtn_set_time);
        show=(TextView)findViewById(R.id.show);






        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar =Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog =new DatePickerDialog(AddTask.this,
                        dateSetListener,year,month,day
                        );
                dialog.show();

            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date =dayOfMonth+"/"+month+"/"+year;
                show.setText(date);
            }
        };


        alarm.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Calendar cal = Calendar.getInstance();
                                         int hours = cal.get(cal.HOUR_OF_DAY);
                                         int minute = cal.get(cal.MINUTE);
                                         TimePickerDialog dialog = new TimePickerDialog(AddTask.this, timeSetListener, hours, minute, DateFormat.is24HourFormat(AddTask.this));
                                         dialog.show();
                                     }
                                 });

                timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time=hourOfDay + ":" + minute;
                                show.setText(time);
                            }
                        };
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddTask.this,MainActivity.class);
                intent.putExtra("taskname",get_task.getText().toString());
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                startActivity(intent);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
