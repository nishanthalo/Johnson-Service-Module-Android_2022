package com.triton.johnson_tap_app.Service_Activity.Breakdown_Services;

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

public class Customer_Details_BreakdownActivity extends AppCompatActivity {

    private Button btnSelection,btn_prev;
    TextView txt_cust_name, txt_cust_no,txt;
    ImageView iv_back;
    String value="",job_id,feedback_group,feedback_details,bd_dta,feedback_remark,mr1,mr2,mr3,mr4,mr5,mr6,mr7,mr8,mr9,mr10,breakdown_servies,tech_signature,str_cust_name="",str_cust_no="",customer_acknowledgement="";
    EditText et_cust_name,et_cust_no;
    String  animal2 ="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_details_breakdown);

        btnSelection = (Button) findViewById(R.id.btn_next);
        txt_cust_name = (TextView) findViewById(R.id.txt_cust_name);
        txt_cust_no = (TextView) findViewById(R.id.txt_cust_no);
        txt = (TextView) findViewById(R.id.txt);
        btn_prev = (Button) findViewById(R.id.btn_show);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_cust_name = (EditText)findViewById(R.id.et_cust_name);
        et_cust_no = (EditText)findViewById(R.id.et_cust_no);

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
            str_cust_name = extras.getString("customer_name");
            et_cust_name.setText(str_cust_name);
        }
        if (extras != null) {
            str_cust_no = extras.getString("customer_number");
            et_cust_no.setText(str_cust_no);
        }

        if (extras != null) {
            customer_acknowledgement = extras.getString("customer_acknowledgement");
        }

        Spannable name_Upload = new SpannableString("Customer Name ");
        name_Upload.setSpan(new ForegroundColorSpan(Customer_Details_BreakdownActivity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_cust_name.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_cust_name.append(name_Upload1);

        Spannable no = new SpannableString("Customer Number ");
        no.setSpan(new ForegroundColorSpan(Customer_Details_BreakdownActivity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_cust_no.setText(no);
        Spannable no1 = new SpannableString("*");
        no1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt_cust_no.append(no1);

        btn_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
        btn_prev.setTextColor(getResources().getColor(R.color.white));
        btn_prev.setEnabled(true);

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
                    Intent send = new Intent(Customer_Details_BreakdownActivity.this, Customer_AcknowledgementActivity.class);
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
                    send.putExtra("tech_signature", tech_signature);
                    send.putExtra("customer_name", s_cust_name);
                    send.putExtra("customer_number", s_cust_no);
                    send.putExtra("customer_acknowledgement", customer_acknowledgement);
                    startActivity(send);
                }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();
//                Intent send = new Intent( Customer_Details_BreakdownActivity.this, Technician_signatureActivity.class);
//                send.putExtra("value",value);
//                send.putExtra("feedback_details",feedback_details);
//                send.putExtra("feedback_group",feedback_group);
//                send.putExtra("bd_details",bd_dta);
//                send.putExtra("job_id",job_id);
//                send.putExtra("feedback_remark", feedback_remark);
//                send.putExtra("mr1", mr1);
//                send.putExtra("mr2", mr2);
//                send.putExtra("mr3", mr3);
//                send.putExtra("mr4", mr4);
//                send.putExtra("mr5", mr5);
//                send.putExtra("mr6", mr6);
//                send.putExtra("mr7", mr7);
//                send.putExtra("mr8", mr8);
//                send.putExtra("mr9", mr9);
//                send.putExtra("mr10", mr10);
//                send.putExtra("breakdown_service", breakdown_servies);
//                send.putExtra("tech_signature", tech_signature);
//                startActivity(send);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

//                Intent send = new Intent( Customer_Details_BreakdownActivity.this, Technician_signatureActivity.class);
//                send.putExtra("value",value);
//                send.putExtra("feedback_details",feedback_details);
//                send.putExtra("feedback_group",feedback_group);
//                send.putExtra("bd_details",bd_dta);
//                send.putExtra("job_id",job_id);
//                send.putExtra("feedback_remark", feedback_remark);
//                send.putExtra("mr1", mr1);
//                send.putExtra("mr2", mr2);
//                send.putExtra("mr3", mr3);
//                send.putExtra("mr4", mr4);
//                send.putExtra("mr5", mr5);
//                send.putExtra("mr6", mr6);
//                send.putExtra("mr7", mr7);
//                send.putExtra("mr8", mr8);
//                send.putExtra("mr9", mr9);
//                send.putExtra("mr10", mr10);
//                send.putExtra("breakdown_service", breakdown_servies);
//                send.putExtra("tech_signature", tech_signature);
//                startActivity(send);
            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}