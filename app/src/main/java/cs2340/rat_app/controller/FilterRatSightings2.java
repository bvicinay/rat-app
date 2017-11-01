package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import cs2340.rat_app.R;
import cs2340.rat_app.model.FilteredDate;

public class FilterRatSightings2 extends AppCompatActivity {

    private Button next;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_rat_sightings2);

        next = (Button) findViewById(R.id.view_sightings);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve data from date picker
                datePicker = (DatePicker) findViewById(R.id.endDatePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                Calendar endDate = Calendar.getInstance();
                endDate.set(year, month, day);
                FilteredDate.finishDate = endDate;

                Intent intent = new Intent(getOuter(), MapsActivity.class);
                startActivity(intent);
            }
        });


    }

    private FilterRatSightings2 getOuter() { return this; }
}
