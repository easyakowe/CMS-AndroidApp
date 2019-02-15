package com.example.precious_lord.aedc_cms;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class ComplaintActivity extends AppCompatActivity {

    EditText conf_email;
    EditText location;

    RadioButton rgcomplainttype_complaint;
    RadioButton rgcomplainttype_genquery;

    RadioButton rgsubcategory_ps;
    RadioButton rgsubcategory_bill;
    RadioButton rgsubcategory_others;

    RadioButton rgcategory_elect;
    RadioButton rgcategory_gen;

    EditText subject;
    EditText message;

    CardView btnSubmit;
    CardView btnViewComplaint;

    String compType = null;
    String subCat = null;
    String cat = null;
    String userEmailIntent;

    private DatabaseHelper databaseHelper;
    private Complaint complaint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        setTitle(R.string.title_complaintpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views

        conf_email = findViewById(R.id.conf_email);
        location = findViewById(R.id.location);

        rgcomplainttype_complaint = findViewById(R.id.rgcomplainttype_complaint);
        rgcomplainttype_genquery = findViewById(R.id.rgcomplainttype_genquery);

        rgsubcategory_ps = findViewById(R.id.rgsubcategory_ps);
        rgsubcategory_bill = findViewById(R.id.rgsubcategory_bill);
        rgsubcategory_others = findViewById(R.id.rgsubcategory_others);

        rgcategory_elect = findViewById(R.id.rgcategory_elect);
        rgcategory_gen = findViewById(R.id.rgcategory_gen);

        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnViewComplaint = findViewById(R.id.btnViewComplaint);

        // Initialize Objects

        databaseHelper = new DatabaseHelper(this);
        complaint = new Complaint();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conf_email.getText().toString().isEmpty() || location.getText().toString().isEmpty()
                        || compType.isEmpty() || cat.isEmpty() || subCat.isEmpty() ||
                        subject.getText().toString().isEmpty() || message.getText().toString().isEmpty()){
                    Snackbar.make(v, "Fill in field(s)", Snackbar.LENGTH_SHORT).show();
                }else{
                    postComplainToSQLite(v);
                }
            }
        });

        btnViewComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v, "Work in progress...", Snackbar.LENGTH_LONG).show();
//                Intent viewIntent = new Intent(getApplicationContext(), ComplaintHistory.class);
//                startActivity(viewIntent);
//                userEmailIntent = conf_email.getText().toString();
                if (conf_email.getText().toString().isEmpty()){
                    Snackbar.make(v, "Confirm your email first", Snackbar.LENGTH_SHORT).show();
                }else{
                    Intent viewIntent = ComplaintHistory.makeIntent(ComplaintActivity.this, conf_email.getText().toString());
                    startActivity(viewIntent);
                }

            }
        });
    }

    public void complaintType(View v){
        boolean checked = ((RadioButton)v).isChecked();

        switch (v.getId()){

                // For Complaint type
            case R.id.rgcomplainttype_complaint:
                    compType = rgcomplainttype_complaint.getText().toString();
                break;

            case R.id.rgcomplainttype_genquery:
                    compType = rgcomplainttype_genquery.getText().toString();
                break;

                // For Subcategory
            case R.id.rgsubcategory_ps:
                subCat = rgsubcategory_ps.getText().toString();
                break;

            case R.id.rgsubcategory_bill:
                subCat = rgsubcategory_bill.getText().toString();
                break;

            case R.id.rgsubcategory_others:
                subCat = rgsubcategory_others.getText().toString();
                break;

                // For Category
            case R.id.rgcategory_elect:
                cat = rgcategory_elect.getText().toString();
                break;

            case R.id.rgcategory_gen:
                cat = rgcategory_gen.getText().toString();
                break;
        }
    }

    public void postComplainToSQLite(View v) {
        if (cat.isEmpty() && subCat.isEmpty() && compType.isEmpty()){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            emptyFields();
        }else{
            complaint.setEmail(conf_email.getText().toString());
            complaint.setLocation(location.getText().toString());
            complaint.setCategory(cat);
            complaint.setSub_category(subCat);
            complaint.setComplaint_type(compType);
            complaint.setComplaint_nature(subject.getText().toString());
            complaint.setComplaint_detail(message.getText().toString());

            databaseHelper.addComplaint(complaint);

            Snackbar.make(v, "Complaint Tendered Successfully", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(this, "Complain tendered Successfully", Toast.LENGTH_LONG).show();
            emptyFields();
        }
    }


    public void emptyFields(){
        location.setText(null);
        subject.setText(null);
        message.setText(null);
        rgcomplainttype_complaint.setChecked(false);
        rgcomplainttype_genquery.setChecked(false);
        rgsubcategory_ps.setChecked(false);
        rgsubcategory_bill.setChecked(false);
        rgsubcategory_others.setChecked(false);
        rgcategory_elect.setChecked(false);
        rgcategory_gen.setChecked(false);
    }
}
