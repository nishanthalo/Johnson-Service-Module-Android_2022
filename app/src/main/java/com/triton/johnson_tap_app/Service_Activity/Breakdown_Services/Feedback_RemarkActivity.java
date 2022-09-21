package com.triton.johnson_tap_app.Service_Activity.Breakdown_Services;

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

public class Feedback_RemarkActivity extends AppCompatActivity {

    TextView text,txt;
    private Button btnSelection, btn_prev;
    ImageView iv_back;
    EditText feedback_remark;
    String s_feedback_remark;
    String job_id,feedback_group,feedback_details,bd_dta,str_feedback_remark="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_feedback_remark);

        text = findViewById(R.id.text);
        txt = findViewById(R.id.txt);
        btnSelection = (Button) findViewById(R.id.btn_next);
        btn_prev = (Button) findViewById(R.id.btn_show);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        feedback_remark = (EditText)findViewById(R.id.feedback_remark);

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

        if (extras != null) {
            feedback_details = extras.getString("feedback_details");
        }

        if (extras != null) {
            str_feedback_remark = extras.getString("feedback_remark");
            feedback_remark.setText(str_feedback_remark);
        }

        Spannable name_Upload = new SpannableString("Feedback Remark ");
        name_Upload.setSpan(new ForegroundColorSpan(Feedback_RemarkActivity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.append(name_Upload1);

        btn_prev.setBackgroundResource(R.drawable.blue_button_background_with_radius);
        btn_prev.setTextColor(getResources().getColor(R.color.white));
        btn_prev.setEnabled(true);

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

                s_feedback_remark = feedback_remark.getText().toString();

                if (s_feedback_remark.equals("")) {
                    feedback_remark.setError("Please Enter the Feedback Remark");
                }
                else {
                    Intent send = new Intent(Feedback_RemarkActivity.this, Material_RequestActivity.class);
                    send.putExtra("feedback_details", feedback_details);
                    send.putExtra("feedback_group", feedback_group);
                    send.putExtra("bd_details", bd_dta);
                    send.putExtra("job_id", job_id);
                    send.putExtra("feedback_remark", s_feedback_remark);
                    startActivity(send);
                }

            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

//                Intent send = new Intent( Feedback_RemarkActivity.this, Feedback_DetailsActivity.class);
//                send.putExtra("feedback_details",feedback_details);
//                send.putExtra("feedback_group",feedback_group);
//                send.putExtra("bd_details",bd_dta);
//                send.putExtra("job_id",job_id);
//                startActivity(send);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

//                Intent send = new Intent( Feedback_RemarkActivity.this, Feedback_DetailsActivity.class);
//                send.putExtra("feedback_details",feedback_details);
//                send.putExtra("feedback_group",feedback_group);
//                send.putExtra("bd_details",bd_dta);
//                send.putExtra("job_id",job_id);
//                startActivity(send);
            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
//        Intent send = new Intent( Feedback_RemarkActivity.this, Feedback_DetailsActivity.class);
//        send.putExtra("feedback_details",feedback_details);
//        send.putExtra("feedback_group",feedback_group);
//        send.putExtra("bd_details",bd_dta);
//        send.putExtra("job_id",job_id);
//        startActivity(send);
    }
}