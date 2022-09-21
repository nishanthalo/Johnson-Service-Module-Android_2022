package com.triton.johnson_tap_app.Service_Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnson_tap_app.activity.Main_Menu_ServicesActivity;
import com.triton.johnson_tap_app.PetBreedTypeSelectListener;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.Service_Adapter.ServiceListAdapter;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.ServiceRequest;
import com.triton.johnson_tap_app.responsepojo.ServiceResponse;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesActivity extends AppCompatActivity implements PetBreedTypeSelectListener {

    ImageView iv_back;
    String se_user_mobile_no, se_user_name, se_id,check_id,service_title;
    private List<ServiceResponse.DataBean> breedTypedataBeanList;
    RecyclerView recyclerView;
    String message;
    ServiceListAdapter petBreedTypesListAdapter;
    private String PetBreedType = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_services);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");

        iv_back = (ImageView) findViewById(R.id.iv_back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(ServicesActivity.this, Main_Menu_ServicesActivity.class);
                startActivity(send);

            }
        });

        jobFindResponseCall(se_user_mobile_no);
    }

    private void jobFindResponseCall(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<ServiceResponse> call = apiInterface.ServiceResponseCall(RestUtils.getContentType(), serviceRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<ServiceResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<ServiceResponse> call, @NonNull Response<ServiceResponse> response) {
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
            public void onFailure(@NonNull Call<ServiceResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private ServiceRequest serviceRequest(String job_no) {
        ServiceRequest service = new ServiceRequest();
        service.setUser_mobile_no(job_no);
        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(service));
        return service;
    }

    private void setBreedTypeView(List<ServiceResponse.DataBean> breedTypedataBeanList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter = new ServiceListAdapter(getApplicationContext(), breedTypedataBeanList,this);
        recyclerView.setAdapter(petBreedTypesListAdapter);
    }

    public void petBreedTypeSelectListener(String petbreedtitle, String petbreedid) {
        PetBreedType = petbreedtitle;
    }

    @Override
    public void onBackPressed() {
        Intent send = new Intent(ServicesActivity.this, Main_Menu_ServicesActivity.class);
        startActivity(send);
    }
}