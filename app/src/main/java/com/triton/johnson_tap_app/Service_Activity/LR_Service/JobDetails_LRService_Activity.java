package com.triton.johnson_tap_app.Service_Activity.LR_Service;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.JobListRequest;
import com.triton.johnson_tap_app.responsepojo.JobListResponse;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetails_LRService_Activity extends AppCompatActivity {

    ImageView img_back,img_clearsearch;
    RecyclerView recyclerView;
    EditText edtsearch;
    TextView txt_no_records;
    JobListAdapter_LRService petBreedTypesListAdapter;
    Context context;
    String status,se_user_mobile_no, se_user_name, se_id,check_id, service_title,message;
    SharedPreferences sharedPreferences;
    List<JobListResponse.DataBean> breedTypedataBeanList;
    private String PetBreedType = "";
    //ArrayList<String> arli_jobid = new ArrayList<String>();

   // List<arli_jobid> jobid = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_jobdetails_lrservice);
        context = this;


        img_back = (ImageView) findViewById(R.id.img_back);
        edtsearch = (EditText) findViewById(R.id.edt_search);
        img_clearsearch = (ImageView) findViewById(R.id.img_clearsearch);
        txt_no_records = findViewById(R.id.txt_no_records);
        recyclerView = findViewById(R.id.recyclerView);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "Services");

        Log.e("Name", "" + service_title);
        Log.e("Mobile", ""+ se_user_mobile_no);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
          //  service_title = extras.getString("service_title");
            status = extras.getString("status");
         //   Log.e("Name",":" + service_title);
            Log.e("Status", "" + status);
        }

        if (status.equals("new")){

            jobFindResponseCall(se_user_mobile_no,service_title);
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(context, LR_Service_Activity.class);
                //send.putExtra("service_title",service_title);
                send.putExtra("status", status);
                startActivity(send);

            }
        });


        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String Search = edtsearch.getText().toString();

                if(Search.equals("")){
                    recyclerView.setVisibility(View.VISIBLE);
                    img_clearsearch.setVisibility(View.INVISIBLE);
                } else {

                    filter(Search);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String Search = edtsearch.getText().toString();

                recyclerView.setVisibility(View.VISIBLE);
                txt_no_records.setVisibility(View.GONE);

                filter(Search);
            }
        });

    }

    private void filter(String search) {

        List<JobListResponse.DataBean> filterlist = new ArrayList<>();
        for (JobListResponse.DataBean item :breedTypedataBeanList){
            if(item.getJob_id().toLowerCase().contains(search.toLowerCase()))
            {
                Log.w(TAG,"filter----"+item.getJob_id().toLowerCase().contains(search.toLowerCase()));
                filterlist.add(item);

            }
        }


        if(filterlist.isEmpty())
        {
            Toast.makeText(this,"No Data Found ... ",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            txt_no_records.setVisibility(View.VISIBLE);
            txt_no_records.setText("No Data Found");
        }else
        {
            petBreedTypesListAdapter.filterrList(filterlist);
        }
    }


     //   arli_jobid.add("L-1456");
      //  arli_jobid.add("L-4578");
    //    arli_jobid.add("L-6745");

    private void jobFindResponseCall(String se_user_mobile_no, String service_title) {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobListResponse> call = apiInterface.NewJobListLRCall(RestUtils.getContentType(),joblistlrrequest(se_user_mobile_no,service_title));
        Log.w(TAG, "JobList Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<JobListResponse>() {
            @Override
            public void onResponse(Call<JobListResponse> call, Response<JobListResponse> response) {
                Log.w(TAG, "JobList Response" + new Gson().toJson(response.body()));

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
            public void onFailure(Call<JobListResponse> call, Throwable t) {

                Log.e("JobList On Failure ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void setBreedTypeView(List<JobListResponse.DataBean> breedTypedataBeanList) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter = new JobListAdapter_LRService(getApplicationContext(), breedTypedataBeanList,status);
        recyclerView.setAdapter(petBreedTypesListAdapter);

    }

    private JobListRequest joblistlrrequest(String se_user_mobile_no, String service_title) {

        JobListRequest joblist = new JobListRequest();
        joblist.setUser_mobile_no(se_user_mobile_no);
        joblist.setService_name(service_title);
        Log.w(TAG, "JobList Request " + new Gson().toJson(joblist));
        return joblist;
    }

    public void petBreedTypeSelectListener(String petbreedtitle, String petbreedid) {
        PetBreedType = petbreedtitle;
    }

    public void onBackPressed() {
        Intent send = new Intent(context, LR_Service_Activity.class);
        // send.putExtra("service_title",service_title);
        startActivity(send);
    }
}
