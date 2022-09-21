package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson_tap_app.R;

import java.util.ArrayList;

public class ESCTRV extends AppCompatActivity implements JobDateListener {

    ImageView iv_Back;
    RecyclerView recyclerView;
    Button btn_Next;
    String str_job_id,service_title,value,se_id,se_user_mobile_no,compno,sertype;
    Context context;
    ArrayList<String> arli_Month = new ArrayList<String>();
    ESCTRV_adapter petBreedTypesListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_esc_trv);
        context = this;

        recyclerView = findViewById(R.id.recyclerView);
        iv_Back = findViewById(R.id.iv_back);
       // img_Pause = findViewById(R.id.img_paused);
        btn_Next = findViewById(R.id.btn_next);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        service_title = sharedPreferences.getString("service_title", "default value");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_job_id = extras.getString("job_id");
            service_title = extras.getString("service_title");
            value = extras.getString("value");
        }

        arli_Month.add("MONTHLY");
        arli_Month.add("QUARTERLY");
        arli_Month.add("HALF YEARLY");
        arli_Month.add("YEARLY");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        petBreedTypesListAdapter = new ESCTRV_adapter(arli_Month,this, (JobDateListener) this);
        recyclerView.setAdapter(petBreedTypesListAdapter);

        btn_Next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //  ArrayList<String> arrayList = new ArrayList<String>();
                Intent intent = new Intent(context, Recycler_SpinnerActivity.class);
//                Bundle args = new Bundle();
//                args.putSerializable("ARRAYLIST",(Serializable)arrayList);
//                intent.putExtra("BUNDLE",args);
                intent.putExtra("job_id",str_job_id);
                intent.putExtra("value",value);
                intent.putExtra("service_title",service_title);
                startActivity(intent);

            }
        });

        iv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Start_Job_Text_PreventiveActivity.class);
                intent.putExtra("job_id",str_job_id);
                intent.putExtra("value",value);
                intent.putExtra("service_title",service_title);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, Start_Job_Text_PreventiveActivity.class);
        intent.putExtra("job_id",str_job_id);
        intent.putExtra("value",value);
        intent.putExtra("service_title",service_title);
        startActivity(intent);
    }

    @Override
    public void onMonthchange(ArrayList<String> arrayList) {
        //Toast.makeText(context, arrayList.toString(), Toast.LENGTH_SHORT).show();
    }
}
