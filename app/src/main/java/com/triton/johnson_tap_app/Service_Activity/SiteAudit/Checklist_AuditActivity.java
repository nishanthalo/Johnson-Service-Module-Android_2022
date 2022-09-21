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


public class Checklist_AuditActivity extends AppCompatActivity {

    Context context;
    ImageView img_Back;
    CardView cv_Lift, cv_Escalator;
    String se_user_mobile_no, se_user_name,status,se_id,check_id, service_title,job_id,message,str_job_status;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_checklist_audit);
        context = this;

        img_Back = findViewById(R.id.img_back);
        cv_Lift = findViewById(R.id.cv_lift);
        cv_Escalator = findViewById(R.id.cv_escalator);

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

                onBackPressed();

//                Intent send = new Intent(context, StartJob_AuditActivity.class);
//                send.putExtra("status", status);
//                startActivity(send);
            }
        });

        cv_Lift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent send = new Intent(context, AuditChecklist.class);
                send.putExtra("status", status);
                send.putExtra("service_type","L");
                startActivity(send);
            }
        });

        cv_Escalator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent send = new Intent(context, AuditChecklist.class);
                send.putExtra("status", status);
                send.putExtra("service_type","E");
                startActivity(send);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent send = new Intent(context, StartJob_AuditActivity.class);
//        send.putExtra("status", status);
//        startActivity(send);
    }
}
