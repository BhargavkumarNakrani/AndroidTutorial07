package com.rku.tutorial07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button button;
    EditText username,password;
    String valusername,valpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login Screen");
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences=getSharedPreferences("loginCheck",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if(sharedPreferences.contains("isLogin")){
            Intent intent = new Intent(this,WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

        button=(Button)findViewById(R.id.btnSubmit);
        username=(EditText)findViewById(R.id.editUserName);
        password=(EditText)findViewById(R.id.editPassword);
    }

    public void loginUser(View view) {
        valusername = username.getText().toString();
        valpassword = password.getText().toString();
        if (valusername.isEmpty()) {
            username.setError("Enter Email Id.");
            return;
        }
        if (valpassword.isEmpty()){
            password.setError("Enter Password");
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(valusername).matches()) {
            //Toast.makeText(LoginActivity.this, "Email is not valid.", Toast.LENGTH_LONG).show();
            username.setError("Please Enter valid Email Address.");
            return;
        }

        if (valusername.equals("admin@gmail.com") &&
                valpassword.equals("admin")) {
            editor.putBoolean("isLogin", true);
            editor.putString("username", valusername);
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else if (databaseHelper.validateData(valusername,valpassword)) {
                editor.putBoolean("isLogin", true);
                editor.putString("username", valusername);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class); // redirecting to LoginActivity.
                startActivity(intent);
                finish();
        }else {
                Toast.makeText(this, "Username or Password is Wrong", Toast.LENGTH_SHORT).show();
        }
    }


    public void register(View view) {
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
    }
}