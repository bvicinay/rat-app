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
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatList;
import cs2340.rat_app.model.RatSighting;
import cs2340.rat_app.model.RatSightingRaw;

public class RatSightingListActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;

    //Firebase
    private static FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabaseRef;
    private ValueEventListener sightingsListener;

    private static final String TAG = "RatSightingListActivity";

    //Button viewMap;

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    Button viewMap;
    Button viewGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_sightings_list);

        RecyclerView sightingsRecyclerView = (RecyclerView) findViewById(R.id.RatSightings_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        sightingsRecyclerView.setLayoutManager(layoutManager);

        adapter = new RatSightingAdapter(RatList.ratSightings);
        sightingsRecyclerView.setAdapter(adapter);

        new LoadLocalData().execute(100);

        //When add sighting fab button is pressed
        FloatingActionButton addSighting = (FloatingActionButton) findViewById(R.id.fab_add_sighting);
        addSighting.setOnClickListener(view -> {
            Intent intent = new Intent(getOuter(), AddSightingActivity.class);
            startActivity(intent);
        });


        viewMap = (Button) findViewById(R.id.map_button);
        viewMap.setOnClickListener(v -> {
            Intent intent = new Intent(getOuter(), FilterRatSightings1.class);
            intent.putExtra("Check", 0);
            startActivity(intent);
        });
        viewGraph = (Button) findViewById(R.id.graph_button);
        viewGraph.setOnClickListener(v -> {
            Intent intent = new Intent(getOuter(), FilterRatSightings1.class);
            intent.putExtra("Check", 1);
            startActivity(intent);
        });

    }

    /**
     * populates the singleton with first 100 items from database
     */
    private void importFromDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();




        Query ratSightingsQuery = mDatabase.child("rat_sightings").limitToFirst(100);
        ratSightingsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                RatSighting sighting = new RatSighting(raw_sighting);
                RatList.ratSightings.add(0, sighting);
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

        private List<RatSighting> dataSet;

        //ViewHolder for each entry in recycler view
        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView itemTitle;
            public TextView itemDate;
            public TextView itemSubtitle;
            public LinearLayout layout;

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

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.itemTitle.setText(dataSet.get(position).getTitle());
            holder.itemDate.setText(dataSet.get(position).getDateStr());
            holder.itemSubtitle.setText(dataSet.get(position).getStreet());
            holder.layout.setOnClickListener(v -> {
                Intent intent = new Intent(getOuter(), RatReportActivity.class);
                RatSighting selected = dataSet.get(position);
                intent.putExtra("RatSighting", dataSet.get(position));
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
            //mDatabase = FirebaseDatabase.getInstance().getReference();

            mDatabase = FirebaseDatabase.getInstance()
            mDatabaseRef = mDatabase.getReference();


        }

        @Override
        protected Integer doInBackground(Integer... params) {
            Query ratSightingsQuery = mDatabase.child("rat_sightings").limitToFirst(params[0]);
            ratSightingsQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    RatList.ratSightings.add(0, sighting);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    int i = RatList.ratSightings.indexOf(sighting);
                    RatList.ratSightings.remove(sighting);
                    RatList.ratSightings.add(i, sighting);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    RatList.ratSightings.remove(sighting);
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


