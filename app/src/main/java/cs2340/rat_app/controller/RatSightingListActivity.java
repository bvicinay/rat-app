package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatList;
import cs2340.rat_app.model.RatSighting;
import cs2340.rat_app.model.RatSightingRaw;

public class RatSightingListActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private static List<RatSighting> sightingsList;

    private static DatabaseReference mDatabaseRef;

   // private static final String TAG = "RatSightingListActivity";

    //Button viewMap;

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_sightings_list);

        RecyclerView sightingsRecyclerView = findViewById(R.id.RatSightings_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        sightingsRecyclerView.setLayoutManager(layoutManager);

        sightingsList = RatList.getInstance();
        adapter = new RatSightingAdapter(sightingsList);
        sightingsRecyclerView.setAdapter(adapter);

        LoadLocalData d1 = new LoadLocalData();
        d1.execute(100);

        //When add sighting fab button is pressed
        FloatingActionButton addSighting = findViewById(R.id.fab_add_sighting);
        addSighting.setOnClickListener(view -> {
            Intent intent = new Intent(getOuter(), AddSightingActivity.class);
            startActivity(intent);
        });


        Button viewMap = findViewById(R.id.map_button);
        viewMap.setOnClickListener(v -> {
            Intent intent = new Intent(getOuter(), FilterRatSightings1.class);
            intent.putExtra("Check", 0);
            startActivity(intent);
        });
        Button viewGraph = findViewById(R.id.graph_button);
        viewGraph.setOnClickListener(v -> {
            Intent intent = new Intent(getOuter(), FilterRatSightings1.class);
            intent.putExtra("Check", 1);
            startActivity(intent);
        });

    }

    /**
     * populates the singleton with first 100 items from database
     *//*
    private void importFromDatabase() {

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference();

        Query ratSightingsQuery = mDatabaseRef.child("rat_sightings");
        ratSightingsQuery.limitToFirst(100);
        ratSightingsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                RatSighting sighting = new RatSighting(raw_sighting);
                sightingsList.add(0, sighting);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }*/

    public class RatSightingAdapter extends RecyclerView.Adapter<RatSightingAdapter.ViewHolder> {

        private final List<RatSighting> dataSet;

        //ViewHolder for each entry in recycler view
        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView itemTitle;
            private final TextView itemDate;
            private final TextView itemSubtitle;
            private final LinearLayout layout;

            /**
             * creates a view holder for the current report
             * @param v the current view
             */
            public ViewHolder(View v) {
                super(v);
                layout = v.findViewById(R.id.ratList);
                itemTitle = v.findViewById(R.id.item_title);
                itemDate = v.findViewById(R.id.item_date);
                itemSubtitle = v.findViewById(R.id.item_subtitle);
            }
        }

        public RatSightingAdapter(List<RatSighting> data) {
            dataSet = data;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RatSightingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_rat_sightings_list_item, parent, false);
            // set the view's size, margins, padding and layout parameters

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            RatSighting r1 = dataSet.get(position);
            holder.itemTitle.setText(r1.getTitle());
            holder.itemDate.setText(r1.getDateStr());
            holder.itemSubtitle.setText(r1.getStreet());
            holder.layout.setOnClickListener(v -> {
                Intent intent = new Intent(getOuter(), RatReportActivity.class);
                intent.putExtra("RatSighting", r1);
                startActivity(intent);
            });

        }
        // Return the size of your data set (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return dataSet.size();
        }


    }

    // AsyncTask that loads in data from Firebase
    public static class LoadLocalData extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute() {

            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            mDatabaseRef = mDatabase.getReference();

        }

        @Override
        protected Integer doInBackground(Integer... params) {
            Query ratSightingsQuery = mDatabaseRef.child("rat_sightings");
            ratSightingsQuery.limitToFirst(params[0]);
            ratSightingsQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    sightingsList.add(0, sighting);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    int i = sightingsList.indexOf(sighting);
                    sightingsList.remove(sighting);
                    sightingsList.add(i, sighting);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    sightingsList.remove(sighting);
                    adapter.notifyDataSetChanged();
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
            adapter.notifyDataSetChanged();
        }

    }

    /**
     * return this activity
     * @return RatSightingListActivity
     */
    private RatSightingListActivity getOuter() {
        return this;
    }

}


