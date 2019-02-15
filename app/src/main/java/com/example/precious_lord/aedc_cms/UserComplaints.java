package com.example.precious_lord.aedc_cms;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserComplaints extends AppCompatActivity {

    TextView dbEmail;
    TextView dbLocation;
    TextView dbCategory;
    TextView dbSubject;
    TextView dbMessage;

//    Button replyComplaint;
//    Button deleteComplaint;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_complaints);
        setTitle("Complaints");

        dbEmail = findViewById(R.id.dbemail);
        dbLocation = findViewById(R.id.dblocation);
        dbCategory = findViewById(R.id.dbcategory);
        dbSubject = findViewById(R.id.dbsubject);
        dbMessage = findViewById(R.id.dbmessage);

//        replyComplaint = findViewById(R.id.replyComplaint);
//        deleteComplaint = findViewById(R.id.deleteComplaint);

        databaseHelper = new DatabaseHelper(this);

//        deleteComplaint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteComp();
//            }
//        });

    }

    public void deleteComp(){
        if (databaseHelper.deleteComplaint() == 1){
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Unsuccessful", Toast.LENGTH_SHORT).show();
        }

    }
}
