package com.triton.johnson_tap_app.Service_Activity.Breakdown_Services;

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

public class Technician_signatureActivity extends AppCompatActivity {

    SignaturePad signaturePad;
    Button saveButton, clearButton;
    private Button btnSelection,btn_prev;
    MultipartBody.Part siganaturePart;
    String userid;
    ImageView image,iv_back;
    private String uploadimagepath = "";
    String value="",job_id,feedback_group,feedback_details,bd_dta,feedback_remark,mr1,mr2,mr3,mr4,mr5,mr6,mr7,mr8,mr9,mr10,breakdown_servies,str_tech_signature="";
    ProgressDialog progressDialog;
    Bitmap signatureBitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_technician_signature);

        signaturePad = (SignaturePad)findViewById(R.id.signaturePad);
        saveButton = (Button)findViewById(R.id.saveButton);
        clearButton = (Button)findViewById(R.id.clearButton);
        btnSelection = (Button) findViewById(R.id.btn_next);
        btn_prev = (Button) findViewById(R.id.btn_show);
        image = (ImageView)findViewById(R.id.image);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        btn_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
        btn_prev.setTextColor(getResources().getColor(R.color.white));
        btn_prev.setEnabled(true);

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

                progressDialog = new ProgressDialog(Technician_signatureActivity.this);
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

                siganaturePart = MultipartBody.Part.createFormData("sampleFile", userid + file.getName(),RequestBody.create(MediaType.parse("image/*"), file));

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
                    Toast.makeText(Technician_signatureActivity.this, "Please Drop Signature", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent send = new Intent(Technician_signatureActivity.this, Customer_Details_BreakdownActivity.class);
                send.putExtra("value", value);
                send.putExtra("feedback_details", feedback_details);
                send.putExtra("feedback_group", feedback_group);
                send.putExtra("bd_details", bd_dta);
                send.putExtra("job_id", job_id);
                send.putExtra("feedback_remark", feedback_remark);
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
                send.putExtra("breakdown_service", breakdown_servies);
                send.putExtra("tech_signature", uploadimagepath);
                startActivity(send);
            }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
//                if(uploadimagepath.equals("")) {
//                    Intent send = new Intent(Technician_signatureActivity.this, BD_StatusActivity.class);
//                    send.putExtra("value", value);
//                    send.putExtra("feedback_details", feedback_details);
//                    send.putExtra("feedback_group", feedback_group);
//                    send.putExtra("bd_details", bd_dta);
//                    send.putExtra("job_id", job_id);
//                    send.putExtra("feedback_remark", feedback_remark);
//                    startActivity(send);
//                }
//                else {
//                    Intent send = new Intent(Technician_signatureActivity.this, BD_StatusActivity.class);
//                    send.putExtra("value", value);
//                    send.putExtra("feedback_details", feedback_details);
//                    send.putExtra("feedback_group", feedback_group);
//                    send.putExtra("bd_details", bd_dta);
//                    send.putExtra("job_id", job_id);
//                    send.putExtra("feedback_remark", feedback_remark);
//                    send.putExtra("tech_signature", uploadimagepath);
//                    startActivity(send);
//                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
//                if(uploadimagepath.equals("")) {
//                    Intent send = new Intent(Technician_signatureActivity.this, BD_StatusActivity.class);
//                    send.putExtra("value", value);
//                    send.putExtra("feedback_details", feedback_details);
//                    send.putExtra("feedback_group", feedback_group);
//                    send.putExtra("bd_details", bd_dta);
//                    send.putExtra("job_id", job_id);
//                    send.putExtra("feedback_remark", feedback_remark);
//                    startActivity(send);
//                }
//                else {
//                    Intent send = new Intent(Technician_signatureActivity.this, BD_StatusActivity.class);
//                    send.putExtra("value", value);
//                    send.putExtra("feedback_details", feedback_details);
//                    send.putExtra("feedback_group", feedback_group);
//                    send.putExtra("bd_details", bd_dta);
//                    send.putExtra("job_id", job_id);
//                    send.putExtra("feedback_remark", feedback_remark);
//                    send.putExtra("tech_signature", uploadimagepath);
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

                        Log.d("image",uploadimagepath);
                        if (uploadimagepath != null) {

                         //   Picasso.get().load(uploadimagepath).into(image);

                            Toast.makeText(Technician_signatureActivity.this, "Signature Saved", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {

        onBackPressed();
    }
}