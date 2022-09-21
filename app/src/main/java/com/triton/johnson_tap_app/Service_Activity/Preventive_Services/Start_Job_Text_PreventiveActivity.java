package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import static com.android.volley.VolleyLog.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Check_Pod_StatusRequest;
import com.triton.johnson_tap_app.requestpojo.Job_statusRequest;
import com.triton.johnson_tap_app.requestpojo.Job_status_updateRequest;
import com.triton.johnson_tap_app.responsepojo.Check_Pod_StatusResponse;
import com.triton.johnson_tap_app.responsepojo.Job_statusResponse;
import com.triton.johnson_tap_app.responsepojo.Job_status_updateResponse;
import com.triton.johnson_tap_app.utils.RestUtils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class Start_Job_Text_PreventiveActivity extends AppCompatActivity {

    FloatingActionButton send;
    TextView text;
    ImageView iv_back;
    String se_user_mobile_no, se_user_name, se_id,check_id, service_title,str_job_id="",message,str_job_status,str_status;
    AlertDialog alertDialog1;
    CharSequence[] values = {"POD","SEMPOD","MOD","ESC/TRV"};
    String compno, sertype;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_start_job_text_preventive);

        send = findViewById(R.id.add_fab);
        text = findViewById(R.id.text);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_job_id = extras.getString("job_id");
        }

        if (extras != null) {
            service_title = extras.getString("service_title");
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "default value");

        compno = sharedPreferences.getString("compno","123");
        sertype = sharedPreferences.getString("sertype","123");

        Spannable name_Upload = new SpannableString("Start Job ");
        name_Upload.setSpan(new ForegroundColorSpan(Start_Job_Text_PreventiveActivity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.append(name_Upload1);

        Job_status();

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Start_Job_Text_PreventiveActivity.this);
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
                        Check_Pod_Status();
                        dialog.dismiss();
                    }
                });

                ll_pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        str_job_status = "Job Paused";
                        Job_status_update();
                        Check_Pod_Status();
                        dialog.dismiss();
                    }
                });

                ll_stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        str_job_status = "Job Stopped";
                        Job_status_update();
                        Check_Pod_Status();
                        dialog.dismiss();
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

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent send = new Intent(Start_Job_Text_PreventiveActivity.this, Customer_Details_preActivity.class);
                startActivity(send);
            }
        });
    }

    private void Job_status() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Job_statusResponse> call = apiInterface.job_status_PreventiveResponseCall(RestUtils.getContentType(), job_statusRequest());
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
        Call<Job_status_updateResponse> call = apiInterface.job_status_update_PreventiveResponseCall(RestUtils.getContentType(), job_status_updateRequest());
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

    private void Check_Pod_Status() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Check_Pod_StatusResponse> call = apiInterface.Check_Pod_StatusResponseCall(RestUtils.getContentType(), count_pasuedRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<Check_Pod_StatusResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Check_Pod_StatusResponse> call, @NonNull retrofit2.Response<Check_Pod_StatusResponse> response) {

                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            str_job_status = response.body().getData().getStatus();

//                            Intent send3 = new Intent(Start_Job_Text_PreventiveActivity.this, Esc_TrvActivity.class);
//                            send3.putExtra("job_id",str_job_id);
//                            send3.putExtra("value","ESC/TRV");
//                            send3.putExtra("service_title",service_title);
//                            startActivity(send3);

                            if(str_job_status.equals("POD")){
                                Intent send = new Intent(Start_Job_Text_PreventiveActivity.this, Monthlist_Preventive_Activity.class);
                                send.putExtra("job_id",str_job_id);
                                send.putExtra("value","POD");
                                send.putExtra("service_title",service_title);
                                startActivity(send);
                            }
                            else if(str_job_status.equals("SEMPOD")){
                                Intent send1 = new Intent(Start_Job_Text_PreventiveActivity.this, Monthlist_Preventive_Activity.class);
                                send1.putExtra("job_id",str_job_id);
                                send1.putExtra("value","SEMPOD");
                                send1.putExtra("service_title",service_title);
                                startActivity(send1);
                            }
                            else if(str_job_status.equals("MOD")){
                                Intent send2 = new Intent(Start_Job_Text_PreventiveActivity.this, Monthlist_Preventive_Activity.class);
                                send2.putExtra("job_id",str_job_id);
                                send2.putExtra("value","MOD");
                                send2.putExtra("service_title",service_title);
                                startActivity(send2);
                            }
                            else {
                                Intent send3 = new Intent(Start_Job_Text_PreventiveActivity.this, ESCTRV.class);
                                send3.putExtra("job_id",str_job_id);
                                send3.putExtra("value","ESC/TRV");
                                send3.putExtra("service_title",service_title);
                                startActivity(send3);
                            }
                        }


                    } else {
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<Check_Pod_StatusResponse> call, @NonNull Throwable t) {
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Check_Pod_StatusRequest count_pasuedRequest() {

        Check_Pod_StatusRequest count = new Check_Pod_StatusRequest();
        count.setUser_mobile_no(se_user_mobile_no);
        count.setJob_id(str_job_id);
        Log.w(TAG,"loginRequest "+ new Gson().toJson(count));
        return count;
    }
}