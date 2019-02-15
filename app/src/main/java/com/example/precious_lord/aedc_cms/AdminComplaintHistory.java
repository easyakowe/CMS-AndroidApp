package com.example.precious_lord.aedc_cms;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminComplaintHistory extends AppCompatActivity implements SearchView.OnQueryTextListener{

     RecyclerView recyclerComplaintList;

    // Recent Changes
    private AppCompatActivity activity = AdminComplaintHistory.this;
    Context context = AdminComplaintHistory.this;
    private ArrayList<Complaint> listComplaint;
    private ComplaintRecyclerAdapter complaintRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint_history);
        setTitle(R.string.title_complainthstry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initObjects();


        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("COMP_EMAIL")) {

            //get all needed extras intent

            String email = getIntent().getExtras().getString("COMP_EMAIL");
            String location = getIntent().getExtras().getString("COMP_LOCATION");
            String category = getIntent().getExtras().getString("COMP_CATEGORY");
            String subject = getIntent().getExtras().getString("COMP_NATURE");
            String nature = getIntent().getExtras().getString("COMP_DETAIL");



        }else{

            Toast.makeText(this, "No New Entry", Toast.LENGTH_SHORT).show();

        }
    }
    private void initViews() {
        recyclerComplaintList = (RecyclerView) findViewById(R.id.recyclerComplaintList);
    }

    private void initObjects() {
        listComplaint = new ArrayList<>();
        complaintRecyclerAdapter = new ComplaintRecyclerAdapter(listComplaint, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerComplaintList.setLayoutManager(mLayoutManager);
        recyclerComplaintList.setItemAnimator(new DefaultItemAnimator());
        recyclerComplaintList.setHasFixedSize(true);
        recyclerComplaintList.setAdapter(complaintRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getDataFromSQLite();

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.complaint_search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }


    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listComplaint.clear();

                listComplaint.addAll(databaseHelper. getAllComplaint());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                complaintRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Complaint> newList = new ArrayList<>();

        for (Complaint complaint : listComplaint) {
            String name = complaint.getCategory().toLowerCase();
            if (name.contains(newText)) {
                newList.add(complaint);
            }
        }
        complaintRecyclerAdapter.setFilter(newList);
        return true;
    }
}
