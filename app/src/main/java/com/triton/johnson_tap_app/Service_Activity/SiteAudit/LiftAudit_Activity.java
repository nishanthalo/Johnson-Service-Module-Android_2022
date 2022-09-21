package com.triton.johnson_tap_app.Service_Activity.SiteAudit;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnson_tap_app.GetFieldListResponse;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.GetFieldListRequest;
import com.triton.johnson_tap_app.responsepojo.Auditcheckresponse;
import com.triton.johnson_tap_app.responsepojo.Preventive_ChecklistResponse;
import com.triton.johnson_tap_app.utils.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiftAudit_Activity extends AppCompatActivity {

    Context context;
    Button btn_Prev,btn_Next;
    ImageView img_Back;
    SharedPreferences sharedPreferences;
    String networkStatus = "";
    LinearLayout Footer;
    RecyclerView recyclerView;
    List<GetFieldListResponse.DataBean> dataBeanList;
    LiftAuditAdapter_SiteAudit petBreedTypesListAdapter;
    String se_user_mobile_no, se_user_name,status,se_id,check_id,service_type, service_title,jobid,message,str_job_status;
    public int TOTAL_NUM_ITEMS;
    public int ITEMS_PER_PAGE = 6;
    public int ITEMS_REMAINING;
    public int LAST_PAGE = TOTAL_NUM_ITEMS / ITEMS_PER_PAGE;
    private int currentPage = 0;
    String Result1;
    private int totalPages;
    private Dialog alertDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_liftaudit);
        context = this;

        img_Back =findViewById(R.id.img_back);
        btn_Prev = findViewById(R.id.btn_prev);
        btn_Next = findViewById(R.id.btn_next);
        recyclerView = findViewById(R.id.recyclerView);
        Footer = findViewById(R.id.footer);
        Footer.setVisibility(View.GONE);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "Services");
        jobid =sharedPreferences.getString("jobid","L-1234");

        Log.e("Name", "" + service_title);
        Log.e("Mobile", ""+ se_user_mobile_no);
        Log.e("Jobid",""+ jobid);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            status = extras.getString("status");
            Log.e("Status", "" + status);
            service_type = extras.getString("service_type");
            Log.e("Service Type", "" + status);
        }

        jobFindResponseCall();
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
//                Intent send = new Intent(context, Checklist_AuditActivity.class);
//                send.putExtra("status", status);
//                startActivity(send);
            }
        });

        btn_Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
//                Intent send = new Intent(context, Checklist_AuditActivity.class);
//                send.putExtra("status", status);
//                startActivity(send);
            }
        });

    }

    private void jobFindResponseCall() {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GetFieldListResponse> call = apiInterface.Preventive_ChecklistAuditResponseCall(RestUtils.getContentType(), getFieldListRequest());
        Log.w(TAG, "Audit Checklist Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<GetFieldListResponse>() {
            @Override
            public void onResponse(Call<GetFieldListResponse> call, Response<GetFieldListResponse> response) {

                Log.w(TAG, "Audit Checklist Response" + new Gson().toJson(response.body()));

                Log.e("Hi","On Success");

                if (response.body() != null) {

                    message = response.body().getMessage();
                    Log.d("message", message);

                    if (200 == response.body().getCode()) {

                        if (response.body().getData() != null) {
                            dataBeanList = response.body().getData();

                            Footer.setVisibility(View.VISIBLE);
                            if (dataBeanList.size() < 6 || dataBeanList.size() == 6) {
                                btn_Prev.setVisibility(View.INVISIBLE);
                                btn_Next.setVisibility(View.GONE);
                            }

                            totalPages = dataBeanList.size() / 6;
                            TOTAL_NUM_ITEMS = dataBeanList.size();
                            Log.w(TAG, "totalPages  : " + totalPages + " TOTAL_NUM_ITEMS : " + TOTAL_NUM_ITEMS);

                            ITEMS_REMAINING = TOTAL_NUM_ITEMS - ITEMS_PER_PAGE;

                            Log.w(TAG, " getfieldListResponseCall  setView  ITEMS_PER_PAGE : " + ITEMS_PER_PAGE + " TOTAL_NUM_ITEMS : " + TOTAL_NUM_ITEMS + " dataBeanList : " + new Gson().toJson(dataBeanList));

                         //   setView(dataBeanList, ITEMS_PER_PAGE, TOTAL_NUM_ITEMS);

                            setBreedTypeView(dataBeanList,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS);
                            Log.d("data", String.valueOf(dataBeanList));

                        }
                    }else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    }else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetFieldListResponse> call, Throwable t) {

                Log.e("Hi","On Failure");
            }
        });
    }


    private void setBreedTypeView(List<GetFieldListResponse.DataBean> dataBeanList, int ITEMS_PER_PAGE, int TOTAL_NUM_ITEMS) {
        Log.e("Nish Databean list",""+dataBeanList.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter = new LiftAuditAdapter_SiteAudit(context, dataBeanList,status,ITEMS_PER_PAGE,TOTAL_NUM_ITEMS);
        recyclerView.setAdapter(petBreedTypesListAdapter);
    }

    private GetFieldListRequest getFieldListRequest() {
        GetFieldListRequest getFieldListRequest = new GetFieldListRequest();
        getFieldListRequest.setJob_id(jobid);
        getFieldListRequest.setService_type(service_type);
        Log.w(TAG, "GetFieldListRequest " + new Gson().toJson(getFieldListRequest));
        return getFieldListRequest;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent send = new Intent(context, Checklist_AuditActivity.class);
//        send.putExtra("status", status);
//        startActivity(send);
    }
}
