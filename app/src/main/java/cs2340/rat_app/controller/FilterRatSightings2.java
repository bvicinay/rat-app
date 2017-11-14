package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import cs2340.rat_app.R;

public class FilterRatSightings2 extends AppCompatActivity {

    private DatePicker datePicker;
    private int id;
    private Calendar startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_rat_sightings2);

        Button next = (Button) findViewById(R.id.show);
        next.setOnClickListener(view -> {
            // Retrieve data from date picker
            datePicker = (DatePicker) findViewById(R.id.endDatePicker);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();

            Calendar endDate = Calendar.getInstance();
            endDate.set(year, month, day);

            id = getIntent().getIntExtra("Check1", 0);
            startDate = (Calendar) getIntent().getSerializableExtra("startDate");
            if (id == 0) {
                Intent intent = new Intent(getOuter(), MapsActivity.class);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getOuter(), GraphViewActivity.class);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                startActivity(intent);
            }
        });


    }

    /**
     * outer method that returns instance of this class
     * @return FilterRatSightings2
     */
    private FilterRatSightings2 getOuter() { return this; }
}
