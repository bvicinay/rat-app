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
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_rat_sightings2);

        next = (Button) findViewById(R.id.show);
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
                id = getIntent().getIntExtra("Check1", 0);
                if (id == 0) {
                    Intent intent = new Intent(getOuter(), MapsActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getOuter(), GraphViewActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    /**
     * outer method that returns instance of this class
     * @return FilterRatSightings2
     */
    private FilterRatSightings2 getOuter() { return this; }
}
