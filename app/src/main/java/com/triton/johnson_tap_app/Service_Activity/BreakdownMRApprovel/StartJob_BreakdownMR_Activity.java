package com.triton.johnson_tap_app.Service_Activity.BreakdownMRApprovel;

import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Job_statusRequest;
import com.triton.johnson_tap_app.requestpojo.Job_status_updateRequest;
import com.triton.johnson_tap_app.responsepojo.Job_statusResponse;
import com.triton.johnson_tap_app.responsepojo.Job_status_updateResponse;
import com.triton.johnson_tap_app.utils.RestUtils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class StartJob_BreakdownMR_Activity extends AppCompatActivity {

    FloatingActionButton send;
    TextView text;
    ImageView iv_back;
    String se_user_mobile_no, se_user_name, se_id,check_id, service_title,str_job_id,message,str_job_status;
    String compno, sertype,status;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_start_job_breakdown_mr);

        send = findViewById(R.id.add_fab);
        text = findViewById(R.id.text);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_job_id = extras.getString("job_id");
            status = extras.getString("status");
            Log.e("Status", "" + status);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "default value");

        compno = sharedPreferences.getString("compno","123");
        sertype = sharedPreferences.getString("sertype","123");


        Spannable name_Upload = new SpannableString("Start Job ");
        name_Upload.setSpan(new ForegroundColorSpan(StartJob_BreakdownMR_Activity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.append(name_Upload1);

        Job_status();

        if (status.equals("pause")){
            Log.e("Inside", "Paused Job");

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent send = new Intent(StartJob_BreakdownMR_Activity.this, MRForms_BreakdownMRActivity.class);
                    send.putExtra("job_id",str_job_id);
                    send.putExtra("status", status);
                    startActivity(send);
                }
            });

        }else{
            Log.e("Inside",  "New Job");

            send.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(StartJob_BreakdownMR_Activity.this);
                    View mView = getLayoutInflater().inflate(R.layout.startjob_popup_layout, null);

                    TextView txt_jobstatus = mView.findViewById(R.id.txt_jobstatus);
                    TextView txt_job_content = mView.findViewById(R.id.txt_job_content);
                    LinearLayout ll_start = mView.findViewById(R.id.ll_start);
                    LinearLayout ll_pause = mView.findViewById(R.id.ll_pause);
                    LinearLayout ll_stop = mView.findViewById(R.id.ll_stop);
                    LinearLayout ll_resume = mView.findViewById(R.id.ll_resume);
                    ImageView img_close = mView.findViewById(R.id.img_close);
                    Button btn_back = mView.findViewById(R.id.btn_back);
                    btn_back.setVisibility(View.GONE);
                    txt_jobstatus.setVisibility(View.GONE);
                    ll_resume.setVisibility(View.GONE);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    try{
                        if(message.equals("Not Started")){
                            ll_pause.setVisibility(View.GONE);
                            ll_stop.setVisibility(View.GONE);
                            ll_start.setVisibility(View.VISIBLE);
                        }
                        else if(message.equals("Job Started")){
                            ll_pause.setVisibility(View.VISIBLE);
                            ll_stop.setVisibility(View.VISIBLE);
                            ll_start.setVisibility(View.GONE);
                        }
                        else if(message.equals("Job Paused")){
                            ll_pause.setVisibility(View.GONE);
                            ll_stop.setVisibility(View.VISIBLE);
                            ll_start.setVisibility(View.VISIBLE);
                        }
                        else if(message.equals("Job Stopped")){
                            ll_pause.setVisibility(View.GONE);
                            ll_stop.setVisibility(View.GONE);
                            ll_start.setVisibility(View.VISIBLE);
                        }
                        else {

                        }
                    } catch (NullPointerException e){
                        e.printStackTrace();
                    }


                    ll_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            str_job_status = "Job Started";
                            Job_status_update();
                            Intent send = new Intent(StartJob_BreakdownMR_Activity.this, MRForms_BreakdownMRActivity.class);
                            send.putExtra("job_id",str_job_id);
                            send.putExtra("status", status);
                            startActivity(send);
                            dialog.dismiss();


                        }
                    });

                    ll_pause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            str_job_status = "Job Paused";
                            Job_status_update();
                            Intent send = new Intent(StartJob_BreakdownMR_Activity.this, MRForms_BreakdownMRActivity.class);
                            send.putExtra("job_id",str_job_id);
                            send.putExtra("status", status);
                            startActivity(send);
                        }
                    });

                    ll_stop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            str_job_status = "Job Stopped";
                            Job_status_update();
                            Intent send = new Intent(StartJob_BreakdownMR_Activity.this, MRForms_BreakdownMRActivity.class);
                            send.putExtra("job_id",str_job_id);
                            send.putExtra("status", status);
                            startActivity(send);
                        }
                    });
                    btn_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });


                    img_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            overridePendingTransition(R.anim.new_right, R.anim.new_left);
                        }
                    });



                }
            });
        }

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:com.example.myapplication"));
                startActivity(intent);
            }
        });



        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent send = new Intent(StartJob_BreakdownMR_Activity.this, CustomerDetailsBreakdownMR_Activity.class);
                send.putExtra("job_id",str_job_id);
                send.putExtra("status", status);
                startActivity(send);
            }
        });
    }

    private void Job_status() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Job_statusResponse> call = apiInterface.CheckworkStatusCall(RestUtils.getContentType(), job_statusRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<Job_statusResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Job_statusResponse> call, @NonNull retrofit2.Response<Job_statusResponse> response) {

                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            Log.d("msg",message);
                        }


                    } else {
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<Job_statusResponse> call, @NonNull Throwable t) {
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Job_statusRequest job_statusRequest() {

        Job_statusRequest custom = new Job_statusRequest();
        custom.setUser_mobile_no(se_user_mobile_no);
        custom.setService_name(service_title);
        custom.setJob_id(str_job_id);
        custom.setSMU_SCH_COMPNO(compno);
        custom.setSMU_SCH_SERTYPE(sertype);
        Log.e("CompNo",""+compno);
        Log.e("SertYpe", ""+sertype);
        Log.w(TAG,"loginRequest "+ new Gson().toJson(custom));
        return custom;
    }

    private void Job_status_update() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Job_status_updateResponse> call = apiInterface.BreakdownMrJobWorkStatusResponseCall(RestUtils.getContentType(), job_status_updateRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<Job_status_updateResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Job_status_updateResponse> call, @NonNull retrofit2.Response<Job_status_updateResponse> response) {

                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            Log.d("msg",message);
                        }


                    } else {
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<Job_status_updateResponse> call, @NonNull Throwable t) {
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Job_status_updateRequest job_status_updateRequest() {

        Job_status_updateRequest custom = new Job_status_updateRequest();
        custom.setUser_mobile_no(se_user_mobile_no);
        custom.setService_name(service_title);
        custom.setJob_id(str_job_id);
        custom.setStatus(str_job_status);
        custom.setSMU_SCH_COMPNO(compno);
        custom.setSMU_SCH_SERTYPE(sertype);
        Log.e("CompNo",""+compno);
        Log.e("SertYpe", ""+sertype);
        Log.w(TAG,"loginRequest "+ new Gson().toJson(custom));
        return custom;
    }

    @Override
    public void onBackPressed() {
        Intent send = new Intent(StartJob_BreakdownMR_Activity.this, CustomerDetailsBreakdownMR_Activity.class);
        send.putExtra("job_id",str_job_id);
        send.putExtra("status", status);
        startActivity(send);
    }
}