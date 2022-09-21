package com.triton.johnson_tap_app.New_Screen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.activity.Dashbaord_MainActivity;
import com.triton.johnson_tap_app.activity.Main_Menu_ServicesActivity;
import com.triton.johnson_tap_app.activity.New_LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class Customer_Details_New_ScreenActivity extends AppCompatActivity {

   TextView title_job;
   String title;
    LinearLayout logout;
    AlertDialog alertDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_details_new_screen);

        title_job = (TextView) findViewById(R.id.title_job);
        logout = (LinearLayout) findViewById(R.id.logout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title_job");
            title_job.setText(title);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                alertDialog = new AlertDialog.Builder(Customer_Details_New_ScreenActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are youe sure do you want to Logout ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Customer_Details_New_ScreenActivity.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();

                                Toasty.success(getApplicationContext(),"Logout Sucessfully", Toast.LENGTH_SHORT, true).show();
                                Intent send = new Intent(Customer_Details_New_ScreenActivity.this, New_LoginActivity.class);
                                startActivity(send);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        })
                        .show();
            }
        });

    }
}