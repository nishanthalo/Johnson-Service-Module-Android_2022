package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.triton.johnson_tap_app.R;

public class Customer_Details_PreventiveActivity extends AppCompatActivity {

    private Button btnSelection,btn_prev;
    TextView txt_cust_name, txt_cust_no,txt;
    ImageView iv_back;
    String value="",job_id,mr1,mr2,mr3,mr4,mr5,mr6,mr7,mr8,mr9,mr10,value_s,service_title,preventive_check,pm_status,Tech_signature,action_req_customer,Form1_value,Form1_name,Form1_comments,customer_acknowledgement="";
    EditText et_cust_name,et_cust_no;
    String Form1_cat_id,Form1_group_id,customer_name="",customer_no="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_details_preventive);

        btnSelection = (Button) findViewById(R.id.btn_next);
        txt_cust_name = (TextView) findViewById(R.id.txt_cust_name);
        txt_cust_no = (TextView) findViewById(R.id.txt_cust_no);
        txt = (TextView) findViewById(R.id.txt);
        btn_prev = (Button) findViewById(R.id.btn_show);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_cust_name = (EditText)findViewById(R.id.et_cust_name);
        et_cust_no = (EditText)findViewById(R.id.et_cust_no);


        Spannable name_Upload = new SpannableString("Customer Name ");
        name_Upload.setSpan(new ForegroundColorSpan(Customer_Details_PreventiveActivity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_cust_name.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_cust_name.append(name_Upload1);

        Spannable no = new SpannableString("Customer Number ");
        no.setSpan(new ForegroundColorSpan(Customer_Details_PreventiveActivity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_cust_no.setText(no);
        Spannable no1 = new SpannableString("*");
        no1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_cust_no.append(no1);

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
            Tech_signature = extras.getString("Tech_signature");
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
            customer_acknowledgement = extras.getString("customer_acknowledgement");
        }

        if (extras != null) {
            customer_name = extras.getString("customer_name");
            et_cust_name.setText(customer_name);
        }
        if (extras != null) {
            customer_no = extras.getString("customer_no");
            et_cust_no.setText(customer_no);
        }

        btnSelection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String s_cust_name = et_cust_name.getText().toString();
                String s_cust_no = et_cust_no.getText().toString();

                if (s_cust_name.equals("")) {
                    et_cust_name.setError("Please Enter the Customer Name");
                }
                else if (s_cust_no.equals("")) {
                    et_cust_no.setError("Please Enter the Customer Number");
                }
                else {
                    Intent send = new Intent(Customer_Details_PreventiveActivity.this, Customer_Acknowledgement_PreventiveActivity.class);
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
                    send.putExtra("preventive_check",preventive_check);
                    send.putExtra("pm_status",pm_status);
                    send.putExtra("Tech_signature",Tech_signature);
                    send.putExtra("customer_no",s_cust_no);
                    send.putExtra("customer_name",s_cust_name);
                    send.putExtra("action_req_customer",action_req_customer);
                    send.putExtra("Form1_value", Form1_value);
                    send.putExtra("Form1_name",Form1_name);
                    send.putExtra("Form1_comments",Form1_comments);
                    send.putExtra("Form1_cat_id",Form1_cat_id);
                    send.putExtra("Form1_group_id",Form1_group_id);
                    send.putExtra("customer_acknowledgement", customer_acknowledgement);
                    startActivity(send);
               }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent send = new Intent( Customer_Details_PreventiveActivity.this, Technician_signature_preventiveActivity.class);
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
                send.putExtra("preventive_check",preventive_check);
                send.putExtra("pm_status",pm_status);
                send.putExtra("Tech_signature",Tech_signature);
                send.putExtra("action_req_customer",action_req_customer);
                send.putExtra("Form1_value", Form1_value);
                send.putExtra("Form1_name",Form1_name);
                send.putExtra("Form1_comments",Form1_comments);
                send.putExtra("Form1_cat_id",Form1_cat_id);
                send.putExtra("Form1_group_id",Form1_group_id);
                send.putExtra("tech_signature",Tech_signature);
                startActivity(send);
            }
        });
    }
}