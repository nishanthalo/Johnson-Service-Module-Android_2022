package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.responsepojo.FileUploadResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Technician_signature_preventiveActivity extends AppCompatActivity {

    SignaturePad signaturePad;
    Button saveButton, clearButton;
    private Button btnSelection,btn_prev;
    MultipartBody.Part siganaturePart;
    String userid;
    ImageView image,iv_back;
    private String uploadimagepath = "";
    String value="",job_id,mr1,mr2,mr3,mr4,mr5,mr6,mr7,mr8,mr9,mr10,value_s,service_title,preventive_check,pm_status,action_req_customer,Form1_value,Form1_name,Form1_comments,str_tech_signature="";
    String Form1_cat_id,Form1_group_id;
    ProgressDialog progressDialog;
    Bitmap signatureBitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_technician_signature_preventive);

        signaturePad = (SignaturePad)findViewById(R.id.signaturePad);
        saveButton = (Button)findViewById(R.id.saveButton);
        clearButton = (Button)findViewById(R.id.clearButton);
        btnSelection = (Button) findViewById(R.id.btn_next);
        btn_prev = (Button) findViewById(R.id.btn_show);
        image = (ImageView)findViewById(R.id.image);
        iv_back = (ImageView) findViewById(R.id.iv_back);

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
            preventive_check = extras.getString("preventive_check");
        }
        if (extras != null) {
            pm_status = extras.getString("pm_status");
        }
        if (extras != null) {
            action_req_customer = extras.getString("action_req_customer");
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
        if (extras != null) {
            str_tech_signature = extras.getString("tech_signature");
            Picasso.get().load(str_tech_signature).into(image);
        }

        saveButton.setEnabled(false);
        clearButton.setEnabled(false);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            public void onSigned() {
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            public void onClear() {
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressDialog = new ProgressDialog(Technician_signature_preventiveActivity.this);
                progressDialog.setMessage("Please Wait Image Upload ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                signatureBitmap = signaturePad.getSignatureBitmap();
                Log.w(TAG, "signatureBitmap" + signatureBitmap);
                File file = new File(getFilesDir(), "Technician Signature" + ".jpg");

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

        btnSelection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (signatureBitmap == null) {
                    Toast.makeText(Technician_signature_preventiveActivity.this, "Please Drop Signature", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent send = new Intent(Technician_signature_preventiveActivity.this, Customer_Details_PreventiveActivity.class);
                    send.putExtra("valuess", value_s);
                    send.putExtra("job_id", job_id);
                    send.putExtra("value", value);
                    send.putExtra("service_title", service_title);
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
                    send.putExtra("preventive_check", preventive_check);
                    send.putExtra("pm_status", pm_status);
                    send.putExtra("Tech_signature", uploadimagepath);
                    send.putExtra("action_req_customer", action_req_customer);
                    send.putExtra("Form1_value", Form1_value);
                    send.putExtra("Form1_name", Form1_name);
                    send.putExtra("Form1_comments", Form1_comments);
                    send.putExtra("Form1_cat_id", Form1_cat_id);
                    send.putExtra("Form1_group_id", Form1_group_id);
                    startActivity(send);
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(uploadimagepath.equals("")) {
                    Intent send = new Intent(Technician_signature_preventiveActivity.this, Preventive_StatusActivity.class);
                    send.putExtra("valuess", value_s);
                    send.putExtra("job_id", job_id);
                    send.putExtra("value", value);
                    send.putExtra("service_title", service_title);
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
                    send.putExtra("preventive_check", preventive_check);
                    send.putExtra("pm_status", pm_status);
                    send.putExtra("action_req_customer", action_req_customer);
                    send.putExtra("Form1_value", Form1_value);
                    send.putExtra("Form1_name", Form1_name);
                    send.putExtra("Form1_comments", Form1_comments);
                    send.putExtra("Form1_cat_id", Form1_cat_id);
                    send.putExtra("Form1_group_id", Form1_group_id);
                    startActivity(send);
                }
                else {
                    Intent send = new Intent(Technician_signature_preventiveActivity.this, Preventive_StatusActivity.class);
                    send.putExtra("valuess", value_s);
                    send.putExtra("job_id", job_id);
                    send.putExtra("value", value);
                    send.putExtra("service_title", service_title);
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
                    send.putExtra("preventive_check", preventive_check);
                    send.putExtra("pm_status", pm_status);
                    send.putExtra("action_req_customer", action_req_customer);
                    send.putExtra("Form1_value", Form1_value);
                    send.putExtra("Form1_name", Form1_name);
                    send.putExtra("Form1_comments", Form1_comments);
                    send.putExtra("Form1_cat_id", Form1_cat_id);
                    send.putExtra("Form1_group_id", Form1_group_id);
                    send.putExtra("tech_signature", uploadimagepath);
                    startActivity(send);
                }
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

                        Log.d("image",uploadimagepath);
                        if (uploadimagepath != null) {

                            //   Picasso.get().load(uploadimagepath).into(image);

                            Toast.makeText(Technician_signature_preventiveActivity.this, "Signature Saved", Toast.LENGTH_SHORT).show();

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
}