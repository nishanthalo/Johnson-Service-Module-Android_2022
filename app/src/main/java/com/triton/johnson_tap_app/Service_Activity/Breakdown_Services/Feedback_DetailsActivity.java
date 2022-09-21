package com.triton.johnson_tap_app.Service_Activity.Breakdown_Services;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.triton.johnson_tap_app.Service_Adapter.Feedback_DetailsAdapter;
import com.triton.johnson_tap_app.UserTypeSelectListener1;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Feedback_DetailsRequest;
import com.triton.johnson_tap_app.responsepojo.Feedback_DetailsResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedback_DetailsActivity extends AppCompatActivity implements UserTypeSelectListener1 {

    TextView text,txt_no_records;
    Button btnSelection, btn_prev;
    private RecyclerView recyclerView;
    private EditText etsearch;
    int textlength = 0;
    ImageView iv_back,img_clearsearch;
    String feedback_group, message, Title, petimage,str2=null,sstring="",bd_dta,job_id,str_feedback_details;
    List<Feedback_DetailsResponse.DataBean> breedTypedataBeanList;
    Feedback_DetailsAdapter activityBasedListAdapter;
    AlertDialog alertDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_feedback_details);

        text = findViewById(R.id.text);
        btn_prev = (Button) findViewById(R.id.btn_show);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        img_clearsearch = (ImageView) findViewById(R.id.img_clearsearch);
        btnSelection = (Button) findViewById(R.id.btn_next);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        etsearch = (EditText) findViewById(R.id.edt_search);
        txt_no_records = findViewById(R.id.txt_no_records);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            feedback_group = extras.getString("feedback_group");
        }

        if (extras != null) {
            bd_dta = extras.getString("bd_details");
        }

        if (extras != null) {
            job_id = extras.getString("job_id");
        }

            str2 = feedback_group.substring(1, feedback_group.length());
            String[] sArr = str2.split(",");
            List<String> sList = Arrays.asList(sArr);
            sstring = String.valueOf(sList);


        Spannable name_Upload = new SpannableString("Feedback Description ");
        name_Upload.setSpan(new ForegroundColorSpan(Feedback_DetailsActivity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.append(name_Upload1);

        btn_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
        btn_prev.setTextColor(getResources().getColor(R.color.white));
        btn_prev.setEnabled(true);
        etsearch = (EditText) findViewById(R.id.edt_search);

        jobFindResponseCall(sstring);

        btnSelection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String data = "";
                for (int i = 0; i < breedTypedataBeanList.size(); i++) {
                    Feedback_DetailsResponse.DataBean singleStudent = breedTypedataBeanList.get(i);
                    if (singleStudent.isSelected() == true) {

                        data = data + "," + breedTypedataBeanList.get(i).getCodes();
                    }

               }

                if(data.equals("")){

                    alertDialog = new AlertDialog.Builder(Feedback_DetailsActivity.this)
                            .setTitle("Please Selected Value")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialog.dismiss();
                                }
                            })
                            .show();
                }
                else {

                    Intent send = new Intent( Feedback_DetailsActivity.this, Feedback_RemarkActivity.class);
                    send.putExtra("feedback_details",data);
                    send.putExtra("feedback_group",feedback_group);
                    send.putExtra("bd_details",bd_dta);
                    send.putExtra("job_id",job_id);
                    startActivity(send);
                }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
//                Intent send = new Intent(Feedback_DetailsActivity.this, Feedback_GroupActivity.class);
//                send.putExtra("feedback_group",feedback_group);
//                send.putExtra("bd_details",bd_dta);
//                send.putExtra("job_id",job_id);
             //   startActivity(send);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

//                Intent send = new Intent(Feedback_DetailsActivity.this, Feedback_GroupActivity.class);
//                send.putExtra("feedback_group",feedback_group);
//                send.putExtra("bd_details",bd_dta);
//                send.putExtra("job_id",job_id);
//                startActivity(send);
            }
        });

        etsearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String Searchvalue = etsearch.getText().toString();

                if(Searchvalue.equals("")){
                    recyclerView.setVisibility(View.VISIBLE);
                    img_clearsearch.setVisibility(View.INVISIBLE);
                    jobFindResponseCall(sstring);

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
                jobFindResponseCall(sstring);
                img_clearsearch.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void filter(String s) {
        List<Feedback_DetailsResponse.DataBean> filteredlist = new ArrayList<>();
        for(Feedback_DetailsResponse.DataBean item : breedTypedataBeanList)
        {
            if(item.getTitle().toLowerCase().contains(s.toLowerCase()))
            {
                Log.w(TAG,"filter----"+item.getTitle().toLowerCase().contains(s.toLowerCase()));
                filteredlist.add(item);
            }
        }
        if(filteredlist.isEmpty())
        {
            Toast.makeText(this,"No Data Found ... ",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            txt_no_records.setVisibility(View.VISIBLE);
        }
        else
        {
            activityBasedListAdapter.filterList(filteredlist);
        }

    }

    private void jobFindResponseCall(String job_no) {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Feedback_DetailsResponse> call = apiInterface.Feedback_DetailsResponseCall(RestUtils.getContentType(), serviceRequest(job_no));
        Log.w(TAG, "Jobno Find Response url  :%s" + " " + call.request().url().toString());
        call.enqueue(new Callback<Feedback_DetailsResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Feedback_DetailsResponse> call, @NonNull Response<Feedback_DetailsResponse> response) {
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
            public void onFailure(@NonNull Call<Feedback_DetailsResponse> call, @NonNull Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Feedback_DetailsRequest serviceRequest(String job_no) {
        Feedback_DetailsRequest service = new Feedback_DetailsRequest();
        service.setCode_list(job_no);
        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(service));
        return service;
    }

    private void setView(List<Feedback_DetailsResponse.DataBean> dataBeanList) {

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        activityBasedListAdapter = new Feedback_DetailsAdapter(getApplicationContext(), dataBeanList, this);
        recyclerView.setAdapter(activityBasedListAdapter);
    }

    public void userTypeSelectListener1(String usertype, String usertypevalue) {
        Title = usertype;

        Log.w(TAG, "myPetsSelectListener : " + "petList" + new Gson().toJson(breedTypedataBeanList));

        if (breedTypedataBeanList != null && breedTypedataBeanList.size() > 0) {
            for (int i = 0; i < breedTypedataBeanList.size(); i++) {
                if (breedTypedataBeanList.get(i).getTitle().equalsIgnoreCase(breedTypedataBeanList.get(i).getTitle())) {
                    petimage = breedTypedataBeanList.get(i).getTitle();
                }
                Log.w(TAG, "myPetsSelectListener : " + "petimage" + petimage);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent send = new Intent(Feedback_DetailsActivity.this, Feedback_GroupActivity.class);
//        send.putExtra("feedback_group",feedback_group);
//        send.putExtra("bd_details",bd_dta);
//        send.putExtra("job_id",job_id);
//        startActivity(send);
    }
}