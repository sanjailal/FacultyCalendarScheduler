package com.example.facultycalendarscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import com.example.facultycalendarscheduler.sampledata.EventDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class calendar_view extends AppCompatActivity implements OnDateSelectedListener {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
  //  @BindView(R2.id.parent) ViewGroup parent;
    @BindView(R2.id.calendarView_single) MaterialCalendarView single;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        ButterKnife.bind(this);
        single.setOnDateChangedListener(this);
        //single.addDecorator(new EventDecorator(Color.BLUE, ));
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        final String text = selected ? FORMATTER.format(date.getDate()) : "No Selection";
        Toast.makeText(calendar_view.this, text, Toast.LENGTH_SHORT).show();
    }
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LocalDate temp = LocalDate.now().minusMonths(2);
            final ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                final CalendarDay day = CalendarDay.from(temp);
                dates.add(day);
                temp = temp.plusDays(5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }
            Log.d("fd",calendarDays.toString(),null);
            single.addDecorator(new EventDecorator(Color.BLUE, calendarDays));
        }
    }





}


