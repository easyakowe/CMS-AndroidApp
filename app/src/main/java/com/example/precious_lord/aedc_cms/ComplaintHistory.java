package com.example.precious_lord.aedc_cms;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.support.v4.view.MenuItemCompat.getActionView;

public class ComplaintHistory extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static final String EXTRA_EMAIL = "The first activity is: ";
    public static final String EXTRA_ADMIN_ID = "The first admin activity is: ";
    RecyclerView recyclerComplaintList;

    // Recent Changes
    private AppCompatActivity activity = ComplaintHistory.this;
    Context context = ComplaintHistory.this;
    private ArrayList<Complaint> listComplaint;
    private ComplaintRecyclerAdapter complaintRecyclerAdapter;
    private DatabaseHelper databaseHelper;
//    SearchView searchBox;
//    private ArrayList<Complaint> filteredList;
    String userEmail;
    String admin_id;
    String sampleEmail = "frank@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_history);
        setTitle(R.string.title_complainthstry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initObjects();

        extractDataFromIntent();

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("COMP_EMAIL")) {

            //get all needed extras intent

            String email = getIntent().getExtras().getString("COMP_EMAIL");
            String location = getIntent().getExtras().getString("COMP_LOCATION");
            String category = getIntent().getExtras().getString("COMP_CATEGORY");
            String subject = getIntent().getExtras().getString("COMP_NATURE");
            String nature = getIntent().getExtras().getString("COMP_DETAIL");
//            String reply = getIntent().getExtras().getString("COMP_REPLY");



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
// Collecting the user email from Intent
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        userEmail= intent.getStringExtra(EXTRA_EMAIL);
    }

    public static Intent makeIntent(Context context, String email){
        Intent intent = new Intent(context, ComplaintHistory.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;
    }

// Collecting the user email from Intent

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
                if (userEmail.isEmpty()){
                    listComplaint.addAll(databaseHelper.getAllComplaint());
                }else{
                    listComplaint.addAll(databaseHelper. getMyComplaint(userEmail));
                }
//                if (admin_id.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "Login First...", Toast.LENGTH_SHORT).show();
//                }else{
//                    listComplaint.addAll(databaseHelper. getAllComplaint());
//                }

//                listComplaint.addAll(databaseHelper. getMyComplaint("frank@gmail.com"));
//                listComplaint.addAll(databaseHelper. getAllComplaint());

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

        for (Complaint complaint : listComplaint){
            String name = complaint.getCategory().toLowerCase();
            if (name.contains(newText)){
                newList.add(complaint);
            }
        }
        complaintRecyclerAdapter.setFilter(newList);
        return true;
    }



//        recyclerComplaintList = findViewById(R.id.recyclerComplaintList);
//
//        ComplaintRecyclerAdapter complaintRecyclerAdapter = new ComplaintRecyclerAdapter(null, this);
//
//        recyclerComplaintList.setAdapter(complaintRecyclerAdapter);

//        recyclerComplaintList.setAdapter(databaseHelper.getAllComplaint());

//        ListView listView = findViewById(R.id.complaintListView);








//
//        SQLiteDatabase db = databaseHelper.getReadableDatabase();
//        databaseHelper = new DatabaseHelper(this);
////        Cursor cursor = databaseHelper.getListComplaints(db);
//
//        String email, location, category, nature, detail;
//        while (cursor.moveToNext()){
//
//        }
//
//
//
//
//        ArrayList<String> theList = new ArrayList<>();
//
//
//        if (cursor.getCount() == 0){
//            Toast.makeText(this, "The database was empty", Toast.LENGTH_SHORT).show();
//        }else{
//            while(cursor.moveToNext()){
//                theList.add(cursor.getString(8));
//                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
//                try {
////                    listView.setAdapter(listAdapter);
//                }catch (Exception e){
//                    Toast.makeText(getApplicationContext(), "The error is" + e, Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }

}
