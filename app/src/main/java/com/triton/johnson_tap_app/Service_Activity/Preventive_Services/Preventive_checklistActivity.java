package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.Service_Adapter.Preventive_ChecklistAdapter;
import com.triton.johnson_tap_app.UserTypeSelectListener1;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Preventive_ChecklistRequest;
import com.triton.johnson_tap_app.responsepojo.Preventive_ChecklistResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Preventive_checklistActivity extends AppCompatActivity implements UserTypeSelectListener1{

    private Button btnSelection;
    private RecyclerView recyclerView;
    ImageView iv_back,ic_paused;
    List<Preventive_ChecklistResponse.DataBean> breedTypedataBeanList;
    Preventive_ChecklistAdapter activityBasedListAdapter;
    private String PetBreedType = "",hellWrld,str,pre_check;
    String message,se_user_mobile_no,se_user_name, se_id,service_title,str_job_id,data,value_s="",job_id,value,mr1,mr2,mr3,mr4,mr5,mr6,mr7,mr8,mr9,mr10,Form1_value,Form1_name,Form1_comments;
    private String Title,petimage,Form1_cat_id,Form1_group_id;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_preventive_checklist);

        btnSelection = (Button) findViewById(R.id.btn_next);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        ic_paused = (ImageView) findViewById(R.id.ic_paused);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value_s = extras.getString("valuess");
        }
        if (extras != null) {
            job_id = extras.getString("job_id");
        }

        if (extras != null) {
            value = extras.getString("value");
        }

        if (extras != null) {
            service_title = extras.getString("service_title");
        }

        if (extras != null) {
            mr1 = extras.getString("mr1");
        }
        if (extras != null) {
            mr2 = extras.getString("mr2");
        }
        if (extras != null) {
            mr3 = extras.getString("mr3");
        }
        if (extras != null) {
            mr4 = extras.getString("mr4");
        }
        if (extras != null) {
            mr5 = extras.getString("mr5");
        }
        if (extras != null) {
            mr6 = extras.getString("mr6");
        }
        if (extras != null) {
            mr7 = extras.getString("mr7");
        }
        if (extras != null) {
            mr8 = extras.getString("mr8");
        }
        if (extras != null) {
            mr9 = extras.getString("mr9");
        }
        if (extras != null) {
            mr10 = extras.getString("mr10");
        }

        if (extras != null) {
            Form1_value = extras.getString("Form1_value");
        }

        if (extras != null) {
            Form1_name = extras.getString("Form1_name");
        }
        if (extras != null) {
            Form1_comments = extras.getString("Form1_comments");
        }
        if (extras != null) {
            Form1_cat_id = extras.getString("Form1_cat_id");
        }

        if (extras != null) {
            Form1_group_id = extras.getString("Form1_group_id");
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "default value");

       jobFindResponseCall(job_id);
     //   jobFindResponseCall("L2163-P01");

        btnSelection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < breedTypedataBeanList.size(); i++) {
                    Preventive_ChecklistResponse.DataBean singleStudent = breedTypedataBeanList.get(i);
                    if (singleStudent.isSelected() == true) {
                        data = data + "," + breedTypedataBeanList.get(i).getCheck_list_value().toString();

                        hellWrld = data.replace("null","");
                        str = hellWrld;
                        str = str.substring(1);
                       // Toasty.warning(getApplicationContext(), "" + str, Toasty.LENGTH_LONG).show();
                    }
                }

                ArrayList<String> list= new ArrayList<String>(Arrays.asList(str.split(",")));
                System.out.println(list);

                ArrayList<String> outputList = new ArrayList<String>();
                for (String item: list) {
                    outputList.add("\""+item+"\"");
                }
                pre_check = String.valueOf(outputList);
                pre_check = pre_check.replaceAll("\\[", "").replaceAll("\\]","");
              //  System.out.println("EEEEEEEEEEE"+ddd);

           //     Log.d("sssssssss", String.valueOf(outputList));

                if(data.equals("")){

                    alertDialog = new AlertDialog.Builder(Preventive_checklistActivity.this)
                            .setTitle("Please Selected Value")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialog.dismiss();
                                }
                            })
                            .show();
                }
                else {

                    Intent send = new Intent(Preventive_checklistActivity.this, Preventive_action_requiredActivity.class);
                    send.putExtra("valuess",value_s);
                    send.putExtra("job_id",job_id);
                    send.putExtra("value",value);
                    send.putExtra("service_title",service_title);
                    send.putExtra("mr1", mr1);
                    send.putExtra("mr2", mr2);
                    send.putExtra("mr3", mr3);
                    send.putExtra("mr4", mr4);
                    send.putExtra("mr5", mr5);
                    send.putExtra("mr6", mr6);
                    send.putExtra("mr7", mr7);
                    send.putExtra("mr8", mr8);
                    send.putExtra("mr9", mr9);
                    send.putExtra("mr10", mr10);
                    send.putExtra("preventive_check",pre_check);
                    send.putExtra("Form1_value", Form1_value);
                    send.putExtra("Form1_name",Form1_name);
                    send.putExtra("Form1_comments",Form1_comments);
                    send.putExtra("Form1_cat_id",Form1_cat_id);
                    send.putExtra("Form1_group_id",Form1_group_id);
                    startActivity(send);
                }
            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(value_s.equals("yes")){
                    Intent send = new Intent(Preventive_checklistActivity.this, Material_Request_MR_Screen_PreventiveActivity.class);
                    send.putExtra("valuess","yes");
                    send.putExtra("job_id",job_id);
                    send.putExtra("value",value);
                    send.putExtra("service_title",service_title);
                    send.putExtra("mr1", mr1);
                    send.putExtra("mr2", mr2);
                    send.putExtra("mr3", mr3);
                    send.putExtra("mr4", mr4);
                    send.putExtra("mr5", mr5);
                    send.putExtra("mr6", mr6);
                    send.putExtra("mr7", mr7);
                    send.putExtra("mr8", mr8);
                    send.putExtra("mr9", mr9);
                    send.putExtra("mr10", mr10);
                    send.putExtra("Form1_value", Form1_value);
                    send.putExtra("Form1_name",Form1_name);
                    send.putExtra("Form1_comments",Form1_comments);
                    send.putExtra("Form1_cat_id",Form1_cat_id);
                    send.putExtra("Form1_group_id",Form1_group_id);
                    startActivity(send);
                }
                else {
                    Intent send = new Intent(Preventive_checklistActivity.this, Material_Request_PreventiveActivity.class);
                    send.putExtra("valuess","no");
                    send.putExtra("job_id",job_id);
                    send.putExtra("value",value);
                    send.putExtra("service_title",service_title);
                    send.putExtra("mr1", mr1);
                    send.putExtra("mr2", mr2);
                    send.putExtra("mr3", mr3);
                    send.putExtra("mr4", mr4);
                    send.putExtra("mr5", mr5);
                    send.putExtra("mr6", mr6);
                    send.putExtra("mr7", mr7);
                    send.putExtra("mr8", mr8);
                    send.putExtra("mr9", mr9);
                    send.putExtra("mr10", mr10);
                    send.putExtra("Form1_value", Form1_value);
                    send.putExtra("Form1_name",Form1_name);
                    send.putExtra("Form1_comments",Form1_comments);
                    send.putExtra("Form1_cat_id",Form1_cat_id);
                    send.putExtra("Form1_group_id",Form1_group_id);
                    startActivity(send);
                }
            }
        });

    }

    private void jobFindResponseCall(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Preventive_ChecklistResponse> call = apiInterface.Preventive_ChecklistResponseCall(RestUtils.getContentType(), serviceRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<Preventive_ChecklistResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Preventive_ChecklistResponse> call, @NonNull Response<Preventive_ChecklistResponse> response) {
                Log.w(TAG, "Jobno Find Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    message = response.body().getMessage();
                    Log.d("message", message);

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();

                            setView(breedTypedataBeanList);
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
            public void onFailure(@NonNull Call<Preventive_ChecklistResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Preventive_ChecklistRequest serviceRequest(String job_no) {
        Preventive_ChecklistRequest service = new Preventive_ChecklistRequest();
        service.setJob_id(job_no);
        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(service));
        return service;
    }

    private void setView(List<Preventive_ChecklistResponse.DataBean> dataBeanList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        activityBasedListAdapter = new Preventive_ChecklistAdapter(getApplicationContext(), dataBeanList,this);
        recyclerView.setAdapter(activityBasedListAdapter);
    }

    public void userTypeSelectListener1(String usertype, String usertypevalue) {
        Title = usertype;

        Log.w(TAG,"myPetsSelectListener : "+ "petList" +new Gson().toJson(breedTypedataBeanList));

        if(breedTypedataBeanList != null && breedTypedataBeanList.size()>0) {
            for (int i = 0; i < breedTypedataBeanList.size(); i++) {
                if (breedTypedataBeanList.get(i).getCheck_list_value().equalsIgnoreCase(breedTypedataBeanList.get(i).getCheck_list_value())) {
                    petimage = breedTypedataBeanList.get(i).getCheck_list_value();
                }
                Log.w(TAG, "myPetsSelectListener : " + "petimage" + petimage);

            }
        }
    }
}