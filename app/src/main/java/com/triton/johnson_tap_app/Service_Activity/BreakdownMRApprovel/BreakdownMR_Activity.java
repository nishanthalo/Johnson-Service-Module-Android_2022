package com.triton.johnson_tap_app.Service_Activity.BreakdownMRApprovel;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.Service_Activity.ServicesActivity;
import com.triton.johnson_tap_app.activity.New_LoginActivity;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Count_pasusedRequest;
import com.triton.johnson_tap_app.responsepojo.Count_pasusedResponse;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class BreakdownMR_Activity extends AppCompatActivity {

    ImageView iv_back;
    CardView cv_new_job, cv_pasused_job;
    String service_title,se_user_mobile_no, se_user_name, se_id,check_id,message,paused_count;
    TextView pasused_count,title_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_breakdown_mr);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        cv_new_job = (CardView) findViewById(R.id.cv_new_job);
        cv_pasused_job = (CardView) findViewById(R.id.cv_pasused_job);
        pasused_count = (TextView) findViewById(R.id.pasused_count);
        title_name = (TextView)findViewById(R.id.title_name);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
       // service_title = sharedPreferences.getString("Breakdown MR" , "BREAKDOWN MR APPROVAL");

       // title_name.setText(service_title);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            service_title = extras.getString("service_title");
//            Log.d("title",service_title);
            //title_name.setText(service_title);
        }

        Count_paused();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(BreakdownMR_Activity.this, ServicesActivity.class);
                startActivity(send);

            }
        });

//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("Breakdown MR",service_title);
//        //editor.putString("jobstatus","new");
//        editor.apply();

        cv_new_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BreakdownMR_Activity.this);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("status","new");
//                editor.apply();
                Intent send = new Intent(BreakdownMR_Activity.this, JobDetails_BreakdownMRActivity.class);
                send.putExtra("service_title",service_title);
                send.putExtra("status" , "new");
                startActivity(send);

            }
        });

        cv_pasused_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent send = new Intent(BreakdownMR_Activity.this, PausedServicesBreakdownMR_Activity.class);
                send.putExtra("value","pasused");
                send.putExtra("status" , "pause");
                send.putExtra("service_title",service_title);
                startActivity(send);

            }
        });
    }

    private void Count_paused() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Count_pasusedResponse> call = apiInterface.Count_JobstatuscountCall(RestUtils.getContentType(), count_pasuedRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<Count_pasusedResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Count_pasusedResponse> call, @NonNull retrofit2.Response<Count_pasusedResponse> response) {

                Log.w(TAG,"Job RESPONSE" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            paused_count = response.body().getData().getPaused_count();
                            pasused_count.setText(paused_count);
                        }


                    } else {
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<Count_pasusedResponse> call, @NonNull Throwable t) {
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Count_pasusedRequest count_pasuedRequest() {

        Count_pasusedRequest count = new Count_pasusedRequest();
        count.setUser_mobile_no(se_user_mobile_no);
        count.setService_name(service_title);
        Log.w(TAG,"loginRequest "+ new Gson().toJson(count));
        return count;

    }

    @Override
    public void onBackPressed() {

        Intent send = new Intent(BreakdownMR_Activity.this, ServicesActivity.class);
        startActivity(send);
    }
}