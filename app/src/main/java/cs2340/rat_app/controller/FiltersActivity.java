package cs2340.rat_app.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.util.Calendar;

import cs2340.rat_app.R;
import cs2340.rat_app.model.FilteredDate;
import cs2340.rat_app.model.RatSightingsList;

public class FiltersActivity extends AppCompatActivity {

    private Switch filterSwitch;
    private LinearLayout inputArea;
    private DatePicker startDate;
    private DatePicker endDate;
    private Boolean datesChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        //Replace back button with close icon
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_icon);




    }
    protected void onPause() {
        super.onPause();
        if (datesChanged) {
            new RatSightingsList.FilterDataTask().execute();
            Log.d("FiltersActivity", "Detected a change in the filter and started task");
            datesChanged = false;
        }
    }

    protected void onResume() {
        super.onResume();

        inputArea = (LinearLayout) findViewById(R.id.dateFilter_inputArea);
        filterSwitch = (Switch) findViewById(R.id.filter_switch);
        filterSwitch.setChecked(FilteredDate.isFiltered);
        Boolean switchState = filterSwitch.isChecked();
        if (switchState) {
            //Display date pickers
            inputArea.setVisibility(View.VISIBLE);
            FilteredDate.isFiltered = true;

        } else {
            inputArea.setVisibility(View.INVISIBLE);
            FilteredDate.isFiltered = false;
        }

        //onAction listener for when button is pressed
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                datesChanged = true;
                if (isChecked) {
                    // The toggle is enabled
                    inputArea.setVisibility(View.VISIBLE);
                    FilteredDate.isFiltered = true;
                } else {
                    // The toggle is disabled
                    inputArea.setVisibility(View.INVISIBLE);
                    FilteredDate.isFiltered = false;
                }
            }
        });
        if (switchState) {
            if (FilteredDate.startDate != null && FilteredDate.finishDate != null) {
                //Date pickers onClick event
                startDate = (DatePicker) findViewById(R.id.startDateFilter);
                int sYear = FilteredDate.startDate.get(Calendar.YEAR);
                int sMonth = FilteredDate.startDate.get(Calendar.MONTH);
                int sDay = FilteredDate.startDate.get(Calendar.DAY_OF_MONTH);
                startDate.init(sYear, sMonth, sDay, new DatePicker.OnDateChangedListener() {
                    public void onDateChanged(DatePicker view, int yy, int mm, int dd) {
                        Calendar selectedStartDate = Calendar.getInstance();
                        selectedStartDate.set(yy, mm, dd);
                        //int doy = selectedStartDate.get(Calendar.DAY_OF_YEAR);
                        FilteredDate.startDate = selectedStartDate;
                        datesChanged = true;
                        FilteredDate.isFiltered = true;

                    }
                });
                int eYear = FilteredDate.finishDate.get(Calendar.YEAR);
                int eMonth = FilteredDate.finishDate.get(Calendar.MONTH);
                int eDay = FilteredDate.finishDate.get(Calendar.DAY_OF_MONTH);
                endDate = (DatePicker) findViewById(R.id.endDateFilter);
                endDate.init(eYear, eMonth, eDay, new DatePicker.OnDateChangedListener() {
                    public void onDateChanged(DatePicker view, int yy, int mm, int dd) {
                        Calendar selectedEndDate = Calendar.getInstance();
                        selectedEndDate.set(yy, mm, dd);
                        //int doy = selectedEndDate.get(Calendar.DAY_OF_YEAR);
                        FilteredDate.finishDate = selectedEndDate;
                        datesChanged = true;
                        FilteredDate.isFiltered = true;
                    }
                });
            } else {
                //Date pickers onClick event
                startDate = (DatePicker) findViewById(R.id.startDateFilter);
                startDate.init(2017, 0, 1, new DatePicker.OnDateChangedListener() {
                    public void onDateChanged(DatePicker view, int yy, int mm, int dd) {
                        Calendar selectedStartDate = Calendar.getInstance();
                        selectedStartDate.set(yy, mm, dd);
                        //int doy = selectedStartDate.get(Calendar.DAY_OF_YEAR);
                        FilteredDate.startDate = selectedStartDate;
                        datesChanged = true;
                        FilteredDate.isFiltered = true;

                    }
                });
                endDate = (DatePicker) findViewById(R.id.endDateFilter);
                endDate.init(2017, 0, 1, new DatePicker.OnDateChangedListener() {
                    public void onDateChanged(DatePicker view, int yy, int mm, int dd) {
                        Calendar selectedEndDate = Calendar.getInstance();
                        selectedEndDate.set(yy, mm, dd);
                        //int doy = selectedEndDate.get(Calendar.DAY_OF_YEAR);
                        FilteredDate.finishDate = selectedEndDate;
                        datesChanged = true;
                        FilteredDate.isFiltered = true;
                    }
                });


            }
        }

        /*if (FilteredDate.isFiltered && FilteredDate.startDate != null && FilteredDate.finishDate != null) {
            int sYear = FilteredDate.startDate.get(Calendar.YEAR);
            int sMonth = FilteredDate.startDate.get(Calendar.MONTH);
            int sDay = FilteredDate.startDate.get(Calendar.DAY_OF_MONTH);
            startDate.init(sYear,sMonth,sDay,  new DatePicker.OnDateChangedListener(){
                public void onDateChanged(DatePicker view, int yy, int mm, int dd){
                    Calendar selectedStartDate = Calendar.getInstance();
                    selectedStartDate.set(yy, mm, dd);
                    //int doy = selectedStartDate.get(Calendar.DAY_OF_YEAR);
                    FilteredDate.startDate = selectedStartDate;
                    datesChanged = true;

                }
            });
            int eYear = FilteredDate.finishDate.get(Calendar.YEAR);
            int eMonth = FilteredDate.finishDate.get(Calendar.MONTH);
            int eDay = FilteredDate.finishDate.get(Calendar.DAY_OF_MONTH);
            endDate.init(eYear,eMonth,eDay,  new DatePicker.OnDateChangedListener(){
                public void onDateChanged(DatePicker view, int yy, int mm, int dd){
                    Calendar selectedEndDate = Calendar.getInstance();
                    selectedEndDate.set(yy, mm, dd);
                    //int doy = selectedEndDate.get(Calendar.DAY_OF_YEAR);
                    FilteredDate.finishDate = selectedEndDate;
                    datesChanged = true;
                }
            });*/
        }



}
