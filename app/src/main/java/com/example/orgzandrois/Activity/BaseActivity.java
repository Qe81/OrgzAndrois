package com.example.orgzandrois.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orgzandrois.Loader.DateTimeInterpreter;
import com.example.orgzandrois.Loader.MonthLoader;
import com.example.orgzandrois.R;
import com.example.orgzandrois.Week.WeekView;
import com.example.orgzandrois.Week.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public abstract class BaseActivity extends AppCompatActivity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener, WeekView.EmptyViewClickListener {
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    private ArrayList<WeekViewEvent> mNewEvents;
    private ArrayAdapter<WeekViewEvent> adapter;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);



        mWeekView = (WeekView) findViewById(R.id.weekView);

        mWeekView.setOnEventClickListener(this);

        mWeekView.setMonthChangeListener(this);


        mWeekView.setEventLongPressListener(this);


        mWeekView.setEmptyViewLongPressListener(this);


        setupDateTimeInterpreter(false);

        mWeekView.setEmptyViewClickListener(this);

        mNewEvents = new ArrayList<WeekViewEvent>();




    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);


                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);


                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);


                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", new Locale("uk", "UA"));
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", new Locale("uk", "UA"));


                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour + ":00";
            }
        });
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Початок події %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }



    @Override
    public void onEventClick(final WeekViewEvent event, RectF eventRect) {
       // Intent intent = new Intent(this, AddEvent.class);
       // startActivity(intent);
       //Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();

                // Сделать remove
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
                //set title
                .setTitle("Скасувати?")
                //set message
                .setMessage("Ви хочете скасувати запис?")
                //set positive button
                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mNewEvents.remove(event);
                        mWeekView.notifyDatasetChanged();
                        Toast.makeText(getApplicationContext(),"Запис скасована ",Toast.LENGTH_LONG).show();
                    }
                })
                //set negative button
                .setNegativeButton("Нi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                    }
                })
                .show();
    }


    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Запис: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {

       // Intent intent = new Intent(this, AddEvent.class);
       // startActivity(intent);
        // Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }

    ArrayList<WeekViewEvent> getNewEvents(int year, int month) {

        // Get the starting point and ending point of the given month. We need this to find the
        // events of the given month.
        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, year);
        startOfMonth.set(Calendar.MONTH, month - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);

        // Find the events that were added by tapping on empty view and that occurs in the given
        // time frame.
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : mNewEvents) {
            if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                events.add(event);
            }
        }
        return events;
    }

    public void onEmptyViewClicked(final Calendar endTime) {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        mDialogBuilder.setView(promptsView);


        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
        final EditText Hs = (EditText) promptsView.findViewById(R.id.HOUR_S);
        final EditText Ms = (EditText) promptsView.findViewById(R.id.MINUTES_S);
        final EditText He = (EditText) promptsView.findViewById(R.id.HOUR_E);
        final EditText Me = (EditText) promptsView.findViewById(R.id.MINUTES_E);
       /* final String cheak = Hs.getText().toString();
        final String cheak2 = Ms.getText().toString();
        final String cheak3 = He.getText().toString();
        final String cheak4 = Me.getText().toString();
        if(cheak.equals("")&&cheak3.equals("")){
            Toast.makeText(getApplicationContext(),"Введите все значения",Toast.LENGTH_SHORT).show();
        }
*/
                //set positive button

        mDialogBuilder.setPositiveButton("Далi", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //set what would happen when positive button is clicked

                    final Calendar time = (Calendar) endTime.clone();
                    time.set(Calendar.HOUR_OF_DAY, 0);
                    time.set(Calendar.MINUTE, 0);
                    endTime.set(Calendar.HOUR_OF_DAY, 0);
                    endTime.set(Calendar.MINUTE, 0);

               // if(cheak2.equals("")&&cheak4.equals("")){
                  //  Ms.setText("0");
                  //  Me.setText("0");


                    int HS = Integer.parseInt(Hs.getText().toString());
                    int MS = Integer.parseInt(Ms.getText().toString());
                    int HE = Integer.parseInt(He.getText().toString());
                    int ME = Integer.parseInt(Me.getText().toString());
                    // Create a new event.
                    time.add(Calendar.HOUR_OF_DAY, HS);
                    time.add(Calendar.MINUTE, MS);
                    endTime.add(Calendar.HOUR_OF_DAY, HE);
                    endTime.add(Calendar.MINUTE, ME);
                    String nameT = userInput.getText().toString();
                    WeekViewEvent event = new WeekViewEvent(20, nameT, getEventTitle(time), time, endTime);
                    mNewEvents.add(event);
                    // Refresh the week view.
                    mWeekView.notifyDatasetChanged();
                }
            //}
        });

                //set negative button
                mDialogBuilder.setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                    }
                })
                .show();

        // Set the new event with duration one hour.

    }


}