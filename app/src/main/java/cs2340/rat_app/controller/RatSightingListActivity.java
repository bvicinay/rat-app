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

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

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
            new LoadLocalData().execute();
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

    //AsynchTask that loads in csv file and creates RatSightings with each lines
    public class LoadLocalData extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            Query ratSightingsQuery = mDatabase.child("rat_sightings").limitToFirst(100);
            ratSightingsQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    RatSightingRaw raw_sighting = dataSnapshot.getValue(RatSightingRaw.class);
                    RatSighting sighting = new RatSighting(raw_sighting);
                    RatSighting.ratSightings.add(0, sighting);
                    adapter.notifyDataSetChanged();

                    //Log.d(TAG, sighting.toString());

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        @Override
        protected void onPostExecute(Integer result) {
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Integer doInBackground(String... params) {
            int count = 1;
            /*try {
                InputStream inputStream = getResources().openRawResource(R.raw.sightings);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                br.readLine(); // skip first line
                String line = br.readLine();
                String[] data;

                while (line != null && count <= 500) {
                    if (isCancelled()) break;
                    data = line.split(",");
                    try {
                        RatSightingRaw newr = new RatSightingRaw(Integer.parseInt(data[0]), data[1], data[7],
                                data[9], data[23], Integer.parseInt(data[8]), data[16], data[49], data[50]);
                        RatSightingRaw.ratSightings.add(0, newr);
                        if (count % 100 == 0) {
                            adapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        // Skip item if data is invalid
                        Log.d(TAG, "Could not parse data point: " + e.getMessage(), e);
                    }

                    //adapter.notifyDataSetChanged();
                    line = br.readLine();
                    count++;
                    Log.d(TAG, data[0]);

                }
                return count;

            } catch (IOException e) {
                Log.d(TAG, e.getMessage(), e);
            }*/
            return count;
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


