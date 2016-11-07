package ahhhlvin.c4q.nyc.irokotest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private GiphyRecyclerViewAdapter recyclerViewAdapter;
    private List<GiphyObject> gifList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        gifList = new ArrayList<>();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.resultsList);
        mRecyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);

        recyclerViewAdapter = new GiphyRecyclerViewAdapter(getApplicationContext(), gifList, mRecyclerView);
        mRecyclerView.setAdapter(recyclerViewAdapter);

        SearchView search = (SearchView) findViewById(R.id.searchView);

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                gifList.clear();
                myAsyncTask mAsyncTask = new myAsyncTask();
                mAsyncTask.execute(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    class myAsyncTask extends AsyncTask<String, Void, String> {

        String url;

        @Override
        protected String doInBackground(String... params) {

            url = "http://api.giphy.com/v1/gifs/search?q=" + Arrays.toString(params) + "&api_key=dc6zaTOxFJmzC";
            return getGifResults(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equalsIgnoreCase("Exception caught")) {
                Toast.makeText(getApplicationContext(), "No Results Found", Toast.LENGTH_SHORT).show();
            } else {
                recyclerViewAdapter.notifyDataSetChanged();
                System.out.println("SETTING UPDATED ADAPTER AND GIF LIST");
            }
        }
    }

    public String getGifResults(String url) {

        try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response responses;

                try {
                    GiphyObject giphyObj;
                    responses = client.newCall(request).execute();

                    String jsonData = responses.body().string();
                    JSONObject jsonObj = new JSONObject(jsonData);
                    JSONArray gifResultArray = jsonObj.getJSONArray("data");

                    for (int i = 0; i < gifResultArray.length(); i++) {

                        JSONObject obj = gifResultArray.getJSONObject(i);
                        JSONObject images = obj.getJSONObject("images");
                        JSONObject downsizedImageObj = images.getJSONObject("downsized");
                        JSONObject highDefImageObj = images.getJSONObject("original");

                        giphyObj = new GiphyObject();
                        giphyObj.setGifURL(downsizedImageObj.getString("url"));
                        giphyObj.setHighDefGifURL(highDefImageObj.getString("url"));

                        gifList.add(giphyObj);
                    }

                    responses.body().close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            return("OK");

        } catch (JSONException e) {
            e.printStackTrace();
            return ("Exception Caught");
        }
    }
}
