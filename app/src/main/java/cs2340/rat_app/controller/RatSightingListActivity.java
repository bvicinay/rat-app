package cs2340.rat_app.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;

public class RatSightingListActivity extends AppCompatActivity {

    private RecyclerView sightingsRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = "RatSightingListActivity";
    public ArrayList<RatSighting> ratSightings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_sightings_list);

        sightingsRecyclerView = (RecyclerView) findViewById(R.id.RatSightings_list);

        layoutManager = new LinearLayoutManager(this);
        sightingsRecyclerView.setLayoutManager(layoutManager);

        adapter = new RatSightingAdapter(ratSightings);
        sightingsRecyclerView.setAdapter(adapter);

        new LoadLocalData().execute();

        //sightingsRecyclerView.setHasFixedSize(true);

    }

    public class RatSightingAdapter extends RecyclerView.Adapter<RatSightingAdapter.ViewHolder> {

        private ArrayList<RatSighting> dataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView key;

            public ViewHolder(View v) {
                super(v);
                key = v.findViewById(R.id.item_title);
                key.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getOuter(), RatReportActivity.class);
                        intent.putExtra("RatReport", );
                        startActivity(intent);
                    }
                });
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

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.key.setText(dataSet.get(position).toString());

        }
        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return dataSet.size();
        }


    }
    public class LoadLocalData extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Integer doInBackground(String... params) {
            int count = 1;
            try {
                InputStream inputStream = getResources().openRawResource(R.raw.sightings);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                br.readLine(); // skip first line
                String line = br.readLine();
                String[] data;

                while (line != null && count <= 500) {
                    if (isCancelled()) break;
                    data = line.split(",");
                    try {
                        RatSighting newr = new RatSighting(Integer.parseInt(data[0]), data[1], data[7],
                                data[9], data[23], Integer.parseInt(data[8]), data[16], data[49], data[50]);
                        ratSightings.add(newr);
                        adapter.notifyDataSetChanged();
                    } catch (ArrayIndexOutOfBoundsException e) {
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
            }
            return count;
        }
        //protected void onProgressUpdate(Progress...) {}

        protected void onPostExecute(int result) {
            System.out.println("FIISHED " + result);

        }



    }
    public RatSightingListActivity getOuter() {
        return this;
    }

}
