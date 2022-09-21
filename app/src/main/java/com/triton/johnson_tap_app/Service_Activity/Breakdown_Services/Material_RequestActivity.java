package com.triton.johnson_tap_app.Service_Activity.Breakdown_Services;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.triton.johnson_tap_app.R;

public class Material_RequestActivity extends AppCompatActivity {

    TextView text;
    CardView yes,no;
    String value;
    ImageView iv_back;
    String job_id,feedback_group,feedback_details,bd_dta,feedback_remark,tech_signature="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_material_request);

        text = findViewById(R.id.text);
        yes = findViewById(R.id.card_yes);
        no = findViewById(R.id.card_no);
        iv_back = (ImageView) findViewById(R.id.iv_back);

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
            feedback_remark = extras.getString("feedback_remark");
        }

        if (extras != null) {
            tech_signature = extras.getString("tech_signature");
        }

        Spannable name_Upload = new SpannableString("Raise MR ");
        name_Upload.setSpan(new ForegroundColorSpan(Material_RequestActivity.this.getResources().getColor(R.color.colorAccent)), 0, name_Upload.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(name_Upload);
        Spannable name_Upload1 = new SpannableString("*");
        name_Upload1.setSpan(new ForegroundColorSpan(Color.RED), 0, name_Upload1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.append(name_Upload1);

        yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

              //  Toast.makeText(Material_RequestActivity.this, feedback_details, Toast.LENGTH_LONG).show();

                        Intent send = new Intent( Material_RequestActivity.this, Material_Request_MR_ScreenActivity.class);
                        send.putExtra("value","yes");
                        send.putExtra("feedback_details",feedback_details);
                        send.putExtra("feedback_group",feedback_group);
                        send.putExtra("bd_details",bd_dta);
                        send.putExtra("job_id",job_id);
                        send.putExtra("feedback_remark", feedback_remark);
                        startActivity(send);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent send = new Intent( Material_RequestActivity.this, BD_StatusActivity.class);
                send.putExtra("value","no");
                send.putExtra("feedback_details",feedback_details);
                send.putExtra("feedback_group",feedback_group);
                send.putExtra("bd_details",bd_dta);
                send.putExtra("job_id",job_id);
                send.putExtra("feedback_remark", feedback_remark);
                startActivity(send);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

//                    Intent send = new Intent(Material_RequestActivity.this, Feedback_RemarkActivity.class);
//                    send.putExtra("feedback_details", feedback_details);
//                    send.putExtra("feedback_group", feedback_group);
//                    send.putExtra("bd_details", bd_dta);
//                   send.putExtra("job_id", job_id);
//                   send.putExtra("feedback_remark", feedback_remark);
//                    startActivity(send);
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
//        Intent send = new Intent(Material_RequestActivity.this, Feedback_RemarkActivity.class);
//        send.putExtra("feedback_details", feedback_details);
//        send.putExtra("feedback_group", feedback_group);
//        send.putExtra("bd_details", bd_dta);
//        send.putExtra("job_id", job_id);
//        send.putExtra("feedback_remark", feedback_remark);
//        startActivity(send);
    }
}