package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyLog;
import com.bumptech.glide.Glide;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.triton.johnson_tap_app.GetFieldListResponse;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.RestUtils;
import com.triton.johnson_tap_app.Service_Activity.Breakdown_Services.Customer_AcknowledgementActivity;
import com.triton.johnson_tap_app.Service_Activity.ServicesActivity;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.requestpojo.Job_Details_TextRequest;
import com.triton.johnson_tap_app.requestpojo.Job_status_updateRequest;
import com.triton.johnson_tap_app.requestpojo.Preventive_Submit_Request;
import com.triton.johnson_tap_app.responsepojo.Breakdown_submitrResponse;
import com.triton.johnson_tap_app.responsepojo.Feedback_DetailsResponse;
import com.triton.johnson_tap_app.responsepojo.FileUploadResponse;
import com.triton.johnson_tap_app.responsepojo.Job_Details_TextResponse;
import com.triton.johnson_tap_app.responsepojo.Job_status_updateResponse;
import com.triton.johnson_tap_app.responsepojo.SubmitPreventiveResponse;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_Acknowledgement_PreventiveActivity extends AppCompatActivity {

    SignaturePad signaturePad;
    Button saveButton, clearButton;
    Button btn_success, btn_prev;
    ImageView iv_back;
    MultipartBody.Part siganaturePart;
    String userid;
    ImageView image;
    String str2="",sstring;
    private String uploadimagepath = "";
    private List<Breakdown_submitrResponse.DataBean.Feedback_detailsBean> defaultLocationList ;
    List<Feedback_DetailsResponse.DataBean> pet_imgList = new ArrayList();
    String value="",job_id,mr1,mr2,mr3,mr4,mr5,mr6,mr7,mr8,mr9,mr10,tech_signature,customer_name,customer_no,value_s,preventive_check,pm_status,action_req_customer,Form1_value,Form1_name,Form1_comments;
    String se_user_mobile_no, se_user_name, se_id,check_id,service_title,Form1_cat_id,Form1_group_id;
    String str_job_status,message,str_customer_acknowledgement;
    ProgressDialog progressDialog;
    TextView job_details_text;
    String str_field_value,str_field_value1,str_field_value2,str_field_value3,str_field_value4,str_field_value5,str_field_value6,str_field_value7,str_field_value8,str_field_value9,str_field_value10,str_field_value11,str_field_value12,str_field_value13,str_field_value14,str_field_value15,str_field_value16,str_field_value17,str_field_value18,str_field_value19,str_field_value20,str_field_value21,str_field_value22,str_field_value23,str_field_value24,str_field_value25,str_field_value26,str_field_value27,str_field_value28,str_field_value29,str_field_value30,str_field_value31,str_field_value32,str_field_value33,str_field_value34,str_field_value35,str_field_value36, str_field_value37,str_field_value38,str_field_value39,str_field_value40,str_field_value41,str_field_value42,str_field_value43,str_field_value44,str_field_value45,str_field_value46,str_field_value47,str_field_value48,str_field_value49,str_field_value50,str_field_value51,str_field_value52,str_field_value53,str_field_value54,str_field_value55,str_field_value56,str_field_value57,str_field_value58,str_field_value59,str_field_value60,str_field_value61,str_field_value62,str_field_value63,str_field_value64,str_field_value65,str_field_value66,str_field_value67,str_field_value68,str_field_value69,str_field_value70,str_field_value71,str_field_value72,str_field_value73,str_field_value74,str_field_value75,str_field_value76,str_field_value77,str_field_value78,str_field_value79,str_field_value80,str_field_value81,str_field_value82,str_field_value83,str_field_value84,str_field_value85,str_field_value86,str_field_value87,str_field_value88,str_field_value89,str_field_value90,str_field_value91,str_field_value92,str_field_value93,str_field_value94,str_field_value95,str_field_value96,str_field_value97,str_field_value98,str_field_value99,str_field_value100;
    String str_field_name,str_field_name1,str_field_name2,str_field_name3,str_field_name4,str_field_name5,str_field_name6,str_field_name7,str_field_name8,str_field_name9,str_field_name10,str_field_name11,str_field_name12,str_field_name13,str_field_name14,str_field_name15,str_field_name16,str_field_name17,str_field_name18,str_field_name19,str_field_name20,str_field_name21,str_field_name22,str_field_name23,str_field_name24,str_field_name25,str_field_name26,str_field_name27,str_field_name28,str_field_name29,str_field_name30,str_field_name31,str_field_name32,str_field_name33,str_field_name34,str_field_name35,str_field_name36,str_field_name37,str_field_name38,str_field_name39,str_field_name40,str_field_name41,str_field_name42,str_field_name43,str_field_name44,str_field_name45,str_field_name46,str_field_name47,str_field_name48,str_field_name49,str_field_name50,str_field_name51,str_field_name52,str_field_name53,str_field_name54,str_field_name55,str_field_name56,str_field_name57,str_field_name58,str_field_name59,str_field_name60,str_field_name61,str_field_name62,str_field_name63,str_field_name64,str_field_name65,str_field_name66,str_field_name67,str_field_name68,str_field_name69,str_field_name70,str_field_name71,str_field_name72,str_field_name73,str_field_name74,str_field_name75,str_field_name76,str_field_name77,str_field_name78,str_field_name79,str_field_name80,str_field_name81,str_field_name82,str_field_name83,str_field_name84,str_field_name85,str_field_name86,str_field_name87,str_field_name88,str_field_name89,str_field_name90,str_field_name91,str_field_name92,str_field_name93,str_field_name94,str_field_name95,str_field_name96,str_field_name97,str_field_name98,str_field_name99,str_field_name100;
    String str_field_comments,str_field_comments1,str_field_comments2,str_field_comments3,str_field_comments4,str_field_comments5,str_field_comments6,str_field_comments7,str_field_comments8,str_field_comments9,str_field_comments10,str_field_comments11,str_field_comments12,str_field_comments13,str_field_comments14,str_field_comments15,str_field_comments16,str_field_comments17,str_field_comments18,str_field_comments19,str_field_comments20,str_field_comments21,str_field_comments22,str_field_comments23,str_field_comments24,str_field_comments25,str_field_comments26,str_field_comments27,str_field_comments28,str_field_comments29,str_field_comments30,str_field_comments31,str_field_comments32,str_field_comments33,str_field_comments34,str_field_comments35,str_field_comments36,str_field_comments37,str_field_comments38,str_field_comments39,str_field_comments40,str_field_comments41,str_field_comments42,str_field_comments43,str_field_comments44,str_field_comments45,str_field_comments46,str_field_comments47,str_field_comments48,str_field_comments49,str_field_comments50,str_field_comments51,str_field_comments52,str_field_comments53,str_field_comments54,str_field_comments55,str_field_comments56,str_field_comments57,str_field_comments58,str_field_comments59,str_field_comments60,str_field_comments61,str_field_comments62,str_field_comments63,str_field_comments64,str_field_comments65,str_field_comments66,str_field_comments67,str_field_comments68,str_field_comments69,str_field_comments70,str_field_comments71,str_field_comments72,str_field_comments73,str_field_comments74,str_field_comments75,str_field_comments76,str_field_comments77,str_field_comments78,str_field_comments79,str_field_comments80,str_field_comments81,str_field_comments82,str_field_comments83,str_field_comments84,str_field_comments85,str_field_comments86,str_field_comments87,str_field_comments88,str_field_comments89,str_field_comments90,str_field_comments91,str_field_comments92,str_field_comments93,str_field_comments94,str_field_comments95,str_field_comments96,str_field_comments97,str_field_comments98,str_field_comments99,str_field_comments100;
    String str_field_cat_id,str_field_cat_id1,str_field_cat_id2,str_field_cat_id3,str_field_cat_id4,str_field_cat_id5,str_field_cat_id6,str_field_cat_id7,str_field_cat_id8,str_field_cat_id9,str_field_cat_id10,str_field_cat_id11,str_field_cat_id12,str_field_cat_id13,str_field_cat_id14,str_field_cat_id15,str_field_cat_id16,str_field_cat_id17,str_field_cat_id18,str_field_cat_id19,str_field_cat_id20,str_field_cat_id21,str_field_cat_id22,str_field_cat_id23,str_field_cat_id24,str_field_cat_id25,str_field_cat_id26,str_field_cat_id27,str_field_cat_id28,str_field_cat_id29,str_field_cat_id30,str_field_cat_id31,str_field_cat_id32,str_field_cat_id33,str_field_cat_id34,str_field_cat_id35,str_field_cat_id36,str_field_cat_id37,str_field_cat_id38,str_field_cat_id39,str_field_cat_id40,str_field_cat_id41,str_field_cat_id42,str_field_cat_id43,str_field_cat_id44,str_field_cat_id45,str_field_cat_id46,str_field_cat_id47,str_field_cat_id48,str_field_cat_id49, str_field_cat_id50,str_field_cat_id51,str_field_cat_id52,str_field_cat_id53,str_field_cat_id54,str_field_cat_id55,str_field_cat_id56,str_field_cat_id57,str_field_cat_id58,str_field_cat_id59,str_field_cat_id60,str_field_cat_id61,str_field_cat_id62,str_field_cat_id63,str_field_cat_id64,str_field_cat_id65,str_field_cat_id66,str_field_cat_id67,str_field_cat_id68,str_field_cat_id69,str_field_cat_id70,str_field_cat_id71,str_field_cat_id72,str_field_cat_id73,str_field_cat_id74,str_field_cat_id75,str_field_cat_id76,str_field_cat_id77,str_field_cat_id78,str_field_cat_id79,str_field_cat_id80,str_field_cat_id81,str_field_cat_id82,str_field_cat_id83,str_field_cat_id84,str_field_cat_id85,str_field_cat_id86,str_field_cat_id87,str_field_cat_id88,str_field_cat_id89,str_field_cat_id90,str_field_cat_id91,str_field_cat_id92,str_field_cat_id93,str_field_cat_id94,str_field_cat_id95,str_field_cat_id96,str_field_cat_id97,str_field_cat_id98,str_field_cat_id99,str_field_cat_id100;
    String str_field_group_id,str_field_group_id1,str_field_group_id2,str_field_group_id3,str_field_group_id4,str_field_group_id5,str_field_group_id6,str_field_group_id7,str_field_group_id8,str_field_group_id9,str_field_group_id10,str_field_group_id11,str_field_group_id12,str_field_group_id13,str_field_group_id14,str_field_group_id15,str_field_group_id16,str_field_group_id17,str_field_group_id18,str_field_group_id19,str_field_group_id20,str_field_group_id21,str_field_group_id22,str_field_group_id23,str_field_group_id24,str_field_group_id25,str_field_group_id26,str_field_group_id27,str_field_group_id28,str_field_group_id29,str_field_group_id30,str_field_group_id31,str_field_group_id32,str_field_group_id33,str_field_group_id34,str_field_group_id35,str_field_group_id36,str_field_group_id37,str_field_group_id38,str_field_group_id39,str_field_group_id40,str_field_group_id41,str_field_group_id42,str_field_group_id43,str_field_group_id44,str_field_group_id45,str_field_group_id46,str_field_group_id47,str_field_group_id48,str_field_group_id49,str_field_group_id50,str_field_group_id51,str_field_group_id52,str_field_group_id53,str_field_group_id54,str_field_group_id55,str_field_group_id56,str_field_group_id57,str_field_group_id58,str_field_group_id59,str_field_group_id60,str_field_group_id61,str_field_group_id62,str_field_group_id63,str_field_group_id64,str_field_group_id65,str_field_group_id66,str_field_group_id67,str_field_group_id68,str_field_group_id69,str_field_group_id70,str_field_group_id71,str_field_group_id72,str_field_group_id73,str_field_group_id74,str_field_group_id75,str_field_group_id76,str_field_group_id77,str_field_group_id78,str_field_group_id79,str_field_group_id80,str_field_group_id81,str_field_group_id82,str_field_group_id83,str_field_group_id84,str_field_group_id85,str_field_group_id86,str_field_group_id87,str_field_group_id88,str_field_group_id89,str_field_group_id90,str_field_group_id91,str_field_group_id92,str_field_group_id93,str_field_group_id94,str_field_group_id95,str_field_group_id96,str_field_group_id97,str_field_group_id98,str_field_group_id99,str_field_group_id100;
    Bitmap signatureBitmap;
    Dialog dialog;
    String compno, sertype;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customer_acknowledgement_preventive);

        signaturePad = (SignaturePad)findViewById(R.id.signaturePad);
        saveButton = (Button)findViewById(R.id.saveButton);
        clearButton = (Button)findViewById(R.id.clearButton);
        btn_success = (Button) findViewById(R.id.btn_success);
        btn_prev = (Button) findViewById(R.id.btn_show);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        image = (ImageView)findViewById(R.id.image);
        job_details_text = (TextView) findViewById(R.id.job_details_text);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        se_id = sharedPreferences.getString("_id", "default value");
        se_user_mobile_no = sharedPreferences.getString("user_mobile_no", "default value");
        se_user_name = sharedPreferences.getString("user_name", "default value");

        compno = sharedPreferences.getString("compno","123");
        sertype = sharedPreferences.getString("sertype","123");

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
            tech_signature = extras.getString("Tech_signature");
        }
        if (extras != null) {
            customer_name = extras.getString("customer_name");
        }
        if (extras != null) {
            customer_no = extras.getString("customer_no");
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
            str_customer_acknowledgement = extras.getString("customer_acknowledgement");
            Picasso.get().load(str_customer_acknowledgement).into(image);
        }

     //    Log.d("dddddddddddddddddddd", Form1_cat_id + "--------------------------->" + Form1_group_id);

        String[] namesList = Form1_value.split(",");
        str_field_value = namesList [0];
        str_field_value1 = namesList [1];
        str_field_value2 = namesList [2];
        str_field_value3 = namesList [3];
        str_field_value4 = namesList [4];
        str_field_value5 = namesList [5];
        str_field_value6 = namesList [6];
        str_field_value7 = namesList [7];
        str_field_value8 = namesList [8];
        str_field_value9 = namesList [9];
        str_field_value10 = namesList [10];
        str_field_value11 = namesList [11];
        str_field_value12 = namesList [12];
        str_field_value13 = namesList [13];
        str_field_value14 = namesList [14];
        str_field_value15 = namesList [15];
        str_field_value16 = namesList [16];
        str_field_value17 = namesList [17];
        str_field_value18 = namesList [18];
        str_field_value19 = namesList [19];
//        str_field_value20 = namesList [20];
//        str_field_value21 = namesList [21];
//        str_field_value22 = namesList [22];
//        str_field_value23 = namesList [23];
//        str_field_value24 = namesList [24];
//        str_field_value25 = namesList [25];
//        str_field_value26 = namesList [26];
//        str_field_value27 = namesList [27];
//        str_field_value28 = namesList [28];
//        str_field_value29 = namesList [29];
//        str_field_value30 = namesList [30];
//        str_field_value31 = namesList [31];
//        str_field_value32 = namesList [32];
//        str_field_value33 = namesList [33];
//        str_field_value34 = namesList [34];
//        str_field_value35 = namesList [35];
//        str_field_value36 = namesList [36];
//        str_field_value37 = namesList [37];
//        str_field_value38 = namesList [38];
//        str_field_value39 = namesList [39];
//        str_field_value40 = namesList [40];
//        str_field_value41 = namesList [41];
//        str_field_value42 = namesList [42];
//        str_field_value43 = namesList [43];
//        str_field_value44 = namesList [44];
//        str_field_value45 = namesList [45];
//        str_field_value46 = namesList [46];
//        str_field_value47 = namesList [47];
//        str_field_value48 = namesList [48];
//        str_field_value49 = namesList [49];
//        str_field_value50 = namesList [50];
//        str_field_value51 = namesList [51];
//        str_field_value52 = namesList [52];
//        str_field_value53 = namesList [53];
//        str_field_value54 = namesList [54];
//        str_field_value55 = namesList [55];
//        str_field_value56 = namesList [56];
//        str_field_value57 = namesList [57];
//        str_field_value58 = namesList [58];
//        str_field_value59 = namesList [59];
//        str_field_value60 = namesList [60];
//        str_field_value61 = namesList [61];
//        str_field_value62 = namesList [62];
//        str_field_value63 = namesList [63];
//        str_field_value64 = namesList [64];
//        str_field_value65 = namesList [65];
//        str_field_value66 = namesList [66];
//        str_field_value67 = namesList [67];
//        str_field_value68 = namesList [68];
//        str_field_value69 = namesList [69];
//        str_field_value70 = namesList [70];
//        str_field_value71 = namesList [71];
//        str_field_value72 = namesList [72];
//        str_field_value73 = namesList [73];
//        str_field_value74 = namesList [74];
//        str_field_value75 = namesList [75];
//        str_field_value76 = namesList [76];
//        str_field_value77 = namesList [77];
//        str_field_value78 = namesList [78];
//        str_field_value79 = namesList [79];
//        str_field_value80 = namesList [80];
//        str_field_value81 = namesList [81];
//        str_field_value82 = namesList [82];
//        str_field_value83 = namesList [83];
//        str_field_value84 = namesList [84];
//        str_field_value85 = namesList [85];
//        str_field_value86 = namesList [86];
//        str_field_value87 = namesList [87];
//        str_field_value88 = namesList [88];
//        str_field_value89 = namesList [89];
//        str_field_value90 = namesList [90];
//        str_field_value91 = namesList [91];
//        str_field_value92 = namesList [92];
//        str_field_value93 = namesList [93];
//        str_field_value94 = namesList [94];
//        str_field_value95 = namesList [95];
//        str_field_value96 = namesList [96];
//        str_field_value97 = namesList [97];
//        str_field_value98 = namesList [98];
//        str_field_value99 = namesList [99];
//        str_field_value100 = namesList [100];


        String[] namesList1 = Form1_name.split(",");
        str_field_name = namesList1 [0];
        str_field_name1 = namesList1 [1];
        str_field_name2 = namesList1 [2];
        str_field_name3 = namesList1 [3];
        str_field_name4 = namesList1 [4];
        str_field_name5 = namesList1 [5];
        str_field_name6 = namesList1 [6];
        str_field_name7 = namesList1 [7];
        str_field_name8 = namesList1 [8];
        str_field_name9 = namesList1 [9];
        str_field_name10 = namesList1 [10];
        str_field_name11 = namesList1 [11];
        str_field_name12 = namesList1 [12];
        str_field_name13 = namesList1 [13];
        str_field_name14 = namesList1 [14];
        str_field_name15 = namesList1 [15];
        str_field_name16 = namesList1 [16];
        str_field_name17 = namesList1 [17];
        str_field_name18 = namesList1 [18];
        str_field_name19 = namesList1 [19];
//        str_field_name20 = namesList1 [20];
//        str_field_name21 = namesList1 [21];
//        str_field_name22 = namesList1 [22];
//        str_field_name23 = namesList1 [23];
//        str_field_name24 = namesList1 [24];
//        str_field_name25 = namesList1 [25];
//        str_field_name26 = namesList1 [26];
//        str_field_name27 = namesList1 [27];
//        str_field_name28 = namesList1 [28];
//        str_field_name29 = namesList1 [29];
//        str_field_name30 = namesList1 [30];
//        str_field_name31 = namesList1 [31];
//        str_field_name32 = namesList1 [32];
//        str_field_name33 = namesList1 [33];
//        str_field_name34 = namesList1 [34];
//        str_field_name35 = namesList1 [35];
//        str_field_name36 = namesList1 [36];
//        str_field_name37 = namesList1 [37];
//        str_field_name38 = namesList1 [38];
//        str_field_name39 = namesList1 [39];
//        str_field_name40 = namesList1 [40];
//        str_field_name41 = namesList1 [41];
//        str_field_name42 = namesList1 [42];
//        str_field_name43 = namesList1 [43];
//        str_field_name44 = namesList1 [44];
//        str_field_name45 = namesList1 [45];
//        str_field_name46 = namesList1 [46];
//        str_field_name47 = namesList1 [47];
//        str_field_name48 = namesList1 [48];
//        str_field_name49 = namesList1 [49];
//        str_field_name50 = namesList1 [50];
//        str_field_name51 = namesList1 [51];
//        str_field_name52 = namesList1 [52];
//        str_field_name53 = namesList1 [53];
//        str_field_name54 = namesList1 [54];
//        str_field_name55 = namesList1 [55];
//        str_field_name56 = namesList1 [56];
//        str_field_name57 = namesList1 [57];
//        str_field_name58 = namesList1 [58];
//        str_field_name59 = namesList1 [59];
//        str_field_name60 = namesList1 [60];
//        str_field_name61 = namesList1 [61];
//        str_field_name62 = namesList1 [62];
//        str_field_name63 = namesList1 [63];
//        str_field_name64 = namesList1 [64];
//        str_field_name65 = namesList1 [65];
//        str_field_name66 = namesList1 [66];
//        str_field_name67 = namesList1 [67];
//        str_field_name68 = namesList1 [68];
//        str_field_name69 = namesList1 [69];
//        str_field_name70 = namesList1 [70];
//        str_field_name71 = namesList1 [71];
//        str_field_name72 = namesList1 [72];
//        str_field_name73 = namesList1 [73];
//        str_field_name74 = namesList1 [74];
//        str_field_name75 = namesList1 [75];
//        str_field_name76 = namesList1 [76];
//        str_field_name77 = namesList1 [77];
//        str_field_name78 = namesList1 [78];
//        str_field_name79 = namesList1 [79];
//        str_field_name80 = namesList1 [79];
//        str_field_name81 = namesList1 [81];
//        str_field_name82 = namesList1 [82];
//        str_field_name83 = namesList1 [83];
//        str_field_name84 = namesList1 [84];
//        str_field_name85 = namesList1 [85];
//        str_field_name86 = namesList1 [86];
//        str_field_name87 = namesList1 [87];
//        str_field_name88 = namesList1 [88];
//        str_field_name89 = namesList1 [89];
//        str_field_name90 = namesList1 [90];
//        str_field_name91 = namesList1 [91];
//        str_field_name92 = namesList1 [92];
//        str_field_name93 = namesList1 [93];
//        str_field_name94 = namesList1 [94];
//        str_field_name95 = namesList1 [95];
//        str_field_name96 = namesList1 [96];
//        str_field_name97 = namesList1 [97];
//        str_field_name98 = namesList1 [98];
//        str_field_name99 = namesList1 [99];
//        str_field_name100 = namesList1 [100];


        String[] namesList2 = Form1_comments.split(",");
        str_field_comments = namesList2 [0];
        str_field_comments1 = namesList2 [1];
        str_field_comments2 = namesList2 [2];
        str_field_comments3 = namesList2 [3];
        str_field_comments4 = namesList2 [4];
        str_field_comments5 = namesList2 [5];
        str_field_comments6 = namesList2 [6];
        str_field_comments7 = namesList2 [7];
        str_field_comments8 = namesList2 [8];
        str_field_comments9 = namesList2 [9];
        str_field_comments10 = namesList2 [10];
        str_field_comments11 = namesList2 [11];
        str_field_comments12 = namesList2 [12];
        str_field_comments13 = namesList2[13];
        str_field_comments14 = namesList2 [14];
        str_field_comments15 = namesList2 [15];
        str_field_comments16 = namesList2 [16];
        str_field_comments17 = namesList2 [17];
        str_field_comments18 = namesList2 [18];
        str_field_comments19 = namesList2 [19];
//        str_field_comments20 = namesList2 [20];
//        str_field_comments21 = namesList2 [21];
//        str_field_comments22 = namesList2 [22];
//        str_field_comments23 = namesList2 [23];
//        str_field_comments24 = namesList2 [24];
//        str_field_comments25 = namesList2 [25];
//        str_field_comments26 = namesList2 [26];
//        str_field_comments27 = namesList2 [27];
//        str_field_comments28 = namesList2 [28];
//        str_field_comments29 = namesList2 [29];
//        str_field_comments30 = namesList2 [30];
//        str_field_comments31 = namesList2 [31];
//        str_field_comments32 = namesList2 [32];
//        str_field_comments33 = namesList2 [33];
//        str_field_comments34 = namesList2 [34];
//        str_field_comments35 = namesList2 [35];
//        str_field_comments36 = namesList2 [36];
//        str_field_comments37 = namesList2 [37];
//        str_field_comments38 = namesList2 [38];
//        str_field_comments39 = namesList2 [39];
//        str_field_comments40 = namesList2 [40];
//        str_field_comments41 = namesList2 [41];
//        str_field_comments42 = namesList2 [42];
//        str_field_comments43 = namesList2 [43];
//        str_field_comments44 = namesList2 [44];
//        str_field_comments45 = namesList2 [45];
//        str_field_comments46 = namesList2 [46];
//        str_field_comments47 = namesList2 [47];
//        str_field_comments48 = namesList2 [48];
//        str_field_comments49 = namesList2 [49];
//        str_field_comments50 = namesList2 [50];
//        str_field_comments51 = namesList2 [51];
//        str_field_comments52 = namesList2 [52];
//        str_field_comments53 = namesList2 [53];
//        str_field_comments54 = namesList2 [54];
//        str_field_comments55 = namesList2 [55];
//        str_field_comments56 = namesList2 [56];
//        str_field_comments57 = namesList2 [57];
//        str_field_comments58 = namesList2 [58];
//        str_field_comments59 = namesList2 [59];
//        str_field_comments60 = namesList2 [60];
//        str_field_comments61 = namesList2 [61];
//        str_field_comments62 = namesList2 [62];
//        str_field_comments63 = namesList2 [63];
//        str_field_comments64 = namesList2 [64];
//        str_field_comments65 = namesList2 [65];
//        str_field_comments66 = namesList2 [66];
//        str_field_comments67 = namesList2 [67];
//        str_field_comments68 = namesList2 [68];
//        str_field_comments69 = namesList2 [69];
//        str_field_comments70 = namesList2 [70];
//        str_field_comments71 = namesList2 [71];
//        str_field_comments72 = namesList2 [72];
//        str_field_comments73 = namesList2 [73];
//        str_field_comments74 = namesList2 [74];
//        str_field_comments75 = namesList2 [75];
//        str_field_comments76 = namesList2 [76];
//        str_field_comments77 = namesList2 [77];
//        str_field_comments78 = namesList2 [78];
//        str_field_comments79 = namesList2 [79];
//        str_field_comments80 = namesList2 [79];
//        str_field_comments81 = namesList2 [81];
//        str_field_comments82 = namesList2 [82];
//        str_field_comments83 = namesList2 [83];
//        str_field_comments84 = namesList2 [84];
//        str_field_comments85 = namesList2 [85];
//        str_field_comments86 = namesList2 [86];
//        str_field_comments87 = namesList2 [87];
//        str_field_comments88 = namesList2 [88];
//        str_field_comments89 = namesList2 [89];
//        str_field_comments90 = namesList2 [90];
//        str_field_comments91 = namesList2 [91];
//        str_field_comments92 = namesList2 [92];
//        str_field_comments93 = namesList2 [93];
//        str_field_comments94 = namesList2 [94];
//        str_field_comments95 = namesList2 [95];
//        str_field_comments96 = namesList2 [96];
//        str_field_comments97 = namesList2 [97];
//        str_field_comments98 = namesList2 [98];
//        str_field_comments99 = namesList2 [99];
//        str_field_comments100 = namesList2 [100];

        String[] namesList3 = Form1_cat_id.split(",");
        str_field_cat_id = namesList3 [0];
        str_field_cat_id1 = namesList3 [1];
        str_field_cat_id2 = namesList3 [2];
        str_field_cat_id3 = namesList3 [3];
        str_field_cat_id4 = namesList3 [4];
        str_field_cat_id5 = namesList3 [5];
        str_field_cat_id6 = namesList3 [6];
        str_field_cat_id7 = namesList3 [7];
        str_field_cat_id8 = namesList3 [8];
        str_field_cat_id9 = namesList3 [9];
        str_field_cat_id10 = namesList3 [10];
        str_field_cat_id11 = namesList3 [11];
        str_field_cat_id12 = namesList3 [12];
        str_field_cat_id13 = namesList3[13];
        str_field_cat_id14 = namesList3 [14];
        str_field_cat_id15 = namesList3 [15];
        str_field_cat_id16 = namesList3 [16];
        str_field_cat_id17 = namesList3 [17];
        str_field_cat_id18 = namesList3 [18];
        str_field_cat_id19 = namesList3 [19];
//        str_field_cat_id20 = namesList3 [20];
//        str_field_cat_id21 = namesList3 [21];
//        str_field_cat_id22 = namesList3 [22];
//        str_field_cat_id23 = namesList3 [23];
//        str_field_cat_id24 = namesList3 [24];
//        str_field_cat_id25 = namesList3 [25];
//        str_field_cat_id26 = namesList3 [26];
//        str_field_cat_id27 = namesList3 [27];
//        str_field_cat_id28 = namesList3 [28];
//        str_field_cat_id29 = namesList3 [29];
//        str_field_cat_id30 = namesList3 [30];
//        str_field_cat_id31 = namesList3 [31];
//        str_field_cat_id32 = namesList3 [32];
//        str_field_cat_id33 = namesList3 [33];
//        str_field_cat_id34 = namesList3 [34];
//        str_field_cat_id35 = namesList3 [35];
//        str_field_cat_id36 = namesList3 [36];
//        str_field_cat_id37 = namesList3 [37];
//        str_field_cat_id38 = namesList3 [38];
//        str_field_cat_id39 = namesList3 [39];
//        str_field_cat_id40 = namesList3 [40];
//        str_field_cat_id41 = namesList3 [41];
//        str_field_cat_id42 = namesList3 [42];
//        str_field_cat_id43 = namesList3 [43];
//        str_field_cat_id44 = namesList3 [44];
//        str_field_cat_id45 = namesList3 [45];
//        str_field_cat_id46 = namesList3 [46];
//        str_field_cat_id47 = namesList3 [47];
//        str_field_cat_id48 = namesList3 [48];
//        str_field_cat_id49 = namesList3 [49];
//        str_field_cat_id50 = namesList3 [50];
//        str_field_cat_id51 = namesList3 [51];
//        str_field_cat_id52 = namesList3 [52];
//        str_field_cat_id53 = namesList3 [53];
//        str_field_cat_id54 = namesList3 [54];
//        str_field_cat_id55 = namesList3 [55];
//        str_field_cat_id56 = namesList3 [56];
//        str_field_cat_id57 = namesList3 [57];
//        str_field_cat_id58 = namesList3 [58];
//        str_field_cat_id59 = namesList3 [59];
//        str_field_cat_id60 = namesList3 [60];
//        str_field_cat_id61 = namesList3 [61];
//        str_field_cat_id62 = namesList3 [62];
//        str_field_cat_id63 = namesList3 [63];
//        str_field_cat_id64 = namesList3 [64];
//        str_field_cat_id65 = namesList3 [65];
//        str_field_cat_id66 = namesList3 [66];
//        str_field_cat_id67 = namesList3 [67];
//        str_field_cat_id68 = namesList3 [68];
//        str_field_cat_id69 = namesList3 [69];
//        str_field_cat_id70 = namesList3 [70];
//        str_field_cat_id71 = namesList3 [71];
//        str_field_cat_id72 = namesList3 [72];
//        str_field_cat_id73 = namesList3 [73];
//        str_field_cat_id74 = namesList3 [74];
//        str_field_cat_id75 = namesList3 [75];
//        str_field_cat_id76 = namesList3 [76];
//        str_field_cat_id77 = namesList3 [77];
//        str_field_cat_id78 = namesList3 [78];
//        str_field_cat_id79 = namesList3 [79];
//        str_field_cat_id80 = namesList3 [79];
//        str_field_cat_id81 = namesList3 [81];
//        str_field_cat_id82 = namesList3 [82];
//        str_field_cat_id83 = namesList3 [83];
//        str_field_cat_id84 = namesList3 [84];
//        str_field_cat_id85 = namesList3 [85];
//        str_field_cat_id86 = namesList3 [86];
//        str_field_cat_id87 = namesList3 [87];
//        str_field_cat_id88 = namesList3 [88];
//        str_field_cat_id89 = namesList3 [89];
//        str_field_cat_id90 = namesList3 [90];
//        str_field_cat_id91 = namesList3 [91];
//        str_field_cat_id92 = namesList3 [92];
//        str_field_cat_id93 = namesList3 [93];
//        str_field_cat_id94 = namesList3 [94];
//        str_field_cat_id95 = namesList3 [95];
//        str_field_cat_id96 = namesList3 [96];
//        str_field_cat_id97 = namesList3 [97];
//        str_field_cat_id98 = namesList3 [98];
//        str_field_cat_id99 = namesList3 [99];
//        str_field_cat_id100 = namesList3 [100];

        String[] namesList4 = Form1_group_id.split(",");
        str_field_group_id = namesList4 [0];
        str_field_group_id1 = namesList4 [1];
        str_field_group_id2 = namesList4 [2];
        str_field_group_id3 = namesList4 [3];
        str_field_group_id4 = namesList4 [4];
        str_field_group_id5 = namesList4 [5];
        str_field_group_id6 = namesList4 [6];
        str_field_group_id7 = namesList4 [7];
        str_field_group_id8 = namesList4 [8];
        str_field_group_id9 = namesList4 [9];
        str_field_group_id10 = namesList4 [10];
        str_field_group_id11 = namesList4 [11];
        str_field_group_id12 = namesList4 [12];
        str_field_group_id13 = namesList4[13];
        str_field_group_id14 = namesList4 [14];
        str_field_group_id15 = namesList4 [15];
        str_field_group_id16 = namesList4 [16];
        str_field_group_id17 = namesList4 [17];
        str_field_group_id18 = namesList4 [18];
        str_field_group_id19 = namesList4 [19];
//        str_field_group_id20 = namesList4 [20];
//        str_field_group_id21 = namesList4 [21];
//        str_field_group_id22 = namesList4 [22];
//        str_field_group_id23 = namesList4 [23];
//        str_field_group_id24 = namesList4 [24];
//        str_field_group_id25 = namesList4 [25];
//        str_field_group_id26 = namesList4 [26];
//        str_field_group_id27 = namesList4 [27];
//        str_field_group_id28 = namesList4 [28];
//        str_field_group_id29 = namesList4 [29];
//        str_field_group_id30 = namesList4 [30];
//        str_field_group_id31 = namesList4 [31];
//        str_field_group_id32 = namesList4 [32];
//        str_field_group_id33 = namesList4 [33];
//        str_field_group_id34 = namesList4 [34];
//        str_field_group_id35 = namesList4 [35];
//        str_field_group_id36 = namesList4 [36];
//        str_field_group_id37 = namesList4 [37];
//        str_field_group_id38 = namesList4 [38];
//        str_field_group_id39 = namesList4 [39];
//        str_field_group_id40 = namesList4 [40];
//        str_field_group_id41 = namesList4 [41];
//        str_field_group_id42 = namesList4 [42];
//        str_field_group_id43 = namesList4 [43];
//        str_field_group_id44 = namesList4 [44];
//        str_field_group_id45 = namesList4 [45];
//        str_field_group_id46 = namesList4 [46];
//        str_field_group_id47 = namesList4 [47];
//        str_field_group_id48 = namesList4 [48];
//        str_field_group_id49 = namesList4 [49];
//        str_field_group_id50 = namesList4 [50];
//        str_field_group_id51 = namesList4 [51];
//        str_field_group_id52 = namesList4 [52];
//        str_field_group_id53 = namesList4 [53];
//        str_field_group_id54 = namesList4 [54];
//        str_field_group_id55 = namesList4 [55];
//        str_field_group_id56 = namesList4 [56];
//        str_field_group_id57 = namesList4 [57];
//        str_field_group_id58 = namesList4 [58];
//        str_field_group_id59 = namesList4 [59];
//        str_field_group_id60 = namesList4 [60];
//        str_field_group_id61 = namesList4 [61];
//        str_field_group_id62 = namesList4 [62];
//        str_field_group_id63 = namesList4 [63];
//        str_field_group_id64 = namesList4 [64];
//        str_field_group_id65 = namesList4 [65];
//        str_field_group_id66 = namesList4 [66];
//        str_field_group_id67 = namesList4 [67];
//        str_field_group_id68 = namesList4 [68];
//        str_field_group_id69 = namesList4 [69];
//        str_field_group_id70 = namesList4 [70];
//        str_field_group_id71 = namesList4 [71];
//        str_field_group_id72 = namesList4 [72];
//        str_field_group_id73 = namesList4 [73];
//        str_field_group_id74 = namesList4 [74];
//        str_field_group_id75 = namesList4 [75];
//        str_field_group_id76 = namesList4 [76];
//        str_field_group_id77 = namesList4 [77];
//        str_field_group_id78 = namesList4 [78];
//        str_field_group_id79 = namesList4 [79];
//        str_field_group_id80 = namesList4 [79];
//        str_field_group_id81 = namesList4 [81];
//        str_field_group_id82 = namesList4 [82];
//        str_field_group_id83 = namesList4 [83];
//        str_field_group_id84 = namesList4 [84];
//        str_field_group_id85 = namesList4 [85];
//        str_field_group_id86 = namesList4 [86];
//        str_field_group_id87 = namesList4 [87];
//        str_field_group_id88 = namesList4 [88];
//        str_field_group_id89 = namesList4 [89];
//        str_field_group_id90 = namesList4 [90];
//        str_field_group_id91 = namesList4 [91];
//        str_field_group_id92 = namesList4 [92];
//        str_field_group_id93 = namesList4 [93];
//        str_field_group_id94 = namesList4 [94];
//        str_field_group_id95 = namesList4 [95];
//        str_field_group_id96 = namesList4 [96];
//        str_field_group_id97 = namesList4 [97];
//        str_field_group_id98 = namesList4 [98];
//        str_field_group_id99 = namesList4 [99];
//        str_field_group_id100 = namesList4 [100];

        job_details_in_text();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Customer_Acknowledgement_PreventiveActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.startjob_popup_layout, null);

        TextView txt_jobstatus = mView.findViewById(R.id.txt_jobstatus);
        TextView txt_job_content = mView.findViewById(R.id.txt_job_content);
        LinearLayout ll_start = mView.findViewById(R.id.ll_start);
        LinearLayout ll_pause = mView.findViewById(R.id.ll_pause);
        LinearLayout ll_stop = mView.findViewById(R.id.ll_stop);
        LinearLayout ll_resume = mView.findViewById(R.id.ll_resume);
        ImageView img_close = mView.findViewById(R.id.img_close);
        Button btn_back = mView.findViewById(R.id.btn_back);
        btn_back.setVisibility(View.GONE);
        txt_jobstatus.setVisibility(View.GONE);
        ll_resume.setVisibility(View.GONE);
        ll_start.setVisibility(View.GONE);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        ll_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_job_status = "Job Started";
                Job_status_update();
                dialog.dismiss();
            }
        });

        ll_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_job_status = "Job Paused";
                Job_status_update();
                dialog.dismiss();
            }
        });

        ll_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_job_status = "Job Stopped";
                Job_status_update();
                dialog.dismiss();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //  onBackPressed();
            }
        });


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                        Intent intent = new Intent(GroupListActivity.this, AllJobListActivity.class);
//                        intent.putExtra("activity_id",activity_id);
//                        intent.putExtra("status",status);
//                        startActivity(intent);
                overridePendingTransition(R.anim.new_right, R.anim.new_left);
            }
        });


        //disable both buttons at start
        saveButton.setEnabled(false);
        clearButton.setEnabled(false);

        //change screen orientation to landscape mode
        //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressDialog = new ProgressDialog(Customer_Acknowledgement_PreventiveActivity.this);
                progressDialog.setMessage("Please Wait Image Upload ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                signatureBitmap = signaturePad.getSignatureBitmap();
                Log.w(TAG, "signatureBitmap" + signatureBitmap);
                File file = new File(getFilesDir(), "Acknowledgment_Signature" + ".jpg");

                OutputStream os;
                try {
                    os = new FileOutputStream(file);
                    if (signatureBitmap != null) {
                        signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    }
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                siganaturePart = MultipartBody.Part.createFormData("sampleFile", userid + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

                uploadDigitalSignatureImageRequest();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        btn_success.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (signatureBitmap == null) {
                    Toast.makeText(Customer_Acknowledgement_PreventiveActivity.this, "Please Drop Signature", Toast.LENGTH_SHORT).show();
                }
                else {
                    locationAddResponseCall();
                }

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(uploadimagepath.equals("")) {
                    Intent send = new Intent(Customer_Acknowledgement_PreventiveActivity.this, Customer_Details_PreventiveActivity.class);
                    send.putExtra("valuess", value_s);
                    send.putExtra("job_id", job_id);
                    send.putExtra("value", value);
                    send.putExtra("service_title", service_title);
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
                    send.putExtra("preventive_check", preventive_check);
                    send.putExtra("pm_status", pm_status);
                    send.putExtra("Tech_signature", tech_signature);
                    send.putExtra("action_req_customer", action_req_customer);
                    send.putExtra("Form1_value", Form1_value);
                    send.putExtra("Form1_name", Form1_name);
                    send.putExtra("Form1_comments", Form1_comments);
                    send.putExtra("Form1_cat_id", Form1_cat_id);
                    send.putExtra("Form1_group_id", Form1_group_id);
                    send.putExtra("customer_no",customer_no);
                    send.putExtra("customer_name",customer_name);
                    startActivity(send);
                }
                else {
                    Intent send = new Intent(Customer_Acknowledgement_PreventiveActivity.this, Customer_Details_PreventiveActivity.class);
                    send.putExtra("valuess", value_s);
                    send.putExtra("job_id", job_id);
                    send.putExtra("value", value);
                    send.putExtra("service_title", service_title);
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
                    send.putExtra("preventive_check", preventive_check);
                    send.putExtra("pm_status", pm_status);
                    send.putExtra("Tech_signature", tech_signature);
                    send.putExtra("action_req_customer", action_req_customer);
                    send.putExtra("Form1_value", Form1_value);
                    send.putExtra("Form1_name", Form1_name);
                    send.putExtra("Form1_comments", Form1_comments);
                    send.putExtra("Form1_cat_id", Form1_cat_id);
                    send.putExtra("Form1_group_id", Form1_group_id);
                    send.putExtra("customer_acknowledgement", uploadimagepath);
                    send.putExtra("customer_no",customer_no);
                    send.putExtra("customer_name",customer_name);
                    startActivity(send);
                }
            }
        });
    }

    private void uploadDigitalSignatureImageRequest() {

        APIInterface apiInterface = RetrofitClient.getImageClient().create(APIInterface.class);
        Call<FileUploadResponse> call = apiInterface.getImageStroeResponse(siganaturePart);
        Log.w(TAG, "url  :%s" + call.request().url().toString());

        call.enqueue(new Callback<FileUploadResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FileUploadResponse> call, @NonNull Response<FileUploadResponse> response) {

                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        Log.w(TAG, "Profpic" + "--->" + new Gson().toJson(response.body()));

                        uploadimagepath = response.body().getData();

                        image.setVisibility(View.INVISIBLE);

                        Log.d("image", uploadimagepath);

                        Toast.makeText(Customer_Acknowledgement_PreventiveActivity.this, "Signature Saved", Toast.LENGTH_SHORT).show();

                        if (uploadimagepath != null) {
                            Glide.with(Customer_Acknowledgement_PreventiveActivity.this)
                                    .load(uploadimagepath)
                                    .into(image);

                            progressDialog.dismiss();

                        }
                    }

                }

            }

            @SuppressLint("LogNotTimber")
            @Override
            public void onFailure(@NonNull Call<FileUploadResponse> call, @NonNull Throwable t) {
                Log.w(TAG, "ServerUrlImagePath" + "On failure working" + t.getMessage());
            }
        });
    }

    public void locationAddResponseCall(){
        dialog = new Dialog(Customer_Acknowledgement_PreventiveActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SubmitPreventiveResponse> call = apiInterface.submitAddPreResponseCall(RestUtils.getContentType(),submitDailyRequest());
        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SubmitPreventiveResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NotNull Call<SubmitPreventiveResponse> call, @NotNull Response<SubmitPreventiveResponse> response) {

                Log.w(TAG, "AddLocationResponse" + new Gson().toJson(response.body()));
                Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

                if (response.body() != null) {
                    dialog.dismiss();
                    if(response.body().getCode() == 200){
                        dialog.dismiss();
                        Log.w(TAG,"url  :%s"+" "+ call.request().url().toString());

                        Log.w(TAG,"dddd %s"+" "+ response.body().getData().toString());

                        Toasty.success(Customer_Acknowledgement_PreventiveActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent send = new Intent( Customer_Acknowledgement_PreventiveActivity.this, ServicesActivity.class);
                        startActivity(send);
                        dialog.dismiss();

                    }else{
                        //  showErrorLoading(response.body().getMessage());
                        dialog.dismiss();
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitPreventiveResponse> call, @NotNull Throwable t) {
                Log.w(TAG,"AddLocationResponseflr"+t.getMessage());
                dialog.dismiss();
            }
        });

    }
    private Preventive_Submit_Request submitDailyRequest() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        Preventive_Submit_Request submitDailyRequest = new Preventive_Submit_Request();
        submitDailyRequest.setJob_status_type(value);
        submitDailyRequest.setMr_status(value_s);
        submitDailyRequest.setMr_1(mr1);
        submitDailyRequest.setMr_2(mr2);
        submitDailyRequest.setMr_3(mr3);
        submitDailyRequest.setMr_4(mr4);
        submitDailyRequest.setMr_5(mr5);
        submitDailyRequest.setMr_6(mr6);
        submitDailyRequest.setMr_7(mr7);
        submitDailyRequest.setMr_8(mr8);
        submitDailyRequest.setMr_9(mr9);
        submitDailyRequest.setMr_10(mr10);
        submitDailyRequest.setPreventive_check(preventive_check);
        submitDailyRequest.setAction_req_customer(action_req_customer);
        submitDailyRequest.setPm_status(pm_status);
        submitDailyRequest.setTech_signature(tech_signature);
        submitDailyRequest.setCustomer_name(customer_name);
        submitDailyRequest.setCustomer_number(customer_no);
        submitDailyRequest.setCustomer_signature(uploadimagepath);
        submitDailyRequest.setUser_mobile_no(se_user_mobile_no);
        submitDailyRequest.setJob_id(job_id);
        submitDailyRequest.setSMU_SCH_COMPNO(compno);
        submitDailyRequest.setSMU_SCH_SERTYPE(sertype);
        Log.e("CompNo",""+compno);
        Log.e("SertYpe", ""+sertype);

        List<Preventive_Submit_Request.Field_valueDatum> mrData = new ArrayList<>();


        for (int i = 0; i < 20; i++) {
            int mynum = i + 1;

            Preventive_Submit_Request.Field_valueDatum mrDatum = new Preventive_Submit_Request.Field_valueDatum();
            
            if (mynum == 1) {
                mrDatum.setField_name(str_field_name);
                mrDatum.setField_comments(str_field_comments);
                mrDatum.setField_value(str_field_value);
                mrDatum.setField_cat_id(str_field_cat_id);
                mrDatum.setField_group_id(str_field_group_id);
            }
            else if (mynum==2) {
                mrDatum.setField_name(str_field_name1);
                mrDatum.setField_comments(str_field_comments1);
                mrDatum.setField_value(str_field_value1);
                mrDatum.setField_cat_id(str_field_cat_id1);
                mrDatum.setField_group_id(str_field_group_id1);
            } 
            else if (mynum==3) {
                mrDatum.setField_name(str_field_name2);
                mrDatum.setField_comments(str_field_comments2);
                mrDatum.setField_value(str_field_value2);
                mrDatum.setField_cat_id(str_field_cat_id2);
                mrDatum.setField_group_id(str_field_group_id2);
            } 
            else if (mynum==4) {
                mrDatum.setField_name(str_field_name3);
                mrDatum.setField_comments(str_field_comments3);
                mrDatum.setField_value(str_field_value3);
                mrDatum.setField_cat_id(str_field_cat_id3);
                mrDatum.setField_group_id(str_field_group_id3);
            } else if (mynum==5) {
                mrDatum.setField_name(str_field_name4);
                mrDatum.setField_comments(str_field_comments4);
                mrDatum.setField_value(str_field_value4);
                mrDatum.setField_cat_id(str_field_cat_id4);
                mrDatum.setField_group_id(str_field_group_id4);
            } else if (mynum==6) {
                mrDatum.setField_name(str_field_name5);
                mrDatum.setField_comments(str_field_comments5);
                mrDatum.setField_value(str_field_value5);
                mrDatum.setField_cat_id(str_field_cat_id5);
                mrDatum.setField_group_id(str_field_group_id5);
            } else if (mynum==7) {
                mrDatum.setField_name(str_field_name6);
                mrDatum.setField_comments(str_field_comments6);
                mrDatum.setField_value(str_field_value6);
                mrDatum.setField_cat_id(str_field_cat_id6);
                mrDatum.setField_group_id(str_field_group_id6);
            } else if (mynum==8) {
                mrDatum.setField_name(str_field_name7);
                mrDatum.setField_comments(str_field_comments7);
                mrDatum.setField_value(str_field_value7);
                mrDatum.setField_cat_id(str_field_cat_id7);
                mrDatum.setField_group_id(str_field_group_id7);
            } else if (mynum==9) {
                mrDatum.setField_name(str_field_name8);
                mrDatum.setField_comments(str_field_comments8);
                mrDatum.setField_value(str_field_value8);
                mrDatum.setField_cat_id(str_field_cat_id8);
                mrDatum.setField_group_id(str_field_group_id8);
            } else if (mynum==10) {
                mrDatum.setField_name(str_field_name9);
                mrDatum.setField_comments(str_field_comments9);
                mrDatum.setField_value(str_field_value9);
                mrDatum.setField_cat_id(str_field_cat_id9);
                mrDatum.setField_group_id(str_field_group_id9);
            }
            else if (mynum==11) {
                mrDatum.setField_name(str_field_name10);
                mrDatum.setField_comments(str_field_comments10);
                mrDatum.setField_value(str_field_value10);
                mrDatum.setField_cat_id(str_field_cat_id10);
                mrDatum.setField_group_id(str_field_group_id10);
            }
            else if (mynum==12) {
                mrDatum.setField_name(str_field_name11);
                mrDatum.setField_comments(str_field_comments11);
                mrDatum.setField_value(str_field_value11);
                mrDatum.setField_cat_id(str_field_cat_id11);
                mrDatum.setField_group_id(str_field_group_id11);
            }
            else if (mynum==13) {
                mrDatum.setField_name(str_field_name12);
                mrDatum.setField_comments(str_field_comments12);
                mrDatum.setField_value(str_field_value12);
                mrDatum.setField_cat_id(str_field_cat_id12);
                mrDatum.setField_group_id(str_field_group_id12);
            }
            else if (mynum==14) {
                mrDatum.setField_name(str_field_name13);
                mrDatum.setField_comments(str_field_comments13);
                mrDatum.setField_value(str_field_value13);
                mrDatum.setField_cat_id(str_field_cat_id13);
                mrDatum.setField_group_id(str_field_group_id13);
            }
            else if (mynum==15) {
                mrDatum.setField_name(str_field_name14);
                mrDatum.setField_comments(str_field_comments14);
                mrDatum.setField_value(str_field_value14);
                mrDatum.setField_cat_id(str_field_cat_id14);
                mrDatum.setField_group_id(str_field_group_id14);
            }
            else if (mynum==16) {
                mrDatum.setField_name(str_field_name15);
                mrDatum.setField_comments(str_field_comments15);
                mrDatum.setField_value(str_field_value15);
                mrDatum.setField_cat_id(str_field_cat_id15);
                mrDatum.setField_group_id(str_field_group_id15);
            }
            else if (mynum==17) {
                mrDatum.setField_name(str_field_name16);
                mrDatum.setField_comments(str_field_comments16);
                mrDatum.setField_value(str_field_value16);
                mrDatum.setField_cat_id(str_field_cat_id16);
                mrDatum.setField_group_id(str_field_group_id16);
            }
            else if (mynum==18) {
                mrDatum.setField_name(str_field_name17);
                mrDatum.setField_comments(str_field_comments17);
                mrDatum.setField_value(str_field_value17);
                mrDatum.setField_cat_id(str_field_cat_id17);
                mrDatum.setField_group_id(str_field_group_id17);
            }
            else if (mynum==19) {
                mrDatum.setField_name(str_field_name18);
                mrDatum.setField_comments(str_field_comments18);
                mrDatum.setField_value(str_field_value18);
                mrDatum.setField_cat_id(str_field_cat_id18);
                mrDatum.setField_group_id(str_field_group_id18);
            }
            else if (mynum==20) {
                mrDatum.setField_name(str_field_name19);
                mrDatum.setField_comments(str_field_comments19);
                mrDatum.setField_value(str_field_value19);
                mrDatum.setField_cat_id(str_field_cat_id19);
                mrDatum.setField_group_id(str_field_group_id19);
            }
//            else if (mynum==21) {
//                mrDatum.setField_name(str_field_name20);
//                mrDatum.setField_comments(str_field_comments20);
//                mrDatum.setField_value(str_field_value20);
//                mrDatum.setField_cat_id(str_field_cat_id20);
//                mrDatum.setField_group_id(str_field_group_id20);
//            }
//            else if (mynum==22) {
//                mrDatum.setField_name(str_field_name21);
//                mrDatum.setField_comments(str_field_comments21);
//                mrDatum.setField_value(str_field_value21);
//                mrDatum.setField_cat_id(str_field_cat_id21);
//                mrDatum.setField_group_id(str_field_group_id21);
//            }
//            else if (mynum==23) {
//                mrDatum.setField_name(str_field_name22);
//                mrDatum.setField_comments(str_field_comments22);
//                mrDatum.setField_value(str_field_value22);
//                mrDatum.setField_cat_id(str_field_cat_id22);
//                mrDatum.setField_group_id(str_field_group_id22);
//            }
//            else if (mynum==24) {
//                mrDatum.setField_name(str_field_name23);
//                mrDatum.setField_comments(str_field_comments23);
//                mrDatum.setField_value(str_field_value23);
//                mrDatum.setField_cat_id(str_field_cat_id23);
//                mrDatum.setField_group_id(str_field_group_id23);
//            }
//            else if (mynum==25) {
//                mrDatum.setField_name(str_field_name24);
//                mrDatum.setField_comments(str_field_comments24);
//                mrDatum.setField_value(str_field_value24);
//                mrDatum.setField_cat_id(str_field_cat_id24);
//                mrDatum.setField_group_id(str_field_group_id24);
//            }
//            else if (mynum==26) {
//                mrDatum.setField_name(str_field_name25);
//                mrDatum.setField_comments(str_field_comments25);
//                mrDatum.setField_value(str_field_value25);
//                mrDatum.setField_cat_id(str_field_cat_id25);
//                mrDatum.setField_group_id(str_field_group_id25);
//            }
//            else if (mynum==27) {
//                mrDatum.setField_name(str_field_name26);
//                mrDatum.setField_comments(str_field_comments26);
//                mrDatum.setField_value(str_field_value26);
//                mrDatum.setField_cat_id(str_field_cat_id26);
//                mrDatum.setField_group_id(str_field_group_id26);
//            }
//            else if (mynum==28) {
//                mrDatum.setField_name(str_field_name27);
//                mrDatum.setField_comments(str_field_comments27);
//                mrDatum.setField_value(str_field_value27);
//                mrDatum.setField_cat_id(str_field_cat_id27);
//                mrDatum.setField_group_id(str_field_group_id27);
//            }
//            else if (mynum==29) {
//                mrDatum.setField_name(str_field_name28);
//                mrDatum.setField_comments(str_field_comments28);
//                mrDatum.setField_value(str_field_value28);
//                mrDatum.setField_cat_id(str_field_cat_id28);
//                mrDatum.setField_group_id(str_field_group_id28);
//            }
//            else if (mynum==30) {
//                mrDatum.setField_name(str_field_name29);
//                mrDatum.setField_comments(str_field_comments29);
//                mrDatum.setField_value(str_field_value29);
//                mrDatum.setField_cat_id(str_field_cat_id29);
//                mrDatum.setField_group_id(str_field_group_id29);
//            }
//            else if (mynum==31) {
//                mrDatum.setField_name(str_field_name30);
//                mrDatum.setField_comments(str_field_comments30);
//                mrDatum.setField_value(str_field_value30);
//                mrDatum.setField_cat_id(str_field_cat_id30);
//                mrDatum.setField_group_id(str_field_group_id30);
//            }
//            else if (mynum==32) {
//                mrDatum.setField_name(str_field_name31);
//                mrDatum.setField_comments(str_field_comments31);
//                mrDatum.setField_value(str_field_value31);
//                mrDatum.setField_cat_id(str_field_cat_id31);
//                mrDatum.setField_group_id(str_field_group_id31);
//            }
//            else if (mynum==33) {
//                mrDatum.setField_name(str_field_name32);
//                mrDatum.setField_comments(str_field_comments32);
//                mrDatum.setField_value(str_field_value32);
//                mrDatum.setField_cat_id(str_field_cat_id32);
//                mrDatum.setField_group_id(str_field_group_id33);
//            }
//            else if (mynum==34) {
//                mrDatum.setField_name(str_field_name33);
//                mrDatum.setField_comments(str_field_comments33);
//                mrDatum.setField_value(str_field_value33);
//                mrDatum.setField_cat_id(str_field_cat_id33);
//                mrDatum.setField_group_id(str_field_group_id33);
//            }
//            else if (mynum==35) {
//                mrDatum.setField_name(str_field_name34);
//                mrDatum.setField_comments(str_field_comments34);
//                mrDatum.setField_value(str_field_value34);
//                mrDatum.setField_cat_id(str_field_cat_id34);
//                mrDatum.setField_group_id(str_field_group_id34);
//            }
//            else if (mynum==36) {
//                mrDatum.setField_name(str_field_name35);
//                mrDatum.setField_comments(str_field_comments35);
//                mrDatum.setField_value(str_field_value35);
//                mrDatum.setField_cat_id(str_field_cat_id35);
//                mrDatum.setField_group_id(str_field_group_id35);
//            }
//            else if (mynum==37) {
//                mrDatum.setField_name(str_field_name36);
//                mrDatum.setField_comments(str_field_comments36);
//                mrDatum.setField_value(str_field_value36);
//                mrDatum.setField_cat_id(str_field_cat_id36);
//                mrDatum.setField_group_id(str_field_group_id36);
//            }
//            else if (mynum==38) {
//                mrDatum.setField_name(str_field_name37);
//                mrDatum.setField_comments(str_field_comments37);
//                mrDatum.setField_value(str_field_value37);
//                mrDatum.setField_cat_id(str_field_cat_id37);
//                mrDatum.setField_group_id(str_field_group_id37);
//            }
//            else if (mynum==39) {
//                mrDatum.setField_name(str_field_name38);
//                mrDatum.setField_comments(str_field_comments38);
//                mrDatum.setField_value(str_field_value38);
//                mrDatum.setField_cat_id(str_field_cat_id38);
//                mrDatum.setField_group_id(str_field_group_id38);
//            }
//            else if (mynum==40) {
//                mrDatum.setField_name(str_field_name39);
//                mrDatum.setField_comments(str_field_comments39);
//                mrDatum.setField_value(str_field_value39);
//                mrDatum.setField_cat_id(str_field_cat_id39);
//                mrDatum.setField_group_id(str_field_group_id39);
//            }
//            else if (mynum==41) {
//                mrDatum.setField_name(str_field_name40);
//                mrDatum.setField_comments(str_field_comments40);
//                mrDatum.setField_value(str_field_value40);
//                mrDatum.setField_cat_id(str_field_cat_id40);
//                mrDatum.setField_group_id(str_field_group_id40);
//            }
//            else if (mynum==42) {
//                mrDatum.setField_name(str_field_name41);
//                mrDatum.setField_comments(str_field_comments41);
//                mrDatum.setField_value(str_field_value41);
//                mrDatum.setField_cat_id(str_field_cat_id41);
//                mrDatum.setField_group_id(str_field_group_id41);
//            }
//            else if (mynum==43) {
//                mrDatum.setField_name(str_field_name42);
//                mrDatum.setField_comments(str_field_comments42);
//                mrDatum.setField_value(str_field_value42);
//                mrDatum.setField_cat_id(str_field_cat_id42);
//                mrDatum.setField_group_id(str_field_group_id42);
//            }
//            else if (mynum==44) {
//                mrDatum.setField_name(str_field_name43);
//                mrDatum.setField_comments(str_field_comments43);
//                mrDatum.setField_value(str_field_value43);
//                mrDatum.setField_cat_id(str_field_cat_id43);
//                mrDatum.setField_group_id(str_field_group_id43);
//            }
//            else if (mynum==45) {
//                mrDatum.setField_name(str_field_name44);
//                mrDatum.setField_comments(str_field_comments44);
//                mrDatum.setField_value(str_field_value44);
//                mrDatum.setField_cat_id(str_field_cat_id44);
//                mrDatum.setField_group_id(str_field_group_id44);
//            }
//            else if (mynum==46) {
//                mrDatum.setField_name(str_field_name45);
//                mrDatum.setField_comments(str_field_comments45);
//                mrDatum.setField_value(str_field_value45);
//                mrDatum.setField_cat_id(str_field_cat_id45);
//                mrDatum.setField_group_id(str_field_group_id45);
//            }
//            else if (mynum==47) {
//                mrDatum.setField_name(str_field_name46);
//                mrDatum.setField_comments(str_field_comments46);
//                mrDatum.setField_value(str_field_value46);
//                mrDatum.setField_cat_id(str_field_cat_id46);
//                mrDatum.setField_group_id(str_field_group_id46);
//            }
//            else if (mynum==48) {
//                mrDatum.setField_name(str_field_name47);
//                mrDatum.setField_comments(str_field_comments47);
//                mrDatum.setField_value(str_field_value47);
//                mrDatum.setField_cat_id(str_field_cat_id47);
//                mrDatum.setField_group_id(str_field_group_id47);
//            }
//            else if (mynum==49) {
//                mrDatum.setField_name(str_field_name48);
//                mrDatum.setField_comments(str_field_comments48);
//                mrDatum.setField_value(str_field_value48);
//                mrDatum.setField_cat_id(str_field_cat_id48);
//                mrDatum.setField_group_id(str_field_group_id48);
//            }
//            else if (mynum==50) {
//                mrDatum.setField_name(str_field_name49);
//                mrDatum.setField_comments(str_field_comments49);
//                mrDatum.setField_value(str_field_value49);
//                mrDatum.setField_cat_id(str_field_cat_id49);
//                mrDatum.setField_group_id(str_field_group_id49);
//            }
//            else if (mynum==51) {
//                mrDatum.setField_name(str_field_name50);
//                mrDatum.setField_comments(str_field_comments50);
//                mrDatum.setField_value(str_field_value50);
//                mrDatum.setField_cat_id(str_field_cat_id50);
//                mrDatum.setField_group_id(str_field_group_id50);
//            }
//            else if (mynum==52) {
//                mrDatum.setField_name(str_field_name51);
//                mrDatum.setField_comments(str_field_comments51);
//                mrDatum.setField_value(str_field_value51);
//                mrDatum.setField_cat_id(str_field_cat_id51);
//                mrDatum.setField_group_id(str_field_group_id51);
//            }
//            else if (mynum==53) {
//                mrDatum.setField_name(str_field_name52);
//                mrDatum.setField_comments(str_field_comments52);
//                mrDatum.setField_value(str_field_value52);
//                mrDatum.setField_cat_id(str_field_cat_id52);
//                mrDatum.setField_group_id(str_field_group_id52);
//            }
//            else if (mynum==54) {
//                mrDatum.setField_name(str_field_name53);
//                mrDatum.setField_comments(str_field_comments53);
//                mrDatum.setField_value(str_field_value53);
//                mrDatum.setField_cat_id(str_field_cat_id53);
//                mrDatum.setField_group_id(str_field_group_id53);
//            }
//            else if (mynum==55) {
//                mrDatum.setField_name(str_field_name54);
//                mrDatum.setField_comments(str_field_comments54);
//                mrDatum.setField_value(str_field_value54);
//                mrDatum.setField_cat_id(str_field_cat_id54);
//                mrDatum.setField_group_id(str_field_group_id54);
//            }
//            else if (mynum==56) {
//                mrDatum.setField_name(str_field_name55);
//                mrDatum.setField_comments(str_field_comments55);
//                mrDatum.setField_value(str_field_value55);
//                mrDatum.setField_cat_id(str_field_cat_id55);
//                mrDatum.setField_group_id(str_field_group_id55);
//            }
//            else if (mynum==57) {
//                mrDatum.setField_name(str_field_name56);
//                mrDatum.setField_comments(str_field_comments56);
//                mrDatum.setField_value(str_field_value56);
//                mrDatum.setField_cat_id(str_field_cat_id56);
//                mrDatum.setField_group_id(str_field_group_id56);
//            }
//            else if (mynum==58) {
//                mrDatum.setField_name(str_field_name57);
//                mrDatum.setField_comments(str_field_comments57);
//                mrDatum.setField_value(str_field_value57);
//                mrDatum.setField_cat_id(str_field_cat_id57);
//                mrDatum.setField_group_id(str_field_group_id57);
//            }
//            else if (mynum==59) {
//                mrDatum.setField_name(str_field_name58);
//                mrDatum.setField_comments(str_field_comments58);
//                mrDatum.setField_value(str_field_value58);
//                mrDatum.setField_cat_id(str_field_cat_id58);
//                mrDatum.setField_group_id(str_field_group_id58);
//            }
//            else if (mynum==60) {
//                mrDatum.setField_name(str_field_name59);
//                mrDatum.setField_comments(str_field_comments59);
//                mrDatum.setField_value(str_field_value59);
//                mrDatum.setField_cat_id(str_field_cat_id59);
//                mrDatum.setField_group_id(str_field_group_id59);
//            }
//            else if (mynum==61) {
//                mrDatum.setField_name(str_field_name60);
//                mrDatum.setField_comments(str_field_comments60);
//                mrDatum.setField_value(str_field_value60);
//                mrDatum.setField_cat_id(str_field_cat_id60);
//                mrDatum.setField_group_id(str_field_group_id60);
//            }
//            else if (mynum==62) {
//                mrDatum.setField_name(str_field_name61);
//                mrDatum.setField_comments(str_field_comments61);
//                mrDatum.setField_value(str_field_value61);
//                mrDatum.setField_cat_id(str_field_cat_id61);
//                mrDatum.setField_group_id(str_field_group_id61);
//            }
//            else if (mynum==63) {
//                mrDatum.setField_name(str_field_name62);
//                mrDatum.setField_comments(str_field_comments62);
//                mrDatum.setField_value(str_field_value62);
//                mrDatum.setField_cat_id(str_field_cat_id62);
//                mrDatum.setField_group_id(str_field_group_id62);
//            }
//            else if (mynum==64) {
//                mrDatum.setField_name(str_field_name63);
//                mrDatum.setField_comments(str_field_comments63);
//                mrDatum.setField_value(str_field_value63);
//                mrDatum.setField_cat_id(str_field_cat_id63);
//                mrDatum.setField_group_id(str_field_group_id63);
//            }
//            else if (mynum==65) {
//                mrDatum.setField_name(str_field_name64);
//                mrDatum.setField_comments(str_field_comments64);
//                mrDatum.setField_value(str_field_value64);
//                mrDatum.setField_cat_id(str_field_cat_id64);
//                mrDatum.setField_group_id(str_field_group_id64);
//            }
//            else if (mynum==66) {
//                mrDatum.setField_name(str_field_name65);
//                mrDatum.setField_comments(str_field_comments65);
//                mrDatum.setField_value(str_field_value65);
//                mrDatum.setField_cat_id(str_field_cat_id65);
//                mrDatum.setField_group_id(str_field_group_id65);
//            }
//            else if (mynum==67) {
//                mrDatum.setField_name(str_field_name66);
//                mrDatum.setField_comments(str_field_comments66);
//                mrDatum.setField_value(str_field_value66);
//                mrDatum.setField_cat_id(str_field_cat_id66);
//                mrDatum.setField_group_id(str_field_group_id66);
//            }
//            else if (mynum==68) {
//                mrDatum.setField_name(str_field_name67);
//                mrDatum.setField_comments(str_field_comments67);
//                mrDatum.setField_value(str_field_value67);
//                mrDatum.setField_cat_id(str_field_cat_id67);
//                mrDatum.setField_group_id(str_field_group_id67);
//            }
//            else if (mynum==69) {
//                mrDatum.setField_name(str_field_name68);
//                mrDatum.setField_comments(str_field_comments68);
//                mrDatum.setField_value(str_field_value68);
//                mrDatum.setField_cat_id(str_field_cat_id68);
//                mrDatum.setField_group_id(str_field_group_id68);
//            }
//            else if (mynum==70) {
//                mrDatum.setField_name(str_field_name69);
//                mrDatum.setField_comments(str_field_comments69);
//                mrDatum.setField_value(str_field_value69);
//                mrDatum.setField_cat_id(str_field_cat_id69);
//                mrDatum.setField_group_id(str_field_group_id69);
//            }
//            else if (mynum==71) {
//                mrDatum.setField_name(str_field_name70);
//                mrDatum.setField_comments(str_field_comments70);
//                mrDatum.setField_value(str_field_value70);
//                mrDatum.setField_cat_id(str_field_cat_id70);
//                mrDatum.setField_group_id(str_field_group_id70);
//            }
//            else if (mynum==72) {
//                mrDatum.setField_name(str_field_name71);
//                mrDatum.setField_comments(str_field_comments71);
//                mrDatum.setField_value(str_field_value71);
//                mrDatum.setField_cat_id(str_field_cat_id71);
//                mrDatum.setField_group_id(str_field_group_id71);
//            }
//            else if (mynum==73) {
//                mrDatum.setField_name(str_field_name72);
//                mrDatum.setField_comments(str_field_comments72);
//                mrDatum.setField_value(str_field_value72);
//                mrDatum.setField_cat_id(str_field_cat_id72);
//                mrDatum.setField_group_id(str_field_group_id72);
//            }
//            else if (mynum==74) {
//                mrDatum.setField_name(str_field_name73);
//                mrDatum.setField_comments(str_field_comments73);
//                mrDatum.setField_value(str_field_value73);
//                mrDatum.setField_cat_id(str_field_cat_id73);
//                mrDatum.setField_group_id(str_field_group_id73);
//            }
//            else if (mynum==75) {
//                mrDatum.setField_name(str_field_name74);
//                mrDatum.setField_comments(str_field_comments74);
//                mrDatum.setField_value(str_field_value74);
//                mrDatum.setField_cat_id(str_field_cat_id74);
//                mrDatum.setField_group_id(str_field_group_id74);
//            }
//            else if (mynum==76) {
//                mrDatum.setField_name(str_field_name75);
//                mrDatum.setField_comments(str_field_comments75);
//                mrDatum.setField_value(str_field_value75);
//                mrDatum.setField_cat_id(str_field_cat_id75);
//                mrDatum.setField_group_id(str_field_group_id75);
//            }
//            else if (mynum==77) {
//                mrDatum.setField_name(str_field_name76);
//                mrDatum.setField_comments(str_field_comments76);
//                mrDatum.setField_value(str_field_value76);
//                mrDatum.setField_cat_id(str_field_cat_id76);
//                mrDatum.setField_group_id(str_field_group_id76);
//            }
//            else if (mynum==78) {
//                mrDatum.setField_name(str_field_name77);
//                mrDatum.setField_comments(str_field_comments77);
//                mrDatum.setField_value(str_field_value77);
//                mrDatum.setField_cat_id(str_field_cat_id77);
//                mrDatum.setField_group_id(str_field_group_id77);
//            }
//            else if (mynum==79) {
//                mrDatum.setField_name(str_field_name78);
//                mrDatum.setField_comments(str_field_comments78);
//                mrDatum.setField_value(str_field_value78);
//                mrDatum.setField_cat_id(str_field_cat_id78);
//                mrDatum.setField_group_id(str_field_group_id78);
//            }
//            else if (mynum==80) {
//                mrDatum.setField_name(str_field_name79);
//                mrDatum.setField_comments(str_field_comments79);
//                mrDatum.setField_value(str_field_value79);
//                mrDatum.setField_cat_id(str_field_cat_id79);
//                mrDatum.setField_group_id(str_field_group_id79);
//            }
//            else if (mynum==81) {
//                mrDatum.setField_name(str_field_name80);
//                mrDatum.setField_comments(str_field_comments80);
//                mrDatum.setField_value(str_field_value80);
//                mrDatum.setField_cat_id(str_field_cat_id80);
//                mrDatum.setField_group_id(str_field_group_id80);
//            }
//            else if (mynum==82) {
//                mrDatum.setField_name(str_field_name81);
//                mrDatum.setField_comments(str_field_comments81);
//                mrDatum.setField_value(str_field_value81);
//                mrDatum.setField_cat_id(str_field_cat_id81);
//                mrDatum.setField_group_id(str_field_group_id81);
//            }
//            else if (mynum==83) {
//                mrDatum.setField_name(str_field_name82);
//                mrDatum.setField_comments(str_field_comments82);
//                mrDatum.setField_value(str_field_value82);
//                mrDatum.setField_cat_id(str_field_cat_id82);
//                mrDatum.setField_group_id(str_field_group_id82);
//            }
//            else if (mynum==84) {
//                mrDatum.setField_name(str_field_name83);
//                mrDatum.setField_comments(str_field_comments83);
//                mrDatum.setField_value(str_field_value83);
//                mrDatum.setField_cat_id(str_field_cat_id83);
//                mrDatum.setField_group_id(str_field_group_id83);
//            }
//            else if (mynum==85) {
//                mrDatum.setField_name(str_field_name84);
//                mrDatum.setField_comments(str_field_comments84);
//                mrDatum.setField_value(str_field_value84);
//                mrDatum.setField_cat_id(str_field_cat_id84);
//                mrDatum.setField_group_id(str_field_group_id84);
//            }
//            else if (mynum==86) {
//                mrDatum.setField_name(str_field_name85);
//                mrDatum.setField_comments(str_field_comments85);
//                mrDatum.setField_value(str_field_value85);
//                mrDatum.setField_cat_id(str_field_cat_id85);
//                mrDatum.setField_group_id(str_field_group_id85);
//            }
//            else if (mynum==87) {
//                mrDatum.setField_name(str_field_name86);
//                mrDatum.setField_comments(str_field_comments86);
//                mrDatum.setField_value(str_field_value86);
//                mrDatum.setField_cat_id(str_field_cat_id86);
//                mrDatum.setField_group_id(str_field_group_id86);
//            }
//            else if (mynum==88) {
//                mrDatum.setField_name(str_field_name87);
//                mrDatum.setField_comments(str_field_comments87);
//                mrDatum.setField_value(str_field_value87);
//                mrDatum.setField_cat_id(str_field_cat_id87);
//                mrDatum.setField_group_id(str_field_group_id87);
//            }
//            else if (mynum==89) {
//                mrDatum.setField_name(str_field_name88);
//                mrDatum.setField_comments(str_field_comments88);
//                mrDatum.setField_value(str_field_value88);
//                mrDatum.setField_cat_id(str_field_cat_id88);
//                mrDatum.setField_group_id(str_field_group_id88);
//            }
//            else if (mynum==90) {
//                mrDatum.setField_name(str_field_name89);
//                mrDatum.setField_comments(str_field_comments89);
//                mrDatum.setField_value(str_field_value89);
//                mrDatum.setField_cat_id(str_field_cat_id89);
//                mrDatum.setField_group_id(str_field_group_id89);
//            }
//            else if (mynum==91) {
//                mrDatum.setField_name(str_field_name90);
//                mrDatum.setField_comments(str_field_comments90);
//                mrDatum.setField_value(str_field_value90);
//                mrDatum.setField_group_id(str_field_group_id90);
//                mrDatum.setField_cat_id(str_field_cat_id90);
//            }
//            else if (mynum==92) {
//                mrDatum.setField_name(str_field_name91);
//                mrDatum.setField_comments(str_field_comments91);
//                mrDatum.setField_value(str_field_value91);
//                mrDatum.setField_cat_id(str_field_cat_id91);
//                mrDatum.setField_group_id(str_field_group_id91);
//            }
//            else if (mynum==93) {
//                mrDatum.setField_name(str_field_name92);
//                mrDatum.setField_comments(str_field_comments92);
//                mrDatum.setField_value(str_field_value92);
//                mrDatum.setField_cat_id(str_field_cat_id92);
//                mrDatum.setField_group_id(str_field_group_id92);
//            }
//            else if (mynum==94) {
//                mrDatum.setField_name(str_field_name93);
//                mrDatum.setField_comments(str_field_comments93);
//                mrDatum.setField_value(str_field_value93);
//                mrDatum.setField_cat_id(str_field_cat_id93);
//                mrDatum.setField_group_id(str_field_group_id93);
//            }
//            else if (mynum==95) {
//                mrDatum.setField_name(str_field_name94);
//                mrDatum.setField_comments(str_field_comments94);
//                mrDatum.setField_value(str_field_value94);
//                mrDatum.setField_cat_id(str_field_cat_id94);
//                mrDatum.setField_group_id(str_field_group_id94);
//            }
//            else if (mynum==96) {
//                mrDatum.setField_name(str_field_name95);
//                mrDatum.setField_comments(str_field_comments95);
//                mrDatum.setField_value(str_field_value95);
//                mrDatum.setField_cat_id(str_field_cat_id95);
//                mrDatum.setField_group_id(str_field_group_id95);
//            }
//            else if (mynum==97) {
//                mrDatum.setField_name(str_field_name96);
//                mrDatum.setField_comments(str_field_comments96);
//                mrDatum.setField_value(str_field_value96);
//                mrDatum.setField_cat_id(str_field_cat_id96);
//                mrDatum.setField_group_id(str_field_group_id96);
//            }
//            else if (mynum==98) {
//                mrDatum.setField_name(str_field_name97);
//                mrDatum.setField_comments(str_field_comments97);
//                mrDatum.setField_value(str_field_value97);
//                mrDatum.setField_cat_id(str_field_cat_id97);
//                mrDatum.setField_group_id(str_field_group_id97);
//            }
//            else if (mynum==99) {
//                mrDatum.setField_name(str_field_name98);
//                mrDatum.setField_comments(str_field_comments98);
//                mrDatum.setField_value(str_field_value98);
//                mrDatum.setField_cat_id(str_field_cat_id98);
//                mrDatum.setField_group_id(str_field_group_id98);
//            }
//            else if (mynum==100) {
//                mrDatum.setField_name(str_field_name99);
//                mrDatum.setField_comments(str_field_comments99);
//                mrDatum.setField_value(str_field_value99);
//                mrDatum.setField_cat_id(str_field_cat_id99);
//                mrDatum.setField_group_id(str_field_group_id99);
//            }
//            else if (mynum==101) {
//                mrDatum.setField_name(str_field_name100);
//                mrDatum.setField_comments(str_field_comments100);
//                mrDatum.setField_value(str_field_value100);
//                mrDatum.setField_cat_id(str_field_cat_id100);
//                mrDatum.setField_group_id(str_field_group_id100);
//            }
            //  Log.e("Nish", "" + mynum + mrDatum.getField_value());
                mrData.add(mrDatum);
            }

            submitDailyRequest.setField_valueData(mrData);
            Log.w(TAG, " locationAddRequest" + new Gson().toJson(submitDailyRequest));
            return submitDailyRequest;
        }

    private GetFieldListResponse getFieldListResponse() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        GetFieldListResponse getFieldListResponse = new GetFieldListResponse();
        getFieldListResponse.setCustomer_name("Reva");
        return getFieldListResponse;
    }


    private void Job_status_update() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Job_status_updateResponse> call = apiInterface.job_status_updateResponseCall(com.triton.johnson_tap_app.utils.RestUtils.getContentType(), job_status_updateRequest());
        Log.w(VolleyLog.TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<Job_status_updateResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Job_status_updateResponse> call, @NonNull Response<Job_status_updateResponse> response) {

                Log.w(VolleyLog.TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            Log.d("msg",message);
                        }


                    } else {
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<Job_status_updateResponse> call, @NonNull Throwable t) {
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Job_status_updateRequest job_status_updateRequest() {

        Job_status_updateRequest custom = new Job_status_updateRequest();
        custom.setUser_mobile_no(se_user_mobile_no);
        custom.setService_name(service_title);
        custom.setJob_id(job_id);
        custom.setStatus(str_job_status);
        custom.setSMU_SCH_COMPNO(compno);
        custom.setSMU_SCH_SERTYPE(sertype);
        Log.e("CompNo",""+compno);
        Log.e("SertYpe", ""+sertype);
        Log.w(VolleyLog.TAG,"loginRequest "+ new Gson().toJson(custom));
        return custom;
    }

    private void job_details_in_text() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Job_Details_TextResponse> call = apiInterface.Job_Details_TextResponseCall(RestUtils.getContentType(), custom_detailsRequest());
        Log.w(VolleyLog.TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<Job_Details_TextResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Job_Details_TextResponse> call, @NonNull Response<Job_Details_TextResponse> response) {

                Log.w(VolleyLog.TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

                            String str_address1 = response.body().getData().getText_value();

                            job_details_text.setText(str_address1);
                        }


                    } else {
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Job_Details_TextResponse> call, @NonNull Throwable t) {
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Job_Details_TextRequest custom_detailsRequest() {

        Job_Details_TextRequest custom = new Job_Details_TextRequest();
        custom.setJob_id(job_id);
        custom.setSMU_SCH_COMPNO(compno);
        custom.setSMU_SCH_SERTYPE(sertype);
        Log.e("CompNo",""+compno);
        Log.e("SertYpe", ""+sertype);
        Log.w(VolleyLog.TAG,"loginRequest "+ new Gson().toJson(custom));
        return custom;
    }
}