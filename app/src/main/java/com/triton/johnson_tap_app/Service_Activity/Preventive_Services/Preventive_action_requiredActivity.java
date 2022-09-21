package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.triton.johnson_tap_app.R;

public class Preventive_action_requiredActivity extends AppCompatActivity {

    TextView text,txt;
    private Button btnSelection, btn_prev;
    ImageView iv_back;
    EditText feedback_remark;
    String job_id,value_s,service_title,value,s_remark,mr1,mr2,mr3,mr4,mr5,mr6,mr7,mr8,mr9,mr10,preventive_check,Form1_value,Form1_name,Form1_comments;
    String Form1_cat_id,Form1_group_id,action_req_customer="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_preventive_action_required);

        text = findViewById(R.id.text);
        txt = findViewById(R.id.txt);
        btnSelection = (Button) findViewById(R.id.btn_next);
        btn_prev = (Button) findViewById(R.id.btn_show);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        feedback_remark = (EditText)findViewById(R.id.feedback_remark);

        Spannable name_Upload = new SpannableString("Action Required By Customer ");
        name_Upload.setSpan(new ForegroundColorSpan(Preventive_action_requiredActivity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.append(name_Upload1);

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
            action_req_customer = extras.getString("action_req_customer");
            feedback_remark.setText(action_req_customer);
        }

      //  Toasty.warning(getApplicationContext(), "" + preventive_check, Toasty.LENGTH_LONG).show();

        feedback_remark.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                txt.setText(s.toString().length() + "/250");

            }
        });

        btnSelection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                s_remark = feedback_remark.getText().toString();

                if (s_remark.equals("")) {
                    feedback_remark.setError("Please Enter the Feedback Remark");
                }
                else {
                    Intent send = new Intent(Preventive_action_requiredActivity.this, Preventive_StatusActivity.class);
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
                    send.putExtra("action_req_customer",s_remark);
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

                Intent send = new Intent( Preventive_action_requiredActivity.this, Preventive_checklistActivity.class);
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
                send.putExtra("Form1_value", Form1_value);
                send.putExtra("Form1_name",Form1_name);
                send.putExtra("Form1_comments",Form1_comments);
                send.putExtra("Form1_cat_id",Form1_cat_id);
                send.putExtra("Form1_group_id",Form1_group_id);
                startActivity(send);
            }
        });
    }
}