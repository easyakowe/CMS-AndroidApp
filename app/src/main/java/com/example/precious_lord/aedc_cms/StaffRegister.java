package com.example.precious_lord.aedc_cms;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StaffRegister extends AppCompatActivity {

    EditText staff_id;
    EditText fullname;
    EditText email;
    EditText phone;
    EditText password;
    EditText confirmPassword;

    Button regButton;
    TextView loginQuestion;

    private DatabaseHelper databaseHelper;
    private Staff staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Staff Registration");

        staff_id = findViewById(R.id.staff_id);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        regButton = findViewById(R.id.regButton);
        loginQuestion = findViewById(R.id.loginQuestion);

        //Initialize Objects

        databaseHelper = new DatabaseHelper(this);
        staff = new Staff();

        // Initialize and assign onClickListeners

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Snackbar.make(v, "Password do not match", Snackbar.LENGTH_SHORT).show();
                }else{
                    postDataToSQLite();
                }
            }
        });

        loginQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    public void postDataToSQLite(){
        if (!databaseHelper.checkStaff(staff_id.getText().toString(), password.getText().toString())){

            staff.setStaff_id(staff_id.getText().toString());
            staff.setFullname(fullname.getText().toString());
            staff.setEmail(email.getText().toString());
            staff.setPhone(phone.getText().toString());
            staff.setPassword(password.getText().toString());

            databaseHelper.addStaff(staff);

            Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();

            Intent regUser = new Intent(this, AdminLogin.class);
            startActivity(regUser);

            emptyEditText();
        }else {
            Toast.makeText(this, "Staff exists already", Toast.LENGTH_LONG).show();
            emptyEditText();
        }
    }

    public void emptyEditText(){
        staff_id.setText(null);
        fullname.setText(null);
        email.setText(null);
        phone.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
    }

}
