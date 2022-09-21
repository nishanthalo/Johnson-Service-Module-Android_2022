package com.triton.johnson_tap_app.Service_Activity.LR_Service;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.triton.johnson_tap_app.Db.CommonUtil;
import com.triton.johnson_tap_app.Db.DbHelper;
import com.triton.johnson_tap_app.Db.DbUtil;
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

public class TechnicianSignature_LRServiceActivity extends AppCompatActivity {

    SignaturePad signaturePad;
    String userid, uploadimagepath = "";
    Button btn_Save,btn_Clear, btn_Prev, btn_Next;
    ImageView img_Signature,img_Back,img_Pause;
    Context context;
    String status,job_id,str_Custname, str_Custno, str_Custremarks,se_user_mobile_no, se_user_name, se_id,service_title,str_Techsign,str_CustAck,signfile;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    Bitmap signatureBitmap;
    MultipartBody.Part siganaturePart;
    String myactivity = "LR Service";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_techniciansignature_lrservice);
        context = this;

        CommonUtil.dbUtil = new DbUtil(context);
        CommonUtil.dbUtil.open();
        CommonUtil.dbHelper = new DbHelper(context);

        signaturePad = findViewById(R.id.signaturePad);
        btn_Save = findViewById(R.id.btn_save);
        btn_Clear = findViewById(R.id.btn_clear);
        btn_Prev = findViewById(R.id.btn_prev);
        btn_Next = findViewById(R.id.btn_next);
        img_Back = findViewById(R.id.img_back);
        img_Pause = findViewById(R.id.img_paused);
        img_Signature = findViewById(R.id.image1);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //  service_title = extras.getString("service_title");
            status = extras.getString("status");
            //   Log.e("Name",":" + service_title);
            Log.e("Status", "" + status);
            job_id = extras.getString("job_id");
            Log.e("jobID", "" + job_id);
            str_Custname = extras.getString("C_name");
            str_Custno = extras.getString("C_no");
            str_Custremarks = extras.getString("C_remarks");
            str_Techsign = extras.getString("tech_signature");
           // Picasso.get().load(str_Techsign).into((Target) img_Signature);
           // Picasso.get().load(str_Techsign).into(img_Signature);
            str_CustAck = extras.getString("cust_ack");
            Log.e("Name", "" + str_Custname);
            Log.e("Number", "" + str_Custno);
            Log.e("remarks", "" + str_Custremarks);

        }

        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "Services");

        Log.e("name",""+service_title);
        Log.e("Mobile", ""+ se_user_mobile_no);

        getSign(job_id,myactivity);


        btn_Save.setEnabled(false);
        btn_Clear.setEnabled(false);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                btn_Save.setEnabled(true);
                btn_Clear.setEnabled(true);
            }

            @Override
            public void onClear() {
                btn_Save.setEnabled(false);
                btn_Clear.setEnabled(false);
            }
        });

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait Image Upload ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                signatureBitmap = signaturePad.getSignatureBitmap();
                Log.w(TAG, "signatureBitmap" + signatureBitmap);
                File file = new File(getFilesDir(), "Technician Signature" + ".jpg");
                Log.e("Nish",""+file);

                OutputStream os;
                try {
                    os = new FileOutputStream(file);
                    if (signatureBitmap != null) {
                        signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);


                        // image.setImageBitmap(signatureBitmap);
                    }
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                siganaturePart = MultipartBody.Part.createFormData("sampleFile", userid + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

                uploadDigitalSignatureImageRequest(file);


                //Toast.makeText(context,"Signature Saved",Toast.LENGTH_SHORT).show();
            }
        });

        btn_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(context, CustomerDetails_LRServiceActivity.class);
                // send.putExtra("service_title",service_title);
                send.putExtra("status" , status);
                send.putExtra("job_id",job_id);
                send.putExtra("C_name" , str_Custname);
                send.putExtra("C_no" , str_Custno);
                send.putExtra("C_remarks" , str_Custremarks);
                send.putExtra("tech_signature", uploadimagepath);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
            }
        });

        btn_Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(context, CustomerDetails_LRServiceActivity.class);
                // send.putExtra("service_title",service_title);
                send.putExtra("status" , status);
                send.putExtra("job_id",job_id);
                send.putExtra("C_name" , str_Custname);
                send.putExtra("C_no" , str_Custno);
                send.putExtra("C_remarks" , str_Custremarks);
                send.putExtra("tech_signature", uploadimagepath);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(context, CustomerAcknowledgement_LRServiceActivity.class);
                // send.putExtra("service_title",service_title);
                send.putExtra("status" , status);
                send.putExtra("job_id",job_id);
                send.putExtra("C_name" , str_Custname);
                send.putExtra("C_no" , str_Custno);
                send.putExtra("C_remarks" , str_Custremarks);
                send.putExtra("tech_signature", uploadimagepath);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);

            }
        });

    }

    @SuppressLint("Range")
    private void getSign(String job_id, String myactivity) {

        Log.e("Sign", "Hi");
        Log.e("Nish",""+job_id);
        Log.e("Nish",""+myactivity);
        Cursor cur =  CommonUtil.dbUtil.getEngSign(job_id,myactivity);
        Cursor curs =  CommonUtil.dbUtil.getEngSign();
        Log.e("ENg Sign", " " + cur.getCount());

        if (cur.getCount()>0 && cur.moveToFirst()){

            do{
                signfile = cur.getString(cur.getColumnIndex(DbHelper.SIGN_FILE));
                String jon = cur.getString(cur.getColumnIndex(DbHelper.JOBID));
                String ss = cur.getString(cur.getColumnIndex(DbHelper.MYACTIVITY));
                uploadimagepath = cur.getString(cur.getColumnIndex(DbHelper.SIGN_PATH));

                Log.e("job" , ""+jon);
                Log.e("act" , ""+ss);
                Log.e("path" , ""+uploadimagepath);

                Picasso.get().load(uploadimagepath).into(img_Signature);


                ///  BitmapDrawable drawable = (BitmapDrawable) img_Siganture.getDrawable();
                //  Bitmap bitmap = drawable.getBitmap();
                ///  bitmap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);
                //  photo.setImageBitmap(bitmap)
                // Bitmap image = ((BitmapDrawable)img_Siganture.getDrawable()).getBitmap();


                //  File file = new File(signfile);
                //  String filePath = file.getPath();
                //   Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                // img_Siganture.setImageBitmap(bitmap);
                // signaturePad.setSignatureBitmap(bitmap);
                //  signaturePad.setSignatureBitmap(bitmap);
                // signatureBitmap = signaturePad.getSignatureBitmap();
            }while (cur.moveToNext());
        }
    }

    private void  uploadDigitalSignatureImageRequest(File file) {

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

//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("ImagePath" ,""+uploadimagepath );
//                        editor.apply();

                        Log.d("image",uploadimagepath);

                        //  image.setImageURI(Uri.parse(uploadimagepath));
                        if (uploadimagepath != null) {

                            //   Picasso.get().load(uploadimagepath).into(image);

                            Toast.makeText(context, "Signature Saved", Toast.LENGTH_SHORT).show();
                            img_Signature.setVisibility(View.GONE);
                            CommonUtil.dbUtil.addEngSign(job_id,myactivity,uploadimagepath,file,"0");
                            Log.e("a" , "" + job_id);
                            Log.e("b", "" + myactivity);
                            Log.e("c" , "" + uploadimagepath);
                            Log.e("d" , "" + file);
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
        Intent send = new Intent(context, CustomerDetails_LRServiceActivity.class);
        // send.putExtra("service_title",service_title);
        send.putExtra("status" , status);
        send.putExtra("job_id",job_id);
        send.putExtra("C_name" , str_Custname);
        send.putExtra("C_no" , str_Custno);
        send.putExtra("C_remarks" , str_Custremarks);
        send.putExtra("tech_signature", uploadimagepath);
        send.putExtra("cust_ack",str_CustAck);
        startActivity(send);
    }
}