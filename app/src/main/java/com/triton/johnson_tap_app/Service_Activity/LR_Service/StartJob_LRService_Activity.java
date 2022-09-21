package com.triton.johnson_tap_app.Service_Activity.LR_Service;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyLog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.Service_Activity.BreakdownMRApprovel.MRForms_BreakdownMRActivity;
import com.triton.johnson_tap_app.Service_Activity.BreakdownMRApprovel.StartJob_BreakdownMR_Activity;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Job_status_updateRequest;
import com.triton.johnson_tap_app.responsepojo.Job_status_updateResponse;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartJob_LRService_Activity extends AppCompatActivity {

    FloatingActionButton send;
    TextView text;
    ImageView iv_back;
    String se_user_mobile_no, se_user_name, se_id,check_id, service_title,job_id,message,str_job_status;
    String compno, sertype,status;
    String str_Custname, str_Custno, str_Custremarks,str_Techsign,str_CustAck,str_Quoteno;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_startjob_lrservice);
        context = this;

        send = findViewById(R.id.add_fab);
        text = findViewById(R.id.text);
        iv_back = (ImageView) findViewById(R.id.img_back);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //  service_title = extras.getString("service_title");
            status = extras.getString("status");
            //   Log.e("Name",":" + service_title);
            Log.e("Status", "" + status);
            job_id = extras.getString("job_id");
            Log.e("JobID",""+job_id);
            str_Custname = extras.getString("C_name");
            str_Custno = extras.getString("C_no");
            str_Custremarks = extras.getString("C_remarks");
            str_Techsign = extras.getString("tech_signature");
            str_CustAck = extras.getString("cust_ack");
            Log.e("A", "" + str_Custname);
            Log.e("A", "" + str_Custno);
            Log.e("A", "" + str_Custremarks);
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "Services");
        str_Quoteno = sharedPreferences.getString("quoteno","123");
        Log.e("Name", "" + service_title);
        Log.e("Mobile", ""+ se_user_mobile_no);
        Log.e("QuoteNO", ""+ str_Quoteno);

        Spannable name_Upload = new SpannableString("Start Job ");
        name_Upload.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.append(name_Upload1);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent send = new Intent(context, LR_Details_Activity.class);
                send.putExtra("job_id",job_id);
                send.putExtra("status" , status);
                send.putExtra("C_name" , str_Custname);
                send.putExtra("C_no" , str_Custno);
                send.putExtra("C_remarks" , str_Custremarks);
                send.putExtra("tech_signature", str_Techsign);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addalert();
            }
        });
    }

    private void addalert() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
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
        ll_start.setVisibility(View.GONE);

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
                Intent send = new Intent(context, CustomerDetails_LRServiceActivity.class);
                send.putExtra("job_id",job_id);
                send.putExtra("status" , status);
                send.putExtra("C_name" , str_Custname);
                send.putExtra("C_no" , str_Custno);
                send.putExtra("C_remarks" , str_Custremarks);
                send.putExtra("tech_signature", str_Techsign);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
                dialog.dismiss();
            }
        });

        ll_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_job_status = "Job Paused";
                Job_status_update();
                Intent send = new Intent(context, CustomerDetails_LRServiceActivity.class);
                send.putExtra("job_id",job_id);
                send.putExtra("status" , status);
                send.putExtra("C_name" , str_Custname);
                send.putExtra("C_no" , str_Custno);
                send.putExtra("C_remarks" , str_Custremarks);
                send.putExtra("tech_signature", str_Techsign);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
                dialog.dismiss();
            }
        });

        ll_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_job_status = "Job Stopped";
                Job_status_update();
                Intent send = new Intent(context, CustomerDetails_LRServiceActivity.class);
                send.putExtra("job_id",job_id);
                send.putExtra("status" , status);
                send.putExtra("C_name" , str_Custname);
                send.putExtra("C_no" , str_Custno);
                send.putExtra("C_remarks" , str_Custremarks);
                send.putExtra("tech_signature", str_Techsign);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
                dialog.dismiss();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //  onBackPressed();
            }
        });


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                        Intent intent = new Intent(GroupListActivity.this, AllJobListActivity.class);
//                        intent.putExtra("activity_id",activity_id);
//                        intent.putExtra("status",status);
//                        startActivity(intent);
                overridePendingTransition(R.anim.new_right, R.anim.new_left);
            }
        });

    }


        private void Job_status_update() {

            APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
            Call<Job_status_updateResponse> call = apiInterface.job_status_updateLRResponseCall(com.triton.johnson_tap_app.utils.RestUtils.getContentType(), job_status_updateRequest());
            Log.w(VolleyLog.TAG,"Response url  :%s"+" "+ call.request().url().toString());

            call.enqueue(new Callback<Job_status_updateResponse>() {
                @SuppressLint("LogNotTimber")
                @Override
                public void onResponse(@NonNull Call<Job_status_updateResponse> call, @NonNull Response<Job_status_updateResponse> response) {

                    Log.w(VolleyLog.TAG,"Response" + new Gson().toJson(response.body()));
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
        custom.setJob_id(job_id);
        custom.setStatus(str_job_status);
        custom.setSMU_SCQH_QUOTENO(str_Quoteno);
        Log.w(VolleyLog.TAG,"Request "+ new Gson().toJson(custom));
        return custom;
    }

    @Override
    public void onBackPressed() {
        Intent send = new Intent(context, LR_Details_Activity.class);
        send.putExtra("job_id",job_id);
        send.putExtra("status" , status);
        send.putExtra("C_name" , str_Custname);
        send.putExtra("C_no" , str_Custno);
        send.putExtra("C_remarks" , str_Custremarks);
        send.putExtra("tech_signature", str_Techsign);
        send.putExtra("cust_ack",str_CustAck);
        startActivity(send);
    }
}
