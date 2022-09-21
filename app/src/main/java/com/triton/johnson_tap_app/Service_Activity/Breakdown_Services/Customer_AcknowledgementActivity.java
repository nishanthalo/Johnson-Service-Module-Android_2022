package com.triton.johnson_tap_app.Service_Activity.Breakdown_Services;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.Service_Activity.ServicesActivity;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Breakdowm_Submit_Request;
import com.triton.johnson_tap_app.requestpojo.Job_Details_TextRequest;
import com.triton.johnson_tap_app.requestpojo.Job_status_updateRequest;
import com.triton.johnson_tap_app.responsepojo.Breakdown_submitrResponse;
import com.triton.johnson_tap_app.responsepojo.Feedback_DetailsResponse;
import com.triton.johnson_tap_app.responsepojo.FileUploadResponse;
import com.triton.johnson_tap_app.responsepojo.Job_Details_TextResponse;
import com.triton.johnson_tap_app.responsepojo.Job_status_updateResponse;
import com.triton.johnson_tap_app.responsepojo.SubmitBreakdownResponseee;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_AcknowledgementActivity extends AppCompatActivity {

    SignaturePad signaturePad;
    Button saveButton, clearButton;
     Button btn_success, btn_prev;
    ImageView iv_back;
    MultipartBody.Part siganaturePart;
    String userid;
    ImageView image;
    String str2="",sstring;
    private String uploadimagepath = "";
    private List<Breakdown_submitrResponse.DataBean.Feedback_detailsBean> defaultLocationList ;
    List<Feedback_DetailsResponse.DataBean> pet_imgList = new ArrayList();
    String value="",job_id,feedback_group,feedback_details,bd_dta,feedback_remark,mr1,mr2,mr3,mr4,mr5,mr6,mr7,mr8,mr9,mr10,breakdown_servies,tech_signature,customer_name,customer_no,str_customer_acknowledgement="";
    String se_user_mobile_no, se_user_name, se_id,check_id,service_title;
    String str_job_status,message;
    ProgressDialog progressDialog;
    TextView job_details_text;
    Bitmap signatureBitmap;
    Dialog dialog;
    String compno, sertype;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_acknowledgement);

        signaturePad = (SignaturePad)findViewById(R.id.signaturePad);
        saveButton = (Button)findViewById(R.id.saveButton);
        clearButton = (Button)findViewById(R.id.clearButton);
        btn_success = (Button) findViewById(R.id.btn_success);
        btn_prev = (Button) findViewById(R.id.btn_show);
        iv_back = (ImageView) findViewById(R.id.iv_back);
       image = (ImageView)findViewById(R.id.image);
        job_details_text = (TextView) findViewById(R.id.job_details_text);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");

        compno = sharedPreferences.getString("compno","123");
        sertype = sharedPreferences.getString("sertype","123");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("value");
        }
        if (extras != null) {
            feedback_group = extras.getString("feedback_group");
        }

        if (extras != null) {
            bd_dta = extras.getString("bd_details");
        }

        if (extras != null) {
            job_id = extras.getString("job_id");
        }

        if (extras != null) {
            feedback_details = extras.getString("feedback_details");
        }

        if (extras != null) {
            feedback_remark = extras.getString("feedback_remark");
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
            breakdown_servies = extras.getString("breakdown_service");
        }
        if (extras != null) {
            tech_signature = extras.getString("tech_signature");
        }
        if (extras != null) {
            customer_name = extras.getString("customer_name");
        }

        if (extras != null) {
            customer_no = extras.getString("customer_number");
        }

        if (extras != null) {
            str_customer_acknowledgement = extras.getString("customer_acknowledgement");
            Picasso.get().load(str_customer_acknowledgement).into(image);
        }

        job_details_in_text();

        str2 =  feedback_details.substring(1, feedback_details.length());
        String[] sArr = str2.split(",");
        List<String> sList = Arrays.asList(sArr);
        sstring = String.valueOf(sList);

        btn_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
        btn_prev.setTextColor(getResources().getColor(R.color.white));
        btn_prev.setEnabled(true);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Customer_AcknowledgementActivity.this);
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

        ll_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_job_status = "Job Started";
                Job_status_update();
                dialog.dismiss();
            }
        });

        ll_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_job_status = "Job Paused";
                Job_status_update();
                dialog.dismiss();
            }
        });

        ll_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_job_status = "Job Stopped";
                Job_status_update();
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


        //disable both buttons at start
        saveButton.setEnabled(false);
        clearButton.setEnabled(false);

        //change screen orientation to landscape mode
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressDialog = new ProgressDialog(Customer_AcknowledgementActivity.this);
                progressDialog.setMessage("Please Wait Image Upload ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                signatureBitmap = signaturePad.getSignatureBitmap();
                Log.w(TAG, "signatureBitmap" + signatureBitmap);
                File file = new File(getFilesDir(), "Acknowledgment_Signature" + ".jpg");

                OutputStream os;
                try {
                    os = new FileOutputStream(file);
                    if (signatureBitmap != null) {
                        signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    }
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                siganaturePart = MultipartBody.Part.createFormData("sampleFile", userid + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

                uploadDigitalSignatureImageRequest();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        btn_success.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (signatureBitmap == null) {
                    Toast.makeText(Customer_AcknowledgementActivity.this, "Please Drop Signature", Toast.LENGTH_SHORT).show();
                }
                else {

                    locationAddResponseCall();
                }

            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

//                if(uploadimagepath.equals("")) {
//                    Intent send = new Intent(Customer_AcknowledgementActivity.this, Customer_Details_BreakdownActivity.class);
//                    send.putExtra("value", value);
//                    send.putExtra("feedback_details", feedback_details);
//                    send.putExtra("feedback_group", feedback_group);
//                    send.putExtra("bd_details", bd_dta);
//                    send.putExtra("job_id", job_id);
//                    send.putExtra("feedback_remark", feedback_remark);
//                    send.putExtra("mr1", mr1);
//                    send.putExtra("mr2", mr2);
//                    send.putExtra("mr3", mr3);
//                    send.putExtra("mr4", mr4);
//                    send.putExtra("mr5", mr5);
//                    send.putExtra("mr6", mr6);
//                    send.putExtra("mr7", mr7);
//                    send.putExtra("mr8", mr8);
//                    send.putExtra("mr9", mr9);
//                    send.putExtra("mr10", mr10);
//                    send.putExtra("breakdown_service", breakdown_servies);
//                    send.putExtra("tech_signature", tech_signature);
//                    send.putExtra("customer_name", customer_name);
//                    send.putExtra("customer_number", customer_no);
//                    startActivity(send);
//                }
//                else {
//                    Intent send = new Intent(Customer_AcknowledgementActivity.this, Customer_Details_BreakdownActivity.class);
//                    send.putExtra("value", value);
//                    send.putExtra("feedback_details", feedback_details);
//                    send.putExtra("feedback_group", feedback_group);
//                    send.putExtra("bd_details", bd_dta);
//                    send.putExtra("job_id", job_id);
//                    send.putExtra("feedback_remark", feedback_remark);
//                    send.putExtra("mr1", mr1);
//                    send.putExtra("mr2", mr2);
//                    send.putExtra("mr3", mr3);
//                    send.putExtra("mr4", mr4);
//                    send.putExtra("mr5", mr5);
//                    send.putExtra("mr6", mr6);
//                    send.putExtra("mr7", mr7);
//                    send.putExtra("mr8", mr8);
//                    send.putExtra("mr9", mr9);
//                    send.putExtra("mr10", mr10);
//                    send.putExtra("breakdown_service", breakdown_servies);
//                    send.putExtra("tech_signature", tech_signature);
//                    send.putExtra("customer_name", customer_name);
//                    send.putExtra("customer_number", customer_no);
//                    send.putExtra("customer_acknowledgement", uploadimagepath);
//                    startActivity(send);
//                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
//                if(uploadimagepath.equals("")) {
//                    Intent send = new Intent(Customer_AcknowledgementActivity.this, Customer_Details_BreakdownActivity.class);
//                    send.putExtra("feedback_details", feedback_details);
//                    send.putExtra("value", value);
//                    send.putExtra("feedback_group", feedback_group);
//                    send.putExtra("bd_details", bd_dta);
//                    send.putExtra("job_id", job_id);
//                    send.putExtra("feedback_remark", feedback_remark);
//                    send.putExtra("mr1", mr1);
//                    send.putExtra("mr2", mr2);
//                    send.putExtra("mr3", mr3);
//                    send.putExtra("mr4", mr4);
//                    send.putExtra("mr5", mr5);
//                    send.putExtra("mr6", mr6);
//                    send.putExtra("mr7", mr7);
//                    send.putExtra("mr8", mr8);
//                    send.putExtra("mr9", mr9);
//                    send.putExtra("mr10", mr10);
//                    send.putExtra("breakdown_service", breakdown_servies);
//                    send.putExtra("tech_signature", tech_signature);
//                    send.putExtra("customer_name", customer_name);
//                    send.putExtra("customer_number", customer_no);
//                    startActivity(send);
//                }
//                else {
//                    Intent send = new Intent(Customer_AcknowledgementActivity.this, Customer_Details_BreakdownActivity.class);
//                    send.putExtra("value", value);
//                    send.putExtra("feedback_details", feedback_details);
//                    send.putExtra("feedback_group", feedback_group);
//                    send.putExtra("bd_details", bd_dta);
//                    send.putExtra("job_id", job_id);
//                    send.putExtra("feedback_remark", feedback_remark);
//                    send.putExtra("mr1", mr1);
//                    send.putExtra("mr2", mr2);
//                    send.putExtra("mr3", mr3);
//                    send.putExtra("mr4", mr4);
//                    send.putExtra("mr5", mr5);
//                    send.putExtra("mr6", mr6);
//                    send.putExtra("mr7", mr7);
//                    send.putExtra("mr8", mr8);
//                    send.putExtra("mr9", mr9);
//                    send.putExtra("mr10", mr10);
//                    send.putExtra("breakdown_service", breakdown_servies);
//                    send.putExtra("tech_signature", tech_signature);
//                    send.putExtra("customer_name", customer_name);
//                    send.putExtra("customer_number", customer_no);
//                    send.putExtra("customer_acknowledgement", uploadimagepath);
//                    startActivity(send);
//                }
            }
        });
    }

    private void uploadDigitalSignatureImageRequest() {

        APIInterface apiInterface = RetrofitClient.getImageClient().create(APIInterface.class);
        Call<FileUploadResponse> call = apiInterface.getImageStroeResponse(siganaturePart);
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<FileUploadResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FileUploadResponse> call, @NonNull Response<FileUploadResponse> response) {

                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        Log.w(TAG, "Profpic" + "--->" + new Gson().toJson(response.body()));

                        uploadimagepath = response.body().getData();

                        image.setVisibility(View.INVISIBLE);

                        Log.d("image", uploadimagepath);

                        Toast.makeText(Customer_AcknowledgementActivity.this, "Signature Saved", Toast.LENGTH_SHORT).show();

                        if (uploadimagepath != null) {
                            Glide.with(Customer_AcknowledgementActivity.this)
                                    .load(uploadimagepath)
                                    .into(image);

                            progressDialog.dismiss();

                        }
                    }

                }

            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<FileUploadResponse> call, @NonNull Throwable t) {
                Log.w(TAG, "ServerUrlImagePath" + "On failure working" + t.getMessage());
            }
        });
    }

    public void locationAddResponseCall(){
        dialog = new Dialog(Customer_AcknowledgementActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SubmitBreakdownResponseee> call = apiInterface.submitAddResponseCall(RestUtils.getContentType(),submitDailyRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SubmitBreakdownResponseee>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NotNull Call<SubmitBreakdownResponseee> call, @NotNull Response<SubmitBreakdownResponseee> response) {

                Log.w(TAG, "AddLocationResponse" + new Gson().toJson(response.body()));
                Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

                if (response.body() != null) {
                    dialog.dismiss();
                    if(response.body().getCode() == 200){
                        dialog.dismiss();
                        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

                        Log.w(TAG,"dddd %s"+" "+ response.body().getData().toString());

                        Toasty.success(Customer_AcknowledgementActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent send = new Intent( Customer_AcknowledgementActivity.this, ServicesActivity.class);
                        startActivity(send);

                    }else{
                        //  showErrorLoading(response.body().getMessage());
                        dialog.dismiss();
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitBreakdownResponseee> call, @NotNull Throwable t) {
                Log.w(TAG,"AddLocationResponseflr"+t.getMessage());
                dialog.dismiss();
            }
        });

    }
    private Breakdowm_Submit_Request submitDailyRequest() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        Breakdowm_Submit_Request submitDailyRequest = new Breakdowm_Submit_Request();
        submitDailyRequest.setBd_details(bd_dta);
        submitDailyRequest.setFeedback_details(sstring);
        submitDailyRequest.setFeedback_remark_text(feedback_remark);
        submitDailyRequest.setMr_status(value);
        submitDailyRequest.setMr_1(mr1);
        submitDailyRequest.setMr_2(mr2);
        submitDailyRequest.setMr_3(mr3);
        submitDailyRequest.setMr_4(mr4);
        submitDailyRequest.setMr_5(mr5);
        submitDailyRequest.setMr_6(mr6);
        submitDailyRequest.setMr_7(mr7);
        submitDailyRequest.setMr_8(mr8);
        submitDailyRequest.setMr_9(mr9);
        submitDailyRequest.setMr_10(mr10);
        submitDailyRequest.setBreakdown_service(breakdown_servies);
        submitDailyRequest.setTech_signature(tech_signature);
        submitDailyRequest.setCustomer_name(customer_name);
        submitDailyRequest.setCustomer_number(customer_no);
        submitDailyRequest.setCustomer_acknowledgemnet(uploadimagepath);
        submitDailyRequest.setDate_of_submission(currentDateandTime);
        submitDailyRequest.setUser_mobile_no(se_user_mobile_no);
        submitDailyRequest.setJob_id(job_id);
        submitDailyRequest.setSMU_SCH_COMPNO(compno);
        submitDailyRequest.setSMU_SCH_SERTYPE(sertype);
        Log.e("CompNo",""+compno);
        Log.e("SertYpe", ""+sertype);

        Log.w(TAG," locationAddRequest"+ new Gson().toJson(submitDailyRequest));
        return submitDailyRequest;
    }

    private void Job_status_update() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Job_status_updateResponse> call = apiInterface.job_status_updateResponseCall(com.triton.johnson_tap_app.utils.RestUtils.getContentType(), job_status_updateRequest());
        Log.w(VolleyLog.TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<Job_status_updateResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Job_status_updateResponse> call, @NonNull Response<Job_status_updateResponse> response) {

                Log.w(VolleyLog.TAG,"SignupResponse" + new Gson().toJson(response.body()));
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
        custom.setSMU_SCH_COMPNO(compno);
        custom.setSMU_SCH_SERTYPE(sertype);
        Log.e("CompNo",""+compno);
        Log.e("SertYpe", ""+sertype);
        Log.w(VolleyLog.TAG,"loginRequest "+ new Gson().toJson(custom));
        return custom;
    }

    private void job_details_in_text() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Job_Details_TextResponse> call = apiInterface.Job_Details_TextResponseCall(RestUtils.getContentType(), custom_detailsRequest());
        Log.w(VolleyLog.TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<Job_Details_TextResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Job_Details_TextResponse> call, @NonNull Response<Job_Details_TextResponse> response) {

                Log.w(VolleyLog.TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                          String str_address1 = response.body().getData().getText_value();

                            job_details_text.setText(str_address1);
                        }


                    } else {
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<Job_Details_TextResponse> call, @NonNull Throwable t) {
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Job_Details_TextRequest custom_detailsRequest() {

        Job_Details_TextRequest custom = new Job_Details_TextRequest();
        custom.setJob_id(job_id);
        custom.setSMU_SCH_COMPNO(compno);
        custom.setSMU_SCH_SERTYPE(sertype);
        Log.e("CompNo",""+compno);
        Log.e("SertYpe", ""+sertype);
        Log.w(VolleyLog.TAG,"loginRequest "+ new Gson().toJson(custom));
        return custom;
    }

    @Override
    public void onBackPressed() {
     onBackPressed();
    }
}