package com.example.precious_lord.aedc_cms;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    EditText current_pass;
    EditText old_email;
    EditText change_email;
    EditText confirm_newEmail;

    Button btnChange;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle(R.string.title_editaccount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        old_email = findViewById(R.id.old_email);
        change_email = findViewById(R.id.change_email);
        confirm_newEmail = findViewById(R.id.confirm_newEmail);
        current_pass = findViewById(R.id.current_pass);

        btnChange = findViewById(R.id.btnChange);

        databaseHelper = new DatabaseHelper(this);

        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!change_email.getText().toString().equals(confirm_newEmail.getText().toString())) {
//
            Toast.makeText(this, "Unsuccessful. New emails do not match", Toast.LENGTH_LONG).show();

        }
        else if (current_pass.getText().toString().isEmpty()){

            Toast.makeText(this, "Unsuccessful. Enter Password...", Toast.LENGTH_LONG).show();


        }else if (old_email.getText().toString().isEmpty() || change_email.getText().toString().isEmpty()
                || confirm_newEmail.getText().toString().isEmpty()){

            Toast.makeText(this, "Fill in empty fields...", Toast.LENGTH_LONG).show();

        }
        else{
            databaseHelper.updateEmail(old_email.getText().toString(), change_email.getText().toString(),
                    current_pass.getText().toString());
//
            Toast.makeText(this, "Email changed successfully", Toast.LENGTH_LONG).show();
            emptyFields();
        }
    }

    public void emptyFields(){
        current_pass.setText(null);
        old_email.setText(null);
        change_email.setText(null);
        confirm_newEmail.setText(null);
    }
}
