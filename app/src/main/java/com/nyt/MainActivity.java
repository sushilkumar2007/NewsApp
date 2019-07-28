package com.nyt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import adapters.FeedAdapter;
import entites.FeedResponse;
import entites.FieldsDeserilizer;
import entites.Result;
import interfaces.IObjectInterface;
import network.NetworkOperation;
import utils.Constants;

public class MainActivity extends AppCompatActivity implements IObjectInterface, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView feedRecycleView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout emptyView;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private Button retryButton;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Result> items = new ArrayList<>();
    private FeedAdapter feedAdapter = new FeedAdapter(this, this, items);
    private KProgressHUD hud;
    String api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api_key = getResources().getString(R.string.nyt_key);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title);
        feedRecycleView = findViewById(R.id.feed_recycleView);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        emptyView = findViewById(R.id.empty_view);
        emptyTextView = findViewById(R.id.empty_message);
        emptyImageView = findViewById(R.id.empty_imageView);
        linearLayoutManager = new LinearLayoutManager(this);
        feedRecycleView.setLayoutManager(linearLayoutManager);
        feedRecycleView.setAdapter(feedAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        performNetworkOperation(true);
    }

    private void getProgressView(boolean showProgressbar) {
        if (hud != null)
            if (hud.isShowing())
                return;
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2);
        if (showProgressbar)
            hud.show();

    }

    public void dismiss() {
        if (hud != null) {
            hud.dismiss();
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void performNetworkOperation(boolean showProgress) {

        getProgressView(showProgress);
        new NetworkOperation.NetworkBuilder().setContext(this)
                .setMethod(Request.Method.GET)
                .setUrl(Constants.NYT_BASE_URL + api_key)
                .setListener(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dismiss();
                            System.out.print(response);
                            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                                    .registerTypeAdapter(Result.class, new FieldsDeserilizer())
                                    .create();
                            FeedResponse fromJson = gson.fromJson(response.toString(), FeedResponse.class);
                            ArrayList<Result> p = (ArrayList<Result>) fromJson.getResults();
                            if (p.size() > 0) {
                                feedAdapter.clear();
                                feedAdapter.addAll(p);
                                toggleEmptyView(false);
                            } else {
                                toggleEmptyView(true);
                            }


                        } catch (Exception e) {
                            dismiss();
                        }

                    }


                })
                .setParams(new HashMap<String, String>())
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       dismiss();
                    }
                })
                .build().execute();
    }


    @Override
    public void OnItemClick(Object o) {
        Result result = (Result) o;
        Intent i = new Intent();
        i.putExtra(Constants.FEED_ITEM, result);
        i.setClass(this, FeedDetailActivity.class);
        startActivity(i);

    }

    SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                feedAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                feedAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void toggleEmptyView(boolean flag) {

        if (flag) {
            emptyView.setVisibility(View.VISIBLE);
            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setText(getResources().getString(R.string.no_item));
            feedRecycleView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            feedRecycleView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        performNetworkOperation(false);
    }
}
