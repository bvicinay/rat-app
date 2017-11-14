package cs2340.rat_app.controller;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import cs2340.rat_app.R;
import cs2340.rat_app.model.FilteredDate;
import cs2340.rat_app.model.RatList;
import cs2340.rat_app.model.RatSighting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Date;


public class GraphViewActivity extends AppCompatActivity {

    HashMap<Calendar, Integer> ratSightingHashMap = new HashMap<>();
    PointsGraphSeries<DataPoint> series;
    public static Calendar min = RatList.ratSightings.get(0).getCreation_date();
    public static Calendar max = RatList.ratSightings.get(0).getCreation_date();
    Calendar startDate = FilteredDate.startDate;
    Calendar finishDate = FilteredDate.finishDate;
    List<RatSighting> sightings = RatList.getInstance();
    List<RatSighting> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_graph);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        int j = 0;

        filteredList = RatSighting.validateDateForGraph(sightings, startDate, finishDate);
        ratSightingHashMap = RatSighting.setRatHashMap(filteredList);

        Set<Calendar> keys = ratSightingHashMap.keySet();
        DataPoint[] points = new DataPoint[keys.size()];
        int i = 0;
        for (Calendar k : keys) {
            int month = k.get(Calendar.MONTH) - 1;
            int year = k.get(Calendar.YEAR);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            Date date = cal.getTime();
            DataPoint plot = new DataPoint(date, ratSightingHashMap.get(k));
            points[i] = plot;
            i++;
        }
        series = new PointsGraphSeries<>(points);
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(keys.size());

        int minMonth = min.get(Calendar.MONTH) - 1;
        int maxMonth = max.get(Calendar.MONTH) - 1;
        int minYear = min.get(Calendar.YEAR);
        int maxYear = max.get(Calendar.YEAR);
        Calendar minCal = Calendar.getInstance();
        Calendar maxCal = Calendar.getInstance();
        minCal.clear();
        maxCal.clear();
        minCal.set(Calendar.MONTH, minMonth);
        maxCal.set(Calendar.MONTH, maxMonth);
        minCal.set(Calendar.YEAR, minYear);
        maxCal.set(Calendar.YEAR, maxYear);
        Date minDate = minCal.getTime();
        Date maxDate = maxCal.getTime();
        graph.getViewport().setMinX(minDate.getTime());
        graph.getViewport().setMaxX(maxDate.getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getViewport().setScrollable(true);
    }

    /**
     *
      * @return
     */
    public GraphViewActivity getOuter() {
            return this;
        }

    public void setMin(RatSighting rat) {
        min = rat.getCreation_date();
    }

    public void setMax(RatSighting rat) {
        max = rat.getCreation_date();
    }

}

