package com.triton.johnson_tap_app.Service_Activity.PartsReplacementACK;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Custom_detailsRequest;
import com.triton.johnson_tap_app.responsepojo.Material_DetailsResponseACK;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialDetailsACK_Activity extends AppCompatActivity {

    Context context;
    String status,job_id,se_user_mobile_no, se_user_name, se_id,check_id, service_title,message,compno,sertype,str_Techsign,str_CustAck,str_ACKCompno;
    TextView txt_Partname,txt_MaterialQty, txt_MaterialID, txt_SeqNumber;
    Button btn_Prev, btn_Next;
    ImageView img_Back;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    List<Material_DetailsResponseACK.DataBean> breedTypedataBeanList;
    MaterialDetailsACK_Adapter petBreedTypesListAdapter;
    private String PetBreedType = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_materialdetailsack);
        context = this;

        txt_Partname = findViewById(R.id.txt_partname);
        txt_MaterialQty = findViewById(R.id.txt_materialqty);
        txt_MaterialID = findViewById(R.id.txt_materialid);
        txt_SeqNumber = findViewById(R.id.txt_seqnumber);
        btn_Prev = findViewById(R.id.btn_prev);
        btn_Next = findViewById(R.id.btn_next);
        img_Back = findViewById(R.id.img_back);
        recyclerView  =findViewById(R.id.recyclerView);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");
        service_title = sharedPreferences.getString("service_title", "Services");
        str_ACKCompno = sharedPreferences.getString("ackcompno","123");
        // compno = sharedPreferences.getString("compno","123");
        // sertype = sharedPreferences.getString("sertype","123");

        Log.e("Name", "" + service_title);
        Log.e("Mobile", ""+ se_user_mobile_no);
        Log.e("ACKCompno","" +str_ACKCompno);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            //  service_title = extras.getString("service_title");
            status = extras.getString("status");
            //   Log.e("Name",":" + service_title);
            Log.e("Status", "" + status);
            job_id = extras.getString("job_id");
            Log.e("JobID",""+job_id);
            str_Techsign = extras.getString("tech_signature");
            str_CustAck = extras.getString("cust_ack");
        }

        materialDetailscall();

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(context, MRDetails_Activity.class);
                // send.putExtra("service_title",service_title);
                send.putExtra("job_id",job_id);
                send.putExtra("status" , status);
                send.putExtra("tech_signature", str_Techsign);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
            }
        });

        btn_Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(context, MRDetails_Activity.class);
                // send.putExtra("service_title",service_title);
                send.putExtra("job_id",job_id);
                send.putExtra("status" , status);
                send.putExtra("tech_signature", str_Techsign);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent send = new Intent(context, TechnicianSignature_ACKServiceActivity.class);
                // send.putExtra("service_title",service_title);
                send.putExtra("job_id",job_id);
                send.putExtra("status" , status);
                send.putExtra("tech_signature", str_Techsign);
                send.putExtra("cust_ack",str_CustAck);
                startActivity(send);
            }
        });
    }

    private void materialDetailscall() {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Material_DetailsResponseACK> call = apiInterface.MaterialResponseCall(RestUtils.getContentType(),materialrequest());
        Log.w(TAG, "Material Details Response url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<Material_DetailsResponseACK>() {
            @Override
            public void onResponse(Call<Material_DetailsResponseACK> call, Response<Material_DetailsResponseACK> response) {

                Log.e(VolleyLog.TAG,"Material Response  "  + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    message = response.body().getMessage();
                    Log.d("message", message);

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {
                            breedTypedataBeanList = response.body().getData();

                            setBreedTypeView(breedTypedataBeanList);
                            Log.d("dataaaaa", String.valueOf(breedTypedataBeanList));

                        }

                    } else if (400 == response.body().getCode()) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                        }
                    } else {

                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<Material_DetailsResponseACK> call, Throwable t) {
                Log.e("Material Details On Failure ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBreedTypeView(List<Material_DetailsResponseACK.DataBean> breedTypedataBeanList) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        petBreedTypesListAdapter = new MaterialDetailsACK_Adapter(getApplicationContext(), breedTypedataBeanList,status);
        recyclerView.setAdapter(petBreedTypesListAdapter);
    }

    private Custom_detailsRequest materialrequest() {
        Custom_detailsRequest custom = new Custom_detailsRequest();
        custom.setJob_id(job_id);
        custom.setUser_mobile_no(se_user_mobile_no);
        custom.setService_name(service_title);
        custom.setSMU_ACK_COMPNO(str_ACKCompno);
        Log.w(VolleyLog.TAG,"MR Details Request "+ new Gson().toJson(custom));
        return custom;
    }

    public void petBreedTypeSelectListener(String petbreedtitle, String petbreedid) {
        PetBreedType = petbreedtitle;
    }

    @Override
    public void onBackPressed() {
        Intent send = new Intent(context, MRDetails_Activity.class);
        // send.putExtra("service_title",service_title);
        send.putExtra("job_id",job_id);
        send.putExtra("status" , status);
        send.putExtra("tech_signature", str_Techsign);
        send.putExtra("cust_ack",str_CustAck);
        startActivity(send);
    }
}
