package com.triton.johnson_tap_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson_tap_app.Service_Activity.InputValueFormListActivity;
import com.triton.johnson_tap_app.Service_Activity.Preventive_Services.Recycler_SpinnerActivity;
import com.triton.johnson_tap_app.interfaces.GetNumberListener;
import com.triton.johnson_tap_app.interfaces.GetSpinnerListener;
import com.triton.johnson_tap_app.interfaces.GetStringListener;
import com.triton.johnson_tap_app.responsepojo.Feedback_GroupResponse;

import java.util.ArrayList;
import java.util.List;


public class ReAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ActivityBasedListAdapter";
    private List<GetFieldListResponse.DataBean> dataBeanList;
    private Context context;
    GetFieldListResponse.DataBean currentItem;
    private int size;
    private List<?> arrayListdropdown;
    int ITEMS_PER_PAGE;
    int TOTAL_NUM_ITEMS;
    int currentPage;
    int check = 0;
    GetSpinnerListener getSpinnerListener;
    GetNumberListener getNumberListener;
    GetStringListener getStringListener;

    public ReAdapter(Context context, List<GetFieldListResponse.DataBean> dataBeanList, int ITEMS_PER_PAGE, int TOTAL_NUM_ITEMS, int currentPage, GetSpinnerListener getSpinnerListener, Recycler_SpinnerActivity getStringListener, Recycler_SpinnerActivity getNumberListener) {
        this.context = context;
        this.dataBeanList = dataBeanList;
      //  this.userTypeSelectListener = userTypeSelectListener;
        this.ITEMS_PER_PAGE = ITEMS_PER_PAGE;
        this.TOTAL_NUM_ITEMS = TOTAL_NUM_ITEMS;
        this.currentPage = currentPage;
        this.getSpinnerListener = getSpinnerListener;
        this.getStringListener = getStringListener;
        this.getNumberListener = getNumberListener ;
    }

//    public ReAdapter(Context applicationContext, List<GetFieldListResponse.DataBean> dataBeanList, int items_per_page, int total_num_items, int currentPage, Recycler_SpinnerActivity getSpinnerListener, Recycler_SpinnerActivity recycler_spinnerActivity, Recycler_SpinnerActivity recycler_spinnerActivity1) {
//    }

    public void filterList(List<GetFieldListResponse.DataBean> filterllist)
    {
        dataBeanList = filterllist;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_spinner, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);
        int startItem = currentPage * ITEMS_PER_PAGE + position;
        if (currentItem.getField_name() != null && !currentItem.getField_name().equals("")) {
            holder.txt_field_title.setText(currentItem.getField_name());
        } else {
            holder.txt_field_title.setText("");

        }

        if (currentItem.getField_comments() != null && !currentItem.getField_comments().equals("")) {
            holder.txt_field_comments.setText(currentItem.getField_comments());
        } else {
            holder.txt_field_comments.setText("");

        }


        if (currentItem.getField_type().equals("String")) {

            holder.edt_string.setVisibility(View.VISIBLE);
            holder.ll_dropdown.setVisibility(View.GONE);
            holder.edt_number.setVisibility(View.GONE);

            if (currentItem.getField_value() != null && !currentItem.getField_value().equals("")) {

                holder.edt_string.setText(currentItem.getField_value());
            }

            holder.edt_string.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    getStringListener.getStringListener(holder.edt_string,s.toString(), startItem, currentItem.getField_length());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
        else if (currentItem.getField_type().equals("Number")) {

            holder.edt_number.setVisibility(View.VISIBLE);
            holder.edt_string.setVisibility(View.GONE);
            holder.ll_dropdown.setVisibility(View.GONE);

            if (currentItem.getField_value() != null && !currentItem.getField_value().equals("")) {

                holder.edt_number.setText(currentItem.getField_value());
            } else {

                holder.edt_number.setText("");
            }


            holder.edt_number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    getNumberListener.getNumberListener(holder.edt_number, s.toString(), startItem, currentItem.getField_length());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        else if (currentItem.getField_type().equals("Dropdown")) {

            holder.edt_number.setVisibility(View.GONE);
            holder.edt_string.setVisibility(View.GONE);
            holder.ll_dropdown.setVisibility(View.VISIBLE);

            arrayListdropdown = currentItem.getDrop_down();
            Log.w(TAG, "currentItem.getDrop_down() : " + arrayListdropdown);

            ArrayList<String> arrayList = new ArrayList<>();

            arrayList.add("Select");

            for (int i = 0; i < currentItem.getDrop_down().size(); i++) {
                String string = currentItem.getDrop_down().get(i).toString();
                Log.w(TAG, "spr string-->" + string);
                arrayList.add(string);

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spr_dropdown.setAdapter(adapter);
            holder.spr_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if (++check > 1) {

                        Log.w(TAG, "currentItem POS " + startItem);

                        Log.w(TAG, "currentItem POS " + parent.getItemAtPosition(pos));

                        getSpinnerListener.getSpinnerListener(holder.spr_dropdown, startItem, parent.getItemAtPosition(pos).toString(), currentItem.getField_length());
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return Math.min(dataBeanList.size(), ITEMS_PER_PAGE);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {

        Spinner spr_dropdown;
        TextView txt_field_title,txt_field_comments;
        LinearLayout ll_dropdown;
        public EditText edt_string, edt_textarea, edt_number, edt_datetime;

        public ViewHolderOne(View itemView) {
            super(itemView);
            ll_dropdown = itemView.findViewById(R.id.ll_dropdown);
            spr_dropdown = itemView.findViewById(R.id.spr_dropdown);
            txt_field_title = itemView.findViewById(R.id.txt_field_title);
            txt_field_comments = itemView.findViewById(R.id.txt_field_comments);
            edt_number = itemView.findViewById(R.id.edt_number);
            edt_string = itemView.findViewById(R.id.edt_string1);
        }
    }
}
