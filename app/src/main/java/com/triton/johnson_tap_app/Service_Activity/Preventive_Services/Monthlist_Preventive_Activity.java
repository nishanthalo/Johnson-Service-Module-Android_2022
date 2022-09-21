package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson_tap_app.R;

import java.util.ArrayList;

public class Monthlist_Preventive_Activity extends AppCompatActivity implements  JobDateListener{

    RecyclerView recyclerView;
    ImageView img_Back, img_Pause;
    Button btn_Prev, btn_Next;
    String str_job_id,service_title,value,se_id,se_user_mobile_no,compno,sertype;
    Context context;
    ArrayList<String> arli_Month = new ArrayList<String>();
    MonthList_Preventive_Adapter petBreedTypesListAdapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.monthlist_preventive);
        context = this;

        recyclerView = findViewById(R.id.recyclerView);
        img_Back = findViewById(R.id.img_back);
        img_Pause = findViewById(R.id.img_paused);
        btn_Prev = findViewById(R.id.btn_prev);
        btn_Next = findViewById(R.id.btn_next);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        service_title = sharedPreferences.getString("service_title", "default value");

        compno = sharedPreferences.getString("compno","123");
        sertype = sharedPreferences.getString("sertype","123");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_job_id = extras.getString("job_id");
            service_title = extras.getString("service_title");
            value = extras.getString("value");
        }

        arli_Month.add("January");
        arli_Month.add("February");
        arli_Month.add("March");
        arli_Month.add("April");
        arli_Month.add("May");
        arli_Month.add("June");
        arli_Month.add("July");
        arli_Month.add("August");
        arli_Month.add("September");
        arli_Month.add("October");
        arli_Month.add("November");
        arli_Month.add("December");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        petBreedTypesListAdapter = new MonthList_Preventive_Adapter(arli_Month,this, (JobDateListener) this);
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

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Start_Job_Text_PreventiveActivity.class);
                intent.putExtra("job_id",str_job_id);
                intent.putExtra("value",value);
                intent.putExtra("service_title",service_title);
                startActivity(intent);
            }
        });

        btn_Prev.setOnClickListener(new View.OnClickListener() {
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
    public void onMonthchange(ArrayList<String> arrayList) {

        //Toast.makeText(context, arrayList.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, Start_Job_Text_PreventiveActivity.class);
        intent.putExtra("job_id",str_job_id);
        intent.putExtra("value",value);
        intent.putExtra("service_title",service_title);
        startActivity(intent);
    }
}
