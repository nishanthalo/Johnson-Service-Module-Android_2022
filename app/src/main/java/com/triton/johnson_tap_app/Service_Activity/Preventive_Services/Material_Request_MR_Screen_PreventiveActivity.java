package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.triton.johnson_tap_app.R;

public class Material_Request_MR_Screen_PreventiveActivity extends AppCompatActivity {

    private Button btnSelection,btn_prev;
    String valuess="",job_id,Form1_value,value,service_title,Form1_name,Form1_comments,Form1_cat_id,Form1_group_id,str_mr1,str_mr2,str_mr3,str_mr4,str_mr5,str_mr6,str_mr7,str_mr8,str_mr9,str_mr10;
    ImageView iv_back;
    EditText mr1,mr2,mr3,mr4,mr5,mr6,mr7,mr8,mr9,mr10;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_material_request_mr_screen_preventive);

        btnSelection = (Button) findViewById(R.id.btn_next);
        btn_prev = (Button) findViewById(R.id.btn_show);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mr1 = (EditText) findViewById(R.id.mr1);
        mr2 = (EditText) findViewById(R.id.mr2);
        mr3 = (EditText) findViewById(R.id.mr3);
        mr4 = (EditText) findViewById(R.id.mr4);
        mr5 = (EditText) findViewById(R.id.mr5);
        mr6 = (EditText) findViewById(R.id.mr6);
        mr7 = (EditText) findViewById(R.id.mr7);
        mr8 = (EditText) findViewById(R.id.mr8);
        mr9 = (EditText) findViewById(R.id.mr9);
        mr10 = (EditText) findViewById(R.id.mr10);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valuess = extras.getString("valuess");
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
            str_mr1 = extras.getString("mr1");
            mr1.setText(str_mr1);
        }
        if (extras != null) {
            str_mr2 = extras.getString("mr2");
            mr2.setText(str_mr2);
        }
        if (extras != null) {
            str_mr3 = extras.getString("mr3");
            mr3.setText(str_mr3);
        }
        if (extras != null) {
            str_mr4 = extras.getString("mr4");
            mr4.setText(str_mr4);
        }
        if (extras != null) {
            str_mr5 = extras.getString("mr5");
            mr5.setText(str_mr5);
        }
        if (extras != null) {
            str_mr6 = extras.getString("mr6");
            mr6.setText(str_mr6);
        }
        if (extras != null) {
            str_mr7 = extras.getString("mr7");
            mr7.setText(str_mr7);
        }
        if (extras != null) {
            str_mr8 = extras.getString("mr8");
            mr8.setText(str_mr8);
        }
        if (extras != null) {
            str_mr9 = extras.getString("mr9");
            mr9.setText(str_mr9);
        }
        if (extras != null) {
            str_mr10 = extras.getString("mr10");
            mr10.setText(str_mr10);
        }

        btnSelection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String s_mr1 = mr1.getText().toString();
                String s_mr2 = mr2.getText().toString();
                String s_mr3 = mr3.getText().toString();
                String s_mr4 = mr4.getText().toString();
                String s_mr5 = mr5.getText().toString();
                String s_mr6 = mr6.getText().toString();
                String s_mr7 = mr7.getText().toString();
                String s_mr8 = mr8.getText().toString();
                String s_mr9 = mr9.getText().toString();
                String s_mr10 = mr10.getText().toString();

                if (s_mr1.equals("")) {
                    mr1.setError("Please Enter the MR1");
                }
                else {
                    Intent send = new Intent(Material_Request_MR_Screen_PreventiveActivity.this, Preventive_checklistActivity.class);
                    send.putExtra("valuess",valuess);
                    send.putExtra("job_id",job_id);
                    send.putExtra("value",value);
                    send.putExtra("service_title",service_title);
                    send.putExtra("mr1", s_mr1);
                    send.putExtra("mr2", s_mr2);
                    send.putExtra("mr3", s_mr3);
                    send.putExtra("mr4", s_mr4);
                    send.putExtra("mr5", s_mr5);
                    send.putExtra("mr6", s_mr6);
                    send.putExtra("mr7", s_mr7);
                    send.putExtra("mr8", s_mr8);
                    send.putExtra("mr9", s_mr9);
                    send.putExtra("mr10", s_mr10);
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

                Intent send = new Intent( Material_Request_MR_Screen_PreventiveActivity.this, Material_Request_PreventiveActivity.class);
                send.putExtra("valuess",valuess);
                send.putExtra("job_id",job_id);
                send.putExtra("value",value);
                send.putExtra("service_title",service_title);
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