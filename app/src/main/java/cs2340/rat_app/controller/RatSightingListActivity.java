package cs2340.rat_app.controller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cs2340.rat_app.R;
import cs2340.rat_app.model.RatSighting;

public class RatSightingListActivity extends AppCompatActivity {

    private static final String TAG = "RatSightingListActivity";
    public ArrayList<RatSighting> ratSightings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sightings_list);

        new LoadLocalData().execute();

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

                while (line != null && count <= 20) {
                    if (isCancelled()) break;
                    data = line.split(",");
                    RatSighting newr = new RatSighting(Integer.parseInt(data[0]), data[1], data[7],
                            data[9], data[23], Integer.parseInt(data[8]), data[16], data[49], data[50]);
                    ratSightings.add(newr);
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

}
