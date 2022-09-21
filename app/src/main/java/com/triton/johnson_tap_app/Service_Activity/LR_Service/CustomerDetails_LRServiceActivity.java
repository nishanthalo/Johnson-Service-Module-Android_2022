package com.triton.johnson_tap_app.Service_Activity.LR_Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.triton.johnson_tap_app.R;

public class CustomerDetails_LRServiceActivity extends AppCompatActivity {

    Context context;
    EditText edt_Custname, edt_Custno, edt_Custremarks;
    TextView txt_Custname, txt_Custno;
    Button btn_Prev,btn_Next;
    String status,job_id,se_user_mobile_no, se_user_name, se_id,service_title,str_Techsign,str_CustAck;
    ImageView img_Back,img_Pause;
    String str_Custname, str_Custno, str_Custremarks;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customerdetails_lrservice);
        context = this;

        edt_Custname = findViewById(R.id.edt_cust_name);
        edt_Custno = findViewById(R.id.edt_cust_no);
        edt_Custremarks = findViewById(R.id.edt_cust_remarks);
        btn_Prev = findViewById(R.id.btn_prev);
        btn_Next = findViewById(R.id.btn_next);
        img_Back = findViewById(R.id.img_back);
        img_Pause = findViewById(R.id.img_paused);
        txt_Custname = findViewById(R.id.txt_cust_name);
        txt_Custno = findViewById(R.id.txt_cust_no);

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
            str_CustAck = extras.getString("cust_ack");
            Log.e("A", "" + str_Custname);
            Log.e("A", "" + str_Custno);
            Log.e("A", "" + str_Custremarks);
            edt_Custname.setText(str_Custname);
            edt_Custno.setText(str_Custno);
            edt_Custremarks.setText(str_Custremarks);
        }
        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "Services");

        Log.e("name",""+service_title);
        Log.e("Mobile", ""+ se_user_mobile_no);

        Spannable name_Upload = new SpannableString("Customer Name ");
        name_Upload.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_Custname.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_Custname.append(name_Upload1);

        Spannable no = new SpannableString("Customer Number ");
        no.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_Custno.setText(no);
        Spannable no1 = new SpannableString("*");
        no1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_Custno.append(no1);

        btn_Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(context, LR_Details_Activity.class);
                // send.putExtra("service_title",service_title);
                send.putExtra("job_id",job_id);
                send.putExtra("status" , status);
                send.putExtra("C_name" , str_Custname);
                send.putExtra("C_no" , str_Custno);
                send.putExtra("C_remarks" , str_Custremarks);
                send.putExtra("tech_signature", str_Techsign);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
            }
        });

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent(context, LR_Details_Activity.class);
                // send.putExtra("service_title",service_title);
                send.putExtra("job_id",job_id);
                send.putExtra("status", status);
                send.putExtra("C_name", str_Custname);
                send.putExtra("C_no", str_Custno);
                send.putExtra("C_remarks", str_Custremarks);
                send.putExtra("tech_signature", str_Techsign);
                send.putExtra("cust_ack", str_CustAck);
                startActivity(send);
            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_Custname = edt_Custname.getText().toString();
                str_Custno = edt_Custno.getText().toString();
                str_Custremarks = edt_Custremarks.getText().toString();

                if (str_Custname.equals("")){
                    edt_Custname.setError("Enter Customer Name");
                }else if(str_Custno.equals("")){
                    edt_Custno.setError("Enter Customer Number");
                }else{

                    Intent send = new Intent(context, TechnicianSignature_LRServiceActivity.class);
                    // send.putExtra("service_title",service_title);
                    send.putExtra("job_id",job_id);
                    send.putExtra("status" , status);
                    send.putExtra("C_name" , str_Custname);
                    send.putExtra("C_no" , str_Custno);
                    send.putExtra("C_remarks" , str_Custremarks);
                    send.putExtra("tech_signature", str_Techsign);
                    send.putExtra("cust_ack",str_CustAck);
                    startActivity(send);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent send = new Intent(context, LR_Details_Activity.class);
        // send.putExtra("service_title",service_title);
        send.putExtra("job_id",job_id);
        send.putExtra("status" , status);
        send.putExtra("C_name" , str_Custname);
        send.putExtra("C_no" , str_Custno);
        send.putExtra("C_remarks" , str_Custremarks);
        send.putExtra("tech_signature", str_Techsign);
        send.putExtra("cust_ack",str_CustAck);
        startActivity(send);
    }
}
