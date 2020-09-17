package com.rku.tutorial07;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String username;
    TextView txtWelcome;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sharedPreferences = getSharedPreferences("loginCheck", MODE_PRIVATE);
        txtWelcome = findViewById(R.id.txtWelcome);

        username = sharedPreferences.getString("username", "");
        editor = sharedPreferences.edit();
        txtWelcome.setText(txtWelcome.getText().toString() + ", " + username);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation Dialog")
                .setCancelable(false)
                .setMessage("Are Sure Yout Want To Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.remove("isLogin");
                        editor.remove("username");
                        editor.commit();
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                AlertDialog dialog=builder.create();
                dialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}