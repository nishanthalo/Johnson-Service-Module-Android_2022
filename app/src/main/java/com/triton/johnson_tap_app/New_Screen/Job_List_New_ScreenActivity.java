package com.triton.johnson_tap_app.New_Screen;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnson_tap_app.Model;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.Service_Adapter.Job_List_new_ScreenAdapter;
import com.triton.johnson_tap_app.Service_Adapter.Services_List_new_SecrrenAdapter;
import com.triton.johnson_tap_app.activity.Dashbaord_MainActivity;
import com.triton.johnson_tap_app.activity.Main_Menu_ServicesActivity;
import com.triton.johnson_tap_app.activity.New_LoginActivity;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Agent_new_screenRequest;
import com.triton.johnson_tap_app.requestpojo.Joblist_new_screenRequest;
import com.triton.johnson_tap_app.responsepojo.Joblist_new_screenResponse;
import com.triton.johnson_tap_app.responsepojo.Service_list_new_screenResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Job_List_New_ScreenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etsearch;
    TextView txt_last_login;
    String se_id,se_user_mobile_no,se_user_name,message,title;
    List<Joblist_new_screenResponse.DataBean> dataBeanList;
    Job_List_new_ScreenAdapter activityBasedListAdapter;
    TextView text,txt_no_records,service_title;
    ImageView iv_back,img_clearsearch;
    LinearLayout logout;
    AlertDialog alertDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_job_list_new_screen);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        txt_no_records = findViewById(R.id.txt_no_records);
        img_clearsearch = (ImageView) findViewById(R.id.img_clearsearch);
        etsearch = (EditText) findViewById(R.id.edt_search);
        service_title = (TextView) findViewById(R.id.service_title);
        logout = (LinearLayout) findViewById(R.id.logout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("service_title");
            service_title.setText(title);
        }

        //jobFindResponseCall("8976322100","LR SERVICE");
        jobFindResponseCall(se_user_mobile_no,title);

        etsearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Searchvalue = etsearch.getText().toString();

                if(Searchvalue.equals("")){
                    recyclerView.setVisibility(View.VISIBLE);
                    jobFindResponseCall(se_user_mobile_no,title);
                    img_clearsearch.setVisibility(View.INVISIBLE);
                }
                else {
                    //   Log.w(TAG,"Search Value---"+Searchvalue);
                    filter(Searchvalue);
                }
            }
        });

        img_clearsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etsearch.setText("");
                recyclerView.setVisibility(View.VISIBLE);
                jobFindResponseCall(se_user_mobile_no,title);
                img_clearsearch.setVisibility(View.INVISIBLE);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                alertDialog = new AlertDialog.Builder(Job_List_New_ScreenActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are youe sure do you want to Logout ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Job_List_New_ScreenActivity.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();

                                Toasty.success(getApplicationContext(),"Logout Sucessfully", Toast.LENGTH_SHORT, true).show();
                                Intent send = new Intent(Job_List_New_ScreenActivity.this, New_LoginActivity.class);
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

    private void filter(String s) {
        List<Joblist_new_screenResponse.DataBean> filteredlist = new ArrayList<>();
        for(Joblist_new_screenResponse.DataBean item : dataBeanList)
        {
            if(item.getJob_no().toLowerCase().contains(s.toLowerCase()))
            {
                Log.w(TAG,"filter----"+item.getJob_no().toLowerCase().contains(s.toLowerCase()));
                filteredlist.add(item);

            }
        }
        if(filteredlist.isEmpty())
        {
            Toast.makeText(this,"No Data Found ... ",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            txt_no_records.setVisibility(View.VISIBLE);
            txt_no_records.setText("No Data Found");
        }else
        {
            activityBasedListAdapter.filterList(filteredlist);
        }

    }


    private void jobFindResponseCall(String job_no, String service_name) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Joblist_new_screenResponse> call = apiInterface.Job_list_new_screenResponseCall(RestUtils.getContentType(), serviceRequest(job_no,service_name));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<Joblist_new_screenResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Joblist_new_screenResponse> call, @NonNull Response<Joblist_new_screenResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    message = response.body().getMessage();
                    Log.d("message", message);

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            dataBeanList = response.body().getData();

                            setView(dataBeanList);
                            Log.d("dataaaaa", String.valueOf(dataBeanList));

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
            public void onFailure(@NonNull Call<Joblist_new_screenResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Joblist_new_screenRequest serviceRequest(String job_no, String service_name) {
        Joblist_new_screenRequest service = new Joblist_new_screenRequest();
        service.setUser_mobile_no(job_no);
        service.setService_name(service_name);
        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(service));
        return service;
    }

    private void setView(List<Joblist_new_screenResponse.DataBean> dataBeanList) {

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        activityBasedListAdapter = new Job_List_new_ScreenAdapter(getApplicationContext(), dataBeanList);
        recyclerView.setAdapter(activityBasedListAdapter);
    }
}