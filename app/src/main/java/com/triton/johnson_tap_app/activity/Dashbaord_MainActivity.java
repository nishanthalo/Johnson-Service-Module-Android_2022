package com.triton.johnson_tap_app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.triton.johnson_tap_app.New_Screen.Agent_List_New_ScreenActivity;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.session.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class Dashbaord_MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.general)
    Button general;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.services)
    Button services;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.dashboard)
    Button dashboard;
    private SessionManager session;
    private String TAG ="MainActivity";
    private SharedPreferences sharedpreferences;
    LinearLayout logout;
    AlertDialog alertDialog;
    String user_type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashbaord_main);

        logout = (LinearLayout) findViewById(R.id.logout);

        ButterKnife.bind(this);
        Log.w(TAG,"Oncreate -->");
        session = new SessionManager(getApplicationContext());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String login=sharedPreferences.getString("login_execute","false");
        user_type = sharedPreferences.getString("_id", "default value");
        if(login.equals("true")){
            login="true";
        }else {
            Intent intent=new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        }

        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Dashbaord_MainActivity.this, MainActivity.class);
                startActivity(send);

            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Dashbaord_MainActivity.this, Main_Menu_ServicesActivity.class);
                startActivity(send);
            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user_type.equals("Admin")) {

                    Intent send = new Intent(Dashbaord_MainActivity.this, Agent_List_New_ScreenActivity.class);
                    startActivity(send);
                }
               else {
                    alertDialog = new AlertDialog.Builder(Dashbaord_MainActivity.this)
                            .setMessage("You don't have the access")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialog.dismiss();
                                }
                            })
                            .show();
                }

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                alertDialog = new AlertDialog.Builder(Dashbaord_MainActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are youe sure do you want to Logout ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashbaord_MainActivity.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();

                                Toasty.success(getApplicationContext(),"Logout Sucessfully", Toast.LENGTH_SHORT, true).show();
                                Intent send = new Intent(Dashbaord_MainActivity.this, New_LoginActivity.class);
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