package com.triton.johnson_tap_app.Service_Activity.SiteAudit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.triton.johnson_tap_app.R;


public class MaterialRequest_AuditActivity extends AppCompatActivity {


    Context context;
    ImageView img_Back;
    CardView cv_Yes, cv_No;
    SharedPreferences sharedPreferences;
    String se_user_mobile_no, se_user_name,status,se_id,check_id, service_title,job_id,message,str_job_status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_materialrequest_audit);
        context = this;


        img_Back = findViewById(R.id.img_back);
        cv_Yes = findViewById(R.id.cv_yes);
        cv_No = findViewById(R.id.cv_no);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "Services");

        Log.e("Name", "" + service_title);
        Log.e("Mobile", ""+ se_user_mobile_no);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            status = extras.getString("status");
            Log.e("Status", "" + status);
        }

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  onBackPressed();
                Intent send = new Intent(context, MaterialRequest_AuditActivity.class);
                send.putExtra("status", status);
                startActivity(send);
            }
        });

        cv_Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // onBackPressed();
                Intent send = new Intent(context, AuditMR_Activity.class);
                send.putExtra("status", status);
                startActivity(send);
            }
        });

        cv_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  onBackPressed();
//                Intent send = new Intent(context, LiftAudit_Activity.class);
//                send.putExtra("status", status);
//                startActivity(send);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent send = new Intent(context, MaterialRequest_AuditActivity.class);
        send.putExtra("status", status);
        startActivity(send);
        ///super.onBackPressed();
    }
}
