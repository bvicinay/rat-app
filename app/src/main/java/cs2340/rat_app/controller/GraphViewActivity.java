package cs2340.rat_app.controller;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.Series;

import cs2340.rat_app.R;
import cs2340.rat_app.model.FilteredDate;
import cs2340.rat_app.model.RatList;
import cs2340.rat_app.model.RatSighting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.Date;

/**
 * Created by trobinson80 on 11/7/17.
 */

public class GraphViewActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("MM:YYYY");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_graph);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series;
        HashMap<Date, Integer> ratSightingHashMap = new HashMap<>();

        for (RatSighting rat : RatList.ratSightings) {
                if (rat.getCreation_date() == null) {
                    continue;
                }
                if (rat.getCreation_date().compareTo(FilteredDate.startDate) > 0 &&
                        rat.getCreation_date().compareTo(FilteredDate.finishDate) < 0) {

                    int month = rat.getCreation_date().get(Calendar.MONTH);
                    int year = rat.getCreation_date().get(Calendar.YEAR);
                    Calendar cal = Calendar.getInstance();
                    cal.clear();
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.YEAR, year);
                    Date date = cal.getTime();
                    if (ratSightingHashMap.containsKey(date)) {
                        ratSightingHashMap.put(date, ratSightingHashMap.get(date) + 1);
                    } else {
                        ratSightingHashMap.put(date, 1);
                    }
                }
            }
            Set<Date> keys = ratSightingHashMap.keySet();
            DataPoint[] points = new DataPoint[keys.size()];
            int i = 0;
            for (Date k: keys)  {
                DataPoint plot = new DataPoint(k, ratSightingHashMap.get(k));
                points[i] = plot;
                i++;
            }
            series = new LineGraphSeries<>(points);
            graph.addSeries(series);

        }

    }

