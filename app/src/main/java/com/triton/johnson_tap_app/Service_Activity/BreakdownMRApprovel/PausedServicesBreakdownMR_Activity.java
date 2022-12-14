package com.triton.johnson_tap_app.Service_Activity.BreakdownMRApprovel;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnson_tap_app.PetBreedTypeSelectListener;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.Service_Activity.PreventiveMRApproval.PreventiveMR_Activity;
import com.triton.johnson_tap_app.Service_Adapter.PasusedListAdapter_BreakDownMR;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Pasused_ListRequest;
import com.triton.johnson_tap_app.responsepojo.Pasused_ListResponse;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PausedServicesBreakdownMR_Activity extends AppCompatActivity implements PetBreedTypeSelectListener {

    ImageView iv_back;
    String value="";
    String se_user_mobile_no, se_user_name, se_id,check_id,service_title;
    RecyclerView recyclerView;
    private List<Pasused_ListResponse.DataBean> breedTypedataBeanList;
    String message, status;
    PasusedListAdapter_BreakDownMR petBreedTypesListAdapter;
    private String PetBreedType = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_paused_services_breakdown_mractivity);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
       // job_status = sharedPreferences.getString("jobstatus", "A");

       // Log.e("Status", " : "+ job_status);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("value");
        }
        if (extras != null) {
            service_title = extras.getString("service_title");
            status = extras.getString("status");
            Log.e("Name",":" + service_title);
            Log.e("Status", "" + status);
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

              //  if(value.equals("pasused")){

                    Intent send = new Intent(PausedServicesBreakdownMR_Activity.this, BreakdownMR_Activity.class);
                    //send.putExtra("service_title",service_title);
                     send.putExtra("status", status);
                     send.putExtra("service_title",service_title);
                    startActivity(send);
               // }
              //  else {
                   // Intent send = new Intent(PausedServicesBreakdownMR_Activity.this, PausedJobs_CustomerDetailsBreakdownMR_Activity.class);
                    //startActivity(send);
              //  }

            }
        });

        jobFindResponseCall(se_user_mobile_no,service_title);

    }

    private void jobFindResponseCall(String job_no, String title) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Pasused_ListResponse> call = apiInterface.Pasused_listResponseBMRCall(RestUtils.getContentType(), serviceRequest(job_no,title));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<Pasused_ListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Pasused_ListResponse> call, @NonNull Response<Pasused_ListResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    message = response.body().getMessage();
                    Log.d("message", message);

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();

                            setBreedTypeView(breedTypedataBeanList);
                            Log.d("dataaaaa", String.valueOf(breedTypedataBeanList));

                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<Pasused_ListResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Pasused_ListRequest serviceRequest(String job_no, String title) {
        Pasused_ListRequest service = new Pasused_ListRequest();
        service.setUser_mobile_no(job_no);
        service.setService_name(title);
        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(service));
        return service;
    }

    private void setBreedTypeView(List<Pasused_ListResponse.DataBean> breedTypedataBeanList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter = new PasusedListAdapter_BreakDownMR(getApplicationContext(), breedTypedataBeanList,this, status);
        recyclerView.setAdapter(petBreedTypesListAdapter);
    }

    public void petBreedTypeSelectListener(String petbreedtitle, String petbreedid) {
        PetBreedType = petbreedtitle;
    }

    @Override
    public void onBackPressed() {
        Intent send = new Intent(PausedServicesBreakdownMR_Activity.this, BreakdownMR_Activity.class);
        send.putExtra("status", status);
        send.putExtra("service_title",service_title);
        startActivity(send);
    }
}