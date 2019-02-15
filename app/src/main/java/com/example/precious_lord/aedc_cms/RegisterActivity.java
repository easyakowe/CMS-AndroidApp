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

public class RegisterActivity extends AppCompatActivity {

    EditText fullname;
    EditText email;
    EditText phone;
    EditText password;
    EditText confirmPassword;

    Button regButton;
    TextView loginQuestion;

    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.text_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Initialize views

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        regButton = findViewById(R.id.regButton);
        loginQuestion = findViewById(R.id.loginQuestion);

        //Initialize Objects

        databaseHelper = new DatabaseHelper(this);
        user = new User();

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
        if (!databaseHelper.checkUser(email.getText().toString(), password.getText().toString())){

            user.setFullname(fullname.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPhone(phone.getText().toString());
            user.setPassword(password.getText().toString());

            databaseHelper.addUser(user);

            Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();

            Intent regUser = new Intent(this, LoginActivity.class);
            startActivity(regUser);

            emptyEditText();
        }else {
            Toast.makeText(this, "User exists already", Toast.LENGTH_LONG).show();
            emptyEditText();
        }
    }

    public void emptyEditText(){
        fullname.setText(null);
        email.setText(null);
        phone.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
    }
}
