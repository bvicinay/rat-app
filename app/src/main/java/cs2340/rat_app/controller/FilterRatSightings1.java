package cs2340.rat_app.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import cs2340.rat_app.R;
import cs2340.rat_app.model.FilteredDate;

public class FilterRatSightings1 extends AppCompatActivity {

    private Button next;
    private DatePicker datePicker;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_rat_sightings1);

        next = (Button) findViewById(R.id.nextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve data from date picker
                datePicker = (DatePicker) findViewById(R.id.startDatePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                Calendar startDate = Calendar.getInstance();
                startDate.set(year, month, day);
                FilteredDate.startDate = startDate;

                id = getIntent().getIntExtra("Check", 0);
                Intent intent = new Intent(getOuter(),FilterRatSightings2.class);
                intent.putExtra("Check1", id);
                startActivity(intent);
            }
        });


    }

    /**
     * outer method that returns instance of this class
     * @return FilterRatSightings1
     */
    private FilterRatSightings1 getOuter() { return this; }
}
