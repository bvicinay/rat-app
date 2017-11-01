package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;
import cs2340.rat_app.model.RatSightingRaw;

public class RatSightingListActivity extends AppCompatActivity {

    //instance variables
    private RecyclerView sightingsRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton addSighting;

    //Firebase
    private DatabaseReference mDatabase;
    private ValueEventListener sightingsListener;

    private static final String TAG = "RatSightingListActivity";

    //Button viewMap;

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    Button viewMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_sightings_list);

        sightingsRecyclerView = (RecyclerView) findViewById(R.id.RatSightings_list);

        layoutManager = new LinearLayoutManager(this);
        sightingsRecyclerView.setLayoutManager(layoutManager);

        adapter = new RatSightingAdapter(RatSighting.ratSightings);
        sightingsRecyclerView.setAdapter(adapter);

        if (RatSighting.ratSightings.size() < 10) {
            new LoadLocalData().execute(100);
        }

        //When add sighting fab button is pressed
        addSighting = (FloatingActionButton) findViewById(R.id.fab_add_sighting);
        addSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getOuter(), AddSightingActivity.class);
                startActivity(intent);
            }
        });


        viewMap = (Button) findViewById(R.id.map_button);
        viewMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getOuter(), FilterRatSightings1.class);
                startActivity(intent);
            }
        });

    }
    private void importFromDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query ratSightingsQuery = mDatabase.child("rat_sightings").limitToFirst(100);
        ratSightingsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                RatSighting sighting = new RatSighting(raw_sighting);
                RatSighting.ratSightings.add(0, sighting);
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
    }

    public class RatSightingAdapter extends RecyclerView.Adapter<RatSightingAdapter.ViewHolder> {

        private ArrayList<RatSighting> dataSet;

        //ViewHolder for each entry in recyclerview
        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView itemTitle;
            public TextView itemDate;
            public TextView itemSubtitle;
            public LinearLayout layout;

            /**
             * creates a viewholder for the current report
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

        public RatSightingAdapter(ArrayList<RatSighting> data) {
            dataSet = data;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RatSightingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_rat_sightings_list_item, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // TODO: pass RatSighting to intent
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.itemTitle.setText(dataSet.get(position).getTitle());
            holder.itemDate.setText(dataSet.get(position).getDateStr());
            holder.itemSubtitle.setText(dataSet.get(position).getStreet());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getOuter(), RatReportActivity.class);
                    RatSighting selected = dataSet.get(position);
                    intent.putExtra("RatSighting", dataSet.get(position));
                    startActivity(intent);
                }
            });

        }
        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return dataSet.size();
        }


    }

    // AsyncTask that loads in data from Firebase
    public class LoadLocalData extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute() {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            Query ratSightingsQuery = mDatabase.child("rat_sightings").limitToFirst(params[0]);
            ratSightingsQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    RatSighting.ratSightings.add(0, sighting);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    int i = RatSighting.ratSightings.indexOf(sighting);
                    RatSighting.ratSightings.remove(sighting);
                    RatSighting.ratSightings.add(i, sighting);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    RatSighting.ratSightings.remove(sighting);
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
    public RatSightingListActivity getOuter() {
        return this;
    }

}


