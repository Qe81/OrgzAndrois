package com.example.orgzandrois.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orgzandrois.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;


public class AddEvent extends AppCompatActivity {

    private EditText DataTimeStart, DataTimeEnd, NameEvent;
    Button Tick;
    ListView listView;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        DataTimeStart=findViewById(R.id.DataTimeStart);
        DataTimeEnd=findViewById(R.id.DataTimeEnd);
        NameEvent=findViewById(R.id.NameEvent);
        Tick=findViewById(R.id.Tick);
        Realm.init(this);
        realm = Realm.getDefaultInstance();





        DataTimeStart.setInputType(InputType.TYPE_NULL);
        DataTimeEnd.setInputType(InputType.TYPE_NULL);

        DataTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataTimeDialog(DataTimeStart,DataTimeEnd);
            }


        });
        DataTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataTimeDialogEnd(DataTimeEnd,DataTimeStart);
            }
        });




      /**  events = JSONHelper.importFromJSON(this);
        if(events!=null){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, events);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
*/

    }
    private void showDataTimeDialog(final EditText DataTimeStart, final EditText DataTimeEnd) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EE, dd.MM.yyyy, HH:mm",new Locale("uk", "UA"));

                        DataTimeStart.setText(simpleDateFormat.format(calendar.getTime()));
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay+1);
                        DataTimeEnd.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                };
                new TimePickerDialog(AddEvent.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), true).show();
            }
        };

        new DatePickerDialog(AddEvent.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showDataTimeDialogEnd(final EditText DataTimeEnd,final EditText DataTimeStart) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE, dd-MM-yyyy, HH:mm");

                        DataTimeEnd.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(AddEvent.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
            }
        };

        new DatePickerDialog(AddEvent.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    //BUTTON / REALM
    public void AddRecordTick(View view){
        realm.beginTransaction();


        realm.commitTransaction();
        Toast.makeText(getApplicationContext(), "Запис успішно додано", Toast.LENGTH_SHORT)
                .show();
        realm.close();

    }

}
