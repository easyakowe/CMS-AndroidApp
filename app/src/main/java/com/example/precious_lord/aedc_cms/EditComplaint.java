package com.example.precious_lord.aedc_cms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditComplaint extends AppCompatActivity {

    public static final String EXTRA_EMAIL = "The email is: ";
    public static final String EXTRA_LOCATION = "The location is: ";
    public static final String EXTRA_CATEGORY = "The category is: ";
    public static final String EXTRA_SUBJECT = "The subject is: ";
    public static final String EXTRA_MESSAGE = "The message is: ";
    public static final String EXTRA_REPLY = "The reply is: ";

    TextView emailFromRecycler;
    TextView locationFromRecycler;
    TextView categoryFromRecycler;
    EditText subjectFromRecycler;
    EditText messageFromRecycler;
    TextView replyFromStaff;

    Button confirm;
    TextView clickToReply;

    LinearLayout verifyStaff_lyt;
    EditText verifyStaffID;
    EditText verifyStaffPass;
    Button verifyEntry;

    LinearLayout giveReply_lyt;
    EditText reply_txt;
    Button sendReply;

    String theEmail;
    String theLocation;
    String theCategory;
    String theSubject;
    String theMessage;
    String theReply;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailFromRecycler = findViewById(R.id.emailFromRecycler);
        locationFromRecycler = findViewById(R.id.locationFromRecycler);
        categoryFromRecycler = findViewById(R.id.categoryFromRecycler);
        subjectFromRecycler = findViewById(R.id.subjectFromRecycler);
        messageFromRecycler = findViewById(R.id.messageFromRecycler);
        replyFromStaff = findViewById(R.id.replyFromStaff);
        confirm = findViewById(R.id.confirm);
        clickToReply = findViewById(R.id.clickToReply);

        verifyStaff_lyt = findViewById(R.id.verifyStaff_lyt);
        verifyStaffID = findViewById(R.id.verifyStaffID);
        verifyStaffPass = findViewById(R.id.verifyStaffPass);
        verifyEntry = findViewById(R.id.verifyEntry);

        giveReply_lyt = findViewById(R.id.giveReply_lyt);
        reply_txt = findViewById(R.id.reply_txt);
        sendReply = findViewById(R.id.sendReply);

        databaseHelper = new DatabaseHelper(this);

        extractDataFromIntent();

        //assign to each view data from Intent
        emailFromRecycler.setText(theEmail);
        locationFromRecycler.setText(theLocation);
        categoryFromRecycler.setText(theCategory);
        subjectFromRecycler.setText(theSubject);
        messageFromRecycler.setText(theMessage);
        replyFromStaff.setText(theReply);

        if (!replyFromStaff.getText().toString().isEmpty()){
            confirm.setEnabled(false);
            subjectFromRecycler.setEnabled(false);
            messageFromRecycler.setEnabled(false);
            clickToReply.setVisibility(View.GONE);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageFromRecycler.getText().toString().isEmpty()){
                    Snackbar.make(v, "No content", Snackbar.LENGTH_SHORT).show();
                }else{
                    databaseHelper.editComplaint(subjectFromRecycler.getText().toString(), messageFromRecycler.getText().toString());
                    Snackbar.make(v, "Complaint Edited", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        clickToReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStaff_lyt.setVisibility(View.VISIBLE);
                clickToReply.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
            }
        });

        verifyEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFromSQLite();
            }
        });

        sendReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.replyComplaint(messageFromRecycler.getText().toString(), reply_txt.getText().toString());
                Snackbar.make(v, "Reply Submitted", Snackbar.LENGTH_SHORT).show();
                replyFromStaff.setText(reply_txt.getText().toString());
                reply_txt.setText(null);
            }
        });
    }

    private void verifyFromSQLite(){
        if (databaseHelper.checkStaff(verifyStaffID.getText().toString(), verifyStaffPass.getText().toString())){
            giveReply_lyt.setVisibility(View.VISIBLE);
            verifyStaff_lyt.setVisibility(View.GONE);
            emptyFields();

        }else{
            Toast.makeText(this, "Invalid Entry", Toast.LENGTH_SHORT).show();
            emptyFields();
        }
    }

    public void emptyFields(){
        verifyStaffID.setText(null);
        verifyStaffPass.setText(null);
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        theEmail= intent.getStringExtra(EXTRA_EMAIL);
        theLocation= intent.getStringExtra(EXTRA_LOCATION);
        theCategory= intent.getStringExtra(EXTRA_CATEGORY);
        theSubject= intent.getStringExtra(EXTRA_SUBJECT);
        theMessage= intent.getStringExtra(EXTRA_MESSAGE);
        theReply= intent.getStringExtra(EXTRA_REPLY);

    }

    public static Intent makeIntent(Context context, String email, String location, String category,
                                    String subject, String message, String reply){

        Intent intent = new Intent(context, EditComplaint.class);
        intent.putExtra(EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_LOCATION, location);
        intent.putExtra(EXTRA_CATEGORY, category);
        intent.putExtra(EXTRA_SUBJECT, subject);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_REPLY, reply);
        return intent;
    }

}
