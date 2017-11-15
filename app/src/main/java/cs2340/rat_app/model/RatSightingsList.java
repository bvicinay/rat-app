package cs2340.rat_app.model;

import android.os.AsyncTask;
import android.util.Log;

import cs2340.rat_app.controller.RatSightingListActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class RatSightingsList {

    //ratSighting singleton
    public static ArrayList<RatSighting> ratSightings = new ArrayList<>();
    public static ArrayList<RatSighting> filteredRatSightings = new ArrayList<>();

    //Declared globally so the instances do not get destroyed after the Task executes
    //If not, the database-change listeners will stop working
    private static DatabaseReference mDatabase;
    private static Query ratSightingsQuery;

    private static boolean initialLoad = false;

    private RatSightingsList() {
    }

    /**
     * returns the singleton
     * @return arraylist of ratsightings
     */
    public static ArrayList<RatSighting> getInstance() {
        return ratSightings;
    }

    /**
     * Filters items by adding the valid ones to the filtered sightings array
     * @return boolean representing whether the data was filtered
     */
    public static boolean filterItems() {
        if (!FilteredDate.isFiltered) {
            filteredRatSightings = ratSightings;
            return false;
        }
        filteredRatSightings.clear();
        int count = 0;
        try {
            for (RatSighting rat: ratSightings) {
                if (rat.getCreation_date() == null) {
                    Log.d("Exception", "Error occured while filtering a RatSighting. The following RatSighting was skipped: " + rat.toString());
                    continue;
                }
                if (rat.getCreation_date().compareTo(FilteredDate.startDate) > 0 &&
                        rat.getCreation_date().compareTo(FilteredDate.finishDate) < 0) {
                    filteredRatSightings.add(rat);
                }
                if (count++ % 20 == 0) {
                    try {
                        RatSightingListActivity.adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.d("RatSightingsList", "Could not update the adapter");
                    }
                }
            }
        } catch(Exception e) {
            Log.d("Exception", "Error occured while filtering the RatSightings", e);
        }
        return true;
    }

    // AsyncTask that loads in data from Firebase
    public static class LoadDatabaseDataTask extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute() {
            ratSightings.clear();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            initialLoad = false;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            ratSightingsQuery = mDatabase.child("rat_sightings").limitToFirst(params[0]);
            ratSightingsQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.d("test", "child added executed");
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    RatSightingsList.ratSightings.add(0, sighting);
                    if (initialLoad) {
                        filterItems();
                    }
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.d("test", "child changed executed");
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    int i = RatSightingsList.ratSightings.indexOf(sighting);
                    RatSightingsList.ratSightings.remove(sighting);
                    RatSightingsList.ratSightings.add(i, sighting);
                    if (initialLoad) {
                        filterItems();
                    }
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    RatSightingsList.ratSightings.remove(sighting);
                    if (initialLoad) {
                        filterItems();
                    }
                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {      }
                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });
            return 1;
        }
        @Override
        protected void onPostExecute(Integer result) {
            initialLoad = true;
            new FilterDataTask().execute();
            try {
                RatSightingListActivity.adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.d("RatSightingsList", "Could not update the adapter");
            }

        }

    }

    // AsyncTask that filters the data after it being loaded
    public static class FilterDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            filteredRatSightings.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            filterItems();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                RatSightingListActivity.adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.d("RatSightingsList", "Could not update the adapter");
            }
        }
    }
}
